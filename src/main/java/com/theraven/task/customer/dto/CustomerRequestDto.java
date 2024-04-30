package com.theraven.task.customer.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {

    @NotBlank(message = "Full name cannot be blank")
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    @Schema(description = "Customer's full name", example = "John Doe")
    private String fullName;

    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Invalid email format")
    @Size(min = 2, max = 100, message = "Email length must be between 2 and 100 characters")
    @Schema(description = "Customer's email address", example = "john.doe@example.com")
    private String email;

    @Pattern(regexp = "\\+[0-9]{6,14}", message = "Phone must start with '+' and contain only digits")
    @Size(min = 6, max = 14, message = "Phone must be between 6 and 14 characters")
    @Schema(description = "Customer's phone number", example = "+1234567890")
    private String phone;
}
