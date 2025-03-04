package customer.commands;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateCustomerRequestDTO {
    @NotNull
    private long id;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String address;
    @NotBlank
    @Pattern(regexp = "^[0-9 ]*$", message = "Phone number must be only numbers")
    private String phoneNumber;
    @Pattern(regexp = "[A-Z]{2,}$", message = "Country code must have more than 2 characters")
    @NotBlank
    private String country;
}
