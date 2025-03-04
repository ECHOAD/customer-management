package interfaces.dto;

import domain.Customer;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

/**
 * This class represents a customer
 * @see  Customer
 * @param id customer id
 * @param firstName customer first name
 * @param middleName customer middle name
 * @param firstLastName customer first last name
 * @param secondLastName customer second last name
 * @param email customer email
 * @param address customer address
 * @param phoneNumber customer phone number
 * @param country customer country
 * @param demonym customer demonym
 */

@Schema(name = "CustomerResponse", description = "Customer response details")
public record CustomerResponseDTO(
        @Schema(description = "Unique identifier of the customer", examples = "1")
        Long id,

        @Schema(description = "Customer first name", examples = "John")
        String firstName,
        @Schema(description = "Customer middle name", examples = "Doe")
        String middleName,
        @Schema(description = "Customer first last name", examples = "Doe")
        String firstLastName,
        @Schema(description = "Customer second last name", examples = "Doe")
        String secondLastName,
        @Schema(description = "Customer email", examples = "john.doe@example.com")
        String email,
        @Schema(description = "Customer address", examples = "123 Street, City, State, Zip")
        String address,
        @Schema(description = "Customer phone number", examples = "123456789")
        String phoneNumber,
        @Schema(description = "Customer country", examples = "USA")
        String country,
        @Schema(description = "Customer demonym", examples = "American")
        String demonym
) {
}