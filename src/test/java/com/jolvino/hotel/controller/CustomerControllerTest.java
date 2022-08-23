package com.jolvino.hotel.controller;

import com.jolvino.hotel.controller.dto.CustomerDTO;
import com.jolvino.hotel.service.CustomerService;
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
    @Autowired
    MockMvc mockMvc;

    @Test
    void findAllCustomers() throws Exception {
        //Given
        List<CustomerDTO> customersDTO = MockObjects.getCustomersDTO();

        //When - Then
        when(service.findAllCustomers()).thenReturn(customersDTO);
        mockMvc.perform(
                        get("/customers")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].identificationDocument",
                        Matchers.is(customersDTO.get(0).getIdentificationDocument())));
    }

    @Test
    void findCustomerById() throws Exception {
        //Given
        CustomerDTO customerDTO = MockObjects.getCustomerDTO();
        String identificationDocument = "A123";

        //When - Then
        when(service.findById(any())).thenReturn(customerDTO);
        mockMvc.perform(
                        get("/customers/{identificationDocument}", identificationDocument)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.identificationDocument", Matchers.is(identificationDocument)));
    }

    @Test
    void createCustomer() throws Exception {
        //Given
        CustomerDTO customerDTO = MockObjects.getCustomerDTO();
        String content = MockObjects.getCustomerDTOAsJson();

        //When - Then
        when(service.createCustomer(any())).thenReturn(customerDTO);

        mockMvc.perform(
                        post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.identificationDocument",
                        Matchers.is(customerDTO.getIdentificationDocument())));
    }


}