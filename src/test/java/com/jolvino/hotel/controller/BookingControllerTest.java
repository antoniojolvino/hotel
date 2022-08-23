package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.BookingDTO;
import com.jolvino.hotel.service.BookingService;
import com.jolvino.hotel.util.mock.MockObjects;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(BookingController.class)
class BookingControllerTest {

    @MockBean
    BookingService service;
    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("When given a room number, then return its bookings")
    void findBookingByRoomNumber() throws Exception {
        //Given
        List<BookingDTO> bookingDTOS = MockObjects.getBookingsDTO();

        //When - Then
        when(service.findBookingsByRoomNumber(any())).thenReturn(bookingDTOS);
        mockMvc.perform(
                        get("/bookings/")
                                .param("roomNumber", "1")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id",
                        Matchers.is(bookingDTOS.get(0).getId().intValue())));
    }

    @Test
    @DisplayName("When given a booking ID, then return its booking")
    void findBookingById() throws Exception {
        //Given
        BookingDTO bookingDTO = MockObjects.getBookingDTO();
        int bookingId = 1;

        //When - Then
        when(service.findBookingById(any())).thenReturn(bookingDTO);
        mockMvc.perform(
                        get("/bookings/{bookingID}", bookingId)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id", Matchers.is(bookingId)));
    }

    @Test
    @DisplayName("When given a booking, then return it created")
    void createBooking() throws Exception {
        //Given
        BookingDTO bookingDTO = MockObjects.getBookingDTO();
        String content = MockObjects.getNewBookingDTOAsJson();
        //When - Then
        when(service.createBooking(any())).thenReturn(bookingDTO);

        mockMvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.id",
                        Matchers.is(bookingDTO.getId().intValue())));
    }

    @Test
    @DisplayName("When given an invalid booking request, then return bad request")
    void createBookingBadRequest() throws Exception {
        //Given
        String content = MockObjects.getBookingDTOAsJson();
        //When - Then
        mockMvc.perform(
                post("/bookings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When given a booking id, then delete its booking")
    void deleteBooking() throws Exception {
        //Given
        int bookingId = 1;

        //When - Then
        doNothing().when(service).deleteBooking(any());
        mockMvc.perform(
                delete("/bookings/{bookingId}", bookingId)
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("When given a booking, then update it")
    void updateBooking() throws Exception {
        //Given
        BookingDTO bookingDTO = MockObjects.getBookingDTO();
        String content = MockObjects.getNewBookingDTOAsJson();
        int bookingId = 1;
        //When - Then
        when(service.updateBooking(any(), any())).thenReturn(bookingDTO);

        mockMvc.perform(
                        put("/bookings/{bookingId}", bookingId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.id",
                        Matchers.is(bookingId)));
    }

    @Test
    @DisplayName("When given an invalid booking request, then return bad request")
    void updateBookingBadRequest() throws Exception {
        //Given
        String content = MockObjects.getBookingDTOAsJson();
        int bookingId = 1;
        //When - Then
        mockMvc.perform(
                put("/bookings/{bookingId}", bookingId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
        ).andExpect(status().isBadRequest());
    }


}