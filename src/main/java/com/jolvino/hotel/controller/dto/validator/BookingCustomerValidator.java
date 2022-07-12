package com.jolvino.hotel.controller.dto.validator;

import com.jolvino.hotel.controller.dto.CustomerDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class BookingCustomerValidator implements
        ConstraintValidator<BookingCustomerConstraint, CustomerDTO> {
    @Override
    public void initialize(BookingCustomerConstraint contactNumber) {
    }

    @Override
    public boolean isValid(CustomerDTO customer,
                           ConstraintValidatorContext cxt) {
        return customer.getIdentificationDocument() != null && !customer.getIdentificationDocument().isBlank();
    }
}
