package com.jolvino.hotel.core.service.impl;

import com.jolvino.hotel.core.exceptions.NoContentException;
import com.jolvino.hotel.core.model.Customer;
import com.jolvino.hotel.core.repository.CustomerRepository;
import com.jolvino.hotel.core.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;

    public List<Customer> findAllCustomers() {
        List<Customer> customers = repository.findAll();
        if (customers.isEmpty()) {
            throw new NoContentException();
        }
        return customers;
    }

    public Customer findById(String identificationDocument) {
        return repository.findById(identificationDocument).orElseThrow(NoContentException::new);
    }

    public Customer createCustomer(Customer customer) {
        return repository.save(customer);
    }
}
