package ru.yandex.incoming34.structures.command;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yandex.incoming34.structures.Currencies;

@Getter
@AllArgsConstructor
public class RegisterCourseCommand extends Command {

	private final LocalDateTime commandDateTime;
	private final Currencies currency;
	private final BigDecimal exchangeRate;

	/*
	 * public RegisterCourseCommand(LocalDateTime commandDateTime, UUID commandUuid,
	 * Currencies currency, BigDecimal exchangeRate) { super(commandDateTime,
	 * commandUuid); this.currency = currency; this.exchangeRate = exchangeRate; }
	 */

}
