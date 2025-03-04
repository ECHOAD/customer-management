package domain.exceptions;

/**
 * This exception is thrown when a customer cannot be created.
 */
public class CustomerDeleteException extends RuntimeException{
    public CustomerDeleteException(String message) {
        super(message);
    }

}
