package customer.commands;


import application.commands.DeleteCustomerCommand;
import application.commands.DeleteCustomerCommandHandler;
import domain.exceptions.CustomerDeleteException;
import customer.factories.CustomerFactory;
import infrastructure.repository.CustomerPanacheRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@QuarkusTest
public class DeleteCustomerCommandHandlerTest {

    @Inject
    CustomerPanacheRepository customerPanacheRepository;

    DeleteCustomerCommandHandler sut;

    @BeforeEach
    @Transactional
    void setup() {
        customerPanacheRepository.deleteAll();

        customerPanacheRepository.persistAndFlush(CustomerFactory
                .newInstance(null, "Adrian",
                        "Estevez",
                        "adrian@test.com",
                        "Street 123",
                        "1234567890",
                        "DO",
                        "Dominican"));

        sut = new DeleteCustomerCommandHandler(customerPanacheRepository);
    }

    @Test
    @TestTransaction
    public void testDeleteCustomer() {
        long lastId = customerPanacheRepository.findAll().firstResult().getId();
        // Arrange / Act
        DeleteCustomerCommand command = new DeleteCustomerCommand(lastId);

        // Act
        sut.apply(command);

        // Assert
        assertEquals(0, customerPanacheRepository.count(), "Customer should be deleted");
    }

    @Test
    @TestTransaction
    public void testDeleteCustomerWithInvalidId() {
        // Arrange / Act
        DeleteCustomerCommand command = new DeleteCustomerCommand(0L);

        // Act & Assert
        assertThrows(CustomerDeleteException.class, () -> sut.apply(command), "Expected CustomerDeleteException");
    }


}
