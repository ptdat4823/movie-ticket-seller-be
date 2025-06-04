package com.example.movie_ticket_seller.dto;

import com.example.movie_ticket_seller.model.Movie;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class ShowTimeCreateDto {
    UUID movieId;
    String startTime;
    Integer seatRows;
    Integer seatPerRow;
    Double ticketPrice;
}
