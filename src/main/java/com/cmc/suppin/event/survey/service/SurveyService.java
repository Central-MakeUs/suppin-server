package com.cmc.suppin.event.survey.service;

import com.cmc.suppin.event.events.domain.Event;
import com.cmc.suppin.event.events.domain.repository.EventRepository;
import com.cmc.suppin.event.survey.controller.dto.SurveyRequestDTO;
import com.cmc.suppin.event.survey.controller.dto.SurveyResponseDTO;
import com.cmc.suppin.event.survey.converter.SurveyConverter;
import com.cmc.suppin.event.survey.domain.PersonalInfoCollectOption;
import com.cmc.suppin.event.survey.domain.Question;
import com.cmc.suppin.event.survey.domain.QuestionOption;
import com.cmc.suppin.event.survey.domain.Survey;
import com.cmc.suppin.event.survey.domain.repository.PersonalInfoCollectOptionRepository;
import com.cmc.suppin.event.survey.domain.repository.QuestionOptionRepository;
import com.cmc.suppin.event.survey.domain.repository.QuestionRepository;
import com.cmc.suppin.event.survey.domain.repository.SurveyRepository;
import com.cmc.suppin.global.enums.UserStatus;
import com.cmc.suppin.member.domain.Member;
import com.cmc.suppin.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class SurveyService {

    private final MemberRepository memberRepository;
    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final QuestionOptionRepository questionOptionRepository;
    private final PersonalInfoCollectOptionRepository personalInfoCollectOptionRepository;
    private final EventRepository eventRepository;

    @Transactional
    public SurveyResponseDTO.SurveyCreateResponse createSurvey(SurveyRequestDTO.SurveyCreateDTO request, String userId) {
        // 사용자 식별
        Member member = memberRepository.findByUserIdAndStatusNot(userId, UserStatus.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // 이벤트 식별
        Event event = eventRepository.findByIdAndMemberId(request.getEventId(), member.getId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found or does not belong to the user"));

        // Survey 엔티티 생성 및 저장
        String uuid = UUID.randomUUID().toString();
        Survey survey = SurveyConverter.toSurveyEntity(event, uuid);
        surveyRepository.save(survey);

        // 각 개인정보 항목 처리 및 저장
        if (request.getPersonalInfoOptionList() != null && !request.getPersonalInfoOptionList().isEmpty()) {
            List<PersonalInfoCollectOption> personalInfoOptions = SurveyConverter.toPersonalInfoCollectOptionEntities(
                    request.getPersonalInfoOptionList().stream().map(SurveyRequestDTO.SurveyCreateDTO.PersonalInfoOptionDTO::getOptionName).collect(Collectors.toList()), survey);
            personalInfoCollectOptionRepository.saveAll(personalInfoOptions);
        }

        // 각 질문 처리 및 저장
        for (SurveyRequestDTO.SurveyCreateDTO.QuestionDTO questionDTO : request.getQuestionList()) {
            Question question = SurveyConverter.toQuestionEntity(questionDTO, survey);
            questionRepository.save(question);

            // 객관식 복수 선택 질문인 경우 처리 및 저장
            if (questionDTO.getOptions() != null && !questionDTO.getOptions().isEmpty()) {
                List<QuestionOption> options = SurveyConverter.toQuestionOptionEntities(questionDTO.getOptions(), question);
                questionOptionRepository.saveAll(options);
            }
        }

        return SurveyResponseDTO.SurveyCreateResponse.builder()
                .surveyId(survey.getId())
                .uuid(survey.getUuid())
                .build();
    }

    @Transactional(readOnly = true)
    public SurveyResponseDTO.SurveyResultDTO getSurvey(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("Survey not found"));

        Event event = survey.getEvent();
        return SurveyConverter.toSurveyResultDTO(survey, event);
    }
}
