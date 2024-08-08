package com.cmc.suppin.event.survey.service;

import com.cmc.suppin.event.events.domain.Event;
import com.cmc.suppin.event.events.domain.repository.EventRepository;
import com.cmc.suppin.event.survey.controller.dto.SurveyRequestDTO;
import com.cmc.suppin.event.survey.controller.dto.SurveyResponseDTO;
import com.cmc.suppin.event.survey.converter.SurveyConverter;
import com.cmc.suppin.event.survey.domain.*;
import com.cmc.suppin.event.survey.domain.repository.*;
import com.cmc.suppin.event.survey.exception.SurveyErrorCode;
import com.cmc.suppin.event.survey.exception.SurveyException;
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
    private final AnonymousParticipantRepository anonymousParticipantRepository;
    private final AnswerRepository answerRepository;
    private final AnswerOptionRepository answerOptionRepository;

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

    // 생성된 설문지 조회
    @Transactional(readOnly = true)
    public SurveyResponseDTO.SurveyResultDTO getSurvey(Long surveyId) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new IllegalArgumentException("Survey not found"));

        Event event = survey.getEvent();
        return SurveyConverter.toSurveyResultDTO(survey, event);
    }

    // 설문 응답 저장
    @Transactional
    public void saveSurveyAnswers(SurveyRequestDTO.SurveyAnswerDTO request) {
        Survey survey = surveyRepository.findById(request.getSurveyId())
                .orElseThrow(() -> new IllegalArgumentException("Survey not found"));

        // 중복 핸드폰 번호 체크
        String phoneNumber = request.getParticipant().getPhoneNumber();
        boolean exists = anonymousParticipantRepository.existsByPhoneNumberAndSurveyId(phoneNumber, request.getSurveyId());
        if (exists) {
            throw new SurveyException(SurveyErrorCode.DUPLICATE_PHONENUMBER);
        }

        AnonymousParticipant participant = SurveyConverter.toAnonymousParticipant(request.getParticipant(), survey);
        anonymousParticipantRepository.save(participant);

        for (SurveyRequestDTO.SurveyAnswerDTO.AnswerDTO answerDTO : request.getAnswers()) {
            Question question = questionRepository.findById(answerDTO.getQuestionId())
                    .orElseThrow(() -> new IllegalArgumentException("Question not found"));

            Answer answer = SurveyConverter.toAnswer(answerDTO, question, participant);
            answerRepository.save(answer);

            if (answerDTO.getAnswerOptions() != null) {
                for (SurveyRequestDTO.SurveyAnswerDTO.AnswerDTO.AnswerOptionDTO optionDTO : answerDTO.getAnswerOptions()) {
                    QuestionOption questionOption = questionOptionRepository.findById(optionDTO.getQuestionOptionId())
                            .orElseThrow(() -> new IllegalArgumentException("QuestionOption not found"));

                    AnswerOption answerOption = SurveyConverter.toAnswerOption(optionDTO, answer, questionOption);
                    answerOptionRepository.save(answerOption);
                }
            }
        }
    }

    // 설문 응답 결과 조회
    @Transactional(readOnly = true)
    public SurveyResponseDTO.SurveyAnswerResultDTO getSurveyAnswers(Long surveyId, Long questionId, String userId) {
        // 사용자 식별
        Member member = memberRepository.findByUserIdAndStatusNot(userId, UserStatus.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Question question = questionRepository.findByIdAndSurveyId(questionId, surveyId)
                .orElseThrow(() -> new IllegalArgumentException("Question not found for the given survey"));

        List<Answer> answers = answerRepository.findByQuestionId(questionId);

        return SurveyConverter.toSurveyAnswerResultDTO(question, answers);
    }
}
