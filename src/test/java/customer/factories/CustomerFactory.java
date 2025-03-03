package customer.factories;

import customer.domain.Customer;

/**
 * A factory class for creating new instances of the Customer class.
 */
public final class CustomerFactory {

    /**
     * Creates a new instance of Customer with the provided details.
     *
     * @param firstName     the first name of the customer
     * @param firstLastName the first last name of the customer
     * @param email         the email address of the customer
     * @param address       the address of the customer
     * @param phoneNumber   the phone number of the customer
     * @param country       the country code of the customer
     * @param demonym       the demonym of the customer
     * @return a new Customer instance
     */
    public static Customer newInstance(String firstName,
                                       String firstLastName,
                                       String email,
                                       String address,
                                       String phoneNumber,
                                       String country,
                                       String demonym) {
        return Customer.builder()
                .firstName(firstName)
                .firstLastName(firstLastName)
                .email(email)
                .address(address)
                .phoneNumber(phoneNumber)
                .country(country)
                .demonym(demonym)
                .build();
    }
}
