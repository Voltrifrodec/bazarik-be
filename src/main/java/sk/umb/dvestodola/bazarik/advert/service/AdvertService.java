package sk.umb.dvestodola.bazarik.advert.service;

import java.util.Date;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.advert.persistence.entity.AdvertEntity;
import sk.umb.dvestodola.bazarik.advert.persistence.repository.AdvertRepository;
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
import sk.umb.dvestodola.bazarik.district.persistence.entity.DistrictEntity;
import sk.umb.dvestodola.bazarik.district.persistence.repository.DistrictRepository;
import sk.umb.dvestodola.bazarik.district.service.DistrictDetailDto;
import sk.umb.dvestodola.bazarik.exception.LibraryApplicationException;
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

	private final AdvertRepository advertRepository;
	private final CategoryRepository categoryRepository;
	private final SubcategoryRepository subcategoryRepository;
	private final SubsubcategoryRepository subsubcategoryRepository;
	private final ContactRepository contactRepository;
	private final DistrictRepository districtRepository;
	private final ImageRepository imageRepository;
	private final CurrencyRepository currencyRepository;

	public AdvertService(
		AdvertRepository advertRepository,
		CategoryRepository categoryRepository,
		SubcategoryRepository subcategoryRepository,
		SubsubcategoryRepository subsubcategoryRepository,
		ContactRepository contactRepository,
		DistrictRepository districtRepository,
		ImageRepository imageRepository,
		CurrencyRepository currencyRepository
	) {
		this.advertRepository = advertRepository;
		this.categoryRepository = categoryRepository;
		this.subcategoryRepository = subcategoryRepository;
		this.subsubcategoryRepository = subsubcategoryRepository;
		this.contactRepository = contactRepository;
		this.districtRepository = districtRepository;
		this.imageRepository = imageRepository;
		this.currencyRepository = currencyRepository;
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
	public void updateAdvert(Long advertId, AdvertRequestDto advertRequest) {
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

		if (! Objects.isNull(advertEntity.getCategory())) {
			Optional<CategoryEntity> categoryEntity = categoryRepository.findById(advertRequest.getCategoryId());
			if (categoryEntity.isPresent()) {
				advertEntity.setCategory(categoryEntity.get());
			} else {
				throw new LibraryApplicationException("Category must have a valid id.");
			}
		}

		if (! Objects.isNull(advertEntity.getSubcategory())) {
			Optional<SubcategoryEntity> subcategoryEntity = subcategoryRepository.findById(advertRequest.getSubcategoryId());
			if (subcategoryEntity.isPresent()) {
				advertEntity.setSubcategory(subcategoryEntity.get());
			} else {
				throw new LibraryApplicationException("Subcategory must have a valid id.");
			}
		}

		if (! Objects.isNull(advertEntity.getSubsubcategory())) {
			Optional<SubsubcategoryEntity> subsubcategoryEntity = subsubcategoryRepository.findById(advertRequest.getSubsubcategoryId());
			if (subsubcategoryEntity.isPresent()) {
				advertEntity.setSubsubcategory(subsubcategoryEntity.get());
			} else {
				throw new LibraryApplicationException("Subsubcategory must have a valid id.");
			}
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
				throw new LibraryApplicationException("District must have a valid id.");
			}
		}

		if (! Objects.isNull(advertEntity.getImage())) {
			Optional<ImageEntity> contactEntity = imageRepository.findById(advertRequest.getImageId());
			if (contactEntity.isPresent()) {
				advertEntity.setImage(contactEntity.get());
			} else {
				throw new LibraryApplicationException("Image must have a valid id.");
			}
		}

        advertRepository.save(advertEntity);
	}

	@Transactional
	public void deleteAdvert(Long advertId) {
		advertRepository.deleteById(advertId);
	}

	private AdvertEntity getAdvertEntityById(Long advertId) {
		Optional<AdvertEntity> advertEntity = advertRepository.findById(advertId);

        if (advertEntity.isEmpty()) {
			throw new LibraryApplicationException("Advert must have a valid id.");
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
			throw new LibraryApplicationException("Currency must have a valid id.");
		}

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
			throw new LibraryApplicationException("District must have a valid id.");
		}

		Optional<ImageEntity> imageEntity = imageRepository.findById(advertRequest.getImageId());
		if (imageEntity.isPresent()) {
			advertEntity.setImage(imageEntity.get());
		} else {
			Optional<ImageEntity> nullImage = imageRepository.findById(1L);
			if (nullImage.isPresent()) {
				advertEntity.setImage(nullImage.get());
			} else {
				throw new LibraryApplicationException("Image could not be set to imageNull (index 0).");
			}
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

		advertDetail.setCategory(mapToCategoryDetail(advertEntity.getCategory()));
		advertDetail.setSubcategory(mapToSubcategoryDetail(advertEntity.getSubcategory()));
		advertDetail.setSubsubcategory(mapToSubsubcategoryDetail(advertEntity.getSubsubcategory()));

		advertDetail.setContact(mapToContactDetail(advertEntity.getContact()));
		advertDetail.setDistrict(mapToDistrictDetail(advertEntity.getDistrict()));
		advertDetail.setImage(mapToImageDetail(advertEntity.getImage()));

		return advertDetail;
	}

	private CategoryDetailDto mapToCategoryDetail(CategoryEntity categoryEntity) {
		CategoryDetailDto categoryDetail = new CategoryDetailDto();

		categoryDetail.setId(categoryEntity.getId());
		categoryDetail.setName(categoryEntity.getName());

		return categoryDetail;
	}

	private SubcategoryDetailDto mapToSubcategoryDetail(SubcategoryEntity subcategoryEntity) {
		SubcategoryDetailDto subcategoryDetail = new SubcategoryDetailDto();

		subcategoryDetail.setId(subcategoryEntity.getId());
		subcategoryDetail.setName(subcategoryEntity.getName());
		subcategoryDetail.setCategory(mapToCategoryDetail(subcategoryEntity.getCategory()));

		return subcategoryDetail;
	}

	private SubsubcategoryDetailDto mapToSubsubcategoryDetail(SubsubcategoryEntity subsubcategoryEntity) {
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
			throw new LibraryApplicationException("Region is missing!");
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
		// contactDetail.setPhoneNumber(contactEntity.getPhoneNumber());
		contactDetail.setEmail(contactEntity.getEmail());
		
		return contactDetail;
	}

	public ImageDetailDto mapToImageDetail(ImageEntity imageEntity) {
		ImageDetailDto imageDetail = new ImageDetailDto();

		imageDetail.setId(imageEntity.getId());

		Blob blob;
		try {
			blob = new SerialBlob(imageEntity.getImage());
			imageDetail.setImage(blob);
		} catch (SerialException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return imageDetail;
	}

}
