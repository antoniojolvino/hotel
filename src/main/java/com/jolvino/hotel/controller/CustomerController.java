package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.CustomerDTO;
import com.jolvino.hotel.controller.dto.mapper.CustomerMapper;
import com.jolvino.hotel.core.model.Customer;
import com.jolvino.hotel.core.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService service;

    private final CustomerMapper mapper;

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> findAllCustomers() {
        List<CustomerDTO> response = mapper.modelToDto(service.findAllCustomers());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerDTO> findCustomerById(@PathVariable("customerId") String identificationDocument) {
        CustomerDTO response = mapper.modelToDto(service.findById(identificationDocument));
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Valid @RequestBody CustomerDTO request) {
        Customer customer = mapper.dtoToModel(request);
        CustomerDTO response = mapper.modelToDto(service.createCustomer(customer));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
