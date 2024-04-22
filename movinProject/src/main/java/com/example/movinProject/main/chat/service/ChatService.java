package com.example.movinProject.main.chat.service;

import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.chat.repository.ChatRepository;
import com.example.movinProject.main.chat.dto.ChatCreateDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional(readOnly = true)
public class ChatService {
    private final ChatRepository chatRepository;

    @Transactional
    public Long createChat(ChatCreateDto chatCreateDto) {
        Chat chat = Chat.init(chatCreateDto);
        return chatRepository.save(chat).getId();
    }
}
