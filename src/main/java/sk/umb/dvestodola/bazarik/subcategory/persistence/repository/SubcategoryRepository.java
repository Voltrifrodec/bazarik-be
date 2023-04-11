package sk.umb.dvestodola.bazarik.subcategory.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.subcategory.persistence.entity.SubcategoryEntity;

@Repository
public interface SubcategoryRepository extends CrudRepository<SubcategoryEntity, Long> {
	Iterable<SubcategoryEntity> getAllByCategoryId(Long categoryId);
}
