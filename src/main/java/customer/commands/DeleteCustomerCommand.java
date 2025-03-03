package customer.commands;


import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PathParam;
import lombok.Data;
import mediator.Command;


@Data
public class DeleteCustomerCommand implements Command<Void> {
    @NotNull
    @PathParam("id")
    private long id;
}