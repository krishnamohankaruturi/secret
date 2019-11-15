package edu.ku.cete.service.exception;

public class StateIdAlreadyExistsException extends Exception {

    public StateIdAlreadyExistsException() {
        super();
    }

    public StateIdAlreadyExistsException(String message) {
        super(message);
    }

    public StateIdAlreadyExistsException(Throwable cause) {
        super(cause);
    }

    public StateIdAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

}
