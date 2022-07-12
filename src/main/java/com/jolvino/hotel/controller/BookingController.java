package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.BookingDTO;
import com.jolvino.hotel.controller.dto.mapper.BookingMapper;
import com.jolvino.hotel.core.model.Booking;
import com.jolvino.hotel.core.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {
    private final BookingService service;

    private final BookingMapper mapper;

    @GetMapping
    public ResponseEntity<List<BookingDTO>> findBookingByRoomNumber(@RequestParam("roomNumber") Integer roomNumber) {
        List<BookingDTO> response = mapper.modelToDto(service.findBookingsByRoomNumber(roomNumber));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<BookingDTO> findBookingById(@PathVariable("customerId") Long bookingID) {
        BookingDTO response = mapper.modelToDto(service.findBookingById(bookingID));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingDTO request) {
        Booking booking = mapper.dtoToModel(request);
        BookingDTO response = mapper.modelToDto(service.createBooking(booking));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/{customerId}")
    public Object deleteBooking(@PathVariable("customerId") Long bookingID) {
        service.deleteBooking(bookingID);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{customerId}")
    public Object updateBooking(@PathVariable("customerId") Long bookingID, @RequestBody BookingDTO request) {
        request.setId(bookingID);
        Booking booking = mapper.dtoToModel(request);
        BookingDTO response = mapper.modelToDto(service.updateBooking(booking));
        return ResponseEntity.ok(response);
    }
}
