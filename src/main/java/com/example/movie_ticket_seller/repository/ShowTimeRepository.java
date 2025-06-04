package com.example.movie_ticket_seller.repository;

import com.example.movie_ticket_seller.model.Seat;
import com.example.movie_ticket_seller.model.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ShowTimeRepository extends JpaRepository<ShowTime, UUID> {
    @Query("""
        SELECT COUNT(st) > 0
        FROM ShowTime st
        WHERE st.id = :id AND st.isDeleted = false
    """)
    boolean existsByIdAndNotDeleted(@Param("id") UUID id);

    Optional<ShowTime> findByIdAndIsDeletedFalse(UUID id);

    @Query("SELECT s.seatMap FROM ShowTime s WHERE s.id = :showTimeId AND s.isDeleted = false")
    List<Seat> findSeatMapByShowTimeId(@Param("showTimeId") UUID showTimeId);
}
