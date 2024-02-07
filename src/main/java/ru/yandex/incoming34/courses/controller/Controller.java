package ru.yandex.incoming34.courses.controller;

import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

	@PutMapping("/new_task")
	public void createTaskOfVerb(String userId) {

	}

}
