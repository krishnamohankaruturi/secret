package edu.ku.cete.domain;

import java.io.Serializable;
import java.util.Date;

public class DistrictSummaryReport implements Serializable {

	/**
	 * Added for F605 projected scoring
	 */
	private static final long serialVersionUID = -2413915651836270351L;

	private Date date;
	private String assessmentProgram;
	private String state;
	private String district;
	private Long districtId;
	private Long estimatedTestSessionsTesting;
	private Long estimatedTestSessionsScoring;
	private String dtcEmailAddress;
	private String projectionType;

	public String getProjectionType() {
		return projectionType;
	}

	public void setProjectionType(String projectionType) {
		this.projectionType = projectionType;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getAssessmentProgram() {
		return assessmentProgram;
	}

	public void setAssessmentProgram(String assessmentProgram) {
		this.assessmentProgram = assessmentProgram;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getDistrict() {
		return district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public Long getEstimatedTestSessionsTesting() {
		return estimatedTestSessionsTesting;
	}

	public void setEstimatedTestSessionsTesting(Long estimatedTestSessionsTesting) {
		this.estimatedTestSessionsTesting = estimatedTestSessionsTesting;
	}

	public Long getEstimatedTestSessionsScoring() {
		return estimatedTestSessionsScoring;
	}

	public void setEstimatedTestSessionsScoring(Long estimatedTestSessionsScoring) {
		this.estimatedTestSessionsScoring = estimatedTestSessionsScoring;
	}

	public String getDtcEmailAddress() {
		return dtcEmailAddress;
	}

	public void setDtcEmailAddress(String dtcEmailAddress) {
		this.dtcEmailAddress = dtcEmailAddress;
	}

	public Long getDistrictId() {
		return districtId;
	}

	public void setDistrictId(Long districtId) {
		this.districtId = districtId;
	}
}
