package sk.umb.dvestodola.bazarik.subsubcategory.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import sk.umb.dvestodola.bazarik.subcategory.persistence.entity.SubcategoryEntity;

@Entity(name = "subsubcategory")
public class SubsubcategoryEntity {
	@Id
	@GeneratedValue
	@Column(name = "id_subsubcategory", unique = true)
	private Long id;

	@Column(name = "name")
	private String name;

	@ManyToOne
	@JoinTable(
		name = "subcategory_subsubcategory",
		joinColumns = @JoinColumn(name = "id_subsubcategory"),
		inverseJoinColumns = @JoinColumn(name = "id_subcategory")
	)
	@JoinColumn(nullable = false)
	private SubcategoryEntity subcategory;


	public SubcategoryEntity getSubcategory() {
		return subcategory;
	}

	public void setCategory(SubcategoryEntity category) {
		this.subcategory = category;
	}

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
