package Memento.exception;

/**
 * Custom exception to handle forbidden access scenarios.
 */
public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
