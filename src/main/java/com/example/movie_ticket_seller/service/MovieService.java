package com.example.movie_ticket_seller.service;

import com.example.movie_ticket_seller.dto.*;
import com.example.movie_ticket_seller.exception.NotFoundException;
import com.example.movie_ticket_seller.mapper.MovieMapper;
import com.example.movie_ticket_seller.model.Movie;
import com.example.movie_ticket_seller.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieDto getMovieById(UUID id) {
        Movie movie = movieRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Movie not found with id: " + id));
        return MovieMapper.ToDto(movie);
    }

    public UUID createMovie(MovieCreateDto dto)
    {
        // add already existed exception
        var toCreate = MovieMapper.FromCreateDto(dto);
        var created = movieRepository.save(toCreate);
        return created.getId();
    }

    public void updateMovie(UUID id, MovieUpdateDto dto) {
        var toUpdate = movieRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Movie not found with id: " + id));

        // Only update fields that are non-null
        if (dto.getTitle() != null) toUpdate.setTitle(dto.getTitle());
        if (dto.getGenre() != null) toUpdate.setGenre(dto.getGenre());
        if (dto.getDurationMinutes() != null) toUpdate.setDurationMinutes(dto.getDurationMinutes());
        if (dto.getDirector() != null) toUpdate.setDirector(dto.getDirector());
        if (dto.getDescription() != null) toUpdate.setDescription(dto.getDescription());
        if (dto.getCast() != null) toUpdate.setCast(dto.getCast());

        movieRepository.save(toUpdate);
    }

    public void deleteMovie(UUID id)
    {
        var toDelete = movieRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Movie not found with id: " + id));

        toDelete.setIsDeleted(true);
        movieRepository.save(toDelete);
    }
}
