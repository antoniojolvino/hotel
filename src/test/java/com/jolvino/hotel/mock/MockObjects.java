package com.jolvino.hotel.mock;

import com.jolvino.hotel.controller.dto.BookingDTO;
import com.jolvino.hotel.controller.dto.CustomerDTO;
import com.jolvino.hotel.controller.dto.RoomDTO;
import com.jolvino.hotel.core.model.Booking;
import com.jolvino.hotel.core.model.Customer;
import com.jolvino.hotel.core.model.Room;

import java.time.LocalDate;
import java.util.List;

public class MockObjects {
    public static List<Booking> mockBookings() {
        Booking booking = mockBooking();
        return List.of(booking);
    }

    public static Booking mockBooking() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(LocalDate.now().plusDays(1));
        booking.setEndDate(LocalDate.now().plusDays(3));
        booking.setCustomer(mockCustomer());
        booking.setRoom(mockRoom());
        return booking;
    }

    public static Room mockRoom() {
        Room room = new Room();
        room.setNumber(1);
        return room;
    }

    public static Customer mockCustomer() {
        Customer customer = new Customer();
        customer.setIdentificationDocument("A123");
        customer.setName("John Doe");
        customer.setEmail("john.doe@gmail.com");
        return customer;
    }

    public static List<BookingDTO> mockBookingsDTO() {
        BookingDTO booking = mockBookingDTO();
        return List.of(booking);
    }

    public static BookingDTO mockBookingDTO() {
        BookingDTO booking = new BookingDTO();
        booking.setId(1L);
        booking.setStartDate(LocalDate.now().plusDays(1));
        booking.setEndDate(LocalDate.now().plusDays(3));
        booking.setCustomer(mockCustomerDTO());
        booking.setRoom(mockRoomDTO());
        return booking;
    }

    public static RoomDTO mockRoomDTO() {
        RoomDTO room = new RoomDTO();
        room.setNumber(1);
        return room;
    }

    public static CustomerDTO mockCustomerDTO() {
        CustomerDTO customer = new CustomerDTO();
        customer.setIdentificationDocument("A123");
        customer.setName("John Doe");
        customer.setEmail("john.doe@gmail.com");
        return customer;
    }
}
