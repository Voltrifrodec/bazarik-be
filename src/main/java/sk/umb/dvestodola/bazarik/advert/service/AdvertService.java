package sk.umb.dvestodola.bazarik.advert.service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.advert.persistence.entity.AdvertEntity;
import sk.umb.dvestodola.bazarik.advert.persistence.repository.AdvertRepository;
import sk.umb.dvestodola.bazarik.category.persistence.entity.CategoryEntity;
import sk.umb.dvestodola.bazarik.category.persistence.repository.CategoryRepository;
import sk.umb.dvestodola.bazarik.contact.persistence.entity.ContactEntity;
import sk.umb.dvestodola.bazarik.contact.persistence.repository.ContactRepository;
import sk.umb.dvestodola.bazarik.country.persistence.entity.CountryEntity;
import sk.umb.dvestodola.bazarik.country.service.CountryDetailDto;
import sk.umb.dvestodola.bazarik.district.persistence.entity.DistrictEntity;
import sk.umb.dvestodola.bazarik.district.persistence.repository.DistrictRepository;
import sk.umb.dvestodola.bazarik.exception.LibraryApplicationException;
import sk.umb.dvestodola.bazarik.image.persistence.entity.ImageEntity;
import sk.umb.dvestodola.bazarik.image.persistence.repository.ImageRepository;
import sk.umb.dvestodola.bazarik.subcategory.persistence.entity.SubcategoryEntity;
import sk.umb.dvestodola.bazarik.subcategory.persistence.repository.SubcategoryRepository;
import sk.umb.dvestodola.bazarik.subsubcategory.persistence.entity.SubsubcategoryEntity;
import sk.umb.dvestodola.bazarik.subsubcategory.persistence.repository.SubsubcategoryRepository;

@Service
public class AdvertService {

	private final AdvertRepository advertRepository;
	private final CategoryRepository categoryRepository;
	private final SubcategoryRepository subcategoryRepository;
	private final SubsubcategoryRepository subsubcategoryRepository;
	private final ContactRepository contactRepository;
	private final DistrictRepository districtRepository;
	private final ImageRepository imageRepository;

	public AdvertService(
		AdvertRepository advertRepository,
		CategoryRepository categoryRepository,
		SubcategoryRepository subcategoryRepository,
		SubsubcategoryRepository subsubcategoryRepository,
		ContactRepository contactRepository,
		DistrictRepository districtRepository,
		ImageRepository imageRepository
	) {
		this.advertRepository = advertRepository;
		this.categoryRepository = categoryRepository;
		this.subcategoryRepository = subcategoryRepository;
		this.subsubcategoryRepository = subsubcategoryRepository;
		this.contactRepository = contactRepository;
		this.districtRepository = districtRepository;
		this.imageRepository = imageRepository;
	}

	public List<AdvertDetailDto> getAllAdverts() {
        return mapToAdvertDetailList(advertRepository.findAll());
    }

	public AdvertDetailDto getAdvertById(Long advertId) {
		return mapToAdvertDetail(getAdvertEntityById(advertId));
	}

	@Transactional
	public Long createAdvert(AdvertRequestDto advertRequestDto) {
		AdvertEntity advertEntity = mapToAdvertEntity(advertRequestDto);

		if (Objects.isNull(advertEntity)) {
			throw new LibraryApplicationException("Advert information must be filled properly.");
		}

		return advertRepository.save(advertEntity).getId();
	}

	@Transactional
	public void updateAdvert(Long advertId, AdvertRequestDto advertRequestDto) {
		AdvertEntity advertEntity = getAdvertEntityById(advertId);
        
		if (! Strings.isEmpty(advertRequestDto.getName())) {
			advertEntity.setName(advertRequestDto.getName());
		}

		// TODO: Check for every attribute
		/* 
		if (! Objects.isNull(advertEntity.getCountry())) {
			/* Optional<CountryEntity> advertsEntity = countryRepository.findById(advertRequestDto.getCountryId());
			if (advertsEntity.isPresent()) {
				advertEntity.setCountry(advertsEntity.get());
			} else {
				throw new LibraryApplicationException("Region must have a valid id.");
			}
		} */

        advertRepository.save(advertEntity);
	}

	@Transactional
	public void deleteAdvert(Long advertId) {
		advertRepository.deleteById(advertId);
	}

