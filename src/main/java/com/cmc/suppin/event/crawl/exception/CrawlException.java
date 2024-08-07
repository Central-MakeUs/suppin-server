package com.cmc.suppin.event.crawl.exception;

import com.cmc.suppin.global.exception.BaseErrorCode;
import com.cmc.suppin.global.exception.CustomException;
import lombok.Getter;

@Getter
public class CrawlException extends CustomException {

    public CrawlException(BaseErrorCode errorCode) {
        super(errorCode);
    }
}
