package ru.yandex.incoming34.structures.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@Getter
public class NewExchangeRateWithDate {

    @Pattern(regexp = "[USD|EUR]{3}")
    @Size(max = 3, min = 3)
    private final String currencyId;
    private final BigDecimal currencyVal;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    private final LocalDateTime regTime;
}
