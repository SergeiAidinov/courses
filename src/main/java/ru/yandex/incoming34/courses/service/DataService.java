package ru.yandex.incoming34.courses.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;
import lombok.AccessLevel;
import lombok.Getter;
import ru.yandex.incoming34.structures.Currencies;
import ru.yandex.incoming34.structures.command.Command;
import ru.yandex.incoming34.structures.command.RegisterCourseCommand;
import ru.yandex.incoming34.structures.command.RequestLastExchangerate;

@Service
@Getter
public class DataService {

    private final int MAX_REPO_SIZE = 5;
    private final Logger LOGGER = Logger.getLogger(DataService.class.getSimpleName());

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
        LOGGER.info(exchangeRates.toString());
    }

    public Optional<Entry<LocalDateTime, BigDecimal>> getLastExchangerate(RequestLastExchangerate requestLastExchangerate) {
        if (Objects.nonNull(exchangeRates.get(requestLastExchangerate.getCurrency())) &&
                Objects.nonNull(exchangeRates.get(requestLastExchangerate.getCurrency()).lastEntry())) {
            return Optional.of(exchangeRates.get(requestLastExchangerate.getCurrency()).lastEntry());
        } else {
            return Optional.empty();
        }
    }

}
