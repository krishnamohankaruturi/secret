/**
 * 
 */
package edu.ku.cete.domain.student;

import java.io.Serializable;

/**
 * @author mahesh
 * This is used only for returning results from database.
 */
public class StudentSchoolRelationDataObject implements Serializable {
	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = -1975397777601393996L;
	private Long id;
	private Long responsibleSchoolId;
	private Long studentId;
	private String stateStudentIdentifier;
	private String responsibleSchoolIdentifier;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	public Long getResponsibleSchoolId() {
		return responsibleSchoolId;
	}
	public Long getStudentId() {
		return studentId;
	}
	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}
	public void setResponsibleSchoolId(Long responsibleSchoolId) {
		this.responsibleSchoolId = responsibleSchoolId;
	}
	public String getStateStudentIdentifier() {
		return this.stateStudentIdentifier;
	}
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		this.stateStudentIdentifier = stateStudentIdentifier;
	}
	public String getResponsibleSchoolIdentifier() {
		return responsibleSchoolIdentifier;
	}
	public void setResponsibleSchoolIdentifier(
			String responsibleSchoolIdentifier) {
		this.responsibleSchoolIdentifier = responsibleSchoolIdentifier;
	}
	public StudentSchoolRelationInformation setRelation(
			StudentSchoolRelationInformation studentSchoolRelationInformation){
		boolean validInput = true;
		if(studentSchoolRelationInformation == null
				|| studentSchoolRelationInformation.getResponsibleSchoolIdentifier() == null
				|| studentSchoolRelationInformation.getStateStudentIdentifier() == null) {
			validInput = false;
		}
		if(
				validInput
				&& !studentSchoolRelationInformation.isFound()
				) {
			if(studentSchoolRelationInformation.getResponsibleSchoolIdentifier()
					.equalsIgnoreCase(this.getResponsibleSchoolIdentifier())
					&& studentSchoolRelationInformation.getStateStudentIdentifier()
					.equals(this.getStateStudentIdentifier())) {
				studentSchoolRelationInformation.setResponsibleSchoolId(getResponsibleSchoolId());
				studentSchoolRelationInformation.setStudentId(getStudentId());
			}
		}
		return studentSchoolRelationInformation;
	}
}
