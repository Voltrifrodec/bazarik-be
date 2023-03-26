package sk.umb.dvestodola.bazarik.pictures.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
public class PictureController {
    
    @GetMapping("/api/pictures")
    public void getImages() {
        System.out.println("Hello world!");
    }

    @GetMapping("/api/pictures/{pictureId}")
    public void getImageById(@Valid @PathVariable Long pictureId) {
        System.out.println("Searching for picture with ID: " + pictureId);
    }

    @PostMapping("/api/pictures")
    public void createImage() {
        System.out.println("Creating new picture...");
    }

    @PutMapping("/api/pictures/{pictureId}")
    public void updateImage(@Valid @PathVariable Long pictureId) {
        System.out.println("Updating picture with ID: " + pictureId);
    }

    @DeleteMapping("/api/pictures/{pictureId}")
    public void deleteImage(@PathVariable Long pictureId) {
        System.out.println("Deleting picture with ID: " + pictureId);
    }

}
