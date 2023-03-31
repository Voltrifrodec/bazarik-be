package sk.umb.dvestodola.bazarik.currency.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.apache.logging.log4j.util.Strings;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import sk.umb.dvestodola.bazarik.currency.persistence.entity.CurrencyEntity;
import sk.umb.dvestodola.bazarik.currency.persistence.repository.CurrencyRepository;
import sk.umb.dvestodola.bazarik.exception.LibraryApplicationException;

@Service
public class CurrencyService {
	
	private final CurrencyRepository currencyRepository;

	CurrencyService(CurrencyRepository currencyRepository) {
		this.currencyRepository = currencyRepository;
	}

	public List<CurrencyDetailDto> getAllCurrencies() {
        return mapToCurrencyDetailList(currencyRepository.findAll());
    }

	public CurrencyDetailDto getCurrencyById(Long currencyId) {
		return mapToCurrencyDetail(getCurrencyEntityById(currencyId));
	}

	@Transactional
	public Long createCurrency(CurrencyRequestDto currencyRequest) {
		CurrencyEntity currencyEntity = mapToCurrencyEntity(currencyRequest);

		return currencyRepository.save(currencyEntity).getId();
	}

	@Transactional
	public void updateCurrency(Long currencyId, CurrencyRequestDto currencyRequest) {
		CurrencyEntity currencyEntity = getCurrencyEntityById(currencyId);
        
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
			throw new LibraryApplicationException("Currency with id=1 must not be deleted.");
		}

		currencyRepository.deleteById(currencyId);
	}

	private CurrencyEntity getCurrencyEntityById(Long currencyId) {
		Optional<CurrencyEntity> currency = currencyRepository.findById(currencyId);

		if (currencyId.equals(1L)) {
			throw new LibraryApplicationException("Currency with id=1 must not be modified.");
		}

        if (currency.isEmpty()) {
			throw new LibraryApplicationException("CurrencyId must be valid.");
        }

		return currency.get();
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
			CurrencyDetailDto currencyDetailDto = mapToCurrencyDetail(currencyEntity);
			currencyEntityList.add(currencyDetailDto);
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
