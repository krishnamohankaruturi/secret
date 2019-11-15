/**
 * 
 */
package edu.ku.cete.service.exception;

/**
 * @author neil.howerton
 *
 */
public class DuplicateTestSessionNameException extends Exception {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    public DuplicateTestSessionNameException() {
    }

    /**
     * @param message
     */
    public DuplicateTestSessionNameException(String message) {
        super(message);
    }

    /**
     * @param cause
     */
    public DuplicateTestSessionNameException(Throwable cause) {
        super(cause);
    }

    /**
     * @param message
     * @param cause
     */
    public DuplicateTestSessionNameException(String message, Throwable cause) {
        super(message, cause);
    }

}
