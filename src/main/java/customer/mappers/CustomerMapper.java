package customer.mappers;

import customer.domain.Customer;
import customer.dto.CustomerResponseDTO;

public final class CustomerMapper {
    private CustomerMapper() {}

    public static CustomerResponseDTO toDTO(final Customer customer) {
        return new CustomerResponseDTO(
                customer.getId(),
                customer.getFirstLastName(),
                customer.getSecondLastName(),
                customer.getFirstName(),
                customer.getMiddleName(),
                customer.getEmail(),
                customer.getAddress(),
                customer.getPhoneNumber(),
                customer.getDemonym(),
                customer.getCountry()
        );
    }
}
