package com.jolvino.hotel.core.service;

import com.jolvino.hotel.core.model.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> findAllCustomers();

    Customer findById(String identificationDocument);

    Customer createCustomer(Customer customer);
}
