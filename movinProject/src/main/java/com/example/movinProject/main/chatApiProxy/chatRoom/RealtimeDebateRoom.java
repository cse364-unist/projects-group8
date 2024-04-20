package com.example.movinProject.main.chatApiProxy.chatRoom;

import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.main.debateRoom.service.ChatGPTService;
import lombok.Builder;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.time.LocalDateTime;
import java.util.*;


@Getter
public class RealtimeDebateRoom {
    public interface StepChangeListener {
        void onStepChange(int step, LocalDateTime stepEndTime, RealtimeDebateRoom realtimeDebateRoom);
    }

    public interface ModeratorMessageListener {
        void onModeratorMessage(String message, RealtimeDebateRoom realtimeDebateRoom);
    }

    private class DebateStep {
        private final int step;
        private final int duration;
        private final String description;

        public DebateStep(int step, int duration, String description) {
            this.step = step;
            this.duration = duration;
            this.description = description;
        }
    }

    private final DebateStep[] debateSteps = {
            new DebateStep(0, 0, "토론 시작 전"),
            new DebateStep(1, 5, "긍정측 입론"),
            new DebateStep(2, 2, "부정측 질의 및 긍정측 답변"),
            new DebateStep(3, 5, "부정측 입론"),
            new DebateStep(4, 2, "긍정측 질의 및 부정측 답변"),
            new DebateStep(5, 3, "긍정측 반박"),
            new DebateStep(6, 3, "부정측 반박"),
            new DebateStep(7, 0, "토론 종료")
    };

    private DebateRoom debateRoom;
    private Set<WebSocketSession> sessions = new HashSet<>();

    // step change listener
    private ArrayList<StepChangeListener> stepChangeListeners = new ArrayList<>();

    // moderator message listener
    private ArrayList<ModeratorMessageListener> moderatorMessageListeners = new ArrayList<>();

    // 0: 토론 시작 전, 1: 긍정측 입론 (5분), 2: 부정측 질의 및 긍정측 답변(2분), 3: 부정측 입론 (5분), 4: 긍정측 질의 및 부정측 답변(2분), 5: 긍정측 반박 (3분), 6: 부정측 반박 (3분), 7: 토론 종료
    private int currentDebateStep = 0;

    private LocalDateTime stepEndTime;

    // moderator summarize constant


    @Builder
    public RealtimeDebateRoom(DebateRoom debateRoom) {
        this.debateRoom = debateRoom;
    }

    public void addClient(WebSocketSession session) {
        sessions.add(session);
    }

    public void removeClient(WebSocketSession session) {
        sessions.remove(session);
    }

    public void addStepChangeListener(StepChangeListener listener) {
        stepChangeListeners.add(listener);
    }

    public void removeStepChangeListener(StepChangeListener listener) {
        stepChangeListeners.remove(listener);
    }

    public void addModeratorMessageListener(ModeratorMessageListener listener) {
        moderatorMessageListeners.add(listener);
    }

    public void removeModeratorMessageListener(ModeratorMessageListener listener) {
        moderatorMessageListeners.remove(listener);
    }

    public void notifyStepChange() {
        for (StepChangeListener listener : stepChangeListeners) {
            listener.onStepChange(currentDebateStep, stepEndTime, this);
        }
    }

    public void startDebate() {
        nextStep();
    }

    private void nextStep() {
        currentDebateStep++;
        stepEndTime = LocalDateTime.now().plusMinutes(debateSteps[currentDebateStep].duration);
        notifyStepChange();

        // TODO: 사회자 메세지 전송 (With CHATGPT)
        if(currentDebateStep >= 1 && currentDebateStep <= 6) {
            notifyModeratorMessage(debateSteps[currentDebateStep].description + "을 시작합니다.");
        }else if(currentDebateStep == 7) {
            notifyModeratorMessage("토론이 종료되었습니다.");
        }

        // TimerTask를 사용하여 다음 step으로 넘어가도록 구현
        if(currentDebateStep == 7) {
            // 토론 종료
            return;
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                nextStep();
            }
        }, (long) debateSteps[currentDebateStep].duration * 60 * 1000);
    }

    private void notifyModeratorMessage(String message) {
        for (ModeratorMessageListener listener : moderatorMessageListeners) {
            listener.onModeratorMessage(message, this);
        }
    }
}
