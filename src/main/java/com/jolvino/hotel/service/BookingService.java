package com.jolvino.hotel.service;

import com.jolvino.hotel.controller.dto.BookingDTO;

import java.util.List;

public interface BookingService {
    List<BookingDTO> findBookingsByRoomNumber(Integer roomNumber);

    BookingDTO findBookingById(Long bookingID);

    BookingDTO createBooking(BookingDTO bookingDTO);

    void deleteBooking(Long bookingID);

    BookingDTO updateBooking(Long bookingID, BookingDTO bookingDTO);
}
