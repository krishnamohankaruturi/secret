package edu.ku.cete.service.exception;

public class NoAccessToResourceException extends Exception {

	private static final long serialVersionUID = -6508745870191834516L;

	public NoAccessToResourceException() {
        super();
    }

    public NoAccessToResourceException(String message) {
        super(message);
    }

    public NoAccessToResourceException(Throwable cause) {
        super(cause);
    }

    public NoAccessToResourceException(String message, Throwable cause) {
        super(message, cause);
    }

}
