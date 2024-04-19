package com.example.movinProject.main.debateRoom.controller;

import com.example.movinProject.domain.debateRoom.domain.DebateRoom;
import com.example.movinProject.main.debateRoom.service.DebateRoomService;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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

}
