package com.cmc.suppin.event.crawl.converter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateConverter {

    public static LocalDateTime convertRelativeTime(String relativeTime) {
        LocalDateTime now = LocalDateTime.now();
        if (relativeTime.contains("일 전")) {
            int days = extractNumber(relativeTime);
            return now.minusDays(days);
        } else if (relativeTime.contains("시간 전")) {
            int hours = extractNumber(relativeTime);
            return now.minusHours(hours);
        } else if (relativeTime.contains("분 전")) {
            int minutes = extractNumber(relativeTime);
            return now.minusMinutes(minutes);
        } else if (relativeTime.contains("주 전")) {
            int weeks = extractNumber(relativeTime);
            return now.minusWeeks(weeks);
        } else if (relativeTime.contains("개월 전")) {
            int months = extractNumber(relativeTime);
            return now.minusMonths(months);
        } else if (relativeTime.contains("년 전")) {
            int years = extractNumber(relativeTime);
            return now.minusYears(years);
        }
        return now;
    }

    private static int extractNumber(String relativeTime) {
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(relativeTime);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group());
        }
        return 0;
    }

    public static String formatLocalDateTime(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }
}
