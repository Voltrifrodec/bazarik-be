package sk.umb.dvestodola.bazarik.security.service;

import jakarta.validation.constraints.NotBlank;

public class SecurityUpdateDto {
	@NotBlank(message = "Advert id must not me blank.")
	private String advertId;

	@NotBlank
	private String email;


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAdvertId() {
		return advertId;
	}

	public void setAdvertId(String advertId) {
		this.advertId = advertId;
	}
}
