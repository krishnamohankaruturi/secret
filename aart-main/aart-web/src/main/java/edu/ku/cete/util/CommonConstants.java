package edu.ku.cete.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author vittaly
 *
 */
public class CommonConstants {
	public static final String STUDENTS_TEST_SECTION_STATUS_STUDENTS_TEST_ID = "studentsTestId";
	public static final String STUDENTS_TEST_SECTION_STATUS_STUDENT_NAME = "studentName";
	public static final String STUDENTS_TEST_SECTION_STATUS_OVERALL_STATUS = "overallStatus";
	public static final String STUDENTS_TEST_SECTION_STATUS_INPROGRESSTIMEDOUT = "inProgressTimedOut";
	
	public static final String CSV_VERTICLE_RECORDS_COLUMNS_HEADER_ATTRIBUTE_NAME = "attributeName";
	public static final String CSV_VERTICLE_RECORDS_COLUMNS_HEADER_ATTRIBUTE_VALUE = "attributeValue";
	
	public static final String COMMA_DELIM = ",";
	public static final String HASH_DELIM = "#";
	public static final String ASSESSMENT_PROGRAM_PARTICIPATION_DELIM = "##";
	public static final String S3_KEY_SEPARATOR = "/";
	
	//For WebService ON/OFF switch
	public static final String KANSAS_SCHEDULED_WEB_SERVICE_SWITCH_ON = "ON";

	//For WebService Auto Registration ON/OFF switch
	public static final String KANSAS_WEB_SERVICE_AUTO_REGISTRATION_SWITCH_ON = "ON";
		
	//Assessment Program Participation constants
	public static final String RESPONSE_CONTENT_TYPE_CSV = "text/csv";
	public static final String RESPONSE_HEADER_CONTENT_DISPOSITION = "Content-Disposition";
	public static final String RESPONSE_HEADER_ATTACHMENT_FILENAME_STRING = "attachment; filename=";
	public static final String RESPONSE_HEADER_ATTACHMENT_FILENAME = "AssessmentProgramParticpation_" + DateUtil.format(new Date(), "MM_dd_yyyy") + ".csv";
	
	public static final String PERCENTILE_DELIM = "%";
	
	//First Contact section constants.
	public static final String FIRST_CONTACT_SECTION_COMMUNICATION = "Communication";
	public static final String FIRST_CONTACT_SECTION_ACADEMIC = "Academic";
	public static final String FIRST_CONTACT_SECTION_ATTENTION = "Attention";
	public static final List<Integer> FIRST_CONTACT_SKIP_PAGES = new ArrayList<Integer> ();
	public static final String FIRST_CONTACT_Q34 = "Q34_";
	public static final String FIRST_CONTACT_NAVIGATION_NEXT = "next";
	public static final String FIRST_CONTACT_NAVIGATION_PREVIOUS = "previous";
	
	public static final String FIRST_CONTACT_PREREQUISITE_AND = "and";
	public static final String FIRST_CONTACT_PREREQUISITE_OR = "or";
	
	//organization type constants.
	public static final Integer ORGANIZATION_TYPE_ID_1 = 1;
	public static final Integer ORGANIZATION_TYPE_ID_2 = 2;
	public static final Integer ORGANIZATION_TYPE_ID_3 = 3;
	public static final Integer ORGANIZATION_TYPE_ID_4 = 4;
	public static final Integer ORGANIZATION_TYPE_ID_5 = 5;
	public static final Integer ORGANIZATION_TYPE_ID_6 = 6;
	public static final Integer ORGANIZATION_TYPE_ID_7 = 7;
	public static final String ORGANIZATION_STATE_TYPE_ID = "ST";
	public static final String ORGANIZATION_AREA_CODE = "AR";
	public static final String ORGANIZATION_BUILDING_CODE = "BLDG";
	public static final String ORGANIZATION_SCHOOL_CODE = "SCH";
	public static final String ORGANIZATION_DISTRICT_CODE = "DT";
	public static final String ORGANIZATION_STATE_CODE = "ST";
	public static final String ORGANIZATION_REGION_CODE = "RG";
	public static final String ORGANIZATION_CONSORTIA_CODE = "CONS";
	    
