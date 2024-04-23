package com.example.movinProject.main.movie.service;

import com.example.movinProject.domain.movie.domain.Movie;
import com.example.movinProject.domain.movie.repository.MovieRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

class MovieServiceTest {


    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieService movieService;
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void testFindDebateMovies() {

        List<Movie> expectedMovies = Collections.singletonList(new Movie());

        // When
        when(movieRepository.findMoviesByOpenDebateRooms()).thenReturn(expectedMovies);
        List<Movie> actualMovies = movieService.findDebateMovies();

        // Then
        assertEquals(expectedMovies, actualMovies);

    }

    @Test
    void testFindPopularMovies() {
        // Given
        double ratingThreshold = 4.0;
        List<Movie> expectedMovies = Collections.singletonList(new Movie());

        // When
        when(movieRepository.findMoviesWithAverageRatingAbove(ratingThreshold)).thenReturn(expectedMovies);
        List<Movie> actualMovies = movieService.findPopularMovies(ratingThreshold);

        // Then
        assertEquals(expectedMovies, actualMovies);
    }

    @Test
    void testSearchMoviesByKeyword() {
        // Given
        String keyword = "action";
        int size = 5;
        List<Movie> expectedMovies = Collections.singletonList(new Movie());

        // When
        when(movieRepository.searchMoviesByKeywordwithSize(keyword, size)).thenReturn(expectedMovies);
        List<Movie> actualMovies = movieService.searchMoviesByKeyword(keyword, size);

        // Then
        assertEquals(expectedMovies, actualMovies);
    }

    @Test
    void testFindById() {
        // Given
        Long movieId = 1L;
        Optional<Movie> expectedMovie = Optional.of(new Movie());

        // When
        when(movieRepository.findById(movieId)).thenReturn(expectedMovie);
        Optional<Movie> actualMovie = movieService.findById(movieId);

        // Then
        assertTrue(actualMovie.isPresent());
        assertEquals(expectedMovie.get(), actualMovie.get());
    }
}