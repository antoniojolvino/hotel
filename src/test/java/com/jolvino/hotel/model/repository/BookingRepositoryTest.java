package com.jolvino.hotel.model.repository;

import com.jolvino.hotel.model.Booking;
import com.jolvino.hotel.util.mock.MockObjects;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookingRepositoryTest {
    @Autowired
    private BookingRepository repository;

    @Test
    void findByRoomNumber() {
        // Given
        int roomNumber = 1;
        // When
        List<Booking> byRoomNumber = repository.findByRoomNumber(roomNumber);
        // Then
        assertEquals(roomNumber, byRoomNumber.size());
    }

    @Test
    void existsScheduleConflicts() {
        // Given
        Booking booking = MockObjects.getBooking();
        booking.setId(null);
        // When
        boolean isConflict = repository.existsScheduleConflicts(booking);
        // Then
        assertTrue(isConflict);
    }
}