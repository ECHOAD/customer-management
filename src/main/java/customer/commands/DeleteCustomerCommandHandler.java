package customer.commands;

import customer.domain.Customer;
import customer.dto.CustomerResponseDTO;
import customer.exception.CustomerUpdateException;
import customer.mappers.CustomerMapper;
import customer.repository.CustomerPanacheRepository;
import external.client.country.service.CountryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mediator.CommandHandler;

import java.util.Objects;

@ApplicationScoped
public class DeleteCustomerCommandHandler implements CommandHandler<DeleteCustomerCommand, Void> {
    private final CustomerPanacheRepository customerRepository;

    @Inject
    public DeleteCustomerCommandHandler(CustomerPanacheRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Override
    public Class<DeleteCustomerCommand> getType() {
        return DeleteCustomerCommand.class;
    }


    @Override
    @Transactional
    public Void apply(DeleteCustomerCommand updateCustomerCommand) {
        Customer customer = customerRepository.findById(updateCustomerCommand.getId());
        customerRepository.delete(customer);
        return null;
    }
}
