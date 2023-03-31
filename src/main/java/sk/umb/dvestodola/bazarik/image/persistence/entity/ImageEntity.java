package sk.umb.dvestodola.bazarik.image.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.sql.Blob;

@Entity(name = "image")
public class ImageEntity {
    @Id
    @GeneratedValue
    private Long id;

	@Column(name = "image", nullable = false)
    private Blob image;

	@Column(name = "size_bytes")
	private Long sizeBytes;

	@Column(name= "type")
	private String type;
	

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public Long getSizeBytes() {
		return sizeBytes;
	}

	public void setSizeBytes(Long sizeBytes) {
		this.sizeBytes = sizeBytes;
	}
}
