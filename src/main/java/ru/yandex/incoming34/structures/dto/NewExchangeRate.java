package ru.yandex.incoming34.structures.dto;

import java.math.BigDecimal;
import java.math.RoundingMode;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class NewExchangeRate {

	@Pattern(regexp = "[USD|EUR]{3}")
	@Size(max = 3, min = 3)
	private final String currencyId;
	private final BigDecimal currencyVal;

	public NewExchangeRate(String currencyId, BigDecimal currencyVal) {
		this.currencyId = currencyId;
		this.currencyVal = currencyVal.setScale(4, RoundingMode.DOWN);
	}

}
