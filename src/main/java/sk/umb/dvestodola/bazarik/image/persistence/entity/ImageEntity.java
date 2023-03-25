package sk.umb.dvestodola.bazarik.image.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

// import java.sql.Blob;
// import java.sql.Date;
import java.util.Date;

@Entity(name = "obrazok")
public class ImageEntity {
    @Id
    @GeneratedValue
    private Long id;

    private Long image;

    private Date lastChange;

    //TODO: Implementovat AddressEntity!
    /*@OneToOne
    private AddressEntity address;

    public AddressEntity getAddress() {
        return address;
    }

    public void setAddress(AddressEntity address) {
        this.address = address;
    }*/

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

    public void setLastChange(Date lastChanged) {
        this.lastChange = lastChanged;
    }
}
