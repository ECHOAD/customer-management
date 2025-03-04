package interfaces.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdateCustomerRequestDTO(
        @NotBlank(message = "Id must be provided" ) String id,
        @NotBlank String address,
        @NotBlank String phoneNumber,
        @Pattern(regexp = "[A-Z]{2}$", message = "Country code must be 2 characters") @NotBlank String country
) {
}
