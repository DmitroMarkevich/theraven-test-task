package com.theraven.task.customer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    /**
     * Retrieves a customer by their email address
     *
     * @param email The email address of the customer to retrieve
     * @return Optional containing the customer if found, otherwise empty
     */
    Optional<Customer> findByEmail(String email);

    /**
     * x
     * Retrieves an active customer by their ID
     *
     * @param id The ID of the customer to retrieve
     * @return Optional containing the active customer if found, otherwise empty
     */
    Optional<Customer> findByIdAndActiveTrue(Long id);

    /**
     * Retrieves a page of active customers
     *
     * @param pageable The pagination information
     * @return Page containing active customers
     */
    Page<Customer> findAllByActiveTrue(Pageable pageable);
}
