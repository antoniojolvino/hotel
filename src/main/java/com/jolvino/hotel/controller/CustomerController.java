package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.CustomerDTO;
import com.jolvino.hotel.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final CustomerService service;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAllCustomers() {
        log.info("Starting to find all customers");

        return ResponseEntity.ok(service.findAllCustomers());
    }

    @GetMapping("/{identificationDocument}")
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable("identificationDocument") String identificationDocument) {
        log.info("Starting customer search by identificationDocument: {}", identificationDocument);
        return ResponseEntity.ok(service.findById(identificationDocument));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO request) {
        log.info("Starting customer creation with identificationDocument: {}", request.getIdentificationDocument());
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createCustomer(request));
    }
}
