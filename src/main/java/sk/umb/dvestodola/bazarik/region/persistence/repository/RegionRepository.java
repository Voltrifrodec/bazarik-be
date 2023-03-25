package sk.umb.dvestodola.bazarik.region.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import sk.umb.dvestodola.bazarik.region.persistence.entity.RegionEntity;

public interface RegionRepository extends CrudRepository<RegionEntity, Long> {

	Iterable<RegionEntity> findAllById(Long regionId);
	
}
