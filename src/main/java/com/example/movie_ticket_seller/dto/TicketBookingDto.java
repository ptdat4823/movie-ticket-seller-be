package com.example.movie_ticket_seller.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@RequiredArgsConstructor
public class TicketBookingDto {
    UUID showTimeId;
    List<UUID> seatIds;
    String userId;
}
