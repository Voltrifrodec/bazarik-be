package sk.umb.dvestodola.bazarik.subcategory.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.subcategory.persistence.entity.SubcategoryEntity;

@Repository
public interface SubcategoryRepository extends CrudRepository<SubcategoryEntity, Long> {
	@Query(value = """
		SELECT s.id_subcategory, s.subcategory_name, COUNT(a.id_advert) AS subcategory_number_of_adverts, c.id_category, c.name, c.emoji, COUNT(a.id_advert) AS number_of_adverts
		FROM subcategory s
		INNER JOIN category_subcategory cs ON cs.id_subcategory = s.id_subcategory
		LEFT JOIN category c ON c.id_category = cs.id_category
		LEFT JOIN advert a ON a.id_subcategory = s.id_subcategory
		WHERE cs.id_category = :categoryId
		GROUP BY cs.id_subcategory
	""", nativeQuery = true)
	Iterable<SubcategoryEntity> getAllByCategoryId(@Param("categoryId") Long categoryId);
}
