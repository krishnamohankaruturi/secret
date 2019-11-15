package edu.ku.cete.tde.webservice.domain;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vittaly
 * object to hold LM micromap request data.
 *
 */
public class MicroMapRequest {

	/**
	 * contentCode 
	 */
	private List<String> contentCode;

	/**
	 * @return
	 */
	public List<String> getContentCode() {
		return contentCode;
	}

	/**
	 * @param contentCode
	 */
	public void setContentCode(List<String> contentCode) {
		this.contentCode = contentCode;
	}
	
	public void addContentCode(String contentCode) {
		if (this.contentCode == null) this.contentCode = new ArrayList<String>();
		if (contentCode != null) this.contentCode.add(contentCode);
	}
	
	public void clear() {
		if (this.contentCode != null) this.contentCode.clear();
	}
}
