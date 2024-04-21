package com.example.movinProject.domain.debateVote.repository;

import com.example.movinProject.domain.debateVote.domain.DebateVote;

public interface DebateVoteRepositoryCustom {
    DebateVote findByUserNameAndDebateRoomId(String username, Long debateRoomId);
}
