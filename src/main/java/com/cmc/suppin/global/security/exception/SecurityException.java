package com.cmc.suppin.global.security.exception;

import com.cmc.suppin.global.exception.BaseErrorCode;
import lombok.Getter;

@Getter
public class SecurityException extends RuntimeException {

    private final BaseErrorCode errorCode;

    public SecurityException(BaseErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
