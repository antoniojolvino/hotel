package com.jolvino.hotel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jolvino.hotel.controller.dto.BookingDTO;
import com.jolvino.hotel.controller.dto.mappers.BookingMapper;
import com.jolvino.hotel.core.model.Booking;
import com.jolvino.hotel.core.service.BookingService;
import com.jolvino.hotel.mock.MockObjects;
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
    @MockBean
    BookingMapper mapper;
    @Autowired
    MockMvc mockMvc;

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.registerModule(new JavaTimeModule());
        objMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objMapper;
    }

    @Test
    @DisplayName("When given a room number, then return its bookings")
    void findBookingByRoomNumber() throws Exception {
        //Given
        List<Booking> bookings = MockObjects.mockBookings();
        List<BookingDTO> bookingDTOS = MockObjects.mockBookingsDTO();

        //When - Then
        when(mapper.modelToDto(anyList())).thenReturn(bookingDTOS);
        when(service.findBookingsByRoomNumber(any())).thenReturn(bookings);
        mockMvc.perform(
                        get("/bookings/")
                                .param("roomNumber", "1")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].booking-id", Matchers.is(bookingDTOS.get(0).getId().intValue())));
    }

    @Test
    @DisplayName("When given a booking ID, then return its booking")
    void findBookingById() throws Exception {
        //Given
        Booking booking = MockObjects.mockBooking();
        BookingDTO bookingDTO = MockObjects.mockBookingDTO();

        //When - Then
        when(mapper.modelToDto(any(Booking.class))).thenReturn(bookingDTO);
        when(service.findBookingById(any())).thenReturn(booking);
        mockMvc.perform(
                        get("/bookings/{bookingID}", "1")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.booking-id", Matchers.is(1)));
    }

    @Test
    @DisplayName("When given a booking, then return it created")
    void createBooking() throws Exception {
        ObjectMapper objMapper = getObjectMapper();

        //Given
        Booking booking = MockObjects.mockBooking();
        BookingDTO bookingDTO = MockObjects.mockBookingDTO();
        String content = objMapper.writeValueAsString(bookingDTO);
        //When - Then
        when(mapper.modelToDto(any(Booking.class))).thenReturn(bookingDTO);
        when(service.createBooking(any())).thenReturn(booking);

        mockMvc.perform(
                        post("/bookings")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.booking-id", Matchers.is(bookingDTO.getId().intValue())));
    }

    @Test
    @DisplayName("When given a booking id, then delete its booking")
    void deleteBooking() throws Exception {
        //When - Then
        doNothing().when(service).deleteBooking(any());
        mockMvc.perform(
                delete("/bookings/{bookingId}", "1")
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("When given a booking, then update it")
    void updateBooking() throws Exception {
        ObjectMapper objMapper = getObjectMapper();

        //Given
        Booking booking = MockObjects.mockBooking();
        BookingDTO bookingDTO = MockObjects.mockBookingDTO();
        String content = objMapper.writeValueAsString(bookingDTO);
        //When - Then
        when(mapper.modelToDto(any(Booking.class))).thenReturn(bookingDTO);
        when(service.updateBooking(any())).thenReturn(booking);

        mockMvc.perform(
                        put("/bookings/{bookingId}", "1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.booking-id", Matchers.is(1)));
    }
}