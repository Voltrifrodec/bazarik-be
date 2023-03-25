package sk.umb.dvestodola.bazarik.image.service;

import java.util.Date;

public class ImageRequestDataTransferObject {

    private Long image;
    private Date lastChange;

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
