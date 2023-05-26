package sk.umb.dvestodola.bazarik.security.service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;
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

	private final int MIN_VALUE = 100;
	private final int MAX_VALUE = 999;

	// Áno viem, nie je to bezpečné
	private final String SALT = "Ab/N#w5|~+bm>+Cj";

	@Autowired
	private EmailService emailService;

	private AdvertRepository advertRepository;

	public SecurityService(AdvertRepository advertRepository) {
		this.advertRepository = advertRepository;
	}

	@Transactional
	public SecurityDetailDto createHashFromAdvert(AdvertRequestDto advertRequest) {
		SecurityDetailDto securityDetail = new SecurityDetailDto();

		Random random = new Random();
		String code = String.valueOf(random.nextInt(MIN_VALUE, MAX_VALUE));
		String email = advertRequest.getContactEmail();
		String hash = this.hashFunction(code);
		
		this.emailService.sendEmail(email, code);
		
		securityDetail.setHash(hash);

		System.out.println("Code: " + code + ", hash: " + hash);

		return securityDetail;
	}

	@Transactional
	public SecurityDetailDto createHashForUpdate(SecurityUpdateDto securityUpdateDto) {
		SecurityDetailDto securityDetail = new SecurityDetailDto();

		UUID advertId = UUID.fromString(securityUpdateDto.getAdvertId());
		Random random = new Random();
		String code = String.valueOf(random.nextInt(MIN_VALUE, MAX_VALUE));
		String checkEmail = securityUpdateDto.getEmail();
		String advertEmail = "";

		Optional<AdvertEntity> advertEntity = this.advertRepository.findById(advertId);
		if (advertEntity.isPresent()) {
			if (checkEmail.equals(advertEntity.get().getContact().getEmail())) {
				advertEmail = advertEntity.get().getContact().getEmail();
			} else {
				throw new BazarikApplicationException("E-mails must match.");
			}
		} else {
			throw new BazarikApplicationException("Advert could not be found by id.");
		}

		this.emailService.sendEmail(advertEmail, code);

		securityDetail.setHash(this.hashFunction(code));
		
		System.out.println("Code: " + code + ", hash: " + securityDetail.getHash());

		return securityDetail;
	}

	@Transactional
	public Boolean checkHash(SecurityRequestDto securityRequest) {
		String code = securityRequest.getCode();
		String hash = securityRequest.getHash();
		System.out.println(code + " " + hash);
		return hash.equals(this.hashFunction(code));
	}

	// https://www.baeldung.com/sha-256-hashing-java
	public String hashFunction(String stringToHash) {
		return new DigestUtils("SHA3-256").digestAsHex(SALT + stringToHash);
	}
	
}
