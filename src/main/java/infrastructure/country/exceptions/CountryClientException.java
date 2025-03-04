package infrastructure.country.exceptions;

public class CountryClientException extends RuntimeException {
    public CountryClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
