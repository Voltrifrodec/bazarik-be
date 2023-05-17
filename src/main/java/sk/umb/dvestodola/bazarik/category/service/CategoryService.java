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
        return mapToCategoryDetailList(categoryRepository.findAll());
    }

	public CategoryDetailDto getCategoryById(Long categoryId) {
		return mapToCategoryDetail(getCategoryEntityById(categoryId));
	}

	@Transactional
	public Long createCategory(@Valid CategoryRequestDto categoryRequest) {
		CategoryEntity categoryEntity = mapToCategoryEntity(categoryRequest);

		return categoryRepository.save(categoryEntity).getId();
	}

	@Transactional
	public void updateCategory(Long categoryId, @Valid CategoryRequestDto categoryRequest) {
		CategoryEntity categoryEntity = getCategoryEntityById(categoryId);
        
		if (! Strings.isEmpty(categoryRequest.getName())) {
			categoryEntity.setName(categoryRequest.getName());
		}
        
        categoryRepository.save(categoryEntity);
	}

	@Transactional
	public void deleteCategory(Long categoryId) {
		categoryRepository.deleteById(categoryId);
	}


	private CategoryEntity getCategoryEntityById(Long categoryId) {
		Optional<CategoryEntity> categoryEntity = categoryRepository.findById(categoryId);

        if (categoryEntity.isEmpty()) {
            throw new BazarikApplicationException("Category muse have a valid id");
        }

		return categoryEntity.get();
	}

	private CategoryEntity mapToCategoryEntity(CategoryRequestDto categoryRequest) {
		CategoryEntity categoryEntity = new CategoryEntity();

		categoryEntity.setName(categoryRequest.getName());

		return categoryEntity;
	}

	private List<CategoryDetailDto> mapToCategoryDetailList(Iterable<CategoryEntity> categoryEntities) {
		List<CategoryDetailDto> categoryEntityList = new ArrayList<>();

		categoryEntities.forEach(categoryEntity -> {
			CategoryDetailDto categoryDetailDto = mapToCategoryDetail(categoryEntity);
			categoryEntityList.add(categoryDetailDto);
		});

		return categoryEntityList;
	}

	private CategoryDetailDto mapToCategoryDetail(CategoryEntity categoryEntity) {
		CategoryDetailDto categoryDetail = new CategoryDetailDto();

		categoryDetail.setId(categoryEntity.getId());
		categoryDetail.setName(categoryEntity.getName());
		categoryDetail.setNumberOfAdverts(categoryEntity.getNumberOfAdverts());

		return categoryDetail;
	}
}
