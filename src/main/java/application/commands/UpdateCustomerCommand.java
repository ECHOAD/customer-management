package application.commands;

import interfaces.dto.CustomerResponseDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import mediator.Command;

@Data
@AllArgsConstructor
public class UpdateCustomerCommand implements Command<CustomerResponseDTO> {
    @NotBlank
    private long id;

    private UpdateCustomerRequestDTO updateCustomerRequestDTO;
}