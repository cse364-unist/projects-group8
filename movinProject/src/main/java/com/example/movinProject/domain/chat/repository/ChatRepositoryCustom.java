package com.example.movinProject.domain.chat.repository;

import com.example.movinProject.domain.chat.domain.Chat;

import java.util.List;

public interface ChatRepositoryCustom {
    List<Chat> findByDebateRoomId(Long debateRoomId);
}
