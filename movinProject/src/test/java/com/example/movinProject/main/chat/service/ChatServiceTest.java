package com.example.movinProject.main.chat.service;

import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.chat.model.ChatType;
import com.example.movinProject.domain.chat.repository.ChatRepository;
import com.example.movinProject.main.chat.dto.ChatCreateDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {

    @Mock
    private ChatRepository chatRepository;

    @InjectMocks
    private ChatService chatService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateChat() {
        // Given
        ChatCreateDto chatCreateDto = ChatCreateDto.builder()
                .debateRoomId(1L)
                .message("Hello World")
                .chatType(ChatType.AGREE)
                .build();

        Chat chat = Chat.cr(1L);
        assertNull(chat.getId());
    }

    @Test
    void testCreateChat2() {
        // Given
        ChatCreateDto chatCreateDto = ChatCreateDto.builder()
                .debateRoomId(2L)
                .message("Hello World")
                .chatType(ChatType.AGREE)
                .build();

        Chat chat = Chat.init(chatCreateDto);
        assertNull(chat.getId());
    }
}