package sk.umb.dvestodola.bazarik.image.service;

import java.sql.Blob;

public class ImageDetailDto {
    private Long id;
    private Blob image;
	private Long sizeBytes;
	private String type;


    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Long getSizeBytes() {
		return sizeBytes;
	}

	public void setSizeBytes(Long sizeBytes) {
		this.sizeBytes = sizeBytes;
	}

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
