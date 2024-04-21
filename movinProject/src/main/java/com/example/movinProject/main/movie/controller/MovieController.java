package com.example.movinProject.main.movie.controller;

import com.example.movinProject.domain.movie.domain.Movie;
import com.example.movinProject.main.movie.dto.MovieDto;
import com.example.movinProject.main.movie.dto.MovieSearchDto;
import com.example.movinProject.main.movie.service.MovieService;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/movies")
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping("/mainPage")
    public ResponseEntity<Map<String, List<Movie>>> getMainPageMovies() {
        List<Movie> debateMovies = movieService.findDebateMovies();
        List<Movie> popularMovies = movieService.findPopularMovies(4.3);

        Map<String, List<Movie>> response = new HashMap<>();
        response.put("debateMovies", debateMovies);
        response.put("popularMovies", popularMovies);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/search")
    public ResponseEntity<Map<String, List<MovieDto>>> searchMovies(@RequestBody MovieSearchDto movieSearchDto) {
        List<Movie> movies = movieService.searchMoviesByKeyword(movieSearchDto.getKeyword(), movieSearchDto.getPage());
        List<MovieDto> movieDtos = movies.stream()
                .map(movie -> MovieDto.builder()
                        .id(movie.getId())
                        .thumbnailUrl(movie.getThumbnailUrl())
                        .name(movie.getTitle())
                        .build())
                .collect(Collectors.toList());

        Map<String, List<MovieDto>> response = new HashMap<>();
        response.put("movies", movieDtos);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById(@PathVariable Long id) {
        Movie movie = movieService.findById(id)
                .orElseThrow(() -> new RuntimeException("Movie not found with id: " + id));

        return ResponseEntity.ok(movie);
    }
}
