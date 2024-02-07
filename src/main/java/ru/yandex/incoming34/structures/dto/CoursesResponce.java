package ru.yandex.incoming34.structures.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yandex.incoming34.structures.ErrorCode;
import ru.yandex.incoming34.structures.ErrorMessage;

@AllArgsConstructor
@Getter
public class CoursesResponce {

	private final ErrorCode errorCode;
	private final ErrorMessage errorMessage;

}
