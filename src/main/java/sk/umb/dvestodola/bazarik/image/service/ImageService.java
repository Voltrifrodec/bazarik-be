package sk.umb.dvestodola.bazarik.image.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.exception.LibraryApplicationException;
import sk.umb.dvestodola.bazarik.image.persistence.entity.ImageEntity;
import sk.umb.dvestodola.bazarik.image.persistence.repository.ImageRepository;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.imageio.ImageIO;
import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.mariadb.jdbc.MariaDbBlob;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

	private final ImageRepository imageRepository;

	public ImageService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
	}

	public List<ImageDetailDto> getAllImages() {
		return mapToDataTransferObjectList(imageRepository.findAll());
	}

	public ImageDetailDto getImageById(Long imageId) {
		return mapToDataTransferObject(getImageEntityById(imageId));
	}

	public ImageEntity getImageEntityById(Long imageId) {
		Optional<ImageEntity> imageEntity = imageRepository.findById(imageId);

		if(imageEntity.isEmpty()) {
			throw new LibraryApplicationException("Image could not be found, id: " + imageId);
		}

		return imageEntity.get();
	}

	@Transactional
    public Long createImage(ImageRequestDto image) {
        ImageEntity imageEntity = mapToEntity(image);

		return imageRepository.save(imageEntity).getId();
	}

	@Transactional
	public Long uploadImage(MultipartFile file) {
		if (Objects.isNull(file)) { throw new LibraryApplicationException("Image must be valid."); }
		if (Objects.isNull(file.getContentType())) { throw new LibraryApplicationException("Image must be valid."); }

		ImageEntity imageEntity = new ImageEntity();

		Blob imageData = compressImage(file, 1280, 0);

		try {
			imageEntity.setSizeBytes(imageData.length());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		imageEntity.setImage(imageData);
		imageEntity.setType("image/jpg");

		return imageRepository.save(imageEntity).getId();

		/*
			// 1. Scalr
			/* System.out.println(file.getSize());
			BufferedImage imageFile = ImageIO.read(file.getInputStream());
			Method method = Method.SPEED;
			Mode mode = Mode.FIT_TO_WIDTH;
			int width = 400;
			int height = 400;
			BufferedImage compressedImage = Scalr.resize(imageFile, method, mode, width, height);

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(compressedImage, "jpg", baos);
			
			Blob imageData = new SerialBlob(baos.toByteArray());
			imageEntity.setImage(imageData);
			System.out.println(baos.size()); */
	}

	public SerialBlob compressImage(MultipartFile file, int width, int height) {
		try {
			BufferedImage originalImage = ImageIO.read(file.getInputStream());

			int targetWidth = (width > 0) ? width : 720;
			int targetHeight = (height > 0) ? height : (int) ((float)originalImage.getHeight() / (float)originalImage.getWidth() * targetWidth);
	
			BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = resizedImage.createGraphics();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
			graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
			graphics2D.dispose();
			originalImage.flush();
	
			ImageIO.write(resizedImage, "jpg", baos);

			return new SerialBlob(baos.toByteArray());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			throw new LibraryApplicationException("Image could not be processed.");
		}
	}

	@Transactional
	public void updateImage(Long imageId, @Valid ImageRequestDto imageRequestDto) {
		// TODO: Maybe?
		return;
		// ImageEntity imageEntity = getImageEntityById(imageId);

		// imageRepository.save(imageEntity);
	}

	@Transactional
	public void deleteImage(Long imageId) {
		imageRepository.deleteById(imageId);
	}

	@Transactional
	public void deleteAllImages() {
		imageRepository.deleteAll();
	}

	public ImageEntity mapToEntity(ImageRequestDto imageRequest) {
		ImageEntity imageEntity = new ImageEntity();

		byte[] byteArray = imageRequest.getImage().getBytes();

		Blob blob = new MariaDbBlob(byteArray);

		imageEntity.setImage(blob);

		return imageEntity;
	}

	public ImageDetailDto mapToDataTransferObject(ImageEntity imageEntity) {
		ImageDetailDto imageDetailDto = new ImageDetailDto();

		imageDetailDto.setId(imageEntity.getId());
		imageDetailDto.setSizeBytes(imageEntity.getSizeBytes());
		imageDetailDto.setType(imageEntity.getType());

		Blob blob;
		try {
			blob = new SerialBlob(imageEntity.getImage());
			imageDetailDto.setImage(blob);
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//imageEntity.getImage();

		/* try {
			byte[] byteArray = imageEntity.getImage().getBytes(1,(int)blob.length());
		} catch (SQLException e) {
			e.printStackTrace();
		} */

		return imageDetailDto;
	}

	public List<ImageDetailDto> mapToDataTransferObjectList(Iterable<ImageEntity> imageEntities) {
		List<ImageDetailDto> imageDetailDataTransferObjects = new ArrayList<>();

		imageEntities.forEach(imageEntity -> {
			ImageDetailDto imageDetailDataTransferObject = mapToDataTransferObject(imageEntity);
			imageDetailDataTransferObjects.add(imageDetailDataTransferObject);
		});

		return imageDetailDataTransferObjects;
	}


}
