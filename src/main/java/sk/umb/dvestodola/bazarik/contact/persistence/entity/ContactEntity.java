package sk.umb.dvestodola.bazarik.contact.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity(name = "contact")
public class ContactEntity {
	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "phone_number", nullable = false)
	private String phoneNumber;

	@Column(name = "email", nullable = false)
	private String email;


	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
