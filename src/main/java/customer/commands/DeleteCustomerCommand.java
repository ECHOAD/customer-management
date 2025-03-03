package customer.commands;

import customer.dto.CustomerResponseDTO;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.ws.rs.PathParam;
import lombok.Data;
import mediator.Command;


@Data
public class DeleteCustomerCommand implements Command<Void> {
    @NotNull
    @PathParam("id")
    private long id;
}