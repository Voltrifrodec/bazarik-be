package sk.umb.dvestodola.bazarik.country.service;

import jakarta.validation.constraints.NotBlank;

public class CountryRequestDto {
	@NotBlank(message = "Country name must not be blank.")
	private String name;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
