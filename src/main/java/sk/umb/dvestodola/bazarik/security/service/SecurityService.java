package sk.umb.dvestodola.bazarik.security.service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.advert.persistence.entity.AdvertEntity;
import sk.umb.dvestodola.bazarik.advert.persistence.repository.AdvertRepository;
import sk.umb.dvestodola.bazarik.advert.service.AdvertRequestDto;
import sk.umb.dvestodola.bazarik.email.service.EmailService;
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;

@Service
public class SecurityService {

	private int MIN_VALUE = 100;
	private int MAX_VALUE = 999;

	@Autowired
	private EmailService emailService;

	private AdvertRepository advertRepository;

	public SecurityService(AdvertRepository advertRepository) {
		this.advertRepository = advertRepository;
	}

	@Transactional
	public String createHashFromAdvert(AdvertRequestDto advertRequest) {
		Random random = new Random();
		String code = String.valueOf(random.nextInt(MIN_VALUE, MAX_VALUE));

		String email = advertRequest.getContactEmail();
		
		String hash = this.hashFunction(code);
		
		this.emailService.sendCodeCreate(email, code);
		
		return hash;
	}

	@Transactional
	public String createHashForUpdate(UUID advertId) {
		Random random = new Random();
		String code = String.valueOf(random.nextInt(MIN_VALUE, MAX_VALUE));
		String email = "";

		Optional<AdvertEntity> advertEntity = this.advertRepository.findById(advertId);
		if (advertEntity.isPresent()) {
			email = advertEntity.get().getContact().getEmail();
		} else {
			throw new BazarikApplicationException("Advert could not be found by id.");
		}

		String hash = this.hashFunction(code);

		this.emailService.sendCodeUpdate(email, code);

		return hash;
	}

	@Transactional
	public Boolean checkHash(SecurityRequestDto securityRequest) {
		String code = securityRequest.getCode();
		String hash = securityRequest.getHash();
		return hash.equals(this.hashFunction(code));
	}

	private String hashFunction(String s) {
		int hash = s.charAt(0);
		int i = 1;

		while (i < s.length()) {
			hash = hash*31 + s.charAt(i);
			i++;
		}

		return String.valueOf(hash);
	}
	
}
