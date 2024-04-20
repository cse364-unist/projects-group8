package com.example.movinProject.domain.debateJoinedUser.repository;

import com.example.movinProject.domain.debateJoinedUser.domain.QDebateJoinedUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DebateJoinedUserRepositoryImpl implements DebateJoinedUserRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<Long> findByDebateRoomId(Long debateRoomId) {
        QDebateJoinedUser debateJoinedUser = QDebateJoinedUser.debateJoinedUser;
        return queryFactory.select(debateJoinedUser.userId)
                .from(debateJoinedUser)
                .where(debateJoinedUser.debateRoomId.eq(debateRoomId))
                .fetch();
    }
}
