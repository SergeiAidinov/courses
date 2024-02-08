package ru.yandex.incoming34.structures.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yandex.incoming34.structures.Currencies;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@AllArgsConstructor
@Getter
public class RegisterCourseCommand {

    private final LocalDateTime commandDateTime;
    private final Currencies currency;
    private final BigDecimal exchangeRate;
}
