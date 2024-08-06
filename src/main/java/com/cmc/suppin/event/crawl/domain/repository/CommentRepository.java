package com.cmc.suppin.event.crawl.domain.repository;

import com.cmc.suppin.event.crawl.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByEventId(Long eventId);

    List<Comment> findByVideoUrl(String videoUrl);
}
