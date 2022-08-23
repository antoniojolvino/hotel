package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.RoomDTO;
import com.jolvino.hotel.service.RoomService;
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

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoomController.class)
class RoomControllerTest {

    @MockBean
    RoomService service;
    @Autowired
    MockMvc mockMvc;

    @Test
    void findAllRooms() throws Exception {
        //Given
        List<RoomDTO> roomsDTO = MockObjects.getRoomsDTO();

        //When - Then
        when(service.findAllRooms()).thenReturn(roomsDTO);
        mockMvc.perform(
                        get("/rooms")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomNumber",
                        Matchers.is(roomsDTO.get(0).getRoomNumber())));
    }

    @Test
    void createRoom() throws Exception {
        //Given
        RoomDTO roomDTO = MockObjects.getRoomDTO();
        String content = MockObjects.getRoomDTOAsJson();

        //When - Then
        when(service.createRoom(any())).thenReturn(roomDTO);

        mockMvc.perform(
                        post("/rooms")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.roomNumber",
                        Matchers.is(roomDTO.getRoomNumber())));
    }

}