package com.example.movinProject.main.debateRoom.controller;

import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.main.debateRoom.dto.DebateRoomVoteDto;
import com.example.movinProject.main.debateRoom.service.DebateRoomService;
import com.example.movinProject.main.debateVote.dto.Vote;
import java.util.List;
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

    @GetMapping("/{movieId}")
    public ResponseEntity<Map<String, List<DebateRoom>>> getDebateRoomsByMovieId(@RequestParam long movieId) {
        Map<String, List<DebateRoom>> debateRooms = debateRoomService.getDebateRoomsGroupedByStateByMovieId(movieId);
        return ResponseEntity.ok(debateRooms);
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
