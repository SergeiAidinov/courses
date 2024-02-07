package ru.yandex.incoming34.structures.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.yandex.incoming34.serializer.CoursesResponceSrializer;
import ru.yandex.incoming34.structures.ErrorCode;
import ru.yandex.incoming34.structures.ErrorMessage;

@AllArgsConstructor
@Getter
@JsonSerialize(using = CoursesResponceSrializer.class)
public class CoursesResponce {

	private final ErrorCode errorCode;
	private final ErrorMessage errorMessage;

}
