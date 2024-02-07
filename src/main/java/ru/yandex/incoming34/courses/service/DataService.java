package ru.yandex.incoming34.courses.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Objects;
import java.util.Queue;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import ru.yandex.incoming34.structures.Currencies;
import ru.yandex.incoming34.structures.command.Command;
import ru.yandex.incoming34.structures.command.RegisterCourseCommand;

@Service
@Getter
public class DataService {

	private final int MAX_REPO_SIZE = 5;

	@Getter(value = AccessLevel.NONE)
	private final Map<Currencies, TreeMap<LocalDateTime, BigDecimal>> exchangeRates = new HashMap<Currencies, TreeMap<LocalDateTime, BigDecimal>>();
	private final Queue<Command> commands = new LinkedList<>();

	@PostConstruct
	private void startCommandProcessing() {
		new Thread(new CommandProcessor(commands, this)).start();
	}

	public void addNewExchangeRate(RegisterCourseCommand registerCourseCommand) {
		if (Objects.isNull(exchangeRates.get(registerCourseCommand.getCurrency())))
			exchangeRates.put(registerCourseCommand.getCurrency(), new TreeMap<LocalDateTime, BigDecimal>());
		exchangeRates.get(registerCourseCommand.getCurrency()).put(registerCourseCommand.getCommandDateTime(),
				registerCourseCommand.getExchangeRate());
		if (exchangeRates.get(registerCourseCommand.getCurrency()).size() > MAX_REPO_SIZE) {
			exchangeRates.get(registerCourseCommand.getCurrency()).pollFirstEntry();
		}
		System.out.println(exchangeRates);
	}

}
