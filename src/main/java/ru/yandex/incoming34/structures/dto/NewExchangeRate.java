package ru.yandex.incoming34.structures.dto;

import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NewExchangeRate {

	@Pattern(regexp = "USD|EUR{3}")
	private String currencyId;
	@Pattern(regexp = "[0-9]{1,}[.][0-9]{4}")
	private String currencyVal;

}
