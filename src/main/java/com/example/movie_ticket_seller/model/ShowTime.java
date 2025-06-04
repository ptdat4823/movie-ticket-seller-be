package com.example.movie_ticket_seller.model;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ShowTime {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id")
    Movie movie;

    LocalDateTime startTime;

    // Using a Map of seats: key is seatId, value is Seat entity
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @MapKey(name = "id")
    private Map<UUID, Seat> seatMap;

    private Integer seatRows;
    private Integer seatsPerRow;
    private Double ticketPrice;
    private Boolean isDeleted;

    public void InitSeatMap() {
        this.seatMap = new HashMap<>();
        for (char row = 'A'; row < 'A' + seatRows; row++) {
            for (int i = 1; i <= seatsPerRow; i++) {
                String seatNum = row + String.valueOf(i);
                Seat seat = new Seat(seatNum);
                seatMap.put(seat.getId(), seat);
            }
        }
    }
}
