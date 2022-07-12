package com.jolvino.hotel.core.service;

import com.jolvino.hotel.core.model.Booking;

import java.util.List;

public interface BookingService {
    List<Booking> findBookingsByRoomNumber(Integer roomNumber);

    Booking findBookingById(Long bookingID);

    Booking createBooking(Booking booking);

    void deleteBooking(Long bookingID);

    Booking updateBooking(Booking booking);
}
