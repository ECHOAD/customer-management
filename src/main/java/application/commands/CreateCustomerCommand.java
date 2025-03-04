package application.commands;

import interfaces.dto.CustomerResponseDTO;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mediator.Command;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerCommand implements Command<CustomerResponseDTO> {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "First name must be only characters")
    String firstName;
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Middle name name must be only characters")
    String middleName;
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "First last name must be only characters")
    String firstLastName;
    @Pattern(regexp = "^[a-zA-Z ]*$", message = "Second last name name must be only characters")
    String secondLastName;
    @Email
    @NotBlank
    String email;
    @NotBlank
    String address;
    @NotBlank
    @Pattern(regexp = "^[0-9 ]*$", message = "Phone number must be only numbers")
    String phoneNumber;
    @Pattern(regexp = "[A-Z]{2,}$", message = "Country code must have more than 2 characters")
    @NotBlank
    String country;
}