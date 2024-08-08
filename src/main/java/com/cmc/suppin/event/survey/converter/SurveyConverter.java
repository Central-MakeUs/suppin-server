package com.cmc.suppin.event.survey.converter;

import com.cmc.suppin.answer.domain.AnonymousParticipant;
import com.cmc.suppin.event.events.domain.Event;
import com.cmc.suppin.event.survey.controller.dto.SurveyRequestDTO;
import com.cmc.suppin.event.survey.domain.Question;
import com.cmc.suppin.event.survey.domain.QuestionOption;
import com.cmc.suppin.event.survey.domain.Survey;
import com.cmc.suppin.global.enums.QuestionType;

import java.util.List;
import java.util.stream.Collectors;

public class SurveyConverter {

    public static Survey toSurveyEntity(Event event) {
        return Survey.builder()
                .event(event)
                .build();
    }

    public static Question toQuestionEntity(SurveyRequestDTO.SurveyCreateDTO.QuestionDTO questionDTO, Survey survey) {
        return Question.builder()
                .survey(survey)
                .questionType(QuestionType.valueOf(questionDTO.getQuestionType().name()))
                .questionText(questionDTO.getQuestionText())
                .build();
    }

    public static List<QuestionOption> toQuestionOptionEntities(List<String> options, Question question) {
        return options.stream()
                .map(optionText -> QuestionOption.builder()
                        .question(question)
                        .optionText(optionText)
                        .build())
                .collect(Collectors.toList());
    }

    public static AnonymousParticipant toAnonymousParticipantEntity(SurveyRequestDTO.SurveyCreateDTO request, Survey survey) {
        return AnonymousParticipant.builder()
                .survey(survey)
                .name(request.getName())
                .address(request.getAddress())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .isAgreed(request.getIsAgreed())
                .build();
    }
}
