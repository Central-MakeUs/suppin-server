package com.cmc.suppin.global.exception.status;

import com.cmc.suppin.global.exception.BaseCode;
import com.cmc.suppin.global.exception.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {
    _OK(HttpStatus.OK, "COMMON200", "성공입니다."),

    //Member
    MEMBER_JOIN_SUCCESS(HttpStatus.OK, "MEMBER2000", "회원 가입 성공입니다."),
    MEMBER_ID_CONFIRM_SUCCESS(HttpStatus.OK, "MEMBER2001", "아이디가 중복되지 않습니다."),
    MEMBER_DELETE_SUCCESS(HttpStatus.OK, "MEMBER2002", "회원 탈퇴 성공입니다."),
    MEMBER_LOGIN_SUCCESS(HttpStatus.OK, "MEMBER2003", "로그인 성공입니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build();
    }
}
