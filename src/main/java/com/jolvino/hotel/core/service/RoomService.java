package com.jolvino.hotel.core.service;

import com.jolvino.hotel.core.model.Room;

import java.util.List;

public interface RoomService {
    List<Room> findAllRooms();

    Room createRoom(Room room);
}
