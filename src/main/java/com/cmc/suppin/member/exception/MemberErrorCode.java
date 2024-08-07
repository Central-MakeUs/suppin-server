package com.cmc.suppin.member.exception;

import com.cmc.suppin.global.exception.BaseErrorCode;
import com.cmc.suppin.global.response.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum MemberErrorCode implements BaseErrorCode {
    MEMBER_NOT_FOUND("mem-404/01", HttpStatus.NOT_FOUND, "회원을 찾을 수 없습니다."),
    VALIDATION_FAILED("mem-400/01", HttpStatus.BAD_REQUEST, "입력값에 대한 검증에 실패했습니다."),
    MEMBER_ALREADY_DELETED("mem-400/02", HttpStatus.BAD_REQUEST, "탈퇴한 회원입니다."),
    PASSWORD_CONFIRM_NOT_MATCHED("mem-400/03", HttpStatus.BAD_REQUEST, "비밀번호가 잘못 입력되었습니다."),
    PASSWORD_FORMAT_NOT_MATCHED("mem-400/04", HttpStatus.BAD_REQUEST, "비밀번호는 8자 이상 20자 이하로 입력해주세요."),
    DUPLICATE_MEMBER_EMAIL("mem-409/01", HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    DUPLICATE_NICKNAME("mem-409/01", HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다.");


    private final String code;
    private final HttpStatus status;
    private final String message;

    MemberErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return ErrorResponse.of(code, message);
    }
}
