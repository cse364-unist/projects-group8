package com.example.movinProject.domain.debateRoom.repository;

import com.example.movinProject.domain.movie.domain.Movie;

import java.util.List;

public interface DebateRoomRepositoryCustom {
    List<Long> findMovieIdsByOpenState();
}
