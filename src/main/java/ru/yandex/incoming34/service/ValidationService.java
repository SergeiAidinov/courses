package ru.yandex.incoming34.service;

import org.springframework.stereotype.Service;

import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import ru.yandex.incoming34.structures.dto.ExchangeRate;

@Service
public class ValidationService {

	private final ValidatorFactory factory = jakarta.validation.Validation.buildDefaultValidatorFactory();
	private final Validator validator = factory.getValidator();

	public boolean validate(ExchangeRate exchangeRate) {
		return validator.validate(exchangeRate).isEmpty();
	}

}
