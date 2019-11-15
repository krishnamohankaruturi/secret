package edu.ku.cete.domain.common;

import edu.ku.cete.domain.audit.AuditableDomain;

public class AppConfiguration extends AuditableDomain {
    
  	private static final long serialVersionUID = 451073285953048386L;

	private Long id;
        
    private String attributeCode;
    
    private String attributeType;
    
    private String attributeName;
    
    private String attributeValue;
       
    protected Long assessmentProgramId;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAttributeCode() {
		return attributeCode;
	}

	public void setAttributeCode(String attributeCode) {
		this.attributeCode = attributeCode;
	}

	public String getAttributeType() {
		return attributeType;
	}

	public void setAttributeType(String attributeType) {
		this.attributeType = attributeType;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public String getAttributeValue() {
		return attributeValue;
	}

	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	public Long getAssessmentProgramId() {
		return this.assessmentProgramId;
	}
	
	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}
}