	//Auto registration constants.
	public static final List<String> AUTO_REGISTRATION_D74_TESTTYPES = new ArrayList<String> (Arrays.asList("1","6","8","A","X","P","L"));
	public static final List<String> AUTO_REGISTRATION_D75_TESTTYPES = new ArrayList<String> (Arrays.asList("1","8","A","L"));
	public static final List<String> AUTO_REGISTRATION_D76_TESTTYPES = new ArrayList<String> (Arrays.asList("1","6","8","X","R","P","L"));
	public static final List<String> AUTO_REGISTRATION_D77_TESTTYPES = new ArrayList<String> (Arrays.asList("1","6","8","X","R","P","L"));
	public static final List<String> AUTO_REGISTRATION_D78_TESTTYPES = new ArrayList<String> (Arrays.asList("1","6","8","X","R","P","L"));
	
	//Professional Development constants.
	public static final String  PD_USER_MODULE_UNENROLLED_CATEGORY_CODE = "UNENROLLED";
	public static final String  PD_USER_MODULE_ENROLLMENT_STATUS_CATEGORY_NAME = "enrollmentStatusName";
	
	//Scoring 
	public static final String  SCORING_MONITOR_SCORES = "monitorStuentScores";
	public static final String  SCORING_MONITOR_SCORE_TEST = "scoreTest";
	
	//GRF
	public static final Long GRF_LEVEL_1 = 1l;
	public static final Long GRF_LEVEL_2 = 2l;
	public static final Long GRF_LEVEL_3 = 3l;
	public static final Long GRF_LEVEL_4 = 4l;
	public static final Long GRF_LEVEL_5 = 5l;
	public static final Long GRF_LEVEL_6 = 6l;
	public static final Long GRF_LEVEL_7 = 7l;
	public static final Long GRF_LEVEL_8 = 8l;
	public static final Long GRF_LEVEL_9 = 9l;
	
	public static final Long GRF_AGGREGATE_REPORT_SCORE_ZERO = 0l;
	public static final String GRF_AGGREGATE_REPORT_NO_SCORE = "n/a";
	
	//KELPA2 Level constants.
	public static final Long KELPA_STUDENT_REPORT_LEVEL_1 = 1l;
	public static final Long KELPA_STUDENT_REPORT_LEVEL_2 = 2l;
	public static final Long KELPA_STUDENT_REPORT_LEVEL_3 = 3l;
	
	public static final String KELPA_STUDENT_REPORT_TEST_STATUS_COMPLETE = "complete";
	public static final String KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESS = "inprogress";
	public static final String KELPA_STUDENT_REPORT_TEST_STATUS_INPROGRESSTIMEDOUT = "inprogresstimedout";
	public static final String KELPA_STUDENT_REPORT_TEST_STATUS_UNUSED = "unused";
	public static final String KELPA_STUDENT_REPORT_SCORING_STATUS = "COMPLETED";
	public static final String KELPA_STUDENT_REPORT_PDF_STATIC_CONTENT_PARAGRAPH_2 = "KEPLA_P_2";
	
	public static final String KELPA_STUDENT_REPORT_PROGRESS_STATUS_PROFICIENT_CODE = "PF";
	public static final String KELPA_STUDENT_REPORT_PROGRESS_STATUS_PROGRESS_NOT_DEMONSTRATED_CODE = "PND";
	public static final String KELPA_STUDENT_REPORT_PROGRESS_STATUS_STATISFACTORY_PROGRESS_CODE = "SP";
	public static final String KELPA_STUDENT_REPORT_PROGRESS_STATUS_BLANK_CODE = "BLANK";
	
	
	//Upload Grf Process
	public static final String EMPTY = "";	
	public static final String SEPARATOR = ",";	
	public static final String SEPARATOR_SLASH = "\"";	
	public static final String NEXT_LINE = "\n";
	public static final String LINE_NUMBER = "linenumber";
	public static final String BATCH_UPLOAD_ID = "batchuploadid";
	public static final String GRF_UPLOAD_CSV_FILE_FOLDER_PATH = "GRF_UPLOAD_CSV";
	public static final String GRF_UPLOAD_FILE_FOLDER_PATH = "GRF_UPLOAD_TEMP_FILE";
	public static final String GRF_UPLOAD_TEMP_FILE_NAME = "grf_temp_file_name";
	public static final String FILE_CSV_EXTENSION = "csv";
	public static final String FILE_XLSX_EXTENSION = "xlsx";

