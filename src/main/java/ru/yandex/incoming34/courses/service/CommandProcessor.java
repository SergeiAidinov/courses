package ru.yandex.incoming34.courses.service;

import java.util.Queue;

import lombok.AllArgsConstructor;
import ru.yandex.incoming34.structures.command.Command;
import ru.yandex.incoming34.structures.command.RegisterCourseCommand;

@AllArgsConstructor
public class CommandProcessor implements Runnable {

	private final Queue<Command> commands;
	private final DataService dataService;

	@Override
	public void run() {
		processCommand();

	}

	private void processCommand() {
		while (true) {
			if (!commands.isEmpty()) {
				Command command = commands.poll();
				System.out.println(command);
				if (command.getClass().equals(ru.yandex.incoming34.structures.command.RegisterCourseCommand.class)) {
					addNewExchangeRate(command);
				}
			}
		}
	}

	private void addNewExchangeRate(Command command) {
		dataService.addNewExchangeRate((RegisterCourseCommand) command);

	}

}
