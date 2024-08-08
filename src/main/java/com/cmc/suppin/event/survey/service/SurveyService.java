package com.cmc.suppin.event.survey.service;

import com.cmc.suppin.answer.domain.AnonymousParticipant;
import com.cmc.suppin.answer.domain.repository.AnonymousParticipantRepository;
import com.cmc.suppin.event.events.domain.Event;
import com.cmc.suppin.event.events.domain.repository.EventRepository;
import com.cmc.suppin.event.survey.controller.dto.SurveyRequestDTO;
import com.cmc.suppin.event.survey.converter.SurveyConverter;
import com.cmc.suppin.event.survey.domain.Question;
import com.cmc.suppin.event.survey.domain.QuestionOption;
import com.cmc.suppin.event.survey.domain.Survey;
import com.cmc.suppin.event.survey.domain.repository.QuestionOptionRepository;
import com.cmc.suppin.event.survey.domain.repository.QuestionRepository;
import com.cmc.suppin.event.survey.domain.repository.SurveyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final EventRepository eventRepository;
    private final AnonymousParticipantRepository anonymousParticipantRepository;

    @Transactional
    public void createSurvey(SurveyRequestDTO.SurveyCreateDTO request, String userId) {
        // 이벤트 식별
        Event event = eventRepository.findById(request.getEventId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        // Survey 엔티티 생성 및 저장
        Survey survey = SurveyConverter.toSurveyEntity(event);
        surveyRepository.save(survey);

        // 각 질문 처리 및 저장
        for (SurveyRequestDTO.SurveyCreateDTO.QuestionDTO questionDTO : request.getQuestions()) {
            Question question = SurveyConverter.toQuestionEntity(questionDTO, survey);
            questionRepository.save(question);

            // 객관식 복수 선택 질문인 경우 처리 및 저장
            if (questionDTO.getOptions() != null && !questionDTO.getOptions().isEmpty()) {
                List<QuestionOption> options = SurveyConverter.toQuestionOptionEntities(questionDTO.getOptions(), question);
                questionOptionRepository.saveAll(options);
            }
        }

        // 익명 설문 참여자 정보 저장
        AnonymousParticipant participant = SurveyConverter.toAnonymousParticipantEntity(request, survey);
        anonymousParticipantRepository.save(participant);
    }
}
