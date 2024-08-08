package com.cmc.suppin.event.survey.domain.repository;

import com.cmc.suppin.event.survey.domain.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findByQuestionId(Long questionId, Pageable pageable);

    @Query("SELECT a FROM Answer a WHERE a.question.id = :questionId AND " +
            "a.createdAt >= :startDate AND " +
            "a.createdAt <= :endDate AND " +
            "LENGTH(a.answerText) >= :minLength AND " +
            "(:keywordList IS NULL OR SIZE(:keywordList) = 0 OR " +
            "(LOWER(a.answerText) LIKE LOWER(CONCAT('%', :keywordList[0], '%')) " +
            "OR LOWER(a.answerText) LIKE LOWER(CONCAT('%', :keywordList[1], '%')) " +
            "OR LOWER(a.answerText) LIKE LOWER(CONCAT('%', :keywordList[2], '%')) ... ))")
    List<Answer> findEligibleAnswers(@Param("questionId") Long questionId,
                                     @Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate,
                                     @Param("minLength") Integer minLength,
                                     @Param("keywordList") List<String> keywordList);

}

