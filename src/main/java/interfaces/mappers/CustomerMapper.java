package interfaces.mappers;

import application.commands.CreateCustomerCommand;
import domain.Customer;
import interfaces.dto.CustomerResponseDTO;

public final class CustomerMapper {
    private CustomerMapper() {}

    public static CustomerResponseDTO toDTO(final Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getFirstName(),
                customer.getMiddleName(),
                customer.getFirstLastName(),
                customer.getSecondLastName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getCountry(),
                customer.getDemonym()
        );
    }

    public static Customer toEntity(final CreateCustomerCommand createCustomerCommand) {
        return new Customer(null, createCustomerCommand.getFirstName(), createCustomerCommand.getMiddleName(),
                createCustomerCommand.getFirstLastName(), createCustomerCommand.getSecondLastName(), createCustomerCommand.getEmail(),
                 createCustomerCommand.getPhoneNumber(), createCustomerCommand.getAddress(), createCustomerCommand.getCountry(), null);
    }
}
