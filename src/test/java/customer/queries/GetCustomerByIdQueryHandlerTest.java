package customer.queries;

import application.queries.GetCustomerByIdQueryHandler;
import application.queries.GetCustomerQueryById;
import interfaces.dto.CustomerResponseDTO;
import domain.exceptions.CustomerNotFoundException;
import customer.factories.CustomerFactory;
import infrastructure.repository.CustomerPanacheRepository;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@QuarkusTest
public class GetCustomerByIdQueryHandlerTest {

    @Inject
    CustomerPanacheRepository customerPanacheRepository;

    GetCustomerByIdQueryHandler sut;


    @BeforeEach
    @Transactional
    void setup() {
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

        customerPanacheRepository.persistAndFlush(CustomerFactory
                .newInstance(null,"John",
                        "",

                        "Doe",
                        "",

                        "john@test.com",
                        "Street 123",
                        "0928372810",
                        "US",
                        "American"));

        customerPanacheRepository.persistAndFlush(CustomerFactory
                .newInstance(null,"Jane",
                        "",
                        "Smith",
                        "",
                        "jane@test.com",
                        "Street 123",
                        "3029302910",
                        "US",
                        "American"));

        sut = new GetCustomerByIdQueryHandler(customerPanacheRepository);
    }


    @Test
    public void testGetCustomerById() {


        // Arrange
        Long firstId = customerPanacheRepository.listAll().get(0).getId();
        GetCustomerQueryById query = new GetCustomerQueryById();
        query.setId(firstId);

        // Act
       CustomerResponseDTO result = sut.apply(query);

        // Assert
        assertNotNull(result, "result should not be null");
        assertEquals(result.id(), firstId, "Id should be %s".formatted(firstId));
    }


    @Test
    public void testGetCustomersWhenCustomerDoesNotExist() {

        // Arrange
        GetCustomerQueryById query = new GetCustomerQueryById();
        query.setId(1L);

        // Act & Assert
        assertThrows(CustomerNotFoundException.class, () -> sut.apply(query), "Expected CustomerNotFoundException");
    }


}
