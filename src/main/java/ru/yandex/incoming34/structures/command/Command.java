package ru.yandex.incoming34.structures.command;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Getter;

//@AllArgsConstructor
@Getter
public abstract class Command {

	private final LocalDateTime commandDateTime;
	private final UUID commandUuid;

	public Command(LocalDateTime commandDateTime, UUID commandUuid) {
		this.commandDateTime = commandDateTime;
		this.commandUuid = commandUuid;
	}

}
