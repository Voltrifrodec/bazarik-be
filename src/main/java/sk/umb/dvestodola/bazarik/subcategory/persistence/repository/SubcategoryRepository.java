package sk.umb.dvestodola.bazarik.subcategory.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import sk.umb.dvestodola.bazarik.subcategory.persistence.entity.SubcategoryEntity;

public interface SubcategoryRepository extends CrudRepository<SubcategoryEntity, Long> {
	Iterable<SubcategoryEntity> getAllByCategoryId(Long categoryId);
}
