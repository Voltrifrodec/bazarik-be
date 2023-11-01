package sk.umb.dvestodola.bazarik.authentication.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import sk.umb.dvestodola.bazarik.authentication.persistence.entity.UserEntity;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
	public Optional<UserEntity> findByUsername(String username);
}
