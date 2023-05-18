package sk.umb.dvestodola.bazarik.advert.service;

import java.util.Date;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.logging.log4j.util.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.advert.persistence.entity.AdvertEntity;
import sk.umb.dvestodola.bazarik.advert.persistence.repository.AdvertPageRepository;
import sk.umb.dvestodola.bazarik.advert.persistence.repository.AdvertRepository;
import sk.umb.dvestodola.bazarik.advert.persistence.repository.AdvertRepositoryImplementation;
import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;
import sk.umb.dvestodola.bazarik.category.persistence.repository.CategoryRepository;
import sk.umb.dvestodola.bazarik.category.service.CategoryDetailDto;
import sk.umb.dvestodola.bazarik.contact.persistence.entity.ContactEntity;
import sk.umb.dvestodola.bazarik.contact.persistence.repository.ContactRepository;
import sk.umb.dvestodola.bazarik.contact.service.ContactDetailDto;
import sk.umb.dvestodola.bazarik.country.persistence.entity.CountryEntity;
import sk.umb.dvestodola.bazarik.country.service.CountryDetailDto;
import sk.umb.dvestodola.bazarik.currency.persistence.entity.CurrencyEntity;
import sk.umb.dvestodola.bazarik.currency.persistence.repository.CurrencyRepository;
import sk.umb.dvestodola.bazarik.currency.service.CurrencyDetailDto;
import sk.umb.dvestodola.bazarik.district.persistence.entity.DistrictEntity;
import sk.umb.dvestodola.bazarik.district.persistence.repository.DistrictRepository;
import sk.umb.dvestodola.bazarik.district.service.DistrictDetailDto;
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;
import sk.umb.dvestodola.bazarik.image.persistence.entity.ImageEntity;
import sk.umb.dvestodola.bazarik.image.persistence.repository.ImageRepository;
import sk.umb.dvestodola.bazarik.image.service.ImageDetailDto;
import sk.umb.dvestodola.bazarik.region.persistence.entity.RegionEntity;
import sk.umb.dvestodola.bazarik.region.service.RegionDetailDto;
import sk.umb.dvestodola.bazarik.subcategory.persistence.entity.SubcategoryEntity;
import sk.umb.dvestodola.bazarik.subcategory.persistence.repository.SubcategoryRepository;
import sk.umb.dvestodola.bazarik.subcategory.service.SubcategoryDetailDto;
import sk.umb.dvestodola.bazarik.subsubcategory.persistence.entity.SubsubcategoryEntity;
import sk.umb.dvestodola.bazarik.subsubcategory.persistence.repository.SubsubcategoryRepository;
import sk.umb.dvestodola.bazarik.subsubcategory.service.SubsubcategoryDetailDto;

@Service
public class AdvertService {

	private final int MINIMUM_QUERY_LENGTH = 3;

	private final AdvertRepository advertRepository;
	private final AdvertRepositoryImplementation advertRepositoryImplementation;
	private final CategoryRepository categoryRepository;
	private final SubcategoryRepository subcategoryRepository;
	private final SubsubcategoryRepository subsubcategoryRepository;
	private final ContactRepository contactRepository;
	private final DistrictRepository districtRepository;
	private final ImageRepository imageRepository;
	private final CurrencyRepository currencyRepository;
	private final AdvertPageRepository advertPageRepository;

	public AdvertService(
		AdvertRepository advertRepository,
		AdvertRepositoryImplementation advertRepositoryImplementation,
		CategoryRepository categoryRepository,
		SubcategoryRepository subcategoryRepository,
		SubsubcategoryRepository subsubcategoryRepository,
		ContactRepository contactRepository,
		DistrictRepository districtRepository,
		ImageRepository imageRepository,
		CurrencyRepository currencyRepository,
		AdvertPageRepository advertPageRepository
	) {
		this.advertRepository = advertRepository;
		this.advertRepositoryImplementation = advertRepositoryImplementation;
		this.categoryRepository = categoryRepository;
		this.subcategoryRepository = subcategoryRepository;
		this.subsubcategoryRepository = subsubcategoryRepository;
		this.contactRepository = contactRepository;
		this.districtRepository = districtRepository;
		this.imageRepository = imageRepository;
		this.currencyRepository = currencyRepository;
		this.advertPageRepository = advertPageRepository;
	}

	public List<AdvertDetailDto> getAllAdverts() {
        return mapToAdvertDetailList(advertRepository.findAll());
    }

