package sk.umb.dvestodola.bazarik.subcategory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.management.RuntimeErrorException;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import javassist.tools.rmi.ObjectNotFoundException;
import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;
import sk.umb.dvestodola.bazarik.category.persistence.repository.CategoryRepository;
import sk.umb.dvestodola.bazarik.category.service.CategoryDetailDto;
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
        return mapToSubcategoryDtoList(subcategoryRepository.findAll());
    }

	public SubcategoryDetailDto getCategoryById(Long subcategoryId) {
		return mapToSubcategoryDetailDto(getSubcategoryEntityById(subcategoryId));
	}

	@Transactional
	public Long createSubcategory(SubcategoryRequestDto subcategoryRequestDto) {
		SubcategoryEntity subcategoryEntity = mapToSubcategoryEntity(subcategoryRequestDto);

		return subcategoryRepository.save(subcategoryEntity).getId();
	}

	@Transactional
	public void updateSubcategory(Long subcategoryId, SubcategoryRequestDto subcategoryRequestDto) {
		SubcategoryEntity subcategoryEntity = getSubcategoryEntityById(subcategoryId);
        
		if (! Strings.isEmpty(subcategoryRequestDto.getName())) {
			subcategoryEntity.setName(subcategoryRequestDto.getName());
		}

        subcategoryRepository.save(subcategoryEntity);
	}

	@Transactional
	public void deleteSubcategory(Long subcategoryId) {
		subcategoryRepository.deleteById(subcategoryId);
	}


	public List<SubcategoryDetailDto> getSubcategoriesByCategoryId(Long categoryId) {
		return getSubcategoryDetailListByCategoryId(categoryId);
	}

	private List<SubcategoryDetailDto> getSubcategoryDetailListByCategoryId(Long categoryId) {
		Iterable<SubcategoryEntity> subcategoryEntities = subcategoryRepository.getAllByCategoryId(categoryId);
		List<SubcategoryDetailDto> subcategoryEntityList = new ArrayList<>();

		subcategoryEntities.forEach(subcategory -> {
			subcategoryEntityList.add(mapToSubcategoryDetailDto(subcategory));
		});

		return subcategoryEntityList;
	}

	private SubcategoryEntity getSubcategoryEntityById(Long subcategoryId) {
		Optional<SubcategoryEntity> subcategory = subcategoryRepository.findById(subcategoryId);

        if (subcategory.isEmpty()) {
            throw new IllegalArgumentException("Subcategory not found. ID: " + subcategoryId);
        }

		return subcategory.get();
	}

	private SubcategoryEntity mapToSubcategoryEntity(SubcategoryRequestDto subcategoryRequestDto) {
		SubcategoryEntity subcategoryEntity = new SubcategoryEntity();
		
		subcategoryEntity.setName(subcategoryRequestDto.getName());

		Optional<CategoryEntity> categoryEntity = categoryRepository.findById(subcategoryRequestDto.getCategoryId());
		if (categoryEntity.isPresent()) {
			subcategoryEntity.setCategory(categoryEntity.get());
		}

		return subcategoryEntity;
	}

	private List<SubcategoryDetailDto> mapToSubcategoryDtoList(Iterable<SubcategoryEntity> categoryEntities) {
		List<SubcategoryDetailDto> subcategoryEntityList = new ArrayList<>();

		categoryEntities.forEach(subcategory -> {
			SubcategoryDetailDto subcategoryDetailDto = mapToSubcategoryDetailDto(subcategory);
			subcategoryEntityList.add(subcategoryDetailDto);
		});

		return subcategoryEntityList;
	}

	private SubcategoryDetailDto mapToSubcategoryDetailDto(SubcategoryEntity subcategoryEntity) {
		SubcategoryDetailDto subcategory = new SubcategoryDetailDto();

		subcategory.setId(subcategoryEntity.getId());
		subcategory.setName(subcategoryEntity.getName());
		subcategory.setCategory(mapToCategoryDetailDto(subcategoryEntity.getCategory()));

		return subcategory;
	}

	private CategoryDetailDto mapToCategoryDetailDto(CategoryEntity category) {
		CategoryDetailDto categoryDetailDto = new CategoryDetailDto();

		if (Objects.isNull(category)) {
			throw new RuntimeException("ERROR: Category is missing!");
		}

		categoryDetailDto.setId(category.getId());
		categoryDetailDto.setName(category.getName());

		return categoryDetailDto;
	}
}
