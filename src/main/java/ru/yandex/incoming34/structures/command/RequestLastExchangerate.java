package ru.yandex.incoming34.structures.command;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yandex.incoming34.structures.Currencies;

@AllArgsConstructor
@Getter
public class RequestLastExchangerate extends Command {

	private final Currencies currency;
	private final UUID commandId;

}
