package sk.umb.dvestodola.bazarik.category.persistence.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;

@Repository
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
	@Override
	@Query(value = """
		SELECT c.id_category, c.name, COUNT(a.id_advert) AS number_of_adverts
		FROM category c
		LEFT JOIN advert a ON a.id_category  = c.id_category
		GROUP BY c.id_category

		UNION

		SELECT c.id_category, c.name, COUNT(a.id_advert) AS number_of_adverts
		FROM advert a
		RIGHT JOIN category c ON a.id_category  = c.id_category
		GROUP BY c.id_category
	""", nativeQuery = true)
	Iterable<CategoryEntity> findAll();
}
