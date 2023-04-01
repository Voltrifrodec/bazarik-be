package sk.umb.dvestodola.bazarik.subsubcategory.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.subsubcategory.persistence.entity.SubsubcategoryEntity;

@Repository
public interface SubsubcategoryRepository extends CrudRepository<SubsubcategoryEntity, Long> {
	Iterable<SubsubcategoryEntity> getAllBySubcategoryId(Long subcategoryId);

}
