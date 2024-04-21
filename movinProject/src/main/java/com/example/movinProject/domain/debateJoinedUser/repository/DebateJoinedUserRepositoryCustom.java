package com.example.movinProject.domain.debateJoinedUser.repository;

import com.example.movinProject.domain.debateJoinedUser.domain.DebateJoinedUser;
import com.example.movinProject.domain.debateVote.domain.DebateVote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DebateJoinedUserRepositoryCustom {
    List<Long> findByDebateRoomId(Long debateRoomId);
    DebateJoinedUser findByUserNameAndDebateRoomId(String username, Long debateRoomId);
}
