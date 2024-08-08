package com.cmc.suppin.answer.domain.repository;

import com.cmc.suppin.answer.domain.Answer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
