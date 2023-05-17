package sk.umb.dvestodola.bazarik.category.service;

import jakarta.validation.constraints.NotBlank;

public class CategoryRequestDto {
	@NotBlank(message = "Category name must not be blank.")
	private String name;
	
	private String emoji;

	public String getEmoji() {
		return this.emoji;
	}

	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
