package edu.ku.cete.web;

import java.io.Serializable;

public class FCSHeader implements Serializable {

	private static final long serialVersionUID = 5791239382922352255L;

	private Long labelId;
	private Long globalPageNum;
	private Long surveyOrder;
	private String labelNumber;
	private String headerName;

	public Long getLabelId() {
		return labelId;
	}

	public void setLabelId(Long labelId) {
		this.labelId = labelId;
	}

	public Long getGlobalPageNum() {
		return globalPageNum;
	}

	public void setGlobalPageNum(Long globalPageNum) {
		this.globalPageNum = globalPageNum;
	}

	public Long getSurveyOrder() {
		return surveyOrder;
	}

	public void setSurveyOrder(Long surveyOrder) {
		this.surveyOrder = surveyOrder;
	}

	public String getLabelNumber() {
		return labelNumber;
	}

	public void setLabelNumber(String labelNumber) {
		this.labelNumber = labelNumber;
	}

	public String getHeaderName() {
		return headerName;
	}

	public void setHeaderName(String headerName) {
		this.headerName = headerName;
	}
}
