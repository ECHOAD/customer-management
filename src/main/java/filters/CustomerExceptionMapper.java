package filters;

import customer.exception.CustomerCreationException;
import customer.exception.CustomerUpdateException;
import exceptions.ExceptionResponseDTO;
import exceptions.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;

/**
 * This class is responsible for mapping exceptions to HTTP responses.
 * It uses RESTEasy Reactive's ServerExceptionMapper to handle exceptions
 * and transform them into appropriate HTTP response entities.
 *
 * The class handles the following exceptions:
 * 1. RuntimeException: Maps to a 500 Internal Server Error response.
 * 2. InternalServerErrorException: Maps to a 500 Internal Server Error response.
 *
 * Each method logs the exception details and returns an ExceptionResponseDTO
 * containing the error message, HTTP status code, and a timestamp.
 *
 * Logger is utilized to log error messages for tracking and debugging purposes.
 */
public class CustomerExceptionMapper {
    private final static Logger logger = Logger.getLogger(CustomerExceptionMapper.class);

    @ServerExceptionMapper(priority = 5)
    public RestResponse<ExceptionResponseDTO> mapRuntimeExceptionException(RuntimeException ex) {
        final var response = new ExceptionResponseDTO(ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), LocalDateTime.now());
        logger.error("(mapExecutionTimeoutException) response: %s", response, ex);
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR, response);
    }

    @ServerExceptionMapper(priority = 3)
    public RestResponse<ExceptionResponseDTO> mapInternalServerErrorException(InternalServerErrorException ex) {
        final var response = new ExceptionResponseDTO(ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), LocalDateTime.now());
        logger.error("(mapExecutionTimeoutException) response: %s", response, ex);
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR, response);
    }

    @ServerExceptionMapper(priority = 2)
    public RestResponse<ExceptionResponseDTO> mapException(CustomerCreationException ex) {
        final var response = new ExceptionResponseDTO(ex.getMessage(), Response.Status.BAD_REQUEST.getStatusCode(), LocalDateTime.now());
        logger.error("(createCustomer) response: %s", response, ex);
        return RestResponse.status(Response.Status.BAD_REQUEST, response);
    }

    @ServerExceptionMapper(priority = 2)
    public RestResponse<ExceptionResponseDTO> mapException(CustomerUpdateException ex) {
        final var response = new ExceptionResponseDTO(ex.getMessage(), Response.Status.BAD_REQUEST.getStatusCode(), LocalDateTime.now());
        logger.error("(updateCustomer) response: %s", response, ex);
        return RestResponse.status(Response.Status.BAD_REQUEST, response);
    }

}
