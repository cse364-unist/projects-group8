package com.example.movinProject.domain.debateRoom.repository;

import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.debateRoom.domain.QDebateRoom;
import com.example.movinProject.domain.debateRoom.model.StateType;
import com.example.movinProject.domain.user.domain.QUser;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class DebateRoomRepositoryImpl implements DebateRoomRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Long> findMovieIdsByOpenState() {
        QDebateRoom debateRoom = QDebateRoom.debateRoom;
        return queryFactory
                .select(debateRoom.movieId)
                .from(debateRoom)
                .where(debateRoom.stateType.eq(StateType.OPEN))
                .fetch();
    }

    @Override
    public List<DebateRoom> findByMovieId(Long movieId) {
        QDebateRoom qDebateRoom = QDebateRoom.debateRoom;
        return queryFactory
                .selectFrom(qDebateRoom)
                .where(qDebateRoom.movieId.eq(movieId))
                .fetch();
    }

    @Override
    public List<DebateRoom> findDebateRoomsByUserName(String userName) {
        QDebateRoom qDebateRoom = QDebateRoom.debateRoom;
        QUser qUser = QUser.user;
        return queryFactory
                .selectFrom(qDebateRoom)
                .join(qDebateRoom.participants, qUser)
                .where(qUser.userName.eq(userName))
                .fetch();
    }
}
