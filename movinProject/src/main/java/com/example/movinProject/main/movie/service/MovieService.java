package com.example.movinProject.main.movie.service;

import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;
import com.example.movinProject.domain.movie.domain.Movie;
import com.example.movinProject.domain.movie.repository.MovieRepository;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovieService {
    private final MovieRepository movieRepository;
    private final DebateRoomRepository debateRoomRepository;

    public List<Movie> findDebateMovies() {
        List<Long> openDebateRoomIds = debateRoomRepository.findMovieIdsByOpenState();
        return movieRepository.findMoviesByOpenDebateRooms(openDebateRoomIds);

    }

    public List<Movie> findPopularMovies(Double rating) {
        return movieRepository.findMoviesWithAverageRatingAbove(rating);
    }

    public List<Movie> searchMoviesByKeyword(String keyword, int pageNum) {
        final int LIMIT = 8;
        return movieRepository.searchMoviesByKeywordwithSize(keyword, pageNum, LIMIT);
    }

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }
}
