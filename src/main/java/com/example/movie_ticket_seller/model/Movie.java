package com.example.movie_ticket_seller.model;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String title;
    private String genre;
    private Integer durationMinutes;
    private String director;
    private String description;
    private List<String> cast;
    private Boolean isDeleted;
}



