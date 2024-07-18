package com.cmc.suppin.global.enums;

public enum QuestionType {
    SHORT_ANSWER("주관식"),
    SINGLE_CHOICE("객관식(단일 선택)"),
    MULTIPLE_CHOICE("객관식(복수 선택)");

    private String description;

    QuestionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
