package com.jolvino.hotel.controller.dto.mappers;

import com.jolvino.hotel.controller.dto.RoomDTO;
import com.jolvino.hotel.core.model.Room;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface RoomMapper {
    Room dtoToModel(RoomDTO dto);

    RoomDTO modelToDto(Room room);

    List<RoomDTO> modelToDTO(List<Room> rooms);
}
