package sk.umb.dvestodola.bazarik.subsubcategory.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SubsubcategoryRequestDto {
	@NotBlank(message = "Subsubcategory name must not be blank.")
	private String name;

	@Valid
	@NotNull(message = "SubcategoryID for subsubcategory must not be blank.")
	private Long subcategoryId;

	public Long getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(Long categoryId) {
		this.subcategoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
