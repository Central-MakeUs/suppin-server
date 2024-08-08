package com.cmc.suppin.event.survey.converter;

import com.cmc.suppin.event.events.domain.Event;
import com.cmc.suppin.event.survey.controller.dto.SurveyRequestDTO;
import com.cmc.suppin.event.survey.domain.PersonalInfoCollectOption;
import com.cmc.suppin.event.survey.domain.Question;
import com.cmc.suppin.event.survey.domain.QuestionOption;
import com.cmc.suppin.event.survey.domain.Survey;

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
                .questionType(questionDTO.getQuestionType())
                .questionText(questionDTO.getQuestionText())
                .build();
    }

    public static List<QuestionOption> toQuestionOptionEntities(List<String> options, Question question) {
        return options.stream()
                .map(option -> QuestionOption.builder()
                        .optionText(option)
                        .question(question)
                        .build())
                .collect(Collectors.toList());
    }

    public static List<PersonalInfoCollectOption> toPersonalInfoCollectOptionEntities(List<String> personalInfoOptions, Survey survey) {
        return personalInfoOptions.stream()
                .map(option -> PersonalInfoCollectOption.builder()
                        .optionName(option)
                        .survey(survey)
                        .build())
                .collect(Collectors.toList());
    }
}