	public AdvertDetailDto getAdvertById(UUID advertId) {
		return mapToAdvertDetail(getAdvertEntityById(advertId));
	}
	
	// https://stackoverflow.com/questions/53601006/how-to-modify-page-type-result-of-spring-repository-search-for-rest-api
	public Page<AdvertDetailDto> getPaginatedAdverts(Pageable pageable) {
		Page<AdvertEntity> advertEntityPage = advertPageRepository.findAll(pageable);
		
		Page<AdvertDetailDto> advertDetailPage = new PageImpl<>(
			advertEntityPage
				.getContent().stream()
				.map(advertDetail -> mapToAdvertDetail(advertDetail))
				.collect(Collectors.toList()),
			pageable,
			advertEntityPage.getTotalElements()
		);
		
		return advertDetailPage;
	}
	
	public List<AdvertDetailDto> getRecentAdverts(Long count) {
		if (count < 1) throw new BazarikApplicationException("Count limit for recent adverts must be bigger than 0.");

		return mapToAdvertDetailList(advertRepositoryImplementation.findRecent(count));
	}

	// @Transactional
	public List<AdvertDetailDto> getAllAdvertsByQuery(String query) {
		query = query.toLowerCase();

		if (query.trim().length() < MINIMUM_QUERY_LENGTH) {
			throw new BazarikApplicationException("Query must have more than 3 (three) characters.");
		}
		
		return mapToAdvertDetailList(advertRepository.findAllAdvertsByQuery(query));
	}

	public Long getNumberOfAdvertsInCategoryByCategoryId(Long categoryId) {		
		return advertRepository.getNumberOfAdvertsInCategoryByCategoryId(categoryId);
	}

	public List<AdvertDetailDto> getAllAdvertsByCategoryId(Long categoryId) {
		return mapToAdvertDetailList(advertRepository.findAllAdvertsByCategoryId(categoryId));
	}

	public List<AdvertDetailDto> getAllAdvertsBySubcategoryId(Long subcategoryId) {
		return mapToAdvertDetailList(advertRepository.findAllAdvertsBySubcategoryId(subcategoryId));
	}

	public List<AdvertDetailDto> getAllAdvertsBySubsubcategoryId(Long subsubcategoryId) {
		return mapToAdvertDetailList(advertRepository.findAllAdvertsBySubsubcategoryId(subsubcategoryId));
	}

	@Transactional
	public UUID createAdvert(AdvertRequestDto advertRequestDto) {
		AdvertEntity advertEntity = mapToAdvertEntity(advertRequestDto);

		if (Objects.isNull(advertEntity)) {
			throw new BazarikApplicationException("Advert information must be filled properly.");
		}

		return advertRepository.save(advertEntity).getId();
	}

