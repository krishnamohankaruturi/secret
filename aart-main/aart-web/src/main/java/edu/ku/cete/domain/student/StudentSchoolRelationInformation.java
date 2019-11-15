/**
 * 
 */
package edu.ku.cete.domain.student;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationRecord;

/**
 * @author mahesh
 * This basically tells that the implementing object has student and the corresponding school information.
 */
public interface StudentSchoolRelationInformation extends StudentRecord,OrganizationRecord {
	
	/**
	 * @return {@link Organization}
	 */
	Organization getResponsibleSchool();
	/**
	 * @return {@link Long}
	 */
	Long getResponsibleSchoolId();
	/**
	 * @return {@link Long}
	 */
	Long getStudentId();
	/**
	 * @param stateStudentIdentifier
	 */
	void setStudentId(Long studentId);	
	/**
	 * @param responsibleSchoolId
	 */
	void setResponsibleSchoolId(Long responsibleSchoolId);	
	/**
	 * @return {@link Student}
	 */
	Student getStudent();
	/**
	 * @return {@link Long}
	 */
	String getStateStudentIdentifier();
	/**
	 * @param stateStudentIdentifier
	 */
	void setStateStudentIdentifier(String stateStudentIdentifier);	
	/**
	 * @return {@link String}
	 */
	String getResponsibleSchoolIdentifier();
	/**
	 * @param responsibleSchoolIdentifier
	 */
	void setResponsibleSchoolIdentifier(String responsibleSchoolIdentifier);
	
	boolean isFound();
}
