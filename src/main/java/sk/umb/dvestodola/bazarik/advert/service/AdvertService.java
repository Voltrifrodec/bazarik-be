package sk.umb.dvestodola.bazarik.advert.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.advert.persistence.repository.AdvertRepository;
import sk.umb.dvestodola.bazarik.exception.LibraryApplicationException;

@Service
public class AdvertService {

	private final AdvertRepository advertRepository;

	AdvertService(AdvertRepository advertRepository) {
		this.advertRepository = advertRepository;
	}

	public List<AdvertDetailDto> getAllAdverts() {
        return mapToRegionDtoList(regionRepository.findAll());
    }

	public AdvertDetailDto getAdvertById(Long regionId) {
		return mapToRegionDetailDto(getRegionEntityById(regionId));
	}

	@Transactional
	public Long createAdvert(AdvertRequestDto regionRequestDto) {
		RegionEntity regionEntity = mapToRegionEntity(regionRequestDto);

		if (Objects.isNull(regionEntity)) {
			throw new LibraryApplicationException("Region must have valid country id.");
		}

		if (Objects.isNull(regionEntity.getCountry())) {
			throw new LibraryApplicationException("Region must have valid country id.");
		}

		return regionRepository.save(regionEntity).getId();
	}

	@Transactional
	public void updateAdvert(Long regionId, AdvertRequestDto regionRequestDto) {
		RegionEntity regionEntity = getRegionEntityById(regionId);
        
		if (! Strings.isEmpty(regionRequestDto.getName())) {
			regionEntity.setName(regionRequestDto.getName());
		}

		// TODO: Check for every attribute
		
		if (! Objects.isNull(regionEntity.getCountry())) {
			/* Optional<CountryEntity> regionsEntity = countryRepository.findById(regionRequestDto.getCountryId());
			if (regionsEntity.isPresent()) {
				regionEntity.setCountry(regionsEntity.get());
			} else {
				throw new LibraryApplicationException("Region must have a valid id.");
			} */
		}

        regionRepository.save(regionEntity);
	}

	@Transactional
	public void deleteAdvert(Long regionId) {
		regionRepository.deleteById(regionId);
	}

	private RegionEntity getRegionEntityById(Long regionId) {
		Optional<RegionEntity> region = regionRepository.findById(regionId);

        if (region.isEmpty()) {
			throw new LibraryApplicationException("Region must have a valid id.");
        }

		return region.get();
	}

	private RegionEntity mapToRegionEntity(AdvertRequestDto regionRequestDto) {
		RegionEntity regionEntity = new RegionEntity();
		
		regionEntity.setName(regionRequestDto.getName());

		/*  Optional<CountryEntity> countryEntity = countryRepository.findById(regionRequestDto.getCountryId());
		if (countryEntity.isPresent()) {
			regionEntity.setCountry(countryEntity.get());
		} */

		return regionEntity;
	}

	private List<AdvertDetailDto> mapToRegionDtoList(Iterable<RegionEntity> regionsEntities) {
		List<AdvertDetailDto> regionEntityList = new ArrayList<>();

		regionsEntities.forEach(region -> {
			AdvertDetailDto regionDetailDto = mapToRegionDetailDto(region);
			regionEntityList.add(regionDetailDto);
		});

		return regionEntityList;
	}

	private AdvertDetailDto mapToRegionDetailDto(RegionEntity regionEntity) {
		AdvertDetailDto region = new AdvertDetailDto();

		region.setId(regionEntity.getId());
		region.setName(regionEntity.getName());
		// region.setCountry(mapToCountryDetailDto(regionEntity.getCountry()));

		return region;
	}

	private CountryDetailDto mapToCountryDetailDto(CountryEntity countryEntity) {
		CountryDetailDto regionsDetailDto = new CountryDetailDto();

		if (Objects.isNull(countryEntity)) {
			throw new LibraryApplicationException("Category is missing!");
		}

		regionsDetailDto.setId(countryEntity.getId());
		regionsDetailDto.setName(countryEntity.getName());

		return regionsDetailDto;
	}
}
