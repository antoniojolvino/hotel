package com.jolvino.hotel.service;

import com.jolvino.hotel.controller.dto.CustomerDTO;

import java.util.List;

public interface CustomerService {

    List<CustomerDTO> findAllCustomers();

    CustomerDTO findById(String identificationDocument);

    CustomerDTO createCustomer(CustomerDTO customer);
}
