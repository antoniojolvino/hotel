package com.jolvino.hotel.controller.dto.validators;

import com.jolvino.hotel.controller.dto.BookingDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.temporal.ChronoUnit;

public class BookingDatesValidator implements
        ConstraintValidator<BookingDatesConstraint, BookingDTO> {
    @Override
    public void initialize(BookingDatesConstraint contactNumber) {
    }

    @Override
    public boolean isValid(BookingDTO bookingDTO,
                           ConstraintValidatorContext cxt) {
        long period = ChronoUnit.DAYS.between(bookingDTO.getStartDate(), bookingDTO.getEndDate());

        return period >=0 && period <3;
    }
}
