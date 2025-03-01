package customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record CreateCustomerRequestDTO(
        @NotBlank String firstName,
        String middleName,
        @NotBlank String firstLastName,
        String secondLastName,
        @Email @NotBlank String email,
        @NotBlank String address,
        @NotBlank String phoneNumber,
        @Pattern(regexp = "[A-Z]{2}$", message = "Country code must be 2 characters") @NotBlank String country
) {
}
