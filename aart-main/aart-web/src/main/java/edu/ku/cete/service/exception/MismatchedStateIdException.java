/**
 * 
 */
package edu.ku.cete.service.exception;

/**
 * @author neil.howerton
 *
 */
public class MismatchedStateIdException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public MismatchedStateIdException() {
        super();
    }

    /**
     * @param message {@link String}
     */
    public MismatchedStateIdException(String message) {
        super(message);
    }

    /**
     * @param cause {@link String}
     */
    public MismatchedStateIdException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message {@link String}
     * @param cause {@link Throwable}
     */
    public MismatchedStateIdException(String message, Throwable cause) {
        super(message, cause);
    }

}
