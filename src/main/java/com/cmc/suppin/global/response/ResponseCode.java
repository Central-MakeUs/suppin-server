package com.cmc.suppin.global.response;

import lombok.Getter;

@Getter
public enum ResponseCode {

    SUCCESS("200", "정상 처리되었습니다."),
    CREATE("201", "정상적으로 생성되었습니다.");

    private final String code;
    private final String message;

    ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}