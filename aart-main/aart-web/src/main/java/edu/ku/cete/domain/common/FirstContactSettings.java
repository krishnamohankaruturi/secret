package edu.ku.cete.domain.common;

import edu.ku.cete.domain.audit.AuditableDomain;

public class FirstContactSettings extends AuditableDomain {
	// Per US17690
	private static final long serialVersionUID = 3328918360202684566L;

	private Long organizationId;

	private String organizationName;

	private Long categoryId;

	private String categoryName;

	private boolean scienceFlag;

	private Long schoolYear;
	
	private boolean elaFlag;
	
	private boolean mathFlag;
	
	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getOrganizationName() {
		return organizationName;
	}

	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}
	
	public boolean getScienceFlag() {
		return scienceFlag;
	}

	public void setScienceFlag(boolean scienceFlag) {
		this.scienceFlag = scienceFlag;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}
	
	public boolean getElaFlag() {
		return elaFlag;
	}

	public void setElaFlag(boolean elaFlag) {
		this.elaFlag = elaFlag;
	}

	public boolean getMathFlag() {
		return mathFlag;
	}

	public void setMathFlag(boolean mathFlag) {
		this.mathFlag = mathFlag;
	}

	@Override
	public String toString() {
		return "FirstContactSettings [organizationId=" + organizationId + ", categoryId=" + categoryId
				+ ", elaFlag=" + elaFlag + ", mathFlag=" + mathFlag + ", scienceFlag=" + scienceFlag + ", schoolYear=" + schoolYear + "]";
	}
	
	
}