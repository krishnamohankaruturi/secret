package edu.ku.cete.domain.validation;

public enum FieldName {
	STUDENT_STATE_ID("State Student Identifier"),
	SCHOOL_IDENTIFIER("School_Identifier"),
	AYP_SCHOOL_IDENTIFIER("AYP_QPA_Bldg_No"),
	ATTENDANCE_SCHOOL("AttendanceSchool"),
	AYP_SCHOOL("AYP school"),
	EDUCATOR_IDENTIFIER("Educator Identifier"),
	STATE_COURSE("StateCourse"),
	UPLOAD("Upload"),
	HEADER_NAME("HeaderName"),
	HEADER_COLUMN_COUNT("HeaderColumnCount"),
	ASSESSMENT("Assessment"),
	GRADE("Grade"),
	RECORD("Record"),
	EMAIL("EmailAddress"),
	USER_IDENTIFIER("UserIdentifier"),
	FILE_FORMAT("FileFormat"),
	DISPLAY_IDENTIFIER("Display Identifier"),
	ORG_TYP_CODE("OrganizationTypeCode"),
	UPLOAD_ORG_TYPE_CODE("organizationTypeCode"),
	UPLOAD_ORG_IDENTIFIER("displayIdentifier"), 
	UPLOAD_ORG_NAME("organizationName"),
	UPLOAD_ORG_PARENT_IDENTIFIER("parentDisplayIdentifier"),
	UPLOAD_ORG_TOP_LEVEL_IDENTIFIER("contractOrgDisplayId"),
    /**
	 * Prasanth :  US16252 : To upload data file     
	 */
	UPLOAD_PRIMARY_ROLE_IDENTIFIER("primaryRole"),
	UPLOAD_PRIMARY_ASSESSMENT_PRGM_IDENTIFIER("primaryAssessmentProgram"),
	
	UPLOAD_SECONDARY_ROLE_IDENTIFIER("secondaryRole"),
	EDUCATOR_OR_USER_IDENTIFIER("EducatorOrUserIdentifier"),
	FIELD_EDUCATOR_IDENTIFIER("educatorIdentifier"),
	EDUCATOR_SCHOOL_IDENTIFIER("educatorSchoolIdentifier"),
	ENROLLMENT("enrollment"),
	STUDENT("student"),
	ATTRIBUTE_NAME("Attribute_Name"),
	ATTRIBUTE_VALUE("Attribute_Value"),
	ATTRIBUTE_NAME_ATTRIBUTE_CONTAINER("Attribute_Name,Attribute_Container Mapping"),
	TEST_ID("TestID"),
	TESTID_STATESTUDENTID_ORGID("TestID, StateStudentID, OrganizationID Mapping"),
	TEST_SUBJECT("Test Subject"),
	SYSTEM_ERROR("System Error"),
	ACCESS_ISSUE("Access Issue"),
	FILE_READ_ISSUE("File Read"),
	CURRENT_SCHOOL_YEAR("Current School Year"),
	CONTENT_AREA("Content Area"),
	COURSE_AREA("Course"),
	SCALE_SCORE("Scale Score"),
	EXIT_DATE("Exit Date"),
	EXIT_REASON("Exit Reason"),
	SCHOOL_YEAR("School Year"),
	TEST_TYPE("Test Type"),
	SUBJECT("Subject"),
	RESIDENT_DISTRICT_IDENTIFIER("Residence_District_Identifier"),
	SCHOOL_ENTRY_DATE("School Entry Date"),
	ESOL_PROGRAM_ENDING_DATE("ESOL_Program_Ending_Date"),
	ESOL_PROGRAM_ENTRY_DATE("ESOL_Program_Entry_Date"),
	DATE_OF_BIRTH("Date of Birth"),
	REMOVE_FROM_ROSTER("Remove from roster"),
	STATE_STUDENT_IDENTIFIER("State Student Identifier"),
	USER_LAST_NAME("LastName"),
	USER_FIRST_NAME("FirstName"),
	ID_NUMBER("IDNumber"),
	RT_COMPLETE("RT Complete"),
	RT_COMPLETE_DATE("RT Complete Date"),
	SCORING_ITEMS("var"),
	SCORING_ITEMS_REASONS("nonScoreReason");
	private String name;
	
	FieldName(String nam){
		this.setName(nam);
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return name;
	}

}
