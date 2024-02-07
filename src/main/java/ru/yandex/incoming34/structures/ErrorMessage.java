package ru.yandex.incoming34.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
	SUCCESS("SUCCESS"), ILLEGAL_ARGUMENT("Invalid input data");

	private final String message;
}
