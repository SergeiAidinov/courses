package ru.yandex.incoming34.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;
import ru.yandex.incoming34.service.DataService;
import ru.yandex.incoming34.service.ValidationService;
import ru.yandex.incoming34.structures.Currencies;
import ru.yandex.incoming34.structures.dto.CoursesResponse;
import ru.yandex.incoming34.structures.dto.ExchangeRate;
import ru.yandex.incoming34.structures.dto.ExchangeRateWithDate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
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
    public CoursesResponse loadData(@Schema(example = "[{\"currencyId\": \"USD\", \"currencyVal\": 92.8722, \"regTime\": \"2024-02-07T11:31:42.201\"}, " +
            "{\"currencyId\": \"USD\", \"currencyVal\": 92.8742, \"regTime\": \"2024-02-07T12:31:44.122\"}, " +
            "{\"currencyId\": \"EUR\", \"currencyVal\": 99.9282, \"regTime\": \"2024-02-07T13:02:22.114\"}]")
                                    @RequestBody List<ExchangeRateWithDate> exchangeRateWithDateList) {
        validationService.throwExceptionIfInvalid(exchangeRateWithDateList);
        dataService.addNewBulkExchangeRate(exchangeRateWithDateList);
        return new CoursesResponse(SUCCESS_MESSAGE);
    }

    @GetMapping("/getCourse/{currencyId}")
    @Operation(description = "Возвращает последний установленный курс")
    public Optional<ExchangeRateWithDate> getLastExchangeRate(@PathVariable Currencies currencyId) {
        return dataService.getLastExchangeRate(currencyId).map(localDateTimeBigDecimalEntry -> new ExchangeRateWithDate(currencyId.name(), localDateTimeBigDecimalEntry.getValue(),
                localDateTimeBigDecimalEntry.getKey()));
    }

    @GetMapping("/getCourseMax5/{currencyId}")
    @Operation(description = "Возвращает массив из пяти самых высоких курсов, которые присутствуют в текущем хранимом массиве записей курсов")
    public List<ExchangeRateWithDate> getFiveMaxCourses(@PathVariable Currencies currencyId) {
        return dataService.getFiveMaxCourses(currencyId);
    }

    @GetMapping("/getCourseExtremum3")
    @Operation(description = "Возвращает массив трех наивысших пиков курса, которые присутствуют в текущем хранимом массиве записей курсов валюты. " +
            "Пик курса характеризуется тем, что записи слева и справа от него по времени регистрации имеют меньшие значения курса.")
    public List<ExchangeRateWithDate> getThreeCourseExtremum(Currencies currencyId) {
        return dataService.getThreeCourseExtremum(currencyId);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(value = {RuntimeException.class, HttpMessageNotReadableException.class})
    private CoursesResponse handleException(RuntimeException exception) {
        return new CoursesResponse(Objects.nonNull(exception.getMessage()) ? exception.getMessage() : "Unknown Error");
    }

}
