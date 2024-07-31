package com.cmc.suppin.global.exception;

import com.cmc.suppin.global.response.ErrorResponse;
import org.springframework.http.HttpStatus;

public interface BaseErrorCode {

    ErrorResponse getErrorResponse();

    String getMessage();

    HttpStatus getStatus();
}
