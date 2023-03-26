package sk.umb.dvestodola.bazarik.subsubcategory.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;
import sk.umb.dvestodola.bazarik.category.service.CategoryDetailDto;
import sk.umb.dvestodola.bazarik.exception.LibraryApplicationException;
import sk.umb.dvestodola.bazarik.subcategory.persistence.entity.SubcategoryEntity;
import sk.umb.dvestodola.bazarik.subcategory.persistence.repository.SubcategoryRepository;
import sk.umb.dvestodola.bazarik.subcategory.service.SubcategoryDetailDto;
import sk.umb.dvestodola.bazarik.subsubcategory.persistence.entity.SubsubcategoryEntity;
import sk.umb.dvestodola.bazarik.subsubcategory.persistence.repository.SubsubcategoryRepository;

@Service
public class SubsubcategoryService {
	
	private final SubsubcategoryRepository subsubcategoryRepository;
	private final SubcategoryRepository subcategoryRepository;

	SubsubcategoryService(SubcategoryRepository subcategoryRepository, SubsubcategoryRepository subsubcategoryRepository) {
		this.subcategoryRepository = subcategoryRepository;
		this.subsubcategoryRepository = subsubcategoryRepository;
	}

	public List<SubsubcategoryDetailDto> getAllSubsubcategories() {
        return mapToSubsubcategoryDtoList(subsubcategoryRepository.findAll());
    }

	public SubsubcategoryDetailDto getSubsubcategoryById(Long subcategoryId) {
		return mapToSubsubcategoryDetailDto(getSubsubcategoryEntityById(subcategoryId));
	}

	@Transactional
	public Long createSubsubcategory(SubsubcategoryRequestDto subsubcategoryRequestDto) {
		SubsubcategoryEntity subsubcategoryEntity = mapToSubsubcategoryEntity(subsubcategoryRequestDto);

		if (Objects.isNull(subsubcategoryEntity)) {
			throw new LibraryApplicationException("Subcategory must have valid category.");
		}

		if (Objects.isNull(subsubcategoryEntity.getSubcategory())) {
			throw new LibraryApplicationException("Subcategory must have valid category.");
		}

		return subsubcategoryRepository.save(subsubcategoryEntity).getId();
	}

	@Transactional
	public void updateSubsubcategory(Long subsubcategoryId, SubsubcategoryRequestDto subsubcategoryRequestDto) {
		SubsubcategoryEntity subsubcategoryEntity = getSubsubcategoryEntityById(subsubcategoryId);
        
		if (! Strings.isEmpty(subsubcategoryRequestDto.getName())) {
			subsubcategoryEntity.setName(subsubcategoryRequestDto.getName());
		}

		if (! Objects.isNull(subsubcategoryEntity.getSubcategory())) {
			Optional<SubcategoryEntity> subcategoryEntity = subcategoryRepository.findById(subsubcategoryRequestDto.getSubcategoryId());
			if (subcategoryEntity.isPresent()) {
				subsubcategoryEntity.setCategory(subcategoryEntity.get());
			} else {
				throw new LibraryApplicationException("Subcategory must have valid category.");
			}
		}

        subsubcategoryRepository.save(subsubcategoryEntity);
	}

	@Transactional
	public void deleteSubsubcategory(Long subcategoryId) {
		subsubcategoryRepository.deleteById(subcategoryId);
	}


	public List<SubsubcategoryDetailDto> getSubsubcategoriesBySubcategoryId(Long subcategoryId) {
		return getSubsubcategoryDetailListByCategoryId(subcategoryId);
	}

	private List<SubsubcategoryDetailDto> getSubsubcategoryDetailListByCategoryId(Long subcategoryId) {
		Iterable<SubsubcategoryEntity> subcategoryEntities = subsubcategoryRepository.getAllBySubcategoryId(subcategoryId);
		List<SubsubcategoryDetailDto> subcategoryEntityList = new ArrayList<>();

		subcategoryEntities.forEach(subcategory -> {
			subcategoryEntityList.add(mapToSubsubcategoryDetailDto(subcategory));
		});

		return subcategoryEntityList;
	}

	private SubsubcategoryEntity getSubsubcategoryEntityById(Long subsubcategoryId) {
		Optional<SubsubcategoryEntity> subsubcategory = subsubcategoryRepository.findById(subsubcategoryId);

        if (subsubcategory.isEmpty()) {
			throw new LibraryApplicationException("Subsubcategory must have valid category id.");
        }

		return subsubcategory.get();
	}

	private SubsubcategoryEntity mapToSubsubcategoryEntity(SubsubcategoryRequestDto subsubcategoryRequestDto) {
		SubsubcategoryEntity subsubcategoryEntity = new SubsubcategoryEntity();
		
		subsubcategoryEntity.setName(subsubcategoryRequestDto.getName());

		Optional<SubcategoryEntity> subcategoryEntity = subcategoryRepository.findById(subsubcategoryRequestDto.getSubcategoryId());
		if (subcategoryEntity.isPresent()) {
			subsubcategoryEntity.setCategory(subcategoryEntity.get());
		} else {
			throw new LibraryApplicationException("Subsubcategory must have valid subcategory id.");
		}

		return subsubcategoryEntity;
	}

	private List<SubsubcategoryDetailDto> mapToSubsubcategoryDtoList(Iterable<SubsubcategoryEntity> subsubcategoryEntities) {
		List<SubsubcategoryDetailDto> subsubcategoryEntityList = new ArrayList<>();

		subsubcategoryEntities.forEach(subsubcategory -> {
			SubsubcategoryDetailDto subsubcategoryDetailDto = mapToSubsubcategoryDetailDto(subsubcategory);
			subsubcategoryEntityList.add(subsubcategoryDetailDto);
		});

		return subsubcategoryEntityList;
	}

	private SubsubcategoryDetailDto mapToSubsubcategoryDetailDto(SubsubcategoryEntity subsubcategoryEntity) {
		SubsubcategoryDetailDto subsubcategoryDetailDto = new SubsubcategoryDetailDto();

		subsubcategoryDetailDto.setId(subsubcategoryEntity.getId());
		subsubcategoryDetailDto.setName(subsubcategoryEntity.getName());
		subsubcategoryDetailDto.setSubcategory(mapToSubcategoryDetailDto(subsubcategoryEntity.getSubcategory()));

		return subsubcategoryDetailDto;
	}

	private SubcategoryDetailDto mapToSubcategoryDetailDto(SubcategoryEntity subcategoryEntity) {
		SubcategoryDetailDto subcategoryDetailDto = new SubcategoryDetailDto();

		if (Objects.isNull(subcategoryEntity)) {
			throw new LibraryApplicationException("Subcategory is missing!");
		}

		subcategoryDetailDto.setId(subcategoryEntity.getId());
		subcategoryDetailDto.setName(subcategoryEntity.getName());
		subcategoryDetailDto.setCategory(mapToCategoryDetailDto(subcategoryEntity.getCategory()));

		return subcategoryDetailDto;
	}

	private CategoryDetailDto mapToCategoryDetailDto(CategoryEntity categoryEntity) {
		CategoryDetailDto categoryDetailDto = new CategoryDetailDto();

		categoryDetailDto.setId(categoryEntity.getId());
		categoryDetailDto.setName(categoryEntity.getName());

		return categoryDetailDto;
	}
}
