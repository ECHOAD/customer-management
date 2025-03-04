package customer.commands;

import application.commands.UpdateCustomerCommand;
import application.commands.UpdateCustomerCommandHandler;
import application.commands.UpdateCustomerRequestDTO;
import domain.Customer;
import infrastructure.country.exceptions.CountryClientException;
import interfaces.dto.CustomerResponseDTO;
import domain.exceptions.CustomerUpdateException;
import customer.factories.CustomerFactory;
import infrastructure.repository.CustomerPanacheRepository;
import infrastructure.country.service.CountryService;
import infrastructure.country.service.CountryServiceImpl;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


@QuarkusTest
public class UpdateCustomerCommandHandlerTest {

    @Inject
    CustomerPanacheRepository customerPanacheRepository;

    CountryService countryService;

    UpdateCustomerCommandHandler sut;

    @BeforeEach
    void setup() {
        countryService = mock(CountryServiceImpl.class);


        when(countryService.findDemonymByCountryCode("US")).thenReturn("American");
        when(countryService.findDemonymByCountryCode("DO")).thenReturn("Dominican");

        sut = new UpdateCustomerCommandHandler(customerPanacheRepository,countryService);
    }

    private void loadData() {
        customerPanacheRepository.deleteAll();
        customerPanacheRepository.persistAndFlush(CustomerFactory
                .newInstance(null,"Adrian",
                        "",
                        "Estevez",
                        "",
                        "adrian@test.com",
                        "Street 123",
                        "1234567890",
                        "DO",
                        "Dominican"));
    }

    @Test
    @TestTransaction
    public void testUpdateCustomerWithoutChangingCountry() {
        // Arrange
        loadData();
        Customer outDatedCustomer = customerPanacheRepository.findAll().firstResult();
        customerPanacheRepository.getEntityManager().detach(outDatedCustomer);

        UpdateCustomerRequestDTO requestDTO = new UpdateCustomerRequestDTO();
        requestDTO.setId(outDatedCustomer.getId());
        requestDTO.setEmail("test@test.com");
        requestDTO.setAddress("Street 345");
        requestDTO.setPhoneNumber("0000000001");
        requestDTO.setCountry("DO");
        UpdateCustomerCommand command = new UpdateCustomerCommand(outDatedCustomer.getId(), requestDTO);

        // Act
        CustomerResponseDTO result = sut.apply(command);

        // Assert
        assertNotEquals(outDatedCustomer.getEmail(), result.email(), "Email should be test@test.com");
        assertNotEquals(outDatedCustomer.getAddress(), result.address(), "Address should be Street 345");
        assertEquals(outDatedCustomer.getCountry(), result.country(), "Country should be DO");
        assertEquals(outDatedCustomer.getDemonym(), result.demonym(), "Country should be Dominican");
    }

    @Test
    @TestTransaction
    public void testUpdateCustomerWhenCountryIsChanged() {
        // Arrange
        loadData();
        Customer outDatedCustomer = customerPanacheRepository.findAll().firstResult();
        customerPanacheRepository.getEntityManager().detach(outDatedCustomer);

        UpdateCustomerRequestDTO requestDTO = new UpdateCustomerRequestDTO();
        requestDTO.setId(outDatedCustomer.getId());
        requestDTO.setEmail("test@test.com");
        requestDTO.setAddress("Street 345");
        requestDTO.setPhoneNumber("0000000001");
        requestDTO.setCountry("US");
        UpdateCustomerCommand command = new UpdateCustomerCommand(outDatedCustomer.getId(), requestDTO);


        // Act
        CustomerResponseDTO result = sut.apply(command);

        // Assert
        assertNotEquals(outDatedCustomer.getEmail(), result.email(), "Email should be test@test.com");
        assertNotEquals(outDatedCustomer.getAddress(), result.address(), "Address should be Street 345");
        assertNotEquals(outDatedCustomer.getCountry(), result.country(), "Country should be US");
        assertNotEquals(outDatedCustomer.getDemonym(), result.demonym(), "Country should be American");
    }


    @Test
    @TestTransaction
    public void testUpdateCustomerWithInvalidId() {
        // Arrange
        loadData();
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
    @TestTransaction
    public void testUpdateCustomerWithInvalidCountry() {
        // Arrange
        loadData();
        UpdateCustomerRequestDTO requestDTO = new UpdateCustomerRequestDTO();
        requestDTO.setId(1L);
        requestDTO.setEmail("test@test.com");
        requestDTO.setAddress("Street 345");
        requestDTO.setPhoneNumber("0000000001");
        requestDTO.setCountry("XX");
        UpdateCustomerCommand command = new UpdateCustomerCommand(1L, requestDTO);


        when(countryService.getCountryByCode(eq("XX"))).thenThrow(CountryClientException.class);

        // Act & Assert
        assertThrows(CustomerUpdateException.class, () -> sut.apply(command), "Expected CustomerUpdateException");
    }
}
