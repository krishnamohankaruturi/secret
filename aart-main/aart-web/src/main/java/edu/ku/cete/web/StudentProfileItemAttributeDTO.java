package edu.ku.cete.web;

import java.util.Date;


/**
 * This class represents the data from profileitemattribute, profileitemattributecontainer,
 * profileitemattributenickname, profileItemAttributenameAttributeContainer, studentprofileitemattributevalue,
 * and student tables
 * @author vittaly
 *
 */
public class StudentProfileItemAttributeDTO extends ProfileItemAttributeDTO {

	/**
	 * studentId
	 */
	private Long studentId;
	
	/**
	 * stateStudentIdentifier
	 */
	private String stateStudentIdentifier;

    /**
     * selectedValue
     */
    private String selectedValue;
    
    private Date modifiedDate;
    private Long modifiedUser;
    
    private String brailleFileType; 
    
    /**
     * @return
     */
    public Long getStudentId() {
		return studentId;
	}

	/**
	 * @param studentId
	 */
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	/**
	 * @return
	 */
	public String getStateStudentIdentifier() {
		return stateStudentIdentifier;
	}

	/**
	 * @param stateStudentIdentifier
	 */
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}

	/**
	 * @return
	 */
	public String getSelectedValue() {
		return selectedValue;
	}

	/**
	 * @param selectedValue
	 */
	public void setSelectedValue(String selectedValue) {
		this.selectedValue = selectedValue;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Long getModifiedUser() {
		return modifiedUser;
	}

	public void setModifiedUser(Long modifiedUser) {
		this.modifiedUser = modifiedUser;
	}

	public String getBrailleFileType() {
		return brailleFileType;
	}

	public void setBrailleFileType(String brailleFileType) {
		this.brailleFileType = brailleFileType;
	}
    
}
