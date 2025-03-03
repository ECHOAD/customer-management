package customer.queries;

import customer.dto.CustomerResponseDTO;
import customer.dto.PaginatedResponse;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.QueryParam;
import lombok.Data;
import mediator.Query;


@Data
public class GetCustomersQuery implements Query<PaginatedResponse<CustomerResponseDTO>> {
    @QueryParam("page")
    @Min(1)
    @DefaultValue("1")
    private int page;

    @QueryParam("limit")
    @Min(1)
    @DefaultValue("10")
    private int limit;

    @QueryParam("country")
    @Size(min = 2, max = 3)
    private String country;
}