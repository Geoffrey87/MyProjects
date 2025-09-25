package Memento.exception;

/**
 * Custom exception to indicate that a requested resource was not found.
 */
public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
