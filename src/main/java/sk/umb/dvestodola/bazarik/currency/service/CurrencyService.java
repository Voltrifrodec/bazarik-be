package sk.umb.dvestodola.bazarik.currency.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.currency.persistence.entity.CurrencyEntity;
import sk.umb.dvestodola.bazarik.currency.persistence.repository.CurrencyRepository;
import sk.umb.dvestodola.bazarik.exception.BazarikApplicationException;

@Service
public class CurrencyService {
	
	private final CurrencyRepository currencyRepository;

	CurrencyService(CurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
	}

	@Cacheable(value = "currencies")
	public List<CurrencyDetailDto> getAllCurrencies() {
		System.out.println("Ťahá currencies zo service metódy.");
    return mapToCurrencyDetailList(currencyRepository.findAll());
  }

	public CurrencyDetailDto getCurrencyById(Long currencyId) {
		return mapToCurrencyDetail(getCurrencyEntityById(currencyId));
	}

	@Transactional
	public Long createCurrency(CurrencyRequestDto currencyRequest) {
		CurrencyEntity currencyEntity = mapToCurrencyEntity(currencyRequest);

		if (Objects.isNull(currencyEntity)) {
			throw new BazarikApplicationException("Currency must be valid.");
		}

		return currencyRepository.save(currencyEntity).getId();
	}

	@Transactional
	public void updateCurrency(Long currencyId, CurrencyRequestDto currencyRequest) {
		CurrencyEntity currencyEntity = getCurrencyEntityById(currencyId);

		if (currencyId == 1 && !currencyRequest.getSymbol().equals("€")) {
			throw new BazarikApplicationException("Currency with id=1 must not be updated.");
		}
        
		if (! Strings.isBlank(currencyRequest.getName())) {
			currencyEntity.setName(currencyRequest.getName());
		}

		if (! Strings.isBlank(currencyRequest.getSymbol())) {
			currencyEntity.setSymbol(currencyRequest.getSymbol());
		}

        currencyRepository.save(currencyEntity);
	}

	@Transactional
	public void deleteCurrency(Long currencyId) {
		if (currencyId.equals(1L)) {
			throw new BazarikApplicationException("Currency with id=1 must not be deleted.");
		}

		currencyRepository.deleteById(currencyId);
	}


	private CurrencyEntity getCurrencyEntityById(Long currencyId) {
		Optional<CurrencyEntity> currencyEntity = currencyRepository.findById(currencyId);

        if (currencyEntity.isEmpty()) {
			throw new BazarikApplicationException("CurrencyId must be valid.");
        }

		return currencyEntity.get();
	}

	private CurrencyEntity mapToCurrencyEntity(CurrencyRequestDto currencyRequest) {
		CurrencyEntity currencyEntity = new CurrencyEntity();
		
		currencyEntity.setName(currencyRequest.getName());
		currencyEntity.setSymbol(currencyRequest.getSymbol());

		return currencyEntity;
	}

	private List<CurrencyDetailDto> mapToCurrencyDetailList(Iterable<CurrencyEntity> currencysEntities) {
		List<CurrencyDetailDto> currencyEntityList = new ArrayList<>();

		currencysEntities.forEach(currencyEntity -> {
			CurrencyDetailDto currencyDetail = mapToCurrencyDetail(currencyEntity);
			currencyEntityList.add(currencyDetail);
		});

		return currencyEntityList;
	}

	private CurrencyDetailDto mapToCurrencyDetail(CurrencyEntity currencyEntity) {
		CurrencyDetailDto currencyDetail = new CurrencyDetailDto();

		currencyDetail.setId(currencyEntity.getId());
		currencyDetail.setName(currencyEntity.getName());
		currencyDetail.setSymbol(currencyEntity.getSymbol());

		return currencyDetail;
	}
}
