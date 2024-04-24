package com.example.movinProject.main.debateRoom.service;

import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.chat.model.ChatType;
import com.example.movinProject.domain.chat.repository.ChatRepository;
import com.example.movinProject.main.debateRoom.model.ChatGPTRequest;
import com.example.movinProject.main.debateRoom.model.Message;
import com.example.movinProject.main.debateRoom.response.ChatGPTResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.lang.module.Configuration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ChatGPTServiceTest {


    private static final String OPEN_AI_CHAT_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    @Mock
    private ChatRepository chatRepository;


    @Mock
    private RestTemplate restTemplate;


    @InjectMocks
    private ChatGPTService chatGPTService;

    private Chat agreeChat1;
    private Chat agreeChat2;
    private Chat disagreeChat1;
    private Chat disagreeChat2;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        LocalDateTime now = LocalDateTime.now();



        agreeChat1 = Chat.createTest(1L, "agree opinion1", ChatType.AGREE, now,  1L);
        agreeChat2 = Chat.createTest(2L, "agree opinion2", ChatType.AGREE, now,  1L);
        disagreeChat1 = Chat.createTest(3L, "disagree opinion1", ChatType.DISAGREE, now,  1L);
        disagreeChat2 = Chat.createTest(4L,  "disagree opinion2", ChatType.DISAGREE, now,  1L);
    }

    @Test
    void getChatGPTResponse() {

    }

    @Test
    void summarizeOpinions() {
//        Long debateRoomId = 1L;
//        final int AGREE = 0;
//        final int DISAGREE = 1;
//        List<Chat> expectedChats = List.of(agreeChat1, agreeChat2, disagreeChat1, disagreeChat2);
//
//        when(chatRepository.findByDebateRoomId(debateRoomId)).thenReturn(expectedChats);
//        List<String> summarizedOpinions = chatGPTService.summarizeOpinions(debateRoomId);
//        Assertions.assertNotNull(summarizedOpinions.get(AGREE));
//        Assertions.assertNotNull(summarizedOpinions.get(DISAGREE));
    }
}