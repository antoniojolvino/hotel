package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.CustomerDTO;
import com.jolvino.hotel.controller.dto.mapper.CustomerMapper;
import com.jolvino.hotel.core.model.Customer;
import com.jolvino.hotel.core.service.CustomerService;
import com.jolvino.hotel.util.mock.MockObjects;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @MockBean
    CustomerService service;
    @MockBean
    CustomerMapper mapper;
    @Autowired
    MockMvc mockMvc;

    @Test
    void findAllCustomers() throws Exception {
        //Given
        List<Customer> customers = MockObjects.getCustomers();
        List<CustomerDTO> customersDTO = MockObjects.getCustomersDTO();

        //When - Then
        when(mapper.modelToDto(anyList())).thenReturn(customersDTO);
        when(service.findAllCustomers()).thenReturn(customers);
        mockMvc.perform(
                        get("/customers")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].identification-document",
                        Matchers.is(customersDTO.get(0).getIdentificationDocument())));
    }

    @Test
    void findCustomerById() throws Exception {
        //Given
        Customer customer = MockObjects.getCustomer();
        CustomerDTO customerDTO = MockObjects.getCustomerDTO();
        String identificationDocument = "A123";

        //When - Then
        when(mapper.modelToDto(any(Customer.class))).thenReturn(customerDTO);
        when(service.findById(any())).thenReturn(customer);
        mockMvc.perform(
                        get("/customers/{identification-document}", identificationDocument)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.identification-document", Matchers.is(identificationDocument)));
    }

    @Test
    void createCustomer() throws Exception {
        //Given
        Customer customer = MockObjects.getCustomer();
        CustomerDTO customerDTO = MockObjects.getCustomerDTO();
        String content = MockObjects.getCustomerDTOAsJson();

        //When - Then
        when(mapper.modelToDto(any(Customer.class))).thenReturn(customerDTO);
        when(service.createCustomer(any())).thenReturn(customer);

        mockMvc.perform(
                        post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.identification-document",
                        Matchers.is(customerDTO.getIdentificationDocument())));
    }
}