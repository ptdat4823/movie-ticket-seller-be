package com.example.movie_ticket_seller.controller;

import com.example.movie_ticket_seller.dto.*;
import com.example.movie_ticket_seller.service.MovieService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/movie")
public class MovieController {
    private final MovieService movieService;

    @GetMapping("/{id}")
    public ResponseEntity<MovieDto> getMovieById(@PathVariable UUID id) {
        var movieDto = movieService.getMovieById(id);
        return ResponseEntity.ok(movieDto);
    }

    @PostMapping
    public ResponseEntity<UUID> createMovie(@RequestBody MovieCreateDto dto) {
        var createdId = movieService.createMovie(dto);

        // Optional: Include URI to the newly created resource
        URI location = URI.create("/movie/" + createdId);
        return ResponseEntity.created(location).body(createdId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateMovie(@PathVariable UUID id, @RequestBody MovieUpdateDto dto) {
        movieService.updateMovie(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable UUID id) {
        movieService.deleteMovie(id);
        return ResponseEntity.noContent().build();
    }
}
