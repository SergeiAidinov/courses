package ru.yandex.incoming34.structures.dto;

import lombok.Getter;
import ru.yandex.incoming34.controller.Controller;

@Getter
public class CoursesResponse {

    private final int errorCode;
    private final String detailedMessage;

    public CoursesResponse(String detailedMessage) {
        this.detailedMessage = detailedMessage;
        errorCode = detailedMessage.equals(Controller.SUCCESS_MESSAGE) ? 0 : 1;
    }
}
