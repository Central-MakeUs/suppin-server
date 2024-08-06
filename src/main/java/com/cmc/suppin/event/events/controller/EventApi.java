package com.cmc.suppin.event.events.controller;

import com.cmc.suppin.event.events.controller.dto.EventRequestDTO;
import com.cmc.suppin.event.events.controller.dto.EventResponseDTO;
import com.cmc.suppin.event.events.service.EventService;
import com.cmc.suppin.global.response.ApiResponse;
import com.cmc.suppin.global.response.ResponseCode;
import com.cmc.suppin.global.security.reslover.Account;
import com.cmc.suppin.global.security.reslover.CurrentAccount;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Event", description = "Event 관련 API")
@RequestMapping("/api/v1/events")
public class EventApi {

    private final EventService eventService;

    @GetMapping("/all")
    @Operation(summary = "전체 이벤트 조회 API", description = "로그인한 사용자의 모든 이벤트와 설문 및 댓글 수를 조회합니다., JWT 토큰만 주시면 됩니다.")
    public ResponseEntity<ApiResponse<List<EventResponseDTO.EventInfoDTO>>> getAllEventsWithCounts(@CurrentAccount Account account) {
        List<EventResponseDTO.EventInfoDTO> events = eventService.getAllEvents(account.userId());
        return ResponseEntity.ok(ApiResponse.of(events));
    }

    @PostMapping("/new/comment/crawling")
    @Operation(summary = "댓글 이벤트 생성 API",
            description = "request : type(ENUM 타입으로, 'COMMENT와 SURVEY' 둘 중 하나를 입력해주시면 됩니다), " +
                    "title, description, url, startDate(yyyy-MM-dd), endDate(yyyy-MM-dd), announcementDate(yyyy-MM-dd)")
    public ResponseEntity<ApiResponse<Void>> createEvent(@RequestBody @Valid EventRequestDTO.CommentEventCreateDTO request, @CurrentAccount Account account) {
        eventService.createCommentEvent(request, account.userId());
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS));
    }
}
