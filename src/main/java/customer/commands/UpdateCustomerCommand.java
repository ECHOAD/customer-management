package customer.commands;

import customer.dto.CustomerResponseDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.PathParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import mediator.Command;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Data
@AllArgsConstructor
public class UpdateCustomerCommand implements Command<CustomerResponseDTO> {
    @NotBlank
    private long id;

    private UpdateCustomerRequestDTO updateCustomerRequestDTO;
}