	public static final String FILE_PDF_EXTENSION = "pdf";
	
	//APP Configuration Constants
	public static final String ESOL_PARTICIPATION_CODE_ATTRIBUTE_TYPE = "st_esolparticipationcode";
	public static final String GENERATION_ATTRIBUTE_TYPE = "student_generation";
	public static final String GENDER_ATTRIBUTE_TYPE = "st_gender";
	public static final String HISPANIC_ETHINICITY_ATTRIBUTE_TYPE = "st_hispanicethnicity";
	public static final String GIFTED_STUDENT_ATTRIBUTE_TYPE = "st_giftedstudent";
	public static final String CURRENT_SCHOOL_YEAR = "current_school_year";
	
	//Add Student page State Student Identifier Error messages
	public static final String STATE_STUDENT_IDENTIFIER_EXISTS_BUT_NOT_ACTIVE = "add_student_exist_not_active_noedit_perm";
	public static final String STATE_STUDENT_IDENTIFIER_NOT_EXISTS = "success";
	public static final String STATE_STUDENT_IDENTIFIER_SHOW_ACTIVATE = "add_student_exist_not_active_find";
	public static final String STATE_STUDENT_IDENTIFIER_EXISTS_WITH_DIFFERENT_ORG = "add_student_exist_active_diff_org";
	public static final String STATE_STUDENT_IDENTIFIER_EXISTS_ACTIVE = "add_student_exist_active";
	public static final String STATE_STUDENT_IDENTIFIER_EXISTS_WITH_DIFFERENT_ASSESSMENTPROGRAM = "add_student_exist_active_diff_assessment_program";
	
	public static final String APP_CONFIGURATION_ATTR_TYPE_FOR_ADD_STUDENT_WARNING_MESSAGE = "addstudentwarningmsg";
	public static final String ERROR_ADD_STATE_STUDENT_IDENTIFIER_PAGE = "error";
	
	//Assessment Program Constants
	public static final String ASSESSMENT_PROGRAM_PLTW = "PLTW";
	public static final String ASSESSMENT_PROGRAM_DLM = "DLM";
	public static final String ASSESSMENT_PROGRAM_KELPA2 = "KELPA2";
	public static final String ASSESSMENT_PROGRAM_CPASS = "CPASS";
	public static final String ASSESSMENT_PROGRAM_I_SMART = "I-SMART";
	public static final String ASSESSMENT_PROGRAM_I_SMART2 = "I-SMART2";
	public static final String ASSESSMENT_PROGRAM_SCORING = "SCORING";
	public static final String ASSESSMENT_PROGRAM_NON_GROUP="PLTW,DLM,I-SMART,I-SMART2";

	public static final String KAP_STUDENT_REPORT_ASSESSMENTCODE = "KAP";
	public static final String KELPA2_STUDENT_REPORT_ASSESSMENTCODE = "KELPA2";
	public static final String PLTW = "PLTW";	
	
	//SSO Errors
	public static final String APP_CONFIGURATION_ATTR_TYPE_FOR_SSO_ERROR_MESSAGES = "sso_error_conditions";
	public static final String APP_CONFIGURATION_SSO_INACTIVE_USER = "sso_user_inactive";
	public static final String APP_CONFIGURATION_SSO_INACTIVE_ORGS = "sso_orgs_inactive";
	public static final String APP_CONFIGURATION_SSO_USER_NOT_FOUND = "sso_user_not_found";
	public static final String APP_CONFIGURATION_SSO_GENERIC = "sso_auth_error";

	//Groups Code Contants
	public static final String GROUP_CODE_TEACHER = "TEA";
	public static final String GROUP_CODE_PROCTOR = "PRO";
	
