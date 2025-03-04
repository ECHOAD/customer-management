package application.commands;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "UpdateCustomerBody", description = "update customer body")
public class UpdateCustomerRequestDTO {
    @NotNull
    @Schema(description = "Unique identifier of the customer", examples = "1")
    private long id;
    @Email
    @NotBlank
    @Schema(description = "Customer email", examples = "john.doe@example.com")
    private String email;
    @NotBlank
    @Schema(description = "Customer address", examples = "123 Street, City, State, Zip")
    private String address;
    @NotBlank
    @Pattern(regexp = "^[0-9 ]*$", message = "Phone number must be only numbers")
    @Schema(description = "Customer phone number", examples = "123456789")
    private String phoneNumber;
    @Pattern(regexp = "[A-Z]{2,}$", message = "Country code must have more than 2 characters")
    @NotBlank
    @Schema(description = "Customer country", examples = "DO")
    private String country;
}
