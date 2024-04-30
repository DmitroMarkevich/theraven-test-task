package com.theraven.task.customer;

import com.theraven.task.customer.dto.CustomerRequestDto;
import com.theraven.task.customer.dto.CustomerResponseDto;
import com.theraven.task.errorhandling.ErrorResponse;
import com.theraven.task.errorhandling.ErrorUtils;
import com.theraven.task.errorhandling.exception.CustomerValidationException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

/**
 * Controller class for handling customer-related operations.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer created successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CustomerResponseDto.class))
            }),
            @ApiResponse(responseCode = "401", description = "Validation failed",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            )
    })
    @Operation(summary = "Create customer", description = "Creates a customer in the application")
    public ResponseEntity<CustomerResponseDto> createCustomer(@Valid @RequestBody CustomerRequestDto requestDto,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomerValidationException(ErrorUtils.handleValidationErrors(bindingResult).toString());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(customerService.createCustomer(requestDto));
    }

    @GetMapping
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of customers retrieved", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CustomerResponseDto.class))
            })
    })
    @Operation(summary = "Get all customers", description = "Retrieves a list of all customers from the application")
    public Page<CustomerResponseDto> getAllCustomers(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size) {
        return customerService.getAllCustomers(page, size);
    }

    @PutMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer updated successfully", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CustomerResponseDto.class))
            }),
            @ApiResponse(responseCode = "400", description = "Validation failed",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            ),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            )
    })
    @Operation(summary = "Update customer", description = "Updates a customer in the application")
    public ResponseEntity<CustomerResponseDto> updateCustomer(@PathVariable Long id,
                                                              @Valid @RequestBody CustomerRequestDto requestDto,
                                                              BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new CustomerValidationException(ErrorUtils.handleValidationErrors(bindingResult).toString());
        }

        return ResponseEntity.ok(customerService.updateCustomer(id, requestDto));
    }

    @GetMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Customer found", content = {
                    @Content(mediaType = "application/json", schema =
                    @Schema(implementation = CustomerResponseDto.class))
            }),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            )
    })
    @Operation(summary = "Get customer by ID", description = "Retrieves a customer from the application by ID")
    public ResponseEntity<CustomerResponseDto> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok(customerService.getCustomerById(id));
    }

    @DeleteMapping("/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Customer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Customer not found",
                    content = @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResponse.class))
            )
    })
    @Operation(summary = "Delete customer", description = "Deletes a customer from the application")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
