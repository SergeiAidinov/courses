package ru.yandex.incoming34.service;

import org.springframework.stereotype.Service;
import ru.yandex.incoming34.structures.Currencies;
import ru.yandex.incoming34.structures.dto.NewExchangeRateWithDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import static java.util.Map.Entry.comparingByValue;

@Service
public class DataService {

    private final int MAX_REPO_SIZE = 5;
    private final Logger LOGGER = Logger.getLogger(DataService.class.getSimpleName());

    private final Map<Currencies, TreeMap<LocalDateTime, BigDecimal>> exchangeRates = new HashMap<Currencies, TreeMap<LocalDateTime, BigDecimal>>();

    public void addNewExchangeRate(NewExchangeRateWithDate registerCourseCommand) {
        if (Objects.isNull(exchangeRates.get(Currencies.valueOf(registerCourseCommand.getCurrencyId())))) {
            exchangeRates.put(Currencies.valueOf(registerCourseCommand.getCurrencyId()), new TreeMap<LocalDateTime, BigDecimal>());
        }
        exchangeRates.get(Currencies.valueOf(registerCourseCommand.getCurrencyId())).put(registerCourseCommand.getRegTime(),
                registerCourseCommand.getCurrencyVal());
        if (exchangeRates.get(Currencies.valueOf(registerCourseCommand.getCurrencyId())).size() > MAX_REPO_SIZE) {
            exchangeRates.get(Currencies.valueOf(registerCourseCommand.getCurrencyId())).pollFirstEntry();
        }
        System.out.println(exchangeRates);
        LOGGER.info(exchangeRates.toString());
    }

    public Optional<Entry<LocalDateTime, BigDecimal>> getLastExchangeRate(Currencies currency) {
        return (Objects.nonNull(exchangeRates.get(currency)) &&
                Objects.nonNull(exchangeRates.get(currency).lastEntry())) ? Optional.of(exchangeRates.get(currency).lastEntry()) : Optional.empty();
    }

    public void addNewBulkExchangeRate(List<NewExchangeRateWithDate> newBulkExchangeRateList) {
        for (NewExchangeRateWithDate newExchangeRateWithDate : newBulkExchangeRateList) {
            addNewExchangeRate(newExchangeRateWithDate);
        }
    }

    public List<NewExchangeRateWithDate> getFiveCourseMax(Currencies currency) {
        if (Objects.nonNull(exchangeRates.get(currency))) {
             return exchangeRates.get(currency).entrySet().stream()
                    .sorted(Map.Entry.comparingByValue())
                    .collect(Collectors.toList())
                    .stream()
                    .limit(5)
                    .collect(Collectors.toList())
                    .stream()
                    .map(localDateTimeBigDecimalEntry -> new NewExchangeRateWithDate(currency.name(), localDateTimeBigDecimalEntry.getValue(), localDateTimeBigDecimalEntry.getKey()))
                    .collect(Collectors.toList());
        }
        return Collections.EMPTY_LIST;
    }

}
