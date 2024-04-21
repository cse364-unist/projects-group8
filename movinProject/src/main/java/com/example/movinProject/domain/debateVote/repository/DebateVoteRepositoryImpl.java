package com.example.movinProject.domain.debateVote.repository;


import com.example.movinProject.domain.debateVote.domain.DebateVote;

import com.example.movinProject.domain.debateVote.domain.QDebateVote;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DebateVoteRepositoryImpl implements DebateVoteRepositoryCustom
{
    private final JPAQueryFactory queryFactory;
    public DebateVote findByUserNameAndDebateRoomId(String username, Long debateRoomId) {
        QDebateVote qDebateVote = QDebateVote.debateVote;
        return queryFactory
                .selectFrom(qDebateVote)
                .where(qDebateVote.userName.eq(username)
                        .and(qDebateVote.debateRoomId.eq(debateRoomId)))
                .fetchOne();
    }

    @Override
    public List<DebateVote> findByDebateRoomId(Long debateRoomId) {
        QDebateVote qDebateVote = QDebateVote.debateVote;
        return queryFactory
                .selectFrom(qDebateVote)
                .where(qDebateVote.debateRoomId.eq(debateRoomId))
                .fetch();
    }

}

