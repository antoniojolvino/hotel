package com.jolvino.hotel.controller.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class RoomDTO {

    @NotNull
    @Min(value = 1)
    @Max(value = 999)
    @JsonProperty("room-number")
    private Integer number;
}
