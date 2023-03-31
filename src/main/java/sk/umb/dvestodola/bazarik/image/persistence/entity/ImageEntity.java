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

	@Column(name = "original_file_name")
	private String originalFileName;
	
	@Column(name= "type")
	private String type;
	
	@Column(name = "original_size_bytes")
	private Long originalSizeBytes;
	
	@Column(name = "size_bytes")
	private Long sizeBytes;

	@Column(name = "image", nullable = false)
	private Blob image;
	

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

	public Long getOriginalSizeBytes() {
		return originalSizeBytes;
	}

	public void setOriginalSizeBytes(Long originalSizeBytes) {
		this.originalSizeBytes = originalSizeBytes;
	}

	public String getOriginalFileName() {
		return originalFileName;
	}

	public void setOriginalFileName(String originalFileName) {
		this.originalFileName = originalFileName;
	}
}
