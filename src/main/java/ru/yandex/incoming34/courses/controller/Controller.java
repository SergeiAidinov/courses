package ru.yandex.incoming34.courses.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping(value = "/api/courses")
public class Controller {

	@PostMapping("/regCourse")
	@Operation(description = "Получив эти данные, приложение course фиксирует время регистрации нового курса и сохраняет данные в коллекцию значений в памяти.")
	public void regCourse(String userId) {

	}

	@PostMapping("/loadData")
	@Operation(description = "В теле сообщения передается массив курсов, которые нужно загрузить в дополнение к уже имеющимся в хранилище.")
	public void loadData() {

	}

	@GetMapping("/getCourse")
	@Operation(description = "Возвращает последний установленный курс")
	public void getCourse() {

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
