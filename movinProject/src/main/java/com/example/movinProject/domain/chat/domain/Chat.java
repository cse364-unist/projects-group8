package com.example.movinProject.domain.chat.domain;

import com.example.movinProject.domain.chat.model.ChatType;
import com.example.movinProject.main.chat.dto.ChatCreateDto;
import com.example.movinProject.main.chatApiProxy.RealtimeMessage;
import com.example.movinProject.main.chatApiProxy.dto.RealtimeMessageDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String message;

    @Enumerated(EnumType.STRING)
    private ChatType chatType;
    private LocalDateTime date;
    public static Chat createByRealtimeMessage(RealtimeMessageDto message){
        Chat chat = new Chat();
        chat.message = message.getMessage();
        chat.date = message.getSendTime();
        if(message.isSenderAgree()){
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
}
