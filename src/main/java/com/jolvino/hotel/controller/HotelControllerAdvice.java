package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.ErrorDTO;
import com.jolvino.hotel.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.TransientPropertyValueException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
@Slf4j
public class HotelControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> processMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.info("MethodArgumentNotValidException was thrown and handled");
        ArrayList<ErrorDTO.Field> fields = new ArrayList<>();
        ex.getFieldErrors().forEach(fieldError ->
                fields.add(ErrorDTO.Field.builder()
                        .fieldName(fieldError.getField())
                        .message(fieldError.getDefaultMessage())
                        .build()));

        return ResponseEntity.badRequest().body(
                ErrorDTO.builder()
                        .statuesCode(HttpStatus.BAD_REQUEST.value())
                        .statusMessage(HttpStatus.BAD_REQUEST.getReasonPhrase())
                        .errorFields(fields)
                        .build());
    }

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public void processNotFoundException() {
        log.info("NotFoundException was thrown and handled");
    }

    @ExceptionHandler(EmptyResultException.class)
    @ResponseStatus(HttpStatus.OK)
    public void processEmptyResultException() {
        log.info("EmptyResultException was thrown and handled");
    }

    @ExceptionHandler(TransientPropertyValueException.class)
    public ResponseEntity<ErrorDTO> processTransientPropertyValueException(TransientPropertyValueException ex) {
        log.info("TransientPropertyValueException was thrown and handled");
        return ResponseEntity.unprocessableEntity().body(ErrorDTO.builder()
                .statuesCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                .statusMessage(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                .errorFields(
                        List.of(ErrorDTO.Field.builder()
                                .fieldName(ex.getPropertyName())
                                .message("must exist").build()))
                .build());
    }

    @ExceptionHandler(SchedulingException.class)
    public ResponseEntity<ErrorDTO> processSchedulingException(SchedulingException ex) {
        log.info("SchedulingException was thrown and handled");
        return ResponseEntity.unprocessableEntity().body(
                ErrorDTO.builder()
                        .statuesCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .statusMessage(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .detail(ex.getMessage())
                        .build());
    }

    @ExceptionHandler(BookingUpdateException.class)
    public ResponseEntity<ErrorDTO> processBookingUpdateException() {
        log.info("BookingUpdateException was thrown and handled");
        return ResponseEntity.unprocessableEntity().body(
                ErrorDTO.builder()
                        .statuesCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .statusMessage(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .detail("Customer and Room must not be changed")
                        .build());
    }

    @ExceptionHandler(CreateBookingException.class)
    public ResponseEntity<ErrorDTO> processCreateBookingException(CreateBookingException ex) {
        log.info("CreateBookingException was thrown and handled");
        return ResponseEntity.unprocessableEntity().body(
                ErrorDTO.builder()
                        .statuesCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .statusMessage(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .detail(ex.getMessage())
                        .build());
    }


    @ExceptionHandler(RoomAlreadyExistsException.class)
    public ResponseEntity<ErrorDTO> processRoomAlreadyExistsException() {
        log.info("RoomAlreadyExistsException was thrown and handled");
        return ResponseEntity.unprocessableEntity().body(
                ErrorDTO.builder()
                        .statuesCode(HttpStatus.UNPROCESSABLE_ENTITY.value())
                        .statusMessage(HttpStatus.UNPROCESSABLE_ENTITY.getReasonPhrase())
                        .detail("Room already exists")
                        .build());
    }


}
