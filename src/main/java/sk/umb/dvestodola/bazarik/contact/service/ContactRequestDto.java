package sk.umb.dvestodola.bazarik.contact.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class ContactRequestDto {
	/* @NotBlank(message = "Phone number must not be blank.")
	@Pattern(regexp = "0[0-9]{3} [0-9]{3} [0-9]{3}$", message = "Phone number must follow pattern: 0900 123 456")
	private String phoneNumber; */

	@NotBlank(message = "Email must not be blank.")
	@Pattern(regexp = "^\\S+@\\S+\\.\\S+$", message = "Email must be valid.")
	private String email;
	

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
