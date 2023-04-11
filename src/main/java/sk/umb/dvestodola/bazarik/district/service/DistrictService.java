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
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;
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
        return mapToDistrictDetailList(districtRepository.findAll());
    }

	public DistrictDetailDto getDistrictById(Long regionId) {
		return mapToDistrictDetail(getDistrictEntityById(regionId));
	}

	@Transactional
	public Long createDistrict(DistrictRequestDto districtRequest) {
		DistrictEntity regionEntity = mapToDistrictEntity(districtRequest);

		if (Objects.isNull(regionEntity)) {
			throw new BazarikApplicationException("District must have valid region id.");
		}

		if (Objects.isNull(regionEntity.getRegion())) {
			throw new BazarikApplicationException("District must have valid region id.");
		}

		return districtRepository.save(regionEntity).getId();
	}

	@Transactional
	public void updateDistrict(Long districtId, DistrictRequestDto districtRequest) {
		DistrictEntity districtEntity = getDistrictEntityById(districtId);
        
		if (! Strings.isEmpty(districtRequest.getName())) {
			districtEntity.setName(districtRequest.getName());
		}

		if (! Objects.isNull(districtEntity.getRegion())) {
			Optional<RegionEntity> regionEntity = regionRepository.findById(districtRequest.getRegionId());

			if (regionEntity.isPresent()) {
				districtEntity.setRegion(regionEntity.get());
			} else {
				throw new BazarikApplicationException("Region must have a valid id.");
			}
		}

		if (! Strings.isEmpty(districtRequest.getPostcode())) {
			districtEntity.setPostcode(districtRequest.getPostcode());
		}

        districtRepository.save(districtEntity);
	}

	@Transactional
	public void deleteDistrict(Long regionId) {
		districtRepository.deleteById(regionId);
	}

	private DistrictEntity getDistrictEntityById(Long regionId) {
		Optional<DistrictEntity> districtEntity = districtRepository.findById(regionId);

        if (districtEntity.isEmpty()) {
			throw new BazarikApplicationException("Region must have a valid id.");
        }

		return districtEntity.get();
	}

	private DistrictEntity mapToDistrictEntity(DistrictRequestDto districtRequest) {
		DistrictEntity districtEntity = new DistrictEntity();
		
		districtEntity.setName(districtRequest.getName());
		districtEntity.setPostcode(districtRequest.getPostcode());

		Optional<RegionEntity> regionEntity = regionRepository.findById(districtRequest.getRegionId());
		if (regionEntity.isPresent()) {
			districtEntity.setRegion(regionEntity.get());
		}

		return districtEntity;
	}

	private List<DistrictDetailDto> mapToDistrictDetailList(Iterable<DistrictEntity> regionsEntities) {
		List<DistrictDetailDto> districtEntityList = new ArrayList<>();

		regionsEntities.forEach(regionEntity -> {
			DistrictDetailDto regionDetail = mapToDistrictDetail(regionEntity);
			districtEntityList.add(regionDetail);
		});

		return districtEntityList;
	}

	private DistrictDetailDto mapToDistrictDetail(DistrictEntity districtEntity) {
		DistrictDetailDto districtDetail = new DistrictDetailDto();

		districtDetail.setId(districtEntity.getId());
		districtDetail.setName(districtEntity.getName());
		districtDetail.setPostcode(districtEntity.getPostcode());
		districtDetail.setRegion(mapToRegionDetail(districtEntity.getRegion()));

		return districtDetail;
	}

	private RegionDetailDto mapToRegionDetail(RegionEntity regionEntity) {
		RegionDetailDto regionDetail = new RegionDetailDto();

		if (Objects.isNull(regionEntity)) {
			throw new BazarikApplicationException("Category must be valid.");
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
}
