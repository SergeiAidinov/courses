package ru.yandex.incoming34.courses.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;

import org.springframework.stereotype.Service;

import ru.yandex.incoming34.structures.Currencies;
import ru.yandex.incoming34.structures.command.Command;

@Service
public class DataService {

	private final Map<Currencies, TreeMap<LocalDateTime, Integer>> exchangeRates = new HashMap<Currencies, TreeMap<LocalDateTime, Integer>>();
	private final Queue<Command> commands = new LinkedList<>();

}
