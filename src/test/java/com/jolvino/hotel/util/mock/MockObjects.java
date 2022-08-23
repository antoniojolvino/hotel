package com.jolvino.hotel.util.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jolvino.hotel.controller.dto.BookingDTO;
import com.jolvino.hotel.controller.dto.CustomerDTO;
import com.jolvino.hotel.controller.dto.RoomDTO;
import com.jolvino.hotel.model.Booking;
import com.jolvino.hotel.model.Customer;
import com.jolvino.hotel.model.Room;

import java.time.LocalDate;
import java.util.List;

public class MockObjects {
    public static List<Booking> getBookings() {
        return List.of(getBooking());
    }

    public static Booking getBooking() {
        Booking booking = new Booking();
        booking.setId(1L);
        booking.setStartDate(LocalDate.now().plusDays(1));
        booking.setEndDate(LocalDate.now().plusDays(3));
        booking.setCustomer(getCustomer());
        booking.setRoom(getRoom());
        return booking;
    }

    public static Room getRoom() {
        Room room = new Room();
        room.setRoomNumber(1);
        return room;
    }

    public static List<Room> getRooms() {
        return List.of(getRoom());
    }

    public static Customer getCustomer() {
        Customer customer = new Customer();
        customer.setIdentificationDocument("A123");
        customer.setName("John Doe");
        customer.setEmail("john.doe@gmail.com");
        return customer;
    }

    public static List<Customer> getCustomers() {
        return List.of(getCustomer());
    }

    public static List<BookingDTO> getBookingsDTO() {
        return List.of(getBookingDTO());
    }

    public static BookingDTO getBookingDTO() {
        BookingDTO booking = new BookingDTO();
        booking.setId(1L);
        booking.setStartDate(LocalDate.now().plusDays(1));
        booking.setEndDate(LocalDate.now().plusDays(3));
        booking.setCustomer(getCustomerDTO());
        booking.setRoom(getRoomDTO());
        return booking;
    }

    public static BookingDTO getNewBookingDTO() {
        BookingDTO booking = new BookingDTO();
        booking.setStartDate(LocalDate.now().plusDays(1));
        booking.setEndDate(LocalDate.now().plusDays(3));
        booking.setCustomer(getCustomerDTO());
        booking.setRoom(getRoomDTO());
        return booking;
    }

    public static RoomDTO getRoomDTO() {
        RoomDTO room = new RoomDTO();
        room.setRoomNumber(1);
        return room;
    }

    public static List<RoomDTO> getRoomsDTO() {
        return List.of(getRoomDTO());
    }

    public static CustomerDTO getCustomerDTO() {
        CustomerDTO customer = new CustomerDTO();
        customer.setIdentificationDocument("A123");
        customer.setName("John Doe");
        customer.setEmail("john.doe@gmail.com");
        return customer;
    }

    public static List<CustomerDTO> getCustomersDTO() {
        return List.of(getCustomerDTO());
    }

    public static String getBookingDTOAsJson() {
        try {
            return getObjectMapper().writeValueAsString(getBookingDTO());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getNewBookingDTOAsJson() {
        try {
            return getObjectMapper().writeValueAsString(getNewBookingDTO());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getRoomDTOAsJson() {
        try {
            return getObjectMapper().writeValueAsString(getRoomDTO());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getCustomerDTOAsJson() {
        try {
            return getObjectMapper().writeValueAsString(getCustomerDTO());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static ObjectMapper getObjectMapper() {
        ObjectMapper objMapper = new ObjectMapper();
        objMapper.registerModule(new JavaTimeModule());
        objMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return objMapper;
    }
}
