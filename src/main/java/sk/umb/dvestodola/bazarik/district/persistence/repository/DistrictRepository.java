package sk.umb.dvestodola.bazarik.district.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import sk.umb.dvestodola.bazarik.district.persistence.entity.DistrictEntity;

public interface DistrictRepository extends CrudRepository<DistrictEntity, Long> {
	Iterable<DistrictEntity> findAllByRegionId(Long regionId);
}
