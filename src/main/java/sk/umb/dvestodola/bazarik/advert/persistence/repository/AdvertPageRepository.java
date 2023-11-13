package sk.umb.dvestodola.bazarik.advert.persistence.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.advert.persistence.entity.AdvertEntity;

@Repository
public interface AdvertPageRepository extends PagingAndSortingRepository<AdvertEntity, UUID> {
	Page<AdvertEntity> findAll(Pageable pageable);

	Page<AdvertEntity> findAllByCategoryId(Long categoryId, Pageable pageable);
	Page<AdvertEntity> findAllBySubcategoryId(Long subcategoryId, Pageable pageable);
	Page<AdvertEntity> findAllBySubsubcategoryId(Long subsubcategoryId, Pageable pageable);

	@Query(value = "SELECT a FROM advert a WHERE LOWER(a.name) LIKE %:query% OR LOWER(a.description) LIKE %:query%")
	Page<AdvertEntity> findAllByQuery(@Param("query") String query, Pageable pageable);

	// https://www.bezkoder.com/jpa-repository-query/
	Page<AdvertEntity> findByNameContainingOrDescriptionContainingIgnoreCase(String name, String description, Pageable pageable);
}
