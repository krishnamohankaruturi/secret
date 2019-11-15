package edu.ku.cete.report.domain;

import edu.ku.cete.domain.audit.AuditableDomain;
public class OrganizationManagementAudit extends AuditableDomain {

	private static final long serialVersionUID = 4830488851932208475L;

	private Long id;

	private Long sourceOrgId;
	
	private String sourceOrgNameWithIdentifier;
	
	private String destOrgNameWithIdentifier;

	private Long destOrgId;

	private Long studentId;

	private Long aartUserId;
	
	private String aartUserName;
	
	private String modifiedUserName;
	
	private String stateStudentIdentifier;

	private Long rosterId;
	
	private String rosterName;

	private Long enrollmentId;

	private Long currentSchoolYear;

	private String operationType;
	
	private Long sourceOrgDistrictId;
	
	private String sourceOrgDistrictName;
	
	private Long destOrgDistrictId;
	
	private String destOrgDistrictName;
	
	private Long sateId;
	
	private String stateName; 
	
	private String modifieddateStr;

	public OrganizationManagementAudit() {
	}

	public OrganizationManagementAudit(Long sourceOrgId, String operationType) {
		this.sourceOrgId = sourceOrgId;
		this.operationType = operationType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getSourceOrgId() {
		return sourceOrgId;
	}

	public void setSourceOrgId(Long sourceOrgId) {
		this.sourceOrgId = sourceOrgId;
	}

	public Long getDestOrgId() {
		return destOrgId;
	}

	public void setDestOrgId(Long destOrgId) {
		this.destOrgId = destOrgId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getAartUserId() {
		return aartUserId;
	}

	public void setAartUserId(Long aartUserId) {
		this.aartUserId = aartUserId;
	}

	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	public Long getRosterId() {
		return rosterId;
	}

	public void setRosterId(Long rosterId) {
		this.rosterId = rosterId;
	}

	public Long getEnrollmentId() {
		return enrollmentId;
	}

	public void setEnrollmentId(Long enrollmentId) {
		this.enrollmentId = enrollmentId;
	}

	public Long getCurrentSchoolYear() {
		return currentSchoolYear;
	}

	public void setCurrentSchoolYear(Long currentSchoolYear) {
		this.currentSchoolYear = currentSchoolYear;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getSourceOrgNameWithIdentifier() {
		return sourceOrgNameWithIdentifier;
	}

	public void setSourceOrgNameWithIdentifier(String sourceOrgNameWithIdentifier) {
		this.sourceOrgNameWithIdentifier = sourceOrgNameWithIdentifier;
	}

	public String getDestOrgNameWithIdentifier() {
		return destOrgNameWithIdentifier;
	}

	public void setDestOrgNameWithIdentifier(String destOrgNameWithIdentifier) {
		this.destOrgNameWithIdentifier = destOrgNameWithIdentifier;
	}

	public String getRosterName() {
		return rosterName;
	}

	public void setRosterName(String rosterName) {
		this.rosterName = rosterName;
	}

	public String getAartUserName() {
		return aartUserName;
	}

	public void setAartUserName(String aartUserName) {
		this.aartUserName = aartUserName;
	}

	public Long getSourceOrgDistrictId() {
		return sourceOrgDistrictId;
	}

	public void setSourceOrgDistrictId(Long sourceOrgDistrictId) {
		this.sourceOrgDistrictId = sourceOrgDistrictId;
	}

	public String getSourceOrgDistrictName() {
		return sourceOrgDistrictName;
	}

	public void setSourceOrgDistrictName(String sourceOrgDistrictName) {
		this.sourceOrgDistrictName = sourceOrgDistrictName;
	}

	public Long getDestOrgDistrictId() {
		return destOrgDistrictId;
	}

	public void setDestOrgDistrictId(Long destOrgDistrictId) {
		this.destOrgDistrictId = destOrgDistrictId;
	}

	public String getDestOrgDistrictName() {
		return destOrgDistrictName;
	}

	public void setDestOrgDistrictName(String destOrgDistrictName) {
		this.destOrgDistrictName = destOrgDistrictName;
	}

	public String getModifiedUserName() {
		return modifiedUserName;
	}

	public void setModifiedUserName(String modifiedUserName) {
		this.modifiedUserName = modifiedUserName;
	}

	public Long getSateId() {
		return sateId;
	}

	public void setSateId(Long sateId) {
		this.sateId = sateId;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	
	public String getModifieddateStr() {
		return modifieddateStr;
	}

	public void setModifieddateStr(String modifieddateStr) {
		this.modifieddateStr = modifieddateStr;
	}

	@Override
	public String toString() {
		return "OrganizationManagementAudit [sourceOrgId=" + sourceOrgId + ", destOrgId=" + destOrgId + ", studentId="
				+ studentId + ", aartUserId=" + aartUserId + ", rosterId=" + rosterId + ", enrollmentId=" + enrollmentId
				+ ", currentSchoolYear=" + currentSchoolYear + ", operationType=" + operationType + "]";
	}

	public void validate() {
		// TODO Auto-generated method stub
		if (this.id == null) {
			this.id = 0L;
		}
		if (this.sourceOrgId == null) {
			this.sourceOrgId = 0L;
		}
		if (this.destOrgId == null) {
			this.destOrgId = 0L;
		}
		if (this.studentId == null) {
			this.studentId = 0L;
		}
		if (this.aartUserId == null) {
			this.aartUserId = 0L;
		}
		if (this.rosterId == null) {
			this.rosterId = 0L;
		}
		if (this.enrollmentId == null) {
			this.enrollmentId = 0L;
		}
		if (this.currentSchoolYear == null) {
			this.currentSchoolYear = 0L;
		}
		if (this.operationType == null) {
			this.operationType = " ";
		}

	}
}
