package com.cmc.suppin.answer.domain.repository;

import com.cmc.suppin.answer.domain.AnonymousParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnonymousParticipantRepository extends JpaRepository<AnonymousParticipant, Long> {
}
