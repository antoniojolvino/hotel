package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.BookingDTO;
import com.jolvino.hotel.service.BookingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Slf4j
public class BookingController {
    private final BookingService service;

    @GetMapping
    public ResponseEntity<List<BookingDTO>> findBookingByRoomNumber(@RequestParam("roomNumber") Integer roomNumber) {
        log.info("Starting booking search by room number: {}", roomNumber);
        return ResponseEntity.ok(service.findBookingsByRoomNumber(roomNumber));
    }

    @GetMapping("/{bookingId}")
    public ResponseEntity<BookingDTO> findBookingById(@PathVariable("bookingId") Long bookingID) {
        log.info("Starting booking search by ID: {}", bookingID);
        return ResponseEntity.ok(service.findBookingById(bookingID));
    }

    @PostMapping
    public ResponseEntity<BookingDTO> createBooking(@Valid @RequestBody BookingDTO request) {
        log.info("Starting booking creation for room number: {}", request.getRoom().getRoomNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createBooking(request));
    }

    @DeleteMapping("/{bookingId}")
    public Object deleteBooking(@PathVariable("bookingId") Long bookingID) {
        log.info("Starting booking removal by ID: {}", bookingID);
        service.deleteBooking(bookingID);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{bookingId}")
    public Object updateBooking(@PathVariable("bookingId") Long bookingID, @Valid @RequestBody BookingDTO request) {
        log.info("Starting booking update by ID: {}", bookingID);
        return ResponseEntity.ok(service.updateBooking(bookingID, request));
    }
}
