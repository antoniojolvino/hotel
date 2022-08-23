package com.jolvino.hotel.exception;

import lombok.Getter;

@Getter
public class SchedulingException extends RuntimeException {
    public SchedulingException(String message) {
        super(message);
    }
}
