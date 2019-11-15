package edu.ku.cete.util;

import java.util.ArrayList;
import java.util.List;

/*
 * Changed during US16344 : for Extracting reports on TEst Form assign to TestCollection for quality check
 */
public enum DataReportTypeEnum {
	
	TRAINING_STATUS(
		(short) 1, "Training Status",
		"Training Status by user for required and optional modules.",
		"PD", "TrainingStatus", "Professional_Development_Training_Status", "false"),
	CURRENT_ENROLLMENT(
		(short) 2, "Current Enrollment",
		"Current enrollment information for active students.",
		"DE", "CURRENT_ENROLLMENT", "Kite_Enrollment_Extract", "false"),
	ACCESSIBILITY_PROFILE(
		(short) 3, "PNP Settings",
		"Personal Needs and Preferences (PNP) settings by student.",
		"DE", "PNP", "Educator_Portal_PNP", "false"),
	ROSTER(
		(short) 4,"Roster",
		"Student assignment by educator and subject.",
		"DE", "ROSTER", "Kite_Roster_Extract", "false"),
	USERS(
		(short) 5, "Users",
		"Educator Portal users and their associated role(s).",
		"DE", "USERS", "Kite_User_Extract", "false"),
	QUESTAR(
		(short) 6, "Questar Pre-ID",
		"Students requiring Paper/pencil, Braille, or Large Print tests.",
		"DE", "QUESTAR_PREID", "Kite_Questar_Extract", "false"),
	TEC(
		(short) 7, "Test Records",
		"Student test types and subjects.",
		"DE", "TEC_RECORDS", "Kite_TEC_Extract", "false"),
	DLM_TEST_STATUS(
		(short) 8, "DLM Test Administration Monitoring", 
		"Testlets assigned, in progress, and completed by subject, and student.",
		"DE", "DLM_TEST_ADMIN", " Kite_DLM_Test_Administration_Status_Extract", "false"),
	TRAINING_DETAILS(
		(short) 9, "Training Details",
		"Details by user and module. Facilitated training responses are reported for required modules.",
		"PD", "TrainingDetails", "Professional_Development_Training_Details", "false"),
	PNP_SUMMARY(
		(short) 10, "PNP Setting Counts",
		"Student PNP setting counts by organization.",
		"DE", "PNP_SUMMARY", "Summary_PNP_Profile", "false"),	
	KSDE_TEST_TASC_RECORDS(
		(short) 11, "KSDE Test And TASC Records",
		"All KSDE Test and TASC records received by Kite for a student for the current school year.",
		"DE", "KSDE_TEST_TASC", "KSDE_Test_TASC_Records_Data_Extract", "false"),	
	TEST_ADMIN_KAP_AMP(
		(short) 13, "KAP Test Administration Monitoring",
		"Test administration status by student and subject.",
		"DE", "TEST_ADMIN", "Kite_Test_Administration_Status_Extract", "false"),
	TEST_TICKET_KAP_AMP(
		(short) 14, "Test Tickets",
		"Student login and ticket information for tests.",
		"DE", "TEST_TICKETS", "Kite_Test_Ticket_Extract", "false"),
	KSDE_ELA_AND_MATH_RETURN_FILE(
		(short) 15, "English Language Arts and Mathematics",
		"KSDE English and Math return file", 
		"KE", "UPLOAD_WEBSERVICE", "KSDE_ELA_MATH", "false"),
	KSDE_SOCIAL_STUDIES_RETURN_FILE(
		(short) 16, "Social Studies",
		"KSDE Social Studies return file", 
		"KE", "UPLOAD_WEBSERVICE", "KSDE_SocialStudies", "false"),
	KSDE_SCIENCE_RETURN_FILE(
		(short) 17, "Science",
		"KSDE Science return file",
		"KE", "UPLOAD_WEBSERVICE", "KSDE_Science", "false"),
	DLM_PD_TRAINING_LIST(
		 (short) 18, "Training Management",
		 "DLM user information needed to manage enrollment and training completion",
		 "DE", "DLM PD Tranining List", "DLM_PD_Training_List_Details", "false"),
	TEST_FORM_ASSIGNMENT(
		(short) 19, "Test Form Assignment to Test Collection QC",
		"Test Form assignment to Test Collection Quality check",
		"DE", "TESTFORM_ASIGNMENT", "Test_Form_Assignments_QC", "false"),
	TEST_FORM_MEDIA(
		(short) 20, "Test Form Media Resource File QC",
		"Test Form assignment to Test Collection Quality check",
		"DE", "TESTFORM_MEDIA", "Test_Form_Media_Resource_File_QC", "false"),
	KSDE_DLM_ELA_AND_MATH_RETURN_FILE(
			(short) 21, "DLM English Language Arts and Mathematics",
			"KSDE DLM English and Math return file", 
			"KE", "UPLOAD_WEBSERVICE", "KSDE_DLM_ELA_MATH", "false"),
	KSDE_DLM_MATH_RETURN_FILE(
			(short) 22, "DLM Mathematics",
			"KSDE DLM Math return file", 
			"KE", "UPLOAD_WEBSERVICE", "KSDE_DLM_MATH", "false"),
	KSDE_DLM_ELA_RETURN_FILE(
			(short) 23, "DLM English Language Arts",
			"KSDE DLM English return file", 
			"KE", "UPLOAD_WEBSERVICE", "KSDE_DLM_ELA", "false"),
	STUDENT_USERNAME_PASSWORD(
			(short) 25,
			"Student Login Usernames/Passwords",
			"Student login usernames and passwords by assessment program and organization.",
			"DE", "DATA_EXTRACTS_STU_UNAME_PASSWORD", "Student_Extracts", "false"),
	RESTRICTED_SPECIAL_CIRCUMSTANCE_CODE(
			(short) 26,
	        "Restricted Special Circumstance Code",
	        "Student test sessions with restricted Special Circumstance code selections.",
	        "DE","SPL_CIRCM_CODE_REP","Special_Circumstance", "false"),
	AMP_DATA_EXTRACT(
			(short) 27,
	        "Alaska Data Extract",
	        "Alaska data extract",
	        "AE","CREATE_AMP_EXTRACT","AlaskaStateDataFile", "false"),
	KSDE_CPASS_GKS_RETURN_FILE(
			(short) 28, "CPASS General Knowledge and Skills",
			"KSDE CPASS General Knowledge and Skills return file", 
			"KE", "UPLOAD_WEBSERVICE", "KSDE_CPASS_GKS", "false"),
	KSDE_CPASS_MFG_RETURN_FILE(
			(short) 29, "CPASS Manufacturing",
			"KSDE CPASS Manufacturing return file", 
			"KE", "UPLOAD_WEBSERVICE", "KSDE_CPASS_MFG", "false"),
	KSDE_CPASS_AGFNR_RETURN_FILE(
			(short) 30, "CPASS Agriculture, Food, and Natural Resources",
			"KSDE CPASS Agriculture, Food, and Natural Resources return file", 
			"KE", "UPLOAD_WEBSERVICE", "KSDE_CPASS_AGFNR", "false"),
	KSDE_DLM_SCIENCE_RETURN_FILE(
			(short) 31, "DLM Science",
			"KSDE DLM Science return file", 
			"KE", "UPLOAD_WEBSERVICE", "KSDE_DLM_Science", "false"),
	KSDE_DLM_HISTORY_RETURN_FILE(
			(short) 32, "DLM History",
			"KSDE DLM History return file", 
			"KE", "UPLOAD_WEBSERVICE", "KSDE_DLM_History", "false"),
	DLM_PD_TRAINING_STATUS(
			 (short) 34, "Training Status",
			 "List of DLM users by organization and their training completion status",
			 "DE", "DLM PD Tranining Status", "DLM_PD_Training_Status_Details", "false"),
	SECURITY_AGREEMENT_COMPLETION (
			(short) 35, "Security Agreement Completion", "Security Agreement status by organization",
			"DE", "SECURITY_AGREEMENT", "Security_Agreement_Extract", "false"),
	K_ELPA_Test_Administration (
			(short) 36, "KELPA2 Test Administration Monitoring", "KELPA2 Test Administration Status by Student and Subject",
			"DE", "KELPA2_AGREEMENT", "KELPA2_Test_Administration_Monitoring_Extract", "false"),
	MONITOR_SCORING_ASSIGNMENT(
			(short)37,"Monitor Scoring","Scoring assignments status by student, subject and stage.",
			"DE","MONITOR_SCORING","Kite_Scoring_Assignments_Status_Extract","false"),	
	DLM_BLUEPRINT_COVERAGE (
			(short) 39, "DLM Blueprint Coverage Summary", "Percent of students meeting blueprint criteria, per organization",
			"DE", "DLM_BP_COVERAGE", "DLM_Blueprint_Coverage_Summary", "false"),
	KSDE_KELPA_STATE_RETURN_FILE(
		 (short) 40, "KELPA2 State Report",
		 "KSDE KELPA2 State Return File",
		 "KE", "UPLOAD_WEBSERVICE", "KSDE_KELPA_STATE_REPORT", "false"),
    DLM_GENERAL_RESEARCH_FILE(	
    	 (short)41, "DLM General Research File (GRF)", 
    	 "General Research File from the most recent year with final student assessment results for each rostered subject.",		//DLM_Gen_Research
    	 "DE", "DLM_Gen_Research", "DLM_General_Research_File_Extract","false"),
   DLM_SPECIAL_CIRCUMSTANCES_FILE(	
		 (short)42,"DLM Special Circumstance File",
		 "List of students who were assigned special circumstance codes during previous year’s assessments.",		//DLM_SPEC_CIRCUM
		 "DE","DLM_SPEC_CIRCUM","DLM_Special_Circumstances_Extract","false"),
   DLM_INCIDENT_FILE(	
		 (short)43,"DLM Incident File",
		 "List of students who may have experienced test administration incidents during previous year’s assessments.",		//DLM_INCIDENT
		 "DE","DLM_INCIDENT","DLM_Incident_Extract","false"),
   ORGANIZATIONS(	
				 (short)44,"Organization Records",
				 "Organization information needed to manage districts and schools",		
				 "DE","ORGANIZATIONS","Kite_Organization_Extract","false"),
   FCS_REPORT(
			(short) 45, "First Contact Survey File", "Current First Contact survey settings by student",
			"DE", "FCS_REPORT", "First_Contact_Survey_Extract", "false"),
   TESTING_READINESS(
			(short) 46, "Testing Readiness", "Student login, test records and PNP settings.",
			"DE", "TESTING_READINESS", "Testing_Readiness", "false"),
   DLM_EXIT_STUDENT(
			(short) 47, "DLM Exited Students", "List of students who were exited anytime during the previous year's assessments",
			"DE", "DLM_EXIT_STUDENT", "DLM_Exited_Students_File_Extract", "false"),
	ACCESSIBILITY_PROFILE_ABRIDGED(
			(short) 48, "PNP Settings (Abridged)",
			"Personal Needs and Preferences (PNP) settings by student.",
			"DE", "PNP_ABRIDGED", "Educator_Portal_PNP", "false"),
	TEST_ADMIN_ISMART(
			(short) 50, "I-SMART Test Administration Monitoring", 
			"Testlets assigned, in progress, and completed by subject, and student.",
			"DE", "ISMART_TEST_ADMIN", "Kite_ISMART_Test_Administration_Status_Extract", "false"),	
	KAP_STUDENT_SCORES(
			(short) 38, "KAP Student Scores Current Students",
			"Year to year student scores by subject for currently enrolled students.",
			"DE", "DATA_EXTRACTS_KAP_STUDENT_SCORES", "KAP_Student_Scores_CURRENT", "false"),
	KAP_STUDENT_SCORES_SPECIFIED_STUDENT(
			(short) 51, "KAP Student Scores Specified Student",
			"Year to year student scores by subject for specified student state ID.",
			"DE", "DATA_EXTRACTS_KAP_STUDENT_SCORES", "KAP_Student_Scores_SPECIFIED", "false"),
	KAP_STUDENT_SCORES_TESTED_STUDENTS(
			(short) 52, "KAP Student Scores Tested Students",
			"Year to year student scores by subject for students tested in the most current report year.",
			"DE", "DATA_EXTRACTS_KAP_STUDENT_SCORES", "KAP_Student_Scores_TESTED", "false"),
	TEST_ADMIN_PLTW_AMP((short) 53,"PLTW Test Administration Monitoring","Test administration status by student and subject.","DE"
			,"DATA_EXTRACTS_TEST_ADMIN_PLTW","PLTW_Test_Administration_Status_Extract","false"),
	PLTW_TESTING_READINESS(
			(short) 54, "PLTW Testing Readiness", "Student login, rostered courses, and PNP.",
			"DE", "PLTW_TESTING_READ", "PLTW_Testing_Readiness", "false"),

