package edu.ku.cete.report.domain;

import java.util.Date;

/*sudhansu : Created for US18184 -For student audit trail
 */
public class StudentAuditTrailHistory {

	private Long id;
	private String eventName;
	private Long modifiedUser;
	private String studentFirstName;
	private String studentLastName;
	private String studentStateId;
	private String beforeValue;
	private String currentValue;
	private Date createdDate;
	private Long domainAuditHistoryId;
	private Long studentId;
	
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

	public String getStudentStateId() {
		return studentStateId;
	}

	public void setStudentStateId(String studentStateId) {
		this.studentStateId = studentStateId;
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

	

	/**
	 * @return the modifiedUserFirstName
	 */
	public String getModifiedUserFirstName() {
		return modifiedUserFirstName;
	}

	/**
	 * @param modifiedUserFirstName the modifiedUserFirstName to set
	 */
	public void setModifiedUserFirstName(String modifiedUserFirstName) {
		this.modifiedUserFirstName = modifiedUserFirstName;
	}

	/**
	 * @return the modifiedUserLastName
	 */
	public String getModifiedUserLastName() {
		return modifiedUserLastName;
	}

	/**
	 * @param modifiedUserLastName the modifiedUserLastName to set
	 */
	public void setModifiedUserLastName(String modifiedUserLastName) {
		this.modifiedUserLastName = modifiedUserLastName;
	}

	/**
	 * @return the modifiedUserEducatorIdentifier
	 */
	public String getModifiedUserEducatorIdentifier() {
		return modifiedUserEducatorIdentifier;
	}

	/**
	 * @param modifiedUserEducatorIdentifier the modifiedUserEducatorIdentifier to set
	 */
	public void setModifiedUserEducatorIdentifier(
			String modifiedUserEducatorIdentifier) {
		this.modifiedUserEducatorIdentifier = modifiedUserEducatorIdentifier;
	}

	/**
	 * @return the modifiedUserName
	 */
	public String getModifiedUserName() {
		return modifiedUserName;
	}

	/**
	 * @param modifiedUserName the modifiedUserName to set
	 */
	public void setModifiedUserName(String modifiedUserName) {
		this.modifiedUserName = modifiedUserName;
	}

	/**
	 * @return the studentId
	 */
	public Long getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId the studentId to set
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public void validate() {
		// TODO Auto-generated method stub
		if (this.id == null) {
			this.id = 0L;
		}
		if (this.eventName == null) {
			this.eventName = " ";
		}
		if (this.modifiedUser == null) {
			this.modifiedUser = 0L;
		}
		if (this.studentFirstName == null) {
			this.studentFirstName = " ";
		}
		if (this.studentLastName == null) {
			this.studentLastName = " ";
		}
		if (this.studentStateId == null) {
			this.studentStateId = " ";
		}
		if (this.beforeValue == null) {
			this.beforeValue = " ";
		}
		if (this.currentValue == null) {
			this.currentValue = " ";
		}
		
		if (this.domainAuditHistoryId == null) {
			this.domainAuditHistoryId = 0L;
		}
		if(this.modifiedUserFirstName==null){
			this.modifiedUserFirstName="";
		}
		if(this.modifiedUserLastName==null){
			this.modifiedUserLastName="";
		}
		if(this.modifiedUserName==null){
			this.modifiedUserName="";
		}
		if(this.modifiedUserEducatorIdentifier==null){
			this.modifiedUserEducatorIdentifier="";
		}
		
	}

	public String getCreateddateStr() {
		return createddateStr;
	}

	public void setCreateddateStr(String createddateStr) {
		this.createddateStr = createddateStr;
	}

}
