package com.example.movinProject.main.chat.dto;

import com.example.movinProject.domain.chat.model.ChatType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter

public class ChatCreateDto {
    private Long debateRoomId;
    private String message;
    private ChatType chatType;
}
