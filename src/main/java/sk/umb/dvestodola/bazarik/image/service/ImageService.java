package sk.umb.dvestodola.bazarik.image.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;
import sk.umb.dvestodola.bazarik.image.persistence.entity.ImageEntity;
import sk.umb.dvestodola.bazarik.image.persistence.repository.ImageRepository;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
		return mapToImageDetailList(imageRepository.findAll());
	}

	public ImageDetailDto getImageById(Long imageId) {
		return mapToImageDetail(getImageEntityById(imageId));
	}

	public ImageEntity getImageEntityById(Long imageId) {
		Optional<ImageEntity> imageEntity = imageRepository.findById(imageId);

		if(imageEntity.isEmpty()) {
			throw new BazarikApplicationException("Image must have a valid id.");
		}

		return imageEntity.get();
	}

	@Transactional
    public Long createImage(ImageRequestDto imageRequest) {
        ImageEntity imageEntity = mapToImageEntity(imageRequest);

		return imageRepository.save(imageEntity).getId();
	}

	@Transactional
	public Long uploadImage(MultipartFile file) {
		if (Objects.isNull(file)) {
			throw new BazarikApplicationException("Image file must be valid.");
		}

		ImageEntity imageEntity = new ImageEntity();

		String imageFormat = "jpg";
		String imageType = "image/" + imageFormat;
		
		try {
			BufferedImage originalImage = ImageIO.read(file.getInputStream());

			int targetWidth = originalImage.getWidth();
			int targetHeight = originalImage.getHeight();

			// targetHeight = (targetHeight > 0) ? targetHeight : (int) ((float)originalImage.getHeight() / (float)originalImage.getWidth() * targetWidth);

			BufferedImage newImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D renderer = newImage.createGraphics();

			renderer.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
			renderer.dispose();

			ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
			ImageIO.write(newImage, imageFormat, byteArrayOutputStream);
			Blob imageData = new SerialBlob(byteArrayOutputStream.toByteArray());

			imageEntity.setOriginalFileName(file.getOriginalFilename());
			imageEntity.setType(imageType);
			imageEntity.setOriginalSizeBytes((long)(file.getBytes().length));
			imageEntity.setOriginalWidth(originalImage.getWidth());
			imageEntity.setOriginalHeight(originalImage.getHeight());
			imageEntity.setWidth(newImage.getWidth());
			imageEntity.setHeight(newImage.getHeight());
			imageEntity.setSizeBytes(imageData.length());
			imageEntity.setImage(imageData);

		} catch (SQLException | IOException e) {
			e.printStackTrace();
			throw new BazarikApplicationException("Unexpected error when handling image file.");
		}

		return imageRepository.save(imageEntity).getId();
	}

	@Transactional
	public void updateImage(Long imageId, @Valid ImageRequestDto imageRequest) {
		// TODO: Maybe?
		return;
	}

	@Transactional
	public void deleteImage(Long imageId) {
		imageRepository.deleteById(imageId);
	}

	@Transactional
	public void deleteAllImages() {
		imageRepository.deleteAll();
	}

	public ImageEntity mapToImageEntity(ImageRequestDto imageRequest) {
		ImageEntity imageEntity = new ImageEntity();

		byte[] byteArray = imageRequest.getImage().getBytes();

		Blob blob = new MariaDbBlob(byteArray);

		imageEntity.setImage(blob);

		return imageEntity;
	}
	
	public List<ImageDetailDto> mapToImageDetailList(Iterable<ImageEntity> imageEntities) {
		List<ImageDetailDto> imageDetailList = new ArrayList<>();

		imageEntities.forEach(imageEntity -> {
			ImageDetailDto imageDetail = mapToImageDetail(imageEntity);
			imageDetailList.add(imageDetail);
		});

		return imageDetailList;
	}

	public ImageDetailDto mapToImageDetail(ImageEntity imageEntity) {
		ImageDetailDto imageDetail = new ImageDetailDto();

		imageDetail.setId(imageEntity.getId());
		imageDetail.setOriginalFileName(imageEntity.getOriginalFileName());
		imageDetail.setType(imageEntity.getType());
		imageDetail.setOriginalWidth(imageEntity.getOriginalWidth());
		imageDetail.setOriginalHeight(imageEntity.getOriginalHeight());
		imageDetail.setOriginalSizeBytes(imageEntity.getOriginalSizeBytes());
		imageDetail.setWidth(imageEntity.getWidth());
		imageDetail.setHeight(imageEntity.getHeight());
		imageDetail.setSizeBytes(imageEntity.getSizeBytes());

		Blob blob;
		try {
			blob = new SerialBlob(imageEntity.getImage());
			imageDetail.setImage(blob);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return imageDetail;
	}
}
