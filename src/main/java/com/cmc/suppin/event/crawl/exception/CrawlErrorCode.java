package com.cmc.suppin.event.crawl.exception;

import com.cmc.suppin.global.exception.BaseErrorCode;
import com.cmc.suppin.global.response.ErrorResponse;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CrawlErrorCode implements BaseErrorCode {

    DUPLICATE_URL("crawl-404/01", HttpStatus.CONFLICT, "이미 수집된 댓글이 있는 URL입니다."),
    CRAWL_FAILED("crawl-500/01", HttpStatus.INTERNAL_SERVER_ERROR, "크롤링에 실패했습니다.");

    private final String code;
    private final HttpStatus status;
    private final String message;

    CrawlErrorCode(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    @Override
    public ErrorResponse getErrorResponse() {
        return ErrorResponse.of(code, message);
    }
}
