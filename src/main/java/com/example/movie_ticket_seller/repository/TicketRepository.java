package com.example.movie_ticket_seller.repository;

import com.example.movie_ticket_seller.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TicketRepository extends JpaRepository<Ticket, UUID> {
    Optional<Ticket> findByIdAndIsDeletedFalse(UUID id);

    List<Ticket> findAllByShowTime_IdAndIsDeletedFalse(UUID showTimeId);

    List<Ticket> findAllBySeat_IdInAndIsDeletedFalse(List<UUID> seatIds);

}
