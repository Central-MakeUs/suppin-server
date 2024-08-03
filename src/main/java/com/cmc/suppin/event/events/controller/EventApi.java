package com.cmc.suppin.event.events.controller;

import com.cmc.suppin.event.events.controller.dto.EventRequestDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Event", description = "Event 관련 API")
@RequestMapping("/api/v1/event")
public class EventApi {

    private final EventService eventService;

    @PostMapping("/new")
    @Operation(summary = "댓글 이벤트 생성 API", description = "request : eventType, title, url, startDate, endDate")
    public ResponseEntity<ApiResponse<Void>> createEvent(@RequestBody @Valid EventRequestDTO.CommentEventCreateDTO request, @CurrentAccount Account account) {
        eventService.createEvent(request, account.userId());
        return ResponseEntity.ok(ApiResponse.of(ResponseCode.SUCCESS));
    }
}
