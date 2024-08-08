package com.cmc.suppin.event.survey.domain.repository;

import com.cmc.suppin.event.survey.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuestionRepository extends JpaRepository<Question, Long> {


    Optional<Question> findByIdAndSurveyId(Long questionId, Long surveyId);
}
