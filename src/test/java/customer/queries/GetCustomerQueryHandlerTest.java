package customer.queries;

import application.queries.GetCustomerQueryHandler;
import application.queries.GetCustomersQuery;
import customer.factories.CustomerFactory;
import interfaces.dto.CustomerResponseDTO;
import interfaces.dto.PaginatedResponse;
import infrastructure.repository.CustomerPanacheRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@QuarkusTest
public class GetCustomerQueryHandlerTest {

    @Inject
    CustomerPanacheRepository customerPanacheRepository;

    GetCustomerQueryHandler sut;


    @BeforeEach
    @Transactional
    void setup() {
        customerPanacheRepository.deleteAll();

        customerPanacheRepository.persistAndFlush(CustomerFactory
                .newInstance(null,"Adrian",
                        "Estevez",
                        "adrian@test.com",
                        "Street 123",
                        "1234567890",
                        "DO",
                        "Dominican"));

        customerPanacheRepository.persistAndFlush(CustomerFactory
                .newInstance(null,"John",
                        "Doe",
                        "john@test.com",
                        "Street 123",
                        "0928372810",
                        "US",
                        "American"));

        customerPanacheRepository.persistAndFlush(CustomerFactory
                .newInstance(null,"Jane",
                        "Smith",
                        "jane@test.com",
                        "Street 123",
                        "3029302910",
                        "US",
                        "American"));

        sut = new GetCustomerQueryHandler(customerPanacheRepository);
    }


    @Test
    public void testGetCustomersWithDefaultFilter() {
        // Arrange
        GetCustomersQuery query = new GetCustomersQuery();
        query.setPage(1);
        query.setLimit(10);
        query.setCountry(null);

        // Act
        PaginatedResponse<CustomerResponseDTO> result = sut.apply(query);

        // Assert
        assertNotNull(result, "result should not be null");
        assertNotNull(result.getData(), "data should not be null");
        assertEquals(3, result.getTotalItems(), "totalItems should be 3");
        assertEquals(1, result.getCurrentPage(), "currentPage should be 1");
    }

    @Test
    public void testGetCustomersWithCountryFilter() {
        // Arrange
        GetCustomersQuery query = new GetCustomersQuery();
        query.setPage(1);
        query.setLimit(10);
        query.setCountry("DO");

        // Act
        PaginatedResponse<CustomerResponseDTO> result = sut.apply(query);

        // Assert
        assertNotNull(result, "result should not be null");
        assertNotNull(result.getData(), "data should not be null");
        assertEquals(1, result.getTotalItems(), "totalItems should be 1");
        assertEquals(1, result.getCurrentPage(), "currentPage should be 1");
    }


    @Test
    public void testGetCustomersWithNonExistingCountryFilter() {
        // Arrange
        GetCustomersQuery query = new GetCustomersQuery();
        query.setPage(1);
        query.setLimit(10);
        query.setCountry("XX");

        // Act
        PaginatedResponse<CustomerResponseDTO> result = sut.apply(query);

        // Assert
        assertNotNull(result, "result should not be null");
        assertNotNull(result.getData(), "data should not be null");
        assertEquals(0, result.getTotalItems(), "totalItems should be 0");
        assertEquals(1, result.getCurrentPage(), "currentPage should be 1");
    }

    @Test
    public void testGetCustomerWithPageOutOfRange() {
        // Arrange
        GetCustomersQuery query = new GetCustomersQuery();
        query.setPage(2);
        query.setLimit(10);
        query.setCountry(null);

        // Act
        PaginatedResponse<CustomerResponseDTO> result = sut.apply(query);

        // Assert
        assertNotNull(result, "result should not be null");
        assertNotNull(result.getData(), "data should not be null");
        assertEquals(0, result.getTotalItems(), "totalItems should be 0");
        assertEquals(2, result.getCurrentPage(), "currentPage should be 2");
    }


}
