package sk.umb.dvestodola.bazarik.category.service;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDto {
	@NotBlank(message = "Category name must not be blank.")
	private String name;
	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
