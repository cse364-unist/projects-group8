package com.example.movinProject.main.debateRoom.service;


import com.example.movinProject.config.exception.BadRequestException;
import com.example.movinProject.config.exception.BadRequestType;
import com.example.movinProject.domain.chat.domain.Chat;
import com.example.movinProject.domain.chat.repository.ChatRepository;
import com.example.movinProject.domain.debateJoinedUser.repository.DebateJoinedUserRepository;
import com.example.movinProject.domain.debateJoinedUser.domain.DebateJoinedUser;
import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.domain.debateRoom.model.StateType;
import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;

import java.io.IOException;
import java.time.LocalDateTime;
import com.example.movinProject.domain.debateVote.domain.DebateVote;
import com.example.movinProject.domain.debateVote.repository.DebateVoteRepository;
import com.example.movinProject.domain.user.domain.User;
import com.example.movinProject.domain.user.repository.UserRepository;
import com.example.movinProject.main.debateRoom.dto.DebateRoomChatDto;
import com.example.movinProject.main.debateRoom.dto.DebateRoomCreateDto;
import com.example.movinProject.main.debateRoom.dto.DebateRoomVoteDto;
import com.example.movinProject.main.debateRoom.dto.VoteDto;

import java.util.*;
import java.util.stream.Collectors;

import com.example.movinProject.main.chatApiProxy.chatRoom.RealtimeDebateRoom;
import com.example.movinProject.main.chatApiProxy.dto.RealtimeMessageDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

@Slf4j
@RequiredArgsConstructor
@Service
// @Transactional(readOnly = true)
public class DebateRoomService {

    private final DebateRoomRepository debateRoomRepository;
    private final DebateJoinedUserRepository debateJoinedUserRepository;
    private final ChatRepository chatRepository;
    private final DebateVoteRepository debateVoteRepository;
    private final UserRepository userRepository;

    private final ChatGPTService chatGPTService;

    private final ObjectMapper objectMapper;
    private Map<Long, RealtimeDebateRoom> chatRooms;

    private HashMap<WebSocketSession, Long> sessionToDebateRoomId = new HashMap<>();

    @PostConstruct
    private void init() {
        chatRooms = new LinkedHashMap<>();
    }

    public RealtimeDebateRoom findRoomById(Long roomId) {
        return chatRooms.get(roomId);
    }


    @Transactional
    public Long create(DebateRoomCreateDto dto){
        DebateRoom debateRoom = DebateRoom.init(
                dto.getTitle(),
                dto.getTopic(),
                StateType.OPEN,
                dto.getStartTime(),
                dto.getMovieId()
        );
        DebateRoom savedDebateRoom = debateRoomRepository.save(debateRoom);
        return savedDebateRoom.getId();
    }


//    @Transactional
    public void startDebate(RealtimeDebateRoom room) {
        System.out.println("토론 시작");
        room.addStepChangeListener(
                (step, stepEndTime, realtimeDebateRoom) -> {
                    RealtimeMessageDto stepChangeMessage = new RealtimeMessageDto();
                    stepChangeMessage.setMessageType(RealtimeMessageDto.MessageType.StepChange);
                    stepChangeMessage.setDebateRoomId(room.getDebateRoom().getId());
                    stepChangeMessage.setStep(step);
                    stepChangeMessage.setStepEndTime(stepEndTime);
                    stepChangeMessage.setSendTime(LocalDateTime.now());

                    realtimeDebateRoom.getSessions().parallelStream().forEach(s -> {
                        try {
                            s.sendMessage(new TextMessage(objectMapper.writeValueAsString(stepChangeMessage)));
                        } catch (IOException e) {
                            log.error("메시지 전송 중 오류가 발생했습니다.", e);
                        }
                    });
                }
        );

        room.addModeratorMessageListener(
                ((message, realtimeDebateRoom) -> {
                    RealtimeMessageDto moderatorMessage = RealtimeMessageDto.builder()
                            .messageType(RealtimeMessageDto.MessageType.TALK)
                            .debateRoomId(room.getDebateRoom().getId())
                            .senderUserId(-1L)
                            .senderUserName("사회자")
                            .message(message)
                            .sendTime(LocalDateTime.now())
                            .build();
                    Chat moderatorChat = Chat.createByRealtimeMessage(moderatorMessage);

                    // 채팅 내용 저장
                    chatRepository.save(moderatorChat);
                    realtimeDebateRoom.getSessions().parallelStream().forEach(s -> {
                        try {
                            s.sendMessage(new TextMessage(objectMapper.writeValueAsString(moderatorMessage)));
                        } catch (IOException e) {
                            log.error("메시지 전송 중 오류가 발생했습니다.", e);
                        }
                    });
                })
        );
//        chatGPTService.summarizeOpinions(room.getDebateRoom().getId());

        // 토론 시작
        room.startDebate();
    }


