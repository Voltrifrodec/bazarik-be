package sk.umb.dvestodola.bazarik.image.service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.exception.LibraryApplicationException;
import sk.umb.dvestodola.bazarik.image.persistence.entity.ImageEntity;
import sk.umb.dvestodola.bazarik.image.persistence.repository.ImageRepository;

import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mariadb.jdbc.MariaDbBlob;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

	private final ImageRepository imageRepository;

	public ImageService(ImageRepository imageRepository) {
		this.imageRepository = imageRepository;
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
	public void updateImage(Long imageId, @Valid ImageRequestDto imageRequestDto) {
		ImageEntity imageEntity = getImageEntityById(imageId);

		// imageEntity.setImage(imageRequestDto.getImage());
		// imageEntity.setLastChange(new Date(/*System.currentTimeMillis()*/));

		imageRepository.save(imageEntity);
	}

	@Transactional
	public void deleteImage(Long imageId) {
		imageRepository.deleteById(imageId);
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

		Blob blob = imageEntity.getImage();

		try {
			byte[] byteArray = imageEntity.getImage().getBytes(1,(int)blob.length());
			imageDetailDto.setImage(byteArray);
		} catch (SQLException e) {
			e.printStackTrace();
		}


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
