package com.jolvino.hotel.core.service.impl;

import com.jolvino.hotel.core.model.Customer;
import com.jolvino.hotel.core.repository.CustomerRepository;
import com.jolvino.hotel.util.mock.MockObjects;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl service;
    @Mock
    private CustomerRepository repository;

    @Test
    void findAllCustomers() {
        //Given
        List<Customer> customers = MockObjects.getCustomers();

        //When
        when(repository.findAll()).thenReturn(customers);
        List<Customer> response = service.findAllCustomers();

        //Then
        assertEquals(customers.size(), response.size());
        assertEquals(customers.get(0), response.get(0));
    }

    @Test
    void findById() {
        //Given
        Customer customer = MockObjects.getCustomer();
        String identificationDocument = "A123";
        //When
        when(repository.findById(any())).thenReturn(Optional.of(customer));
        Customer response = service.findById(identificationDocument);
        //Then
        assertEquals(customer, response);
    }

    @Test
    void createCustomer() {
        //Given
        Customer customer = MockObjects.getCustomer();
        //When
        when(repository.save(any())).thenReturn(customer);
        Customer response = service.createCustomer(customer);
        //Then
        assertEquals(customer, response);
    }
}