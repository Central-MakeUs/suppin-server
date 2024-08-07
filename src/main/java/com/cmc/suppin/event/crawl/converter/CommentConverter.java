package com.cmc.suppin.event.crawl.converter;

import com.cmc.suppin.event.crawl.domain.Comment;
import com.cmc.suppin.event.events.domain.Event;

public class CommentConverter {

    public static Comment toCommentEntity(String author, String text, String time, String url, Event event) {
        return Comment.builder()
                .author(author)
                .commentText(text)
                .commentDate(time)
                .url(url)
                .event(event)
                .build();
    }
}
