package sk.umb.dvestodola.bazarik.region.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.region.service.RegionDetailDto;
import sk.umb.dvestodola.bazarik.region.service.RegionRequestDto;
import sk.umb.dvestodola.bazarik.region.service.RegionService;

@RestController
public class RegionController {

	private final RegionService regionService;

	public RegionController(RegionService regionService) {
		this.regionService = regionService;
	}

	@GetMapping("/api/regions")
	public List<RegionDetailDto> getRegions() {
		System.out.println("Get all regions was called.");
		return regionService.getAllSubcategories();
	}

	@GetMapping("/api/regions/{regionId}")
	public RegionDetailDto getCategory(@PathVariable Long regionId) {
		System.out.println("Get region was called, " + regionId);
		return regionService.getCategoryById(regionId);
	}

	@PostMapping("/api/regions")
	public Long createCategory(@Valid @RequestBody RegionRequestDto region) {
		System.out.println("Create region was called.");
		return regionService.createRegion(region);
	}

	@PutMapping("/api/regions/{regionId}")
	public void updateRegion(@PathVariable Long regionId, @Valid @RequestBody RegionRequestDto region) {
		System.out.println("Update region was called, " + regionId);
		regionService.updateRegion(regionId, region);
	}

	@DeleteMapping("/api/regions/{regionId}")
	public void deleteRegion(@PathVariable Long regionId) {
		System.out.println("Delete region was called, " + regionId);
		regionService.deleteRegion(regionId);
	}
}
