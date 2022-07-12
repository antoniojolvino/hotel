package com.jolvino.hotel.core.service.impl;

import com.jolvino.hotel.core.exceptions.BookingNotFoundException;
import com.jolvino.hotel.core.exceptions.BookingUpdateException;
import com.jolvino.hotel.core.exceptions.NoContentException;
import com.jolvino.hotel.core.exceptions.SchedulingException;
import com.jolvino.hotel.core.model.Booking;
import com.jolvino.hotel.core.repository.BookingRepository;
import com.jolvino.hotel.core.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {
    private final BookingRepository repository;

    public List<Booking> findBookingsByRoomNumber(Integer roomNumber) {
        List<Booking> bookings = repository.findByRoomNumber(roomNumber);
        if (bookings == null || bookings.isEmpty()) {
            throw new NoContentException();
        }
        return bookings;
    }

    public Booking findBookingById(Long bookingID) {
        return repository.findById(bookingID).orElseThrow(NoContentException::new);
    }

    public Booking createBooking(Booking booking) {
        validateSchedule(booking);
        return repository.save(booking);
    }

    public void deleteBooking(Long bookingID) {
        repository.deleteById(bookingID);
    }

    public Booking updateBooking(Booking booking) {
        Optional<Booking> optBooking = repository.findById(booking.getId());
        optBooking.orElseThrow(BookingNotFoundException::new);
        optBooking.filter(
                        bkn -> bkn.getRoom().equals(booking.getRoom())
                                && bkn.getCustomer().equals(booking.getCustomer()))
                .orElseThrow(BookingUpdateException::new);
        validateSchedule(booking);
        return repository.save(booking);
    }

    private void validateSchedule(Booking booking) {
        long period = ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate());
        long fromNow = ChronoUnit.DAYS.between(LocalDate.now(), booking.getStartDate());
        if (fromNow <= 0 //A - All reservations start at least the next day of booking
                || fromNow > 30 //B - can’t be reserved more than 30 days in advance
                || period < 0 //C - To simplify the use case, a “DAY’ in the hotel room starts from 00:00 to 23:59:59.
                || period >= 3 //D - the stay can’t be longer than 3 days
                || repository.existsScheduleConflicts(booking.getRoom().getNumber(), booking.getStartDate(), booking.getEndDate())) //E
            throw new SchedulingException();
    }
}
