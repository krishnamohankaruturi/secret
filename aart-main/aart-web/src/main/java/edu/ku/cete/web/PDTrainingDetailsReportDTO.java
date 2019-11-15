package edu.ku.cete.web;

import java.util.List;

import edu.ku.cete.domain.professionaldevelopment.UserTestResponse;

public class PDTrainingDetailsReportDTO {
		
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
	
	private Long userTestId;
	
	private String moduleName;
	private Boolean requiredFlag;
	private String moduleStatus;
	private Boolean testResult;
	
	List<UserTestResponse> userResponses; 
	
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
	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public Boolean getRequiredFlag() {
		return requiredFlag;
	}
	public void setRequiredFlag(Boolean requiredFlag) {
		this.requiredFlag = requiredFlag;
	}
	public String getModuleStatus() {
		return moduleStatus;
	}
	public void setModuleStatus(String moduleStatus) {
		this.moduleStatus = moduleStatus;
	}	
	public Long getUserTestId() {
		return userTestId;
	}
	public void setUserTestId(Long userTestId) {
		this.userTestId = userTestId;
	}	
	public Boolean getTestResult() {
		return testResult;
	}
	public void setTestResult(Boolean testResult) {
		this.testResult = testResult;
	}
	public List<UserTestResponse> getUserResponses() {
		return userResponses;
	}
	public void setUserResponses(List<UserTestResponse> userResponses) {
		this.userResponses = userResponses;
	}	
}
