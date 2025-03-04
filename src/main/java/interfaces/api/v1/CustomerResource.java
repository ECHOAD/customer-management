package interfaces.api.v1;

import application.commands.CreateCustomerCommand;
import application.commands.DeleteCustomerCommand;
import application.commands.UpdateCustomerCommand;
import application.commands.UpdateCustomerRequestDTO;
import interfaces.dto.CustomerResponseDTO;
import interfaces.dto.PaginatedResponse;
import application.queries.GetCustomerQueryById;
import application.queries.GetCustomersQuery;
import io.smallrye.common.constraint.NotNull;
import jakarta.inject.Inject;
import jakarta.validation.Valid;


import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.BeanParam;
import jakarta.ws.rs.Path;


import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import mediator.Mediator;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;


@Path("/api/v1/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Customers", description = "Operations about customers")
public class CustomerResource {
    private final Mediator mediator;


    @Inject
    public CustomerResource(Mediator mediator) {
        this.mediator = mediator;
    }

    @GET
    @Operation(summary = "Get all customers", description = "Returns a paginated list of customers.")
    @APIResponse(responseCode = "200", description = "A list of customers",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = PaginatedResponse.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response getCustomers(@BeanParam GetCustomersQuery getCustomersQuery) {
        return Response.ok(mediator.send(getCustomersQuery)).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get a customer by ID", description = "Retrieves a specific customer by their unique identifier.")
    @APIResponse(responseCode = "200", description = "A customer",
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    schema = @Schema(implementation = CustomerResponseDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response getCustomerById(@BeanParam GetCustomerQueryById getCustomerQueryById) {
        return Response.ok(mediator.send(getCustomerQueryById)).build();
    }

    @POST
    @APIResponse(responseCode = "201", description = "The created customer",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = CustomerResponseDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response createCustomer(@NotNull @Valid final CreateCustomerCommand createCustomerCommand, @Context final UriInfo uriInfo) {
        CustomerResponseDTO customer = mediator.send(createCustomerCommand);
        return Response.created(URI.create(uriInfo.getAbsolutePath() + "/" + customer.id())).entity(customer).build();
    }

    @DELETE
    @Path("/{id}")
    @APIResponse(responseCode = "200", description = "Customer deleted successfully")
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response deleteCustomer(@NotNull @PathParam("id") Long id) {
        return Response.ok(mediator.send(new DeleteCustomerCommand(id))).build();
    }

    @PUT
    @Path("/{id}")
    @APIResponse(responseCode = "200", description = "The updated customer",
                 content = @Content(mediaType = MediaType.APPLICATION_JSON,
                                    schema = @Schema(implementation = CustomerResponseDTO.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response updateCustomer(@PathParam("id") long id, @NotNull @Valid UpdateCustomerRequestDTO updateCustomerCommandDTO) {
        return Response.ok(mediator.send(new UpdateCustomerCommand(id, updateCustomerCommandDTO))).build();
    }
}
