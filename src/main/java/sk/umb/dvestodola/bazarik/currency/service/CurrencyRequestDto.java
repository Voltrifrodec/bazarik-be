package sk.umb.dvestodola.bazarik.currency.service;

import jakarta.validation.constraints.NotBlank;

public class CurrencyRequestDto {
	@NotBlank(message = "Currency name must not be blank.")
	private String name;

	@NotBlank(message = "Currency symbol must not be blank.")
	private String symbol;

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}
}
