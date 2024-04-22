package com.example.movinProject.main.debateRoom.controller;

import com.example.movinProject.main.debateRoom.dto.ChatGPTSummarizeDto;
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

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/chats")
public class ChatGPTController {
    private final ChatGPTService chatGPTService;


    @PostMapping("/summarize")
    public ResponseEntity<List<String>> processInputRequest(@RequestBody ChatGPTSummarizeDto dto) {
        final int AGREE = 0;
        final int DISAGREE = 1;
        List<String> summarizedOpinions = chatGPTService.summarizeOpinions(dto.getDebateRoomId());

        return new ResponseEntity<>(summarizedOpinions, HttpStatus.OK);
    }
}
