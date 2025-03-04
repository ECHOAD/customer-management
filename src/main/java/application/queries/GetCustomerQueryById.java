package application.queries;

import interfaces.dto.CustomerResponseDTO;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.PathParam;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mediator.Query;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetCustomerQueryById implements Query<CustomerResponseDTO> {
    @PathParam("id")
    @NotNull
    private long id;

}
