package customer.apiv1;

import application.commands.CreateCustomerCommand;
import application.commands.DeleteCustomerCommand;
import application.commands.UpdateCustomerCommand;
import application.commands.UpdateCustomerRequestDTO;
import interfaces.api.v1.CustomerResource;
import interfaces.dto.CustomerResponseDTO;
import interfaces.dto.PaginatedResponse;
import application.queries.GetCustomerQueryById;
import application.queries.GetCustomersQuery;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import mediator.Mediator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.net.URI;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CustomerResourceTest {

    @Mock
    private Mediator mediator;

    @Mock
    private UriInfo uriInfo;

    @InjectMocks
    private CustomerResource customerResource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(uriInfo.getAbsolutePath()).thenReturn(URI.create("http://localhost/api/v1/customers"));
    }

    @Test
    void testGetCustomers() {
        CustomerResponseDTO customer1 = new CustomerResponseDTO(1L, "John Doe", "john@example.com",
                "Street 123", "1234567890", "DO",
                "Some place in the Dominican Republic", "8091230000", "DO", "Dominican");

        CustomerResponseDTO customer2 = new CustomerResponseDTO(2L, "John Smith", "john@example.com",
                "Street 456", "1234567890", "US",
                "Some place in the United States", "8091230000", "US", "American");

        PaginatedResponse<CustomerResponseDTO> responseMock = new PaginatedResponse<>(Arrays.asList(customer1, customer2), 2, 1, 1, false);

        when(mediator.send(any(GetCustomersQuery.class))).thenReturn(responseMock);

        Response response = customerResource.getCustomers(new GetCustomersQuery());

        assertEquals(200, response.getStatus());
        assertEquals(responseMock, response.getEntity());
    }

    @Test
    void testGetCustomerById() {
        CustomerResponseDTO customer1 = new CustomerResponseDTO(1L, "John Doe", "john@example.com",
                "Street 123", "1234567890", "DO",
                "Some place in the Dominican Republic", "8091230000", "DO", "Dominican");

        when(mediator.send(any(GetCustomerQueryById.class))).thenReturn(customer1);

        Response response = customerResource.getCustomerById(new GetCustomerQueryById(1L));

        assertEquals(200, response.getStatus());
        assertEquals(customer1, response.getEntity());
    }

    @Test
    void testCreateCustomer() {
        CustomerResponseDTO customerMock = new CustomerResponseDTO(1L, "Adrian", "adrianeea.ae@gmail.com",
                "Street 123", "8091230000", "DO",
                "Some place in the Dominican Republic", "8091230000", "DO", "Dominican");

        when(mediator.send(any(CreateCustomerCommand.class))).thenReturn(customerMock);

        Response response = customerResource.createCustomer(new CreateCustomerCommand(
                "John",
                "",
                "Doe",
                "Doe",
                "john@example.com",
                "Street 123",
                "1234567890",
                "US"
        ), uriInfo);

        assertEquals(201, response.getStatus());
        assertEquals(customerMock, response.getEntity());
    }

    @Test
    void testDeleteCustomer() {

        Response response = customerResource.deleteCustomer(1L);
        verify(mediator, times(1)).send(any(DeleteCustomerCommand.class));
        assertEquals(200, response.getStatus());
    }

    @Test
    void testUpdateCustomer() {
        CustomerResponseDTO updatedCustomerMock = new CustomerResponseDTO(1L, "John Doe", "john@example.com",
                "Street 123", "1234567890", "DO",
                "Some place in the Dominican Republic", "8091230000", "DO", "Dominican");

        when(mediator.send(any(UpdateCustomerCommand.class))).thenReturn(updatedCustomerMock);

        Response response = customerResource.updateCustomer(1L, new UpdateCustomerRequestDTO(
                1L,
                "jane@example.com",
                "Some street",
                "3050001234",
                "US"
        ));

        assertEquals(200, response.getStatus());
        assertEquals(updatedCustomerMock, response.getEntity());
    }
}
