package com.example.movinProject.domain.debateJoinedUser.repository;

import com.example.movinProject.domain.debateJoinedUser.domain.DebateJoinedUser;
import com.example.movinProject.domain.debateVote.domain.DebateVote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DebateJoinedUserRepositoryCustom {
    DebateJoinedUser findByUserNameAndDebateRoomId(String username, Long debateRoomId);
}
