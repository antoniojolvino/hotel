package com.jolvino.hotel.controller.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorDTO {
    private int statuesCode;
    private String statusMessage;
    private String detail;
    private List<Field> errorFields;

    @Data
    @Builder
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    public static class Field {
        private String fieldName;
        private String message;
    }
}
