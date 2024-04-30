package com.example.movinProject.main.movie.controller;

import com.example.movinProject.domain.movie.domain.Movie;
import com.example.movinProject.domain.movie.repository.MovieRepository;
import com.example.movinProject.main.movie.dto.MovieDto;
import com.example.movinProject.main.movie.dto.MovieSearchDto;
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

    @Test
    @DisplayName("Get main page movies test2")
    void testGetMainPageMovies2(){
        ResponseEntity<Map<String, List<Movie>>> response = movieController.getMainPageMovies();
        Map<String, List<Movie>> movies = response.getBody();
        assertNotNull(movies.get("debateMovies"));
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

    @Test
    @DisplayName("test search movies1")
    void testSearchMovies(){
        Movie movie1 = Movie.create("title","genre", 4.2, "thumb", "des");

        Movie createdMovie = movieRepository.save(movie1);

        MovieSearchDto dto = MovieSearchDto.builder()
                .page(1)
                .keyword("title")
                .build();

        ResponseEntity<Map<String, List<MovieDto>>> response = movieController.searchMovies(dto);

        assertNotNull(response);
    }

    @Test
    @DisplayName("test search movies1")
    void testSearchMovies2(){
        Movie movie1 = Movie.create("title","genre", 4.2, "thumb", "des");

        Movie createdMovie = movieRepository.save(movie1);

        MovieSearchDto dto = MovieSearchDto.builder()
                .page(1)
                .keyword("fldi")
                .build();

        ResponseEntity<Map<String, List<MovieDto>>> response = movieController.searchMovies(dto);

        assertEquals(response.getBody().size(),1);
    }
}
