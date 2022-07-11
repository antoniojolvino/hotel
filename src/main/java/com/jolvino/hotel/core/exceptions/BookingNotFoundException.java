package com.jolvino.hotel.core.exceptions;

import org.springframework.dao.DataAccessException;


public class BookingNotFoundException extends DataAccessException {
    public BookingNotFoundException() {
        super("");
    }
}