	//Extract File Name
	public static final String Kite_Scoring_Assignments_Status_Extract="Kite_Scoring_Assignments_Status_Extract";
	public static final String  KSDE_KELPA_STATE_REPORT="KSDE_KELPA_STATE_REPORT";
	

	public static final Long KAP_EXTRACT_TYPE_ID_38 = 38l;
	public static final Long KAP_EXTRACT_TYPE_ID_52 = 52l;
	
	public static final String DATA_EXTRACT_REPORT_DISPLAYIDENTIFIER = "KS";
	public static final String DATA_EXTRACT_REPORT_STUDENT = "Student";
	public static final int KAP_EXTRACT_STARTING_YEAR = 2016; 
	public static final String KAP_EXTRACT_METAMETRIC_STARTING_YEAR = "kap_score_extract_metametric_starting_year";
	public static final String SKIP_STUDENT_CALC_FOR_UNCOMPLETED_STAGES = "skip_student_calculation_for_uncompleted_stages";

	public static final String ENROLLMENT_RANDOMIZATION_TYPE = "enrollment";
	public static final String DASHBOARD_MESSAGE_RECORD_TYPE = "Student";
	public static final String PLTW_BATCH_AUTO_MESSAGE_TYPE = "pltw_batch_auto_error_message";
	public static final String PLTW_MISSING_COMPREHENSIVE_RACE = "missing_comrehensiverace";
	public static final String PLTW_MISSING_HISPANIC_ETHNICITY = "missing_hispanic_ethnicity";
	public static final String PLTW_MISSING_RACE_ETHNICITY = "missing_race_ethnicity";
	public static final String PLTW_STUDENT_REPORT_SCORING_STATUS = "COMPLETED";
	public static final String PD_REPORT_STATUS = "PD_REPORT_STATUS";
	
	public static final String JSON_TIME_FORMAT = "yyyy-MM-dd HH:mm a z";

	public static final String ISMART_SCIENCE = "IS-Sci";
	public static final String ISMART_2_SCIENCE = "IS2-Sci";
	
	public static final String CONTENT_AREA_ABBREVIATED_NAME_ELA = "ELA";
	public static final String CONTENT_AREA_ABBREVIATED_NAME_MATH = "M";
	public static final String CONTENT_AREA_ABBREVIATED_NAME_SCIENCE = "Sci";
	public static final String CONTENT_AREA_ABBREVIATED_NAME_SOCIAL_STUDIES = "SS";
	public static final String STRING_ZERO = "0";
	public static final String STRING_ONE = "1";
	
	public static final String PredictiveSchoolYear = "PredictiveSchoolYear";

	//Report Access Constants
	public static final String GENERAL_REPORT_ACCESS_FOR_DLM = "GEN_DLM";
	public static final String GENERAL_REPORT_ACCESS = "GEN";
	public static final String INSTRUCTIONALlY_EMBEDDED_REPORT_ACCESS = "IE";
	public static final String YEAR_END_REPORT_ACCESS = "YE";
	public static final String STUDENT_ARCHIEVE_REPORT_ACCESS  = "SA";
	public static final String EXTRACTS_ACCESS = "EX";
	public static final String COLON = ":";
	public static final String PERM_ACCESS_STATUS_ZERO = " : 0";
	public static final String PERM_ACCESS_STATUS_ONE = " : 1";
	public static final String PERM_ACCESS_STATUS_TWO = " : 2";
	
	public static final String TwoOrMoreRaceCode = "11111";
	public static final String RegExpTwoOrMoreRace = "0*1+0*1+0*1*";
	
	public static final String COMPLEXITY_BAND_FOUNDATIONAL = "FOUNDATIONAL";
	public static final String COMPLEXITY_BAND_EMERGENT = "EMERGENT";
	public static final String COMPLEXITY_BAND_CONVENTIONAL = "CONVENTIONAL";
	public static final String COMPLEXITY_BAND_1 = "BAND_1";
	public static final String COMPLEXITY_BAND_2 = "BAND_2";
	public static final String COMPLEXITY_BAND_3 = "BAND_3";
	public static final String COMPLEXITY_BAND_4 = "BAND_4";
	

