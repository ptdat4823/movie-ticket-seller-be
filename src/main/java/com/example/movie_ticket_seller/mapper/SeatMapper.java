package com.example.movie_ticket_seller.mapper;

import com.example.movie_ticket_seller.dto.MovieDto;
import com.example.movie_ticket_seller.dto.SeatDto;
import com.example.movie_ticket_seller.model.Movie;
import com.example.movie_ticket_seller.model.Seat;
import com.example.movie_ticket_seller.model.ShowTime;

import java.util.*;

public class SeatMapper {
    public static SeatDto ToDto(Seat entity)
    {
        return SeatDto.builder()
                .id(entity.getId())
                .seatNumber(entity.getSeatNumber())
                .reserved(entity.isReserved())
                .userId(entity.getUserId())
                .build();
    }

    public static List<SeatDto> ToSeatDtoList(List<Seat> list)
    {
        return list.stream()
                .sorted(Comparator.comparing(Seat::getSeatNumber, Seat.seatNumberComparator()))
                .map(SeatMapper::ToDto)
                .toList();
    }
}
