package com.jolvino.hotel.controller.dto.mappers;

import com.jolvino.hotel.controller.dto.BookingDTO;
import com.jolvino.hotel.core.model.Booking;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface BookingMapper {
    Booking dtoToModel(BookingDTO dto);

    BookingDTO modelToDto(Booking booking);

    List<BookingDTO> modelToDto(List<Booking> bookings);
}
