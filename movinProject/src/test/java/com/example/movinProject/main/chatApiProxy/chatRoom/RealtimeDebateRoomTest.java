package com.example.movinProject.main.chatApiProxy.chatRoom;

import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.debateRoom.model.StateType;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.main.debateRoom.service.ChatGPTService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Timer;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RealtimeDebateRoomTest {

    DebateRoom debateRoom;

    @Mock
    ChatGPTService chatGPTService;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        debateRoom = DebateRoom.initTest(1L, "title1", "topic1", StateType.DISCUSS, LocalDateTime.now(), 1L);

        when(chatGPTService.summarizeOpinions(any())).thenReturn(List.of(new String[]{"1", "2"}));
    }

    @Test
    void addClient() {
    }

    @Test
    void removeClient() {
        RealtimeDebateRoom realtimeDebateRoom = new RealtimeDebateRoom(debateRoom, chatGPTService);

        WebSocketSession session1 = mock(WebSocketSession.class);
        WebSocketSession session2 = mock(WebSocketSession.class);

        User user1 = User.createTest(1L, "user1", "password1", "email1");
        User user2 = User.createTest(2L, "user2", "password2", "email2");

        realtimeDebateRoom.addClient(session1, user1, true);
        realtimeDebateRoom.addClient(session2, user2, false);

        assertNotNull(realtimeDebateRoom.removeClient(session2));
    }

    @Test
    void removeClientError() {
        RealtimeDebateRoom realtimeDebateRoom = new RealtimeDebateRoom(debateRoom, chatGPTService);

        assertNull(realtimeDebateRoom.removeClient(mock(WebSocketSession.class)));
    }


    @Test
    void notifyStepChangeForAllStep() throws IOException {
        RealtimeDebateRoom realtimeDebateRoom = new RealtimeDebateRoom(debateRoom, chatGPTService);

        WebSocketSession session1 = mock(WebSocketSession.class);
        User user1 = User.createTest(1L, "user1", "password1", "email1");
        realtimeDebateRoom.addClient(session1, user1, true);

        AtomicInteger moderatorCount = new AtomicInteger();
        realtimeDebateRoom.addModeratorMessageListener(
                (message, room) -> {
                    moderatorCount.getAndIncrement();
                }
        );

        for (int i = 0; i < 7; i++) {
            ReflectionTestUtils.invokeMethod(realtimeDebateRoom, "nextStep");
            ((Timer)ReflectionTestUtils.getField(realtimeDebateRoom, "stepTimer")).cancel();
        }

        assertEquals(7, moderatorCount.get());
    }

    @Test
    void findDebateRoomSessionInfoBySessionCaseNull() {
        RealtimeDebateRoom realtimeDebateRoom = new RealtimeDebateRoom(debateRoom, chatGPTService);

        WebSocketSession session1 = mock(WebSocketSession.class);
        WebSocketSession session2 = mock(WebSocketSession.class);

        realtimeDebateRoom.addClient(session1, User.createTest(1L, "user1", "password1", "email1"), true);
        realtimeDebateRoom.addClient(session2, User.createTest(2L, "user2", "password2", "email2"), false);

        assertNotNull(realtimeDebateRoom.findDebateRoomSessionInfoBySession(session2));
    }


    @Test
    void stopAll() throws IOException {
        RealtimeDebateRoom realtimeDebateRoom = new RealtimeDebateRoom(debateRoom, chatGPTService);

        WebSocketSession session1 = mock(WebSocketSession.class);
        WebSocketSession session2 = mock(WebSocketSession.class);
        User user1 = User.createTest(1L, "user1", "password1", "email1");
        User user2 = User.createTest(2L, "user2", "password2", "email2");

        realtimeDebateRoom.addClient(session1, user1, true);
        realtimeDebateRoom.addClient(session2, user2, false);

        realtimeDebateRoom.stopAll();

        verify(session1).close();
        verify(session2).close();
    }
}