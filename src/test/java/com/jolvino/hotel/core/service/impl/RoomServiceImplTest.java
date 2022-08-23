package com.jolvino.hotel.core.service.impl;

import com.jolvino.hotel.controller.dto.RoomDTO;
import com.jolvino.hotel.controller.dto.mapper.RoomMapper;
import com.jolvino.hotel.exception.EmptyResultException;
import com.jolvino.hotel.exception.RoomAlreadyExistsException;
import com.jolvino.hotel.model.Room;
import com.jolvino.hotel.model.repository.RoomRepository;
import com.jolvino.hotel.service.impl.RoomServiceImpl;
import com.jolvino.hotel.util.mock.MockObjects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RoomServiceImplTest {
    @Mock
    private RoomMapper mapper;

    @Mock
    private RoomRepository repository;

    @InjectMocks
    private RoomServiceImpl service;

    @Test
    void findAllRooms() {
        //Given
        List<Room> rooms = MockObjects.getRooms();
        List<RoomDTO> roomsDTO = MockObjects.getRoomsDTO();

        //When
        when(repository.findAll()).thenReturn(rooms);
        when(mapper.modelToDto(anyList())).thenReturn(roomsDTO);
        List<RoomDTO> response = service.findAllRooms();

        //Then
        assertEquals(roomsDTO.size(), response.size());
        assertEquals(roomsDTO.get(0), response.get(0));
    }

    @Test
    void createRoom() {
        //Given
        Room room = MockObjects.getRoom();
        RoomDTO roomDTO = MockObjects.getRoomDTO();
        //When
        when(mapper.modelToDto(any(Room.class))).thenReturn(roomDTO);
        when(mapper.dtoToModel(any(RoomDTO.class))).thenReturn(room);
        when(repository.save(any())).thenReturn(room);
        RoomDTO response = service.createRoom(roomDTO);
        //Then
        assertEquals(roomDTO, response);
    }

    @Test
    void findAllRoomsEmptyResultException() {
        //Given
        //When
        when(repository.findAll()).thenReturn(List.of());
        //Then
        assertThrows(EmptyResultException.class, () -> service.findAllRooms());
    }

    @Test
    void createRoomRoomAlreadyExistsException() {
        //Given
        RoomDTO roomDTO = MockObjects.getRoomDTO();
        //When
        when(repository.existsById(any())).thenReturn(Boolean.TRUE);
        //Then
        assertThrows(RoomAlreadyExistsException.class, () -> service.createRoom(roomDTO));
    }
}