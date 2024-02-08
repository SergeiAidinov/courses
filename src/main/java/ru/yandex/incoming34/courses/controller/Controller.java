package ru.yandex.incoming34.courses.controller;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import ru.yandex.incoming34.courses.service.DataService;
import ru.yandex.incoming34.courses.service.ValidationService;
import ru.yandex.incoming34.structures.Currencies;
import ru.yandex.incoming34.structures.ErrorCode;
import ru.yandex.incoming34.structures.ErrorMessage;
import ru.yandex.incoming34.structures.dto.RequestLastExchangerate;
import ru.yandex.incoming34.structures.dto.CoursesResponce;
import ru.yandex.incoming34.structures.dto.NewExchangeRate;

@RestController
@RequestMapping(value = "/api/courses")
@AllArgsConstructor
public class Controller {

    private final DataService dataService;
    private final ValidationService validationService;

    @PostMapping("/regCourse")
    @Operation(description = "Получив эти данные, приложение course фиксирует время регистрации нового курса и сохраняет данные в коллекцию значений в памяти.")
    public CoursesResponce regCourse(
            @Schema(example = "{\"currencyId\": \"USD\", \"currencyVal\": 92.8722}") @RequestBody NewExchangeRate newExchangeRate) {
        System.out.println("Cont!!!" + this.hashCode());
        if (validationService.validate(newExchangeRate)) {
            dataService.addNewExchangeRate(new CoursesResponce.RegisterCourseCommand(LocalDateTime.now(),
                    Currencies.valueOf(newExchangeRate.getCurrencyId()), newExchangeRate.getCurrencyVal()));
            return new CoursesResponce(ErrorCode.ZERO, ErrorMessage.SUCCESS);
        }

        return new CoursesResponce(ErrorCode.ONE, ErrorMessage.ILLEGAL_ARGUMENT);
    }

    @PostMapping("/loadData")
    @Operation(description = "В теле сообщения передается массив курсов, которые нужно загрузить в дополнение к уже имеющимся в хранилище.")
    public void loadData() {

    }

    @GetMapping("/getCourse/{currencyId}")
    @Operation(description = "Возвращает последний установленный курс")
    public Optional<Entry<LocalDateTime, BigDecimal>> getCourse(Currencies currencyId) {
        System.out.println(currencyId);
        return dataService.getLastExchangerate(new RequestLastExchangerate(currencyId, UUID.randomUUID()));

    }

    @GetMapping("/getCourseMax5")
    @Operation(description = "Возвращает массив пяти последних самых высоких курса, которые присутствуют в текущем хранимом массиве записей курсов")
    public void getFiveCourseMax() {

    }

    @GetMapping("/getCourseExtremum3")
    @Operation(description = "Возвращает массив трех последних наивысших пиков курса, которые присутствуют в текущем хранимом массиве записей курсов валюты")
    public void getThreeCourseExtremum() {

    }

}
