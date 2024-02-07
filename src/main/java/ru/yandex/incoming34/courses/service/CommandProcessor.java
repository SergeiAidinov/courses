package ru.yandex.incoming34.courses.service;

import java.util.Queue;

import lombok.AllArgsConstructor;
import ru.yandex.incoming34.structures.command.Command;

@AllArgsConstructor
public class CommandProcessor implements Runnable {

	private final Queue<Command> commands;

	@Override
	public void run() {
		processCommand();

	}

	private void processCommand() {
		while (true) {
			if (!commands.isEmpty()) {
				System.out.println(commands.poll());
			}
		}
	}

}