	@Transactional
	public void updateAdvert(UUID advertId, AdvertRequestDto advertRequest) {
		AdvertEntity advertEntity = getAdvertEntityById(advertId);

		advertEntity.setDateModified(new Date());
        
		if (! Strings.isEmpty(advertRequest.getName())) {
			advertEntity.setName(advertRequest.getName());
		}

		if (! Strings.isEmpty(advertRequest.getDescription())) {
			advertEntity.setDescription(advertRequest.getDescription());
		}

		if (! Strings.isEmpty(advertRequest.getKeywords())) {
			advertEntity.setKeywords(advertRequest.getKeywords());
		}

		if (! Objects.isNull(advertRequest.getPriceEur()) && advertRequest.getPriceEur() >= 0) {
			advertEntity.setPriceEur(advertRequest.getPriceEur());
		}

		if (! Objects.isNull(advertRequest.getFixedPrice())) {
			advertEntity.setFixedPrice(advertRequest.getFixedPrice());
		}

		if (! Objects.isNull(advertRequest.getCategoryId())) {
			Optional<CategoryEntity> categoryEntity = categoryRepository.findById(advertRequest.getCategoryId());
			if (categoryEntity.isPresent()) {
				advertEntity.setCategory(categoryEntity.get());
			} else {
				throw new BazarikApplicationException("Category must have a valid id.");
			}
		}

		if (! Objects.isNull(advertEntity.getCategory())) {
			if (! Objects.isNull(advertRequest.getSubcategoryId())) {
				Optional<SubcategoryEntity> subcategoryEntity = subcategoryRepository.findById(advertRequest.getSubcategoryId());
				
				if (subcategoryEntity.isPresent()) {
					if (advertEntity.getCategory().getId().equals(subcategoryEntity.get().getCategory().getId())) {
						advertEntity.setSubcategory(subcategoryEntity.get());
					} else {
						// advertEntity.setSubcategory(null);
						return;
					}
				} else {
					throw new BazarikApplicationException("Subcategory must have a valid id.");
				}
			} else {
				advertEntity.setSubcategory(null);
			}
		} else {
			advertEntity.setSubcategory(null);
		}

		if (! Objects.isNull(advertEntity.getSubcategory())) {
			if (! Objects.isNull(advertRequest.getSubsubcategoryId())) {
				Optional<SubsubcategoryEntity> subsubcategoryEntity = subsubcategoryRepository.findById(advertRequest.getSubsubcategoryId());
				
				if (subsubcategoryEntity.isPresent()) {
					if (advertEntity.getSubcategory().getId().equals(subsubcategoryEntity.get().getSubcategory().getId())) {
						advertEntity.setSubsubcategory(subsubcategoryEntity.get());
					} else {
						// advertEntity.setSubsubcategory(null);
						return;
					}
				} else {
					throw new BazarikApplicationException("Subsubcategory must have a valid id.");
				}
			} else {
				advertEntity.setSubsubcategory(null);
			}
		} else {
			advertEntity.setSubsubcategory(null);
		}

		if (! Objects.isNull(advertEntity.getContact())) {
			Optional<ContactEntity> contactEntity = contactRepository.findByEmail(advertRequest.getContactEmail());
			if (contactEntity.isPresent()) {
				advertEntity.setContact(contactEntity.get());
			} else {
				ContactEntity newContactEntity = new ContactEntity();

				newContactEntity.setEmail(advertRequest.getContactEmail());

				advertEntity.setContact(contactRepository.save(newContactEntity));
			}
		}

		if (! Objects.isNull(advertEntity.getDistrict())) {
			Optional<DistrictEntity> districtEntity = districtRepository.findById(advertRequest.getDistrictId());
			if (districtEntity.isPresent()) {
				advertEntity.setDistrict(districtEntity.get());
			} else {
				throw new BazarikApplicationException("District must have a valid id.");
			}
		}

		if (! Objects.isNull(advertEntity.getImage())) {
			Optional<ImageEntity> contactEntity = imageRepository.findById(advertRequest.getImageId());

			if (contactEntity.isPresent()) {
				advertEntity.setImage(contactEntity.get());
			} else {
				throw new BazarikApplicationException("Image must have a valid id.");
			}
		} else {
			Optional<ImageEntity> contactEntity = imageRepository.findById(0L);

			if (contactEntity.isPresent()) {
				advertEntity.setImage(contactEntity.get());
			} else {
				throw new BazarikApplicationException("Default image with index 0 is missing.");
			}
		}

        advertRepository.save(advertEntity);
	}

	@Transactional
	public void deleteAdvert(UUID advertId) {
		advertRepository.deleteById(advertId);
	}


	private AdvertEntity getAdvertEntityById(UUID advertId) {
		Optional<AdvertEntity> advertEntity = advertRepository.findById(advertId);

        if (advertEntity.isEmpty()) {
			throw new BazarikApplicationException("Advert must have a valid id.");
        }

		return advertEntity.get();
	}

