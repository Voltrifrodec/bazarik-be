package sk.umb.dvestodola.bazarik.region.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class RegionRequestDto {
	@NotBlank(message = "Region name must not be blank.")
	private String name;

	@Valid
	@NotNull(message = "Country ID for region must not be blank.")
	private Long countryId;

	public Long getCountryId() {
		return countryId;
	}

	public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
