package com.example.movie_ticket_seller.service;

import com.example.movie_ticket_seller.dto.*;
import com.example.movie_ticket_seller.exception.NotFoundException;
import com.example.movie_ticket_seller.helper.LocalDateTimeHelper;
import com.example.movie_ticket_seller.mapper.SeatMapper;
import com.example.movie_ticket_seller.mapper.ShowTimeMapper;
import com.example.movie_ticket_seller.repository.MovieRepository;
import com.example.movie_ticket_seller.repository.ShowTimeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShowTimeService {
    private final ShowTimeRepository showTimeRepository;
    private final MovieRepository movieRepository;

    public ShowTimeDto getShowTimeById(UUID id) {
        var showTime = showTimeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Show time not found with id: " + id));
        return ShowTimeMapper.ToDto(showTime);
    }

    public UUID createShowTime(ShowTimeCreateDto dto)
    {
        var toCreate = ShowTimeMapper.FromCreateDto(dto);

        if(dto.getMovieId() != null)
        {
            var movie = movieRepository.findByIdAndIsDeletedFalse(dto.getMovieId())
                    .orElseThrow(() -> new NotFoundException("Movie not found with id: " + dto.getMovieId()));

            toCreate.setMovie(movie);
        }

        var created = showTimeRepository.save(toCreate);
        return created.getId();
    }

    public void updateShowTime(UUID id, ShowTimeUpdateDto dto) {
        var toUpdate = showTimeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Show time not found with id: " + id));

        if(dto.getMovieId() != null)
        {
            var movie = movieRepository.findByIdAndIsDeletedFalse(dto.getMovieId())
                    .orElseThrow(() -> new NotFoundException("Movie not found with id: " + dto.getMovieId()));

            toUpdate.setMovie(movie);
        }

        if (dto.getStartTime() != null)
        {
            toUpdate.setStartTime(LocalDateTimeHelper.FromISOString(dto.getStartTime()));
        }
        if(dto.getTicketPrice() != null)
        {
            toUpdate.setTicketPrice(dto.getTicketPrice());
        }

        showTimeRepository.save(toUpdate);
    }

    public void deleteShowTime(UUID id)
    {
        var toDelete = showTimeRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Show time not found with id: " + id));

        toDelete.setIsDeleted(true);
        showTimeRepository.save(toDelete);
    }

    public List<SeatDto> getSeatStatus(UUID id)
    {
        var showTimeExisted = showTimeRepository.existsByIdAndNotDeleted(id);
        if(!showTimeExisted) throw new NotFoundException("Show time not found with id: " + id);

        var seatList = showTimeRepository.findSeatMapByShowTimeId(id);
        return SeatMapper.ToSeatDtoList(seatList);
    }
}
