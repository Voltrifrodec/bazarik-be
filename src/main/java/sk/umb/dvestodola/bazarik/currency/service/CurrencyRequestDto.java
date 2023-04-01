package sk.umb.dvestodola.bazarik.currency.service;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.NotBlank;

public class CurrencyRequestDto {
	@NotBlank(message = "Currency symbol must not be blank.")
	@Length(min = 1, max = 1, message = "Symbol must contain only one character.")
	private String symbol;

	@NotBlank(message = "Currency name must not be blank.")
	private String name;

	
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
