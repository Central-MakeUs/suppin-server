package com.cmc.suppin.member.converter;

import com.cmc.suppin.member.controller.dto.MemberRequestDTO;
import com.cmc.suppin.member.controller.dto.MemberResponseDTO;
import com.cmc.suppin.member.domain.Member;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class MemberConverter {

    public Member toEntity(MemberRequestDTO.JoinDTO request, BCryptPasswordEncoder encoder) {
        return Member.builder()
                .userId(request.getUserId())
                .name(request.getName())
                .password(encoder.encode(request.getPassword()))
                .email(request.getEmail())
                .phoneNumber(request.getPhone())
                .build();
    }

    public static MemberResponseDTO.JoinResultDTO toJoinResultDTO(Member member) {
        return MemberResponseDTO.JoinResultDTO.builder()
                .memberId(member.getId())
                .userId(member.getUserId())
                .name(member.getName())
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .build();
    }

    public static MemberResponseDTO.IdConfirmResultDTO toIdConfirmResultDTO(boolean checkUserId) {
        return MemberResponseDTO.IdConfirmResultDTO.builder()
                .checkUserId(checkUserId)
                .build();
    }

}
