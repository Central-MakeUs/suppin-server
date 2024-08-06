package com.cmc.suppin.event.events.converter;

import com.cmc.suppin.event.events.controller.dto.EventRequestDTO;
import com.cmc.suppin.event.events.controller.dto.EventResponseDTO;
import com.cmc.suppin.event.events.domain.Event;
import com.cmc.suppin.member.domain.Member;

import java.time.LocalDateTime;

public class EventConverter {

    public static Event toCommentEventEntity(EventRequestDTO.CommentEventCreateDTO request, Member member) {
        return Event.builder()
                .type(request.getType())
                .title(request.getTitle())
                .url(request.getUrl())
                .startDate(LocalDateTime.parse(request.getStartDate()))
                .endDate(LocalDateTime.parse(request.getEndDate()))
                .announcementDate(LocalDateTime.parse(request.getAnnouncementDate()))
                .member(member)
                .build();
    }

    public static EventResponseDTO.CommentEventDetailDTO toEventDetailDTO(Event event) {
        return EventResponseDTO.CommentEventDetailDTO.builder()
                .type(event.getType())
                .title(event.getTitle())
                .url(event.getUrl())
                .startDate(event.getStartDate().toString())
                .endDate(event.getEndDate().toString())
                .announcementDate(event.getAnnouncementDate().toString())
                .build();
    }
}
