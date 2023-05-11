package sk.umb.dvestodola.bazarik.authentication.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.umb.dvestodola.bazarik.authentication.persistence.entity.TokenEntity;

import java.util.Optional;

@Repository
public interface TokenRepository extends CrudRepository<TokenEntity, Long> {
	Optional<TokenEntity> findByToken(String token);

	Long deleteByToken(String token);
}
