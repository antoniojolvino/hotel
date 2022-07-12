package com.jolvino.hotel.controller.dto.mapper;

import com.jolvino.hotel.controller.dto.CustomerDTO;
import com.jolvino.hotel.core.model.Customer;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface CustomerMapper {
    Customer dtoToModel(CustomerDTO dto);

    CustomerDTO modelToDto(Customer customer);

    List<CustomerDTO> modelToDto(List<Customer> customers);
}
