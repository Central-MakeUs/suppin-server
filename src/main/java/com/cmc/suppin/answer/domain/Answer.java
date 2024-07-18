package com.cmc.suppin.answer.domain;

import com.cmc.suppin.survey.domain.Question;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Answer {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "answer_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @ManyToOne
    @JoinColumn(name = "anonymous_participant_id")
    private AnonymousParticipant anonymousParticipant;

    @OneToMany(mappedBy = "answer")
    private List<AnswerOption> answerOptionList = new ArrayList<>();

    @Column(columnDefinition = "TEXT", nullable = false)
    private String answerText;

}

