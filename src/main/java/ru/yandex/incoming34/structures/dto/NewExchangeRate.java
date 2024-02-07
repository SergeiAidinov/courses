package ru.yandex.incoming34.structures.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yandex.incoming34.structures.Currencies;

@AllArgsConstructor
@Getter
public class NewExchangeRate {

	private final Currencies currencyId;
	private final String currency;

}
