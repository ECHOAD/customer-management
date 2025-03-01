package filters;

import exceptions.ExceptionResponseDTO;
import exceptions.InternalServerErrorException;
import jakarta.ws.rs.core.Response;
import org.jboss.resteasy.reactive.RestResponse;
import org.jboss.resteasy.reactive.server.ServerExceptionMapper;
import org.jboss.logging.Logger;

import java.time.LocalDateTime;

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

//
//    @ServerExceptionMapper(priority = 1)
//    public RestResponse<ExceptionResponseDTO> mapBankAccountNotFoundException(BankAccountNotFoundException ex) {
//        final var response = new ExceptionResponseDTO(ex.getMessage(), Response.Status.NOT_FOUND.getStatusCode(), LocalDateTime.now());
//        logger.error("(mapExecutionTimeoutException) response: %s", response, ex);
//        return RestResponse.status(Response.Status.NOT_FOUND, response);
//    }
}
