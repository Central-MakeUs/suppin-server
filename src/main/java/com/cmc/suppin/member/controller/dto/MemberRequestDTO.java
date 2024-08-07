package com.cmc.suppin.member.controller.dto;

import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class MemberRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JoinDTO {

        private TermsAgreeDTO termsAgree;

        @NotBlank(message = "아이디를 입력해주세요")
        @Id
        private String userId;

        @NotBlank(message = "이름을 입력해주세요")
        private String name;

        @NotBlank(message = "비밀번호를 입력해주세요")
        @Pattern(regexp = "(?=.*[0-9])(?=.*[a-zA-Z])(?=.*\\W)(?=\\S+$).{8,20}", message = "비밀번호는 8~20자 영문, 숫자, 특수문자를 사용하세요.")
        private String password;

        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "휴대폰 번호를 입력해주세요")
        private String phone;

        private String userType;

        @NotBlank(message = "이메일 인증번호를 입력해주세요")
        private String verificationCode;

    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TermsAgreeDTO {
        @NotNull(message = "14세 이상 동의 여부를 선택해주세요")
        private Boolean ageOver14Agree;

        @NotNull(message = "서비스 이용 동의 여부를 선택해주세요")
        private Boolean serviceUseAgree;

        @NotNull(message = "개인정보 수집 및 이용 동의 여부를 선택해주세요")
        private Boolean personalInfoAgree;

        private Boolean marketingAgree; // 선택 사항
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class IdConfirmDTO {
        private String userId;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailConfirmDTO {
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class LoginRequestDTO {
        private String userId;
        private String password;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PasswordUpdateDTO {
        private String password;
        private String newPassword;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailRequestDTO {
        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class EmailVerificationDTO {
        @NotBlank(message = "이메일을 입력해주세요")
        @Email(message = "이메일 형식이 올바르지 않습니다.")
        private String email;

        @NotBlank(message = "인증번호를 입력해주세요")
        private String verificationCode;
    }
}
