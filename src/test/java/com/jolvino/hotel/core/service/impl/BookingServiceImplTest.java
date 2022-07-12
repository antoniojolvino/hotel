package com.jolvino.hotel.core.service.impl;

import com.jolvino.hotel.core.exceptions.BookingNotFoundException;
import com.jolvino.hotel.core.exceptions.BookingUpdateException;
import com.jolvino.hotel.core.exceptions.SchedulingException;
import com.jolvino.hotel.core.model.Booking;
import com.jolvino.hotel.core.model.Customer;
import com.jolvino.hotel.core.repository.BookingRepository;
import com.jolvino.hotel.util.mock.MockObjects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl service;
    @Mock
    private BookingRepository repository;

    @Test
    @DisplayName("When given a room number, then return its bookings")
    void findBookingsByRoomNumber() {

        //Given
        List<Booking> bookings = MockObjects.getBookings();

        //When
        when(repository.findByRoomNumber(any())).thenReturn(bookings);
        List<Booking> response = service.findBookingsByRoomNumber(1);

        //Then
        assertEquals(bookings.size(), response.size());
        assertEquals(bookings.get(0), response.get(0));
    }

    @Test
    @DisplayName("When given a booking ID, then return its booking")
    void findBookingById() {
        //Given
        Booking booking = MockObjects.getBooking();
        //When
        when(repository.findById(any())).thenReturn(Optional.of(booking));
        Booking response = service.findBookingById(1L);
        //Then
        assertEquals(booking, response);
    }

    @Test
    @DisplayName("When given a booking, then return it created")
    void createBooking() {
        //Given
        Booking booking = MockObjects.getBooking();
        //When
        when(repository.save(any())).thenReturn(booking);
        Booking response = service.createBooking(booking);
        //Then
        assertEquals(booking, response);
    }

    @Test
    @DisplayName("When creating with the start date in the past, then return error")
    void createBookingErrorA() {
        //Given
        Booking booking = MockObjects.getBooking();
        booking.setStartDate(LocalDate.now().plusDays(-1));
        //When - Then
        assertThrows(SchedulingException.class, () -> service.createBooking(booking));
    }

    @Test
    @DisplayName("When creating with more than 30 days in advance, then return error")
    void createBookingErrorB() {
        //Given
        Booking booking = MockObjects.getBooking();
        booking.setStartDate(LocalDate.now().plusDays(31));
        booking.setEndDate(LocalDate.now().plusDays(32));
        //When - Then
        assertThrows(SchedulingException.class, () -> service.createBooking(booking));
    }

    @Test
    @DisplayName("When creating with inverted dates, then return error")
    void createBookingErrorC() {
        //Given
        Booking booking = MockObjects.getBooking();
        booking.setStartDate(LocalDate.now().plusDays(2));
        booking.setEndDate(LocalDate.now().plusDays(1));
        //When - Then
        assertThrows(SchedulingException.class, () -> service.createBooking(booking));
    }

    @Test
    @DisplayName("When creating with period longer than 3 days, then return error")
    void createBookingErrorD() {
        //Given
        Booking booking = MockObjects.getBooking();
        booking.setStartDate(LocalDate.now().plusDays(1));
        booking.setEndDate(LocalDate.now().plusDays(4));
        //When - Then
        assertThrows(SchedulingException.class, () -> service.createBooking(booking));
    }

    @Test
    @DisplayName("When creating with conflict schedule, then return error")
    void createBookingErrorE() {
        //Given
        Booking booking = MockObjects.getBooking();
        //When - Then
        when(repository.existsScheduleConflicts(any(), any(), any())).thenReturn(true);
        assertThrows(SchedulingException.class, () -> service.createBooking(booking));
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
    void deleteBookingErrorNotFound() {
        //Given
        Long bookingID = 1L;
        //When - Then
        doThrow(EmptyResultDataAccessException.class).when(repository).deleteById(any());
        assertThrows(DataAccessException.class, () -> service.deleteBooking(bookingID));
    }

    @Test
    @DisplayName("When given a booking, the update it")
    void updateBooking() {
        //Given
        Booking bookingFound = MockObjects.getBooking();
        Booking newBooking = MockObjects.getBooking();
        newBooking.setStartDate(LocalDate.now().plusDays(5));
        newBooking.setEndDate(LocalDate.now().plusDays(7));
        //When
        when(repository.findById(any())).thenReturn(Optional.of(bookingFound));
        when(repository.save(any())).thenReturn(newBooking);
        Booking response = service.updateBooking(newBooking);
        //Then
        assertEquals(newBooking, response);
    }

    @Test
    @DisplayName("When given a not found booking, then return error")
    void updateBookingErrorNotFound() {
        //Given
        Booking newBooking = MockObjects.getBooking();
        //When
        when(repository.findById(any())).thenReturn(Optional.empty());
        assertThrows(BookingNotFoundException.class, () -> service.updateBooking(newBooking));

    }

    @Test
    @DisplayName("When given a booking from wrong customer, then return error")
    void updateBookingErrorNotFoundUpdate() {
        //Given
        Booking bookingFound = MockObjects.getBooking();
        Booking newBooking = MockObjects.getBooking();
        newBooking.setCustomer(new Customer());
        //When
        when(repository.findById(any())).thenReturn(Optional.of(bookingFound));
        assertThrows(BookingUpdateException.class, () -> service.updateBooking(newBooking));

    }
}