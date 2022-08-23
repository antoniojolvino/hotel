package com.jolvino.hotel.controller.dto.mapper;

import com.jolvino.hotel.controller.dto.BookingDTO;
import com.jolvino.hotel.model.Booking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper
public interface BookingMapper {
    Booking dtoToModel(BookingDTO dto);


    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "room", ignore = true),
            @Mapping(target = "customer", ignore = true)
    })
    Booking dtoToModel(@MappingTarget Booking booking, BookingDTO dto);

    BookingDTO modelToDto(Booking booking);

    List<BookingDTO> modelToDto(List<Booking> bookings);
}
