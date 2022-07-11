package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.ErrorDTO;
import com.jolvino.hotel.core.exceptions.BookingUpdateException;
import com.jolvino.hotel.core.exceptions.NoContentException;
import com.jolvino.hotel.core.exceptions.SchedulingException;
import com.jolvino.hotel.core.model.Booking;
import com.jolvino.hotel.core.model.Customer;
import com.jolvino.hotel.core.model.Room;
import org.hibernate.TransientPropertyValueException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Order(Ordered.HIGHEST_PRECEDENCE)
@org.springframework.web.bind.annotation.ControllerAdvice
public class HotelControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> processMethodArgumentNotValidException() {
        return ResponseEntity.badRequest().body(
                ErrorDTO.builder()
                        .statuesCode(HttpStatus.BAD_REQUEST.value())
                        .statusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .build());
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<Object> processNoContentException() {
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(TransientPropertyValueException.class)
    public ResponseEntity<Object> processTransientPropertyValueException(TransientPropertyValueException ex) throws ClassNotFoundException {
        String detail = null;
        if(ex.getTransientEntityName().equals(Room.class.getName()))
            detail = "Room does not exist";
        else if(ex.getTransientEntityName().equals(Customer.class.getName()))
            detail = "Customer does not exist";
        else if(ex.getTransientEntityName().equals(Booking.class.getName()))
            detail = "Booking does not exist";

        return ResponseEntity.unprocessableEntity().body(
                ErrorDTO.builder()
                        .statuesCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .statusMessage(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .detail(detail)
                        .build());

    }

    @ExceptionHandler(SchedulingException.class)
    public ResponseEntity<Object> processInvalidSchedulingException(){
        return ResponseEntity.unprocessableEntity().body(
                ErrorDTO.builder()
                        .statuesCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .statusMessage(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .detail("Invalid schedule for this room")
                        .build());
    }

    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<Object> processDataAccessException(){
        return ResponseEntity.unprocessableEntity().body(
                ErrorDTO.builder()
                        .statuesCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .statusMessage(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .detail("Entity not found")
                        .build());
    }

    @ExceptionHandler(BookingUpdateException.class)
    public ResponseEntity<Object> processBookingUpdateException(){
        return ResponseEntity.unprocessableEntity().body(
                ErrorDTO.builder()
                        .statuesCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .statusMessage(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .detail("Customer and Room must not be updated")
                        .build());
    }

}
