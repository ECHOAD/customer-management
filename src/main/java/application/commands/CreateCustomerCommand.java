package application.commands;

import interfaces.dto.CustomerResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mediator.Command;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "CreateCustomerBody", description = "Create customer body")
public class CreateCustomerCommand implements Command<CustomerResponseDTO> {

    @Schema(description = "Customer first name", examples = "John")
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "First name must be only characters")
    String firstName;

    @Schema(description = "Customer middle name", examples = "Doe")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Middle name must be only characters")
    String middleName;

    @Schema(description = "Customer first last name", examples = "Doe")
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "First last name must be only characters")
    String firstSurname;

    @Schema(description = "Customer second last name", examples = "Doe")
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Second last name must be only characters")
    String secondSurname;

    @Schema(description = "Customer email", examples = "john.doe@example.com")
    @Email
    @NotBlank
    String email;
    @Schema(description = "Customer address", examples = "123 Street, City, State, Zip")
    @NotBlank
    String address;
    @Schema(description = "Customer phone number", examples = "123456789")
    @NotBlank
    @Pattern(regexp = "^[0-9 ]*$", message = "Phone number must be only numbers")
    String phoneNumber;
    @Schema(description = "Customer country", examples = "DO")
    @Pattern(regexp = "[A-Z]{2,}$", message = "Country code must have more than 2 characters")
    @NotBlank
    String country;
}