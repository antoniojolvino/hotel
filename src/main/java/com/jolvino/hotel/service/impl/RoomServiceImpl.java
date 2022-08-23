package com.jolvino.hotel.service.impl;

import com.jolvino.hotel.controller.dto.RoomDTO;
import com.jolvino.hotel.controller.dto.mapper.RoomMapper;
import com.jolvino.hotel.exception.EmptyResultException;
import com.jolvino.hotel.exception.RoomAlreadyExistsException;
import com.jolvino.hotel.model.repository.RoomRepository;
import com.jolvino.hotel.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoomServiceImpl implements RoomService {
    private final RoomRepository repository;

    private final RoomMapper mapper;

    public List<RoomDTO> findAllRooms() {
        List<RoomDTO> roomsDTO = mapper.modelToDto(
                Optional.of(repository.findAll()).filter(
                        list -> !list.isEmpty()
                ).orElseThrow(EmptyResultException::new));

        log.info("There are {} rooms found", roomsDTO.size());
        return roomsDTO;
    }

    public RoomDTO createRoom(RoomDTO roomDTO) {
        if (repository.existsById(roomDTO.getRoomNumber()))
            throw new RoomAlreadyExistsException();

        RoomDTO roomDTOCreated = mapper.modelToDto(repository.save(mapper.dtoToModel(roomDTO)));
        log.info("Room number {} was created", roomDTOCreated.getRoomNumber());
        return roomDTOCreated;
    }
}
