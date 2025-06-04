package com.example.movie_ticket_seller.repository;

import com.example.movie_ticket_seller.model.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    Optional<Movie> findByIdAndIsDeletedFalse(UUID id);
}
