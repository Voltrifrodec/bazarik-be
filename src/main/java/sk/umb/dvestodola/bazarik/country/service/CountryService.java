package sk.umb.dvestodola.bazarik.country.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import sk.umb.dvestodola.bazarik.country.persistence.entity.CountryEntity;
import sk.umb.dvestodola.bazarik.country.persistence.repository.CountryRepository;
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;

@Service
public class CountryService {
	
	private final CountryRepository countryRepository;

	CountryService(CountryRepository countryRepository) {
		this.countryRepository = countryRepository;
	}

	public List<CountryDetailDto> getAllCategories() {
        return mapToCountryDetailList(countryRepository.findAll());
    }

	public CountryDetailDto getCountryById(Long countryId) {
		return mapToCountryDetail(getCountryEntityById(countryId));
	}

	@Transactional
	public Long createCountry(@Valid CountryRequestDto countryRequest) {
		CountryEntity countryEntity = mapToCountryEntity(countryRequest);

		return countryRepository.save(countryEntity).getId();
	}

	@Transactional
	public void updateCountry(Long countryId, @Valid CountryRequestDto countryRequest) {
		CountryEntity countryEntity = getCountryEntityById(countryId);
        
		if (! Strings.isEmpty(countryRequest.getName())) {
			countryEntity.setName(countryRequest.getName());
		}
        
        countryRepository.save(countryEntity);
	}

	@Transactional
	public void deleteCountry(Long countryId) {
		countryRepository.deleteById(countryId);
	}


	private CountryEntity getCountryEntityById(Long countryId) {
		Optional<CountryEntity> countryEntity = countryRepository.findById(countryId);

        if (countryEntity.isEmpty()) {
            throw new BazarikApplicationException("Country id must be valid.");
        }

		return countryEntity.get();
	}

	private CountryEntity mapToCountryEntity(CountryRequestDto countryRequest) {
		CountryEntity countryEntity = new CountryEntity();

		countryEntity.setName(countryRequest.getName());

		return countryEntity;
	}

	private List<CountryDetailDto> mapToCountryDetailList(Iterable<CountryEntity> countryEntities) {
		List<CountryDetailDto> countryEntityList = new ArrayList<>();

		countryEntities.forEach(countryEntity -> {
			CountryDetailDto countryDetail = mapToCountryDetail(countryEntity);
			countryEntityList.add(countryDetail);
		});

		return countryEntityList;
	}

	private CountryDetailDto mapToCountryDetail(CountryEntity countryEntity) {
		CountryDetailDto countryDetail = new CountryDetailDto();

		countryDetail.setId(countryEntity.getId());
		countryDetail.setName(countryEntity.getName());

		return countryDetail;
	}
}
