package sk.umb.dvestodola.bazarik.country.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.country.service.CountryDetailDto;
import sk.umb.dvestodola.bazarik.country.service.CountryRequestDto;
import sk.umb.dvestodola.bazarik.country.service.CountryService;
import sk.umb.dvestodola.bazarik.region.service.RegionDetailDto;

@RestController
public class CountryController {

	private final CountryService countryService;

	public CountryController(CountryService countryService) {
		this.countryService = countryService;
	}

	@GetMapping("/api/countries")
	public List<CountryDetailDto> getAllCountries() {
		System.out.println("Get all countries was called.");
		return countryService.getAllCountries();
	}

	@GetMapping("/api/countries/{countryId}/regions")
	public List<RegionDetailDto> getAllRegionsByCountryId(@PathVariable Long countryId) {
		System.out.println("Get all countries was called.");
		return countryService.getAllRegionsByCountryId(countryId);
	}

	@GetMapping("/api/countries/{countryId}")
	public CountryDetailDto getCountryById(@PathVariable Long countryId) {
		System.out.println("Get country was called, " + countryId);
		return countryService.getCountryById(countryId);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/api/countries")
	public Long createCountry(@Valid @RequestBody CountryRequestDto countryRequest) {
		System.out.println("Create country was called.");
		return countryService.createCountry(countryRequest);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/api/countries/{countryId}")
	public void updateCountry(@PathVariable Long countryId, @Valid @RequestBody CountryRequestDto countryRequest) {
		System.out.println("Update country was called, " + countryId);
		countryService.updateCountry(countryId, countryRequest);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/api/countries/{countryId}")
	public void deleteCountry(@PathVariable Long countryId) {
		System.out.println("Delete country was called, " + countryId);
		countryService.deleteCountry(countryId);
	}
}
