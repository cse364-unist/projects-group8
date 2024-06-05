package com.example.movinProject.main.debateRoom.service;

import com.example.movinProject.config.SpringSecurity.service.JwtService;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.main.chatApiProxy.RealtimeMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class WebSocketChatHandlerTest {

    @Mock
    JwtService jwtService;

    @Mock
    ObjectMapper objectMapper;

    @Mock
    DebateRoomService debateRoomService;

    @Mock
    UserDetailsService userDetailsService;

    @InjectMocks
    WebSocketChatHandler webSocketChatHandler;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleTextMessageEnter() throws Exception {
        // User user = User.createTest(1L, "fakeUserId", "password", "email");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("fakeUserId");

        RealtimeMessage realtimeMessage = new RealtimeMessage();
        realtimeMessage.setType(RealtimeMessage.MessageType.ENTER);
        realtimeMessage.setDebateRoomId(1L);

        TextMessage fakeMessage = mock(TextMessage.class);
        when(fakeMessage.getPayload()).thenReturn("fakePayload");
        when(objectMapper.readValue("fakePayload", RealtimeMessage.class)).thenReturn(realtimeMessage);

        when(jwtService.extractUsername(any())).thenReturn("fakeUserId");
        when(userDetailsService.loadUserByUsername(any())).thenReturn(userDetails);
        when(jwtService.isTokenValid(any(), any())).thenReturn(true);

        webSocketChatHandler.handleTextMessage(mock(WebSocketSession.class), fakeMessage);

        verify(debateRoomService, times(1)).userEnter(any(), any(), any());
    }


    @Test
    void handleTextMessageTalk() throws Exception {
        // User user = User.createTest(1L, "fakeUserId", "password", "email");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("fakeUserId");

        RealtimeMessage realtimeMessage = new RealtimeMessage();

        realtimeMessage.setType(RealtimeMessage.MessageType.TALK);
        realtimeMessage.setDebateRoomId(1L);

        TextMessage fakeMessage = mock(TextMessage.class);
        when(fakeMessage.getPayload()).thenReturn("fakePayload");
        when(objectMapper.readValue("fakePayload", RealtimeMessage.class)).thenReturn(realtimeMessage);

        when(jwtService.extractUsername(any())).thenReturn("fakeUserId");
        when(userDetailsService.loadUserByUsername(any())).thenReturn(userDetails);
        when(jwtService.isTokenValid(any(), any())).thenReturn(true);

        webSocketChatHandler.handleTextMessage(mock(WebSocketSession.class), fakeMessage);

        verify(debateRoomService, times(1)).chatMessageReceived(any(), any());
    }


    @Test
    void handleTextMessageNullUserId() throws Exception {
        // User user = User.createTest(1L, "fakeUserId", "password", "email");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("fakeUserId");

        RealtimeMessage realtimeMessage = new RealtimeMessage();
        realtimeMessage.setType(RealtimeMessage.MessageType.TALK);
        realtimeMessage.setDebateRoomId(1L);

        TextMessage fakeMessage = mock(TextMessage.class);
        when(fakeMessage.getPayload()).thenReturn("fakePayload");
        when(objectMapper.readValue("fakePayload", RealtimeMessage.class)).thenReturn(realtimeMessage);

        when(jwtService.extractUsername(any())).thenReturn(null);

        Assertions.assertThrows(Exception.class, () -> {
            webSocketChatHandler.handleTextMessage(mock(WebSocketSession.class), fakeMessage);
        });
    }

    @Test
    void handleTextMessageTokenNotValid() throws Exception {
        // User user = User.createTest(1L, "fakeUserId", "password", "email");
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("fakeUserId");

        RealtimeMessage realtimeMessage = new RealtimeMessage();
        realtimeMessage.setType(RealtimeMessage.MessageType.TALK);
        realtimeMessage.setDebateRoomId(1L);

        TextMessage fakeMessage = mock(TextMessage.class);
        when(fakeMessage.getPayload()).thenReturn("fakePayload");
        when(objectMapper.readValue("fakePayload", RealtimeMessage.class)).thenReturn(realtimeMessage);

        when(jwtService.extractUsername(any())).thenReturn("fakeUserId");
        when(userDetailsService.loadUserByUsername(any())).thenReturn(userDetails);
        when(jwtService.isTokenValid(any(), any())).thenReturn(false);

        Assertions.assertThrows(Exception.class, () -> {
            webSocketChatHandler.handleTextMessage(mock(WebSocketSession.class), fakeMessage);
        });
    }
}