	public static final String STRING_TRUE = "true";
	public static final String STRING_FALSE = "false";
	public static final String FIELDSPECIFICFICATION_FIELDTYPE_STRINGTYPE="string";
	public static final String FIELDSPECIFICFICATION_MAPPEDNAME="mappedName";
	public static final String FIELDSPECIFICFICATION_FIELDNAME="fieldName";
	public static final String FIELDSPECIFICFICATION_FIELDTYPE="fieldType";
	public static final String FIELDSPECIFICFICATION_REJECTIFINVALID="rejectIfInvalid";
	public static final String FIELDSPECIFICFICATION_SHOWERROR="showError";
	public static final String FIELDSPECIFICFICATION_REJECTIFEMPTY="rejectIfEmpty";
	public static final String FIELDSPECIFICFICATION_ALLOWABLEVALUES = "allowableValues";
	public static final String UPLOAD_STATUS_FAILED = "FAILED";
	public static final String ROLES_PERMISSION_UPLOAD_ALLOWABLEVALUES_STRING =  "{'',n/a,x}";
	public static final String ROLES_PERMISSION_UPLOAD_NOTAVAIL_STRING =  "N/A";
	public static final String ROLES_PERMISSION_UPLOAD_HAVEPERMISSION_STRING =  "X";
	public static final String UPLOAD_ALL_STRING =  "ALL";
	public static final String GROUP_GLOBALSYSTEMADMIN =  "GSAD";

	// Kelpa2 Access Constants
	public static final String KELPA2_EXTRACT_STARTING_YEAR = "KELPA2_EXTRACT_START_YEAR"; 
	public static final String KELPA2_STUDENT_REPORT_TEST_STATUS_UNUSED = "unused";
	public static final String KELPA2_STUDENT_REPORT_TEST_STATUS_NOT_TESTED = "Not Tested";
	public static final String KELPA2_STUDENT_REPORT_SCORING_STATUS_COMPLETED = "COMPLETED";

	// Common headers for kelpa and kap access
	public static final String STATE_STUDENT_IDENTIFIER = "State_Student_Identifier";
	public static final String STUDENT_LEGAL_LAST_NAME = "Student_Legal_Last_Name";
	public static final String STUDENT_LEGAL_FIRST_NAME = "Student_Legal_First_Name";
	public static final String STUDENT_LEGAL_MIDDLE_NAME = "Student_Legal_Middle_Name";
	public static final String CURRENT_ENROLLED_DISTRICT_IDENTIFIER = "Current_Enrolled_District_Identifier";
	public static final String CURRENT_ENROLLED_DISTRICT_NAME = "Current_Enrolled_District_Name";
	public static final String CURRENT_ENROLLED_ACCOUNTABILITY_SCHOOL_IDENTIFIER = "Current_Enrolled_Accountability_School_Identifier";
	public static final String CURRENT_ENROLLED_ACCOUNTABILITY_SCHOOL_NAME = "Current_Enrolled_Accountability_School_Name";
	public static final String CURRENT_ENROLLED_ATTENDANCE_SCHOOL_IDENTIFIER = "Current_Enrolled_Attendance_School_Identifier";
	public static final String CURRENT_ENROLLED_ATTENDANCE_SCHOOL_NAME = "Current_Enrolled_Attendance_School_Name";
	public static final String CURRENT_ENROLLED_GRADE = "Current_Enrolled_Grade";

