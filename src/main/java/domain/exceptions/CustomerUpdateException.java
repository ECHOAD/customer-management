package domain.exceptions;

/**
 * This exception is thrown when a customer cannot be created.
 */
public class CustomerUpdateException extends RuntimeException{
    public CustomerUpdateException(String message) {
        super(message);
    }

    public CustomerUpdateException(String message, Throwable cause) {
        super(message, cause);
    }
}
