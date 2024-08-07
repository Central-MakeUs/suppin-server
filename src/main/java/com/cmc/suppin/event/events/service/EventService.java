package com.cmc.suppin.event.events.service;

import com.cmc.suppin.event.events.controller.dto.EventRequestDTO;
import com.cmc.suppin.event.events.controller.dto.EventResponseDTO;
import com.cmc.suppin.event.events.converter.EventConverter;
import com.cmc.suppin.event.events.domain.Event;
import com.cmc.suppin.event.events.domain.repository.EventRepository;
import com.cmc.suppin.global.enums.EventStatus;
import com.cmc.suppin.global.enums.EventType;
import com.cmc.suppin.global.enums.UserStatus;
import com.cmc.suppin.member.domain.Member;
import com.cmc.suppin.member.domain.repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    // 이벤트 상태 업데이트 메서드
    @Transactional
    public void updateEventStatus() {
        LocalDateTime now = LocalDateTime.now();
        List<Event> events = eventRepository.findAll();

        for (Event event : events) {
            if (event.getEndDate().isBefore(now) && event.getStatus() != EventStatus.DONE) {
                event.setStatus(EventStatus.DONE);
            } else if (event.getEndDate().isAfter(now) && event.getStatus() != EventStatus.PROCESSING) {
                event.setStatus(EventStatus.PROCESSING);
            }
        }
    }

    public List<EventResponseDTO.EventInfoDTO> getAllEvents(String userId) {
        Member member = memberRepository.findByUserIdAndStatusNot(userId, UserStatus.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        List<Event> events = eventRepository.findByMemberId(member.getId());
        return events.stream()
                .map(EventConverter::toEventInfoDTO)
                .collect(Collectors.toList());
    }

    public Event createCommentEvent(EventRequestDTO.CommentEventCreateDTO request, String userId) {
        Member member = memberRepository.findByUserIdAndStatusNot(userId, UserStatus.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (request.getType() != EventType.COMMENT) {
            throw new IllegalArgumentException("Event type must be COMMENT");
        }

        Event event = EventConverter.toCommentEventEntity(request, member);
        event.setMember(member);
        event.setStatus(EventStatus.PROCESSING);
        return eventRepository.save(event);
    }

    public Event createSurveyEvent(EventRequestDTO.SurveyEventCreateDTO request, String userId) {
        Member member = memberRepository.findByUserIdAndStatusNot(userId, UserStatus.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        if (request.getType() != EventType.SURVEY) {
            throw new IllegalArgumentException("Event type must be SURVEY");
        }

        Event event = EventConverter.toSurveyEventEntity(request, member);
        event.setMember(member);
        event.setStatus(EventStatus.PROCESSING);
        return eventRepository.save(event);
    }

    public void updateEvent(Long eventId, EventRequestDTO.EventUpdateDTO request, String userId) {
        Member member = memberRepository.findByUserIdAndStatusNot(userId, UserStatus.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Event event = eventRepository.findByIdAndMemberId(eventId, member.getId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Event updatedEvent = EventConverter.toUpdatedEventEntity(request, member);
        updatedEvent.setId(event.getId());  // 유지하려는 ID 설정
        eventRepository.save(updatedEvent);
    }

    public void deleteEvent(Long eventId, String userId) {
        Member member = memberRepository.findByUserIdAndStatusNot(userId, UserStatus.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Event event = eventRepository.findByIdAndMemberId(eventId, member.getId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        eventRepository.delete(event);
    }
}
