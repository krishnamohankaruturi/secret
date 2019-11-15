package edu.ku.cete.batch.support;


public class InvalidRecordException extends RuntimeException {

	private static final long serialVersionUID = -1202610984077631623L;

	public InvalidRecordException(String message) {
		super(message);
	}
}
