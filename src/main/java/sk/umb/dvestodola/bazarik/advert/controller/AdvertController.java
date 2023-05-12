package sk.umb.dvestodola.bazarik.advert.controller;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
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

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping("/api/adverts")
	public List<AdvertDetailDto> getAllAdverts() {
		System.out.println("Get all adverts was called.");
		return advertService.getAllAdverts();
	}

	@GetMapping("/api/adverts/recent/{count}")
	public List<AdvertDetailDto> getRecentAdverts(@PathVariable(required = false) Long count) {
		System.out.println("Get all recent adverts was called, " + count);
		return advertService.getRecentAdverts((Objects.isNull(count)) ? 4L : count);
	}

	@GetMapping("/api/search/{query}")
	public List<AdvertDetailDto> getAllAdvertsByQuery(@PathVariable String query) {
		System.out.println("Get all adverts by query was called, " + query);
		return advertService.getAllAdvertsByQuery(query);
	}

	@GetMapping("/api/categories/{categoryId}/adverts")
	public List<AdvertDetailDto> getAllAdvertsByCategoryId(@PathVariable Long categoryId) {
		return advertService.getAllAdvertsByCategoryId(categoryId);
	}

	@GetMapping("/api/subcategories/{subcategoryId}/adverts")
	public List<AdvertDetailDto> getAllAdvertsBySubcategoryId(@PathVariable Long subcategoryId) {
		return advertService.getAllAdvertsBySubcategoryId(subcategoryId);
	}

	@GetMapping("/api/subsubcategories/{subsubcategoryId}/adverts")
	public List<AdvertDetailDto> getAllAdvertsBySubsubcategoryId(@PathVariable Long subsubcategoryId) {
		return advertService.getAllAdvertsBySubsubcategoryId(subsubcategoryId);
	}

	@GetMapping("/api/adverts/{advertId}")
	public AdvertDetailDto getAdvertById(@PathVariable UUID advertId) {
		System.out.println("Get advert was called, " + advertId);
		return advertService.getAdvertById(advertId);
	}

	@PostMapping("/api/adverts")
	public UUID createAdvert(@Valid @RequestBody AdvertRequestDto advert) {
		System.out.println("Create advert was called.");
		return advertService.createAdvert(advert);
	}

	@PutMapping("/api/adverts/{advertId}")
	public void updateAdvert(@PathVariable UUID advertId, @Valid @RequestBody AdvertRequestDto advert) {
		System.out.println("Update advert was called, " + advertId);
		advertService.updateAdvert(advertId, advert);
	}

	@DeleteMapping("/api/adverts/{advertId}")
	public void deleteAdvert(@PathVariable UUID advertId) {
		System.out.println("Delete advert was called, " + advertId);
		advertService.deleteAdvert(advertId);
	}
	
}
