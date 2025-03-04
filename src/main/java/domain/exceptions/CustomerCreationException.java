package domain.exceptions;

/**
 * This exception is thrown when a customer cannot be created.
 */
public class CustomerCreationException extends RuntimeException{
    public CustomerCreationException(String message) {
        super(message);
    }

    public CustomerCreationException(String message, Throwable cause) {
        super(message, cause);
    }
}