    public void userEnter(WebSocketSession session, Long debateRoomId, String userName){
        RealtimeDebateRoom room = findRoomById(debateRoomId);

        // if room is not exist, create new room
        if(room == null) {
            DebateRoom debateRoom = debateRoomRepository.findById(debateRoomId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 토론방입니다."));
            room = new RealtimeDebateRoom(debateRoom, chatGPTService);
            chatRooms.put(debateRoomId, room);
        }

        sessionToDebateRoomId.put(session, debateRoomId);

        // Get User from db
        User user = userRepository.findByUserName(
                userName
        ).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        // Get user's agree
        Optional<DebateJoinedUser> debateJoinedUser = debateJoinedUserRepository.findByUserNameAndDebateRoomId(user.getUserName(), debateRoomId);
        if(debateJoinedUser.isEmpty()) {
            throw new IllegalArgumentException("토론방에 참여하지 않은 사용자입니다.");
        }
        boolean isAgree = debateJoinedUser.get().isAgree();

        RealtimeMessageDto realtimeMessage = RealtimeMessageDto.builder().
                messageType(RealtimeMessageDto.MessageType.ENTER).
                debateRoomId(debateRoomId).
                senderUserId(user.getId()).
                senderUserName(user.getUserName()).
                isSenderAgree(isAgree).
                sendTime(LocalDateTime.now()).
                build();
        room.addClient(session, user, isAgree);

        // 메시지 전송
        room.getSessions().parallelStream().forEach(s -> {
            try {
                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(realtimeMessage)));
            } catch (IOException e) {
                log.error("메시지 전송 중 오류가 발생했습니다.", e);
            }
        });

        // 만약 모든 사용자가 입장했다면, 토론 시작
        List<String> userIds = debateJoinedUserRepository.findByDebateRoomId(debateRoomId);
        if(room.getSessions().size() == userIds.size()) {
            // 토론 시작
            startDebate(room);
        }
    }

    public void userLeave(WebSocketSession session) {
        Long debateRoomId = sessionToDebateRoomId.get(session);
        if(debateRoomId == null) {
            throw new IllegalArgumentException("비정상적 접근입니다.");
        }

        RealtimeDebateRoom room = findRoomById(debateRoomId);
        if(room == null) {
            throw new IllegalArgumentException("비정상적 접근입니다.");
        }

        RealtimeDebateRoom.DebateRoomSessionInfo sessionInfo = room.findDebateRoomSessionInfoBySession(session);
        if(sessionInfo == null) {
            throw new IllegalArgumentException("비정상적 접근입니다.");
        }

        RealtimeMessageDto realtimeMessage = RealtimeMessageDto.builder().
                messageType(RealtimeMessageDto.MessageType.QUIT).
                debateRoomId(debateRoomId).
                senderUserId(sessionInfo.getUser().getId()).
                senderUserName(sessionInfo.getUser().getUserName()).
                isSenderAgree(sessionInfo.isAgree()).
                sendTime(LocalDateTime.now()).
                build();

        room.removeClient(session);
        sessionToDebateRoomId.remove(session);

        // 만약 모든 사용자가 퇴장했다면, 방 삭제
        if(room.getSessions().isEmpty()) {
            room.stopAll();
            chatRooms.remove(debateRoomId);
            return;
        }

        // 메시지 전송
        room.getSessions().parallelStream().forEach(s -> {
            try {
                s.sendMessage(new TextMessage(objectMapper.writeValueAsString(realtimeMessage)));
            } catch (IOException e) {
                log.error("메시지 전송 중 오류가 발생했습니다.", e);
            }
        });
    }

