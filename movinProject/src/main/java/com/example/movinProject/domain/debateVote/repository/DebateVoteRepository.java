package com.example.movinProject.domain.debateVote.repository;

import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepositoryCustom;
import com.example.movinProject.domain.debateVote.domain.DebateVote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DebateVoteRepository extends JpaRepository<DebateVote, Long>, DebateVoteRepositoryCustom {

}
