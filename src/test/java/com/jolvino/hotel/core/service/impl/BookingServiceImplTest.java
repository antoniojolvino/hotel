package com.jolvino.hotel.core.service.impl;

import com.jolvino.hotel.controller.dto.BookingDTO;
import com.jolvino.hotel.controller.dto.mapper.BookingMapper;
import com.jolvino.hotel.exception.*;
import com.jolvino.hotel.model.Booking;
import com.jolvino.hotel.model.Customer;
import com.jolvino.hotel.model.Room;
import com.jolvino.hotel.model.repository.BookingRepository;
import com.jolvino.hotel.service.impl.BookingServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.orm.jpa.JpaObjectRetrievalFailureException;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.jolvino.hotel.util.mock.MockObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @Mock
    private BookingMapper mapper;

    @Mock
    private BookingRepository repository;

    @InjectMocks
    private BookingServiceImpl service;

    @Test
    @DisplayName("When given a room number, then return its bookings")
    void findBookingsByRoomNumber() {

        //Given
        List<Booking> bookings = getBookings();
        List<BookingDTO> bookingsDTO = getBookingsDTO();

        //When
        when(repository.findByRoomNumber(any())).thenReturn(bookings);
        when(mapper.modelToDto(anyList())).thenReturn(bookingsDTO);
        List<BookingDTO> response = service.findBookingsByRoomNumber(1);

        //Then
        assertEquals(bookingsDTO.size(), response.size());
        assertEquals(bookingsDTO.get(0), response.get(0));
    }

    @Test
    @DisplayName("When given a room number without scheduling, then return exception")
    void findBookingsByRoomNumberEmptyResultException() {
        //Given
        int roomNumber = 1;

        //When
        when(repository.findByRoomNumber(any())).thenReturn(List.of());

        //Then
        assertThrows(EmptyResultException.class, () -> service.findBookingsByRoomNumber(roomNumber));
    }

    @Test
    @DisplayName("When given a booking ID, then return its booking")
    void findBookingById() {
        //Given
        Booking booking = getBooking();
        BookingDTO bookingDTO = getBookingDTO();
        Long bookingID = 1L;
        //When
        when(repository.findById(any())).thenReturn(Optional.of(booking));
        when(mapper.modelToDto(any(Booking.class))).thenReturn(bookingDTO);
        BookingDTO response = service.findBookingById(bookingID);
        //Then
        assertEquals(bookingDTO, response);
    }

    @Test
    @DisplayName("When given a booking, then return it created")
    void createBooking() {
        //Given
        Booking booking = getBooking();
        BookingDTO bookingDTO = getBookingDTO();
        //When
        when(repository.save(any())).thenReturn(booking);
        when(mapper.dtoToModel(any())).thenReturn(booking);
        when(mapper.modelToDto(any(Booking.class))).thenReturn(bookingDTO);
        BookingDTO response = service.createBooking(bookingDTO);
        //Then
        assertEquals(bookingDTO, response);
    }

    @Test
    @DisplayName("When creating with the start date in the past, then return error")
    void createBookingJPAException() {
        //Given
        Booking booking = getBooking();
        BookingDTO bookingDTO = getBookingDTO();

        //When
        String entity_relation_not_found = "Entity Relation Not Found";
        when(repository.save(any())).thenThrow(
                new JpaObjectRetrievalFailureException(
                        new EntityNotFoundException(entity_relation_not_found)
                ));
        when(mapper.dtoToModel(any())).thenReturn(booking);

        // Then
        CreateBookingException ex = assertThrows(CreateBookingException.class, () -> service.createBooking(bookingDTO));

        assertEquals(entity_relation_not_found, ex.getMessage());
    }

    @Test
    @DisplayName("When creating with the start date in the past, then return error")
    void createBookingCreateBookingException() {
        // Given
        // When
        // Then
        CreateBookingException ex = assertThrows(CreateBookingException.class, () -> service.createBooking(null));

        assertEquals("There was a problem creating booking", ex.getMessage());
    }

    @Test
    @DisplayName("When creating with the start date in the past, then return error")
    void createBookingSchedulingExceptionStartPast() {
        //Given
        BookingDTO bookingDTO = getBookingDTO();
        Booking booking = getBooking();
        booking.setStartDate(LocalDate.now().plusDays(-1));
        //When
        when(mapper.dtoToModel(any())).thenReturn(booking);
        // Then
        assertThrows(SchedulingException.class, () -> service.createBooking(bookingDTO));
    }

    @Test
    @DisplayName("When creating with more than 30 days in advance, then return error")
    void createBookingSchedulingException30Days() {
        //Given
        BookingDTO bookingDTO = getBookingDTO();
        Booking booking = getBooking();
        booking.setStartDate(LocalDate.now().plusDays(31));
        booking.setEndDate(LocalDate.now().plusDays(32));
        //When
        when(mapper.dtoToModel(any())).thenReturn(booking);
        // Then
        assertThrows(SchedulingException.class, () -> service.createBooking(bookingDTO));
    }

    @Test
    @DisplayName("When creating with inverted dates, then return error")
    void createBookingSchedulingExceptionInvertedDates() {
        //Given
        BookingDTO bookingDTO = getBookingDTO();
        Booking booking = getBooking();
        booking.setStartDate(LocalDate.now().plusDays(2));
        booking.setEndDate(LocalDate.now().plusDays(1));
        //When
        when(mapper.dtoToModel(any())).thenReturn(booking);
        // Then
        assertThrows(SchedulingException.class, () -> service.createBooking(bookingDTO));
    }

    @Test
    @DisplayName("When creating with period longer than 3 days, then return error")
    void createBookingSchedulingExceptionLonger3Days() {
        //Given
        BookingDTO bookingDTO = getBookingDTO();
        Booking booking = getBooking();
        booking.setStartDate(LocalDate.now().plusDays(1));
        booking.setEndDate(LocalDate.now().plusDays(4));
        //When
        when(mapper.dtoToModel(any())).thenReturn(booking);
        // Then
        assertThrows(SchedulingException.class, () -> service.createBooking(bookingDTO));
    }

    @Test
    @DisplayName("When creating with conflict schedule, then return error")
    void createBookingSchedulingExceptionConflict() {
        //Given
        BookingDTO bookingDTO = getBookingDTO();
        Booking booking = getBooking();
        //When
        when(mapper.dtoToModel(any())).thenReturn(booking);
        when(repository.existsScheduleConflicts(any())).thenReturn(true);
        // Then
        assertThrows(SchedulingException.class, () -> service.createBooking(bookingDTO));
    }

    @Test
    @DisplayName("When given a booking id, then delete its booking")
    void deleteBooking() {
        //Given
        Long bookingID = 1L;
        //When
        doNothing().when(repository).deleteById(any());
        service.deleteBooking(bookingID);
        //Then
        verify(repository, times(1)).deleteById(any());
    }

    @Test
    @DisplayName("When given a not found booking id, then return error")
    void deleteBookingNotFoundException() {
        //Given
        Long bookingID = 1L;
        //When - Then
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(any());
        assertThrows(NotFoundException.class, () -> service.deleteBooking(bookingID));
    }

    @Test
    @DisplayName("When given a booking, the update it")
    void updateBooking() {
        //Given
        BookingDTO bookingDTO = getBookingDTO();
        Booking bookingFound = getBooking();
        Booking newBooking = getBooking();
        newBooking.setStartDate(LocalDate.now().plusDays(5));
        newBooking.setEndDate(LocalDate.now().plusDays(7));
        //When
        when(repository.findById(any())).thenReturn(Optional.of(bookingFound));
        when(repository.save(any())).thenReturn(newBooking);
        when(mapper.dtoToModel(any(), any())).thenReturn(newBooking);
        when(mapper.modelToDto(any(Booking.class))).thenReturn(bookingDTO);
        BookingDTO response = service.updateBooking(1L, bookingDTO);
        //Then
        assertEquals(bookingDTO, response);
    }

    @Test
    @DisplayName("When given a not found booking, then return error")
    void updateBookingNotFoundException() {
        //Given
        BookingDTO bookingDTO = getBookingDTO();
        Long bookingID = 1L;
        //When
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> service.updateBooking(bookingID, bookingDTO));

    }

    @Test
    @DisplayName("When given a booking from wrong customer, then return error")
    void bookingUpdateExceptionA() {
        //Given
        BookingDTO bookingDTO = getBookingDTO();
        Long bookingID = 1L;
        Booking bookingFound = getBooking();
        Customer customer = getCustomer();
        customer.setIdentificationDocument("novo");
        bookingFound.setCustomer(customer);
        //When
        when(repository.findById(any())).thenReturn(Optional.of(bookingFound));
        assertThrows(BookingUpdateException.class, () -> service.updateBooking(bookingID, bookingDTO));
    }

    @Test
    @DisplayName("When given a booking from wrong room, then return error")
    void bookingUpdateExceptionB() {
        //Given
        BookingDTO bookingDTO = getBookingDTO();
        Long bookingID = 1L;
        Booking bookingFound = getBooking();
        Room room = getRoom();
        room.setRoomNumber(56);
        bookingFound.setRoom(room);
        //When
        when(repository.findById(any())).thenReturn(Optional.of(bookingFound));
        assertThrows(BookingUpdateException.class, () -> service.updateBooking(bookingID, bookingDTO));
    }
}