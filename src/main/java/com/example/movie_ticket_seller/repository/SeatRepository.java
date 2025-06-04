package com.example.movie_ticket_seller.repository;

import com.example.movie_ticket_seller.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
}
