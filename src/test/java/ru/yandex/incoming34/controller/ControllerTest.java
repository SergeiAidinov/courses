package ru.yandex.incoming34.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.yandex.incoming34.service.DataService;
import ru.yandex.incoming34.service.ValidationService;

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
        System.out.println(controller.hashCode());
    }

    @Test
    void loadData() {
    }

    @Test
    void getLastExchangeRate() {
    }

    @Test
    void getFiveMaxCourses() {
    }

    @Test
    void getThreeCourseExtremum() {
    }
}