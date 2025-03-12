package com.LegisTrack.LegisTrack.exception;


/**
 * Custom exception for business logic errors.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}

