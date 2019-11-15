package edu.ku.cete.web;

import java.util.Date;

public class PNPAbridgedExtractDTO {
	protected Long studentId;
	protected String stateStudentIdentifier;
	protected String studentFirstName;
	protected String studentLastName;
	protected String profileStatus;
	protected String stateName;
	protected String districtName;
	protected String districtDisplayIdentifier;
	protected String schoolName;
	protected String schoolDisplayIdentifier;
	protected String pnpJsonText;
	protected String modifiedBy;
	protected Date modifiedDate;
	protected String comprehensiveRace;
	protected String hispanicEthnicity;
	
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	public String getStudentFirstName() {
		return studentFirstName;
	}
	public void setStudentFirstName(String studentFirstName) {
		this.studentFirstName = studentFirstName;
	}
	public String getStudentLastName() {
		return studentLastName;
	}
	public void setStudentLastName(String studentLastName) {
		this.studentLastName = studentLastName;
	}
	public String getProfileStatus() {
		return profileStatus;
	}
	public void setProfileStatus(String profileStatus) {
		this.profileStatus = profileStatus;
	}
	public String getStateName() {
		return stateName;
	}
	public void setStateName(String stateName) {
		this.stateName = stateName;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getDistrictDisplayIdentifier() {
		return districtDisplayIdentifier;
	}
	public void setDistrictDisplayIdentifier(String districtDisplayIdentifier) {
		this.districtDisplayIdentifier = districtDisplayIdentifier;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getSchoolDisplayIdentifier() {
		return schoolDisplayIdentifier;
	}
	public void setSchoolDisplayIdentifier(String schoolDisplayIdentifier) {
		this.schoolDisplayIdentifier = schoolDisplayIdentifier;
	}
	public String getPnpJsonText() {
		return pnpJsonText;
	}
	public void setPnpJsonText(String pnpJsonText) {
		this.pnpJsonText = pnpJsonText;
	}
	public String getModifiedBy() {
		return modifiedBy;
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public String getComprehensiveRace() {
		return comprehensiveRace;
	}
	public void setComprehensiveRace(String comprehensiveRace) {
		this.comprehensiveRace = comprehensiveRace;
	}
	public String getHispanicEthnicity() {
		String hispanicEthnicity="";
		if(this.hispanicEthnicity != null  && (hispanicEthnicity.equalsIgnoreCase("Yes")||hispanicEthnicity.equalsIgnoreCase("1")||hispanicEthnicity.equalsIgnoreCase("true"))) {	
			hispanicEthnicity= this.hispanicEthnicity;
		}
		else if(this.hispanicEthnicity != null  && (hispanicEthnicity.equalsIgnoreCase("No")||hispanicEthnicity.equalsIgnoreCase("0")||hispanicEthnicity.equalsIgnoreCase("false"))) {
			hispanicEthnicity= this.hispanicEthnicity;
		}
		else if(this.hispanicEthnicity != null) {
			hispanicEthnicity= this.hispanicEthnicity;
		}
		return hispanicEthnicity;
	}
	public void setHispanicEthnicity(String hispanicEthnicity) {
		if(hispanicEthnicity.equalsIgnoreCase("Yes")||hispanicEthnicity.equalsIgnoreCase("1")||hispanicEthnicity.equalsIgnoreCase("true")) {	
			this.hispanicEthnicity = "true";
		}
		else if(hispanicEthnicity.equalsIgnoreCase("No")||hispanicEthnicity.equalsIgnoreCase("0")||hispanicEthnicity.equalsIgnoreCase("false")) {
			this.hispanicEthnicity = "false";
		}
		else {
			this.hispanicEthnicity = hispanicEthnicity;
		}
	}
}
