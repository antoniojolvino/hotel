package com.jolvino.hotel.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDTO {
    @JsonProperty("status-code")
    private int statuesCode;
    @JsonProperty("status-message")
    private String statusMessage;
    @JsonProperty("detail-message")
    private String detail;
}
