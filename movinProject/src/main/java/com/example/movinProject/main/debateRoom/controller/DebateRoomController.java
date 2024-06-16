package com.example.movinProject.main.debateRoom.controller;

import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.main.debateRoom.dto.*;
import com.example.movinProject.main.debateRoom.service.DebateRoomService;
import com.example.movinProject.main.debateVote.dto.Joins;
import com.example.movinProject.main.debateVote.dto.Vote;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
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

    @GetMapping("")
    public ResponseEntity<Map<String, List<DebateRoomDto>>> getDebateRoomsByMovieId(@RequestParam Long movieId) {
        Map<String, List<DebateRoomDto>> debateRooms = debateRoomService.getDebateRoomsGroupedByStateByMovieId(movieId);
        return ResponseEntity.ok(debateRooms);
    }

    @PostMapping("/{id}/end")
    public ResponseEntity<VoteDto> postVoteInfo(@RequestParam Long id) {
        VoteDto voteInfo = debateRoomService.resultVoteInfo(id);

        return ResponseEntity.ok(voteInfo);
    }


    @GetMapping("/{id}")
    public ResponseEntity<DebateRoomChatDto> getDebateRoom(@PathVariable Long id, @AuthenticationPrincipal UserDetails userDetails) {
        DebateRoomChatDto dto = debateRoomService.getDebateRoomDetails(id, userDetails.getUsername());
        if (dto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/vote")
    public ResponseEntity<DebateRoomVoteDto> vote(@PathVariable Long id, @RequestParam boolean vote, @AuthenticationPrincipal UserDetails userDetails) {
        Vote votein = new Vote();
        votein.setVote(vote);
        DebateRoomVoteDto dto = debateRoomService.castVote(id, userDetails.getUsername(), votein.isVote());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/{id}/join")
    public ResponseEntity<DebateRoomVoteDto> join(@PathVariable Long id, @RequestParam boolean joins, @AuthenticationPrincipal UserDetails userDetails) {
        Joins join = new Joins();
        join.setAgree(joins);
        DebateRoomVoteDto dto = debateRoomService.castjoin(id, userDetails.getUsername(), join.isAgree());
        return ResponseEntity.ok(dto);
    }

    @PostMapping("/create")
    public ResponseEntity<Long> create(
            @RequestParam String title,
            @RequestParam String topic,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startTime,
            @RequestParam Long movieId,
            @AuthenticationPrincipal UserDetails userDetails) {

        DebateRoomCreateDto debateRoomCreateDto = new DebateRoomCreateDto();
        debateRoomCreateDto.setTitle(title);
        debateRoomCreateDto.setTopic(topic);
        debateRoomCreateDto.setStartTime(startTime);
        debateRoomCreateDto.setMovieId(movieId);

        Long debateRoomId = debateRoomService.create(debateRoomCreateDto);
        return new ResponseEntity<>(debateRoomId, HttpStatus.CREATED);
    }

}
