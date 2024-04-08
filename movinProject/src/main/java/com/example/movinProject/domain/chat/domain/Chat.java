package com.example.movinProject.domain.chat.domain;

import com.example.movinProject.domain.chat.model.ChatType;
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

    private Long DebateRoomId;
    private Long userId;
    private String userName;
    private String message;

    @Enumerated(EnumType.STRING)
    private ChatType chatType;
    private LocalDateTime date;

}
