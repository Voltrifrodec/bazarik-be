package sk.umb.dvestodola.bazarik.contact.persistence.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.umb.dvestodola.bazarik.contact.persistence.entity.ContactEntity;

@Repository
public interface ContactRepository extends CrudRepository<ContactEntity, Long> {
	Iterable<ContactEntity> findAllByPhoneNumber(String phoneNumber);
	Optional<ContactEntity> findByPhoneNumber(String phoneNumber);
	
	Iterable<ContactEntity> findAllByEmail(String email);
	Optional<ContactEntity> findByEmail(String email);
}