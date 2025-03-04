package filters;

import domain.exceptions.CustomerCreationException;
import domain.exceptions.CustomerDeleteException;
import domain.exceptions.CustomerUpdateException;
import interfaces.dto.ExceptionResponseDTO;
import infrastructure.exceptions.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;

/**
 * This class is responsible for mapping exceptions to HTTP responses.
 * It uses RESTEasy Reactive's ServerExceptionMapper to handle exceptions
 * and transform them into appropriate HTTP response entities.
 * <p>
 * The class handles the following exceptions:
 * 1. RuntimeException: Maps to a 500 Internal Server Error response.
 * 2. InternalServerErrorException: Maps to a 500 Internal Server Error response.
 * <p>
 * Each method logs the exception details and returns an ExceptionResponseDTO
 * containing the error message, HTTP status code, and a timestamp.
 * <p>
 * Logger is utilized to log error messages for tracking and debugging purposes.
 */
public class CustomerExceptionMapper {
    private final static Logger logger = Logger.getLogger(CustomerExceptionMapper.class);
    @ServerExceptionMapper(priority = 5)
    public RestResponse<ExceptionResponseDTO> mapRuntimeExceptionException(RuntimeException ex) {
        final var response = new ExceptionResponseDTO(ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), LocalDateTime.now());
        logger.error("(mapRuntimeExceptionException) response: %s", response, ex);
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR, response);
    }

    @ServerExceptionMapper(priority = 4)
    public RestResponse<ExceptionResponseDTO> mapInternalServerErrorException(InternalServerErrorException ex) {
        final var response = new ExceptionResponseDTO(ex.getMessage(), Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), LocalDateTime.now());
        logger.error("(mapInternalServerErrorException) response: %s", response, ex);
        return RestResponse.status(Response.Status.INTERNAL_SERVER_ERROR, response);
    }

    @ServerExceptionMapper(priority = 3)
    public RestResponse<ExceptionResponseDTO> mapCustomerCreationException(CustomerCreationException ex) {
        final var response = new ExceptionResponseDTO(ex.getMessage(), Response.Status.BAD_REQUEST.getStatusCode(), LocalDateTime.now());
        logger.error("(createCustomer) response: %s", response, ex);
        return RestResponse.status(Response.Status.BAD_REQUEST, response);
    }

    @ServerExceptionMapper(priority = 2)
    public RestResponse<ExceptionResponseDTO> mapCustomerUpdateException(CustomerUpdateException ex) {
        final var response = new ExceptionResponseDTO(ex.getMessage(), Response.Status.BAD_REQUEST.getStatusCode(), LocalDateTime.now());
        logger.error("(updateCustomer) response: %s", response, ex);
        return RestResponse.status(Response.Status.BAD_REQUEST, response);
    }

    @ServerExceptionMapper(priority = 1)
    public RestResponse<ExceptionResponseDTO> mapCustomerDeleteException(CustomerDeleteException ex) {
        final var response = new ExceptionResponseDTO(ex.getMessage(), Response.Status.BAD_REQUEST.getStatusCode(), LocalDateTime.now());
        logger.error("(deleteCustomer) response: %s", response, ex);
        return RestResponse.status(Response.Status.BAD_REQUEST, response);
    }

}
