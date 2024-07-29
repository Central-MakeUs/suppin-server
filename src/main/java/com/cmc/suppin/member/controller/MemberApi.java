package com.cmc.suppin.member.controller;

import com.cmc.suppin.global.presentation.ApiResponse;
import com.cmc.suppin.member.controller.dto.MemberRequestDTO;
import com.cmc.suppin.member.controller.dto.MemberResponseDTO;
import com.cmc.suppin.member.converter.MemberConverter;
import com.cmc.suppin.member.domain.Member;
import com.cmc.suppin.member.service.command.MemberCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
@Tag(name = "Member", description = "Member 관련 API")
@RequestMapping("/api/members")
public class MemberApi {

    private final MemberCommandService memberCommandService;


    @PostMapping("/join")
    @Operation(summary = "회원가입 API", description = "request 파라미터 : id, password, name, phone, email")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDTO request) {
        Member member = memberCommandService.join(request);

        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member));

    }

    @GetMapping("/checkUserId")
    @Operation(summary = "아이디 중복 체크 API", description = "request : userId, response: 중복이면 false, 중복 아니면 true")
    public ApiResponse<MemberResponseDTO.IdConfirmResultDTO> checkUserId(@RequestBody MemberRequestDTO.IdConfirmDTO request) {
        boolean checkUserId = memberCommandService.confirmUserId(request);

        return ApiResponse.onSuccess(MemberConverter.toIdConfirmResultDTO(checkUserId));
    }
}
