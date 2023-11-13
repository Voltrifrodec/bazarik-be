package sk.umb.dvestodola.bazarik.advert.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Valid
public class AdvertRequestDto {
	@NotBlank(message = "Name must not be blank.")
	private String name;

	private String description;

	@Valid
	@NotNull(message = "Category id name must not be null.")
	private Long categoryId;
	
	@Valid
	// @NotNull(message = "Subcategory id name must not be null.")
	private Long subcategoryId;
	
	// @Valid
	// @NotNull(message = "Subsubcategory id name must not be null.")
	private Long subsubcategoryId;

	// @NotBlank(message = "Email must not be blank.")
	@Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "Email must be valid.")
	private String contactEmail;

	@Valid
	@NotNull(message = "District id name must not be null.")
	private Long districtId;

	private String keywords;

	@Min(value = 0, message = "Minimal priceEur must be at least 0.")
	private Integer priceEur;

	@NotNull(message = "Fixed price must not be null.")
	private Boolean fixedPrice;

	// @NotNull(message = "Image id must not be null.")
	private Long imageId;
	

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
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

	public String getContactEmail() {
		return contactEmail;
	}

	public void setContactEmail(String contactEmail) {
		this.contactEmail = contactEmail;
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
