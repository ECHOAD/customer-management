package customer.commands;


import customer.dto.DeletedCustomerDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PathParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import mediator.Command;


@Data
@AllArgsConstructor
public class DeleteCustomerCommand implements Command<DeletedCustomerDTO> {
    @NotNull
    @PathParam("id")
    private long id;
}