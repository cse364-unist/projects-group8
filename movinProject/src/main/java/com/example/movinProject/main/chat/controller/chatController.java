package com.example.movinProject.main.chat.controller;

import com.example.movinProject.main.chat.dto.ChatCreateDto;
import com.example.movinProject.main.chat.service.ChatService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chats")
public class chatController {
    private final ChatService chatService;

    @PostMapping("/create")
    public ResponseEntity<Long> create(@RequestBody ChatCreateDto dto){
        Long chatId = chatService.createChat(dto);
        return new ResponseEntity<>(chatId, HttpStatus.CREATED);
    }

}
