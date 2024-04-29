package com.example.movinProject.domain.debateJoinedUser.repository;

import com.example.movinProject.domain.debateJoinedUser.domain.DebateJoinedUser;
import com.example.movinProject.domain.debateJoinedUser.domain.QDebateJoinedUser;
import com.example.movinProject.domain.debateVote.domain.DebateVote;
import com.example.movinProject.domain.debateVote.domain.QDebateVote;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.example.movinProject.domain.debateJoinedUser.domain.QDebateJoinedUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;


import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class DebateJoinedUserRepositoryImpl implements DebateJoinedUserRepositoryCustom{
    private final JPAQueryFactory queryFactory;
    @Override
    public List<DebateJoinedUser> findByDebateRoomId(Long debateRoomId) {
        QDebateJoinedUser qDebateJoinedUser = QDebateJoinedUser.debateJoinedUser;
        return queryFactory
                .selectFrom(qDebateJoinedUser)
                .where(qDebateJoinedUser.debateRoomId.eq(debateRoomId))
                .fetch();
    }

    @Override
    public Optional<DebateJoinedUser> findByUserNameAndDebateRoomId(String username, Long debateRoomId) {
        QDebateJoinedUser qDebateJoinedUser = QDebateJoinedUser.debateJoinedUser;
            return Optional.ofNullable(queryFactory
                    .selectFrom(qDebateJoinedUser)
                    .where(qDebateJoinedUser.userName.eq(username)
                            .and(qDebateJoinedUser.debateRoomId.eq(debateRoomId)))
                    .fetchOne());
    }

    @Override
    public List<Long> findDebateRoomIdsByUserName(String userName) {
        QDebateJoinedUser qDebateJoinedUser = QDebateJoinedUser.debateJoinedUser;
        return queryFactory
                .select(qDebateJoinedUser.debateRoomId)
                .from(qDebateJoinedUser)
                .where(qDebateJoinedUser.userName.eq(userName))
                .fetch();
}
}
