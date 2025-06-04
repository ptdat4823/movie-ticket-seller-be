package com.example.movie_ticket_seller.service;

import com.example.movie_ticket_seller.dto.TicketBookingDto;
import com.example.movie_ticket_seller.dto.TicketDto;
import com.example.movie_ticket_seller.exception.AlreadyExistedException;
import com.example.movie_ticket_seller.exception.NotBookedException;
import com.example.movie_ticket_seller.exception.NotFoundException;
import com.example.movie_ticket_seller.helper.ThreadHelper;
import com.example.movie_ticket_seller.mapper.TicketMapper;
import com.example.movie_ticket_seller.model.Seat;
import com.example.movie_ticket_seller.model.ShowTime;
import com.example.movie_ticket_seller.model.Ticket;
import com.example.movie_ticket_seller.repository.SeatRepository;
import com.example.movie_ticket_seller.repository.ShowTimeRepository;
import com.example.movie_ticket_seller.repository.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class TicketService {
    private final ConcurrentHashMap<UUID, Object> seatLocks = new ConcurrentHashMap<>();

    private final TicketRepository ticketRepository;
    private final SeatRepository seatRepository;
    private final ShowTimeRepository showTimeRepository;

    public List<TicketDto> getAll(UUID showTimeId)
    {
        var ticketList = ticketRepository.findAllByShowTime_IdAndIsDeletedFalse(showTimeId);
        return ticketList.stream().map(TicketMapper::ToDto).toList();
    }

    public TicketDto getTicketById(UUID id) {
        Ticket ticket = ticketRepository.findByIdAndIsDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found with id: " + id));
        return TicketMapper.ToDto(ticket);
    }

    @Retryable(maxAttempts=12, backoff=@Backoff(delay=100, maxDelay=500))
    public void bookTickets(TicketBookingDto dto) {
        if (dto == null || dto.getSeatIds().isEmpty()) {
            throw new IllegalArgumentException("Seat list cannot be empty");
        }

        // Create a list of locks
        List<Object> locks = dto.getSeatIds().stream()
                .sorted() // Ensure deterministic locking order to avoid deadlocks
                .map(seatId -> seatLocks.computeIfAbsent(seatId, k -> new Object()))
                .distinct()
                .toList();

        // Lock all seats in sorted order to prevent deadlocks
        ThreadHelper.synchronizedMultiple(locks, () -> {
            // Fetch ShowTime
            ShowTime showTime = showTimeRepository.findByIdAndIsDeletedFalse(dto.getShowTimeId())
                    .orElseThrow(() -> new NotFoundException("ShowTime not found: " + dto.getShowTimeId()));

            List<Seat> updatedSeats = new ArrayList<>();

            for (var seatId : dto.getSeatIds()) {
                // Validate and reserve seat
                Seat seat = showTime.getSeatMap().get(seatId);
                if (seat == null) {
                    throw new NotFoundException("Seat not found with id: " + seatId);
                }
                if (seat.isReserved()) {
                    throw new AlreadyExistedException("Seat already reserved: " + seat.getSeatNumber());
                }
                System.out.println("Seat with id " + seat.getId() + " accessed by " + dto.getUserId());

                seat.setReserved(true);
                seat.setUserId(dto.getUserId());
                updatedSeats.add(seat);
            }

            seatRepository.saveAll(updatedSeats);
            System.out.println("Saving seat...........");

            List<Ticket> tickets = new ArrayList<>();

            for (var seatId : dto.getSeatIds()) {
                Seat seat = showTime.getSeatMap().get(seatId);

                var ticket = TicketMapper.FromBookingDto(dto);
                ticket.setShowTime(showTime);
                ticket.setSeat(seat);

                tickets.add(ticket);
            }

            ticketRepository.saveAll(tickets);
        });
    }


    @Transactional
    public void cancelTicket(TicketBookingDto dto) {
        if (dto == null || dto.getSeatIds().isEmpty()) {
            throw new IllegalArgumentException("Seat list cannot be empty");
        }

        // Create a list of locks
        List<Object> locks = dto.getSeatIds().stream()
                .sorted() // Ensure deterministic locking order to avoid deadlocks
                .map(seatId -> seatLocks.computeIfAbsent(seatId, k -> new Object()))
                .distinct()
                .toList();

        ThreadHelper.synchronizedMultiple(locks, () -> {
            // Fetch ShowTime
            ShowTime showTime = showTimeRepository.findByIdAndIsDeletedFalse(dto.getShowTimeId())
                    .orElseThrow(() -> new NotFoundException("ShowTime not found: " + dto.getShowTimeId()));

            List<Seat> updatedSeats = new ArrayList<>();

            for (var seatId : dto.getSeatIds()) {
                // Validate and reserve seat
                Seat seat = showTime.getSeatMap().get(seatId);
                if (seat == null) {
                    throw new NotFoundException("Seat not found with id: " + seatId);
                }
                if (!seat.isReserved()) {
                    throw new NotBookedException("Seat with id " + seat.getSeatNumber() + " is not booked");
                }

                seat.setReserved(false);
                seat.setUserId("");
                updatedSeats.add(seat);
            }

            seatRepository.saveAll(updatedSeats);

            var tickets = ticketRepository.findAllBySeat_IdInAndIsDeletedFalse(dto.getSeatIds());

            for (var ticket : tickets) {
                if(ticket.getIsDeleted()){
                    throw new IllegalStateException("Ticket with id " + ticket.getId() + " already cancelled.");
                }
                ticket.setIsDeleted(true);
            }

            ticketRepository.saveAll(tickets);
        });
    }
}
