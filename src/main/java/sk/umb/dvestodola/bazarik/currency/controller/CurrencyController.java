package sk.umb.dvestodola.bazarik.currency.controller;

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
import sk.umb.dvestodola.bazarik.currency.service.CurrencyService;
import sk.umb.dvestodola.bazarik.currency.service.CurrencyDetailDto;
import sk.umb.dvestodola.bazarik.currency.service.CurrencyRequestDto;

@RestController
public class CurrencyController {

	private final CurrencyService currencyService;

	public CurrencyController(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	@GetMapping("/api/currencies")
	public List<CurrencyDetailDto> getAllCurrencies() {
		System.out.println("Get all currencies was called.");
		return currencyService.getAllCurrencies();
	}

	@GetMapping("/api/currencies/{currencyId}")
	public CurrencyDetailDto getCurrencyById(@PathVariable Long currencyId) {
		System.out.println("Get currency by id was called, " + currencyId);
		return currencyService.getCurrencyById(currencyId);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/api/currencies")
	public Long createCurrency(@Valid @RequestBody CurrencyRequestDto currencyRequest) {
		System.out.println("Create currency was called.");
		return currencyService.createCurrency(currencyRequest);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/api/currencies/{currencyId}")
	public void updateCurrency(@PathVariable Long currencyId, @Valid @RequestBody CurrencyRequestDto currencyRequest) {
		System.out.println("Update currency was called, " + currencyId);
		currencyService.updateCurrency(currencyId, currencyRequest);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/api/currencies/{currencyId}")
	public void deleteCurrency(@PathVariable Long currencyId) {
		System.out.println("Delete currency was called, " + currencyId);
		currencyService.deleteCurrency(currencyId);
	}
}
