package com.example.movinProject.domain.debateJoinedUser.repository;

import com.example.movinProject.domain.debateJoinedUser.domain.DebateJoinedUser;
import com.example.movinProject.domain.debateJoinedUser.domain.QDebateJoinedUser;
import com.example.movinProject.domain.debateVote.domain.DebateVote;
import com.example.movinProject.domain.debateVote.domain.QDebateVote;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DebateJoinedUserRepositoryImpl implements DebateJoinedUserRepositoryCustom{

    private final JPAQueryFactory queryFactory;
    @Override
    public DebateJoinedUser findByUserNameAndDebateRoomId(String username, Long debateRoomId) {

        QDebateJoinedUser qDebateJoinedUser = QDebateJoinedUser.debateJoinedUser;
            return queryFactory
                    .selectFrom(qDebateJoinedUser)
                    .where(qDebateJoinedUser.userName.eq(username)
                            .and(qDebateJoinedUser.debateRoomId.eq(debateRoomId)))
                    .fetchOne();
    }
}
