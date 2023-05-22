package sk.umb.dvestodola.bazarik.subsubcategory.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.subsubcategory.persistence.entity.SubsubcategoryEntity;

@Repository
public interface SubsubcategoryRepository extends CrudRepository<SubsubcategoryEntity, Long> {
	@Query(value =  """
		SELECT 
			subsub.id_subsubcategory, subsub.subsubcategory_name,
			sub.id_subcategory, sub.subcategory_name, sub.subcategory_number_of_adverts,
			c.id_category, c.category_name, NULL as category_number_of_adverts, c.category_emoji,
			COUNT(a.id_advert) as subsubcategory_number_of_adverts

		FROM subcategory sub
			INNER JOIN subcategory_subsubcategory ss ON sub.id_subcategory = ss.id_subcategory
			LEFT JOIN subsubcategory subsub ON subsub.id_subsubcategory = ss.id_subsubcategory
			INNER JOIN category_subcategory cs ON cs.id_subcategory = ss.id_subcategory
			LEFT JOIN category c ON c.id_category = cs.id_category 
			LEFT JOIN advert a ON a.id_subsubcategory = subsub.id_subsubcategory

		WHERE sub.id_subcategory = :subcategoryId

		GROUP BY subsub.id_subsubcategory;
	""", nativeQuery = true)
	Iterable<SubsubcategoryEntity> getAllBySubcategoryId(@Param("subcategoryId") Long subcategoryId);
}
