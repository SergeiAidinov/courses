package ru.yandex.incoming34.service;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yandex.incoming34.structures.dto.ExchangeRateWithDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.yandex.incoming34.structures.Currencies.EUR;
import static ru.yandex.incoming34.structures.Currencies.USD;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DataServiceTest {


    private final DataService dataService;
    private final LocalDateTime localDateTimeUsd = LocalDateTime.parse("2023-02-01T11:00:15.963");
    private final LocalDateTime localDateTimeEur = LocalDateTime.parse("2023-02-03T08:00:15.854");

    DataServiceTest(@Qualifier("dataService") DataService dataService) {
        this.dataService = dataService;
    }

    @Test
    @Order(value = 1)
    void addExchangeRate() {
        dataService.addExchangeRate(new ExchangeRateWithDate("USD", new BigDecimal("98.1234"), localDateTimeUsd));
        dataService.addExchangeRate(new ExchangeRateWithDate("EUR", new BigDecimal("101.6723"), localDateTimeEur));
        Assert.assertEquals(Optional.of(Map.entry(localDateTimeUsd, new BigDecimal("98.1234"))), dataService.getLastExchangeRate(USD));
        Assert.assertEquals(Optional.of(Map.entry(localDateTimeEur, new BigDecimal("101.6723"))), dataService.getLastExchangeRate(EUR));
    }

    @Test
    @Order(value = 2)
    void addNewBulkExchangeRate() {
        List<ExchangeRateWithDate> newBulkExchangeRateList = new ArrayList<>();
        BigDecimal lastUsdExchangeRate = new BigDecimal("75.1234");
        BigDecimal lastEurExchangeRate = new BigDecimal("98.6723");
        for (int i = 1; i < DataService.MAX_REPO_SIZE + 10; i++) {
            lastUsdExchangeRate = lastUsdExchangeRate.add(BigDecimal.valueOf(1));
            lastEurExchangeRate = lastEurExchangeRate.add(BigDecimal.valueOf(2));
            if (i % 10 == 0) {
                newBulkExchangeRateList.add(new ExchangeRateWithDate("USD", lastUsdExchangeRate.add(BigDecimal.valueOf(i * 15)), localDateTimeUsd.plusMinutes(i)));
                newBulkExchangeRateList.add(new ExchangeRateWithDate("EUR", lastEurExchangeRate.add(BigDecimal.valueOf(i * 10)), localDateTimeEur.plusHours(i)));
            } else {
                newBulkExchangeRateList.add(new ExchangeRateWithDate("USD", lastUsdExchangeRate, localDateTimeUsd.plusMinutes(i)));
                newBulkExchangeRateList.add(new ExchangeRateWithDate("EUR", lastEurExchangeRate, localDateTimeEur.plusHours(i)));
            }
        }
        dataService.addNewBulkExchangeRate(newBulkExchangeRateList);
        Assert.assertEquals(Optional.of(Map.entry(LocalDateTime.parse("2023-02-01T12:49:15.963"),
                new BigDecimal("184.1234"))), dataService.getLastExchangeRate(USD));
        Assert.assertEquals(Optional.of(Map.entry(LocalDateTime.parse("2023-02-07T21:00:15.854"),
                new BigDecimal("316.6723"))), dataService.getLastExchangeRate(EUR));
    }

    @Test
    @Order(value = 3)
    void getFiveMaxCourses() {
        final List<ExchangeRateWithDate> sampleUsdList = List.of(
                new ExchangeRateWithDate("USD", new BigDecimal("1035.1234"), LocalDateTime.parse("2023-02-01T12:00:15.963")),
                new ExchangeRateWithDate("USD", new BigDecimal("1195.1234"), LocalDateTime.parse("2023-02-01T12:10:15.963")),
                new ExchangeRateWithDate("USD", new BigDecimal("1355.1234"), LocalDateTime.parse("2023-02-01T12:20:15.963")),
                new ExchangeRateWithDate("USD", new BigDecimal("1515.1234"), LocalDateTime.parse("2023-02-01T12:30:15.963")),
                new ExchangeRateWithDate("USD", new BigDecimal("1675.1234"), LocalDateTime.parse("2023-02-01T12:40:15.963"))
        );
        Assert.assertTrue(sampleUsdList.containsAll(dataService.getFiveMaxCourses(USD))
                && sampleUsdList.size() == dataService.getFiveMaxCourses(USD).size());

        final List<ExchangeRateWithDate> sampleEurList = List.of(
                new ExchangeRateWithDate("EUR", new BigDecimal("818.6723"), LocalDateTime.parse("2023-02-05T20:00:15.854")),
                new ExchangeRateWithDate("EUR", new BigDecimal("938.6723"), LocalDateTime.parse("2023-02-06T06:00:15.854")),
                new ExchangeRateWithDate("EUR", new BigDecimal("1058.6723"), LocalDateTime.parse("2023-02-06T16:00:15.854")),
                new ExchangeRateWithDate("EUR", new BigDecimal("1178.6723"), LocalDateTime.parse("2023-02-07T02:00:15.854")),
                new ExchangeRateWithDate("EUR", new BigDecimal("1298.6723"), LocalDateTime.parse("2023-02-07T12:00:15.854"))
        );
        Assert.assertTrue(sampleEurList.containsAll(dataService.getFiveMaxCourses(EUR))
                && sampleEurList.size() == dataService.getFiveMaxCourses(EUR).size());
    }

    @Test
    @Order(value = 4)
    void getThreeCourseExtremum() {
        dataService.getThreeCourseExtremum(USD);
    }
}