package sk.umb.dvestodola.bazarik.image.controller;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import sk.umb.dvestodola.bazarik.image.service.ImageDetailDto;
import sk.umb.dvestodola.bazarik.image.service.ImageRequestDto;
import sk.umb.dvestodola.bazarik.image.service.ImageService;

@RestController
public class ImageController {

    private final ImageService imageService;

    public ImageController(ImageService imageService) {
        this.imageService = imageService;
    }

	@GetMapping("/api/images")
	public List<ImageDetailDto> getAllImages() {
        System.out.println("Get all images was called.");
		return imageService.getAllImages();
	}

    @GetMapping("/api/images/{imageId}")
    public ImageDetailDto getImageById(@PathVariable Long imageId) {
        System.out.println("Get image by id was called, id: " + imageId);
        return imageService.getImageById(imageId);
    }

    @PostMapping("/api/images")
    public Long createImage(@Valid @RequestBody ImageRequestDto imageRequest) {
        System.out.println("Create image was called.");
        return imageService.createImage(imageRequest);
    }

	@PostMapping("api/uploadImage")
	public Long handleImageUpload(@Valid @RequestParam("file") MultipartFile file) {
        System.out.println("Upload image was called.");
		return imageService.uploadImage(file);
	}

    @PutMapping("/api/images/{imageId}")
    public void updateImage(@PathVariable Long imageId, @Valid @RequestBody ImageRequestDto imageRequest) {
        System.out.println("Update image was called, id: " + imageId);
        imageService.updateImage(imageId, imageRequest);
    }

    @DeleteMapping("/api/images/{imageId}")
    public void deleteImage(@PathVariable Long imageId) {
        System.out.println("Delete image was called, id: " + imageId);
        imageService.deleteImage(imageId);
    }

	@DeleteMapping("/api/images")
    public void deleteAllImages() {
        System.out.println("Delete all images was called.");
        imageService.deleteAllImages();
    }
}
