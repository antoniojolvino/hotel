package com.jolvino.hotel.controller.dto.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BookingDatesValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface BookingDatesConstraint {
    String message() default "Customer identificationDocument must be filled";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
