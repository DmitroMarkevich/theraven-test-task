package com.theraven.task.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDto {

    @Schema(description = "Customer's ID", example = "591431")
    private Long id;

    @Schema(description = "Customer's full name", example = "John Doe")
    private String fullName;

    @Schema(description = "Customer's email address", example = "john.doe@example.com")
    private String email;

    @Schema(description = "Customer's phone number", example = "+1234567890")
    private String phone;
}
