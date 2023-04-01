package sk.umb.dvestodola.bazarik.subcategory.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SubcategoryRequestDto {
	@NotBlank(message = "Subcategory name must not be blank.")
	private String name;

	@Valid
	@NotNull(message = "CategoryID for subcategory must not be blank.")
	private Long categoryId;


	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
