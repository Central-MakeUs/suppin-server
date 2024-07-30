package com.cmc.suppin.member.controller;

import com.cmc.suppin.global.exception.status.SuccessStatus;
import com.cmc.suppin.global.presentation.ApiResponse;
import com.cmc.suppin.member.controller.dto.MemberDetails;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    /**
     * 회원가입
     */
    @PostMapping("/join")
    @Operation(summary = "회원가입 API", description = "request 파라미터 : id, password, name, phone, email")
    public ApiResponse<MemberResponseDTO.JoinResultDTO> join(@RequestBody @Valid MemberRequestDTO.JoinDTO request) {
        Member member = memberCommandService.join(request);

        return ApiResponse.onSuccess(MemberConverter.toJoinResultDTO(member), SuccessStatus.MEMBER_JOIN_SUCCESS);

    }

    @PostMapping("/checkUserId")
    @Operation(summary = "아이디 중복 체크 API", description = "request : userId, response: 중복이면 false, 중복 아니면 true")
    public ApiResponse<MemberResponseDTO.IdConfirmResultDTO> checkUserId(@RequestBody MemberRequestDTO.IdConfirmDTO request) {
        boolean checkUserId = memberCommandService.confirmUserId(request);

        return ApiResponse.onSuccess(MemberConverter.toIdConfirmResultDTO(checkUserId), SuccessStatus.MEMBER_ID_CONFIRM_SUCCESS);
    }

    @DeleteMapping("/delete")
    @Operation(summary = "회원탈퇴 API", description = "JWT 토큰을 헤더에 포함시켜 보내주시면 됩니다.")
    public ApiResponse<Void> deleteMember(@AuthenticationPrincipal MemberDetails memberDetails) {
        if (memberDetails == null) {
            return ApiResponse.onFailure("403", "인증된 사용자만 삭제할 수 있습니다.", null);
        }
        memberCommandService.deleteMember(memberDetails.getUserId());
        return ApiResponse.onSuccess(null, SuccessStatus.MEMBER_DELETE_SUCCESS);
    }

    /**
     * TODO: 로그인, 로그아웃, 비밀번호 변경, 회원정보 상세 조회, 회원정보 수정 API
     */
    // 로그인
    @PostMapping("/login")
    @Operation(summary = "로그인 API", description = "request : userId, password")
    public ApiResponse<MemberResponseDTO.LoginResponseDTO> login(@RequestBody @Valid MemberRequestDTO.LoginRequestDTO request) {
        MemberResponseDTO.LoginResponseDTO response = memberCommandService.login(request);
        return ApiResponse.onSuccess(response, SuccessStatus.MEMBER_LOGIN_SUCCESS);
    }


//    // 로그아웃
//    @PostMapping("/logout")
//    @Operation(summary = "로그아웃 API", description = "JWT 토큰을 헤더에 포함시켜 보내주시면 됩니다.")
//    public ApiResponse<Void> logout(@AuthenticationPrincipal MemberDetails memberDetails) {
//        if (memberDetails == null) {
//            return ApiResponse.onFailure("403", "인증된 사용자만 로그아웃할 수 있습니다.", null);
//        }
//        memberCommandService.logout(memberDetails.getUserId());
//        return ApiResponse.onSuccess(null, SuccessStatus.MEMBER_LOGOUT_SUCCESS);
//    }
//
//    // 비밀번호 변경
//    @PutMapping("/changePassword")
//    @Operation(summary = "비밀번호 변경 API", description = "request : userId, password, newPassword")
//    public ApiResponse<Void> changePassword(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody @Valid MemberRequestDTO.ChangePasswordDTO request) {
//        if (memberDetails == null) {
//            return ApiResponse.onFailure("403", "인증된 사용자만 비밀번호를 변경할 수 있습니다.", null);
//        }
//        memberCommandService.changePassword(memberDetails.getUserId(), request);
//        return ApiResponse.onSuccess(null, SuccessStatus.MEMBER_CHANGE_PASSWORD_SUCCESS);
//    }
//
//    // 회원정보 상세 조회(마이페이지)
//    @GetMapping("/info")
//    @Operation(summary = "회원정보 상세 조회 API", description = "JWT 토큰을 헤더에 포함시켜 보내주시면 됩니다.")
//    public ApiResponse<MemberResponseDTO.MemberInfoDTO> getMemberInfo(@AuthenticationPrincipal MemberDetails memberDetails) {
//        if (memberDetails == null) {
//            return ApiResponse.onFailure("403", "인증된 사용자만 조회할 수 있습니다.", null);
//        }
//        Member member = memberCommandService.getMemberInfo(memberDetails.getUserId());
//        return ApiResponse.onSuccess(MemberConverter.toMemberInfoDTO(member), SuccessStatus.MEMBER_INFO_SUCCESS);
//    }
//
//    // 회원정보 수정
//    @PutMapping("/info/update")
//    @Operation(summary = "회원정보 수정 API", description = "request : userId, name, phone, email")
//    public ApiResponse<Void> updateMemberInfo(@AuthenticationPrincipal MemberDetails memberDetails, @RequestBody @Valid MemberRequestDTO.UpdateMemberInfoDTO request) {
//        if (memberDetails == null) {
//            return ApiResponse.onFailure("403", "인증된 사용자만 수정할 수 있습니다.", null);
//        }
//        memberCommandService.updateMemberInfo(memberDetails.getUserId(), request);
//        return ApiResponse.onSuccess(null, SuccessStatus.MEMBER_UPDATE_SUCCESS);
//    }

    // TODO: 아이디 찾기, 비밀번호 찾기 API 구현 필요

}
