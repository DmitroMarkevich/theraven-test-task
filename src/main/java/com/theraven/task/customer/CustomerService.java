package com.theraven.task.customer;

import com.theraven.task.customer.dto.CustomerRequestDto;
import com.theraven.task.customer.dto.CustomerResponseDto;
import com.theraven.task.errorhandling.exception.CustomerExistsException;
import com.theraven.task.errorhandling.exception.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerMapper customerMapper;
    private final CustomerRepository customerRepository;

    /**
     * Creates a new customer
     *
     * @param customerRequestDto the dto object of the new customer
     * @return information about the created customer
     * @throws CustomerExistsException if a customer with this email already exists
     */
    public CustomerResponseDto createCustomer(CustomerRequestDto customerRequestDto) {
        Optional<Customer> existingCustomerOptional = customerRepository.findByEmail(customerRequestDto.getEmail());

        if (existingCustomerOptional.isPresent()) {
            Customer customer = existingCustomerOptional.get();
            if (!customer.isActive()) {
                return activateCustomer(customer, customerRequestDto);
            } else {
                log.warn("User with email {} already exists and is active", customerRequestDto.getEmail());
                throw new CustomerExistsException("User with email " + customerRequestDto.getEmail() + " already exists.");
            }
        }

        Customer customer = customerMapper.mapRequestDtoToEntity(customerRequestDto);
        return customerMapper.mapEntityToResponseDto(customerRepository.save(customer));
    }

    /**
     * Activates a previously deleted customer and updates their details.
     * This method activates a customer if they were previously inactive,
     * updates their details and saves the changes to the db
     *
     * @param customer           the customer entity to be activated and updated
     * @param customerRequestDto the updated data of the customer
     * @return the updated customer information
     */
    private CustomerResponseDto activateCustomer(Customer customer, CustomerRequestDto customerRequestDto) {
        customer.setActive(true);
        customer.setUpdatedAt(new Date().getTime());
        customer.setFullName(customerRequestDto.getFullName());
        customer.setPhone(customerRequestDto.getPhone());
        return customerMapper.mapEntityToResponseDto(customerRepository.save(customer));
    }

    /**
     * Retrieves a customer by their ID
     *
     * @param id the ID of the customer
     * @return the customer information if found
     * @throws CustomerNotFoundException if customer is not found with the given ID
     */
    public CustomerResponseDto getCustomerById(Long id) {
        return customerRepository.findByIdAndActiveTrue(id)
                .map(customerMapper::mapEntityToResponseDto)
                .orElseThrow(() -> {
                    log.warn("Customer not found with ID {}", id);
                    return new CustomerNotFoundException("Customer not found with ID: " + id);
                });
    }

    /**
     * Retrieves a page of customers with pagination support
     *
     * @param page the page number (default 0)
     * @param size the size of each page (default 10)
     * @return a page of customer information
     */
    public Page<CustomerResponseDto> getAllCustomers(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Customer> customerPage = customerRepository.findAllByActiveTrue(pageable);

        return customerPage.map(customerMapper::mapEntityToResponseDto);
    }

    /**
     * Updates the details of an existing customer
     *
     * @param id                 the ID of the customer to update
     * @param customerRequestDto the updated data of the customer
     * @return the updated customer information
     * @throws IllegalArgumentException  if the provided email doesn't match the customer's existing email
     * @throws CustomerNotFoundException if customer is not found with the given ID
     */
    public CustomerResponseDto updateCustomer(Long id, CustomerRequestDto customerRequestDto) {
        Optional<Customer> existingCustomerOptional = customerRepository.findByIdAndActiveTrue(id);
        Customer existingCustomer = existingCustomerOptional.orElseThrow(() -> {
            log.warn("Customer not found with ID {}", id);
            return new CustomerNotFoundException("Customer not found with ID: " + id);
        });

        if (!existingCustomer.getEmail().equals(customerRequestDto.getEmail())) {
            throw new IllegalArgumentException("Email cannot be updated.");
        }

        existingCustomer.setUpdatedAt(new Date().getTime());
        existingCustomer.setFullName(customerRequestDto.getFullName());
        existingCustomer.setPhone(customerRequestDto.getPhone());

        return customerMapper.mapEntityToResponseDto(customerRepository.save(existingCustomer));
    }

    /**
     * Deletes a customer by the ID
     *
     * @param id the ID of the customer to delete
     * @throws CustomerNotFoundException if customer is not found with the given ID
     */
    public void deleteCustomer(Long id) {
        Optional<Customer> existingCustomerOptional = customerRepository.findByIdAndActiveTrue(id);
        Customer existingCustomer = existingCustomerOptional.orElseThrow(() -> {
            log.warn("Customer not found with ID {}", id);
            return new CustomerNotFoundException("Customer not found with ID: " + id);
        });

        existingCustomer.setActive(false);
        customerRepository.save(existingCustomer);
        log.info("Customer with ID {} deleted", id);
    }
}
