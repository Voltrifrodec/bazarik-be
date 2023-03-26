package sk.umb.dvestodola.bazarik.image.service;

import java.sql.Blob;

public class ImageDetailDto {
    private Long id;
    private Blob image;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Blob getImage() {
        return image;
    }

    public void setImage(Blob image) {
        this.image = image;
    }
}
