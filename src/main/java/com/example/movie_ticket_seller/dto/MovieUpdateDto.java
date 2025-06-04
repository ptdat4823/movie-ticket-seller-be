package com.example.movie_ticket_seller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovieUpdateDto {
    private String title;
    private String genre;
    private Integer durationMinutes;
    private String director;
    private String description;
    private List<String> cast;
}
