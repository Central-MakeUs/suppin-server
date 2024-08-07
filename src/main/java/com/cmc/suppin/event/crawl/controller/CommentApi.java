package com.cmc.suppin.event.crawl.controller;

import com.cmc.suppin.event.crawl.controller.dto.CommentResponseDTO;
import com.cmc.suppin.event.crawl.service.CommentService;
import com.cmc.suppin.global.response.ApiResponse;
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
@Tag(name = "Event-Comments", description = "Crawling Comments 관련 API")
@RequestMapping("/api/v1/event/comments")
public class CommentApi {

    private final CommentService commentService;

    @GetMapping("/list")
    @Operation(summary = "크롤링된 전체 댓글 조회 API",
            description = "주어진 이벤트 ID와 URL의 댓글을 페이지네이션하여 이벤트의 endDate 전에 작성된 댓글들만 조회합니다.<br><br>" +
                    "Request: eventId: 조회할 이벤트의 ID, url: 댓글을 조회할 유튜브 URL, page: 조회할 페이지 번호 (0부터 시작), " +
                    "size: 한 페이지당 댓글 수, Authorization: JWT 토큰을 포함한 인증 헤더<br>" +
                    "Response: participantCount: 현재 페이지에서 가져온 댓글 수, crawlTime: 댓글 조회(크롤링) 요청 시간, comments: 각 댓글의 상세 정보 배열" +
                    "author: 댓글 작성자, commentText: 댓글 내용, commentDate: 댓글 작성 시간")
    public ResponseEntity<ApiResponse<CommentResponseDTO.CrawledCommentListDTO>> getComments(
            @RequestParam Long eventId,
            @RequestParam String url,
            @RequestParam int page,
            @RequestParam int size,
            @CurrentAccount Account account) {
        CommentResponseDTO.CrawledCommentListDTO comments = commentService.getComments(eventId, url, page, size, account.userId());
        return ResponseEntity.ok(ApiResponse.of(comments));
    }
}
