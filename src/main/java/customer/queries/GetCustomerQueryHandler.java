package customer.queries;

import customer.dto.CustomerResponseDTO;
import customer.repository.CustomerPanacheRepository;
import jakarta.inject.Inject;
import mediator.QueryHandler;

import java.util.List;


public class GetCustomerQueryHandler implements QueryHandler<GetCustomersQuery, List<CustomerResponseDTO>> {
    private final CustomerPanacheRepository customerPanacheRepository;

    @Inject
    public GetCustomerQueryHandler(CustomerPanacheRepository customerPanacheRepository) {
        this.customerPanacheRepository = customerPanacheRepository;
    }

    @Override
    public Class<GetCustomersQuery> getType() {
        return GetCustomersQuery.class;
    }


    @Override
    public List<CustomerResponseDTO> apply(GetCustomersQuery getCustomersQuery) {
        return List.of();
    }
}
