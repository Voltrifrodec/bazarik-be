package sk.umb.dvestodola.bazarik.category.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;
import sk.umb.dvestodola.bazarik.category.persistence.repository.CategoryRepository;
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;

@Service
public class CategoryService {
	
	private final CategoryRepository categoryRepository;

	CategoryService(CategoryRepository categoryRepository) {
		this.categoryRepository = categoryRepository;
	}

	public List<CategoryDetailDto> getAllCategories() {
        return mapToCategoryDtoList(categoryRepository.findAll());
    }

	public CategoryDetailDto getCategoryById(Long categoryId) {
		return mapToCategoryDto(getCategoryEntityById(categoryId));
	}

	@Transactional
	public Long createCategory(@Valid CategoryRequestDto categoryRequestDto) {
		CategoryEntity categoryEntity = mapToCategoryEntity(categoryRequestDto);

		return categoryRepository.save(categoryEntity).getId();
	}

	@Transactional
	public void updateCategory(Long categoryId, @Valid CategoryRequestDto categoryRequestDto) {
		CategoryEntity categoryEntity = getCategoryEntityById(categoryId);
        
		if (! Strings.isEmpty(categoryRequestDto.getName())) {
			categoryEntity.setName(categoryRequestDto.getName());
		}
        
        categoryRepository.save(categoryEntity);
	}

	@Transactional
	public void deleteCategory(Long categoryId) {
		categoryRepository.deleteById(categoryId);
	}


	private CategoryEntity getCategoryEntityById(Long categoryId) {
		Optional<CategoryEntity> category = categoryRepository.findById(categoryId);

        if (category.isEmpty()) {
            throw new BazarikApplicationException("Category could not be found, id: " + categoryId);
        }

		return category.get();
	}

	private CategoryEntity mapToCategoryEntity(CategoryRequestDto categoryRequestDto) {
		CategoryEntity categoryEntity = new CategoryEntity();

		categoryEntity.setName(categoryRequestDto.getName());

		return categoryEntity;
	}

	private List<CategoryDetailDto> mapToCategoryDtoList(Iterable<CategoryEntity> categoryEntities) {
		List<CategoryDetailDto> categoryEntityList = new ArrayList<>();

		categoryEntities.forEach(category -> {
			CategoryDetailDto categoryDetailDto = mapToCategoryDto(category);
			categoryEntityList.add(categoryDetailDto);
		});

		return categoryEntityList;
	}

	private CategoryDetailDto mapToCategoryDto(CategoryEntity categoryEntity) {
		CategoryDetailDto category = new CategoryDetailDto();

		category.setId(categoryEntity.getId());
		category.setName(categoryEntity.getName());

		return category;
	}
}
