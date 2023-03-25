package sk.umb.dvestodola.bazarik.subcategory.service;

import sk.umb.dvestodola.bazarik.category.service.CategoryDetailDto;

public class SubcategoryDetailDto {
	private Long id;
	private String name;
	private CategoryDetailDto category;

	public CategoryDetailDto getCategory() {
		return category;
	}

	public void setCategory(CategoryDetailDto category) {
		this.category = category;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
