package com.cmc.suppin.member.domain.repository;

import com.cmc.suppin.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByUserId(String userId);

    Optional<Member> findByUserId(String userId);

    void deleteByUserId(String userId);
}
