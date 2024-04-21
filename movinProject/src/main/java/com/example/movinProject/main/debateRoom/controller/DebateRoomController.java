package com.example.movinProject.main.debateRoom.controller;

import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.main.debateRoom.dto.DebateRoomVoteDto;
import com.example.movinProject.main.debateRoom.service.DebateRoomService;
import com.example.movinProject.main.debateVote.dto.Vote;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/debateRooms")
@RequiredArgsConstructor
public class DebateRoomController {

    private final DebateRoomService debateRoomService;
    private DebateRoomRepository debateRoomRepository;
    private DebateVoteRepository debateVoteRepository;

    @GetMapping("/{movieId}")
    public ResponseEntity<Map<String, List<DebateRoom>>> getDebateRoomsByMovieId(@RequestParam long movieId) {
        Map<String, List<DebateRoom>> debateRooms = debateRoomService.getDebateRoomsGroupedByStateByMovieId(movieId);
        return ResponseEntity.ok(debateRooms);
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<VoteDto> postVoteInfo(@RequestParam Long Id) {
        VoteDto voteInfo = new VoteDto();
        DebateRoom endRoom = debateRoomRepository.findById(id);
        List<DebateVote> list = debateVoteRepository.findByDebateRoomId(id);
        voteInfo.setTitle(endRoom.getTitle());
        voteInfo.setTopic(endRoom.getTopic());

        voteInfo.setStartTime(endRoom.getStartTime());
        voteInfo.setDuration(endRoom.getDuration());
        voteInfo.setMaxUserNumber(endRoom.getMaxUserNumber());
        voteInfo.setAgreeJoinedUserNumber(endRoom.getAgreeJoinedUserNumber());
        voteInfo.setDisagreeJoinedUserNumber(endRoom.getDisagreeJoinedUserNumber());
        voteInfo.setSummarize(endRoom.getSummarize());
        int agreeNum = 0;
        int disagreeNum = 0;
        UserRepository userRepository;
        List<User> agreeUser = new ArrayList<>();
        List<User> disagreeUser = new ArrayList<>();
        for (DebateVote v : list) {
            if (v.getAgree()) {
                agreeNum++;
                agreeUser.add(userRepository.findById(v.getUserId));
            }
            else {
                disagreeNum++;
                disagreeUser.add(userRepository.findById(v.getUserId));
            }
        }
        int totUserNum = agreeNum.size() + disagreeNum.size();
        int money = 0;
        if (agreeNum > disagreeNum) {
            for (User u : agreeUser) {
                u.setMoney(u.getMoney() + money)
            }
        }
        else {
            for (User u : disagreeUser) {
                u.setMoney(u.getMoney() + money)
            }
        }
        endRoom.setStateType(StateType.End);
        voteInfo.setState(endRoom.getStateType());
        return ResponseEntity.ok(voteInfo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DebateRoomVoteDto> getDebateRoom(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        DebateRoomVoteDto dto = debateRoomService.getDebateRoomDetails(id, userDetails.getUsername());
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<DebateRoomVoteDto> vote(@PathVariable Long id, @RequestBody Vote vote, @AuthenticationPrincipal UserDetails userDetails) {
        DebateRoomVoteDto dto = debateRoomService.castVote(id, userDetails.getUsername(), vote.isVote());
        return ResponseEntity.ok(dto);
    }

}
