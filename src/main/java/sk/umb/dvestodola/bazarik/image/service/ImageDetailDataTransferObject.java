package sk.umb.dvestodola.bazarik.image.service;

import java.util.Date;

public class ImageDetailDataTransferObject {
    private Long id;
    private Long image;
    private Date lastChange;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
    }

    public Date getLastChange() {
        return lastChange;
    }

    public void setLastChange(Date latestChange) {
        this.lastChange = latestChange;
    }
}
