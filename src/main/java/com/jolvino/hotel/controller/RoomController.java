package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.RoomDTO;
import com.jolvino.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Slf4j
public class RoomController {

    private final RoomService service;


    @GetMapping
    public ResponseEntity<List<RoomDTO>> findAllRooms() {
        log.info("Starting to find all rooms");
        return ResponseEntity.ok(service.findAllRooms());
    }

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomDTO request) {
        log.info("Starting room creation with roomNumber: {}", request.getRoomNumber());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createRoom(request));
    }
}
