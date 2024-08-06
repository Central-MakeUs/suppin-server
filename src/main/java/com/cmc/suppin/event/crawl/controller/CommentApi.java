package com.cmc.suppin.event.crawl.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Event-Comments", description = "Crawling Comments 관련 API")
@RequestMapping("/api/v1/comments")
public class CommentApi {


}
