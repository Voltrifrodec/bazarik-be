package sk.umb.dvestodola.bazarik.image.controller;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.*;

import sk.umb.dvestodola.bazarik.image.service.ImageDetailDto;
import sk.umb.dvestodola.bazarik.image.service.ImageRequestDto;
import sk.umb.dvestodola.bazarik.image.service.ImageService;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

    @GetMapping("/api/images/{imageId}")
    public ImageDetailDto getImageById(@PathVariable Long imageId) {
        System.out.println("Get image by id was called, id: " + imageId);
        return imageService.getImageById(imageId);
    }

    @PostMapping("/api/images")
    public Long createImage(@Valid @RequestBody ImageRequestDto image) {
        System.out.println("Create image was called.");
        return imageService.createImage(image);
    }

    @PutMapping("/api/images/{imageId}")
    public void updateImage(@PathVariable Long imageId, @Valid @RequestBody ImageRequestDto image) {
        System.out.println("Update image was called, id: " + imageId);
        imageService.updateImage(imageId, image);
    }

    @DeleteMapping("/api/images/{imageId}")
    public void deleteImage(@PathVariable Long imageId) {
        System.out.println("Delete image was called, id: " + imageId);
        imageService.deleteImage(imageId);
    }
}
