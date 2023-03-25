package sk.umb.dvestodola.bazarik.image.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.umb.dvestodola.bazarik.image.persistence.entity.ImageEntity;

import java.util.Date;

@Repository
public interface ImageRepository extends CrudRepository<ImageEntity, Long> {
    Iterable<ImageEntity> findByDate(Date lastChanged);
}
