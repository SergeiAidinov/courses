package ru.yandex.incoming34.structures.dto;

import java.math.BigDecimal;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yandex.incoming34.structures.RegExpPatterns;

@Getter
@AllArgsConstructor
public class NewExchangeRate {

	@Pattern(regexp = RegExpPatterns.AVAILABLE_CURRENCIES)
	private final String currencyId;
	private final BigDecimal currencyVal;

}
