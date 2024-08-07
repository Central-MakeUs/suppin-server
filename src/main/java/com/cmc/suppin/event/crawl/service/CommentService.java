package com.cmc.suppin.event.crawl.service;

import com.cmc.suppin.event.crawl.controller.dto.CommentResponseDTO;
import com.cmc.suppin.event.crawl.converter.CommentConverter;
import com.cmc.suppin.event.crawl.domain.Comment;
import com.cmc.suppin.event.crawl.domain.repository.CommentRepository;
import com.cmc.suppin.event.events.domain.Event;
import com.cmc.suppin.event.events.domain.repository.EventRepository;
import com.cmc.suppin.global.enums.UserStatus;
import com.cmc.suppin.member.domain.Member;
import com.cmc.suppin.member.domain.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CommentService {

    private final CommentRepository commentRepository;
    private final EventRepository eventRepository;
    private final MemberRepository memberRepository;

    // 크롤링된 댓글 조회
    public CommentResponseDTO.CrawledCommentListDTO getComments(Long eventId, String url, int page, int size, String userId) {
        Member member = memberRepository.findByUserIdAndStatusNot(userId, UserStatus.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Event event = eventRepository.findByIdAndMemberId(eventId, member.getId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("commentDate").descending());
        Page<Comment> comments = commentRepository.findByEventIdAndUrlAndCommentDateBefore(eventId, url, event.getEndDate(), pageable);

        int totalComments = commentRepository.countByEventIdAndUrl(eventId, url);

        String crawlTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

        return CommentConverter.toCommentListDTO(comments.getContent(), crawlTime, totalComments);
    }

    // 당첨자 조건별 랜덤 추첨
    public List<CommentResponseDTO.WinnerResponseDTO> drawWinners(Long eventId, String startDate, String endDate, int winnerCount, List<String> keywords, String userId) {
        Member member = memberRepository.findByUserIdAndStatusNot(userId, UserStatus.DELETED)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        Event event = eventRepository.findByIdAndMemberId(eventId, member.getId())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        // String을 LocalDate로 변환하고 LocalDateTime으로 변환
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate start = LocalDate.parse(startDate, dateFormatter);
        LocalDate end = LocalDate.parse(endDate, dateFormatter);

        // LocalDateTime으로 변환
        LocalDateTime startDateTime = start.atStartOfDay();
        LocalDateTime endDateTime = end.atTime(LocalTime.MAX);

        // 당첨자 추첨 로직
        List<Comment> comments = commentRepository.findByEventIdAndCommentDateBetween(event.getId(), startDateTime, endDateTime);

        // 키워드 필터링(OR 로직)
        List<Comment> filteredComments = comments.stream()
                .filter(comment -> keywords.stream().anyMatch(keyword -> comment.getCommentText().contains(keyword)))
                .collect(Collectors.toList());

        // 랜덤 추첨
        Collections.shuffle(filteredComments);
        List<Comment> winners = filteredComments.stream().limit(winnerCount).collect(Collectors.toList());

        return winners.stream()
                .map(comment -> new CommentResponseDTO.WinnerResponseDTO(comment.getAuthor(), comment.getCommentText(), comment.getCommentDate().toString()))
                .collect(Collectors.toList());
    }

}
