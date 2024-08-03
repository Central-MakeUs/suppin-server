package com.cmc.suppin.event.events.domain.repository;

import com.cmc.suppin.event.events.domain.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByMemberId(Long memberId);
}
