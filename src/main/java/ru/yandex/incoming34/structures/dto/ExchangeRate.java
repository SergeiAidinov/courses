package ru.yandex.incoming34.structures.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static ru.yandex.incoming34.service.ValidationService.AVAILABLE_CURRENCIES;

@Getter
@AllArgsConstructor
public class ExchangeRate {

	@Pattern(regexp = AVAILABLE_CURRENCIES)
	private final String currencyId;
	private final BigDecimal currencyVal;

}
