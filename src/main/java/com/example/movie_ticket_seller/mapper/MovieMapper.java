package com.example.movie_ticket_seller.mapper;

import com.example.movie_ticket_seller.dto.MovieCreateDto;
import com.example.movie_ticket_seller.dto.MovieDto;
import com.example.movie_ticket_seller.dto.ShowTimeDto;
import com.example.movie_ticket_seller.model.Movie;
import com.example.movie_ticket_seller.model.ShowTime;

import java.util.List;

public class MovieMapper {
    public static MovieDto ToDto(Movie entity)
    {
        return MovieDto.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .genre(entity.getGenre())
                .durationMinutes(entity.getDurationMinutes())
                .director(entity.getDirector())
                .description(entity.getDescription())
                .cast(entity.getCast())
                .build();
    }

    public static Movie FromCreateDto(MovieCreateDto dto)
    {
        return Movie.builder()
                .title(dto.getTitle())
                .genre(dto.getGenre())
                .durationMinutes(dto.getDurationMinutes())
                .director(dto.getDirector())
                .description(dto.getDescription())
                .cast(dto.getCast())
                .isDeleted(false)
                .build();
    }
}
