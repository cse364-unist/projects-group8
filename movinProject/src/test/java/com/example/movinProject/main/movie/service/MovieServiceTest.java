package com.example.movinProject.main.movie.service;

import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.debateRoom.model.StateType;
import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;
import com.example.movinProject.domain.movie.domain.Movie;
import com.example.movinProject.domain.movie.repository.MovieRepository;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MovieServiceTest {
    @Mock
    private MovieRepository movieRepository;

    @Mock
    private DebateRoomRepository debateRoomRepository;

    @InjectMocks
    private MovieService movieService;

    private Movie movie1;
    private Movie movie2;
    private Movie movie3;
    private Movie movie4;
    private Movie movie5;

    private DebateRoom debateRoom1;
    private DebateRoom debateRoom2;
    private DebateRoom debateRoom3;
    private DebateRoom debateRoom4;
    private DebateRoom debateRoom5;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        movie1 = Movie.createTest(1L,"movie1", "comic", 3.4, "url1", "movie1 descriptions");
        movie2 = Movie.createTest(2L,"movie2", "comic", 3.5, "url2", "movie2 descriptions");
        movie3 = Movie.createTest(3L,"movie3", "comic", 3.6, "url3", "movie3 descriptions");
        movie4 = Movie.createTest(4L,"movie4", "comic", 3.7, "url4", "movie4 descriptions");
        movie5 = Movie.createTest(5L,"movie5", "comic", 3.8, "url5", "movie5 descriptions");

        LocalDateTime now = LocalDateTime.now();
        debateRoom1 = DebateRoom.initTest(1L, movie1.getTitle(), "topic1" ,StateType.OPEN, now, movie1.getId());
        debateRoom2 = DebateRoom.initTest(2L, movie2.getTitle(), "topic2" ,StateType.CLOSE, now, movie2.getId());
        debateRoom3 = DebateRoom.initTest(3L, movie3.getTitle(), "topic3" ,StateType.OPEN, now, movie3.getId());
        debateRoom4 = DebateRoom.initTest(4L, movie4.getTitle(), "topic4" ,StateType.CLOSE, now, movie4.getId());
        debateRoom5 = DebateRoom.initTest(5L, movie5.getTitle(), "topic5" ,StateType.OPEN, now, movie5.getId());
    }

    @Test
    void findDebateMovies() {
        List<Movie> expectedMovies = List.of(movie1, movie3, movie5);
        when(debateRoomRepository.findMovieIdsByOpenState()).thenReturn(List.of(1L, 3L, 5L));
        when(movieRepository.findMoviesByOpenDebateRooms(List.of(1L, 3L, 5L))).thenReturn(expectedMovies);
        List<Movie> result = movieService.findDebateMovies();

        // testing
        for(int i = 0; i < 3; i++){
            Assertions.assertEquals(expectedMovies.get(i), result.get(i));
        }
    }

    @Test
    void findPopularMovies() {
        double testingRating = 3.5;
        List<Movie> expectedMovies = List.of(movie2, movie3, movie4, movie5);
        when(movieRepository.findMoviesWithAverageRatingAbove(testingRating)).thenReturn(expectedMovies);
        List<Movie> results = movieService.findPopularMovies(testingRating);
        for(int i = 0; i < 4; i++){
            Assertions.assertEquals(expectedMovies.get(i), results.get(i));

        }
    }

    @Test
    void searchMoviesByKeyword() {
        String keyword = "movie2 descrip";
        int size = 5;
        List<Movie> expectedMovies = List.of(movie2);

        when(movieRepository.searchMoviesByKeywordwithSize(keyword, size)).thenReturn(List.of(movie2));
        List<Movie> results = movieService.searchMoviesByKeyword(keyword, size);
        for(int i = 0; i < 1; i++){
            Assertions.assertEquals(expectedMovies.get(i), results.get(i));
        }
    }

    @Test
    void findById() {
        Optional<Movie> expectedMovie2 = Optional.of(movie2);
        Optional<Movie> expectedMovie4 = Optional.of(movie4);

        when(movieRepository.findById(2L)).thenReturn(Optional.of(movie2));
        when(movieRepository.findById(4L)).thenReturn(Optional.of(movie4));

        Optional<Movie> result2 = movieService.findById(2L);
        Optional<Movie> result4 = movieService.findById(4L);

        Assertions.assertEquals(expectedMovie2, result2);
        Assertions.assertEquals(expectedMovie4, result4);
    }
}