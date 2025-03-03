package customer.commands;


import customer.factories.CustomerFactory;
import customer.repository.CustomerPanacheRepository;
import io.quarkus.test.TestTransaction;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.mockito.Mockito.*;

@QuarkusTest
public class DeleteCustomerCommandHandlerTest {

    @Inject
    CustomerPanacheRepository customerPanacheRepository;

    DeleteCustomerCommandHandler sut;

    @BeforeEach
    @Transactional
    void setup() {

        customerPanacheRepository = spy(customerPanacheRepository);

        customerPanacheRepository.deleteAll();

        customerPanacheRepository.persist(CustomerFactory
                .newInstance("Adrian",
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
    public void testUpdateCustomerWithoutChangingCountry() {
        // Arrange / Act
        DeleteCustomerCommand command = new DeleteCustomerCommand(1L);

        // Act
        sut.apply(command);
        verify(customerPanacheRepository, times(1)).deleteById(1L);
    }


}
