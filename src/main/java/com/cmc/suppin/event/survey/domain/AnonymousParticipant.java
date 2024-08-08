package com.cmc.suppin.event.survey.domain;

import com.cmc.suppin.global.domain.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@DynamicInsert
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class AnonymousParticipant extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "anonymous_participant_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @OneToMany(mappedBy = "anonymousParticipant")
    private List<Answer> answerList = new ArrayList<>();

    private String name;

    private String address;

    private String email;

    private String instagramId;

    @Column(columnDefinition = "VARCHAR(20)", unique = true)
    private String phoneNumber;

    @Column(nullable = false)
    private Boolean isAgreed;

    private Boolean isWinner;

    private Boolean isChecked;
}