	DLM_CUSTOM_FILES(
			(short) 55, "Custom Files", "To Upload DLM Custom Files",
			"DE", "VIEW_STATE_SPECIFIC_FILES", "DISPLAY_state_specific_files_link", "false"),

	K_ELPA_CURRENT_STUDENT_SCORES(
			(short) 56, "KELPA2 Student Scores Current Students", "Year to year student scores for currently enrolled students", 
			"DE", "DATA_EXTRACTS_KELPA_STUDENT_SCORES","KELPA_Student_Scores_CURRENT", "false");
	
	private Short id;
	private String name;
	private String description;
	
	// Area of the application that the report belongs to.
	// These are generally meant for controller use, and the service
	// layer just needs the IDs to retrieve from the database
	// PD - Professional Development
	// DE - Data Extracts
	// KE - KSDE Extracts
	private String appCode;
	
	// used for permission purposes, unlikely that these should ever change
	private String permissionCode;
	
	// used for file name purposes, also unlikely to change
	private String fileName;
	private String queued;
	//used for data dictionary
    private String dataDetailFileName;
    private String dataDetailFileLocation;
    private String specialDataDetailFileLocation;
    private String specialDataDetailFileName;
    private boolean specialDataDetailActiveFlag;
    private boolean dataDetailActiveFlag;
	
