package edu.ku.cete.service;

/**
 * @author nicholas
 */
public class ServiceException extends Exception {

    /**
     * Generated serial ID.
     */
    private static final long serialVersionUID = -5999215421394973766L;

    /**
     * EMPTY constructor.
     */
    public ServiceException() {
        super();
    }

    /**
     * @param message The message to throw.
     */
    public ServiceException(final String message) {
        super(message);
    }
}
