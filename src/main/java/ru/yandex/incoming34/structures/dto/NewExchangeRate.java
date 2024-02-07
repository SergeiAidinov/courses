package ru.yandex.incoming34.structures.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;

@Getter
public class NewExchangeRate {

	@Pattern(regexp = "USD|EUR{3}")
	private final String currencyId;
	private final BigDecimal currencyVal;

	public NewExchangeRate(@Pattern(regexp = "USD|EUR{3}") String currencyId, BigDecimal currencyVal) {
		this.currencyId = currencyId;
		this.currencyVal = currencyVal.setScale(4, RoundingMode.DOWN);
	}

}
