package ru.yandex.incoming34.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yandex.incoming34.service.DataService;
import ru.yandex.incoming34.service.ValidationService;
import ru.yandex.incoming34.structures.Currencies;
import ru.yandex.incoming34.structures.dto.ExchangeRate;
import ru.yandex.incoming34.structures.dto.ExchangeRateWithDate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Controller.class, DataService.class, ValidationService.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ControllerTest {

    private final Controller controller;

    ControllerTest(@Qualifier("controller") Controller controller) {
        this.controller = controller;
    }

    @Test
    void addExchangeRate() {
        RuntimeException runtimeException = Assert.assertThrows(RuntimeException.class, () -> controller.addExchangeRate(new ExchangeRate("GBP", new BigDecimal("142.3487"))));
        Assert.assertEquals(ValidationService.UNSUPPORTED_CURRENCY, runtimeException.getMessage());
    }

    @Test
    void loadData() {
        RuntimeException runtimeException = Assert.assertThrows(RuntimeException.class, () -> controller.loadData(
                List.of(
                        new ExchangeRateWithDate(Currencies.USD.name(), new BigDecimal("92.4361"), LocalDateTime.parse("2023-02-01T11:00:15.963")),
                        new ExchangeRateWithDate("GBP", new BigDecimal("137.4361"), LocalDateTime.parse("2023-02-02T10:00:15.963"))
                )
        ));
        Assert.assertEquals(ValidationService.UNSUPPORTED_CURRENCY, runtimeException.getMessage());
    }

}