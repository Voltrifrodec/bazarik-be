package sk.umb.dvestodola.bazarik.subcategory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;
import sk.umb.dvestodola.bazarik.category.persistence.repository.CategoryRepository;
import sk.umb.dvestodola.bazarik.category.service.CategoryDetailDto;
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;
import sk.umb.dvestodola.bazarik.subcategory.persistence.entity.SubcategoryEntity;
import sk.umb.dvestodola.bazarik.subcategory.persistence.repository.SubcategoryRepository;

@Service
public class SubcategoryService {
	
	private final SubcategoryRepository subcategoryRepository;
	private final CategoryRepository categoryRepository;

	SubcategoryService(SubcategoryRepository subcategoryRepository, CategoryRepository categoryRepository) {
		this.subcategoryRepository = subcategoryRepository;
		this.categoryRepository = categoryRepository;
	}

	public List<SubcategoryDetailDto> getAllSubcategories() {
        return mapToSubcategoryDetailList(subcategoryRepository.findAll());
    }

	public SubcategoryDetailDto getSubcategoryById(Long subcategoryId) {
		return mapToSubcategoryDetail(getSubcategoryEntityById(subcategoryId));
	}

	public List<SubcategoryDetailDto> getSubcategoriesByCategoryId(Long categoryId) {
		return mapToSubcategoryDetailList(subcategoryRepository.getAllByCategoryId(categoryId));
		// return getSubcategoryDetailListByCategoryId(categoryId);
	}

	@Transactional
	public Long createSubcategory(SubcategoryRequestDto subcategoryRequest) {
		SubcategoryEntity subcategoryEntity = mapToSubcategoryEntity(subcategoryRequest);

		if (Objects.isNull(subcategoryEntity)) {
			throw new BazarikApplicationException("Subcategory must have valid category.");
		}

		if (Objects.isNull(subcategoryEntity.getCategory())) {
			throw new BazarikApplicationException("Subcategory must have valid category.");
		}

		return subcategoryRepository.save(subcategoryEntity).getId();
	}

	@Transactional
	public void updateSubcategory(Long subcategoryId, SubcategoryRequestDto subcategoryRequest) {
		SubcategoryEntity subcategoryEntity = getSubcategoryEntityById(subcategoryId);
        
		if (! Strings.isEmpty(subcategoryRequest.getName())) {
			subcategoryEntity.setName(subcategoryRequest.getName());
		}

		if (! Objects.isNull(subcategoryEntity.getCategory())) {
			Optional<CategoryEntity> categoryEntity = categoryRepository.findById(subcategoryRequest.getCategoryId());

			if (categoryEntity.isPresent()) {
				subcategoryEntity.setCategory(categoryEntity.get());
			} else {
				throw new BazarikApplicationException("Subcategory must have valid category.");
			}
		}

        subcategoryRepository.save(subcategoryEntity);
	}

	@Transactional
	public void deleteSubcategory(Long subcategoryId) {
		subcategoryRepository.deleteById(subcategoryId);
	}


	private SubcategoryEntity getSubcategoryEntityById(Long subcategoryId) {
		Optional<SubcategoryEntity> subcategoryEntity = subcategoryRepository.findById(subcategoryId);

        if (subcategoryEntity.isEmpty()) {
			throw new BazarikApplicationException("Subcategory must have valid category id.");
        }

		return subcategoryEntity.get();
	}

	private SubcategoryEntity mapToSubcategoryEntity(SubcategoryRequestDto subcategoryRequest) {
		SubcategoryEntity subcategoryEntity = new SubcategoryEntity();
		
		subcategoryEntity.setName(subcategoryRequest.getName());

		Optional<CategoryEntity> categoryEntity = categoryRepository.findById(subcategoryRequest.getCategoryId());

		if (categoryEntity.isPresent()) {
			subcategoryEntity.setCategory(categoryEntity.get());
		} else {
			throw new BazarikApplicationException("Subcategory must have valid category id.");
		}

		return subcategoryEntity;
	}

	private List<SubcategoryDetailDto> mapToSubcategoryDetailList(Iterable<SubcategoryEntity> categoryEntities) {
		List<SubcategoryDetailDto> subcategoryEntityList = new ArrayList<>();

		categoryEntities.forEach(subcategory -> {
			SubcategoryDetailDto subcategoryDetail = mapToSubcategoryDetail(subcategory);
			subcategoryEntityList.add(subcategoryDetail);
		});

		return subcategoryEntityList;
	}

	private SubcategoryDetailDto mapToSubcategoryDetail(SubcategoryEntity subcategoryEntity) {
		SubcategoryDetailDto subcategoryDetail = new SubcategoryDetailDto();

		subcategoryDetail.setId(subcategoryEntity.getId());
		subcategoryDetail.setName(subcategoryEntity.getName());
		subcategoryDetail.setCategory(mapToCategoryDetail(subcategoryEntity.getCategory()));
		subcategoryDetail.setNumberOfAdverts(subcategoryEntity.getNumberOfAdverts());

		return subcategoryDetail;
	}

	private CategoryDetailDto mapToCategoryDetail(CategoryEntity categoryEntity) {
		CategoryDetailDto categoryDetail = new CategoryDetailDto();

		if (Objects.isNull(categoryEntity)) {
			throw new BazarikApplicationException("Category must have a valid id.");
		}

		categoryDetail.setId(categoryEntity.getId());
		categoryDetail.setName(categoryEntity.getName());
		categoryDetail.setEmoji(categoryEntity.getEmoji());
		categoryDetail.setNumberOfAdverts(categoryEntity.getNumberOfAdverts());

		return categoryDetail;
	}
}
