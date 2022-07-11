package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.RoomDTO;
import com.jolvino.hotel.controller.dto.mappers.RoomMapper;
import com.jolvino.hotel.core.model.Room;
import com.jolvino.hotel.core.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService service;

    private final RoomMapper mapper;

    @GetMapping
    public ResponseEntity<List<RoomDTO>> findAllRooms() {
        List<RoomDTO> response = mapper.modelToDTO(service.findAllRooms());
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<RoomDTO> createRoom(@Valid @RequestBody RoomDTO request) {
        Room room = mapper.dtoToModel(request);
        RoomDTO response = mapper.modelToDto(service.createRoom(room));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
