package ru.yandex.incoming34.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.incoming34.service.DataService;
import ru.yandex.incoming34.service.ValidationService;
import ru.yandex.incoming34.structures.Currencies;
import ru.yandex.incoming34.structures.dto.CoursesResponse;
import ru.yandex.incoming34.structures.dto.ExchangeRate;
import ru.yandex.incoming34.structures.dto.ExchangeRateWithDate;
import org.springframework.http.converter.HttpMessageNotReadableException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/courses")
@AllArgsConstructor
public class Controller {

    private final DataService dataService;
    private final ValidationService validationService;
    public static final String SUCCESS_MESSAGE = "SUCCESS";

    @PostMapping("/regCourse")
    @Operation(description = "Получив эти данные, приложение course фиксирует время регистрации нового курса и сохраняет данные в коллекцию значений в памяти.")
    public CoursesResponse addExchangeRate(
            @Schema(example = "{\"currencyId\": \"USD\", \"currencyVal\": 92.8722}") @RequestBody ExchangeRate exchangeRate) {
        validationService.throwExceptionIfInvalid(exchangeRate);
        dataService.addExchangeRate(new ExchangeRateWithDate(
                exchangeRate.getCurrencyId(), exchangeRate.getCurrencyVal(), LocalDateTime.now()));
        return new CoursesResponse(SUCCESS_MESSAGE);
    }

    @PostMapping(value = "/loadData")
    @Operation(description = "В теле сообщения передается массив курсов, которые нужно загрузить в дополнение к уже имеющимся в хранилище.")
    public void loadData(@Schema(example = "[{\"currencyId\": \"USD\", \"currencyVal\": 92.8722, \"regTime\": \"2024-02-07T11:31:42.201\"}, " +
            "{\"currencyId\": \"USD\", \"currencyVal\": 92.8742, \"regTime\": \"2024-02-07T12:31:44.122\"}, " +
            "{\"currencyId\": \"EUR\", \"currencyVal\": 99.9282, \"regTime\": \"2024-02-07T13:02:22.114\"}]")
                         @RequestBody List<ExchangeRateWithDate> exchangeRateWithDateList) {
        validationService.throwExceptionIfInvalid(exchangeRateWithDateList);
        dataService.addNewBulkExchangeRate(exchangeRateWithDateList);
    }

    @GetMapping("/getCourse/{currencyId}")
    @Operation(description = "Возвращает последний установленный курс")
    public Optional<Entry<LocalDateTime, BigDecimal>> getLastExchangeRate(Currencies currencyId) {
        return dataService.getLastExchangeRate(currencyId);
    }

    @GetMapping("/getCourseMax5/{currencyId}")
    @Operation(description = "Возвращает массив пяти последних самых высоких курса, которые присутствуют в текущем хранимом массиве записей курсов")
    public List<ExchangeRateWithDate> getFiveMaxCourses(Currencies currencyId) {
        return dataService.getFiveMaxCourses(currencyId);
    }

    @GetMapping("/getCourseExtremum3")
    @Operation(description = "Возвращает массив трех последних наивысших пиков курса, которые присутствуют в текущем хранимом массиве записей курсов валюты")
    public List<ExchangeRateWithDate> getThreeCourseExtremum(Currencies currencyId) {
        return dataService.getThreeCourseExtremum(currencyId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {RuntimeException.class, HttpMessageNotReadableException.class})
    public CoursesResponse handleException(RuntimeException exception) {
        return new CoursesResponse(exception.getMessage());
    }

}
