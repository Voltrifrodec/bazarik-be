package sk.umb.dvestodola.bazarik.subsubcategory.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.subsubcategory.service.SubsubcategoryRequestDto;
import sk.umb.dvestodola.bazarik.subsubcategory.service.SubsubcategoryDetailDto;
import sk.umb.dvestodola.bazarik.subsubcategory.service.SubsubcategoryService;

@RestController
public class SubsubcategoryController {

	private final SubsubcategoryService subsubcategoryService;

	public SubsubcategoryController(SubsubcategoryService subsubcategoryService) {
		this.subsubcategoryService = subsubcategoryService;
	}

	@GetMapping("/api/subsubcategories")
	public List<SubsubcategoryDetailDto> getSubsubcategories() {
		System.out.println("Get all subsubcategories was called.");
		return subsubcategoryService.getAllSubsubcategories();
	}

	@GetMapping("/api/subsubcategories/{subsubcategoryId}")
	public SubsubcategoryDetailDto getSubsubcategory(@PathVariable Long subsubcategoryId) {
		System.out.println("Get subsubcategory was called, " + subsubcategoryId);
		return subsubcategoryService.getSubsubcategoryById(subsubcategoryId);
	}

	
	@PostMapping("/api/subsubcategories")
	public Long createSubsubcategory(@Valid @RequestBody SubsubcategoryRequestDto subsubcategory) {
		System.out.println("Create subsubcategory was called.");
		return subsubcategoryService.createSubsubcategory(subsubcategory);
	}

	@PutMapping("/api/subsubcategories/{subsubcategoryId}")
	public void updateSubsubcategory(@PathVariable Long subsubcategoryId, @Valid @RequestBody SubsubcategoryRequestDto subsubcategory) {
		System.out.println("Update subsubcategory was called, " + subsubcategoryId);
		subsubcategoryService.updateSubsubcategory(subsubcategoryId, subsubcategory);
	}

	@DeleteMapping("/api/subsubcategories/{subsubcategoryId}")
	public void deleteSubsubcategory(@PathVariable Long subsubcategoryId) {
		System.out.println("Delete subsubcategory was called, " + subsubcategoryId);
		subsubcategoryService.deleteSubsubcategory(subsubcategoryId);
	}
}
