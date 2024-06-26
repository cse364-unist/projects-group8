package com.example.movinProject.domain.debateRoom.repository;

import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import java.util.List;
import java.util.Optional;

public interface DebateRoomRepositoryCustom {
    List<Long> findMovieIdsByOpenState();

    List<DebateRoom> findByMovieId(Long movieId);

}
