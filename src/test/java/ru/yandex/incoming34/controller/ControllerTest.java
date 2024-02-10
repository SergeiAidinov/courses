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

import java.math.BigDecimal;

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
        runtimeException = Assert.assertThrows(RuntimeException.class, () -> controller.addExchangeRate(new ExchangeRate(Currencies.USD.name(), new BigDecimal("91.3qwe"))));
        Assert.assertEquals("Character q is neither a decimal digit number, decimal point, nor \"e\" notation exponential mark.", runtimeException.getMessage());
    }

    @Test
    void loadData() {
    }

}