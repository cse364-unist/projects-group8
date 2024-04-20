package com.example.movinProject.main.debateRoom.controller;

import com.example.movinProject.main.debateRoom.model.ChatBotInputRequest;
import com.example.movinProject.main.debateRoom.response.ChatGPTResponse;
import com.example.movinProject.main.debateRoom.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(value="/chat")
public class ChatGPTController {
    private final ChatGPTService chatGPTService;


//    @PostMapping("")
//    public ResponseEntity<ChatGPTResponse> processInputRequest(@RequestBody ChatBotInputRequest chatbotInputRequest) {
//        ChatGPTResponse chatCPTResponse = chatGPTService.getChatGPTResponse(chatbotInputRequest.getMessage());
//        return new ResponseEntity<>(chatCPTResponse, HttpStatus.OK);
//    }
}
