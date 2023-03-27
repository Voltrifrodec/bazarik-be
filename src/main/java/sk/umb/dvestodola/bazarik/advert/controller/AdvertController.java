package sk.umb.dvestodola.bazarik.advert.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.advert.service.AdvertService;
import sk.umb.dvestodola.bazarik.advert.service.AdvertDetailDto;
import sk.umb.dvestodola.bazarik.advert.service.AdvertRequestDto;

@RestController
public class AdvertController {

	private final AdvertService advertService;

	public AdvertController(AdvertService advertService) {
		this.advertService = advertService;
	}

	@GetMapping("/api/adverts")
	public List<AdvertDetailDto> getAllAdverts() {
		System.out.println("Get all adverts was called.");
		return advertService.getAllAdverts();
	}

	// TODO: Get mapping for adverts in category
	// TODO: Get mapping for all adverts in subcategory
	// TODO: Get mapping for all adverts in subsubcategory

	@GetMapping("/api/adverts/{advertId}")
	public AdvertDetailDto getAdvertById(@PathVariable Long advertId) {
		System.out.println("Get advert was called, " + advertId);
		return advertService.getAdvertById(advertId);
	}

	@PostMapping("/api/adverts")
	public Long createAdvert(@Valid @RequestBody AdvertRequestDto advert) {
		System.out.println("Create advert was called.");
		return advertService.createAdvert(advert);
	}

	@PutMapping("/api/adverts/{advertId}")
	public void updateAdvert(@PathVariable Long advertId, @Valid @RequestBody AdvertRequestDto advert) {
		System.out.println("Update advert was called, " + advertId);
		advertService.updateAdvert(advertId, advert);
	}

	@DeleteMapping("/api/adverts/{advertId}")
	public void deleteAdvert(@PathVariable Long advertId) {
		System.out.println("Delete advert was called, " + advertId);
		advertService.deleteAdvert(advertId);
	}
	
}
