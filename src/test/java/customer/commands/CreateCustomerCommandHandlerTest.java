package customer.commands;

import application.commands.CreateCustomerCommand;
import application.commands.CreateCustomerCommandHandler;
import interfaces.dto.CustomerResponseDTO;
import domain.exceptions.CustomerCreationException;
import infrastructure.country.dto.CountryDemonymDto;
import infrastructure.country.dto.CountryDto;
import infrastructure.country.dto.CountryName;
import infrastructure.country.exceptions.CountryClientException;
import infrastructure.country.service.CountryService;
import infrastructure.country.service.CountryServiceImpl;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;


import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@QuarkusTest
public class CreateCustomerCommandHandlerTest {

    CountryService countryService;

    @Inject
    CreateCustomerCommandHandler sut;

    @BeforeEach
    void setup() {
        countryService = mock(CountryServiceImpl.class);

        // Simulated country data
        CountryDto doCountryDto = CountryDto.builder()
                .name(CountryName.builder().official("Dominican Republic").common("Dominican Republic").build())
                .demonyms(Map.of("en", new CountryDemonymDto("Dominican", "Dominican")))
                .build();

        CountryDto usCountryDto = CountryDto.builder()
                .name(CountryName.builder().official("United States").common("United States").build())
                .demonyms(Map.of("en", new CountryDemonymDto("American", "Americans")))
                .build();

        when(countryService.getCountryByCode("DO")).thenReturn(doCountryDto);
        when(countryService.getCountryByCode("US")).thenReturn(usCountryDto);
    }

    @Test
    @TestTransaction
    public void testCreateCustomerWithDominicanCountry() {
        // Arrange
        CreateCustomerCommand command = new CreateCustomerCommand();
        command.setFirstName("John");
        command.setMiddleName("Smith");
        command.setFirstSurname("Doe");
        command.setSecondSurname("Doe");
        command.setEmail("john@test.com");
        command.setAddress("Street 123");
        command.setPhoneNumber("1234567890");
        command.setCountry("DO");

        // Act
        CustomerResponseDTO result = sut.apply(command);

        // Assert
        assertNotNull(result, "The result should not be null");
        assertEquals("John", result.firstName(), "First name should be John");
        assertEquals("Smith", result.middleName(), "Middle name should be Smith");
        assertEquals("Doe", result.firstSurname(), "First last name should be Doe");
        assertEquals("Doe", result.secondSurname(), "Second last name should be Doe");
        assertEquals("john@test.com", result.email(), "Email should be john@test.com");
        assertEquals("Street 123", result.address(), "Address should be Street 123");
        assertEquals("1234567890", result.phoneNumber(), "Phone number should be 1234567890");
        assertEquals("DO", result.country(), "Country should be DO");
        assertEquals("Dominican", result.demonym(), "Country should be Dominican");
    }

    @Test
    @TestTransaction
    public void testCreateCustomerWhenCountryIsNotFound() {
        // Arrange
        CreateCustomerCommand command = new CreateCustomerCommand();
        command.setFirstName("Jane");
        command.setMiddleName("Doe");
        command.setFirstSurname("Smith");
        command.setSecondSurname("Smith");
        command.setEmail("jane@test.com");
        command.setAddress("Avenue 456");
        command.setPhoneNumber("0987654321");
        command.setCountry("XX");

        when(countryService.getCountryByCode("XX")).thenThrow(CountryClientException.class);
        // Act / Assert
        assertThrows(CustomerCreationException.class, () -> sut.apply(command), "Expected CustomerCreationException");

    }
}
