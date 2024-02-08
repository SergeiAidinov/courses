package ru.yandex.incoming34.structures.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NewExchangeRate {

	@Pattern(regexp = "[USD|EUR]{3}")
	@Size(max = 3, min = 3)
	private final String currencyId;
	private final BigDecimal currencyVal;

}
