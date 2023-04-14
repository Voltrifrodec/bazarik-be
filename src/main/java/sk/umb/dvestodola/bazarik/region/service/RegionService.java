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
import sk.umb.dvestodola.bazarik.district.persistence.entity.DistrictEntity;
import sk.umb.dvestodola.bazarik.district.persistence.repository.DistrictRepository;
import sk.umb.dvestodola.bazarik.district.service.DistrictDetailDto;
import sk.umb.dvestodola.bazarik.country.persistence.entity.CountryEntity;
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;

@Service
public class RegionService {

	private final RegionRepository regionRepository;
	private final CountryRepository countryRepository;
	private final DistrictRepository districtRepository;

	RegionService(
		RegionRepository regionRepository,
		CountryRepository countryRepository,
		DistrictRepository districtRepository
	) {
		this.regionRepository = regionRepository;
		this.countryRepository = countryRepository;
		this.districtRepository = districtRepository;
	}

	public List<RegionDetailDto> getAllRegions() {
        return mapToRegionDetailList(regionRepository.findAll());
    }
	
	public List<DistrictDetailDto> getAllDistrictsByRegionById(Long regionId) {
		return mapToDistrictDetailList(districtRepository.findAllByRegionId(regionId));
	}

	public RegionDetailDto getRegionById(Long regionId) {
		return mapToRegionDetail(getRegionEntityById(regionId));
	}

	@Transactional
	public Long createRegion(RegionRequestDto regionRequest) {
		RegionEntity regionEntity = mapToRegionEntity(regionRequest);

		return regionRepository.save(regionEntity).getId();
	}

	@Transactional
	public void updateRegion(Long regionId, RegionRequestDto regionRequest) {
		RegionEntity regionEntity = getRegionEntityById(regionId);
        
		if (! Strings.isEmpty(regionRequest.getName())) {
			regionEntity.setName(regionRequest.getName());
		}

		if (! Objects.isNull(regionEntity.getCountry())) {
			Optional<CountryEntity> countryEntity = countryRepository.findById(regionRequest.getCountryId());

			if (countryEntity.isPresent()) {
				regionEntity.setCountry(countryEntity.get());
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
		Optional<RegionEntity> regionEntity = regionRepository.findById(regionId);

        if (regionEntity.isEmpty()) {
			throw new BazarikApplicationException("Region must have a valid id.");
        }

		return regionEntity.get();
	}

	private RegionEntity mapToRegionEntity(RegionRequestDto regionRequest) {
		RegionEntity regionEntity = new RegionEntity();
		
		regionEntity.setName(regionRequest.getName());

		Optional<CountryEntity> countryEntity = countryRepository.findById(regionRequest.getCountryId());

		if (countryEntity.isPresent()) {
			regionEntity.setCountry(countryEntity.get());
		} else {
			throw new BazarikApplicationException("Country must have a valid id.");
		}

		return regionEntity;
	}

	private List<RegionDetailDto> mapToRegionDetailList(Iterable<RegionEntity> regionsEntities) {
		List<RegionDetailDto> regionEntityList = new ArrayList<>();

		regionsEntities.forEach(regionEntity -> {
			RegionDetailDto regionDetail = mapToRegionDetail(regionEntity);
			regionEntityList.add(regionDetail);
		});

		return regionEntityList;
	}

	private RegionDetailDto mapToRegionDetail(RegionEntity regionEntity) {
		RegionDetailDto regionDetail = new RegionDetailDto();

		regionDetail.setId(regionEntity.getId());
		regionDetail.setName(regionEntity.getName());
		regionDetail.setCountry(mapToCountryDetail(regionEntity.getCountry()));

		return regionDetail;
	}

	private CountryDetailDto mapToCountryDetail(CountryEntity countryEntity) {
		CountryDetailDto regionsDetail = new CountryDetailDto();

		if (Objects.isNull(countryEntity)) {
			throw new BazarikApplicationException("Category is missing!");
		}

		regionsDetail.setId(countryEntity.getId());
		regionsDetail.setName(countryEntity.getName());

		return regionsDetail;
	}

	
	private List<DistrictDetailDto> mapToDistrictDetailList(Iterable<DistrictEntity> districtEntities) {
		ArrayList<DistrictDetailDto> districtDetailList = new ArrayList<>();

		districtEntities.forEach(district -> {
			DistrictDetailDto districtDetail = mapToDistrictDetail(district);
			districtDetailList.add(districtDetail);
		});

		return districtDetailList;
	}

	private DistrictDetailDto mapToDistrictDetail(DistrictEntity regionEntity) {
		DistrictDetailDto region = new DistrictDetailDto();

		region.setId(regionEntity.getId());
		region.setName(regionEntity.getName());
		region.setPostcode(regionEntity.getPostcode());
		region.setRegion(mapToCountryDetail(regionEntity.getRegion()));

		return region;
	}

	private RegionDetailDto mapToCountryDetail(RegionEntity regionEntity) {
		RegionDetailDto regionDetailDto = new RegionDetailDto();

		if (Objects.isNull(regionEntity)) {
			throw new BazarikApplicationException("Category is missing!");
		}

		regionDetailDto.setId(regionEntity.getId());
		regionDetailDto.setName(regionEntity.getName());
		regionDetailDto.setCountry(mapToCountryDetail(regionEntity.getCountry()));

		return regionDetailDto;
	}
}
