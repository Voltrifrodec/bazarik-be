package sk.umb.dvestodola.bazarik.image.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sk.umb.dvestodola.bazarik.BazarikApplication;
import sk.umb.dvestodola.bazarik.advert.persistence.entity.AdvertEntity;
import sk.umb.dvestodola.bazarik.advert.persistence.repository.AdvertRepository;
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;
import sk.umb.dvestodola.bazarik.image.persistence.entity.ImageEntity;
import sk.umb.dvestodola.bazarik.image.persistence.repository.ImageRepository;

@Service
public class ImageGeneratorService {
	@Autowired ImageRepository imageRepository;
	@Autowired AdvertRepository advertRepository;
	@Autowired RabbitTemplate rabbitTemplate;

	public void generateImage(UUID advertId) {
		ThreadPoolExecutor executor = new ThreadPoolExecutor(10, 10, 1, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());

		executor.execute(new Runnable() {
			@Override
			public void run() {
				Optional<AdvertEntity> optionalAdvertEntity = advertRepository.findById(advertId);
				if (optionalAdvertEntity.isEmpty()) throw new BazarikApplicationException("Could not find advert by id, " + advertId);
				AdvertEntity advertEntity = optionalAdvertEntity.get();
		
				System.out.println("Sent advert name to message queue.");
				byte[] bytes = (byte[]) rabbitTemplate.convertSendAndReceive(BazarikApplication.topicExchangeName, "foo.bar.image", advertEntity.getName());
				System.out.println("Received response from message queue.");

				try {
					BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
					
					ImageEntity imageEntity = new ImageEntity();
					imageEntity.setHeight(img.getHeight());
					imageEntity.setWidth(img.getWidth());
					imageEntity.setOriginalFileName("Generated Image");
					imageEntity.setImage(new SerialBlob(bytes));
		
					imageEntity = imageRepository.save(imageEntity);
					advertEntity.setImage(imageEntity);
					advertRepository.save(advertEntity);
				} catch (IOException | SQLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	public void generateDescription(UUID advertId) {
	}

	@SuppressWarnings(value = "unused")
	private void printResponse(Object response) {
		System.out.print("Printing response: <");
		byte[] bytes = (byte[]) response;
		for (byte b : bytes) {
			System.out.print((char) b);
		}
		System.out.print(">");
	}
}
