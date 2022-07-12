package com.jolvino.hotel.controller.dto.validators;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BookingRoomValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BookingRoomConstraint {
    String message() default "Room number must be filled";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
