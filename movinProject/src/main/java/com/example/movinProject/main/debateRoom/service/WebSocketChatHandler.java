package com.example.movinProject.main.debateRoom.service;

import com.example.movinProject.config.SpringSecurity.service.JwtService;
import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.domain.user.repository.UserRepository;
import com.example.movinProject.main.chatApiProxy.RealtimeMessage;
import com.example.movinProject.main.chatApiProxy.chatRoom.RealtimeDebateRoom;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
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
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
    }


    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
        String payload = message.getPayload();
        RealtimeMessage realtimeMessage = objectMapper.readValue(payload, RealtimeMessage.class);

        String jwtToken = realtimeMessage.getToken();
        final String userId = jwtService.extractUsername(jwtToken);
        String userName = "";

        if (userId != null) {
            var userDetails = this.userDetailsService.loadUserByUsername(userId);
            userName = userDetails.getUsername();
            if (!jwtService.isTokenValid(jwtToken, userDetails)) {
                throw new Exception("Invalid token");
            }
        }else {
            throw new Exception("Invalid token");
        }

        if (realtimeMessage.getType().equals(RealtimeMessage.MessageType.ENTER)) {
            this.debateRoomService.userEnter(session, realtimeMessage.getDebateRoomId(), userName);
        } else if (realtimeMessage.getType().equals(RealtimeMessage.MessageType.TALK)) {
            this.debateRoomService.chatMessageReceived(session, realtimeMessage.getMessage());
        } else {
            // Unknown message type
            log.error("Unknown message type");
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        this.debateRoomService.userLeave(session);
    }
}
