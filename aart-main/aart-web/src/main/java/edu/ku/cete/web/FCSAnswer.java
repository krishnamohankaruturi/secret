package edu.ku.cete.web;

import java.io.Serializable;

public class FCSAnswer implements Serializable {

	private static final long serialVersionUID = 7396919180599645366L;
	private String labelNumber;
	private String labelType;
	private String response;

	public String getLabelNumber() {
		return labelNumber;
	}

	public void setLabelNumber(String labelNumber) {
		this.labelNumber = labelNumber;
	}

	public String getLabelType() {
		return labelType;
	}

	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}
}
