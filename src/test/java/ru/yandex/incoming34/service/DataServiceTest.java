package ru.yandex.incoming34.service;

import lombok.AllArgsConstructor;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yandex.incoming34.structures.dto.ExchangeRateWithDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DataService.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DataServiceTest {


    private final DataService dataService;

    DataServiceTest(@Qualifier("dataService") DataService dataService) {
        this.dataService = dataService;
    }

    @Test
    void addExchangeRate() {
        System.out.println();
        dataService.addExchangeRate(new ExchangeRateWithDate("USD", new BigDecimal("98.1234"), LocalDateTime.now()));
        Assert.assertEquals(1,1);
    }

    @Test
    void getLastExchangeRate() {
    }

    @Test
    void addNewBulkExchangeRate() {
    }

    @Test
    void getFiveMaxCourses() {
    }

    @Test
    void getThreeCourseExtremum() {
    }
}