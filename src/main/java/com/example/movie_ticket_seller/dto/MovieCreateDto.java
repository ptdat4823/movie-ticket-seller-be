package com.example.movie_ticket_seller.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class MovieCreateDto {
    private String title;
    private String genre;
    private Integer durationMinutes;
    private String director;
    private String description;
    private List<String> cast;
}
