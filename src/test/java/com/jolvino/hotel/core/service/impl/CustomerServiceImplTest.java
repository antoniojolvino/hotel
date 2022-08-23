package com.jolvino.hotel.core.service.impl;

import com.jolvino.hotel.controller.dto.CustomerDTO;
import com.jolvino.hotel.controller.dto.mapper.CustomerMapper;
import com.jolvino.hotel.exception.EmptyResultException;
import com.jolvino.hotel.exception.NotFoundException;
import com.jolvino.hotel.model.Customer;
import com.jolvino.hotel.model.repository.CustomerRepository;
import com.jolvino.hotel.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.jolvino.hotel.util.mock.MockObjects.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerServiceImplTest {

    @Mock
    private CustomerMapper mapper;

    @Mock
    private CustomerRepository repository;

    @InjectMocks
    private CustomerServiceImpl service;

    @Test
    void findAllCustomers() {
        //Given
        List<CustomerDTO> customersDTO = getCustomersDTO();
        List<Customer> customers = getCustomers();

        //When
        when(repository.findAll()).thenReturn(customers);
        when(mapper.modelToDto(anyList())).thenReturn(customersDTO);
        List<CustomerDTO> response = service.findAllCustomers();

        //Then
        assertEquals(customersDTO.size(), response.size());
        assertEquals(customersDTO.get(0), response.get(0));
    }

    @Test
    void findAllCustomersEmptyResultException() {
        //Given
        List<Customer> customers = List.of();

        //When
        when(repository.findAll()).thenReturn(customers);

        //Then
        assertThrows(EmptyResultException.class, () -> service.findAllCustomers());

    }

    @Test
    void findById() {
        //Given
        CustomerDTO customerDTO = getCustomerDTO();
        Customer customer = getCustomer();
        String identificationDocument = "A123";
        //When
        when(repository.findById(any())).thenReturn(Optional.of(customer));
        when(mapper.modelToDto(any(Customer.class))).thenReturn(customerDTO);
        CustomerDTO response = service.findById(identificationDocument);
        //Then
        assertEquals(customerDTO, response);
    }

    @Test
    void findByIdNotFoundException() {
        //Given
        String identificationDocument = "A123";
        //When
        when(repository.findById(any())).thenReturn(Optional.empty());
        //Then
        assertThrows(NotFoundException.class, () -> service.findById(identificationDocument));
    }

    @Test
    void createCustomer() {
        //Given
        CustomerDTO customerDTO = getCustomerDTO();
        Customer customer = getCustomer();
        //When
        when(repository.save(any())).thenReturn(customer);
        when(mapper.dtoToModel(any())).thenReturn(customer);
        when(mapper.modelToDto(any(Customer.class))).thenReturn(customerDTO);
        CustomerDTO response = service.createCustomer(customerDTO);
        //Then
        assertEquals(customerDTO, response);
    }


}