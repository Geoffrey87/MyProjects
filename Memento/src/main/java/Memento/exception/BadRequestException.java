package Memento.exception;

/**
 * Custom exception to handle bad requests.
 */
public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}
