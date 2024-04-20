package com.example.movinProject.domain.chat.repository;

import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.chat.domain.QChat;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ChatRepositoryImpl implements ChatRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    @Override
    public List<Chat> findByDebateRoomId(Long debateRoomId) {
        QChat qChat = QChat.chat;
        return queryFactory
                .select(qChat)
                .from(qChat)
                .where(qChat.debateRoomId.eq(debateRoomId))
                .fetch();
    }
}
