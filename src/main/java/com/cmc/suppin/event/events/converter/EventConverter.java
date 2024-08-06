package com.cmc.suppin.event.events.converter;

import com.cmc.suppin.event.events.controller.dto.EventRequestDTO;
import com.cmc.suppin.event.events.controller.dto.EventResponseDTO;
import com.cmc.suppin.event.events.domain.Event;
import com.cmc.suppin.member.domain.Member;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class EventConverter {

    public static Event toCommentEventEntity(EventRequestDTO.CommentEventCreateDTO request, Member member) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return Event.builder()
                .type(request.getType())
                .title(request.getTitle())
                .url(request.getUrl())
                .startDate(LocalDate.parse(request.getStartDate(), formatter).atStartOfDay())
                .endDate(LocalDate.parse(request.getEndDate(), formatter).atStartOfDay())
                .announcementDate(LocalDate.parse(request.getAnnouncementDate(), formatter).atStartOfDay())
                .description(request.getDescription())
                .member(member)
                .build();
    }

    public static EventResponseDTO.CommentEventDetailDTO toEventDetailDTO(Event event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return EventResponseDTO.CommentEventDetailDTO.builder()
                .type(event.getType())
                .title(event.getTitle())
                .url(event.getUrl())
                .startDate(event.getStartDate().format(formatter))
                .endDate(event.getEndDate().format(formatter))
                .announcementDate(event.getAnnouncementDate().format(formatter))
                .build();
    }

    public static EventResponseDTO.EventInfoDTO toEventInfoDTO(Event event) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return EventResponseDTO.EventInfoDTO.builder()
                .type(event.getType())
                .title(event.getTitle())
                .url(event.getUrl())
                .startDate(event.getStartDate().format(formatter))
                .endDate(event.getEndDate().format(formatter))
                .announcementDate(event.getAnnouncementDate().format(formatter))
                .surveyCount(event.getSurveyList().size())
                .commentCount(event.getCommentList().size())
                .status(event.getStatus())
                .build();
    }
}
