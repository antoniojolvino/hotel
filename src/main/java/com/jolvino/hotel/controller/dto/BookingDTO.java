package com.jolvino.hotel.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.jolvino.hotel.controller.dto.validator.BookingCustomerConstraint;
import com.jolvino.hotel.controller.dto.validator.BookingRoomConstraint;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookingDTO {

    @Null
    private Long id;

    @NotNull
    private LocalDate startDate;

    @NotNull
    private LocalDate endDate;

    @NotNull
    @BookingRoomConstraint
    private RoomDTO room;

    @NotNull
    @BookingCustomerConstraint
    private CustomerDTO customer;
}
