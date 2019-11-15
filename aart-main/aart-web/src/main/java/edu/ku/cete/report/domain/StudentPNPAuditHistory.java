package edu.ku.cete.report.domain;

import edu.ku.cete.domain.audit.AuditableDomain;

public class StudentPNPAuditHistory extends AuditableDomain {
	
	private static final long serialVersionUID = 6864486051302798043L;
	
	private Long id;
	private String eventName;
	private String stateStudentIdentifier;
	private String studentFirstName;
	private String studentLastName;
	private Long studentId;
	private String beforeValue;
	private String currentValue;
	private String loggedInUserFirstName;
	private String loggedInUserLastName;
	private String state;
	private String modifiedUserFirstName;
	private String modifiedUserLastName;
	private String modifiedUserEducatorIdentifier;
	private String modifiedUserName;
	private Long modifiedUser;
	private String createddateStr;
	
	public StudentPNPAuditHistory()
	{
		
	}
	
	public StudentPNPAuditHistory(Long studentId) {
		this.studentId = studentId;
	} 
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
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getLoggedInUserFirstName() {
		return loggedInUserFirstName;
	}
	public void setLoggedInUserFirstName(String loggedInUserFirstName) {
		this.loggedInUserFirstName = loggedInUserFirstName;
	}
	public String getLoggedInUserLastName() {
		return loggedInUserLastName;
	}
	public void setLoggedInUserLastName(String loggedInUserLastName) {
		this.loggedInUserLastName = loggedInUserLastName;
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

	public void setModifiedUserEducatorIdentifier(
			String modifiedUserEducatorIdentifier) {
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
			this.id = 0L;
		}
		if (this.eventName == null) {
			this.eventName = " ";
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
		if (this.studentId == null) {
			this.studentId = 0L;
		}
		if (this.beforeValue == null) {
			this.beforeValue = " ";
		}
		if (this.currentValue == null) {
			this.currentValue = " ";
		}
		if (this.loggedInUserFirstName == null) {
			this.loggedInUserFirstName = " ";
		}
		if (this.loggedInUserLastName == null) {
			this.loggedInUserLastName = " ";
		}
		if (this.state == null) {
			this.state = " ";
		}
		if(this.modifiedUserFirstName == null){
			this.modifiedUserFirstName= " ";
					}
		if(this.modifiedUserLastName == null){
			this.modifiedUserLastName= " ";
					}
		
		if(this.modifiedUserName == null){
			this.modifiedUserName= " ";
					}
		if(this.modifiedUserEducatorIdentifier == null){
			this.modifiedUserEducatorIdentifier= " ";
					}
		if(this.modifiedUser  == null){
			this.modifiedUser = 0L;
					}

	}

	public Long getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getCreateddateStr() {
		return createddateStr;
	}

	public void setCreateddateStr(String createddateStr) {
		this.createddateStr = createddateStr;
	}
	
	
}
