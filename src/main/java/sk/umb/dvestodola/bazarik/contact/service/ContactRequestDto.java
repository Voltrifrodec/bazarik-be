package sk.umb.dvestodola.bazarik.contact.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ContactRequestDto {
	@NotBlank(message = "Phone number must not be blank.")
	@Pattern(regexp = "0[0-9]{3}[0-9]{3}[0-9]{3}$")
	private String phoneNumber;

	@NotBlank(message = "Phone number must not be blank.")
	@Pattern(regexp = "^\\S+@\\S+\\.\\S+$")
	private String email;
	

	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
