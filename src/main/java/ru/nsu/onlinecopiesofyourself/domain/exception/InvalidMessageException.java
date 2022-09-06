package ru.nsu.onlinecopiesofyourself.domain.exception;

public class InvalidMessageException extends RuntimeException{
    public InvalidMessageException() {
    }

    public InvalidMessageException(String message) {
        super(message);
    }

    public InvalidMessageException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidMessageException(Throwable cause) {
        super(cause);
    }
}
