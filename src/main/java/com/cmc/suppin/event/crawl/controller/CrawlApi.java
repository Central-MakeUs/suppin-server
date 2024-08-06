package com.cmc.suppin.event.crawl.controller;

import com.cmc.suppin.event.crawl.controller.dto.CrawlResponseDTO;
import com.cmc.suppin.event.crawl.service.CrawlService;
import com.cmc.suppin.global.response.ApiResponse;
import com.cmc.suppin.global.response.ResponseCode;
import com.cmc.suppin.global.security.reslover.Account;
import com.cmc.suppin.global.security.reslover.CurrentAccount;
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

    @GetMapping("/crawling/youtube/comments")
    public ResponseEntity<ApiResponse<CrawlResponseDTO.CrawlResultDTO>> crawlYoutubeComments(@RequestParam String url, @CurrentAccount Account account) {
        crawlService.crawlYoutubeComments(url, account.userId());
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS));
    }
}
