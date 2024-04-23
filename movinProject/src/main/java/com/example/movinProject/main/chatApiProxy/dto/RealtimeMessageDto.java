package com.example.movinProject.main.chatApiProxy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RealtimeMessageDto {

    // 메시지  타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK, QUIT, StepChange
    }

    private MessageType messageType; // 메시지 타입
    private int step; // 현재 스텝

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime stepEndTime; // 보낸 시간

    private Long debateRoomId; // 방 번호
    private Long senderUserId; // 채팅을 보낸 사람, 만약 사회자 메세지라면 -1
    private String senderUserName; // 채팅을 보낸 사람 이름
    private boolean isSenderAgree; // 찬성인지 반대인지

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime sendTime; // 보낸 시간

    private String message; // 메시지
}
