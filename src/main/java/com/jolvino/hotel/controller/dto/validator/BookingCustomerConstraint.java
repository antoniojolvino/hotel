package com.jolvino.hotel.controller.dto.validator;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = BookingCustomerValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BookingCustomerConstraint {
    String message() default "Customer identificationDocument must be filled";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
