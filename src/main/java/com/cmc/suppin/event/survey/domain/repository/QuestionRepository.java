package com.cmc.suppin.event.survey.domain.repository;

import com.cmc.suppin.event.survey.domain.Question;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
}
