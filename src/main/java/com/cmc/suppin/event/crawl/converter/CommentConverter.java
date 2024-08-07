package com.cmc.suppin.event.crawl.converter;

import com.cmc.suppin.event.crawl.domain.Comment;
import com.cmc.suppin.event.events.domain.Event;

import java.time.LocalDateTime;

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
}

