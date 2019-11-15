package edu.ku.cete.batch.upload;

public class InvalidHeaderException extends RuntimeException {
	private static final long serialVersionUID = 1044381423524552719L;

	public InvalidHeaderException(String message) {
		super(message);
	}
}
