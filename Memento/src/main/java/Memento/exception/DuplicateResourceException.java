package Memento.exception;


/**
 * Custom exception to handle cases where a resource already exists.
 */
public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message) {
        super(message);
    }
}
