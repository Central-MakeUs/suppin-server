package com.cmc.suppin.comment.domain;

import com.cmc.suppin.event.domain.Event;
import com.cmc.suppin.global.domain.BaseDateTimeEntity;

import javax.persistence.*;

@Entity
public class Comment extends BaseDateTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id")
    private Event event;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String url;
    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String nickname;
    @Column(columnDefinition = "TEXT", nullable = false)
    private String commentText;

}
