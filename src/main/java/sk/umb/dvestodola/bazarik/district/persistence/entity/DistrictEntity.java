package sk.umb.dvestodola.bazarik.district.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import sk.umb.dvestodola.bazarik.district.persistence.entity.DistrictEntity;
import sk.umb.dvestodola.bazarik.region.persistence.entity.RegionEntity;

@Entity(name = "district")
public class DistrictEntity {
	@Id
	@GeneratedValue
	@Column(name = "id_district", unique = true)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "postcode")
	private String postcode;

	@ManyToOne
	@JoinColumn(name = "id_region", nullable = false)
	private RegionEntity region;

	
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

	public RegionEntity getRegion() {
		return region;
	}

	public void setRegion(RegionEntity region) {
		this.region = region;
	}

	
}
