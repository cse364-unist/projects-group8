package com.example.movinProject.main.chat.controller;


import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.chat.model.ChatType;
import com.example.movinProject.domain.chat.repository.ChatRepository;
import com.example.movinProject.main.chat.dto.ChatCreateDto;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest
class ChatControllerTest {

    @Autowired
    private ChatRepository chatRepository;

    @Autowired
    private ChatController chatController;

    @Test
    @DisplayName("Test chat creation")
    void chatCreateTest() {
        ChatCreateDto chatCreateDto = ChatCreateDto.builder()
                .debateRoomId(1L)
                .message("Hello World!")
                .chatType(ChatType.AGREE)
                .build();

        ResponseEntity<Long> response = chatController.create(1L,"Hello World", ChatType.AGREE);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    @DisplayName("Test chat creation")
    void chatCreateTest2() {
        ChatCreateDto chatCreateDto = ChatCreateDto.builder()
                .debateRoomId(2L)
                .message("Hello World2!")
                .chatType(ChatType.DISAGREE)
                .build();

        ResponseEntity<Long> response = chatController.create(2L, "Hello World2!", ChatType.DISAGREE);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
    }

}