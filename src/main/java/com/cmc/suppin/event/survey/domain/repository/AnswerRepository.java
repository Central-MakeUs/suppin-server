package com.cmc.suppin.event.survey.domain.repository;

import com.cmc.suppin.event.survey.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    List<Answer> findByQuestionId(Long questionId);
}
