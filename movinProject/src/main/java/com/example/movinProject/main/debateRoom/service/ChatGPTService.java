package com.example.movinProject.main.debateRoom.service;

import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.chat.model.ChatType;
import com.example.movinProject.domain.chat.repository.ChatRepository;
import com.example.movinProject.main.debateRoom.model.ChatGPTRequest;
import com.example.movinProject.main.debateRoom.model.Message;
import com.example.movinProject.main.debateRoom.response.ChatGPTResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatGPTService {
    @Value("${openai.api.key}")
    private String apiKey;

    private static final String OPEN_AI_CHAT_ENDPOINT = "https://api.openai.com/v1/chat/completions";

    private final RestTemplate restTemplate;
    private final ChatRepository chatRepository;

    public ChatGPTResponse getChatGPTResponse(String prompt) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Authorization", "Bearer " + apiKey);

        ChatGPTRequest chatGPTRequest = new ChatGPTRequest();
        chatGPTRequest.setModel("gpt-3.5-turbo");
        chatGPTRequest.setMessages(List.of(new Message("user", prompt)));
        chatGPTRequest.setMax_tokens(20);

        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<ChatGPTRequest> request = new HttpEntity<>(chatGPTRequest, headers);

        return restTemplate.postForObject(OPEN_AI_CHAT_ENDPOINT, request, ChatGPTResponse.class);
    }

    public List<String> summarizeOpinions(Long debateRoomId) {
        // find all chats in debateRoom
        List<Chat> chats = chatRepository.findByDebateRoomId(debateRoomId);

        // separate message between agree and disagree
        List<String> agreeStrings = chats.stream()
                .filter(chat -> chat.getChatType() == ChatType.AGREE)
                .map(Chat::getMessage)
                .toList();
        List<String> disagreeStrings = chats.stream()
                .filter(chat -> chat.getChatType() == ChatType.DISAGREE)
                .map(Chat::getMessage)
                .toList();

        // chat serialize
        String agreeSuffix = "can you summarize these agree opinions?: ";
        String serializedAgreeStrings = String.join(", ", agreeStrings);

        String disagreeSuffix = "can you summarize these disagree opinions?: ";
        String serializedDisagreeStrings = String.join(", ", disagreeStrings);


        String agreeSummary = this.getChatGPTResponse(agreeSuffix + serializedAgreeStrings)
                .getChoices()
                .get(0)
                .message
                .getContent();
        String disagreeSummary = this.getChatGPTResponse(disagreeSuffix + serializedDisagreeStrings)
                .getChoices()
                .get(0)
                .message
                .getContent();
        return List.of(agreeSummary, disagreeSummary);
    }
}
