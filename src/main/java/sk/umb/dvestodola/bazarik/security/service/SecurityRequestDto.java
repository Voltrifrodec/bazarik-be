package sk.umb.dvestodola.bazarik.security.service;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SecurityRequestDto {
	@NotBlank(message = "Code must not be blank.")
	@NotNull(message = "Code must not be null.")
	private String code;

	@NotBlank(message = "Hash must not be blank.")
	@NotNull(message = "Hash must not be null.")
	private String hash;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}
}
