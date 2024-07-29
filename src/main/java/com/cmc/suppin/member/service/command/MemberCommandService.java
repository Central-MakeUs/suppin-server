package com.cmc.suppin.member.service.command;

import com.cmc.suppin.member.controller.dto.MemberRequestDTO;
import com.cmc.suppin.member.controller.dto.MemberResponseDTO;
import com.cmc.suppin.member.domain.Member;

public interface MemberCommandService {

    Member join(MemberRequestDTO.JoinDTO request);

    Boolean confirmUserId(MemberRequestDTO.IdConfirmDTO request);

    void deleteMember(String memberId);

    MemberResponseDTO.LoginResponseDTO login(MemberRequestDTO.LoginRequestDTO request);
}
