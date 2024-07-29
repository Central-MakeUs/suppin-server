package com.cmc.suppin.member.domain;

import com.cmc.suppin.event.domain.Event;
import com.cmc.suppin.global.domain.BaseDateTimeEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Member extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Event> eventList = new ArrayList<>();

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String nickname;

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String email;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String password;

    @Column(columnDefinition = "VARCHAR(13)", nullable = false)
    private String phoneNumber;

    private String role;

}

