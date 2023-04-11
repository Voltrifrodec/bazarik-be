package sk.umb.dvestodola.bazarik.advert.persistence.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.advert.persistence.entity.AdvertEntity;

@Repository
public interface AdvertRepository extends CrudRepository<AdvertEntity, UUID> {
	Iterable<AdvertEntity> findAllAdvertsByCategoryId(Long categoryId);
	Iterable<AdvertEntity> findAllAdvertsBySubcategoryId(Long subcategoryId);
	Iterable<AdvertEntity> findAllAdvertsBySubsubcategoryId(Long subsubcategoryId);
	Iterable<AdvertEntity> findAllAdvertsByCurrencyId(Long currencyId);
}
