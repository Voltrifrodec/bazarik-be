package sk.umb.dvestodola.bazarik.category.service;

public class CategoryDetailDto {
	private Long id;
	private String name;
	private String emoji;
	private Long numberOfAdverts;
	
	public Long getNumberOfAdverts() {
		return numberOfAdverts;
	}
	public void setNumberOfAdverts(Long numberOfAdverts) {
		this.numberOfAdverts = numberOfAdverts;
	}
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
	public String getEmoji() {
		return this.emoji;
	}
	public void setEmoji(String emoji) {
		this.emoji = emoji;
	}
}
