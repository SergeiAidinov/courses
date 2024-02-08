package ru.yandex.incoming34.service;

import org.springframework.stereotype.Service;
import ru.yandex.incoming34.structures.Currencies;
import ru.yandex.incoming34.structures.dto.ExchangeRateWithDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

@Service
public class DataService {

    private final int MAX_REPO_SIZE = 5;
    private final Map<Currencies, TreeMap<LocalDateTime, BigDecimal>> exchangeRates = new HashMap<Currencies, TreeMap<LocalDateTime, BigDecimal>>();

    public void addExchangeRate(ExchangeRateWithDate registerCourseCommand) {
        if (Objects.isNull(exchangeRates.get(Currencies.valueOf(registerCourseCommand.getCurrencyId())))) {
            exchangeRates.put(Currencies.valueOf(registerCourseCommand.getCurrencyId()), new TreeMap<LocalDateTime, BigDecimal>());
        }
        exchangeRates.get(Currencies.valueOf(registerCourseCommand.getCurrencyId())).put(registerCourseCommand.getRegTime(),
                registerCourseCommand.getCurrencyVal());
        if (exchangeRates.get(Currencies.valueOf(registerCourseCommand.getCurrencyId())).size() > MAX_REPO_SIZE) {
            exchangeRates.get(Currencies.valueOf(registerCourseCommand.getCurrencyId())).pollFirstEntry();
        }
    }

    public Optional<Entry<LocalDateTime, BigDecimal>> getLastExchangeRate(Currencies currency) {
        return (Objects.nonNull(exchangeRates.get(currency)) &&
                Objects.nonNull(exchangeRates.get(currency).lastEntry())) ? Optional.of(exchangeRates.get(currency).lastEntry()) : Optional.empty();
    }

    public void addNewBulkExchangeRate(List<ExchangeRateWithDate> newBulkExchangeRateList) {
        for (ExchangeRateWithDate exchangeRateWithDate : newBulkExchangeRateList) {
            addExchangeRate(exchangeRateWithDate);
        }
    }

    public List<ExchangeRateWithDate> getFiveMaxCourses(Currencies currencyId) {
        return (Objects.nonNull(exchangeRates.get(currencyId))) ?
                exchangeRates.get(currencyId).entrySet().stream()
                        .sorted(Map.Entry.comparingByValue())
                        .collect(Collectors.toList())
                        .stream()
                        .limit(5)
                        .collect(Collectors.toList())
                        .stream()
                        .map(localDateTimeBigDecimalEntry ->
                                new ExchangeRateWithDate(
                                        currencyId.name(),
                                        localDateTimeBigDecimalEntry.getValue(),
                                        localDateTimeBigDecimalEntry.getKey()
                                )
                        ).collect(Collectors.toList()) : Collections.EMPTY_LIST;
    }

    public List<ExchangeRateWithDate> getThreeCourseExtremum(Currencies currencyId) {
        List<Map.Entry<LocalDateTime, BigDecimal>> peakList = new ArrayList<>();
       if (Objects.nonNull(exchangeRates.get(currencyId)) && exchangeRates.get(currencyId).entrySet().size() >= 3) {
          final List<Entry<LocalDateTime, BigDecimal>> entryList =  exchangeRates.get(currencyId).entrySet().stream().toList();
          for (int i = 1; i < entryList.size() - 1; i++){
              Entry<LocalDateTime, BigDecimal> previousEntry = entryList.get(i - 1);
              Entry<LocalDateTime, BigDecimal> currentEntry = entryList.get(i);
              Entry<LocalDateTime, BigDecimal> nextEntry = entryList.get(i + 1);
              if (previousEntry.getValue().compareTo(currentEntry.getValue()) < 0 && currentEntry.getValue().compareTo(nextEntry.getValue()) > 0) {
                  peakList.add(currentEntry);
              }
          }
       }
        System.out.println(peakList);
        return peakList.stream()
               .sorted(Entry.comparingByValue())
               .collect(Collectors.toList())
               .stream()
               .limit(3)
               .collect(Collectors.toList())
               .stream()
               .map(localDateTimeBigDecimalEntry ->
                       new ExchangeRateWithDate(
                               currencyId.name(),
                               localDateTimeBigDecimalEntry.getValue(),
                               localDateTimeBigDecimalEntry.getKey()
                       )
               ).collect(Collectors.toList());
    }
}
