package sk.umb.dvestodola.bazarik.district.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.district.persistence.entity.DistrictEntity;

@Repository
public interface DistrictRepository extends CrudRepository<DistrictEntity, Long> {

}
