package sk.umb.dvestodola.bazarik.currency.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.currency.persistence.entity.CurrencyEntity;

@Repository
public interface CurrencyRepository extends CrudRepository<CurrencyEntity, Long> {
	Optional<CurrencyEntity> findBySymbol(String symbol);
}
