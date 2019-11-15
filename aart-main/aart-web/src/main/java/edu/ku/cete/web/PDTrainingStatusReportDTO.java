package edu.ku.cete.web;

public class PDTrainingStatusReportDTO {
		
	private long organizationId;
	private long stateId;
	
	private String state;
	private String districtName;
	private String schoolId;
	private String schoolName;
	private String userStatus;
	private String educatorId;
	private String educatorFirstName;	
	private String educatorLastName;
	
	private long nbrOfPassedRequiredModules;
	private long nbrOfEnrolledRequiredModules;
	private long nbrOfRequiredModules;
	
	private long nbrOfPassedOptionalModules;
	private long nbrOfEnrolledOptionalModules;
	private long nbrOfOptionalModules;
	
	public long getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(long organizationId) {
		this.organizationId = organizationId;
	}
	public long getStateId() {
		return stateId;
	}
	public void setStateId(long stateId) {
		this.stateId = stateId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getDistrictName() {
		return districtName;
	}
	public void setDistrictName(String districtName) {
		this.districtName = districtName;
	}
	public String getSchoolId() {
		return schoolId;
	}
	public void setSchoolId(String schoolId) {
		this.schoolId = schoolId;
	}
	public String getSchoolName() {
		return schoolName;
	}
	public void setSchoolName(String schoolName) {
		this.schoolName = schoolName;
	}
	public String getUserStatus() {
		return userStatus;
	}
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}
	public String getEducatorId() {
		return educatorId;
	}
	public void setEducatorId(String educatorId) {
		this.educatorId = educatorId;
	}
	public String getEducatorFirstName() {
		return educatorFirstName;
	}
	public void setEducatorFirstName(String educatorFirstName) {
		this.educatorFirstName = educatorFirstName;
	}
	public String getEducatorLastName() {
		return educatorLastName;
	}
	public void setEducatorLastName(String educatorLastName) {
		this.educatorLastName = educatorLastName;
	}
	public long getNbrOfPassedRequiredModules() {
		return nbrOfPassedRequiredModules;
	}
	public void setNbrOfPassedRequiredModules(long nbrOfPassedRequiredModules) {
		this.nbrOfPassedRequiredModules = nbrOfPassedRequiredModules;
	}
	public long getNbrOfEnrolledRequiredModules() {
		return nbrOfEnrolledRequiredModules;
	}
	public void setNbrOfEnrolledRequiredModules(long nbrOfEnrolledRequiredModules) {
		this.nbrOfEnrolledRequiredModules = nbrOfEnrolledRequiredModules;
	}
	public long getNbrOfRequiredModules() {
		return nbrOfRequiredModules;
	}
	public void setNbrOfRequiredModules(long nbrOfRequiredModules) {
		this.nbrOfRequiredModules = nbrOfRequiredModules;
	}
	public long getNbrOfPassedOptionalModules() {
		return nbrOfPassedOptionalModules;
	}
	public void setNbrOfPassedOptionalModules(long nbrOfPassedOptionalModules) {
		this.nbrOfPassedOptionalModules = nbrOfPassedOptionalModules;
	}
	public long getNbrOfEnrolledOptionalModules() {
		return nbrOfEnrolledOptionalModules;
	}
	public void setNbrOfEnrolledOptionalModules(long nbrOfEnrolledOptionalModules) {
		this.nbrOfEnrolledOptionalModules = nbrOfEnrolledOptionalModules;
	}
	public long getNbrOfOptionalModules() {
		return nbrOfOptionalModules;
	}
	public void setNbrOfOptionalModules(long nbrOfOptionalModules) {
		this.nbrOfOptionalModules = nbrOfOptionalModules;
	}	
}
