package customer.api.v1;

import customer.domain.Customer;
import io.smallrye.common.constraint.NotNull;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.QueryParam;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import org.eclipse.microprofile.openapi.annotations.enums.SchemaType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import java.net.URI;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Path("/api/v1/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "Customers", description = "Operations about customers")
public class CustomerResource {

    private final Map<String, Customer> customers = new ConcurrentHashMap<>();

    @GET
    @APIResponse(responseCode = "200", description = "A list of customers",
            content = @Content(schema = @Schema(type = SchemaType.ARRAY, implementation = Customer.class)))
    public Response getCustomers() {
        return Response.ok(customers.values()).build();
    }

    @POST
    @APIResponse(responseCode = "201", description = "The created customer",
            content = @Content(schema = @Schema(implementation = Customer.class)))
    @APIResponse(responseCode = "400", description = "Invalid input")
    public Response createCustomer(@NotNull @Valid final Customer customer, @Context final UriInfo uriInfo) {
        if (customers.containsKey(customer.getEmail())) {
            return Response.status((Response.Status.BAD_REQUEST)).build();
        }

        customers.put(customer.getEmail(), customer);
        URI uri = uriInfo.getAbsolutePathBuilder().path(customer.getEmail()).build();
        return Response.created(uri).entity(customer).build();
    }

    @GET
    public Response getCustomerByCountryCode(@NotEmpty @QueryParam("countryCode") String countryCode, @Min(1) @QueryParam("page") int page, @Min(1) @QueryParam("limit") int limit) {
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @DELETE
    public Response deleteCustomer(@NotEmpty @QueryParam("email") String email) {
        return Response.status(Response.Status.ACCEPTED).build();
    }

    @PUT
    public Response updateCustomer(@NotEmpty @QueryParam("email") String email) {
        return Response.status(Response.Status.ACCEPTED).build();
    }
}
