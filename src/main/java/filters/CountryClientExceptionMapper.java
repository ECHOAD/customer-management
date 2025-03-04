package filters;

import interfaces.dto.ExceptionResponseDTO;
import infrastructure.country.exceptions.CountryClientException;
import jakarta.ws.rs.core.Response;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;

import java.time.LocalDateTime;

/**
 * Maps exceptions of type {@link CountryClientException} to a {@link RestResponse}
 * with a status of {@link Response.Status#INTERNAL_SERVER_ERROR}.
 *
 */
public class CountryClientExceptionMapper {

    private final static Logger logger = Logger.getLogger(CountryClientExceptionMapper.class);

    @ServerExceptionMapper(priority = 3)
    public RestResponse<ExceptionResponseDTO> mapCountryClientException(CountryClientException ex) {
        final var response = new ExceptionResponseDTO("Error calling country service: ".concat(ex.getMessage()),
                Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
                LocalDateTime.now());
        logger.error("(mapExecutionTimeoutException) response: %s", response, ex);
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR, response);
    }

}
