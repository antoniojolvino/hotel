package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.RoomDTO;
import com.jolvino.hotel.controller.dto.mapper.RoomMapper;
import com.jolvino.hotel.core.model.Room;
import com.jolvino.hotel.core.service.RoomService;
import com.jolvino.hotel.util.mock.MockObjects;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoomController.class)

class RoomControllerTest {

    @MockBean
    RoomService service;
    @MockBean
    RoomMapper mapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    void findAllRooms() throws Exception {
        //Given
        List<Room> rooms = MockObjects.getRooms();
        List<RoomDTO> roomsDTO = MockObjects.getRoomsDTO();

        //When - Then
        when(mapper.modelToDto(anyList())).thenReturn(roomsDTO);
        when(service.findAllRooms()).thenReturn(rooms);
        mockMvc.perform(
                        get("/rooms")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].room-number",
                        Matchers.is(roomsDTO.get(0).getNumber())));
    }

    @Test
    void createRoom() throws Exception {
        //Given
        Room room = MockObjects.getRoom();
        RoomDTO roomDTO = MockObjects.getRoomDTO();
        String content = MockObjects.getRoomDTOAsJson();

        //When - Then
        when(mapper.modelToDto(any(Room.class))).thenReturn(roomDTO);
        when(service.createRoom(any())).thenReturn(room);

        mockMvc.perform(
                        post("/rooms")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.room-number",
                        Matchers.is(roomDTO.getNumber())));
    }
}