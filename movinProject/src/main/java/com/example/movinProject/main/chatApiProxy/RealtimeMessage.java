package com.example.movinProject.main.chatApiProxy;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealtimeMessage {
    // 메시지 타입 : 입장, 채팅, 나감
    public enum MessageType {
        ENTER, TALK
    }

    private MessageType type; // 메시지 타입
    private Long debateRoomId; // 방 번호
    private String message; // 메시지
    private String token; // 토큰
}
