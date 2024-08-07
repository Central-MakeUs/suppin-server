package com.cmc.suppin.event.crawl.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

public class CommentRequestDTO {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class CommentListRequestDTO {
        private Long eventId;
        private String url;
        private int page;
        private int size;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class WinnerRequestDTO {
        private Long eventId;
        private String startDate;
        private String endDate;
        private int winnerCount;
        private List<String> keywords;
    }
}
