package sk.umb.dvestodola.bazarik.advert.service;

import java.sql.Date;

import jakarta.validation.constraints.NotBlank;

public class AdvertRequestDto {
	@NotBlank(message = "Advert name must not be blank.")
	private String name;

	private String description;

	@NotBlank(message = "Category id name must not be blank.")
	private Long categoryId;
	
	@NotBlank(message = "Subcategory id name must not be blank.")
	private Long subcategoryId;

	@NotBlank(message = "Subsubcategory id name must not be blank.")
	private Long subsubcategoryId;

	@NotBlank(message = "Contact id must not be blank.")
	private Long contactId;

	@NotBlank(message = "District id must not be blank.")
	private Long districtId;

	private String keywords;

	@NotBlank(message = "Date added must not be blank.")
	private Date date_added;

	@NotBlank(message = "Price must not be blank.")
	private Integer priceEur;

	@NotBlank(message = "Fixed price must not be blank.")
	private Boolean fixedPrice;

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

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getSubcategoryId() {
		return subcategoryId;
	}

	public void setSubcategoryId(Long subcategoryId) {
		this.subcategoryId = subcategoryId;
	}

	public Long getSubsubcategoryId() {
		return subsubcategoryId;
	}

	public void setSubsubcategoryId(Long subsubcategoryId) {
		this.subsubcategoryId = subsubcategoryId;
	}

	public Long getContactId() {
		return contactId;
	}

	public void setContactId(Long contactId) {
		this.contactId = contactId;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
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
