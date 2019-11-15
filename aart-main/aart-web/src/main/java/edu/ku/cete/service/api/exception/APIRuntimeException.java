package edu.ku.cete.service.api.exception;

/**
 * Purely for rollback identification reasons, this shouldn't be used widely
 * @author bkoelper
 *
 */
public class APIRuntimeException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4469038604766790101L;

	public APIRuntimeException(String message) {
		super(message);
	}
}
