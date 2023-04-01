package sk.umb.dvestodola.bazarik.region.service;

import sk.umb.dvestodola.bazarik.country.service.CountryDetailDto;

public class RegionDetailDto {
	private Long id;
	private String name;
	private CountryDetailDto country;

	
	public CountryDetailDto getCountry() {
		return country;
	}

	public void setCountry(CountryDetailDto category) {
		this.country = category;
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
