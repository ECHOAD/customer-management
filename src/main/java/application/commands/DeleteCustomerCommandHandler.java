package application.commands;

import domain.Customer;
import interfaces.dto.DeletedCustomerDTO;
import domain.exceptions.CustomerDeleteException;
import infrastructure.repository.CustomerPanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mediator.CommandHandler;
import org.jboss.logging.Logger;

import java.util.Optional;


@ApplicationScoped
public class DeleteCustomerCommandHandler implements CommandHandler<DeleteCustomerCommand, DeletedCustomerDTO> {
    private final static Logger logger = Logger.getLogger(DeleteCustomerCommandHandler.class);

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
    public DeletedCustomerDTO apply(DeleteCustomerCommand deleteCustomerCommand) {
        Optional<Customer> optionalCustomer = customerRepository.findByIdOptional(deleteCustomerCommand.getId());

        if (optionalCustomer.isEmpty()) {
            logger.error("(DeleteCustomerCommandHandler - apply) Customer not found");
            throw new CustomerDeleteException("Customer not found");
        }
        customerRepository.delete(optionalCustomer.get());
        customerRepository.flush();
        return new DeletedCustomerDTO(deleteCustomerCommand.getId(), "Customer deleted successfully");
    }
}
