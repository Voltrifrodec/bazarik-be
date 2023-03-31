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
			throw new BazarikApplicationException("Image could not be found, id: " + imageId);
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
		if (Objects.isNull(file)) { throw new BazarikApplicationException("Image file must be valid."); }

		ImageEntity imageEntity = new ImageEntity();
		String imageType = "jpg";
		Blob imageData = compressImage(file, imageType, 1024, 0);

		try {
			imageEntity.setOriginalFileName(file.getOriginalFilename());
			imageEntity.setSizeBytes(imageData.length());
			imageEntity.setImage(imageData);
			imageEntity.setType("image/" + imageType);
			imageEntity.setOriginalSizeBytes((long)(file.getBytes().length));
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

	private SerialBlob compressImage(MultipartFile file, String imageType, int width, int height) {
		try {
			BufferedImage originalImage = ImageIO.read(file.getInputStream());

			if (originalImage == null) {
				throw new BazarikApplicationException("File must be of type image.");
			}
			
			int targetWidth = (width > 0) ? width : 720;
			int targetHeight = (height > 0) ? height : (int) ((float)originalImage.getHeight() / (float)originalImage.getWidth() * targetWidth);

			BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
			Graphics2D graphics2D = resizedImage.createGraphics();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
	
			graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
			graphics2D.dispose();
			originalImage.flush();
	
			ImageIO.write(resizedImage, imageType, baos);

			return new SerialBlob(baos.toByteArray());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
			throw new BazarikApplicationException("Image could not be processed.");
		}
	}


	public ImageEntity mapToImageEntity(ImageRequestDto imageRequest) {
		ImageEntity imageEntity = new ImageEntity();

		byte[] byteArray = imageRequest.getImage().getBytes();

		Blob blob = new MariaDbBlob(byteArray);

		imageEntity.setImage(blob);

		return imageEntity;
	}

	public ImageDetailDto mapToImageDetail(ImageEntity imageEntity) {
		ImageDetailDto imageDetail = new ImageDetailDto();

		imageDetail.setId(imageEntity.getId());
		imageDetail.setOriginalFileName(imageEntity.getOriginalFileName());
		imageDetail.setType(imageEntity.getType());
		imageDetail.setSizeBytes(imageEntity.getSizeBytes());
		imageDetail.setOriginalSizeBytes(imageEntity.getOriginalSizeBytes());

		Blob blob;
		try {
			blob = new SerialBlob(imageEntity.getImage());
			imageDetail.setImage(blob);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return imageDetail;
	}

	public List<ImageDetailDto> mapToImageDetailList(Iterable<ImageEntity> imageEntities) {
		List<ImageDetailDto> imageDetailList = new ArrayList<>();

		imageEntities.forEach(imageEntity -> {
			ImageDetailDto imageDetail = mapToImageDetail(imageEntity);
			imageDetailList.add(imageDetail);
		});

		return imageDetailList;
	}


}
