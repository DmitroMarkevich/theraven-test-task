package com.theraven.task.customer;

import com.theraven.task.customer.dto.CustomerRequestDto;
import com.theraven.task.customer.dto.CustomerResponseDto;
import com.theraven.task.errorhandling.exception.CustomerExistsException;
import com.theraven.task.errorhandling.exception.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    private final Long ID = 1L;
    private final Long TIME = new Date().getTime();
    private final String FULL_NAME = "Dmytro Markevych";
    private final String EMAIL = "dmytro@gmail.com";
    private final String PHONE_NUMBER = "+31234567890";

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerService customerService;

    @Test
    void testCreateCustomer() {
        CustomerRequestDto requestDto = new CustomerRequestDto(FULL_NAME, EMAIL, PHONE_NUMBER);
        Customer customer = new Customer(ID, TIME, TIME, FULL_NAME, EMAIL, PHONE_NUMBER, true);
        CustomerResponseDto responseDto = new CustomerResponseDto(ID, FULL_NAME, FULL_NAME, PHONE_NUMBER);

        when(customerRepository.findByEmail(EMAIL)).thenReturn(Optional.empty());
        when(customerMapper.mapRequestDtoToEntity(requestDto)).thenReturn(customer);
        when(customerMapper.mapEntityToResponseDto(customer)).thenReturn(responseDto);
        when(customerRepository.save(customer)).thenReturn(customer);

        CustomerResponseDto createdCustomer = customerService.createCustomer(requestDto);

        assertNotNull(createdCustomer);
        assertEquals(responseDto, createdCustomer);
        verify(customerRepository, times(1)).findByEmail(EMAIL);
        verify(customerMapper, times(1)).mapRequestDtoToEntity(requestDto);
        verify(customerMapper, times(1)).mapEntityToResponseDto(customer);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testDeleteCustomer() {
        Customer existingCustomer = new Customer(ID, TIME, TIME, FULL_NAME, EMAIL, PHONE_NUMBER, true);
        when(customerRepository.findByIdAndActiveTrue(ID)).thenReturn(Optional.of(existingCustomer));

        customerService.deleteCustomer(ID);

        assertFalse(existingCustomer.isActive());
        verify(customerRepository, times(1)).findByIdAndActiveTrue(ID);
        verify(customerRepository, times(1)).delete(existingCustomer);
        verifyNoMoreInteractions(customerRepository);
    }

    @Test
    public void testCreateCustomerExistException() {
        CustomerRequestDto requestDto = new CustomerRequestDto(FULL_NAME, EMAIL, PHONE_NUMBER);
        Customer existingCustomer = new Customer(ID, TIME, TIME, FULL_NAME, EMAIL, PHONE_NUMBER, true);
        when(customerRepository.findByEmail(requestDto.getEmail())).thenReturn(Optional.of(existingCustomer));

        assertThrows(CustomerExistsException.class, () -> customerService.createCustomer(requestDto));
    }

    @Test
    void testGetCustomerById() {
        Customer customer = new Customer(ID, TIME, TIME, FULL_NAME, EMAIL, PHONE_NUMBER, true);
        CustomerResponseDto responseDto = new CustomerResponseDto(ID, FULL_NAME, EMAIL, PHONE_NUMBER);

        when(customerRepository.findByIdAndActiveTrue(ID)).thenReturn(Optional.of(customer));
        when(customerMapper.mapEntityToResponseDto(customer)).thenReturn(responseDto);

        CustomerResponseDto result = customerService.getCustomerById(ID);

        assertNotNull(result);
        assertEquals(responseDto, result);
        verify(customerRepository, times(1)).findByIdAndActiveTrue(ID);
        verify(customerMapper, times(1)).mapEntityToResponseDto(customer);
        verifyNoMoreInteractions(customerRepository);
        verifyNoMoreInteractions(customerMapper);
    }

    @Test
    void testUpdateCustomer() {
        CustomerRequestDto requestDto = new CustomerRequestDto(FULL_NAME, EMAIL, PHONE_NUMBER);
        Customer existingCustomer = new Customer(ID, TIME, TIME, FULL_NAME, EMAIL, "+11234567890", true);
        CustomerResponseDto responseDto = new CustomerResponseDto(ID, FULL_NAME, EMAIL, PHONE_NUMBER);

        when(customerRepository.findByIdAndActiveTrue(ID)).thenReturn(Optional.of(existingCustomer));
        when(customerRepository.save(existingCustomer)).thenReturn(existingCustomer);
        when(customerMapper.mapEntityToResponseDto(existingCustomer)).thenReturn(responseDto);

        CustomerResponseDto updatedCustomer = customerService.updateCustomer(ID, requestDto);

        assertNotNull(updatedCustomer);
        assertEquals(ID, updatedCustomer.getId());
        assertEquals(FULL_NAME, updatedCustomer.getFullName());
        assertEquals(EMAIL, updatedCustomer.getEmail());
        assertEquals(PHONE_NUMBER, updatedCustomer.getPhone());
        verify(customerRepository, times(1)).findByIdAndActiveTrue(ID);
        verify(customerRepository, times(1)).save(existingCustomer);
        verify(customerMapper, times(1)).mapEntityToResponseDto(existingCustomer);
    }

    @Test
    public void testUpdateCustomer_UpdateEmailNotAllowed() {
        CustomerRequestDto requestDto = new CustomerRequestDto(FULL_NAME, EMAIL, PHONE_NUMBER);
        Customer existingCustomer = new Customer(ID, TIME, TIME, FULL_NAME, EMAIL, PHONE_NUMBER, true);
        existingCustomer.setEmail("dmytro111@gmail.com");

        when(customerRepository.findByIdAndActiveTrue(ID)).thenReturn(Optional.of(existingCustomer));

        assertThrows(IllegalArgumentException.class, () -> customerService.updateCustomer(ID, requestDto));
    }

    @Test
    void testDeleteCustomerCustomerNotFoundException() {
        when(customerRepository.findByIdAndActiveTrue(ID)).thenReturn(Optional.empty());

        assertThrows(CustomerNotFoundException.class, () -> customerService.deleteCustomer(ID));
        verify(customerRepository, times(1)).findByIdAndActiveTrue(ID);
        verifyNoMoreInteractions(customerRepository);
    }
}
