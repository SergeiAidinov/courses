package ru.yandex.incoming34.service;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yandex.incoming34.structures.Currencies;
import ru.yandex.incoming34.structures.dto.ExchangeRateWithDate;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.yandex.incoming34.structures.Currencies.USD;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DataServiceTest {


    private final DataService dataService;
    private Field exchangeRatesField;
    private final LocalDateTime localDateTimeUsd = LocalDateTime.of(2023, 02, 1, 11, 00, 15, 963_000_000);
    private final LocalDateTime localDateTimeEur = LocalDateTime.of(2023, 02, 3, 8, 00, 15, 854_000_000);

    DataServiceTest(@Qualifier("dataService") DataService dataService) {
        this.dataService = dataService;
    }

    @BeforeAll
    private void before() {
        System.out.println("Before");
        try {
            exchangeRatesField = DataService.class.getDeclaredField("exchangeRates");
            exchangeRatesField.setAccessible(true);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    private void after() {
        exchangeRatesField.setAccessible(false);
        Assert.assertFalse(exchangeRatesField.isAccessible());
    }


    @Test
    @Order(value = 1)
    void addExchangeRate() {
        dataService.addExchangeRate(new ExchangeRateWithDate("USD", new BigDecimal("98.1234"), localDateTimeUsd));
        dataService.addExchangeRate(new ExchangeRateWithDate("EUR", new BigDecimal("101.6723"), localDateTimeEur));
        Assert.assertEquals(Optional.of(Map.entry(localDateTimeUsd, new BigDecimal("98.1234"))), dataService.getLastExchangeRate(USD));
        Assert.assertEquals(Optional.of(Map.entry(localDateTimeEur, new BigDecimal("101.6723"))), dataService.getLastExchangeRate(Currencies.EUR));
    }

    @Test
    @Order(value = 2)
    void getLastExchangeRate() {
        dataService.getLastExchangeRate(USD);
    }

    @Test
    @Order(value = 3)
    void addNewBulkExchangeRate() {
        List<ExchangeRateWithDate> newBulkExchangeRateList = new ArrayList<>();
        BigDecimal lastUsdExchangeRate = new BigDecimal("75.1234");
        for (int i = 1; i < DataService.MAX_REPO_SIZE + 10; i++){
            lastUsdExchangeRate = lastUsdExchangeRate.add(BigDecimal.valueOf(1));
            if (i % 10 == 0) {
                newBulkExchangeRateList.add(new ExchangeRateWithDate("USD", lastUsdExchangeRate.add(BigDecimal.valueOf(i * 15)), localDateTimeUsd.plusMinutes(i)));
            } else {
                newBulkExchangeRateList.add(new ExchangeRateWithDate("USD", lastUsdExchangeRate, localDateTimeUsd.plusMinutes(i)));
            }

        }
        dataService.addNewBulkExchangeRate(newBulkExchangeRateList);
        dataService.getLastExchangeRate(USD);
    }

    @Test
    @Order(value = 4)
    void getFiveMaxCourses() {
        dataService.getFiveMaxCourses(USD);
    }

    @Test
    @Order(value = 5)
    void getThreeCourseExtremum() {
        dataService.getThreeCourseExtremum(USD);
    }
}