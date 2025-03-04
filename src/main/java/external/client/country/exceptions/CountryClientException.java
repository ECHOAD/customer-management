package external.client.country.exceptions;

public class CountryClientException extends RuntimeException {
    public CountryClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
