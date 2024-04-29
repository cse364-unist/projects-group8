package com.example.movinProject.domain.debateJoinedUser.repository;

import com.example.movinProject.domain.debateJoinedUser.domain.DebateJoinedUser;

import java.util.Optional;

import java.util.List;

public interface DebateJoinedUserRepositoryCustom {
    List<DebateJoinedUser> findByDebateRoomId(Long debateRoomId);
    Optional<DebateJoinedUser> findByUserNameAndDebateRoomId(String username, Long debateRoomId);
    List<Long> findDebateRoomIdsByUserName(String username);
}
