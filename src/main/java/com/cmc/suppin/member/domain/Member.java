package com.cmc.suppin.member.domain;

import com.cmc.suppin.event.domain.Event;
import com.cmc.suppin.global.domain.BaseDateTimeEntity;
import com.cmc.suppin.global.enums.UserRole;
import com.cmc.suppin.global.enums.UserStatus;
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
public class Member extends BaseDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @OneToMany(mappedBy = "member")
    private List<Event> eventList = new ArrayList<>();

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String userId;

    @Column(columnDefinition = "VARCHAR(20)", nullable = false)
    private String name;

    @Column(columnDefinition = "VARCHAR(30)", nullable = false)
    private String email;

    @Column(columnDefinition = "VARCHAR(255)", nullable = false)
    private String password;

    @Column(columnDefinition = "VARCHAR(13)", nullable = false)
    private String phoneNumber;

    private Boolean termsAgree;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    private UserStatus status;

    // 추가된 생성자
    public Member(String name, String password, String email, String phoneNumber, UserRole role) {
        this.name = name;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public boolean isDeleted() {
        return this.status == UserStatus.DELETED;
    }

    public void delete() {
        this.status = UserStatus.DELETED;
    }
}

