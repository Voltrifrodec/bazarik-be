package sk.umb.dvestodola.bazarik.subsubcategory.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.subsubcategory.persistence.entity.SubsubcategoryEntity;

@Repository
public interface SubsubcategoryRepository extends CrudRepository<SubsubcategoryEntity, Long> {
	@Query(value =  """
		SELECT ss.id_subsubcategory, ss.name as subsubcategory_name, s.id_subcategory, s.name as subcategory_name, s.subcategory_number_of_adverts, c.id_category, c.name as category_name, NULL as number_of_adverts, c.emoji, COUNT(a.id_advert) as subsubcategory_number_of_adverts
		FROM subsubcategory ss
		LEFT JOIN subcategory_subsubcategory sss ON ss.id_subsubcategory = sss.id_subsubcategory 

		LEFT JOIN advert a ON a.id_subsubcategory = ss.id_subsubcategory

		INNER JOIN subcategory s ON sss.id_subcategory = s.id_subcategory 

		LEFT JOIN category_subcategory cs ON cs.id_subcategory = s.id_subcategory

		INNER JOIN category c ON c.id_category = cs.id_category

		WHERE sss.id_subcategory = :subcategoryId

		GROUP BY ss.id_subsubcategory;
	""", nativeQuery = true)
	Iterable<SubsubcategoryEntity> getAllBySubcategoryId(@Param("subcategoryId") Long subcategoryId);
}
