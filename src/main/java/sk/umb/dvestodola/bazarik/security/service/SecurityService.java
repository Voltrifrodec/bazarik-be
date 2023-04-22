package sk.umb.dvestodola.bazarik.security.service;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.advert.service.AdvertRequestDto;
import sk.umb.dvestodola.bazarik.email.service.EmailService;

@Service
public class SecurityService {

	private int MIN_VALUE = 100;
	private int MAX_VALUE = 999;

	@Autowired
	private EmailService emailService;

	@Transactional
	public String createHashFromAdvert(AdvertRequestDto advertRequest) {
		Random random = new Random();
		Long randomNumber = (long)random.nextInt(MIN_VALUE, MAX_VALUE);
		String code = randomNumber.toString();

		String email = advertRequest.getContactEmail();
		
		String hash = this.hashFunction(code);

		// this.emailService.sendKey(email, code);
		System.out.println("Posielam na mail: " + email + ", code: " + code);

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
