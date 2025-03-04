package application.commands;

import domain.Customer;
import interfaces.dto.CustomerResponseDTO;
import domain.exceptions.CustomerUpdateException;
import interfaces.mappers.CustomerMapper;
import infrastructure.repository.CustomerPanacheRepository;
import infrastructure.country.exceptions.CountryClientException;
import infrastructure.country.service.CountryService;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import mediator.CommandHandler;
import org.jboss.logging.Logger;

import java.util.Objects;

@ApplicationScoped
public class UpdateCustomerCommandHandler implements CommandHandler<UpdateCustomerCommand, CustomerResponseDTO> {
    private final static Logger logger = Logger.getLogger(UpdateCustomerCommandHandler.class);
    private final static String ERROR_LOGGER_MESSAGE = "(UpdateCustomerCommandHandler - %s) %s";


    private final CustomerPanacheRepository customerRepository;
    private final CountryService countryService;

    @Inject
    public UpdateCustomerCommandHandler(CustomerPanacheRepository customerRepository
            , CountryService countryService) {
        this.customerRepository = customerRepository;
        this.countryService = countryService;
    }

    @Override
    public Class<UpdateCustomerCommand> getType() {
        return UpdateCustomerCommand.class;
    }


    @Override
    @Transactional
    public CustomerResponseDTO apply(UpdateCustomerCommand updateCustomerCommand) {
        if (updateCustomerCommand.getId() != updateCustomerCommand.getUpdateCustomerRequestDTO().getId()) {
            logger.error(String.format(ERROR_LOGGER_MESSAGE, "apply", "Customer id doesn't match"));
            throw new CustomerUpdateException("Customer id doesn't match");
        }

        if(customerRepository.findByIdOptional(updateCustomerCommand.getId()).isEmpty()) {
            logger.error(String.format(ERROR_LOGGER_MESSAGE, "apply", "Customer not found"));
            throw new CustomerUpdateException("Customer not found");
        }

        Customer customer = customerRepository.findById(updateCustomerCommand.getId());
        boolean shouldUpdateCountry = !Objects.equals(updateCustomerCommand.getUpdateCustomerRequestDTO().getCountry(), customer.getCountry());

        customer.setEmail(updateCustomerCommand.getUpdateCustomerRequestDTO().getEmail());
        customer.setAddress(updateCustomerCommand.getUpdateCustomerRequestDTO().getAddress());
        customer.setPhoneNumber(updateCustomerCommand.getUpdateCustomerRequestDTO().getPhoneNumber());
        customer.setCountry(updateCustomerCommand.getUpdateCustomerRequestDTO().getCountry());

        if (shouldUpdateCountry) {
            updateCustomerDemonym(updateCustomerCommand, customer);
        }

        customerRepository.persistAndFlush(customer);

        return CustomerMapper.toDTO(customer);
    }

    private void updateCustomerDemonym(UpdateCustomerCommand updateCustomerCommand, Customer customer) {
        try {
            customer.setDemonym(countryService.findDemonymByCountryCode(updateCustomerCommand.getUpdateCustomerRequestDTO().getCountry()));
        } catch (CountryClientException e) {
            logger.error(String.format(ERROR_LOGGER_MESSAGE, "updateCustomerDemonym", "CountryClientException"));
            throw new CustomerUpdateException(e.getMessage());
        }
    }
}
