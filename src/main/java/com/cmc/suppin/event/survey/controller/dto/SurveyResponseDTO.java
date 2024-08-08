package com.cmc.suppin.event.survey.controller.dto;

import com.cmc.suppin.global.enums.QuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class SurveyResponseDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SurveyCreateResponse {
        private Long surveyId;
        private String uuid;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SurveyResultDTO {
        private Long eventId;
        private String eventTitle;
        private String eventDescription;
        private String startDate;
        private String endDate;
        private String announcementDate;
        private List<PersonalInfoOptionDTO> personalInfoOptions;
        private List<QuestionDTO> questions;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class QuestionDTO {
            private QuestionType questionType;
            private String questionText;
            private List<String> options;
        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class PersonalInfoOptionDTO {
            private String optionName;
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class SurveyAnswerResultDTO {
        private Long questionId;
        private String questionText;
        private List<AnswerDTO> answers;

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class AnswerDTO {
            private String participantName;
            private String answerText;
            private List<String> selectedOptions;
        }
    }
}
