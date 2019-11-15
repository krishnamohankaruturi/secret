package edu.ku.cete.service.exception;

public class NoSuchOrganizationException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public NoSuchOrganizationException() {
        super();
    }

    public NoSuchOrganizationException(String message) {
        super(message);
    }
}
