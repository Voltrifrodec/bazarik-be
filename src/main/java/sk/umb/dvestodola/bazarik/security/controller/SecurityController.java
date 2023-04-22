package sk.umb.dvestodola.bazarik.security.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.security.service.SecurityRequestDto;
import sk.umb.dvestodola.bazarik.security.service.SecurityService;
import sk.umb.dvestodola.bazarik.security.service.SecurityUpdateDto;
import sk.umb.dvestodola.bazarik.advert.service.AdvertRequestDto;

@RestController
public class SecurityController {
	private SecurityService securityService;

	public SecurityController(SecurityService securityService) {
		this.securityService = securityService;
	}

	@PostMapping("/api/security/create")
	public String createHashFromAdvert(@Valid @RequestBody AdvertRequestDto advertRequest) {
		System.out.println("Create hash for advert create was called");
		return this.securityService.createHashFromAdvert(advertRequest);
	}

	@PostMapping("/api/security/update")
	public String createHashForUpdate(@RequestBody SecurityUpdateDto securityUpdateDto) {
		System.out.println("Create hash for advert update was called, " + securityUpdateDto.getAdvertId());
		return this.securityService.createHashForUpdate(securityUpdateDto);
	}

	@PostMapping("/api/security/check")
	public Boolean checkCode(@RequestBody SecurityRequestDto securityRequest) {
		System.out.println("Check code with hash was called");
		return this.securityService.checkHash(securityRequest);
	}

}
