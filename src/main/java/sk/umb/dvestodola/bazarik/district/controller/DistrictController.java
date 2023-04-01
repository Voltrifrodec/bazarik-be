package sk.umb.dvestodola.bazarik.district.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.district.service.DistrictDetailDto;
import sk.umb.dvestodola.bazarik.district.service.DistrictRequestDto;
import sk.umb.dvestodola.bazarik.district.service.DistrictService;

@RestController
public class DistrictController {

	private final DistrictService districtService;

	public DistrictController(DistrictService districtService) {
		this.districtService = districtService;
	}

	@GetMapping("/api/districts")
	public List<DistrictDetailDto> getAllDistricts() {
		System.out.println("Get all districts was called.");
		return districtService.getAllDistricts();
	}

	@GetMapping("/api/districts/{districtId}")
	public DistrictDetailDto getDistrict(@PathVariable Long districtId) {
		System.out.println("Get district was called, " + districtId);
		return districtService.getDistrictById(districtId);
	}

	@PostMapping("/api/districts")
	public Long createDistrict(@Valid @RequestBody DistrictRequestDto districtRequest) {
		System.out.println("Create district was called.");
		return districtService.createDistrict(districtRequest);
	}

	@PutMapping("/api/districts/{districtId}")
	public void updateDistrict(@PathVariable Long districtId, @Valid @RequestBody DistrictRequestDto districtRequest) {
		System.out.println("Update district was called, " + districtId);
		districtService.updateDistrict(districtId, districtRequest);
	}

	@DeleteMapping("/api/districts/{districtId}")
	public void deleteDistrict(@PathVariable Long districtId) {
		System.out.println("Delete district was called, " + districtId);
		districtService.deleteDistrict(districtId);
	}
}