	public static final String GRADE_ = "Grade_";
	public static final String REPORT_DISTRICT_IDENTIFIER_ = "Report_District_Identifier_";
	public static final String REPORT_DISTRICT_NAME_ = "Report_District_Name_";
	public static final String REPORT_SCHOOL_IDENTIFIER_ = "Report_School_Identifier_";
	public static final String REPORT_SCHOOL_NAME_ = "Report_School_Name_";
	public static final String LISTENING_SCALE_SCORE_ = "Listening_Scale_Score_";
	public static final String LISTENING_PERFORMANCE_LEVEL_= "Listening_Performance_Level_";
	public static final String READING_SCALE_SCORE_ = "Reading_Scale_Score_";
	public static final String READING_PERFORMANCE_LEVEL_= "Reading_Performance_Level_";
	public static final String SPEAKING_SCALE_SCORE_ = "Speaking_Scale_Score_";
	public static final String SPEAKING_PERFORMANCE_LEVEL_ = "Speaking_Performance_Level_";
	public static final String WRITING_SCALE_SCORE_ = "Writing_Scale_Score_";
	public static final String WRITING_PERFORMANCE_LEVEL_ = "Writing_Performance_Level_";
	public static final String OVERALL_PROFICIENCY_LEVEL_ = "Overall_Proficiency_Level_";

	public static final String GEN_WRITING_RESPONSE = "GEN_ST_WRITING";
	public static final String ALT_MONITORING_SUMMARY = "ALT_MONITORING_SUMMARY";
	
	public static final String API_HEADER_CHECKSUM = "X-Checksum";
	public static final String API_HEADER_APIKEY = "X-API-KEY";
	public static final String API_HEADER_APPID = "X-APP-ID";
	public static final String APPCONFIG_ATTRIBUTE_TYPE_CHECK_HASHES = "api-check-hashes";
	public static final String APPCONFIG_ATTRIBUTE_TYPE_APP_ID = "app-id";
	public static final String APPCONFIG_ATTRIBUTE_TYPE_API_KEY = "api-key";
	public static final String APPCONFIG_ATTRIBUTE_TYPE_API_USER = "api-user";
	
	public static final String DLM_SPECIAL_CIRCUMSTANCE = "DLM Special Circumstance File:Available with score report delivery.";
	public static final String DLM_EXITED_STUDENTS = "DLM Exited Students:Available with score report delivery.";

	public static final String ESOL_PARTICIPATION_CODE_ASSESSMENT_PROGRAM = "assessment_program_esol_code_4or7and8";
	
	//IAP Page
	public static final String IAP_StudentExistNotActive = "This student already exists but is not active. Please use Find Student to activate the student.";
	public static final String IAP_StudentIDNotExistNorActive = "A student with the entered State Student Identifier already exists but is not active. Please see your district or state coordinator to activate this student for the current school year.";
	public static final String IAP_StudentNotInDistrict = "A student with the entered State Student Identifier already exists but is not found in your organization. Please resolve with your state coordinator.";
	public static final String IAP_StudentNotInSchool = "A student with the entered State Student Identifier already exists but is not found in your organization. Please resolve with your district or state coordinator.";
	public static final String IPA_Search_PaginationLimit = "IPA_Search_Pagination";
	
	public static final String IAP_STATUS_CATEGORYTYPE_CODE = "IAP_STATUS";
	public static final String IAP_STATUS_STARTED = "STARTED";
	public static final String IAP_STATUS_COMPLETED_WITH_TESTLET = "COMPLETED_WITH_TESTLET";
	public static final String IAP_STATUS_COMPLETED_WITH_NO_TESTLET = "COMPLETED_WITH_NO_TESTLET";
	public static final String IAP_STATUS_CANCELED = "CANCELED";
		
	//State abbreviations
	public static final String DELAWARE_ABBR_NAME = "DE";
	public static final String DC_ABBR_NAME = "DC";
	
	//added for brd F933 Adding value not selected for hispanic ethincity dropdown
	public static final String HISPANIC_CODE_ASSESSMENT_PROGRAM = "st_Hispanic_Ethnicity_Assessment";
	public static final String NOT_SELECTED = "Not Selected";
	
		//F915 Exit code by state
	public static final String KIDS_EXIT_ASS_PGM = "KIDS_EXIT_ASS_PGM";
	
	//F956
	public static final String STATE_STUDENT_IDENTIFIER_LENGTH_ERROR = "STATE_STUDENT_IDENTIFIER_LENGTH_ERROR";
	
	public static final String SPRING_TESTING_CYCLE = "SPRING";
	
	public static final String DISTANT_DATE = "1800-01-01";
}
