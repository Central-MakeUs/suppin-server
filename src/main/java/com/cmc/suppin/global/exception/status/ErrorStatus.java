package com.cmc.suppin.global.exception.status;

import com.cmc.suppin.global.exception.BaseErrorCode;
import com.cmc.suppin.global.exception.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 에러
    _INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    _BAD_REQUEST(HttpStatus.BAD_REQUEST, "COMMON400", "잘못된 요청입니다."),
    _UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "COMMON401", "인증이 필요합니다."),
    _FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),


    // test
    TEMP_EXCEPTION(HttpStatus.BAD_REQUEST, "TEMP4001", "테스트"),

    // Member
    MEMBER_USERID_DUPLICATED(HttpStatus.BAD_REQUEST, "MEMBER4001", "중복된 아이디 입니다."),
    MEMBER_PASSWORD_ERROR(HttpStatus.BAD_REQUEST, "MEMBER4002", "비밀번호가 잘못되었습니다."),

    //JWT
    JWT_BAD_REQUEST(HttpStatus.UNAUTHORIZED, "JWT4001", "잘못된 JWT 서명입니다."),
    JWT_ACCESS_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT4002", "액세스 토큰이 만료되었습니다."),
    JWT_REFRESH_TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "JWT4003", "리프레시 토큰이 만료되었습니다. 다시 로그인하시기 바랍니다."),
    JWT_UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "JWT4004", "지원하지 않는 JWT 토큰입니다."),
    JWT_TOKEN_NOT_FOUND(HttpStatus.UNAUTHORIZED, "JWT4005", "유효한 JWT 토큰이 없습니다."),

    // 페이징 관련 에러
    PAGE_NEGATIVE_INPUT(HttpStatus.BAD_REQUEST, "PAGE4001", "페이지 번호는 1이상의 숫자여야 합니다."),
    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build();
    }
}
