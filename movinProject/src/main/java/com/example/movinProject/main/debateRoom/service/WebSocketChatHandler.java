package com.example.movinProject.main.debateRoom.service;

import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;
import com.example.movinProject.main.chatApiProxy.RealtimeMessage;
import com.example.movinProject.main.chatApiProxy.chatRoom.RealtimeDebateRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Set;

@Slf4j
@Component
@RequiredArgsConstructor
public class WebSocketChatHandler extends TextWebSocketHandler {
    private final ObjectMapper objectMapper;
    private final DebateRoomService debateRoomService;


    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {

    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        RealtimeMessage realtimeMessage = objectMapper.readValue(payload, RealtimeMessage.class);
        if (realtimeMessage.getType().equals(RealtimeMessage.MessageType.ENTER)) {
            this.debateRoomService.userEnter(session, realtimeMessage.getDebateRoomId(), realtimeMessage.getUserId(), realtimeMessage.getUserName(), realtimeMessage.isAgree());

        } else if (realtimeMessage.getType().equals(RealtimeMessage.MessageType.QUIT)) {
            this.debateRoomService.userLeave(session, realtimeMessage.getDebateRoomId(), realtimeMessage.getUserId(), realtimeMessage.getUserName(), realtimeMessage.isAgree());
        } else if (realtimeMessage.getType().equals(RealtimeMessage.MessageType.TALK)) {
            this.debateRoomService.chatMessageReceived(realtimeMessage.getDebateRoomId(), realtimeMessage.getMessage(),
                    realtimeMessage.getUserId(), realtimeMessage.getUserName(), realtimeMessage.isAgree());
        } else {
            // Unknown message type
            log.error("Unknown message type");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
    }
}
