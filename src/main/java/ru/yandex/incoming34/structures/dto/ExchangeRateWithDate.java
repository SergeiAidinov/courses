package ru.yandex.incoming34.structures.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static ru.yandex.incoming34.service.ValidationService.AVAILABLE_CURRENCIES;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class ExchangeRateWithDate {

    @Pattern(regexp = AVAILABLE_CURRENCIES)
    private final String currencyId;
    private final BigDecimal currencyVal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private final LocalDateTime regTime;
}
