package sk.umb.dvestodola.bazarik.image.service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.image.persistence.entity.ImageEntity;
import sk.umb.dvestodola.bazarik.image.persistence.repository.ImageRepository;

// import java.sql.Date;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ImageService {

    // CRUD & Methods
    // - nepotrebujem:
    //      • getAllImages()
    //      • getImageById()
    // - potrebujem:
    //      [✓] constructor()
    //      [✓] createImage()
    //      [✓] updateImage()
    //      [✓] deleteImage()
    //      [✓] getImageEntityById() -> ak by sa musel vymazat obrazok na server-side
    //      [✓] mapToEntity()
    //      [✓] mapToDataTransferObjectList
    //      [✓] mapToDataTransferObject

    // Ulozenie repa
    private final ImageRepository imageRepository;

    // Konstruktor
    public ImageService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    // Hladanie obrazka alebo entity obrazka podla id (ak by to vobec bolo potrebne)
    // TODO: Prerobit throw exception osobitne (ako sme robili na cviceni)
    public ImageDetailDataTransferObject getImageById(Long imageId) {
        return mapToDataTransferObject(getImageEntityById(imageId));
    }

    public ImageEntity getImageEntityById(Long imageId) {
        Optional<ImageEntity> imageEntity = imageRepository.findById(imageId);

        if(imageEntity.isEmpty()) {
            return new ImageEntity();
        }

        return imageEntity.get();
    }

    // CRUD
    @Transactional
    public Long createImage(ImageRequestDataTransferObject imageRequestDataTransferObject) {
        ImageEntity imageEntity = mapToEntity(imageRequestDataTransferObject);

        return imageRepository.save(imageEntity).getId();
    }

    @Transactional
    public void updateImage(Long imageId, ImageRequestDataTransferObject imageRequestDataTransferObject) {
        ImageEntity imageEntity = getImageEntityById(imageId);
        imageEntity.setImage(imageRequestDataTransferObject.getImage());
        imageEntity.setLastChange(new Date(/*System.currentTimeMillis()*/));

        imageRepository.save(imageEntity);
    }

    @Transactional
    public void deleteImage(Long imageId) {
        imageRepository.deleteById(imageId);
    }



    // Mapping
    public ImageEntity mapToEntity(ImageRequestDataTransferObject imageRequestDataTransferObject) {
        ImageEntity imageEntity = new ImageEntity();
        imageEntity.setImage(imageRequestDataTransferObject.getImage());
        imageEntity.setLastChange(imageRequestDataTransferObject.getLastChange());

        return imageEntity;
    }

    public ImageDetailDataTransferObject mapToDataTransferObject(ImageEntity imageEntity) {
        ImageDetailDataTransferObject imageDetailDataTransferObject = new ImageDetailDataTransferObject();

        imageDetailDataTransferObject.setId(imageEntity.getId());
        imageDetailDataTransferObject.setImage(imageEntity.getImage());
        imageDetailDataTransferObject.setLastChange(imageEntity.getLastChange());

        return imageDetailDataTransferObject;
    }

    public List<ImageDetailDataTransferObject> mapToDataTransferObjectList(Iterable<ImageEntity> imageEntities) {
        List<ImageDetailDataTransferObject> imageDetailDataTransferObjects = new ArrayList<>();

        imageEntities.forEach(imageEntity -> {
            ImageDetailDataTransferObject imageDetailDataTransferObject = mapToDataTransferObject(imageEntity);
            imageDetailDataTransferObjects.add(imageDetailDataTransferObject);
        });

        return imageDetailDataTransferObjects;
    }

}
