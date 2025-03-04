package customer.factories;

import domain.Customer;

/**
 * A factory class for creating new instances of the Customer class.
 */
public final class CustomerFactory {

    /**
     * Creates a new instance of Customer with the provided details.
     *
     * @param firstName     the first name of the customer
     * @param middleName    the middle name of the customer
     * @param firstSurname  the first last name of the customer
     * @param secondSurname the second last name of the customer
     * @param email         the email address of the customer
     * @param address       the address of the customer
     * @param phoneNumber   the phone number of the customer
     * @param country       the country code of the customer
     * @param demonym       the demonym of the customer
     * @return a new Customer instance
     */
    public static Customer newInstance(
            Long id,
            String firstName,
            String middleName,
            String firstSurname,
            String secondSurname,
            String email,
            String address,
            String phoneNumber,
            String country,
            String demonym) {
        return Customer.builder()
                .id(id)
                .firstName(firstName)
                .middleName(middleName)
                .firstSurname(firstSurname)
                .secondSurname(secondSurname)
                .email(email)
                .address(address)
                .phoneNumber(phoneNumber)
                .country(country)
                .demonym(demonym)
                .build();
    }
}
