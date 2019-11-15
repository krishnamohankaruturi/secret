package edu.ku.cete.batch.support;


public class SkipBatchException extends RuntimeException {

	private static final long serialVersionUID = -1202610984077631623L;

	public SkipBatchException(String message) {
		super(message);
	}
}
