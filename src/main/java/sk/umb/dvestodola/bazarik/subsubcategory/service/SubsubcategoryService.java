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
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;
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
        return mapToSubsubcategoryList(subsubcategoryRepository.findAll());
    }

	public SubsubcategoryDetailDto getSubsubcategoryById(Long subcategoryId) {
		return mapToSubsubcategoryDetail(getSubsubcategoryEntityById(subcategoryId));
	}

	@Transactional
	public Long createSubsubcategory(SubsubcategoryRequestDto subsubcategoryRequest) {
		SubsubcategoryEntity subsubcategoryEntity = mapToSubsubcategoryEntity(subsubcategoryRequest);

		if (Objects.isNull(subsubcategoryEntity)) {
			throw new BazarikApplicationException("Subsubcategory must have valid subcategory.");
		}

		if (Objects.isNull(subsubcategoryEntity.getSubcategory())) {
			throw new BazarikApplicationException("Subcategory must have valid category.");
		}

		return subsubcategoryRepository.save(subsubcategoryEntity).getId();
	}

	@Transactional
	public void updateSubsubcategory(Long subsubcategoryId, SubsubcategoryRequestDto subsubcategoryRequest) {
		SubsubcategoryEntity subsubcategoryEntity = getSubsubcategoryEntityById(subsubcategoryId);
        
		if (! Strings.isEmpty(subsubcategoryRequest.getName())) {
			subsubcategoryEntity.setName(subsubcategoryRequest.getName());
		}

		if (! Objects.isNull(subsubcategoryEntity.getSubcategory())) {
			Optional<SubcategoryEntity> subcategoryEntity = subcategoryRepository.findById(subsubcategoryRequest.getSubcategoryId());

			if (subcategoryEntity.isPresent()) {
				subsubcategoryEntity.setCategory(subcategoryEntity.get());
			} else {
				throw new BazarikApplicationException("Subcategory must have valid category.");
			}
		}

        subsubcategoryRepository.save(subsubcategoryEntity);
	}

	@Transactional
	public void deleteSubsubcategory(Long subcategoryId) {
		subsubcategoryRepository.deleteById(subcategoryId);
	}


	public List<SubsubcategoryDetailDto> getAllSubsubcategoriesBySubcategoryId(Long subcategoryId) {
		return getSubsubcategoryDetailListByCategoryId(subcategoryId);
	}

	private List<SubsubcategoryDetailDto> getSubsubcategoryDetailListByCategoryId(Long subcategoryId) {
		Iterable<SubsubcategoryEntity> subcategoryEntities = subsubcategoryRepository.getAllBySubcategoryId(subcategoryId);
		List<SubsubcategoryDetailDto> subcategoryEntityList = new ArrayList<>();

		subcategoryEntities.forEach(subcategory -> {
			subcategoryEntityList.add(mapToSubsubcategoryDetail(subcategory));
		});

		return subcategoryEntityList;
	}

	private SubsubcategoryEntity getSubsubcategoryEntityById(Long subsubcategoryId) {
		Optional<SubsubcategoryEntity> subsubcategoryEntity = subsubcategoryRepository.findById(subsubcategoryId);

        if (subsubcategoryEntity.isEmpty()) {
			throw new BazarikApplicationException("Subsubcategory must have valid category.");
        }

		return subsubcategoryEntity.get();
	}

	private SubsubcategoryEntity mapToSubsubcategoryEntity(SubsubcategoryRequestDto subsubcategoryRequest) {
		SubsubcategoryEntity subsubcategoryEntity = new SubsubcategoryEntity();
		
		subsubcategoryEntity.setName(subsubcategoryRequest.getName());

		Optional<SubcategoryEntity> subcategoryEntity = subcategoryRepository.findById(subsubcategoryRequest.getSubcategoryId());

		if (subcategoryEntity.isPresent()) {
			subsubcategoryEntity.setCategory(subcategoryEntity.get());
		} else {
			throw new BazarikApplicationException("Subsubcategory must have valid subcategory.");
		}

		return subsubcategoryEntity;
	}

	private List<SubsubcategoryDetailDto> mapToSubsubcategoryList(Iterable<SubsubcategoryEntity> subsubcategoryEntities) {
		List<SubsubcategoryDetailDto> subsubcategoryEntityList = new ArrayList<>();

		subsubcategoryEntities.forEach(subsubcategoryEntity -> {
			SubsubcategoryDetailDto subsubcategoryDetail = mapToSubsubcategoryDetail(subsubcategoryEntity);
			subsubcategoryEntityList.add(subsubcategoryDetail);
		});

		return subsubcategoryEntityList;
	}

	private SubsubcategoryDetailDto mapToSubsubcategoryDetail(SubsubcategoryEntity subsubcategoryEntity) {
		SubsubcategoryDetailDto subsubcategoryDetail = new SubsubcategoryDetailDto();

		subsubcategoryDetail.setId(subsubcategoryEntity.getId());
		subsubcategoryDetail.setName(subsubcategoryEntity.getName());
		subsubcategoryDetail.setSubcategory(mapToSubcategoryDetail(subsubcategoryEntity.getSubcategory()));

		return subsubcategoryDetail;
	}

	private SubcategoryDetailDto mapToSubcategoryDetail(SubcategoryEntity subcategoryEntity) {
		SubcategoryDetailDto subcategoryDetail = new SubcategoryDetailDto();

		if (Objects.isNull(subcategoryEntity)) {
			throw new BazarikApplicationException("Subcategory must be valid.");
		}

		subcategoryDetail.setId(subcategoryEntity.getId());
		subcategoryDetail.setName(subcategoryEntity.getName());
		subcategoryDetail.setCategory(mapToCategoryDetail(subcategoryEntity.getCategory()));

		return subcategoryDetail;
	}

	private CategoryDetailDto mapToCategoryDetail(CategoryEntity categoryEntity) {
		CategoryDetailDto categoryDetail = new CategoryDetailDto();

		categoryDetail.setId(categoryEntity.getId());
		categoryDetail.setName(categoryEntity.getName());

		return categoryDetail;
	}
}
