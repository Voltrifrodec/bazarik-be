package sk.umb.dvestodola.bazarik.advert.persistence.repository;

import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import sk.umb.dvestodola.bazarik.advert.persistence.entity.AdvertEntity;

@Repository
public class AdvertRepositoryImplementation {
	@PersistenceContext
	private EntityManager entityManager;

	// @Override
	// https://www.baeldung.com/jpa-limit-query-results
	public Iterable<AdvertEntity> findRecent(Long count) {
		return entityManager.createQuery("SELECT a FROM advert a ORDER BY a.dateAdded", 
			AdvertEntity.class).setMaxResults(count.intValue()).getResultList();
	}

}
