package com.example.movinProject.domain.chat.domain;

import com.example.movinProject.domain.chat.model.ChatType;
import com.example.movinProject.main.chatApiProxy.dto.RealtimeMessageDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ChatTest {
    @Test
    void createByRealtimeMessage1() {
        // given
        RealtimeMessageDto message = RealtimeMessageDto.builder()
                .message("message")
                .sendTime(LocalDateTime.now())
                .senderUserId(1L)
                .debateRoomId(1L)
                .senderUserName("user")
                .isSenderAgree(true)
                .build();

        // when
        Chat chat = Chat.createByRealtimeMessage(message);

        // then
        assertEquals(chat.getMessage(), message.getMessage());
        assertEquals(chat.getDate(), message.getSendTime());
        assertEquals(chat.getUserId(), message.getSenderUserId());
        assertEquals(chat.getDebateRoomId(), message.getDebateRoomId());
        assertEquals(chat.getUserName(), message.getSenderUserName());
        assertEquals(chat.getChatType(), ChatType.AGREE);
    }

    @Test
    void createByRealtimeMessage2() {
        // given
        RealtimeMessageDto message = RealtimeMessageDto.builder()
                .message("message")
                .sendTime(LocalDateTime.now())
                .senderUserId(2L)
                .debateRoomId(1L)
                .senderUserName("user")
                .isSenderAgree(false)
                .build();

        // when
        Chat chat = Chat.createByRealtimeMessage(message);

        // then
        assertEquals(chat.getMessage(), message.getMessage());
        assertEquals(chat.getDate(), message.getSendTime());
        assertEquals(chat.getUserId(), message.getSenderUserId());
        assertEquals(chat.getDebateRoomId(), message.getDebateRoomId());
        assertEquals(chat.getUserName(), message.getSenderUserName());
        assertEquals(chat.getChatType(), ChatType.DISAGREE);
    }

}