package external.client.country.service;

import external.client.country.CountryClient;
import external.client.country.dto.CountryDemonymDto;
import external.client.country.dto.CountryDto;
import external.client.country.exceptions.CountryClientException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.NotFoundException;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.ClientWebApplicationException;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link CountryService} that delegates to {@link CountryClient} using REST-Easy.
 */
@ApplicationScoped
public class CountryServiceImpl implements CountryService {

    public static final String DEFAULT_DEMONYM_KEY = "eng";

    private final static String COUNTRY_CODE_NOT_FOUND = "Country code '%s' not found.";
    private final static String UNABLE_TO_HANDLE_COUNTRY_CODE = "Unable to handle country code '%s'. Exception message: %s";
    private final static Logger logger = Logger.getLogger(CountryService.class);


    @Inject
    @RestClient
    CountryClient countryClient;


    /**
     * {@inheritDoc}
     */
    @Override
    public CountryDto getCountryByCode(String code) {
        try {
            List<CountryDto> countryDTOs = countryClient.getCountryByCode(code);
            return countryDTOs.get(0);
        } catch (ClientWebApplicationException e) {
            throw new CountryClientException(String.format(UNABLE_TO_HANDLE_COUNTRY_CODE, code, e.getMessage()), e);
        }
    }

    /**
     * Returns a {@link CountryDto} containing the country information for the country with the given code.
     * <p>
     * If the country code is not found, a {@link NotFoundException} is thrown.
     * <p>
     * If the REST call to the country service fails, a {@link InternalServerErrorException} is thrown.
     * <p>
     * If the country code is not recognized, a {@link CountryClientException} is thrown.
     *
     * @param countryCode the 2-character ISO 3166-1 alpha-2 country code
     * @return a {@link CountryDto} containing the country information
     * @throws NotFoundException if the country code is not found
     * @throws InternalServerErrorException if the REST call to the country service fails
     * @throws CountryClientException if the country code is not recognized
     */
    @Override
    public String findDemonymByCountryCode(String countryCode) {
        CountryDto countryDto = getCountryByCode(countryCode);
        CountryDemonymDto countryDemonymDto = countryDto.getDemonyms().get(DEFAULT_DEMONYM_KEY);
        return countryDemonymDto.getM();
    }

}
