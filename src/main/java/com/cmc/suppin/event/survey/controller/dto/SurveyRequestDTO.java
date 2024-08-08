package com.cmc.suppin.event.survey.controller.dto;

import com.cmc.suppin.global.enums.QuestionType;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SurveyRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SurveyCreateDTO {
        private Long eventId;
        private String name;
        private String address;
        private String email;
        private String phoneNumber;
        private Boolean isAgreed;
        private List<QuestionDTO> questions;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class QuestionDTO {
            @NotEmpty(message = "질문 유형: SUBJECTIVE(주관식), SINGLE_CHOICE(객관식(단일 선택)), MULTIPLE_CHOICE(객관식(복수 선택))")
            private QuestionType questionType;
            private String questionText;
            private List<String> options; // 객관식 질문일 경우 선택지 리스트
        }
    }
}
