package com.theraven.task.customer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.theraven.task.customer.dto.CustomerRequestDto;
import com.theraven.task.customer.dto.CustomerResponseDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.Date;

class CustomerMapperTest {

    private final Long ID = 1L;
    private final Long TIME = new Date().getTime();
    private final String FULL_NAME = "Dmytro Markevych";
    private final String EMAIL = "dmytro@gmail.com";
    private final String PHONE_NUMBER = "+31234567890";

    private ModelMapper modelMapper;
    private CustomerMapper customerMapper;

    @BeforeEach
    public void setUp() {
        modelMapper = mock(ModelMapper.class);
        customerMapper = new CustomerMapper(modelMapper);
    }

    @Test
    public void testMapRequestDtoToEntity() {
        CustomerRequestDto requestDto = new CustomerRequestDto(FULL_NAME, EMAIL, PHONE_NUMBER);
        Customer expectedUser = new Customer(ID, TIME, TIME, FULL_NAME, EMAIL, PHONE_NUMBER, true);

        when(modelMapper.map(requestDto, Customer.class)).thenReturn(expectedUser);
        Customer customer = customerMapper.mapRequestDtoToEntity(requestDto);
        assertEquals(expectedUser, customer);
    }

    @Test
    void testMapEntityToResponseDto() {
        Customer customer = new Customer(ID, TIME, TIME, FULL_NAME, EMAIL, PHONE_NUMBER, true);
        CustomerResponseDto customerResponseDto = new CustomerResponseDto(ID, FULL_NAME, EMAIL, PHONE_NUMBER);

        when(modelMapper.map(customer, CustomerResponseDto.class)).thenReturn(customerResponseDto);
        CustomerResponseDto mappedResponseDto = customerMapper.mapEntityToResponseDto(customer);
        assertEquals(customerResponseDto, mappedResponseDto);
    }
}
