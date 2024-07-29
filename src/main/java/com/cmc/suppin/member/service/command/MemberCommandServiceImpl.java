package com.cmc.suppin.member.service.command;

import com.cmc.suppin.member.controller.dto.MemberRequestDTO;
import com.cmc.suppin.member.converter.MemberConverter;
import com.cmc.suppin.member.domain.Member;
import com.cmc.suppin.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberCommandServiceImpl implements MemberCommandService {

    private final MemberRepository memberRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberConverter memberConverter;

    /**
     * 회원가입
     */
    @Override
    public Member join(MemberRequestDTO.JoinDTO request) {
        // 중복된 아이디 체크
        if (memberRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }

        // DTO를 Entity로 변환
        Member member = memberConverter.toEntity(request, bCryptPasswordEncoder);

        // 회원 정보 저장
        memberRepository.save(member);

        return member;
    }

    /**
     * ID 중복 확인
     */
    @Override
    public Boolean confirmUserId(MemberRequestDTO.IdConfirmDTO request) {
        // 아이디 중복 체크
        return !memberRepository.existsByUserId(request.getUserId());
    }
}
