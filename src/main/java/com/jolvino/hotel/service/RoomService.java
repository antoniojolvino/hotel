package com.jolvino.hotel.service;

import com.jolvino.hotel.controller.dto.RoomDTO;

import java.util.List;

public interface RoomService {
    List<RoomDTO> findAllRooms();

    RoomDTO createRoom(RoomDTO room);

}
