package com.cmc.suppin.member.service;

import com.cmc.suppin.global.config.MailConfig;
import com.cmc.suppin.global.enums.UserStatus;
import com.cmc.suppin.global.exception.MemberErrorCode;
import com.cmc.suppin.global.security.jwt.JwtTokenProvider;
import com.cmc.suppin.global.security.user.UserDetailsImpl;
import com.cmc.suppin.member.controller.dto.MemberRequestDTO;
import com.cmc.suppin.member.controller.dto.MemberResponseDTO;
import com.cmc.suppin.member.converter.MemberConverter;
import com.cmc.suppin.member.domain.EmailVerificationToken;
import com.cmc.suppin.member.domain.Member;
import com.cmc.suppin.member.domain.repository.EmailVerificationTokenRepository;
import com.cmc.suppin.member.domain.repository.MemberRepository;
import com.cmc.suppin.member.exception.MemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

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
    private final EmailVerificationTokenRepository emailVerificationTokenRepository;
    private final MailConfig mailConfig;

    public boolean requestEmailVerification(String email) {
        String code = generateVerificationCode();
        saveVerificationToken(email, code);
        return mailConfig.sendMail(email, code);
    }

    public boolean verifyEmailCode(String email, String code) {
        return verifyToken(email, code);
    }

    /**
     * 회원가입
     */
    public Member join(MemberRequestDTO.JoinDTO request) {
        // 이메일 인증 체크
        if (!verifyEmailCode(request.getEmail(), request.getVerificationCode())) {
            throw new IllegalArgumentException("이메일 인증이 필요합니다.");
        }

        // 중복된 아이디 체크
        if (memberRepository.existsByUserIdAndStatusNot(request.getUserId(), UserStatus.DELETED)) {
            throw new IllegalArgumentException("이미 존재하는 유저입니다.");
        }

        // 비밀번호 조건 검증
        String password = request.getPassword();
        if (!isValidPassword(password)) {
            throw new IllegalArgumentException("비밀번호는 8~20자 영문, 숫자, 특수문자를 사용해야 합니다.");
        }

        // DTO를 Entity로 변환
        Member member = memberConverter.toEntity(request, passwordEncoder);
        member.setStatus(UserStatus.ACTIVE);

        // 회원 정보 저장
        memberRepository.save(member);

        return member;
    }

    /**
     * ID 중복 확인
     */
    public Boolean confirmUserId(String userId) {
        // 아이디 중복 체크
        return !memberRepository.existsByUserIdAndStatusNot(userId, UserStatus.DELETED);
    }

    /**
     * 이메일 중복 확인
     */
    public Boolean confirmEmail(String email) {
        // 이메일 중복 체크
        return !memberRepository.existsByEmailAndStatusNot(email, UserStatus.DELETED);
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

    /**
     * 로그아웃
     */
    public void logout(Long accountId) {
        // 현재 인증된 사용자의 토큰을 무효화하는 로직
        String accessToken = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        if (jwtTokenProvider.validateAccessToken(accessToken)) {
            jwtTokenProvider.addTokenToBlacklist(accessToken); // 토큰 블랙리스트에 추가
        }
    }

    private Member getMember(Long memberId) {
        return memberRepository.findByIdAndStatusNot(memberId, UserStatus.DELETED)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
    }

    public MemberResponseDTO.MemberDetailsDTO getMemberDetails(Long id) {
        Member member = memberRepository.findByIdAndStatusNot(id, UserStatus.DELETED)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));
        return MemberConverter.toMemberDetailsDTO(member);
    }

    public void updatePassword(MemberRequestDTO.PasswordUpdateDTO request, Long id) {
        validatePasswordFormat(request.getNewPassword());
        Member member = getMember(id);

        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new MemberException(MemberErrorCode.PASSWORD_CONFIRM_NOT_MATCHED);
        }

        member.updatePassword(passwordEncoder.encode(request.getNewPassword()));
    }

    private String generateVerificationCode() {
        SecureRandom random = new SecureRandom();
        int code = random.nextInt(900000) + 100000; // 6자리 숫자 생성 (100000 ~ 999999)
        return String.valueOf(code);
    }

    private void saveVerificationToken(String email, String code) {
        EmailVerificationToken verificationToken = EmailVerificationToken.builder()
                .email(email)
                .token(code)
                .expiryDate(LocalDateTime.now().plusMinutes(5))
                .build();

        emailVerificationTokenRepository.deleteByEmail(email);
        emailVerificationTokenRepository.save(verificationToken);
    }

    private boolean verifyToken(String email, String token) {
        Optional<EmailVerificationToken> verificationTokenOpt = emailVerificationTokenRepository.findByEmailAndToken(email, token);
        if (verificationTokenOpt.isPresent()) {
            EmailVerificationToken verificationToken = verificationTokenOpt.get();
            return !verificationToken.isExpired();
        }
        return false;
    }

    /**
     * 검증 메서드
     */
    private boolean isValidPassword(String password) {
        return password.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}");
    }

    private void validateMember(String userId) {
        Member member = memberRepository.findByUserIdAndStatusNot(userId, UserStatus.DELETED)
                .orElseThrow(() -> new MemberException(MemberErrorCode.MEMBER_NOT_FOUND));

        if (member.isDeleted()) {
            throw new MemberException(MemberErrorCode.MEMBER_NOT_FOUND);
        }
    }

    private void validateDuplicateEmail(MemberRequestDTO.JoinDTO request) {
        if (Boolean.TRUE.equals(memberRepository.existsByEmailAndStatusNot(request.getEmail(), UserStatus.DELETED))) {
            throw new MemberException(MemberErrorCode.DUPLICATE_MEMBER_EMAIL);
        }
    }

    public void validatePasswordFormat(String password) {
        if (!password.matches("(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}")) {
            throw new MemberException(MemberErrorCode.PASSWORD_FORMAT_NOT_MATCHED);
        }
    }

    public void checkPassword(String password, Long id) {
        Member member = getMember(id);
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new MemberException(MemberErrorCode.PASSWORD_CONFIRM_NOT_MATCHED);
        }
    }
}
