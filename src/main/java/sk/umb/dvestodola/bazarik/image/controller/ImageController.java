package sk.umb.dvestodola.bazarik.image.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import sk.umb.dvestodola.bazarik.image.service.ImageDetailDataTransferObject;
import sk.umb.dvestodola.bazarik.image.service.ImageRequestDataTransferObject;
import sk.umb.dvestodola.bazarik.image.service.ImageService;

import java.util.List;

@RestController
public class ImageController {

    // CRUD & Methods
    // - nepotrebujem:
    //      [✕] searchImages()
    // - potrebujem:
    //      [✓] constructor()
    //      [✓] getImage()
    //      [✓] createImage()
    //      [✓] updateImage()
    //      [✓] deleteImage()
    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/api/images/{imageId}")
    public ImageDetailDataTransferObject getImage(@PathVariable Long imageId) {
        System.out.println("Getting image with ID: " + imageId);
        return imageService.getImageById(imageId);
    }

    @GetMapping("/api/images")
    public Long createImage(@Valid @RequestBody ImageRequestDataTransferObject imageRequestDataTransferObject) {
        System.out.println("Add new image...");
        return imageService.createImage(imageRequestDataTransferObject);
    }

    @GetMapping("/api/images/{imageId}")
    public void updateImage(@PathVariable Long imageId, @Valid @RequestBody ImageRequestDataTransferObject imageRequestDataTransferObject) {
        System.out.println("Updating image entry with ID: " + imageId);
        imageService.updateImage(imageId, imageRequestDataTransferObject);
    }

    @GetMapping("/api/images/{imageId}")
    public void deleteImage(@PathVariable Long imageId) {
        System.out.println("Deleting image with ID: " + imageId);
        imageService.deleteImage(imageId);
    }
}
