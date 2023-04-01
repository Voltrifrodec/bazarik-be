package sk.umb.dvestodola.bazarik.subsubcategory.service;

import sk.umb.dvestodola.bazarik.subcategory.service.SubcategoryDetailDto;

public class SubsubcategoryDetailDto {
	private Long id;
	private String name;
	private SubcategoryDetailDto subcategory;

	
	public SubcategoryDetailDto getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(SubcategoryDetailDto subcategory) {
		this.subcategory = subcategory;
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
