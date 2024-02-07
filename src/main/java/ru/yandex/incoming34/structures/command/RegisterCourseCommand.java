package ru.yandex.incoming34.structures.command;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import ru.yandex.incoming34.structures.Currencies;

public class RegisterCourseCommand extends Command {

	private final Currencies currency;
	private final BigDecimal exchangeRate;

	public RegisterCourseCommand(LocalDateTime commandDateTime, UUID commandUuid, Currencies currency,
			BigDecimal exchangeRate) {
		super(commandDateTime, commandUuid);
		this.currency = currency;
		this.exchangeRate = exchangeRate;
	}

}
