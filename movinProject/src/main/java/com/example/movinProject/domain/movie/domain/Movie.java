package com.example.movinProject.domain.movie.domain;

import jakarta.persistence.*;
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
    private int year;
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
    public static Movie loadCreate(String title,
                                   int year,
                                   Double avgRating,
                                   String genre,
                                   String description,
                                   String thumbnailUrl
                                   ){
        Movie movie = new Movie();
        movie.title = title;
        movie.year = year;
        movie.genre = genre;
        movie.avgRating = avgRating;
        movie.thumbnailUrl = thumbnailUrl;
        movie.description = description;
        return movie;
    }
    public static Movie createTest(
            Long id,
            String title,
            String genre,
            Double avgRating,
            String thumbnailUrl,
            String description
    ){
        Movie movie = new Movie();
        movie.id = id;
        movie.title = title;
        movie.genre = genre;
        movie.avgRating = avgRating;
        movie.thumbnailUrl = thumbnailUrl;
        movie.description = description;
        return movie;
    }
}
