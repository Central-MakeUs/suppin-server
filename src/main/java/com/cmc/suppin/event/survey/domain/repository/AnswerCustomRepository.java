package com.cmc.suppin.event.survey.domain.repository;

import com.cmc.suppin.event.survey.domain.Answer;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnswerCustomRepository {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Answer> findEligibleAnswers(Long questionId, LocalDateTime startDate, LocalDateTime endDate, Integer minLength, List<String> keywords) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Answer> query = cb.createQuery(Answer.class);
        Root<Answer> answer = query.from(Answer.class);

        List<Predicate> predicates = new ArrayList<>();

        // 기본 조건 추가
        predicates.add(cb.equal(answer.get("question").get("id"), questionId));
        predicates.add(cb.greaterThanOrEqualTo(answer.get("createdAt"), startDate));
        predicates.add(cb.lessThanOrEqualTo(answer.get("createdAt"), endDate));
        predicates.add(cb.ge(cb.length(answer.get("answerText")), minLength));

        // 키워드 조건 추가
        if (keywords != null && !keywords.isEmpty()) {
            List<Predicate> keywordPredicates = new ArrayList<>();
            for (String keyword : keywords) {
                keywordPredicates.add(cb.like(cb.lower(answer.get("answerText")), "%" + keyword.toLowerCase() + "%"));
            }
            predicates.add(cb.or(keywordPredicates.toArray(new Predicate[0])));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
