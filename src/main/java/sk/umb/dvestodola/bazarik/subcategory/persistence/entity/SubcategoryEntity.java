package sk.umb.dvestodola.bazarik.subcategory.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;

@Entity(name = "subcategory")
public class SubcategoryEntity {
	@Id
	@GeneratedValue
	@Column(name = "id_subcategory", unique = true)
	private Long id;

	@Column(name = "subcategory_name")
	private String name;

	@ManyToOne
	@JoinTable(
		name = "category_subcategory",
		joinColumns = @JoinColumn(name = "id_subcategory"),
		inverseJoinColumns = @JoinColumn(name = "id_category")
	)
	@JoinColumn(name = "id_category", nullable = false)
	private CategoryEntity category;

	@Column(name = "subcategory_number_of_adverts")
	private Long numberOfAdverts;


	public Long getNumberOfAdverts() {
		return numberOfAdverts;
	}

	public void setNumberOfAdverts(Long numberOfAdverts) {
		this.numberOfAdverts = numberOfAdverts;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
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
