package com.cmc.suppin.survey.domain;

import com.cmc.suppin.answer.domain.AnonymousParticipant;
import com.cmc.suppin.event.domain.Event;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Survey {
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

