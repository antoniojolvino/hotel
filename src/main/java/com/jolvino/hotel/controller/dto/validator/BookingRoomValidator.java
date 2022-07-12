package com.jolvino.hotel.controller.dto.validator;

import com.jolvino.hotel.controller.dto.RoomDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookingRoomValidator implements
        ConstraintValidator<BookingRoomConstraint, RoomDTO> {
    @Override
    public void initialize(BookingRoomConstraint contactNumber) {
    }

    @Override
    public boolean isValid(RoomDTO room,
                           ConstraintValidatorContext cxt) {
        return room.getNumber() != null;
    }
}
