package com.cmc.suppin.member.service.command;

import com.cmc.suppin.global.exception.MemberErrorCode;
import com.cmc.suppin.global.security.jwt.JwtTokenProvider;
import com.cmc.suppin.global.security.user.UserDetailsImpl;
import com.cmc.suppin.member.controller.dto.MemberRequestDTO;
import com.cmc.suppin.member.controller.dto.MemberResponseDTO;
import com.cmc.suppin.member.converter.MemberConverter;
import com.cmc.suppin.member.domain.Member;
import com.cmc.suppin.member.domain.repository.MemberRepository;
import com.cmc.suppin.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberConverter memberConverter;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;

    /**
     * 회원가입
     */
    public Member join(MemberRequestDTO.JoinDTO request) {
        // 중복된 아이디 체크
        if (memberRepository.existsByUserId(request.getUserId())) {
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }

        // 비밀번호 조건 검증
        String password = request.getPassword();
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("비밀번호는 8~20자 영문, 숫자, 특수문자를 사용해야 합니다.");
        }

        // DTO를 Entity로 변환
        Member member = memberConverter.toEntity(request, passwordEncoder);

        // 회원 정보 저장
        memberRepository.save(member);

        return member;
    }

    /**
     * ID 중복 확인
     */
    public Boolean confirmUserId(MemberRequestDTO.IdConfirmDTO request) {
        // 아이디 중복 체크
        return !memberRepository.existsByUserId(request.getUserId());
    }

    /**
     * 이메일 중복 확인
     */
    public Boolean confirmEmail(MemberRequestDTO.EmailConfirmDTO request) {
        // 이메일 중복 체크
        return !memberRepository.existsByEmail(request.getEmail());
    }

    /**
     * 회원 탈퇴
     */
    public void deleteMember(Long memberId) {
        final Member member = getMember(memberId);
        if (member.isDeleted()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
        member.delete();
    }

    /**
     * 로그인
     */
    public MemberResponseDTO.LoginResponseDTO login(MemberRequestDTO.LoginRequestDTO request) {
        validateMember(request.getUserId());

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                request.getUserId(),
                request.getPassword()
        );

        Authentication authenticate = authenticationManager.authenticate(authentication);

        String accessToken = jwtTokenProvider.createAccessToken((UserDetailsImpl) authenticate.getPrincipal());

        return MemberResponseDTO.LoginResponseDTO.builder()
                .token(accessToken)
                .userId(request.getUserId())
                .build();
    }

    // 검증 메서드
    private boolean isValidPassword(String password) {
        return password.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}");
    }

    private void validateMember(String userId) {
        Member member = memberRepository.findByUserId(userId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (member.isDeleted()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }

    private Member getMember(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    private void validateDuplicateEmail(MemberRequestDTO.JoinDTO request) {
        if (Boolean.TRUE.equals(memberRepository.existsByEmail(request.getEmail()))) {
            throw new MemberException(MemberErrorCode.DUPLICATE_MEMBER_EMAIL);
        }
    }


    public void logout(Long memberId) {

    }
}
