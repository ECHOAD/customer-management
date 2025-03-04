package customer.queries;

import customer.domain.Customer;
import customer.dto.CustomerResponseDTO;
import customer.exception.CustomerNotFoundException;
import customer.mappers.CustomerMapper;
import customer.repository.CustomerPanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import mediator.QueryHandler;

import java.util.Optional;

@ApplicationScoped
public class GetCustomerByIdQueryHandler implements QueryHandler<GetCustomerQueryById, CustomerResponseDTO> {
    private final CustomerPanacheRepository customerRepository;

    @Inject
    public GetCustomerByIdQueryHandler(CustomerPanacheRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Class<GetCustomerQueryById> getType() {
        return GetCustomerQueryById.class;
    }


    @Override
    public CustomerResponseDTO apply(GetCustomerQueryById getCustomersQuery) {
        Optional<Customer> customer = customerRepository.findByIdOptional(getCustomersQuery.getId());

        if (customer.isEmpty()) {
            throw new CustomerNotFoundException(String.format("Customer with id %s not found", getCustomersQuery.getId()));
        }

        return CustomerMapper.toDTO(customer.get());
    }

}
