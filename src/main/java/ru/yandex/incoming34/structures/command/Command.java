package ru.yandex.incoming34.structures.command;

import java.time.LocalDateTime;
import java.util.UUID;

public abstract class Command {

	private final LocalDateTime commandDateTime;
	private final UUID commandUuid;

	public Command(LocalDateTime commandDateTime, UUID commandUuid) {
		this.commandDateTime = commandDateTime;
		this.commandUuid = commandUuid;
	}

	public LocalDateTime getCommandDateTime() {
		return commandDateTime;
	}

	public UUID getCommandUuid() {
		return commandUuid;
	}

}
