package com.cmc.suppin.answer.domain;

import com.cmc.suppin.survey.domain.QuestionOption;

import javax.persistence.*;

@Entity
public class AnswerOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_option_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "option_id")
    private QuestionOption questionOption;
}

