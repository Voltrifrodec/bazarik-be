package sk.umb.dvestodola.bazarik.district.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class DistrictRequestDto {
	@NotBlank(message = "Region name must not be blank.")
	private String name;

	@NotBlank(message = "District postcode must not be blank.")
	private String postcode;

	@Valid
	@NotNull(message = "Region ID for district must not be null.")
	private Long regionId;


	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long countryId) {
		this.regionId = countryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
}
