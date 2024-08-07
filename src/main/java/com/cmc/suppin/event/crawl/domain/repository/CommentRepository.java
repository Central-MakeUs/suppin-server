package com.cmc.suppin.event.crawl.domain.repository;

import com.cmc.suppin.event.crawl.domain.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEventId(Long eventId);

    List<Comment> findByUrl(String url);

    void deleteByUrl(String url);

    Page<Comment> findByEventIdAndUrlAndCommentDateBefore(Long eventId, String url, LocalDateTime endDate, Pageable pageable);
}
