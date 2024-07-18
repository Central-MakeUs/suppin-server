package com.cmc.suppin.answer.domain;

import com.cmc.suppin.global.domain.BaseDateTimeEntity;
import com.cmc.suppin.survey.domain.Survey;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class AnonymousParticipant extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anonymous_participant_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String sessionId;

    @OneToMany(mappedBy = "anonymousParticipant")
    private List<Answer> answerList = new ArrayList<>();
}