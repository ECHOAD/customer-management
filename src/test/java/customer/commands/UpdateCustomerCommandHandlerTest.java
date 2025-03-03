package customer.commands;

import customer.domain.Customer;
import customer.dto.CustomerResponseDTO;
import customer.exception.CustomerUpdateException;
import customer.factories.CustomerFactory;
import customer.repository.CustomerPanacheRepository;
import external.client.country.dto.CountryDemonymDto;
import external.client.country.dto.CountryDto;
import external.client.country.dto.CountryName;
import external.client.country.service.CountryService;
import external.client.country.service.CountryServiceImpl;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@QuarkusTest
public class UpdateCustomerCommandHandlerTest {

    @Inject
    CustomerPanacheRepository customerPanacheRepository;

    CountryService countryService;

    @Inject
    UpdateCustomerCommandHandler sut;

    @BeforeEach
    @Transactional
    void setup() {

        customerPanacheRepository.deleteAll();

        customerPanacheRepository.persist(CustomerFactory
                .newInstance("Adrian",
                        "Estevez",
                        "adrian@test.com",
                        "Street 123",
                        "1234567890",
                        "DO",
                        "Dominican"));

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
    public void testUpdateCustomerWithoutChangingCountry() {
        // Arrange
        Customer outDatedCustomer = customerPanacheRepository.findById(1L);

        UpdateCustomerRequestDTO requestDTO = new UpdateCustomerRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setEmail("test@test.com");
        requestDTO.setAddress("Street 345");
        requestDTO.setPhoneNumber("0000000001");
        requestDTO.setCountry("DO");
        UpdateCustomerCommand command = new UpdateCustomerCommand(1L, requestDTO);


        // Act
        CustomerResponseDTO result = sut.apply(command);

        // Assert
        assertNotEquals(outDatedCustomer.getEmail(), result.email(), "Email should be test@test.com");
        assertNotEquals(outDatedCustomer.getAddress(), result.address(), "Address should be Street 345");
        assertEquals(outDatedCustomer.getCountry(), result.country(), "Country should be DO");
        assertEquals(outDatedCustomer.getDemonym(), result.demonym(), "Country should be Dominican");
    }

    @Test
    public void testUpdateCustomerWhenCountryIsChanged() {
        // Arrange
        Customer outDatedCustomer = customerPanacheRepository.findById(1L);

        UpdateCustomerRequestDTO requestDTO = new UpdateCustomerRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setEmail("test@test.com");
        requestDTO.setAddress("Street 345");
        requestDTO.setPhoneNumber("0000000001");
        requestDTO.setCountry("US");
        UpdateCustomerCommand command = new UpdateCustomerCommand(1L, requestDTO);


        // Act
        CustomerResponseDTO result = sut.apply(command);

        // Assert
        assertNotEquals(outDatedCustomer.getEmail(), result.email(), "Email should be test@test.com");
        assertNotEquals(outDatedCustomer.getAddress(), result.address(), "Address should be Street 345");
        assertNotEquals(outDatedCustomer.getCountry(), result.country(), "Country should be US");
        assertNotEquals(outDatedCustomer.getDemonym(), result.demonym(), "Country should be American");
    }


    @Test
    public void testUpdateCustomerWithInvalidId() {
        // Arrange
        UpdateCustomerRequestDTO requestDTO = new UpdateCustomerRequestDTO();
        requestDTO.setId(2L);
        requestDTO.setEmail("test@test.com");
        requestDTO.setAddress("Street 345");
        requestDTO.setPhoneNumber("0000000001");
        requestDTO.setCountry("US");
        UpdateCustomerCommand command = new UpdateCustomerCommand(1L, requestDTO);

        // Act & Assert
        assertThrows(CustomerUpdateException.class, () -> sut.apply(command), "Expected CustomerUpdateException");
    }

    @Test
    public void testUpdateCustomerWithInvalidCountry() {
        // Arrange
        UpdateCustomerRequestDTO requestDTO = new UpdateCustomerRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setEmail("test@test.com");
        requestDTO.setAddress("Street 345");
        requestDTO.setPhoneNumber("0000000001");
        requestDTO.setCountry("XX");
        UpdateCustomerCommand command = new UpdateCustomerCommand(1L, requestDTO);

        // Act & Assert
        assertThrows(CustomerUpdateException.class, () -> sut.apply(command), "Expected CustomerUpdateException");
    }
}
