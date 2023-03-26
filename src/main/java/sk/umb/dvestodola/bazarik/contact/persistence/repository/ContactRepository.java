package sk.umb.dvestodola.bazarik.contact.persistence.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import sk.umb.dvestodola.bazarik.contact.persistence.entity.ContactEntity;

@Repository
public interface ContactRepository extends CrudRepository<ContactEntity, Long> {
	Iterable<ContactEntity> findByPhoneNumber(String phoneNumber);

	Iterable<ContactEntity> findByEmail(String email);
}