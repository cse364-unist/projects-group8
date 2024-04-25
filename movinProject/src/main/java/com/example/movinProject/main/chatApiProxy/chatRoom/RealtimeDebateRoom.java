package com.example.movinProject.main.chatApiProxy.chatRoom;

import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.main.debateRoom.service.ChatGPTService;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.socket.WebSocketSession;

import java.lang.reflect.Array;
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

    public interface SummarizeCreatedListener {
        void onSummarizeCreated(String summarized, RealtimeDebateRoom realtimeDebateRoom);
    }

    private static class DebateStep {
        private final int step;
        private final int duration;
        private final String description;

        public DebateStep(int step, int duration, String description) {
            this.step = step;
            this.duration = duration;
            this.description = description;
        }
    }

    private static final DebateStep[] debateSteps = {
            new DebateStep(0, 0, "토론 시작 전"),
            new DebateStep(1, 5, "긍정측 입론"),
            new DebateStep(2, 2, "부정측 질의 및 긍정측 답변"),
            new DebateStep(3, 5, "부정측 입론"),
            new DebateStep(4, 2, "긍정측 질의 및 부정측 답변"),
            new DebateStep(5, 3, "긍정측 반박"),
            new DebateStep(6, 3, "부정측 반박"),
            new DebateStep(7, 0, "토론 종료")
    };

    @Getter
    public class DebateRoomSessionInfo {
        User user;
        boolean isAgree;
        WebSocketSession session;

        public DebateRoomSessionInfo(User user, boolean isAgree, WebSocketSession session) {
            this.user = user;
            this.isAgree = isAgree;
            this.session = session;
        }

        public DebateRoomSessionInfo(User user, boolean isAgree) {
            this.user = user;
            this.isAgree = isAgree;
        }
    }


    private DebateRoom debateRoom;
    private Set<WebSocketSession> sessions = new HashSet<>();

    private ArrayList<DebateRoomSessionInfo> sessionInfos = new ArrayList<>();

    // step change listener
    private ArrayList<StepChangeListener> stepChangeListeners = new ArrayList<>();

    // moderator message listener
    private ArrayList<ModeratorMessageListener> moderatorMessageListeners = new ArrayList<>();

    // summarize created listener
    private ArrayList<SummarizeCreatedListener> summarizeCreatedListeners = new ArrayList<>();

    // 0: 토론 시작 전, 1: 긍정측 입론 (5분), 2: 부정측 질의 및 긍정측 답변(2분), 3: 부정측 입론 (5분), 4: 긍정측 질의 및 부정측 답변(2분), 5: 긍정측 반박 (3분), 6: 부정측 반박 (3분), 7: 토론 종료
    private int currentDebateStep = 0;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "Asia/Seoul")
    private LocalDateTime stepEndTime;

    private ChatGPTService chatGPTService;

    private Timer stepTimer = null;


    // moderator summarize constant


    @Builder
    public RealtimeDebateRoom(DebateRoom debateRoom, ChatGPTService chatGPTService) {
        this.debateRoom = debateRoom;
        this.chatGPTService = chatGPTService;
    }

    public void addClient(WebSocketSession session, User user, boolean isAgree) {
        sessions.add(session);
        sessionInfos.add(new DebateRoomSessionInfo(user, isAgree, session));
    }

    public DebateRoomSessionInfo removeClient(WebSocketSession session) {
        sessions.remove(session);
        // pop sessionInfo
        DebateRoomSessionInfo sessionInfo = sessionInfos.stream()
                .filter(info -> info.session == session)
                .findFirst()
                .orElse(null);

        if(sessionInfo == null) {
            return null;
        }else{
            sessionInfos.remove(sessionInfo);
        }


        return new DebateRoomSessionInfo(sessionInfo.user, sessionInfo.isAgree);
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

    public void addSummarizeCreatedListener(SummarizeCreatedListener listener) {
        summarizeCreatedListeners.add(listener);
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


    private double factor = 20.0;
    
    private void nextStep() {
        currentDebateStep++;
        int AGREE = 0;
        int DISAGREE = 1;
        stepEndTime = LocalDateTime.now().plusSeconds((long) (debateSteps[currentDebateStep].duration / factor * 60));
        notifyStepChange();

        if(currentDebateStep >= 1 && currentDebateStep <= 6) {
            notifyModeratorMessage(debateSteps[currentDebateStep].description + "을 시작합니다.");
        }else if(currentDebateStep == 7) {
            // 지피티 요약 받기
            List<String> summarized = chatGPTService.summarizeOpinions(debateRoom.getId());

            StringBuilder sb = new StringBuilder();

            sb.append("토론이 종료되었습니다.\n");
            sb.append("요약 내용:\n\n");
            sb.append("찬성 측:\n");
            sb.append(summarized.get(AGREE) + "\n");
            sb.append("반대 측:\n");
            sb.append(summarized.get(DISAGREE) + "\n");
            notifyModeratorMessage(sb.toString());

            for (SummarizeCreatedListener listener : summarizeCreatedListeners) {
                listener.onSummarizeCreated(
                        "찬성 측:\n" + summarized.get(AGREE) + "\n반대 측:\n" + summarized.get(DISAGREE) + "\n"
                        , this);
            }
        }

        // TimerTask를 사용하여 다음 step으로 넘어가도록 구현
        if(currentDebateStep == 7) {
            // 토론 종료
            return;
        }

        if(stepTimer != null)
            stepTimer.cancel();
        stepTimer = new Timer();
        stepTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                nextStep();
            }
        }, (long) ((long) debateSteps[currentDebateStep].duration * 60 * 1000 / factor));
    }

    private void notifyModeratorMessage(String message) {
        for (ModeratorMessageListener listener : moderatorMessageListeners) {
            listener.onModeratorMessage(message, this);
        }
    }

    public DebateRoomSessionInfo findDebateRoomSessionInfoBySession(WebSocketSession session) {
        return sessionInfos.stream()
                .filter(info -> info.session == session)
                .findFirst()
                .orElse(null);
    }

    public void stopAll() {
        if(stepTimer != null)
            stepTimer.cancel();

        // close all sessions
        for (WebSocketSession session : sessions) {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        sessions.clear();

        // clear all sessionInfos
        sessionInfos.clear();
    }
}
