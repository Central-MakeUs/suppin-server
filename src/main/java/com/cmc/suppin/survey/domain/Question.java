package com.cmc.suppin.survey.domain;

import com.cmc.suppin.answer.domain.Answer;
import com.cmc.suppin.global.enums.QuestionType;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToMany(mappedBy = "question")
    private List<QuestionOption> questionOptionList = new ArrayList<>();

    @OneToMany(mappedBy = "question")
    private List<Answer> answerList = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private QuestionType questionType;

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String questionText;

}
