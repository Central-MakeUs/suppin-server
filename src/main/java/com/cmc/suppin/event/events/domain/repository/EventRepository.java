package com.cmc.suppin.event.events.domain.repository;

import com.cmc.suppin.event.events.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByMemberId(Long memberId);

    Optional<Event> findByIdAndMemberId(Long id, Long memberId);

}
