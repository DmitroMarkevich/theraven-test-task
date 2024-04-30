package com.theraven.task.customer;

import com.theraven.task.customer.dto.CustomerRequestDto;
import com.theraven.task.customer.dto.CustomerResponseDto;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

/**
 * The CustomerMapper class is responsible for mapping between Customer entities and DTOs
 * It utilizes the ModelMapper library for the mapping process.
 */
@Component
@RequiredArgsConstructor
public class CustomerMapper {
    private final ModelMapper modelMapper;

    public Customer mapRequestDtoToEntity(CustomerRequestDto customerRequestDto) {
        return modelMapper.map(customerRequestDto, Customer.class);
    }

    public CustomerResponseDto mapEntityToResponseDto(Customer customer) {
        return modelMapper.map(customer, CustomerResponseDto.class);
    }
}
