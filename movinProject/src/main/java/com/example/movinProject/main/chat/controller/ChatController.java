package com.example.movinProject.main.chat.controller;

import com.example.movinProject.domain.chat.model.ChatType;
import com.example.movinProject.main.chat.dto.ChatCreateDto;
import com.example.movinProject.main.chat.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class ChatController {
    private final ChatService chatService;

    @PostMapping("/create")
    public ResponseEntity<Long> create(
            @RequestParam Long debateRoomId,
            @RequestParam String message,
            @RequestParam ChatType chatType){
        ChatCreateDto dto = ChatCreateDto.builder()
                .debateRoomId(debateRoomId)
                .message(message)
                .chatType(chatType)
                .build();
        Long chatId = chatService.createChat(dto);
        return new ResponseEntity<>(chatId, HttpStatus.CREATED);
    }

}
