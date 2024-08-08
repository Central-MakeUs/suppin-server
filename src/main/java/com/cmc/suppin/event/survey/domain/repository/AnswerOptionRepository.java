package com.cmc.suppin.event.survey.domain.repository;

import com.cmc.suppin.event.survey.domain.AnswerOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {
}
