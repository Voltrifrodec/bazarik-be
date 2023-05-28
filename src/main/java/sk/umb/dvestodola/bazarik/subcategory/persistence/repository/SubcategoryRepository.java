package sk.umb.dvestodola.bazarik.subcategory.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.subcategory.persistence.entity.SubcategoryEntity;

@Repository
public interface SubcategoryRepository extends CrudRepository<SubcategoryEntity, Long> {
	@Query(value = """
		SELECT
			sub.id_subcategory, sub.subcategory_name,
			COUNT(a.id_advert) AS subcategory_number_of_adverts,
			c.id_category, c.category_name, c.category_emoji, COUNT(a.id_advert) AS category_number_of_adverts
		
		FROM subcategory sub
			INNER JOIN category_subcategory cs ON cs.id_subcategory = sub.id_subcategory
			LEFT JOIN category c ON c.id_category = cs.id_category
			LEFT JOIN advert a ON a.id_subcategory = sub.id_subcategory

		WHERE c.id_category = :categoryId

		GROUP BY sub.id_subcategory
	""", nativeQuery = true)
	Iterable<SubcategoryEntity> getAllByCategoryId(@Param("categoryId") Long categoryId);
}