	private AdvertEntity mapToAdvertEntity(AdvertRequestDto advertRequest) {
		AdvertEntity advertEntity = new AdvertEntity();

		advertEntity.setName(advertRequest.getName());
		advertEntity.setDescription(advertRequest.getDescription());
		advertEntity.setKeywords(advertRequest.getKeywords());
		advertEntity.setDateAdded(new Date());
		advertEntity.setDateModified(advertEntity.getDateAdded());
		advertEntity.setPriceEur(advertRequest.getPriceEur());
		advertEntity.setFixedPrice(advertRequest.getFixedPrice());
		
		Optional<CurrencyEntity> currencyEntity = currencyRepository.findById(1L);
		if (currencyEntity.isPresent()) {
			advertEntity.setCurrency(currencyEntity.get());
		} else {
			throw new BazarikApplicationException("Currency must have a valid id.");
		}


		Optional<CategoryEntity> categoryEntity = categoryRepository.findById(advertRequest.getCategoryId());
		if (categoryEntity.isPresent()) {
			advertEntity.setCategory(categoryEntity.get());
		} else {
			throw new BazarikApplicationException("Category must have a valid id.");
		}


		if (! Objects.isNull(advertRequest.getSubcategoryId())) {
			Optional<SubcategoryEntity> subcategoryEntity = subcategoryRepository.findById(advertRequest.getSubcategoryId());

			if (subcategoryEntity.isPresent()) {
				if (advertEntity.getCategory().getId().equals(subcategoryEntity.get().getCategory().getId())) {
					advertEntity.setSubcategory(subcategoryEntity.get());
				} else {
					throw new BazarikApplicationException("Subcategory must have a valid id.");
					// advertEntity.setSubcategory(null);
				}
			} else {
				// advertEntity.setSubcategory(null);
				throw new BazarikApplicationException("Subcategory must have a valid id.");
			}
		} else {
			advertEntity.setSubcategory(null);
		}


		if (! Objects.isNull(advertEntity.getSubcategory())) {
			if (! Objects.isNull(advertRequest.getSubsubcategoryId())) {
				Optional<SubsubcategoryEntity> subsubcategoryEntity = subsubcategoryRepository.findById(advertRequest.getSubsubcategoryId());
	
				if (subsubcategoryEntity.isPresent()) {
					if (advertEntity.getSubcategory().getId().equals(subsubcategoryEntity.get().getSubcategory().getId())) {
						advertEntity.setSubsubcategory(subsubcategoryEntity.get());
					} else {
						// advertEntity.setSubsubcategory(null);
						throw new BazarikApplicationException("Subsubcategory must have a valid id.");
					}
				} else {
					// advertEntity.setSubsubcategory(null);
					throw new BazarikApplicationException("Subsubcategory must have a valid id.");
				}
			} else {
				advertEntity.setSubsubcategory(null);
			}
		} else {
			advertEntity.setSubsubcategory(null);
		}
		
		Optional<ContactEntity> contactEntity = contactRepository.findByEmail(advertRequest.getContactEmail());
		if (contactEntity.isPresent()) {
			advertEntity.setContact(contactEntity.get());
		} else {
			ContactEntity newContactEntity = new ContactEntity();

			newContactEntity.setEmail(advertRequest.getContactEmail());
			newContactEntity.setPhoneNumber("");

			advertEntity.setContact(contactRepository.save(newContactEntity));
		}

		Optional<DistrictEntity> districtEntity = districtRepository.findById(advertRequest.getDistrictId());
		if (districtEntity.isPresent()) {
			advertEntity.setDistrict(districtEntity.get());
		} else {
			throw new BazarikApplicationException("District must have a valid id.");
		}

		Optional<ImageEntity> imageEntity = imageRepository.findById(advertRequest.getImageId());
		if (imageEntity.isPresent()) {
			advertEntity.setImage(imageEntity.get());
		} else {
			throw new BazarikApplicationException("Image must have a valid id.");
		}
		
		return advertEntity;
	}

	private List<AdvertDetailDto> mapToAdvertDetailList(Iterable<AdvertEntity> advertsEntities) {
		List<AdvertDetailDto> advertEntityList = new ArrayList<>();

		advertsEntities.forEach(advert -> {
			AdvertDetailDto advertDetail = mapToAdvertDetail(advert);
			advertEntityList.add(advertDetail);
		});

		return advertEntityList;
	}

	private AdvertDetailDto mapToAdvertDetail(AdvertEntity advertEntity) {
		AdvertDetailDto advertDetail = new AdvertDetailDto();

		advertDetail.setId(advertEntity.getId());
		advertDetail.setName(advertEntity.getName());
		advertDetail.setDescription(advertEntity.getDescription());
		advertDetail.setKeywords(advertEntity.getKeywords());
		advertDetail.setDateAdded(advertEntity.getDateAdded());
		advertDetail.setDateModified(advertEntity.getDateModified());
		advertDetail.setPriceEur(advertEntity.getPriceEur());
		advertDetail.setFixedPrice(advertEntity.getFixedPrice());

		advertDetail.setCurrency(mapToCurrencyDetail(advertEntity.getCurrency()));

		advertDetail.setCategory(mapToCategoryDetail(advertEntity.getCategory()));
		advertDetail.setSubcategory(mapToSubcategoryDetail(advertEntity.getSubcategory()));
		advertDetail.setSubsubcategory(mapToSubsubcategoryDetail(advertEntity.getSubsubcategory()));

		advertDetail.setContact(mapToContactDetail(advertEntity.getContact()));
		advertDetail.setDistrict(mapToDistrictDetail(advertEntity.getDistrict()));
		advertDetail.setImage(mapToImageDetail(advertEntity.getImage()));

		return advertDetail;
	}

