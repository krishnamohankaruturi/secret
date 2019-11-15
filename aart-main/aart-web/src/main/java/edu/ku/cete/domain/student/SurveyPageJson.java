package edu.ku.cete.domain.student;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SurveyPageJson {
	private Long surveySectionId;
	private Long globalPageNumber;
	private boolean isCompleted;
	private Date createdDate;
	private Date modifiedDate;
	private Long createdUser;
	private Long modifiedUser;
	private boolean activeFlag;
	private List<StudentSurveyResponseJson> studentSurveyResponsesJsons = new ArrayList<StudentSurveyResponseJson>();
	
	public Long getGlobalPageNumber() {
		return globalPageNumber;
	}
	public void setGlobalPageNumber(Long globalPageNumber) {
		this.globalPageNumber = globalPageNumber;
	}
	public boolean isCompleted() {
		return isCompleted;
	}
	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}
	public Date getCreatedDate() {
		return createdDate;
	}
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	public Date getModifiedDate() {
		return modifiedDate;
	}
	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}
	public Long getCreatedUser() {
		return createdUser;
	}
	public void setCreatedUser(Long createdUser) {
		this.createdUser = createdUser;
	}	
	public Long getModifiedUser() {
		return modifiedUser;
	}
	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}
	public boolean isActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(boolean activeFlag) {
		this.activeFlag = activeFlag;
	}	
	public Long getSurveySectionId() {
		return surveySectionId;
	}
	public void setSurveySectionId(Long surveySectionId) {
		this.surveySectionId = surveySectionId;
	}
	public List<StudentSurveyResponseJson> getStudentSurveyResponsesJsons() {
		return studentSurveyResponsesJsons;
	}
	public void setStudentSurveyResponsesJsons(
			List<StudentSurveyResponseJson> studentSurveyResponsesJsons) {
		this.studentSurveyResponsesJsons = studentSurveyResponsesJsons;
	}	
}
