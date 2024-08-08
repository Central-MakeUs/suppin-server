package com.cmc.suppin.event.survey.controller.dto;

import com.cmc.suppin.global.enums.QuestionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
        @NotNull
        private Long eventId;
        private List<PersonalInfoOptionDTO> personalInfoOptionList;
        private List<QuestionDTO> questionList;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class QuestionDTO {
            @NotBlank(message = "질문 유형: SUBJECTIVE(주관식), SINGLE_CHOICE(객관식(단일 선택)), MULTIPLE_CHOICE(객관식(복수 선택))")
            private QuestionType questionType;
            @NotBlank(message = "질문 내용을 입력해주세요")
            private String questionText;
            private List<String> options; // 객관식 질문일 경우 선택지 리스트
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class PersonalInfoOptionDTO {
            @NotBlank(message = "개인정보 수집 항목을 입력해주세요")
            private String optionName;
        }
    }
}
