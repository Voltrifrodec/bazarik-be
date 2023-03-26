package sk.umb.dvestodola.bazarik.district.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.country.persistence.entity.CountryEntity;
import sk.umb.dvestodola.bazarik.country.service.CountryDetailDto;
import sk.umb.dvestodola.bazarik.district.persistence.entity.DistrictEntity;
import sk.umb.dvestodola.bazarik.district.persistence.repository.DistrictRepository;
import sk.umb.dvestodola.bazarik.exception.LibraryApplicationException;
import sk.umb.dvestodola.bazarik.region.persistence.entity.RegionEntity;
import sk.umb.dvestodola.bazarik.region.persistence.repository.RegionRepository;
import sk.umb.dvestodola.bazarik.region.service.RegionDetailDto;

@Service
public class DistrictService {
	
	private final DistrictRepository districtRepository;
	private final RegionRepository regionRepository;

	DistrictService(DistrictRepository districtRepository, RegionRepository regionRepository) {
		this.districtRepository = districtRepository;
		this.regionRepository = regionRepository;
	}

	public List<DistrictDetailDto> getAllDistricts() {
        return mapToDistrictDtoList(districtRepository.findAll());
    }

	public DistrictDetailDto getDistrictById(Long regionId) {
		return mapToDistrictDetailDto(getDistrictEntityById(regionId));
	}

	@Transactional
	public Long createDistrict(DistrictRequestDto districtRequestDto) {
		DistrictEntity regionEntity = mapToDistrictEntity(districtRequestDto);

		if (Objects.isNull(regionEntity)) {
			throw new LibraryApplicationException("District must have valid country id.");
		}

		if (Objects.isNull(regionEntity.getRegion())) {
			throw new LibraryApplicationException("District must have valid country id.");
		}

		return districtRepository.save(regionEntity).getId();
	}

	@Transactional
	public void updateDistrict(Long districtId, DistrictRequestDto districtRequestDto) {
		DistrictEntity regionEntity = getDistrictEntityById(districtId);
        
		if (! Strings.isEmpty(districtRequestDto.getName())) {
			regionEntity.setName(districtRequestDto.getName());
		}

		if (! Objects.isNull(regionEntity.getRegion())) {
			Optional<RegionEntity> districtEntity = regionRepository.findById(districtRequestDto.getRegionId());
			if (districtEntity.isPresent()) {
				regionEntity.setRegion(districtEntity.get());
			} else {
				throw new LibraryApplicationException("Region must have a valid id.");
			}
		}

		if (! Strings.isEmpty(districtRequestDto.getPostcode())) {
			regionEntity.setPostcode(districtRequestDto.getPostcode());
		}

        districtRepository.save(regionEntity);
	}

	@Transactional
	public void deleteDistrict(Long regionId) {
		districtRepository.deleteById(regionId);
	}

	private DistrictEntity getDistrictEntityById(Long regionId) {
		Optional<DistrictEntity> region = districtRepository.findById(regionId);

        if (region.isEmpty()) {
			throw new LibraryApplicationException("Region must have a valid id.");
        }

		return region.get();
	}

	private DistrictEntity mapToDistrictEntity(DistrictRequestDto districtRequestDto) {
		DistrictEntity districtEntity = new DistrictEntity();
		
		districtEntity.setName(districtRequestDto.getName());
		districtEntity.setPostcode(districtRequestDto.getPostcode());

		Optional<RegionEntity> regionEntity = regionRepository.findById(districtRequestDto.getRegionId());
		if (regionEntity.isPresent()) {
			districtEntity.setRegion(regionEntity.get());
		}

		return districtEntity;
	}

	private List<DistrictDetailDto> mapToDistrictDtoList(Iterable<DistrictEntity> regionsEntities) {
		List<DistrictDetailDto> regionEntityList = new ArrayList<>();

		regionsEntities.forEach(region -> {
			DistrictDetailDto regionDetailDto = mapToDistrictDetailDto(region);
			regionEntityList.add(regionDetailDto);
		});

		return regionEntityList;
	}

	private DistrictDetailDto mapToDistrictDetailDto(DistrictEntity regionEntity) {
		DistrictDetailDto region = new DistrictDetailDto();

		region.setId(regionEntity.getId());
		region.setName(regionEntity.getName());
		region.setPostcode(regionEntity.getPostcode());
		region.setRegion(mapToCountryDetailDto(regionEntity.getRegion()));

		return region;
	}

	private RegionDetailDto mapToCountryDetailDto(RegionEntity regionEntity) {
		RegionDetailDto regionDetailDto = new RegionDetailDto();

		if (Objects.isNull(regionEntity)) {
			throw new LibraryApplicationException("Category is missing!");
		}

		regionDetailDto.setId(regionEntity.getId());
		regionDetailDto.setName(regionEntity.getName());
		regionDetailDto.setCountry(mapToCountryDetailDto(regionEntity.getCountry()));

		return regionDetailDto;
	}

	private CountryDetailDto mapToCountryDetailDto(CountryEntity countryEntity) {
		CountryDetailDto country = new CountryDetailDto();
		
		country.setId(countryEntity.getId());
		country.setName(countryEntity.getName());

		return country;
	}
}
