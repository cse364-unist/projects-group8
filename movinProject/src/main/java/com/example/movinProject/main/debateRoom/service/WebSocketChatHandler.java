package com.example.movinProject.main.debateRoom.service;

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
        RealtimeDebateRoom room = debateRoomService.findRoomById(realtimeMessage.getDebateRoomId());

        Set<WebSocketSession> sessions = room.getSessions();   //방에 있는 현재 사용자 한명이 WebsocketSession
        if (realtimeMessage.getType().equals(RealtimeMessage.MessageType.ENTER)) {
            sessions.add(session);

            this.debateRoomService.userEnter(session, realtimeMessage.getDebateRoomId(), realtimeMessage.getUserId(), realtimeMessage.getUserName(), realtimeMessage.isAgree());

        } else if (realtimeMessage.getType().equals(RealtimeMessage.MessageType.QUIT)) {
            sessions.remove(session);
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
        //javascript에서  session.close해서 연결 끊음. 그리고 이 메소드 실행.
        //session은 연결 끊긴 session을 매개변수로 이거갖고 뭐 하세요.... 하고 제공해주는 것 뿐
    }
}
