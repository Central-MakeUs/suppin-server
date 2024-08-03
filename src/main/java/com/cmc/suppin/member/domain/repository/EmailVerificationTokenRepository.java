package com.cmc.suppin.member.domain.repository;

import com.cmc.suppin.member.domain.EmailVerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {
    Optional<EmailVerificationToken> findByEmailAndToken(String email, String token);

    void deleteByEmail(String email);
}
