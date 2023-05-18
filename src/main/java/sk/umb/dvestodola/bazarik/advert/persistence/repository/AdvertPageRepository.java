package sk.umb.dvestodola.bazarik.advert.persistence.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.advert.persistence.entity.AdvertEntity;

@Repository
public interface AdvertPageRepository extends PagingAndSortingRepository<AdvertEntity, Long> {
	Page<AdvertEntity> findAll(Pageable pageable);

	Page<AdvertEntity> findAllByCategoryId(Long categoryId, Pageable pageable);
	Page<AdvertEntity> findAllBySubcategoryId(Long subcategoryId, Pageable pageable);
	Page<AdvertEntity> findAllBySubsubcategoryId(Long subsubcategoryId, Pageable pageable);
}
