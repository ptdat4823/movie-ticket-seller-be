package com.example.movie_ticket_seller.controller;

import com.example.movie_ticket_seller.dto.*;
import com.example.movie_ticket_seller.service.ShowTimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RestController()
@RequestMapping("/showtime")
public class ShowTimeController {
    private final ShowTimeService showTimeService;

    @GetMapping("/{id}")
    public ResponseEntity<ShowTimeDto> getShowTimeById(@PathVariable UUID id) {
        var showTime = showTimeService.getShowTimeById(id);
        return ResponseEntity.ok(showTime);
    }

    @GetMapping("/{id}/seat-status")
    public ResponseEntity<List<SeatDto>> getSeatStatus(@PathVariable UUID id) {
        var seatStatus = showTimeService.getSeatStatus(id);
        return ResponseEntity.ok(seatStatus);
    }

    @PostMapping
    public ResponseEntity<UUID> createShowTime(@Validated @RequestBody ShowTimeCreateDto dto) {
        var createdId = showTimeService.createShowTime(dto);

        // Optional: Include URI to the newly created resource
        URI location = URI.create("/showtime/" + createdId);
        return ResponseEntity.created(location).body(createdId);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateShowTime(@PathVariable UUID id, @RequestBody ShowTimeUpdateDto dto) {
        showTimeService.updateShowTime(id, dto);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowTime(@PathVariable UUID id) {
        showTimeService.deleteShowTime(id);
        return ResponseEntity.noContent().build();
    }
}
