package com.example.movinProject.domain.chat.domain;

import  com.example.movinProject.domain.chat.model.ChatType;
import com.example.movinProject.main.chat.dto.ChatCreateDto;
import com.example.movinProject.main.chatApiProxy.RealtimeMessage;
import com.example.movinProject.main.chatApiProxy.dto.RealtimeMessageDto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long debateRoomId;
    private Long userId;
    private String userName;

    @Column(columnDefinition = "NVARCHAR(10000)")
    private String message;

    @Enumerated(EnumType.STRING)
    private ChatType chatType;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime date;

    public static Chat createByRealtimeMessage(RealtimeMessageDto message){
        Chat chat = new Chat();
        chat.message = message.getMessage();
        chat.date = message.getSendTime();
        if(message.getSenderUserId() == -1){
            chat.chatType = ChatType.MODERATE;
        } else if(message.isSenderAgree()){
            chat.chatType = ChatType.AGREE;
        }else{
            chat.chatType = ChatType.DISAGREE;
        }
        chat.userId = message.getSenderUserId();
        chat.debateRoomId = message.getDebateRoomId();
        chat.userName = message.getSenderUserName();
        return chat;
    }
    public static Chat init(ChatCreateDto dto){
        Chat chat = new Chat();
        chat.message = dto.getMessage();
        chat.date = LocalDateTime.now();
        chat.chatType = dto.getChatType();
        chat.debateRoomId = dto.getDebateRoomId();
        return chat;
    }
    public static Chat createTest(Long id, String message, ChatType chatType, LocalDateTime date, Long debateRoomId){
        Chat chat = new Chat();
        chat.id = id;
        chat.message = message;
        chat.date = date;
        chat.chatType = chatType;
        chat.debateRoomId = debateRoomId;
        return chat;
    }

    public static Chat cr(Long userId) {
        Chat chat = new Chat();
        chat.userId = userId;
        return chat;
    }

}
