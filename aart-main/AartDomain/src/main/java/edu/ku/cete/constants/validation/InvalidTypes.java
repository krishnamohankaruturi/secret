package edu.ku.cete.constants.validation;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import edu.ku.cete.util.InvalidTypesSerializer;

/**
 * @author m802r921
 * @version 1.0
 * @created 14-May-2012 11:30:12 AM
 */
@JsonSerialize(using = InvalidTypesSerializer.class)
public enum InvalidTypes {
	
	EMPTY ("Empty"),
	IN_VALID ("InValid"),
	IN_VALID_TYPE ("InValidType"),
	NOT_FOUND ("NotFound"),
	NOT_UNIQUE("NotUnique"),
	ROSTER_NOT_UNIQUE(" found more than one roster with same subject and same course."),
	NOT_UNIQUE_INACTIVE("NotUniqueInActive"),
	CREATED_NEW("CreatedNew"),
	NOT_RECOGNIZED("NotRecognized"),
	MULTIPLE_FOUND("MultipleFound"),
	NOT_ALLOWED("NotAllowed"), 
	IN_CORRECT("InCorrect"),
	CURRENT_SCHOOL_YEAR_NOT_CORRECT("does not match current year for state which is"),
	SCHOOL_IDENTIFIER_NOT_FOUND("is not found in the system. This record will not be created."),
	COURSE_NOT_FOUND("is not valid for Subject provided."),
	EDUCATOR_ID_NOT_FOUND("is not associated with school."),
	SUBJECT_NOT_FOUND("must be valid"),
	SUBJECT("Subject"),
	NOT_ALLOWED_NOT_FOUND("NotAllowedorNotFound"),
	ERROR("ErrorOccurred"),
	SCHOOL_NOT_CONTRACTING_FOR_ASSESSMENT("SchoolNotContractingForAssessment"),
	REMOVE_FROM_ROSTER_ERROR("Record must match on school ID, student state ID, subject and course."),
	NOT_SYNCED("NotSynced"),
	FOUND_FOR_SCHOOL("FoundForSchool"),
	ALREADY_EXISTS("AlreadyExists"),
	MIS_MATCH("MisMatch"),
	SCHOOL_STUDENT_MATCH("SchoolStudentMatch"), 
	MULTIPLE_EDUCATORS("MultipleEducators"), 
	EDUCATOR_IDENTIFIER_NOT_FOUND("is not associated with school"),
	ORG_EDUCATOR_MATCH("OrgEdMatch"),
	STATE_STUDENT_IDENTIFIER_EMPTY("should not be empty."),
	EDUCATOR_ID_INACTIVE("is inactive in the system. This record will not be created"),
	NULL(""),
	STUDENT_NOT_FOUND(" was not found in the specified organization."),
	STUDENT_NOT_ENROLLED(" is not enrolled in the specified organization."),
	STUDENT_NOT_IN_PROGRAM(" is not part of the specified assessment program."),
	STUDENT_NOT_ROSTERED_TO_USER(" is not rostered to you."), 
	USER_IS_NOT_A_TEACHER(" is not a Teacher.");
	
	
	/**
	 * invalid type name.
	 */
	private String invalidTypeName;

	/**
	 * @param invalidTypeName
	 */
	InvalidTypes(String invalidTypeName){
		this.setInvalidTypeName(invalidTypeName);
	}

	/**
	 * @return the invalidTypeName
	 */
	public String getInvalidTypeName() {
		return invalidTypeName;
	}

	/**
	 * @param invalidTypName the invalidTypeName to set
	 */
	public void setInvalidTypeName(String invalidTypName) {
		this.invalidTypeName = invalidTypName;
	}
	

	/**
	 * @return {@link String}
	 */
	@Override
	public String toString() {
		return invalidTypeName;
	}
}
