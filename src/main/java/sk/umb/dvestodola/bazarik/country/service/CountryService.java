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
        return mapToCountryDtoList(countryRepository.findAll());
    }

	public CountryDetailDto getCountryById(Long countryId) {
		return mapToCountryDto(getCountryEntityById(countryId));
	}

	@Transactional
	public Long createCountry(@Valid CountryRequestDto countryRequestDto) {
		CountryEntity countryEntity = mapToCountryEntity(countryRequestDto);

		return countryRepository.save(countryEntity).getId();
	}

	@Transactional
	public void updateCountry(Long countryId, @Valid CountryRequestDto countryRequestDto) {
		CountryEntity countryEntity = getCountryEntityById(countryId);
        
		if (! Strings.isEmpty(countryRequestDto.getName())) {
			countryEntity.setName(countryRequestDto.getName());
		}
        
        countryRepository.save(countryEntity);
	}

	@Transactional
	public void deleteCountry(Long countryId) {
		countryRepository.deleteById(countryId);
	}


	private CountryEntity getCountryEntityById(Long countryId) {
		Optional<CountryEntity> country = countryRepository.findById(countryId);

        if (country.isEmpty()) {
            throw new BazarikApplicationException("Country could not be found, id: " + countryId);
        }

		return country.get();
	}

	private CountryEntity mapToCountryEntity(CountryRequestDto countryRequestDto) {
		CountryEntity countryEntity = new CountryEntity();

		countryEntity.setName(countryRequestDto.getName());

		return countryEntity;
	}

	private List<CountryDetailDto> mapToCountryDtoList(Iterable<CountryEntity> countryEntities) {
		List<CountryDetailDto> countryEntityList = new ArrayList<>();

		countryEntities.forEach(country -> {
			CountryDetailDto countryDetailDto = mapToCountryDto(country);
			countryEntityList.add(countryDetailDto);
		});

		return countryEntityList;
	}

	private CountryDetailDto mapToCountryDto(CountryEntity countryEntity) {
		CountryDetailDto country = new CountryDetailDto();

		country.setId(countryEntity.getId());
		country.setName(countryEntity.getName());

		return country;
	}
}
