package com.cmc.suppin.member.domain.repository;

import com.cmc.suppin.member.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByUserIdAndStatusNot(String userId, String status);

    Boolean existsByEmailAndStatusNot(String email, String status);

    Optional<Member> findByUserIdAndStatusNot(String userId, String status);

    Optional<Member> findByIdAndStatusNot(Long id, String status);

    void deleteByUserId(String userId);
}
