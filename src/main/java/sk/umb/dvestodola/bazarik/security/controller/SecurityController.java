package sk.umb.dvestodola.bazarik.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.security.service.SecurityRequestDto;
import sk.umb.dvestodola.bazarik.security.service.SecurityService;
import sk.umb.dvestodola.bazarik.advert.service.AdvertRequestDto;

@RestController
public class SecurityController {
	private SecurityService securityService;

	public SecurityController(SecurityService securityService) {
		this.securityService = securityService;
	}

	@PostMapping("/api/security/hash")
	public String createHashFromAdvert(@Valid @RequestBody AdvertRequestDto advertRequest) {
		return this.securityService.createHashFromAdvert(advertRequest);
	}

	@PostMapping("/api/security/check")
	public Boolean checkCode(@RequestBody SecurityRequestDto securityRequest) {
		return this.securityService.checkHash(securityRequest);
	}

}
