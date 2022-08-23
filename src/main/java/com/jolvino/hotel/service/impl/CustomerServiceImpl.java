package com.jolvino.hotel.service.impl;

import com.jolvino.hotel.controller.dto.CustomerDTO;
import com.jolvino.hotel.controller.dto.mapper.CustomerMapper;
import com.jolvino.hotel.exception.EmptyResultException;
import com.jolvino.hotel.exception.NotFoundException;
import com.jolvino.hotel.model.Customer;
import com.jolvino.hotel.model.repository.CustomerRepository;
import com.jolvino.hotel.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository repository;

    private final CustomerMapper mapper;

    public List<CustomerDTO> findAllCustomers() {
        return Optional.of(repository.findAll())
                .filter(list -> !list.isEmpty())
                .map(customers -> {
                    log.info("There are {} customers found", customers.size());
                    return mapper.modelToDto(customers);
                }).orElseThrow(EmptyResultException::new);
    }

    public CustomerDTO findById(String identificationDocument) {
        CustomerDTO customerDTO = repository.findById(identificationDocument)
                .map(mapper::modelToDto)
                .orElseThrow(NotFoundException::new);
        log.info("Customer was found with identificationDocument: {}", identificationDocument);
        return customerDTO;
    }

    public CustomerDTO createCustomer(CustomerDTO request) {
        Customer customer = mapper.dtoToModel(request);
        Customer savedCustomer = repository.save(customer);
        log.info("Customer was registered with identificationDocument: {}", customer.getIdentificationDocument());
        return mapper.modelToDto(savedCustomer);
    }
}
