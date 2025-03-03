package customer.commands;

import customer.domain.Customer;
import customer.dto.CustomerResponseDTO;
import customer.exception.CustomerCreationException;
import customer.mappers.CustomerMapper;
import customer.repository.CustomerPanacheRepository;
import external.client.country.exceptions.CountryClientException;
import external.client.country.service.CountryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mediator.CommandHandler;
import org.jboss.logging.Logger;

/**
 * Command handler for create customer
 *
 * @see CreateCustomerCommand
 * @see CustomerResponseDTO
 * @see CommandHandler
 */
@ApplicationScoped
public class CreateCustomerCommandHandler implements CommandHandler<CreateCustomerCommand, CustomerResponseDTO> {

    private final static Logger logger = Logger.getLogger(CreateCustomerCommandHandler.class);

    private final CustomerPanacheRepository customerRepository;
    private final CountryService countryService;

    @Inject
    public CreateCustomerCommandHandler(CustomerPanacheRepository customerRepository
            , CountryService countryService) {
        this.customerRepository = customerRepository;
        this.countryService = countryService;
    }

    @Override
    public Class<CreateCustomerCommand> getType() {
        return CreateCustomerCommand.class;
    }


    /**
     * Creates a new customer
     *
     * @param createCustomerCommand the command to create customer
     * @return the created customer
     * @throws CustomerCreationException if the customer could not be created
     */
    @Override
    @Transactional
    public CustomerResponseDTO apply(CreateCustomerCommand createCustomerCommand) {
        try {
            Customer customer = CustomerMapper.toEntity(createCustomerCommand);
            customer.setDemonym(countryService.findDemonymByCountryCode(createCustomerCommand.getCountry()));
            customerRepository.persist(customer);
            return CustomerMapper.toDTO(customer);

        } catch (CountryClientException e) {
            logger.error("(CreateCustomerCommandHandler - apply) response: %s", e.getMessage(), e);
            throw new CustomerCreationException(e.getMessage());
        }
    }

}
