package ru.yandex.incoming34.service;

import org.springframework.stereotype.Service;
import ru.yandex.incoming34.structures.Currencies;
import ru.yandex.incoming34.structures.dto.ExchangeRateWithDate;

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

    public void addNewExchangeRate(ExchangeRateWithDate registerCourseCommand) {
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

    public void addNewBulkExchangeRate(List<ExchangeRateWithDate> newBulkExchangeRateList) {
        for (ExchangeRateWithDate exchangeRateWithDate : newBulkExchangeRateList) {
            addNewExchangeRate(exchangeRateWithDate);
        }
    }

    public List<ExchangeRateWithDate> getFiveCourseMax(Currencies currency) {
        return (Objects.nonNull(exchangeRates.get(currency))) ?
                exchangeRates.get(currency).entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toList())
                        .stream()
                        .limit(5)
                        .collect(Collectors.toList())
                        .stream()
                        .map(localDateTimeBigDecimalEntry ->
                                new ExchangeRateWithDate(
                                        currency.name(),
                                        localDateTimeBigDecimalEntry.getValue(),
                                        localDateTimeBigDecimalEntry.getKey()
                                )).collect(Collectors.toList()) : Collections.EMPTY_LIST;
    }

}
