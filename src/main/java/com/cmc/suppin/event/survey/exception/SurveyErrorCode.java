package com.cmc.suppin.event.survey.exception;

import com.cmc.suppin.global.exception.BaseErrorCode;
import com.cmc.suppin.global.response.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum SurveyErrorCode implements BaseErrorCode {

    DUPLICATE_PHONENUMBER("survey-404/01", HttpStatus.CONFLICT, "이미 참여한 참가자입니다. 중복해서 참여할 수 없습니다.");


    private final String code;
    private final HttpStatus status;
    private final String message;

    SurveyErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return ErrorResponse.of(code, message);
    }
}
