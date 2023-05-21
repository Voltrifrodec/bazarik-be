package sk.umb.dvestodola.bazarik.advert.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.advert.service.AdvertService;
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;
import sk.umb.dvestodola.bazarik.page.service.PageRequestDto;
import sk.umb.dvestodola.bazarik.advert.service.AdvertDetailDto;
import sk.umb.dvestodola.bazarik.advert.service.AdvertRequestDto;

@RestController
public class AdvertController {

	private final AdvertService advertService;

	public AdvertController(
		AdvertService advertService
	) {
		this.advertService = advertService;
	}

	// https://www.baeldung.com/spring-data-jpa-pagination-sorting
	@GetMapping("/api/adverts")
	public Page<AdvertDetailDto> findPaginated(
		@RequestParam("page") int page,
		@RequestParam("size") int size,
		@RequestParam("query") Optional<String> query,
		UriComponentsBuilder uriComponentsBuilder,
		HttpServletResponse response
	) {
		System.out.println("Get paginated adverts was called, page: " + page + ", size: " + size);

		if (size > 25) {
			throw new BazarikApplicationException("Paginable size must not exceed 25!");
		}
		
		Pageable pageable = PageRequest.of(page, size);
		return advertService.getPaginatedAdverts(pageable);
	}

	@GetMapping("/api/adverts/recent/{count}")
	public List<AdvertDetailDto> getRecentAdverts(@PathVariable(required = false) Long count) {
		if (count <= 0) {
			throw new BazarikApplicationException("Recent advert count must be above 0!");
		}
		if (count > 5) {
			throw new BazarikApplicationException("Recent advert count must not exceed 5!");
		}
		System.out.println("Get all recent adverts was called, " + count);
		return advertService.getRecentAdverts((Objects.isNull(count)) ? 4L : count);
	}

	@GetMapping("/api/search/{query}")
	public List<AdvertDetailDto> getAllAdvertsByQuery(@PathVariable String query) {
		System.out.println("Get all adverts by query was called, " + query);
		return advertService.getAllAdvertsByQuery(query);
	}

	@GetMapping("/api/categories/{categoryId}/adverts/count")
	public Long getNumberOfAdvertsInCategoryByCategoryId(@PathVariable Long categoryId) {
		System.out.println("Get number of adverts in category by categoryId was called, " + categoryId);
		return advertService.getNumberOfAdvertsInCategoryByCategoryId(categoryId);
	}

	@GetMapping("/api/subcategories/{subcategoryId}/adverts/count")
	public Long getNumberOfAdvertsInSubcategoryBySubcategoryId(@PathVariable Long subcategoryId) {
		System.out.println("Get number of adverts in category by categoryId was called, " + subcategoryId);
		return advertService.getNumberOfAdvertsInCategoryByCategoryId(subcategoryId);
	}

	@GetMapping("/api/categories/{categoryId}/adverts")
	public Page<AdvertDetailDto> findPaginatedByCategoryId(
		@PathVariable Long categoryId,
		PageRequestDto pageRequest,
		UriComponentsBuilder uriComponentsBuilder,
		HttpServletResponse response
	) {
		System.out.println("Get paginated adverts by category id was called, categoryId: " + categoryId + ", page: " + pageRequest.getPage() + ", size: " + pageRequest.getSize());

		Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize());
		return advertService.getPaginatedAdvertsByCategoryId(categoryId, pageable);
	}

	@GetMapping("/api/subcategories/{subcategoryId}/adverts")
	public Page<AdvertDetailDto> findPaginatedBySubcategoryId(
		@PathVariable Long subcategoryId,
		PageRequestDto pageRequest,
		UriComponentsBuilder uriComponentsBuilder,
		HttpServletResponse response
	) {
		System.out.println("Get paginated adverts by subcategory id was called, subcategoryId: " + subcategoryId + ", page: " + pageRequest.getPage() + ", size: " + pageRequest.getSize());

		Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize());
		return advertService.getPaginatedAdvertsBySubcategoryId(subcategoryId, pageable);
	}

	@GetMapping("/api/subsubcategories/{subsubcategoryId}/adverts")
	public Page<AdvertDetailDto> findPaginatedBySubsubcategoryId(
		@PathVariable Long subsubcategoryId,
		PageRequestDto pageRequest,
		UriComponentsBuilder uriComponentsBuilder,
		HttpServletResponse response
	) {
		System.out.println("Get paginated adverts by subsubcategory id was called, subsubcategoryId: " + subsubcategoryId + ", page: " + pageRequest.getPage() + ", size: " + pageRequest.getSize());

		Pageable pageable = PageRequest.of(pageRequest.getPage(), pageRequest.getSize());
		return advertService.getPaginatedAdvertsBySubsubcategoryId(subsubcategoryId, pageable);
	}

	@GetMapping("/api/adverts/{advertId}")
	public AdvertDetailDto getAdvertById(@PathVariable UUID advertId) {
		System.out.println("Get advert was called, " + advertId);
		return advertService.getAdvertById(advertId);
	}

	@PostMapping("/api/adverts")
	public UUID createAdvert(@Valid @RequestBody AdvertRequestDto advert) {
		System.out.println("Create advert was called.");
		return advertService.createAdvert(advert);
	}

	@PutMapping("/api/adverts/{advertId}")
	public void updateAdvert(@PathVariable UUID advertId, @Valid @RequestBody AdvertRequestDto advert) {
		System.out.println("Update advert was called, " + advertId);
		advertService.updateAdvert(advertId, advert);
	}

	@DeleteMapping("/api/adverts/{advertId}")
	public void deleteAdvert(@PathVariable UUID advertId) {
		System.out.println("Delete advert was called, " + advertId);
		advertService.deleteAdvert(advertId);
	}
	
}
