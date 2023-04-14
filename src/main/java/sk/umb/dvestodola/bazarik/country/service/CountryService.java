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
import sk.umb.dvestodola.bazarik.region.persistence.entity.RegionEntity;
import sk.umb.dvestodola.bazarik.region.persistence.repository.RegionRepository;
import sk.umb.dvestodola.bazarik.region.service.RegionDetailDto;

@Service
public class CountryService {
	
	private final CountryRepository countryRepository;
	private final RegionRepository regionRepository;

	CountryService(CountryRepository countryRepository, RegionRepository regionRepository) {
		this.countryRepository = countryRepository;
		this.regionRepository = regionRepository;
	}

	public List<CountryDetailDto> getAllCountries() {
        return mapToCountryDetailList(countryRepository.findAll());
    }

	public List<RegionDetailDto> getAllRegionsByCountryId(Long countryId) {
		return mapToRegionDetailList(regionRepository.findAllRegionsByCountryId(countryId));
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
		List<CountryDetailDto> countryDetailList = new ArrayList<>();

		countryEntities.forEach(countryEntity -> {
			CountryDetailDto countryDetail = mapToCountryDetail(countryEntity);
			countryDetailList.add(countryDetail);
		});

		return countryDetailList;
	}

	private CountryDetailDto mapToCountryDetail(CountryEntity countryEntity) {
		CountryDetailDto countryDetail = new CountryDetailDto();

		countryDetail.setId(countryEntity.getId());
		countryDetail.setName(countryEntity.getName());

		return countryDetail;
	}

	private List<RegionDetailDto> mapToRegionDetailList(Iterable<RegionEntity> regionEntityList) {
		List<RegionDetailDto> regionDetailList = new ArrayList<>();

		regionEntityList.forEach(regionEntity -> {
			RegionDetailDto regionDetail = mapToRegionDetail(regionEntity);
			regionDetailList.add(regionDetail);
		});

		return regionDetailList;
	}

	private RegionDetailDto mapToRegionDetail(RegionEntity regionEntity) {
		RegionDetailDto regionDetail = new RegionDetailDto();

		regionDetail.setId(regionEntity.getId());
		regionDetail.setName(regionEntity.getName());
		regionDetail.setCountry(mapToCountryDetail(regionEntity.getCountry()));

		return regionDetail;
	}
}
