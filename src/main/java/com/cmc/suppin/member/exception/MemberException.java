package com.cmc.suppin.member.exception;

import com.cmc.suppin.global.exception.BaseErrorCode;
import com.cmc.suppin.global.exception.CustomException;
import lombok.Getter;

@Getter
public class MemberException extends CustomException {

    public MemberException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
