package com.jolvino.hotel.service.enums;

import com.jolvino.hotel.exception.SchedulingException;
import com.jolvino.hotel.model.Booking;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.function.Function;

@AllArgsConstructor
@Getter
public enum ScheduleValidationEnum {
    PAST_FROM_NOW(booking -> ChronoUnit.DAYS.between(LocalDate.now(), booking.getStartDate()) <= 0, new SchedulingException("All reservations start at least the next day of booking")),
    FAR_FROM_NOW(booking -> ChronoUnit.DAYS.between(LocalDate.now(), booking.getStartDate()) > 30, new SchedulingException("The room can’t be reserved more than 30 days in advance")),
    SWITCHED_DATES(booking -> ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate()) < 0, new SchedulingException("Start Date and End Date are switched")),
    LONGER_THREE(booking -> ChronoUnit.DAYS.between(booking.getStartDate(), booking.getEndDate()) >= 3, new SchedulingException("The stay can’t be longer than 3 days"));

    private final Function<Booking, Boolean> function;

    private final SchedulingException exception;

    public static ScheduleValidationEnum validateBooking(Booking booking) {
        for (ScheduleValidationEnum item : ScheduleValidationEnum.values()) {
            if (item.getFunction().apply(booking)) {
                return item;
            }
        }

        return null;
    }
}
