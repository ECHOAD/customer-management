package infrastructure.exceptions;

public class InternalServerErrorException extends RuntimeException {

    public InternalServerErrorException() {

    }

    public InternalServerErrorException(String message) {
        super(message);
    }
}
