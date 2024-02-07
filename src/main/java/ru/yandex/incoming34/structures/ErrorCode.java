package ru.yandex.incoming34.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {
	ZERO(0), ONE(1);

	private final int code;
}
