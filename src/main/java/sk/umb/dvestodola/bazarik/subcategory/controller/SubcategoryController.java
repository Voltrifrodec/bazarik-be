package sk.umb.dvestodola.bazarik.subcategory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.subcategory.service.SubcategoryDetailDto;
import sk.umb.dvestodola.bazarik.subcategory.service.SubcategoryRequestDto;
import sk.umb.dvestodola.bazarik.subcategory.service.SubcategoryService;
import sk.umb.dvestodola.bazarik.subsubcategory.service.SubsubcategoryDetailDto;
import sk.umb.dvestodola.bazarik.subsubcategory.service.SubsubcategoryService;

@RestController
public class SubcategoryController {

	private final SubcategoryService subcategoryService;
	private final SubsubcategoryService subsubcategoryService;

	public SubcategoryController(SubcategoryService subcategoryService, SubsubcategoryService subsubcategoryService) {
		this.subcategoryService = subcategoryService;
		this.subsubcategoryService = subsubcategoryService;
	}

	@GetMapping("/api/subcategories")
	public List<SubcategoryDetailDto> getSubcategories() {
		System.out.println("Get all subcategories was called.");
		return subcategoryService.getAllSubcategories();
	}

	@GetMapping("/api/subcategories/{subcategoryId}")
	public SubcategoryDetailDto getSubcategoryById(@PathVariable Long subcategoryId) {
		System.out.println("Get subcategory was called, " + subcategoryId);
		return subcategoryService.getSubcategoryById(subcategoryId);
	}

	@GetMapping("/api/subcategories/{subcategoryId}/subsubcategories")
	public List<SubsubcategoryDetailDto> getSubsubcategoriesBySubcategoryId(@PathVariable Long subcategoryId) {
		System.out.println("Get subcategories by categoryId was called, " + subcategoryId);
		return subsubcategoryService.getSubsubcategoriesBySubcategoryId(subcategoryId);
	}

	@PostMapping("/api/subcategories")
	public Long createSubcategory(@Valid @RequestBody SubcategoryRequestDto subcategory) {
		System.out.println("Create subcategory was called.");
		return subcategoryService.createSubcategory(subcategory);
	}

	@PutMapping("/api/subcategories/{subcategoryId}")
	public void updateSubcategory(@PathVariable Long subcategoryId, @Valid @RequestBody SubcategoryRequestDto subcategory) {
		System.out.println("Update subcategory was called, " + subcategoryId);
		subcategoryService.updateSubcategory(subcategoryId, subcategory);
	}

	@DeleteMapping("/api/subcategories/{subcategoryId}")
	public void deleteSubcategory(@PathVariable Long subcategoryId) {
		System.out.println("Delete subcategory was called, " + subcategoryId);
		subcategoryService.deleteSubcategory(subcategoryId);
	}
}
