package com.cmc.suppin.event.survey.exception;

import com.cmc.suppin.global.exception.BaseErrorCode;
import com.cmc.suppin.global.exception.CustomException;
import lombok.Getter;

@Getter
public class SurveyException extends CustomException {

    public SurveyException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
