package com.example.movinProject.main.debateRoom.controller;
import com.example.movinProject.domain.debateRoom.repository.DebateRoomRepository;
import com.example.movinProject.domain.movie.repository.MovieRepository;
import com.example.movinProject.main.debateRoom.dto.DebateRoomChatDto;
import com.example.movinProject.main.debateRoom.service.DebateRoomService;
import javax.inject.Inject;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;


public class DebateRoomControllerTest {

    @Mock
    private DebateRoomService debateRoomService;

    @Mock
    private DebateRoomRepository debateRoomRepository;


    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private DebateRoomController debateRoomController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void getDebateRoomTest() {
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("ti");

        // Set up service method expectations
        when(debateRoomService.getDebateRoomDetails(1L, "ti")).thenReturn(null);

        // Execute the method under test
        ResponseEntity<DebateRoomChatDto> response = debateRoomController.getDebateRoom(1L, mockUserDetails);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getDebateRoomTest2() {
        UserDetails mockUserDetails = mock(UserDetails.class);
        when(mockUserDetails.getUsername()).thenReturn("ti");

        DebateRoomChatDto dto = DebateRoomChatDto.createtest("tis");

        // Set up service method expectations
        when(debateRoomService.getDebateRoomDetails(1L, "ti")).thenReturn(dto);

        // Execute the method under test
        ResponseEntity<DebateRoomChatDto> response = debateRoomController.getDebateRoom(1L, mockUserDetails);

        // Verify the response
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}