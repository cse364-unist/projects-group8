package com.example.movinProject.main.movie.controller;

import com.example.movinProject.domain.movie.domain.Movie;
import com.example.movinProject.domain.movie.repository.MovieRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;


import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MovieControllerTest {
    @Autowired
    private MovieController movieController;

    @Autowired
    private MovieRepository movieRepository;

    @Test
    @DisplayName("Get main page movies test")
    void testGetMainPageMovies(){

        ResponseEntity<Map<String, List<Movie>>> response = movieController.getMainPageMovies();
        Map<String, List<Movie>> movies = response.getBody();

        assertNotNull(movies, "The response body should not be null");
        assertTrue(movies.containsKey("debateMovies"), "Should return debate movies");
        assertTrue(movies.containsKey("popularMovies"), "Should return popular movies");
    }

    @AfterEach
    void tearDown() {
        movieRepository.deleteAll();
    }


    @Test
    @DisplayName("Get movie by ID test")
    void testGetMovieById() {

        Movie movie1 = Movie.create("title","genre", 4.2, "thumb", "des");

        Movie createdMovie = movieRepository.save(movie1);

        ResponseEntity<Movie> response = movieController.getMovieById(createdMovie.getId());
        Movie movie = response.getBody();

        assertNotNull(movie, "The movie should not be null");
        assertEquals(createdMovie.getId(), movie.getId(), "The movie ID should match the request");
    }

    @Test
    @DisplayName("Get movie by ID test2")
    void testGetMovieById2() {

        Movie movie1 = Movie.create("title","genre", 4.2, "thumb", "des");

        Movie createdMovie = movieRepository.save(movie1);

        ResponseEntity<Movie> response = movieController.getMovieById(createdMovie.getId());
        Movie movie = response.getBody();

        assertEquals(createdMovie.getGenre(), movie.getGenre());

    }
}
