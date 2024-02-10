package ru.yandex.incoming34.service;

import org.springframework.stereotype.Service;

import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ru.yandex.incoming34.structures.dto.ExchangeRate;
import ru.yandex.incoming34.structures.dto.ExchangeRateWithDate;

import java.util.List;

@Service
public class ValidationService {

	public static final String AVAILABLE_CURRENCIES = "[USD|EUR]{3}";
	public final static String UNSUPPORTED_CURRENCY = "Unsupported currency";
	private final ValidatorFactory factory = jakarta.validation.Validation.buildDefaultValidatorFactory();
	private final Validator validator = factory.getValidator();

	public void throwExceptionIfInvalid(ExchangeRate exchangeRate) {
		if(!validator.validate(exchangeRate).isEmpty()) throw  new RuntimeException(UNSUPPORTED_CURRENCY);
	}

	public void throwExceptionIfInvalid(List<ExchangeRateWithDate> exchangeRateWithDateList) {
		for (ExchangeRateWithDate exchangeRateWithDate : exchangeRateWithDateList) {
			if(!validator.validate(exchangeRateWithDate).isEmpty()) throw  new RuntimeException(UNSUPPORTED_CURRENCY);
		}
	}

}
