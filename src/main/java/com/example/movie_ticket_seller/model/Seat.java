package com.example.movie_ticket_seller.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.Comparator;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Seat {

    @Id
    private UUID id;

    private String seatNumber;
    private boolean reserved;
    private Boolean isDeleted;
    private String userId;

    public Seat(String seatNumber) {
        this.id = UUID.randomUUID();
        this.seatNumber = seatNumber;
        this.reserved = false;
        this.isDeleted = false;
        this.userId = "";
    }

    public static Comparator<String> seatNumberComparator() {
        return (sn1, sn2) -> {
            // Extract row (first character) and numeric column (remaining digits)
            char row1 = sn1.charAt(0);
            char row2 = sn2.charAt(0);

            if (row1 != row2) {
                return Character.compare(row1, row2);
            }

            // Parse numeric part
            int col1 = Integer.parseInt(sn1.substring(1));
            int col2 = Integer.parseInt(sn2.substring(1));

            return Integer.compare(col1, col2);
        };
    }

}

