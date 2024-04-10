package com.example.movinProject.domain.movie.repository;

import com.example.movinProject.domain.movie.domain.Movie;
import java.util.List;

public interface MovieRepositoryCustom {

    List<Movie> findMoviesByOpenDebateRooms();

    List<Movie> findMoviesWithAverageRatingAbove(Double rating);

    List<Movie> searchMoviesByKeywordwithSize(String keyword, int size);

}
