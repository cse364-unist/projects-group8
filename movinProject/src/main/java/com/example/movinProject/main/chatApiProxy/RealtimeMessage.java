package com.example.movinProject.main.chatApiProxy;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RealtimeMessage {
    // 메시지 타입 : 입장, 채팅, 나감
    public enum MessageType {
        ENTER, TALK, QUIT
    }

    private MessageType type; // 메시지 타입
    private Long debateRoomId; // 방 번호

    // TODO: authentication을 통해 userId,이름과 isAgree을 가져오도록 수정
    private Long userId; // 채팅을 보낸 사람
    private String userName; // 채팅을 보낸 사람 이름
    private boolean isAgree; // 찬성인지 반대인지

    private String message; // 메시지
}
