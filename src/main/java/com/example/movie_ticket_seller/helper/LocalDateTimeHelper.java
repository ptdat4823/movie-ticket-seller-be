package com.example.movie_ticket_seller.helper;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeHelper {
    public static String ToISOString(LocalDateTime localDateTime)
    {
        return localDateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public static LocalDateTime FromISOString(String isoString)
    {
        return ZonedDateTime.parse(isoString).toLocalDateTime();
    }
}
