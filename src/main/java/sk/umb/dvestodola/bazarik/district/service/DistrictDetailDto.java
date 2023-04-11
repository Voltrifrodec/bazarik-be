package sk.umb.dvestodola.bazarik.district.service;

import sk.umb.dvestodola.bazarik.region.service.RegionDetailDto;

public class DistrictDetailDto {
	private Long id;
	private String name;
	private String postcode;
	private RegionDetailDto region;

	
	public RegionDetailDto getRegion() {
		return region;
	}

	public void setRegion(RegionDetailDto region) {
		this.region = region;
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

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}
}
