package com.jolvino.hotel.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jolvino.hotel.controller.dto.validators.BookingCustomerConstraint;
import com.jolvino.hotel.controller.dto.validators.BookingRoomConstraint;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class BookingDTO {
    @JsonProperty("booking-id")
    private Long id;

    @NotNull
    @JsonProperty("start-date")
    private LocalDate startDate;

    @NotNull
    @JsonProperty("end-date")
    private LocalDate endDate;

    @NotNull
    @BookingRoomConstraint
    @JsonProperty("room")
    private RoomDTO room;

    @NotNull
    @BookingCustomerConstraint
    @JsonProperty("customer")
    private CustomerDTO customer;
}
