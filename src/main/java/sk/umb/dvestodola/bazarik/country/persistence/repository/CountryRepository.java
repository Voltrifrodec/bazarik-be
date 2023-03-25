package sk.umb.dvestodola.bazarik.country.persistence.repository;

import org.springframework.data.repository.CrudRepository;

import sk.umb.dvestodola.bazarik.country.persistence.entity.CountryEntity;

public interface CountryRepository extends CrudRepository<CountryEntity, Long> {
	
}
