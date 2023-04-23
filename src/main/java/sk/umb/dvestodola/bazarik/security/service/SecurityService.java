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

	private final int MIN_VALUE = 100;
	private final int MAX_VALUE = 999;

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

		String message = "Pre overenie inzerátu zadajte overovací kód:";
		
		String hash = this.hashFunction(code);
		
		this.emailService.sendEmail(email, code, message);
		
		return hash;
	}

	@Transactional
	public String createHashForUpdate(SecurityUpdateDto securityUpdateDto) {
		String advertId = securityUpdateDto.getAdvertId();
		Random random = new Random();
		String code = String.valueOf(random.nextInt(MIN_VALUE, MAX_VALUE));
		String checkEmail = securityUpdateDto.getEmail();
		String advertEmail = "";
		String message = "Pre overenie inzerátu zadajte overovací kód:";

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

		this.emailService.sendEmail(advertEmail, code, message);

		return this.hashFunction(code);
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
