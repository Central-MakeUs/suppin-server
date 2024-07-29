package com.cmc.suppin.answer.domain;

import com.cmc.suppin.survey.domain.QuestionOption;
import jakarta.persistence.*;


@Entity
public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_option_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_id", nullable = false)
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "question_option_id", nullable = false)
    private QuestionOption questionOption;
}

