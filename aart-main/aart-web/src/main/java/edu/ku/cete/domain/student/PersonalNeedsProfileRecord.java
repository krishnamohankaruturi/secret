/**
 * 
 */
package edu.ku.cete.domain.student;

import java.util.HashMap;
import java.util.Map;

import edu.ku.cete.domain.common.HexBinary;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.property.TraceableEntity;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.util.ParsingConstants;

/**
 * @author mahesh
 * TODO if 1 more record is bound to contains PNP profile then create an interface called PMPRecord
 */
public class PersonalNeedsProfileRecord
extends TraceableEntity implements StudentSchoolRelationInformation {

	/**
	 * for serialization.
	 */
	private static final long serialVersionUID = 6815376801184084650L;

	/**
	 * id.
	 */
	private Long id;
	
	/**
	 * student.
	 */
	private Student student = new Student();
	
	/**
	 * responsible school.
	 */
	private Organization responsibleSchool = new Organization();	
	
	/**
	 * attributeName
	 */
	private String attributeName;
	
	/**
	 * attributeValue
	 */
	private String attributeValue;
	
	/**
	 * attributeContainer
	 */
	private String attributeContainer;
	
	/**
	 * @return
	 */
	public String getAttributeName() {
		return attributeName;
	}
	
	/**
	 * @param attributeName
	 */
	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}
	
	/**
	 * @return
	 */
	public String getAttributeValue() {
		return attributeValue;
	}
	
	/**
	 * @param attributeValue
	 */
	public void setAttributeValue(String attributeValue) {
		this.attributeValue = attributeValue;
	}

	/**
	 * @return
	 */
	public String getAttributeContainer() {
		return attributeContainer;
	}
	
	/**
	 * @param attributeContainer
	 */
	public void setAttributeContainer(String attributeContainer) {
		this.attributeContainer = attributeContainer;
	}
	
	/**
	 * @return the responsibleSchool
	 */
	public Organization getResponsibleSchool() {
		return responsibleSchool;
	}
	
	/**
	 * @param responsibleSchool the responsibleSchool to set
	 */
	public void setResponsibleSchool(Organization responsibleSchool) {
		this.responsibleSchool = responsibleSchool;
	}
	
	/**
	 * @return the stateStudentIdentifier
	 */
	public String getStateStudentIdentifier() {
		return student.getStateStudentIdentifier();
	}
	
	/**
	 * @param stateStudentIdentifier the stateStudentIdentifier to set
	 */
	public void setStateStudentIdentifier(String stateStudentIdentifier) {
		student.setStateStudentIdentifier(stateStudentIdentifier);
	}
	
	/**
	 * @return the responsibleSchoolIdentifier
	 */
	public String getResponsibleSchoolIdentifier() {
		return responsibleSchool.getDisplayIdentifier();
	}
	
	/**
	 * @param responsibleSchoolIdentifier the responsibleSchoolIdentifier to set
	 */
	public void setResponsibleSchoolIdentifier(
			String responsibleSchoolIdentifier) {
		this.responsibleSchool.setDisplayIdentifier(responsibleSchoolIdentifier);
	}	
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}
	
	/**
	 * @param student the student to set
	 */
	public void setStudent(Student student) {
		this.student = student;
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.domain.common.Identifiable#getId()
	 */
	@Override
	public Long getId() {
		return id;
	}

	/* (non-Javadoc)
	 * @see edu.ku.cete.domain.common.Identifiable#getId(int)
	 */
	@Override
	public Long getId(int order) {
		return getId();
	}

	/* (non-Javadoc)
	 * @see edu.ku.cete.domain.common.Identifiable#getStringIdentifier(int)
	 */
	@Override
	public String getStringIdentifier(int order) {		
		return null;
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.domain.student.StudentSchoolRelationInformation#getStudent()
	 */
	@Override
	public Student getStudent() {
		return student;
	}

	/* (non-Javadoc)
	 * @see edu.ku.cete.domain.common.ValidateableRecord#getIdentifier()
	 */
	@Override
	public String getIdentifier() {
		return student.getStateStudentIdentifier() + ParsingConstants.BLANK;
	}
	/* (non-Javadoc)
	 * @see edu.ku.cete.domain.common.OrganizationRecord#getOrganization()
	 */
	@Override
	public Organization getOrganization() {
		return getResponsibleSchool();
	}
	/* (non-Javadoc)
	 * @see edu.ku.cete.domain.student.StudentSchoolRelationInformation#getStudentId()
	 */
	@Override
	public Long getStudentId() {
		return getStudent().getId();
	}
	/* (non-Javadoc)
	 * @see edu.ku.cete.domain.student.StudentSchoolRelationInformation#setStudentId(java.lang.Long)
	 */
	@Override
	public void setStudentId(Long studentId) {
		getStudent().setId(studentId);
	}
	/* (non-Javadoc)
	 * @see edu.ku.cete.domain.student.StudentSchoolRelationInformation#getResponsibleSchoolId()
	 */
	@Override
	public Long getResponsibleSchoolId() {
		return getResponsibleSchool().getId();
	}
	/* (non-Javadoc)
	 * @see edu.ku.cete.domain.student.StudentSchoolRelationInformation#setResponsibleSchoolId(java.lang.Long)
	 */
	@Override
	public void setResponsibleSchoolId(Long responsibleSchoolId) {
		getResponsibleSchool().setId(responsibleSchoolId);
	}
	/* (non-Javadoc)
	 * @see edu.ku.cete.domain.student.StudentSchoolRelationInformation#isFound()
	 */
	@Override
	public boolean isFound() {
		return (getResponsibleSchoolId() != null
				&& getStudentId() != null);
	}
	
}
