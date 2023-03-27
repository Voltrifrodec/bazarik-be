package sk.umb.dvestodola.bazarik.advert.service;

import java.sql.Date;

import sk.umb.dvestodola.bazarik.category.service.CategoryDetailDto;
import sk.umb.dvestodola.bazarik.contact.service.ContactDetailDto;
import sk.umb.dvestodola.bazarik.district.service.DistrictDetailDto;
import sk.umb.dvestodola.bazarik.subcategory.service.SubcategoryDetailDto;
import sk.umb.dvestodola.bazarik.subsubcategory.service.SubsubcategoryDetailDto;

public class AdvertDetailDto {
	private Long id;
	private String name;
	private String description;
	private CategoryDetailDto category;
	private SubcategoryDetailDto subcategory;
	private SubsubcategoryDetailDto subsubcategory;
	private ContactDetailDto contact;
	private DistrictDetailDto district;
	private String keywords;
	private Date date_added;
	private Integer priceEur;
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
	public CategoryDetailDto getCategory() {
		return category;
	}
	public void setCategory(CategoryDetailDto category) {
		this.category = category;
	}
	public SubcategoryDetailDto getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(SubcategoryDetailDto subcategory) {
		this.subcategory = subcategory;
	}
	public SubsubcategoryDetailDto getSubsubcategory() {
		return subsubcategory;
	}
	public void setSubsubcategory(SubsubcategoryDetailDto subsubcategory) {
		this.subsubcategory = subsubcategory;
	}
	public ContactDetailDto getContact() {
		return contact;
	}
	public void setContact(ContactDetailDto contact) {
		this.contact = contact;
	}
	public DistrictDetailDto getDistrict() {
		return district;
	}
	public void setDistrict(DistrictDetailDto district) {
		this.district = district;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public Date getDate_added() {
		return date_added;
	}
	public void setDate_added(Date date_added) {
		this.date_added = date_added;
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
