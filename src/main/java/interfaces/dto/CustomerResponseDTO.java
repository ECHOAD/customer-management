package interfaces.dto;

import domain.Customer;

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
public record CustomerResponseDTO(
        Long id,
        String firstName,
        String middleName,
        String firstLastName,
        String secondLastName,
        String email,
        String address,
        String phoneNumber,
        String country,
        String demonym
) {
}
