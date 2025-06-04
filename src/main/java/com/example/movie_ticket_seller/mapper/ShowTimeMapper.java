package com.example.movie_ticket_seller.mapper;

import com.example.movie_ticket_seller.dto.ShowTimeCreateDto;
import com.example.movie_ticket_seller.dto.ShowTimeDto;
import com.example.movie_ticket_seller.dto.TicketDto;
import com.example.movie_ticket_seller.helper.LocalDateTimeHelper;
import com.example.movie_ticket_seller.model.ShowTime;
import com.example.movie_ticket_seller.model.Ticket;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ShowTimeMapper {
    public static ShowTimeDto ToDto(ShowTime entity)
    {
        return ShowTimeDto.builder()
                .id(entity.getId())
                .movie(MovieMapper.ToDto(entity.getMovie()))
                .startTime(LocalDateTimeHelper.ToISOString(entity.getStartTime()))
                .seatRows(entity.getSeatRows())
                .seatsPerRow(entity.getSeatsPerRow())
                .ticketPrice(entity.getTicketPrice())
                .build();
    }

    public static ShowTime FromCreateDto(ShowTimeCreateDto dto)
    {
        var toCreate = ShowTime.builder()
                .startTime(LocalDateTimeHelper.FromISOString(dto.getStartTime()))
                .seatRows(dto.getSeatRows())
                .seatsPerRow(dto.getSeatPerRow())
                .ticketPrice(dto.getTicketPrice())
                .isDeleted(false)
                .build();
        toCreate.InitSeatMap();
        return toCreate;
    }
}
