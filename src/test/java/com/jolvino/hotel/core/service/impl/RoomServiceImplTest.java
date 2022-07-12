package com.jolvino.hotel.core.service.impl;

import com.jolvino.hotel.core.model.Room;
import com.jolvino.hotel.core.repository.RoomRepository;
import com.jolvino.hotel.util.mock.MockObjects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {

    @InjectMocks
    private RoomServiceImpl service;
    @Mock
    private RoomRepository repository;

    @Test
    void findAllRooms() {
        //Given
        List<Room> rooms = MockObjects.getRooms();

        //When
        when(repository.findAll()).thenReturn(rooms);
        List<Room> response = service.findAllRooms();

        //Then
        assertEquals(rooms.size(), response.size());
        assertEquals(rooms.get(0), response.get(0));
    }

    @Test
    void createRoom() {
        //Given
        Room room = MockObjects.getRoom();
        //When
        when(repository.save(any())).thenReturn(room);
        Room response = service.createRoom(room);
        //Then
        assertEquals(room, response);
    }
}