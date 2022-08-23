package com.jolvino.hotel.controller;

import com.jolvino.hotel.exception.*;
import com.jolvino.hotel.service.RoomService;
import org.hibernate.TransientPropertyValueException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(RoomController.class)
class HotelControllerAdviceTest {


    @MockBean
    RoomService service;
    @Autowired
    MockMvc mockMvc;

    @Test
    void processMethodArgumentNotValidException() throws Exception {
        String content = "{}";

        mockMvc.perform(
                post("/rooms")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isBadRequest());
    }

    @Test
    void processNotFoundException() throws Exception {
        when(service.findAllRooms()).thenThrow(new NotFoundException());

        mockMvc.perform(
                get("/rooms")
        ).andExpect(status().isNotFound());
    }

    @Test
    void processEmptyResultException() throws Exception {
        when(service.findAllRooms()).thenThrow(new EmptyResultException());

        mockMvc.perform(
                get("/rooms")
        ).andExpect(status().isOk());
    }

    @Test
    void processTransientPropertyValueException() throws Exception {
        when(service.findAllRooms()).thenThrow(
                new TransientPropertyValueException(
                        "message", "transientEntityName", "propertyOwnerEntityName", "propertyName"));

        mockMvc.perform(
                get("/rooms")
        ).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void processSchedulingException() throws Exception {
        when(service.findAllRooms()).thenThrow(new SchedulingException("message"));

        mockMvc.perform(
                get("/rooms")
        ).andExpect(status().isUnprocessableEntity());

    }

    @Test
    void processBookingUpdateException() throws Exception {
        when(service.findAllRooms()).thenThrow(new BookingUpdateException());

        mockMvc.perform(
                get("/rooms")
        ).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void processCreateBookingException() throws Exception {
        when(service.findAllRooms()).thenThrow(new CreateBookingException("message"));

        mockMvc.perform(
                get("/rooms")
        ).andExpect(status().isUnprocessableEntity());
    }

    @Test
    void processRoomAlreadyExistsException() throws Exception {
        when(service.findAllRooms()).thenThrow(new RoomAlreadyExistsException());

        mockMvc.perform(
                get("/rooms")
        ).andExpect(status().isUnprocessableEntity());
    }
}