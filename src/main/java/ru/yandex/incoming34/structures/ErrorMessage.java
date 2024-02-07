package ru.yandex.incoming34.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorMessage {
	SUCCESS("SUCCESS");

	private final String message;
}
