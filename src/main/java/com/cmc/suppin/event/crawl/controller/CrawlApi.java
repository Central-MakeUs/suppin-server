package com.cmc.suppin.event.crawl.controller;

import com.cmc.suppin.event.crawl.service.CrawlService;
import com.cmc.suppin.global.response.ApiResponse;
import com.cmc.suppin.global.response.ResponseCode;
import com.cmc.suppin.global.security.reslover.Account;
import com.cmc.suppin.global.security.reslover.CurrentAccount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Crawling", description = "Crawling 관련 API")
@RequestMapping("/api/v1/event")
public class CrawlApi {

    private final CrawlService crawlService;

    // 유튜브 크롤링
    @GetMapping("/crawling/comments")
    @Operation(summary = "유튜브 댓글 크롤링 API", description = "주어진 URL의 유튜브 댓글을 크롤링하고 DB에 저장합니다.")
    public ResponseEntity<ApiResponse<Void>> crawlYoutubeComments(@RequestParam String url, @RequestParam Long eventId, @CurrentAccount Account account) {
        crawlService.crawlYoutubeComments(url, eventId, account.userId());
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS));
    }

    // TODO: 인스타그램 게시글 크롤링
}
