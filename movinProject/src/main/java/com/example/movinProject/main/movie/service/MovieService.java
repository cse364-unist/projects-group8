package com.example.movinProject.main.movie.service;

import com.example.movinProject.domain.movie.domain.Movie;
import com.example.movinProject.domain.movie.repository.MovieRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    private MovieRepository movieRepository;

    public List<Movie> findDebateMovies() {
        return movieRepository. findMoviesByOpenDebateRooms();

    }

    public List<Movie> findPopularMovies(Double rating) {
        return movieRepository.findMoviesWithAverageRatingAbove(rating);
    }

    public List<Movie> searchMoviesByKeyword(String keyword, int size) {
        return movieRepository.searchMoviesByKeywordwithSize(keyword, size);
    }

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }
}
