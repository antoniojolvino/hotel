package com.jolvino.hotel.core.service.impl;

import com.jolvino.hotel.core.exceptions.NoContentException;
import com.jolvino.hotel.core.model.Room;
import com.jolvino.hotel.core.repository.RoomRepository;
import com.jolvino.hotel.core.service.RoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {
    private final RoomRepository repository;

    public List<Room> findAllRooms() {
        List<Room> rooms = repository.findAll();
        if (rooms.isEmpty()) {
            throw new NoContentException();
        }
        return rooms;
    }

    public Room createRoom(Room room) {
        return repository.save(room);
    }
}
