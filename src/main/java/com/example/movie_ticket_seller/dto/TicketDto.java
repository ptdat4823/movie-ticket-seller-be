package com.example.movie_ticket_seller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TicketDto {
    private UUID id;
    private ShowTimeDto showTime;
    private String seatNumber;
    private Double price;
    private String purchaseTime;
}
