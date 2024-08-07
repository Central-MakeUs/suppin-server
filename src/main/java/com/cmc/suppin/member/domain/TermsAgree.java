package com.cmc.suppin.member.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TermsAgree {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private Boolean ageOver14Agree;

    @Column(nullable = false)
    private Boolean serviceUseAgree;

    @Column(nullable = false)
    private Boolean personalInfoAgree;

    @Column(nullable = true)
    private Boolean marketingAgree;
}