	private CurrencyDetailDto mapToCurrencyDetail(CurrencyEntity currencyEntity) {
		CurrencyDetailDto currencyDetail = new CurrencyDetailDto();

		currencyDetail.setId(currencyEntity.getId());
		currencyDetail.setSymbol(currencyEntity.getSymbol());
		currencyDetail.setName(currencyEntity.getName());

		return currencyDetail;
	}

	private CategoryDetailDto mapToCategoryDetail(CategoryEntity categoryEntity) {
		CategoryDetailDto categoryDetail = new CategoryDetailDto();

		categoryDetail.setId(categoryEntity.getId());
		categoryDetail.setName(categoryEntity.getName());

		return categoryDetail;
	}

	private SubcategoryDetailDto mapToSubcategoryDetail(SubcategoryEntity subcategoryEntity) {
		if (Objects.isNull(subcategoryEntity)) return null;

		SubcategoryDetailDto subcategoryDetail = new SubcategoryDetailDto();

		subcategoryDetail.setId(subcategoryEntity.getId());
		subcategoryDetail.setName(subcategoryEntity.getName());
		subcategoryDetail.setCategory(mapToCategoryDetail(subcategoryEntity.getCategory()));

		return subcategoryDetail;
	}

	private SubsubcategoryDetailDto mapToSubsubcategoryDetail(SubsubcategoryEntity subsubcategoryEntity) {
		if (Objects.isNull(subsubcategoryEntity)) return null;

		SubsubcategoryDetailDto subsubcategoryDetail = new SubsubcategoryDetailDto();

		subsubcategoryDetail.setId(subsubcategoryEntity.getId());
		subsubcategoryDetail.setName(subsubcategoryEntity.getName());
		subsubcategoryDetail.setSubcategory(mapToSubcategoryDetail(subsubcategoryEntity.getSubcategory()));

		return subsubcategoryDetail;
	}

	private DistrictDetailDto mapToDistrictDetail(DistrictEntity regionEntity) {
		DistrictDetailDto regionDetail = new DistrictDetailDto();

		regionDetail.setId(regionEntity.getId());
		regionDetail.setName(regionEntity.getName());
		regionDetail.setPostcode(regionEntity.getPostcode());
		regionDetail.setRegion(mapToCountryDetail(regionEntity.getRegion()));

		return regionDetail;
	}

	private RegionDetailDto mapToCountryDetail(RegionEntity regionEntity) {
		RegionDetailDto regionDetail = new RegionDetailDto();

		if (Objects.isNull(regionEntity)) {
			throw new BazarikApplicationException("Region is missing!");
		}

		regionDetail.setId(regionEntity.getId());
		regionDetail.setName(regionEntity.getName());
		regionDetail.setCountry(mapToCountryDetail(regionEntity.getCountry()));

		return regionDetail;
	}

	private CountryDetailDto mapToCountryDetail(CountryEntity countryEntity) {
		CountryDetailDto countryDetail = new CountryDetailDto();
		
		countryDetail.setId(countryEntity.getId());
		countryDetail.setName(countryEntity.getName());

		return countryDetail;
	}

	private ContactDetailDto mapToContactDetail(ContactEntity contactEntity) {
		ContactDetailDto contactDetail = new ContactDetailDto();

		contactDetail.setId(contactEntity.getId());
		contactDetail.setPhoneNumber(contactEntity.getPhoneNumber());
		contactDetail.setEmail(contactEntity.getEmail());
		
		return contactDetail;
	}

	public ImageDetailDto mapToImageDetail(ImageEntity imageEntity) {
		ImageDetailDto imageDetail = new ImageDetailDto();

		imageDetail.setId(imageEntity.getId());
		imageDetail.setOriginalFileName(imageEntity.getOriginalFileName());
		imageDetail.setType(imageEntity.getType());
		imageDetail.setSizeBytes(imageEntity.getSizeBytes());
		imageDetail.setOriginalSizeBytes(imageEntity.getOriginalSizeBytes());

		Blob blob;
		try {
			blob = new SerialBlob(imageEntity.getImage());
			imageDetail.setImage(blob);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return imageDetail;
	}

	

}
