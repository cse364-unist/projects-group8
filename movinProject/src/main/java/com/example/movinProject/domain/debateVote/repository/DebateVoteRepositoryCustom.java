package com.example.movinProject.domain.debateVote.repository;

import com.example.movinProject.domain.debateVote.domain.DebateVote;

import java.util.List;

public interface DebateVoteRepositoryCustom {
    DebateVote findByUserNameAndDebateRoomId(String username, Long debateRoomId);
    List<DebateVote> findByDebateRoomId(Long debateRoomId);
}
