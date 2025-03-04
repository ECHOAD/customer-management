package infrastructure.country;


import infrastructure.country.dto.CountryDto;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

/**
 * Country client to get country by code
 */
@RegisterRestClient(configKey = "country-api")
public interface CountryClient {

    /**
     * Fetches a list of CountryDto objects based on the provided country codes.
     *
     * @param codes a comma-separated string of country codes
     * @return a list of CountryDto objects representing the countries with the specified codes
     */
    @GET
    @Path("/alpha")
    List<CountryDto> getCountryByCode(@QueryParam("codes") String codes);
}
