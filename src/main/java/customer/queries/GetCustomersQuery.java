package customer.queries;

import customer.dto.CustomerResponseDTO;
import lombok.Builder;
import lombok.Data;
import mediator.Query;

import java.util.List;

@Data
@Builder
public class GetCustomersQuery implements Query<List<CustomerResponseDTO>> {
    private int page;
    private int limit;
    private String countryCode;
}