	DataReportTypeEnum(Short id, String name,
			String description, String appCode, String permissionCode, String fileName, String queued) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.appCode = appCode;
		this.permissionCode = permissionCode;
		this.fileName = fileName;
		this.queued = queued;
	}
	
	public Short getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getAppCode() {
		return appCode;
	}
	
	public String getPermissionCode() {
		return permissionCode;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public String getQueued() {
		return queued;
	}
	
	
	/**
	 * @return the dataDetailFileName
	 */
	public String getDataDetailFileName() {
		return dataDetailFileName;
	}

	/**
	 * @param dataDetailFileName the dataDetailFileName to set
	 */
	public void setDataDetailFileName(String dataDetailFileName) {
		this.dataDetailFileName = dataDetailFileName;
	}

	/**
	 * @return the dataDetailFileLocation
	 */
	public String getDataDetailFileLocation() {
		return dataDetailFileLocation;
	}

	/**
	 * @param dataDetailFileLocation the dataDetailFileLocation to set
	 */
	public void setDataDetailFileLocation(String dataDetailFileLocation) {
		this.dataDetailFileLocation = dataDetailFileLocation;
	}

	/**
	 * @return the specialDataDetailFileLocation
	 */
	public String getSpecialDataDetailFileLocation() {
		return specialDataDetailFileLocation;
	}

	/**
	 * @param specialDataDetailFileLocation the specialDataDetailFileLocation to set
	 */
	public void setSpecialDataDetailFileLocation(
			String specialDataDetailFileLocation) {
		this.specialDataDetailFileLocation = specialDataDetailFileLocation;
	}

	/**
	 * @return the specialDataDetailFileName
	 */
	public String getSpecialDataDetailFileName() {
		return specialDataDetailFileName;
	}

	/**
	 * @param specialDataDetailFileName the specialDataDetailFileName to set
	 */
	public void setSpecialDataDetailFileName(String specialDataDetailFileName) {
		this.specialDataDetailFileName = specialDataDetailFileName;
	}

	/**
	 * @return the specialDataDetailActiveFlag
	 */
	public boolean isSpecialDataDetailActiveFlag() {
		return specialDataDetailActiveFlag;
	}

	/**
	 * @param specialDataDetailActiveFlag the specialDataDetailActiveFlag to set
	 */
	public void setSpecialDataDetailActiveFlag(boolean specialDataDetailActiveFlag) {
		this.specialDataDetailActiveFlag = specialDataDetailActiveFlag;
	}

	/**
	 * @return the dataDetailActiveFlag
	 */
	public boolean isDataDetailActiveFlag() {
		return dataDetailActiveFlag;
	}

	/**
	 * @param dataDetailActiveFlag the dataDetailActiveFlag to set
	 */
	public void setDataDetailActiveFlag(boolean dataDetailActiveFlag) {
		this.dataDetailActiveFlag = dataDetailActiveFlag;
	}

	public static DataReportTypeEnum getById(Short id) {
		if (id != null) {
			for (DataReportTypeEnum rule : DataReportTypeEnum.values()) {
				if (rule.getId().equals(id)) {
					return rule;
				}
			}
		}
		return null;
	}
	
	public static DataReportTypeEnum getByName(String name) {
		if (name != null) {
			for (DataReportTypeEnum rule : DataReportTypeEnum.values()) {
				if (rule.getName().equalsIgnoreCase(name)) {
					return rule;
				}
			}
		}
		return null;
	}
	
	public static List<Short> getDataExtractReportTypeIds(){
		List<Short> typeIds = new ArrayList<Short>();
		for (DataReportTypeEnum rule : DataReportTypeEnum.values()) {
			if("DE".equals(rule.getAppCode())){
				typeIds.add(rule.getId());
			}
		}
		return typeIds;
	}
	
}