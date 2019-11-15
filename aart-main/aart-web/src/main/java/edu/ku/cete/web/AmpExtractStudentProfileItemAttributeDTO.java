package edu.ku.cete.web;

public class AmpExtractStudentProfileItemAttributeDTO {

	private Long studentId;
    private String selectedValue;
    private Long attributeNameAttributeContainerId;
	private Long attributeNameId;
    private String attributeName;
    private Long attributeContainerId;    
    private String attributeContainerName;
    
    public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public String getSelectedValue() {
		return selectedValue;
	}
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public Long getAttributeNameAttributeContainerId() {
		return attributeNameAttributeContainerId;
	}

	public void setAttributeNameAttributeContainerId(Long attributeNameAttributeContainerId) {
		this.attributeNameAttributeContainerId = attributeNameAttributeContainerId;
	}

	public Long getAttributeNameId() {
		return attributeNameId;
	}

	public void setAttributeNameId(Long attributeNameId) {
		this.attributeNameId = attributeNameId;
	}

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	public Long getAttributeContainerId() {
		return attributeContainerId;
	}

	public void setAttributeContainerId(Long attributeContainerId) {
		this.attributeContainerId = attributeContainerId;
	}

	public String getAttributeContainerName() {
		return attributeContainerName;
	}

	public void setAttributeContainerName(String attributeContainerName) {
		this.attributeContainerName = attributeContainerName;
	}
	
}