//    @Transactional
    public void chatMessageReceived(WebSocketSession session, String message) {
        // 채팅방에 있는 모든 사용자에게 메시지 전송
        // find the room that findDebateRoomSessionInfoBySession is not null
        RealtimeDebateRoom room = findRoomById(sessionToDebateRoomId.get(session));
        if(room == null) {
            throw new IllegalArgumentException("오류가 발생했습니다.");
        }

        RealtimeDebateRoom.DebateRoomSessionInfo sessionInfo = room.findDebateRoomSessionInfoBySession(session);

        if(sessionInfo == null) {
            throw new IllegalArgumentException("비정상적 접근입니다.");
        }

        // TODO: DebateRoom이 시작한 상태인지 확인하고, 시작하지 않은 상태라면 메시지를 보내지 않도록 수정

        RealtimeMessageDto realtimeMessage = RealtimeMessageDto.builder()
                .messageType(RealtimeMessageDto.MessageType.TALK)
                .debateRoomId(room.getDebateRoom().getId())
                .senderUserId(sessionInfo.getUser().getId())
                .senderUserName(sessionInfo.getUser().getUserName())
                .message(message)
                .isSenderAgree(sessionInfo.isAgree())
                .sendTime(LocalDateTime.now())
                .build();

        // 메시지 전송
        room.getSessions().parallelStream().forEach(ss -> {
            try {
                ss.sendMessage(new TextMessage(objectMapper.writeValueAsString(realtimeMessage)));
            } catch (IOException e) {
                log.error("메시지 전송 중 오류가 발생했습니다.", e);
            }
        });

        // 채팅 내용 저장
        Chat createdChat = Chat.createByRealtimeMessage(realtimeMessage);
        chatRepository.save(createdChat);
    }


    public Map<String, List<DebateRoom>> getDebateRoomsGroupedByStateByMovieId(Long movieId) {
        List<DebateRoom> rooms = debateRoomRepository.findByMovieId(movieId);
        Map<String, List<DebateRoom>> groupedRooms = new HashMap<>();
        groupedRooms.put("openDebateRooms", rooms.stream()
                .filter(room -> StateType.OPEN.equals(room.getStateType()))
                .collect(Collectors.toList()));
        groupedRooms.put("voteDebateRooms", rooms.stream()
                .filter(room -> StateType.VOTE.equals(room.getStateType()))
                .collect(Collectors.toList()));
        return groupedRooms;
    }

    public DebateRoomChatDto getDebateRoomDetails(Long id, String userName) {
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElse(null);
        if (debateRoom == null) {
            return null;
        }

        DebateRoomChatDto dto = new DebateRoomChatDto();
        dto.setTitle(debateRoom.getTitle());
        dto.setTopic(debateRoom.getTopic());
        dto.setStateType(debateRoom.getStateType());
        dto.setStartTime(debateRoom.getStartTime());
        dto.setDuration(debateRoom.getDuration());
        dto.setMaxUserNumber(debateRoom.getMaxUserNumber());
        dto.setAgreeJoinedUserNumber(debateRoom.getAgreeJoinedUserNumber());
        dto.setDisagreeJoinedUserNumber(debateRoom.getDisagreeJoinedUserNumber());
        dto.setSummarize(debateRoom.getSummarize());

        DebateVote debateVote = debateVoteRepository.findByUserNameAndDebateRoomId(userName, id);
        if(debateVote != null) {
            dto.setVoted(true);
            dto.setVoteAgree(debateVote.isAgree());
        }
        else {
            dto.setVoted(false);
            dto.setVoteAgree(false);
        }

        Optional<DebateJoinedUser> debateJoinedUser = debateJoinedUserRepository.findByUserNameAndDebateRoomId(userName, id);
        if(debateJoinedUser.isPresent()){
            dto.setJoined(true);
            dto.setAgree(debateJoinedUser.get().isAgree());
        }
        else {
            dto.setJoined(false);
            dto.setAgree(false);
        }
        List<Chat> chats = chatRepository.findByDebateRoomId(id);

        dto.setChats(chats);
        return dto;
    }

    @Transactional
    public DebateRoomVoteDto castjoin(Long id, String username, boolean agree) {
        Optional<DebateJoinedUser> oriDebateJoinedUser = debateJoinedUserRepository.findByUserNameAndDebateRoomId(username, id);
        if(oriDebateJoinedUser.isPresent()) {
            throw new RuntimeException("이미 참여한 토론입니다.");
        }

        DebateJoinedUser debateJoinedUser = DebateJoinedUser.create(
        id, username, agree);

        debateJoinedUserRepository.save(debateJoinedUser);

        DebateRoom debateRoom = debateRoomRepository.findById(id).orElse(null);
        if (debateRoom == null) {
            return null;
        }
        debateRoom.addTotleMoney(100);
        debateRoomRepository.save(debateRoom);
        User user = userRepository.findByUserName(username).orElse(null);
        if (user == null) {
            return null;
        }
        user.subMoney(100);
        userRepository.save(user);

        DebateRoomVoteDto dto = new DebateRoomVoteDto();
        dto.setTitle(debateRoom.getTitle());
        dto.setTopic(debateRoom.getTopic());
        dto.setStateType(debateRoom.getStateType());
        dto.setStartTime(debateRoom.getStartTime());
        dto.setDuration(debateRoom.getDuration());
        dto.setMaxUserNumber(debateRoom.getMaxUserNumber());
        dto.setAgreeJoinedUserNumber(debateRoom.getAgreeJoinedUserNumber());
        dto.setDisagreeJoinedUserNumber(debateRoom.getDisagreeJoinedUserNumber());
        dto.setSummarize(debateRoom.getSummarize());

        DebateVote debateVote = debateVoteRepository.findByUserNameAndDebateRoomId(username, id);
        if(debateVote != null) {
            dto.setVoted(true);
            dto.setVoteAgree(debateVote.isAgree());
        }
        else {
            dto.setVoted(false);
            dto.setVoteAgree(false);
        }


        dto.setJoined(true);
        dto.setAgree(agree);


        return dto;

    }

    @Transactional
    public DebateRoomVoteDto castVote(Long id, String username, boolean agree) {

        DebateVote vote = DebateVote.create(
            id, username, agree, LocalDateTime.of(2024,4,1,0,0));

        debateVoteRepository.save(vote);
        DebateRoom debateRoom = debateRoomRepository.findById(id).orElse(null);
        if (debateRoom == null) {
            return null;
        }
        debateRoom.addTotleMoney(100);
        debateRoomRepository.save(debateRoom);
        User user = userRepository.findByUserName(username).orElse(null);
        if (user == null) {
            return null;
        }
        user.subMoney(100);
        userRepository.save(user);

        DebateRoomVoteDto dto = new DebateRoomVoteDto();
        dto.setTitle(debateRoom.getTitle());
        dto.setTopic(debateRoom.getTopic());
        dto.setStateType(debateRoom.getStateType());
        dto.setStartTime(debateRoom.getStartTime());
        dto.setDuration(debateRoom.getDuration());
        dto.setMaxUserNumber(debateRoom.getMaxUserNumber());
        dto.setAgreeJoinedUserNumber(debateRoom.getAgreeJoinedUserNumber());
        dto.setDisagreeJoinedUserNumber(debateRoom.getDisagreeJoinedUserNumber());
        dto.setSummarize(debateRoom.getSummarize());

        dto.setVoted(true);
        dto.setVoteAgree(agree);

        Optional<DebateJoinedUser> debateJoinedUser = debateJoinedUserRepository.findByUserNameAndDebateRoomId(username, id);
        if(debateJoinedUser.get() != null) {
            dto.setJoined(true);
            dto.setAgree(debateJoinedUser.get().isAgree());
        }else {
            dto.setJoined(false);
            dto.setAgree(false);
        }

        return dto;
    }

    @Transactional
    public VoteDto resultVoteInfo(Long id) {
        DebateRoom endRoom = debateRoomRepository.findById(id).orElseThrow(() -> new BadRequestException(BadRequestType.CANNOT_FIND_DEBATE_ROOM));
        List<DebateVote> list = debateVoteRepository.findByDebateRoomId(id);
        VoteDto voteInfo = VoteDto.create(endRoom);

        int agreeNum = 0;
        int disagreeNum = 0;

        List<User> agreeUser = new ArrayList<>();
        List<User> disagreeUser = new ArrayList<>();
        for (DebateVote v : list) {
            if (v.isAgree()) {
                agreeNum++;
                User foundUser = userRepository.findByUserName(v.getUserName())
                        .orElseThrow(()-> new BadRequestException(BadRequestType.CANNOT_FIND_USER));
                agreeUser.add(foundUser);
            }
            else {
                disagreeNum++;

                User foundUser = userRepository.findByUserName(v.getUserName())
                        .orElseThrow(()-> new BadRequestException(BadRequestType.CANNOT_FIND_USER));
                disagreeUser.add(foundUser);
            }
        }
        int totUserNum = agreeUser.size() + disagreeUser.size();
        int money = endRoom.getTotalMoney() / totUserNum; // TODO: 분배를 위한 money amount를 계산하기
        List<User> savedUsers = new ArrayList<>();
        if (agreeNum > disagreeNum) {
            for (User u : agreeUser) {
                double rate = (double) agreeNum / (double) disagreeNum;
                money += (int) (money * rate);
                u.adjustMoney(money);
                savedUsers.add(u);
            }
        }
        else {
            for (User u : disagreeUser) {
                double rate = (double) disagreeNum / (double) agreeNum;
                money += (int) (money * rate);
                u.adjustMoney(money);
                savedUsers.add(u);
            }
        }
        // save all users at once
        userRepository.saveAll(savedUsers);

        endRoom.setStateType(StateType.CLOSE);
        voteInfo.setState(endRoom.getStateType());
        return voteInfo;
    }
    
}
