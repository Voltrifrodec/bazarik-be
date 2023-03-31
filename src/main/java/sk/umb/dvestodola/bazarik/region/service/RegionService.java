package sk.umb.dvestodola.bazarik.region.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.region.persistence.entity.RegionEntity;
import sk.umb.dvestodola.bazarik.region.persistence.repository.RegionRepository;
import sk.umb.dvestodola.bazarik.country.persistence.repository.CountryRepository;
import sk.umb.dvestodola.bazarik.country.service.CountryDetailDto;
import sk.umb.dvestodola.bazarik.country.persistence.entity.CountryEntity;
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;

@Service
public class RegionService {

	private final RegionRepository regionRepository;
	private final CountryRepository countryRepository;

	RegionService(RegionRepository regionRepository, CountryRepository countryRepository) {
		this.regionRepository = regionRepository;
		this.countryRepository = countryRepository;
	}

	public List<RegionDetailDto> getAllSubcategories() {
        return mapToRegionDtoList(regionRepository.findAll());
    }

	public RegionDetailDto getRegionById(Long regionId) {
		return mapToRegionDetailDto(getRegionEntityById(regionId));
	}

	@Transactional
	public Long createRegion(RegionRequestDto regionRequestDto) {
		RegionEntity regionEntity = mapToRegionEntity(regionRequestDto);

		if (Objects.isNull(regionEntity)) {
			throw new BazarikApplicationException("Region must have valid country id.");
		}

		if (Objects.isNull(regionEntity.getCountry())) {
			throw new BazarikApplicationException("Region must have valid country id.");
		}

		return regionRepository.save(regionEntity).getId();
	}

	@Transactional
	public void updateRegion(Long regionId, RegionRequestDto regionRequestDto) {
		RegionEntity regionEntity = getRegionEntityById(regionId);
        
		if (! Strings.isEmpty(regionRequestDto.getName())) {
			regionEntity.setName(regionRequestDto.getName());
		}

		if (! Objects.isNull(regionEntity.getCountry())) {
			Optional<CountryEntity> regionsEntity = countryRepository.findById(regionRequestDto.getCountryId());
			if (regionsEntity.isPresent()) {
				regionEntity.setCountry(regionsEntity.get());
			} else {
				throw new BazarikApplicationException("Region must have a valid id.");
			}
		}

        regionRepository.save(regionEntity);
	}

	@Transactional
	public void deleteRegion(Long regionId) {
		regionRepository.deleteById(regionId);
	}

	private RegionEntity getRegionEntityById(Long regionId) {
		Optional<RegionEntity> region = regionRepository.findById(regionId);

        if (region.isEmpty()) {
			throw new BazarikApplicationException("Region must have a valid id.");
        }

		return region.get();
	}

	private RegionEntity mapToRegionEntity(RegionRequestDto regionRequestDto) {
		RegionEntity regionEntity = new RegionEntity();
		
		regionEntity.setName(regionRequestDto.getName());

		Optional<CountryEntity> countryEntity = countryRepository.findById(regionRequestDto.getCountryId());
		if (countryEntity.isPresent()) {
			regionEntity.setCountry(countryEntity.get());
		}

		return regionEntity;
	}

	private List<RegionDetailDto> mapToRegionDtoList(Iterable<RegionEntity> regionsEntities) {
		List<RegionDetailDto> regionEntityList = new ArrayList<>();

		regionsEntities.forEach(region -> {
			RegionDetailDto regionDetailDto = mapToRegionDetailDto(region);
			regionEntityList.add(regionDetailDto);
		});

		return regionEntityList;
	}

	private RegionDetailDto mapToRegionDetailDto(RegionEntity regionEntity) {
		RegionDetailDto region = new RegionDetailDto();

		region.setId(regionEntity.getId());
		region.setName(regionEntity.getName());
		region.setCountry(mapToCountryDetailDto(regionEntity.getCountry()));

		return region;
	}

	private CountryDetailDto mapToCountryDetailDto(CountryEntity countryEntity) {
		CountryDetailDto regionsDetailDto = new CountryDetailDto();

		if (Objects.isNull(countryEntity)) {
			throw new BazarikApplicationException("Category is missing!");
		}

		regionsDetailDto.setId(countryEntity.getId());
		regionsDetailDto.setName(countryEntity.getName());

		return regionsDetailDto;
	}
}
