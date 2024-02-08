package ru.yandex.incoming34.structures.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yandex.incoming34.structures.RegExpPatterns;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class NewExchangeRateWithDate {

    @Pattern(regexp = RegExpPatterns.AVAILABLE_CURRENCIES)
    private final String currencyId;
    private final BigDecimal currencyVal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private final LocalDateTime regTime;
}
