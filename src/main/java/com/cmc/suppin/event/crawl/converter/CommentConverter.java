package com.cmc.suppin.event.crawl.converter;

import com.cmc.suppin.event.crawl.controller.dto.CommentResponseDTO;
import com.cmc.suppin.event.crawl.domain.Comment;
import com.cmc.suppin.event.events.domain.Event;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class CommentConverter {

    public static Comment toCommentEntity(String author, String text, LocalDateTime commentDate, String url, Event event) {
        return Comment.builder()
                .author(author)
                .commentText(text)
                .commentDate(commentDate)
                .url(url)
                .event(event)
                .build();
    }

    public static CommentResponseDTO.CommentDetailDTO toCommentDetailDTO(Comment comment) {
        return CommentResponseDTO.CommentDetailDTO.builder()
                .author(comment.getAuthor())
                .commentText(comment.getCommentText())
                .commentDate(comment.getCommentDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")))
                .build();
    }

    public static CommentResponseDTO.CrawledCommentListDTO toCommentListDTO(List<Comment> comments, String crawlTime) {
        List<CommentResponseDTO.CommentDetailDTO> commentDetailDTOS = comments.stream()
                .map(CommentConverter::toCommentDetailDTO)
                .collect(Collectors.toList());

        return CommentResponseDTO.CrawledCommentListDTO.builder()
                .participantCount(commentDetailDTOS.size())
                .crawlTime(crawlTime)
                .comments(commentDetailDTOS)
                .build();
    }
}

