package sk.umb.dvestodola.bazarik.image.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import sk.umb.dvestodola.bazarik.image.persistence.entity.ImageEntity;

@Repository
public interface ImageRepository extends CrudRepository<ImageEntity, Long> {
    // Iterable<ImageEntity> findByDate(Date lastChanged);

	@Query("SELECT i FROM image i WHERE i.sizeBytes > :bytes")
	Iterable<ImageEntity> findAllImagesLargerThanBytes(@Param("bytes") Long bytes);
}
