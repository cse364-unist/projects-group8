package com.example.movinProject.domain.movie.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Movie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String genre;
    private Double avgRating;
    private String thumbnailUrl;
    private String description;

    public static Movie create(
            String title,
            String genre,
            Double avgRating,
            String thumbnailUrl,
            String description
    ){
        Movie movie = new Movie();
        movie.title = title;
        movie.genre = genre;
        movie.avgRating = avgRating;
        movie.thumbnailUrl = thumbnailUrl;
        movie.description = description;
        return movie;
    }
}
