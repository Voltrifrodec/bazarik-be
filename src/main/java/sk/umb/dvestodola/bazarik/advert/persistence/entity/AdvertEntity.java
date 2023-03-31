package sk.umb.dvestodola.bazarik.advert.persistence.entity;

import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;
import sk.umb.dvestodola.bazarik.contact.persistence.entity.ContactEntity;
import sk.umb.dvestodola.bazarik.district.persistence.entity.DistrictEntity;
import sk.umb.dvestodola.bazarik.image.persistence.entity.ImageEntity;
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
	@JoinColumn(name = "id_category", nullable = false)
	private CategoryEntity category;
	
	@OneToOne
	@JoinColumn(name = "id_subcategory", nullable = true)
	private SubcategoryEntity subcategory;

	@OneToOne
	@JoinColumn(name = "id_subsubcategory", nullable = true)
	private SubsubcategoryEntity subsubcategory;

	@OneToOne
	@JoinColumn(name = "id_contact", nullable = false)
	private ContactEntity contact;

	@OneToOne
	@JoinColumn(name = "id_district", nullable = false)
	private DistrictEntity district;

	@OneToOne
	@JoinColumn(name = "id_image")
	private ImageEntity image;

	@Column(name = "keywords")
	private String keywords;

	@Column(name = "date_added")
	private Date dateAdded;

	@Column(name = "priceEur")
	private Integer priceEur;

	@Column(name = "fixedPrice")
	private Boolean fixedPrice;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public CategoryEntity getCategory() {
		return category;
	}

	public void setCategory(CategoryEntity category) {
		this.category = category;
	}

	public SubcategoryEntity getSubcategory() {
		return subcategory;
	}

	public void setSubcategory(SubcategoryEntity subcategory) {
		this.subcategory = subcategory;
	}

	public SubsubcategoryEntity getSubsubcategory() {
		return subsubcategory;
	}

	public void setSubsubcategory(SubsubcategoryEntity subsubcategory) {
		this.subsubcategory = subsubcategory;
	}

	public ContactEntity getContact() {
		return contact;
	}

	public void setContact(ContactEntity contact) {
		this.contact = contact;
	}

	public DistrictEntity getDistrict() {
		return district;
	}

	public void setDistrict(DistrictEntity district) {
		this.district = district;
	}
	
	public ImageEntity getImage() {
		return image;
	}

	public void setImage(ImageEntity image) {
		this.image = image;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public Date getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Date dateAdded) {
		this.dateAdded = dateAdded;
	}

	public Integer getPriceEur() {
		return priceEur;
	}

	public void setPriceEur(Integer priceEur) {
		this.priceEur = priceEur;
	}

	public Boolean getFixedPrice() {
		return fixedPrice;
	}

	public void setFixedPrice(Boolean fixedPrice) {
		this.fixedPrice = fixedPrice;
	}
}
