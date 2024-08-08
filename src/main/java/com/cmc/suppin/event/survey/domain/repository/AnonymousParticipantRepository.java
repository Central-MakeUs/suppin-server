package com.cmc.suppin.event.survey.domain.repository;

import com.cmc.suppin.event.survey.domain.AnonymousParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousParticipantRepository extends JpaRepository<AnonymousParticipant, Long> {
    boolean existsByPhoneNumberAndSurveyId(String phoneNumber, Long surveyId);

}
