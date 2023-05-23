package sk.umb.dvestodola.bazarik.page.service;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public class PageRequestDto {
	private static final int DEFAULT_PAGE_NUMBER = 0;
	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final String DEFAULT_PAGE_QUERY = "";

	@Min(0)
	private int page = DEFAULT_PAGE_NUMBER;

	@Min(0)
	@Max(25)
	private int size;
	
	@NotBlank()
	private String query;

	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}
}
