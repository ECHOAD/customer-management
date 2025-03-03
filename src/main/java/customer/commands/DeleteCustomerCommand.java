package customer.commands;


import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PathParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import mediator.Command;


@Data
@AllArgsConstructor
public class DeleteCustomerCommand implements Command<Void> {
    @NotNull
    @PathParam("id")
    private long id;
}