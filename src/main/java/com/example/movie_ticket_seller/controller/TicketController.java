package com.example.movie_ticket_seller.controller;

import com.example.movie_ticket_seller.dto.TicketBookingDto;
import com.example.movie_ticket_seller.dto.TicketDto;
import com.example.movie_ticket_seller.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/ticket")
public class TicketController {
    private final TicketService ticketService;

    @GetMapping("/{showTimeId}/all")
    public ResponseEntity<List<TicketDto>> getAllTicketByShowTimeId(@PathVariable UUID showTimeId) {
        List<TicketDto> list = ticketService.getAll(showTimeId);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TicketDto> getTicketById(@PathVariable UUID id) {
        TicketDto dto = ticketService.getTicketById(id);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<Void> bookTicket(@RequestBody TicketBookingDto dto) {
        ticketService.bookTickets(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/cancel")
    public ResponseEntity<Void> cancelTicket(@RequestBody TicketBookingDto dto) {
        ticketService.cancelTicket(dto);
        return ResponseEntity.noContent().build();
    }
}
