package com.example.movie_ticket_seller.model;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showtime_id")
    private ShowTime showTime;

    @ManyToOne(fetch = FetchType.EAGER) // Seat is usually small, eager fetching is okay
    @JoinColumn(name = "seat_id")
    private Seat seat;

    private LocalDateTime purchaseTime;
    private Boolean isDeleted;

    public Ticket(UUID id, ShowTime showTime, Seat seat) {
        this.id = id;
        this.showTime = showTime;
        this.seat = seat;
        this.purchaseTime = LocalDateTime.now();
    }
}



