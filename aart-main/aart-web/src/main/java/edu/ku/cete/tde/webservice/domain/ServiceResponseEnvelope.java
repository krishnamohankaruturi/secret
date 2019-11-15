package edu.ku.cete.tde.webservice.domain;


/**
 * @author cikai.champion
 *
 */
public class ServiceResponseEnvelope<T> {
	private ServiceResponseStatus status;
	
	private T response;

	/**
	 * @return the status
	 */
	public ServiceResponseStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(ServiceResponseStatus status) {
		this.status = status;
	}

	/**
	 * @return the response
	 */
	public T getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(T response) {
		this.response = response;
	}
	
}
