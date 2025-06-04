package com.example.movie_ticket_seller.dto;

import com.example.movie_ticket_seller.model.ShowTime;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class MovieDto {
    private UUID id;
    private String title;
    private String genre;
    private Integer durationMinutes;
    private String director;
    private String description;
    private List<String> cast;
}
