package edu.ku.cete.report.domain;

import java.util.Date;

public class FirstContactSurveyAuditHistory {
	private Long id;
	private String eventName;
	private Long modifiedUser;
	private String stateStudentIdentifier;
	private String studentFirstName;
	private String studentLastName;
	private Long surveyId;
	private Long studentId;
	private String beforeValue;
	private String currentValue;
	private Date createdDate;
	private Long domainAuditHistoryId;
	private String surveyStatusBeforeEdit;
	private String surveyStatusAfterEdit;

	private String modifiedUserFirstName;
	private String modifiedUserLastName;
	private String modifiedUserEducatorIdentifier;
	private String modifiedUserName;
	private String createddateStr;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Long getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
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

	public Long getSurveyId() {
		return surveyId;
	}

	public void setSurveyId(Long surveyId) {
		this.surveyId = surveyId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public String getBeforeValue() {
		return beforeValue;
	}

	public void setBeforeValue(String beforeValue) {
		this.beforeValue = beforeValue;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public Long getDomainAuditHistoryId() {
		return domainAuditHistoryId;
	}

	public void setDomainAuditHistoryId(Long domainAuditHistoryId) {
		this.domainAuditHistoryId = domainAuditHistoryId;
	}

	public String getSurveyStatusBeforeEdit() {
		return surveyStatusBeforeEdit;
	}

	public void setSurveyStatusBeforeEdit(String surveyStatusBeforeEdit) {
		this.surveyStatusBeforeEdit = surveyStatusBeforeEdit;
	}

	public String getSurveyStatusAfterEdit() {
		return surveyStatusAfterEdit;
	}

	public void setSurveyStatusAfterEdit(String surveyStatusAfterEdit) {
		this.surveyStatusAfterEdit = surveyStatusAfterEdit;
	}

	public String getModifiedUserFirstName() {
		return modifiedUserFirstName;
	}

	public void setModifiedUserFirstName(String modifiedUserFirstName) {
		this.modifiedUserFirstName = modifiedUserFirstName;
	}

	public String getModifiedUserLastName() {
		return modifiedUserLastName;
	}

	public void setModifiedUserLastName(String modifiedUserLastName) {
		this.modifiedUserLastName = modifiedUserLastName;
	}

	public String getModifiedUserEducatorIdentifier() {
		return modifiedUserEducatorIdentifier;
	}

	public void setModifiedUserEducatorIdentifier(String modifiedUserEducatorIdentifier) {
		this.modifiedUserEducatorIdentifier = modifiedUserEducatorIdentifier;
	}

	public String getModifiedUserName() {
		return modifiedUserName;
	}

	public void setModifiedUserName(String modifiedUserName) {
		this.modifiedUserName = modifiedUserName;
	}

	public void validate() {
		// TODO Auto-generated method stub
		if (this.id == null) {
			this.id = 0l;
		}
		if (this.eventName == null) {
			this.eventName = " ";
		}
		if (this.modifiedUser == null) {
			this.modifiedUser = 0l;
		}
		if (this.stateStudentIdentifier == null) {
			this.stateStudentIdentifier = " ";
		}
		if (this.studentFirstName == null) {
			this.studentFirstName = " ";
		}
		if (this.studentLastName == null) {
			this.studentLastName = " ";
		}
		if (this.surveyId == null) {
			this.surveyId = 0l;
		}
		if (this.studentId == null) {
			this.studentId = 0l;
		}
		if (this.beforeValue == null) {
			this.beforeValue = " ";
		}
		if (this.currentValue == null) {
			this.currentValue = " ";
		}
		if (this.domainAuditHistoryId == null) {
			this.domainAuditHistoryId = 0l;
		}
		if (this.surveyStatusBeforeEdit == null) {
			this.surveyStatusBeforeEdit = " ";
		}
		if (this.surveyStatusAfterEdit == null) {
			this.surveyStatusAfterEdit = " ";
		}
		if (this.modifiedUserName == null) {
			this.modifiedUserName = " ";
		}
		if (this.modifiedUserFirstName == null) {
			this.modifiedUserFirstName = " ";

		}
		if (this.modifiedUserLastName == null) {
			this.modifiedUserLastName = " ";

		}
		if (this.modifiedUserName == null) {
			this.modifiedUserName = " ";

		}
		if (this.modifiedUserEducatorIdentifier == null) {
			this.modifiedUserEducatorIdentifier = " ";

		}
	}

	public String getCreateddateStr() {
		return createddateStr;
	}

	public void setCreateddateStr(String createddateStr) {
		this.createddateStr = createddateStr;
	}

}
