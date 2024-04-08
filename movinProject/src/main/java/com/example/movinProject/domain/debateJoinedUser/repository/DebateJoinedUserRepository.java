package com.example.movinProject.domain.debateJoinedUser.repository;

import com.example.movinProject.domain.debateJoinedUser.domain.DebateJoinedUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DebateJoinedUserRepository extends JpaRepository<DebateJoinedUser, Long>, DebateJoinedUserRepositoryCustom{
}
