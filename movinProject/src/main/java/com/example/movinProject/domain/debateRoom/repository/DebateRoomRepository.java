package com.example.movinProject.domain.debateRoom.repository;

import com.example.movinProject.domain.debateJoinedUser.repository.DebateJoinedUserRepositoryCustom;
import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebateRoomRepository extends JpaRepository<DebateRoom, Long>, DebateRoomRepositoryCustom {
}
