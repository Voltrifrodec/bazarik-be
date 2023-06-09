package sk.umb.dvestodola.bazarik.region.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.district.service.DistrictDetailDto;
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
	public List<RegionDetailDto> getAllRegions() {
		System.out.println("Get all regions was called.");
		return regionService.getAllRegions();
	}

	@GetMapping("/api/regions/{regionId}/districts")
	public List<DistrictDetailDto> getAllDistrictsByRegionId(@PathVariable Long regionId) {
		System.out.println("Get all districts by region id was called, " + regionId);
		return regionService.getAllDistrictsByRegionById(regionId);
	}

	@GetMapping("/api/regions/{regionId}")
	public RegionDetailDto getRegionById(@PathVariable Long regionId) {
		System.out.println("Get region by id was called, " + regionId);
		return regionService.getRegionById(regionId);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/api/regions")
	public Long createRegion(@Valid @RequestBody RegionRequestDto regionRequest) {
		System.out.println("Create region was called.");
		return regionService.createRegion(regionRequest);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping("/api/regions/{regionId}")
	public void updateRegion(@PathVariable Long regionId, @Valid @RequestBody RegionRequestDto regionRequest) {
		System.out.println("Update region was called, " + regionId);
		regionService.updateRegion(regionId, regionRequest);
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@DeleteMapping("/api/regions/{regionId}")
	public void deleteRegion(@PathVariable Long regionId) {
		System.out.println("Delete region was called, " + regionId);
		regionService.deleteRegion(regionId);
	}
}
