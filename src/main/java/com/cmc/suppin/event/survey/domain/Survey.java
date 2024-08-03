package com.cmc.suppin.event.survey.domain;

import com.cmc.suppin.answer.domain.AnonymousParticipant;
import com.cmc.suppin.event.domain.Event;
import com.cmc.suppin.global.domain.BaseDateTimeEntity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Survey extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @OneToMany(mappedBy = "survey")
    private List<Question> questionList = new ArrayList<>();

    @OneToMany(mappedBy = "survey")
    private List<AnonymousParticipant> anonymousParticipantList = new ArrayList<>();

}

