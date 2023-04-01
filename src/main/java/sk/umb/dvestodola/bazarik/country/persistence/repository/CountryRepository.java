package sk.umb.dvestodola.bazarik.country.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.country.persistence.entity.CountryEntity;

@Repository
public interface CountryRepository extends CrudRepository<CountryEntity, Long> {
	
}
