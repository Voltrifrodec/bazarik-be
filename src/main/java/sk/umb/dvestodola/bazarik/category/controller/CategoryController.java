package sk.umb.dvestodola.bazarik.category.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import sk.umb.dvestodola.bazarik.category.service.CategoryDetailDto;
import sk.umb.dvestodola.bazarik.category.service.CategoryRequestDto;
import sk.umb.dvestodola.bazarik.category.service.CategoryService;

@RestController
public class CategoryController {

	private final CategoryService categoryService;

	public CategoryController(CategoryService categoryService) {
		this.categoryService = categoryService;
	}

	@GetMapping("/api/categories")
	public List<CategoryDetailDto> getCategories() {
		System.out.println("Get all categories was called: ");
		return categoryService.getAllCategories();
	}

	@GetMapping("/api/categories/{categoryId}")
	public CategoryDetailDto getCategory(@PathVariable Long categoryId) {
		System.out.println("Get category was called, " + categoryId);
		return categoryService.getCategoryById(categoryId);
	}

	@PostMapping("/api/categories")
	public Long createCategory(@RequestBody CategoryRequestDto category) {
		System.out.println("Create category was called.");
		return categoryService.createCategory(category);
	}

	@PutMapping("/api/categories/{categoryId}")
	public void updateCategory(@PathVariable Long categoryId, @RequestBody CategoryRequestDto category) {
		System.out.println("Update category was called, " + categoryId);
		categoryService.updateCategory(categoryId, category);
	}

	@DeleteMapping("/api/categories/{categoryId}")
	public void deleteCategory(@PathVariable Long categoryId) {
		System.out.println("Delete category was called, " + categoryId);
		categoryService.deleteCategory(categoryId);
	}
}
