package sk.umb.dvestodola.bazarik.region.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.region.persistence.entity.RegionEntity;

@Repository
public interface RegionRepository extends CrudRepository<RegionEntity, Long> {
	Iterable<RegionEntity> findAllById(Long regionId);
}
