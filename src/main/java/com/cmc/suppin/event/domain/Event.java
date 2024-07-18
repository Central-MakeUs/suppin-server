package com.cmc.suppin.event.domain;

import com.cmc.suppin.comment.domain.Comment;
import com.cmc.suppin.global.domain.BaseDateTimeEntity;
import com.cmc.suppin.global.enums.EventType;
import com.cmc.suppin.member.domain.Member;
import com.cmc.suppin.survey.domain.Survey;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "event_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToMany(mappedBy = "event")
    private List<Survey> surveyList = new ArrayList<>();

    @OneToMany(mappedBy = "event")
    private List<Comment> commentList = new ArrayList<>();

    @Column(columnDefinition = "VARCHAR(100)", nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventType type;

    @Column
    private LocalDateTime startDate;
    @Column
    private LocalDateTime endDate;
    @Column
    private LocalDateTime announcementDate;

    // Getters and Setters

}
