package com.cmc.suppin.event.events.exception;

import com.cmc.suppin.global.exception.BaseErrorCode;
import com.cmc.suppin.global.exception.CustomException;

public class EventException extends CustomException {

    public EventException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
