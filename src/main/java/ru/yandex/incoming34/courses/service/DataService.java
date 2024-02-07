package ru.yandex.incoming34.courses.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import ru.yandex.incoming34.structures.Currencies;
import ru.yandex.incoming34.structures.command.Command;

@Service
@Getter
public class DataService {

	@Getter(value = AccessLevel.NONE)
	private final Map<Currencies, TreeMap<LocalDateTime, BigDecimal>> exchangeRates = new HashMap<Currencies, TreeMap<LocalDateTime, BigDecimal>>();
	private final Queue<Command> commands = new LinkedList<>();

	@PostConstruct
	private void startCommandProcessing() {
		new Thread(new CommandProcessor(commands)).start();
	}

}
