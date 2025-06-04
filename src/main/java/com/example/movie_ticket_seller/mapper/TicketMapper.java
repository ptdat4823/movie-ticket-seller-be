package com.example.movie_ticket_seller.mapper;

import com.example.movie_ticket_seller.dto.TicketBookingDto;
import com.example.movie_ticket_seller.dto.TicketDto;
import com.example.movie_ticket_seller.helper.LocalDateTimeHelper;
import com.example.movie_ticket_seller.model.Ticket;

import java.time.LocalDateTime;

public class TicketMapper {
    public static TicketDto ToDto(Ticket entity)
    {
        return TicketDto.builder()
                .id(entity.getId())
                .seatNumber(entity.getSeat().getSeatNumber())
                .purchaseTime(LocalDateTimeHelper.ToISOString(entity.getPurchaseTime()))
                .showTime(ShowTimeMapper.ToDto(entity.getShowTime()))
                .build();
    }

    public static Ticket FromBookingDto(TicketBookingDto dto)
    {
        return Ticket.builder()
                .purchaseTime(LocalDateTime.now())
                .isDeleted(false)
                .build();
    }
}
