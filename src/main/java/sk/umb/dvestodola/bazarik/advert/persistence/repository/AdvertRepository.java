package sk.umb.dvestodola.bazarik.advert.persistence.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.advert.persistence.entity.AdvertEntity;

@Repository
public interface AdvertRepository extends CrudRepository<AdvertEntity, UUID> {
	Iterable<AdvertEntity> findAllAdvertsByCategoryId(Long categoryId);
	Iterable<AdvertEntity> findAllAdvertsBySubcategoryId(Long subcategoryId);
	Iterable<AdvertEntity> findAllAdvertsBySubsubcategoryId(Long subsubcategoryId);
	Iterable<AdvertEntity> findAllAdvertsByCurrencyId(Long currencyId);

	// HOW TO: ImageRepository.java
	@Query(value = "SELECT a FROM advert a WHERE LOWER(a.name) LIKE %:query% OR LOWER(a.description) LIKE %:query%")
	Iterable<AdvertEntity> findAllAdvertsByQuery(@Param("query") String query);

	@Query(value = "SELECT COUNT(a) FROM advert a WHERE a.category.id = :categoryId")
	Long getNumberOfAdvertsInCategoryByCategoryId(@Param("categoryId") Long categoryId);

	@Query(value = "SELECT COUNT(a) FROM advert a WHERE a.subcategory.id = :subcategoryId")
	Long getNumberOfAdvertsInSubcategoryBySubcategoryId(@Param("subcategoryId") Long subcategoryId);

	@Query(value = "SELECT COUNT(a) FROM advert a WHERE a.subsubcategory.id = :subsubcategoryId")
	Long getNumberOfAdvertsInSubsubcategoryBySubsubcategoryId(@Param("subsubcategoryId") Long subsubcategoryId);
}
