package com.cmc.suppin.global.exception.handler;

import com.cmc.suppin.global.exception.BaseErrorCode;
import com.cmc.suppin.global.exception.GeneralException;

public class JwtHandler extends GeneralException {
    public JwtHandler(BaseErrorCode code) {
        super(code);
    }
}
