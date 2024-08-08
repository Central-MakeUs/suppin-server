package com.cmc.suppin.event.survey.domain.repository;

import com.cmc.suppin.event.survey.domain.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {
    Page<Answer> findByQuestionId(Long questionId, Pageable pageable);

    /*
    @Query("SELECT a FROM Answer a WHERE a.question.id = :questionId AND " +
            "a.createdAt >= :startDate AND " +
            "a.createdAt <= :endDate AND " +
            "LENGTH(a.answerText) >= :minLength AND " +
            "(COALESCE(:keywords, NULL) IS NULL OR " +
            "EXISTS (SELECT 1 FROM Answer a2 WHERE a2.id = a.id AND (" +
            "LOWER(a2.answerText) LIKE LOWER(CONCAT('%', :#{#keywords[0]}, '%'))" +
            " OR LOWER(a2.answerText) LIKE LOWER(CONCAT('%', :#{#keywords[1]}, '%'))" +
            // 추가적인 키워드 OR 조건을 여기에 동적으로 추가해야 합니다.
            ")))")
    List<Answer> findEligibleAnswers(@Param("questionId") Long questionId,
                                     @Param("startDate") LocalDateTime startDate,
                                     @Param("endDate") LocalDateTime endDate,
                                     @Param("minLength") Integer minLength,
                                     @Param("keywords") List<String> keywords);
     */
}

