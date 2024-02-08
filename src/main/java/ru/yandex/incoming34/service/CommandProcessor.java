package ru.yandex.incoming34.service;

/*

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
				if (command.getClass().equals(ru.yandex.incoming34.structures.dto.CoursesResponce.RegisterCourseCommand.class)) {
					addNewExchangeRate(command);
				}
			}
		}
	}

	private void addNewExchangeRate(Command command) {
		dataService.addNewExchangeRate((RegisterCourseCommand) command);

	}

}
*/
