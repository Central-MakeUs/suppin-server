package com.cmc.suppin.survey.domain;

import com.cmc.suppin.answer.domain.AnswerOption;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class QuestionOption {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "question_option_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(columnDefinition = "VARCHAR(50)", nullable = false)
    private String optionText;

    @OneToMany(mappedBy = "option")
    private List<AnswerOption> answerOptionList = new ArrayList<>();

}

