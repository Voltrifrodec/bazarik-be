package sk.umb.dvestodola.bazarik.country.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "country")
public class CountryEntity {
	@Id
	@GeneratedValue
	@Column(name = "id_country", unique = true)
	private Long id;

	@Column(name = "name")
	private String name;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