	private AdvertEntity getAdvertEntityById(Long advertId) {
		Optional<AdvertEntity> advert = advertRepository.findById(advertId);

        if (advert.isEmpty()) {
			throw new LibraryApplicationException("Advert must have a valid id.");
        }

		return advert.get();
	}

	private AdvertEntity mapToAdvertEntity(AdvertRequestDto advertRequest) {
		AdvertEntity advertEntity = new AdvertEntity();

		advertEntity.setName(advertRequest.getName());
		advertEntity.setDescription(advertRequest.getDescription());

		Optional<CategoryEntity> categoryEntity = categoryRepository.findById(advertRequest.getCategoryId());
		if (categoryEntity.isPresent()) {
			advertEntity.setCategory(categoryEntity.get());
		} else {
			throw new LibraryApplicationException("Category must have a valid id.");
		}

		Optional<SubcategoryEntity> subcategoryEntity = subcategoryRepository.findById(advertRequest.getSubcategoryId());
		if (subcategoryEntity.isPresent()) {
			advertEntity.setSubcategory(subcategoryEntity.get());
		} else {
			throw new LibraryApplicationException("Subcategory must have a valid id.");
		}

		Optional<SubsubcategoryEntity> subsubcategoryEntity = subsubcategoryRepository.findById(advertRequest.getSubsubcategoryId());
		if (subsubcategoryEntity.isPresent()) {
			advertEntity.setSubsubcategory(subsubcategoryEntity.get());
		} else {
			throw new LibraryApplicationException("Subsubcategory must have a valid id.");
		}
		
		Iterable<ContactEntity> contactEntities = contactRepository.findByEmail(advertRequest.getContactEmail());
		if (contactEntities.iterator().hasNext()) {
			advertEntity.setContact(contactEntities.iterator().next());
		} else {
			ContactEntity contactEntity = new ContactEntity();
			contactEntity.setEmail(advertRequest.getContactEmail());
			advertEntity.setContact(contactRepository.save(contactEntity));
		}

		Optional<DistrictEntity> districtEntity = districtRepository.findById(advertRequest.getDistrictId());
		if (districtEntity.isPresent()) {
			advertEntity.setDistrict(districtEntity.get());
		} else {
			throw new LibraryApplicationException("District must have a valid id.");
		}

		Optional<ImageEntity> imageEntity = imageRepository.findById(advertRequest.getImageId());
		if (imageEntity.isPresent()) {
			advertEntity.setImage(imageEntity.get());
		} else {
			Optional<ImageEntity> nullImage = imageRepository.findById(0L);
			if (nullImage.isPresent()) {
				advertEntity.setImage(nullImage.get());
			} else {
				throw new LibraryApplicationException("Image could not be set to imageNull (index 0).");
				// throw new LibraryApplicationException("Image must have a valid id.");
			}
		}
		
		advertEntity.setKeywords(advertRequest.getKeywords());
		advertEntity.setDateAdded(new Date(new java.util.Date().getTime()));
		advertEntity.setPriceEur(advertRequest.getPriceEur());
		advertEntity.setFixedPrice(advertRequest.getFixedPrice());
		
		return advertEntity;
	}

	private List<AdvertDetailDto> mapToAdvertDetailList(Iterable<AdvertEntity> advertsEntities) {
		List<AdvertDetailDto> advertEntityList = new ArrayList<>();

		advertsEntities.forEach(advert -> {
			AdvertDetailDto advertDetailDto = mapToAdvertDetail(advert);
			advertEntityList.add(advertDetailDto);
		});

		return advertEntityList;
	}

	private AdvertDetailDto mapToAdvertDetail(AdvertEntity advertEntity) {
		AdvertDetailDto advert = new AdvertDetailDto();

		advert.setId(advertEntity.getId());
		advert.setName(advertEntity.getName());
		// advert.setCountry(mapToCountryDetailDto(advertEntity.getCountry()));

		return advert;
	}

	private CountryDetailDto mapToCountryDetailDto(CountryEntity countryEntity) {
		CountryDetailDto advertsDetailDto = new CountryDetailDto();

		if (Objects.isNull(countryEntity)) {
			throw new LibraryApplicationException("Category is missing!");
		}

		advertsDetailDto.setId(countryEntity.getId());
		advertsDetailDto.setName(countryEntity.getName());

		return advertsDetailDto;
	}
}
