package edu.ku.cete.service.exception;

public class EpServiceException extends RuntimeException {

	private static final long serialVersionUID = 1931357066050228770L; 
	 
	
	public EpServiceException(String errMsg) { 
		super(errMsg);
	} 

}
