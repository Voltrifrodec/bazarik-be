package sk.umb.dvestodola.bazarik.advert.persistence.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;
import sk.umb.dvestodola.bazarik.contact.persistence.entity.ContactEntity;
import sk.umb.dvestodola.bazarik.district.persistence.entity.DistrictEntity;
import sk.umb.dvestodola.bazarik.subcategory.persistence.entity.SubcategoryEntity;
import sk.umb.dvestodola.bazarik.subsubcategory.persistence.entity.SubsubcategoryEntity;

@Entity(name = "advert")
public class AdvertEntity {
	@Id
	@GeneratedValue
	@Column(name = "id_advert", unique = true)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@OneToOne
	private CategoryEntity category;

	@OneToOne
	private SubcategoryEntity subcategory;

	@OneToOne
	private SubsubcategoryEntity subsubcategory;

	@OneToOne
	private ContactEntity contact;

	@OneToOne
	private DistrictEntity district;

	@Column
	private String keywords;

	@Column
	private Date date_added;

	@Column
	private Integer priceEur;

	@Column
	private Boolean fixedPrice;

	
	
}
