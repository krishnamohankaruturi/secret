package edu.ku.cete.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.EnrollmentTestTypeSubjectArea;
import edu.ku.cete.domain.EnrollmentTestTypeSubjectAreaExample;
import edu.ku.cete.domain.StudentAssessmentProgram;
import edu.ku.cete.domain.enrollment.SubjectArea;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.EnrollmentsRostersExample;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentJson;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.ksde.kids.result.KidsDashboardRecord;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.model.CcqScoreMapper;
import edu.ku.cete.model.EnrollmentTestTypeSubjectAreaDao;
import edu.ku.cete.model.OperationalTestWindowDao;
import edu.ku.cete.model.StudentAssessmentProgramMapper;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.model.enrollment.EnrollmentsRostersDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.InterimGroupService;
import edu.ku.cete.service.InterimService;
import edu.ku.cete.service.KidsEnrollmentService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ScoringAssignmentServices;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.EnrollmentsRostersService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.AssessmentProgramService;
/**
 * @author s077g656
 *
 */
@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class KidsEnrollmentServiceImpl implements KidsEnrollmentService {

	private Logger LOGGER = LoggerFactory.getLogger(KidsEnrollmentServiceImpl.class);
	
	@Autowired
	private EnrollmentsRostersService enrollmentsRostersService;
	
	@Autowired
	private EnrollmentsRostersDao enrollmentsRostersDao;
	
	@Autowired
	private EnrollmentDao enrollmentDao;

	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private EnrollmentTestTypeSubjectAreaDao enrollmentTestTypeSubjectAreaDao;

	@Autowired
	private StudentService studentService;

	@Autowired
	private OrganizationService organizationService;

	@Autowired
	private AssessmentProgramDao assessmentProgramDao;

	@Autowired
	private GradeCourseService gradeCourseService;

	@Autowired
	private StudentsTestsService studentsTestsService;

	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired 
	private OperationalTestWindowDao operationalTestWindowDao;
	
	@Autowired
	private StudentAssessmentProgramMapper studentAssessmentProgramsDao;

	@Autowired
	private CcqScoreMapper ccqScoreDao;
	
	@Autowired
	private StudentProfileService studentProfileService;
	
	@Autowired
	private InterimGroupService interimGroupService;
	
	@Autowired
	private ScoringAssignmentServices scoringAssignmentService;
	
	@Autowired	
	private ContentAreaService contentAreaService;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;

	@Autowired
	private StudentAssessmentProgramMapper studentAssessmentProgramMapper;
	
	@Value("${studentstests.stage1.completed}")
	private String STAGE1_COMPLETED;

	@Value("${studentstests.stage2.completed}")
	private String STAGE2_COMPLETED;

	@Value("${studentstests.nostage}")
	private String NO_STAGE;

	@Value("${kidRecord.notProcessedCode}")
	private String NOT_PROCESSED;
	
    @Value("${studentstests.stage.exists}")
    private String STAGE_EXISTS;
    
    @Value("${studentstests.all.stages.completed}")
    private String ALL_STAGES_COMPLETED;
	
    @Value("${humanscoring.domains}")
    private String HUMAN_SCORING_DOMAINS;
    
    @Autowired
	private InterimService interimService;
    
    @Value("${kids.errorMessage.type.notification}")
	private String MESSAGE_TYPE_NOTIFICATION;
	
	@Value("${kids.errorMessage.type.error}")
	private String MESSAGE_TYPE_ERROR;

	@Autowired
	private AppConfigurationService appConfigurationService;
	
	/**
	 * @param kidRecord
	 *            {@link KidRecord}
	 * @param user
	 *            {@link User}
	 * @return {@link Enrollment}
	 */
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final KidRecord cascadeAddOrUpdateForKids(KidRecord kidRecord,
			ContractingOrganizationTree contractingOrganizationTree, User user) {

		String errorMessage = StringUtils.EMPTY;
		
		// At lease one valid test type is required
		if (kidRecord.getRecordType().equalsIgnoreCase("TEST") && !isValidTestRecord(kidRecord)) {
			LOGGER.debug("RecordCommonId: " + kidRecord.getRecordCommonId() + " is rejected because no valid test type code is found.");
			updateKidAuditData(kidRecord, "RecordCommonId: "+ kidRecord.getRecordCommonId() + ": Atleast one valid test type code is required.", null);
			kidRecord.setDoReject(true);
			kidRecord.setStatus(NOT_PROCESSED);
			return kidRecord;
		}
		
		// ayp school id is a must for enrollment.
		Organization aypSchool = contractingOrganizationTree.getUserOrganizationTree()
				.getOrganization(kidRecord.getAypSchoolIdentifier());

		if (aypSchool == null) {
			LOGGER.debug("RecordCommonId: " + kidRecord.getRecordCommonId() + " is rejected because the AYP School organization is unrecognized");
			kidRecord.addInvalidField(ParsingConstants.BLANK + FieldName.AYP_SCHOOL, kidRecord.getAypSchoolIdentifier(), true, InvalidTypes.NOT_FOUND);
			kidRecord.setStatus(NOT_PROCESSED);
			return kidRecord;
		}
		kidRecord.getEnrollment().setAypSchoolId(aypSchool.getId());
		kidRecord.setAypSchoolName(aypSchool.getOrganizationName());
		
		// Validate if attendance school is found. Find the organization only from at or below the user's organization.
		Organization attendanceSchool = contractingOrganizationTree.getUserOrganizationTree()
				.getOrganization(kidRecord.getAttendanceSchoolProgramIdentifier());
		
		if (attendanceSchool == null) {
			LOGGER.debug("RecordCommonId: " + kidRecord.getRecordCommonId() + " is rejected because the organization is unrecognized.");
			kidRecord.addInvalidField(ParsingConstants.BLANK + FieldName.ATTENDANCE_SCHOOL,
					kidRecord.getAttendanceSchoolProgramIdentifier(), true, InvalidTypes.NOT_FOUND);
			kidRecord.setStatus(NOT_PROCESSED);
			return kidRecord;
		}
		kidRecord.getEnrollment().setAttendanceSchoolId(attendanceSchool.getId());
		kidRecord.setAttendanceSchoolId(attendanceSchool.getId());
		kidRecord.setAttendanceSchoolName(attendanceSchool.getOrganizationName());
		
		// school year validation
		long contractingOrgSchoolYear = user.getContractingOrganization().getCurrentSchoolYear();
		int enrollmentCurrentSchoolYear = kidRecord.getCurrentSchoolYear();
		if (contractingOrgSchoolYear != enrollmentCurrentSchoolYear) {
			LOGGER.debug("RecordCommonId: " + kidRecord.getRecordCommonId() + " is rejected because the contracting organization school year does not match.");
			kidRecord.addInvalidField(FieldName.CURRENT_SCHOOL_YEAR + ParsingConstants.BLANK,
					kidRecord.getCurrentSchoolYear() + ParsingConstants.BLANK, true, InvalidTypes.IN_CORRECT,
					" does not match the contracting organization school year.");
			kidRecord.setStatus(NOT_PROCESSED);
			return kidRecord;
		}
		
		
		if(!kidRecord.getStateStudentIdentifier().trim().isEmpty() ){
        	String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
        	int allowedLength = Integer.parseInt(stateStudentIdentifierLength);
        		if(kidRecord.getStateStudentIdentifier().trim().length()>allowedLength){
        			String stateStudentIdentifierLengthError = appConfigurationService.getByAttributeCode(CommonConstants.STATE_STUDENT_IDENTIFIER_LENGTH_ERROR);
        			errorMessage = stateStudentIdentifierLengthError.concat(Integer.toString(allowedLength)).concat(".");
        			kidRecord.addInvalidField(FieldName.STUDENT_STATE_ID + ParsingConstants.BLANK,
        					kidRecord.getStateStudentIdentifier() + ParsingConstants.BLANK, true, InvalidTypes.IN_CORRECT,
        					errorMessage);
        			kidRecord.setStatus(NOT_PROCESSED);
        			return kidRecord;
        		}
        }
		
		//Set student state and district
		Organization studentState = organizationService.getContractingOrganization(attendanceSchool.getId());
		kidRecord.getStudent().setStateId(studentState.getId());
		
		// set the currentContextUserId to be used in transaction
		kidRecord.getStudent().setCurrentContextUserId(user.getId());
		
		AssessmentProgram dlmap = assessmentProgramDao.findByAbbreviatedName("DLM");
		AssessmentProgram kapap = assessmentProgramDao.findByAbbreviatedName("KAP");
		if (kidRecord.getRecordType().equalsIgnoreCase("TEST")) {
			//Exit Withdrawal Type validations
			if(StringUtils.isNotEmpty(kidRecord.getExitWithdrawalType())
					|| kidRecord.getExitWithdrawalDate() != null){
				updateKidAuditData(kidRecord, "RecordCommonId: "+ kidRecord.getRecordCommonId()+": ExitWithdrawalType or  ExitWithdrawalDate are received, which are not valid for TEST.", null);
				kidRecord.setStatus(NOT_PROCESSED);
				return kidRecord;
			}
			// Grade validation
			GradeCourse gradeCourse = null;
			GradeCourse grade = null;
			if (kidRecord.getCurrentGradeLevel() != null) {
				gradeCourse = new GradeCourse();
				String strGradeLevel = "0" + kidRecord.getCurrentGradeLevel();
				// add a zero to the beginning and then if there are more than 2 digits remove the first digit
				strGradeLevel = strGradeLevel.length() > 2 ? strGradeLevel.substring(1) : strGradeLevel;
				// store in abbr name to send to query
				gradeCourse.setAbbreviatedName(strGradeLevel);
				gradeCourse.setCurrentContextUserId(user.getId());
				grade = gradeCourseService.getWebServiceGradeCourseByCode(gradeCourse);
			}

			if (grade != null) {
				kidRecord.getEnrollment().setCurrentGradeLevel(grade.getId());
				kidRecord.setGradeCourseAbbreviatedName(grade.getAbbreviatedName());
			}
			
			//Find district information
			OrganizationTreeDetail otd = organizationService.getOrganizationDetailBySchoolId(attendanceSchool.getId());
			if (otd != null && otd.getDistrictId() != 0) {
				kidRecord.getEnrollment().setAttendanceSchoolDistrictId(otd.getDistrictId());
			} else {
				
				errorMessage = "Record is rejected, district not found in OrganizationTreeDetails.";
				LOGGER.debug(errorMessage);
				updateKidAuditData(kidRecord,  errorMessage, null);
				kidRecord.setStatus(NOT_PROCESSED);
				return kidRecord;
			}
			
			//Comprehensive race
			String mappedCompRace = null;
	    	if(StringUtils.isNotEmpty(kidRecord.getStudent().getComprehensiveRace())){
	    		String comprehensiveRace = kidRecord.getStudent().getComprehensiveRace();
	    		if(comprehensiveRace.matches(CommonConstants.RegExpTwoOrMoreRace)) {
	    			comprehensiveRace = CommonConstants.TwoOrMoreRaceCode;
	    		}
	    		mappedCompRace = studentDao.findMappedComprehensiveRaceCode(comprehensiveRace);
	    	}
	    	kidRecord.getStudent().setComprehensiveRace(mappedCompRace);

	    	// Map TestType Subject areas
			List<TestType> testTypes = enrollmentTestTypeSubjectAreaDao.selectAllTestTypes();
			List<SubjectArea> subjectAreas = enrollmentTestTypeSubjectAreaDao.selectAllSubjectAreas();
			Map<String, TestType> testTypesMap = new HashMap<String, TestType>();
			Map<String, SubjectArea> subjectAreaMap = new HashMap<String, SubjectArea>();
			Map<Long, SubjectArea> subjectAreaIdMap = new HashMap<Long, SubjectArea>();
			Map<Long, TestType> testTypeIdMap = new HashMap<Long, TestType>();

			for (TestType testType : testTypes) {
				if (testType.getTestTypeCode().equals("C")) {
					testTypesMap.put(testType.getTestTypeCode(), testType);
					testTypesMap.get("C").getId();
					break;
				}
			}

			for (SubjectArea subjectArea : subjectAreas) {
				subjectAreaMap.put(subjectArea.getSubjectAreaCode(), subjectArea);
				subjectAreaIdMap.put(subjectArea.getId(), subjectArea);
				testTypes = enrollmentTestTypeSubjectAreaDao.selectTestTypesBySubjectCode(subjectArea.getSubjectAreaCode());
				for (TestType testType : testTypes) {
					testTypeIdMap.put(testType.getId(), testType);
					testTypesMap.put(subjectArea.getSubjectAreaCode() + "-" + testType.getTestTypeCode(), testType);
				}
			}
			
			//Find all testtype subjectareas from kidrecord
			List<EnrollmentTestTypeSubjectArea> inTestTypeSubjectAreas = getTestTypeSubjectAreasFromKidRecord(kidRecord,
					testTypesMap, subjectAreaMap);
			List<Long> contentAreaIdsMatchedWithKidsTestRecords = getKidsSubjectAreasAndContentAreaMapping(inTestTypeSubjectAreas);
			
			// Add student if not exists, else update with current values at the end of the process
			Student student = null;
			
			if(kidRecord.getStudent() != null && kidRecord.getStudent().getPrimaryDisabilityCode() != null 
					&& kidRecord.getStudent().getPrimaryDisabilityCode().trim().length() > 0){
				kidRecord.getStudent().setPrimaryDisabilityCode(kidRecord.getStudent().getPrimaryDisabilityCode().trim().toUpperCase());
			}
			
			student = studentService.validateIfStudentExists(kidRecord.getStudent());
			
			if(!student.getSaveStatus().equals(RecordSaveStatus.STUDENT_FOUND)){
				student = studentService.add(kidRecord.getStudent());
			}else{
				populateStudentToUpdate(kidRecord, student);
			}
			if(student == null || student.getId() == 0){
				errorMessage = "RecordCommonId: "+ kidRecord.getRecordCommonId() + ": Issue while creating or finding student in system.";
				LOGGER.debug(errorMessage);
				updateKidAuditData(kidRecord, errorMessage, null);
				kidRecord.setStatus(NOT_PROCESSED);
				return kidRecord;
			}
				
			//identify assessment programs from input TEST record
			getAssessmentProgramKid(kidRecord, studentState, student);
			kidRecord.getStudent().setId(student.getId());
			kidRecord.setStudentId(kidRecord.getStudent().getId());
			kidRecord.getEnrollment().setStudentId(kidRecord.getStudent().getId());
			kidRecord.getStudent().setSourceType(SourceTypeEnum.TESTWEBSERVICE.getCode());
			kidRecord.getEnrollment().setStudent(kidRecord.getStudent());
			kidRecord.getEnrollment()
					.setAttendanceSchoolProgramIdentifier(kidRecord.getAttendanceSchoolProgramIdentifier());
			kidRecord.getEnrollment().setAttendanceSchoolId(attendanceSchool.getId());
			kidRecord.getEnrollment().setCurrentContextUserId(user.getId());
			kidRecord.getEnrollment().setAypSchoolId(aypSchool.getId());
			kidRecord.getEnrollment().setAuditColumnProperties();
			kidRecord.getEnrollment().setSourceType(SourceTypeEnum.TESTWEBSERVICE.getCode());
			kidRecord.getKidsDashboardRecords().clear();
			
			if(historySubjectExists(kidRecord)){
				AssessmentProgram asmpgrm = assessmentProgramDao.findByAbbreviatedName("KAP");
	        	kidRecord.getHistoryProctor().setUniqueCommonIdentifier(kidRecord.getHistoryGovProctorId());
	        	kidRecord.getHistoryProctor().setFirstName(kidRecord.getHistoryGovProctorFirstName());
	        	kidRecord.getHistoryProctor().setSurName(kidRecord.getHistoryGovProctorLastName());
	        	kidRecord.getHistoryProctor().setAssessmentProgramId(asmpgrm.getId());
			}			
			
	        if(isKELPAKid(kidRecord)) {
	        	AssessmentProgram asmpgrm = assessmentProgramDao.findByAbbreviatedName("KELPA2");
	        	kidRecord.getStudent().setAssessmentProgramId(asmpgrm.getId());
	        	kidRecord.getEducator().setUniqueCommonIdentifier(kidRecord.getElpaProctorId());
	        	kidRecord.getEducator().setFirstName(kidRecord.getElpaProctorFirstName());
	        	kidRecord.getEducator().setSurName(kidRecord.getElpaProctorLastName());
	        	kidRecord.getEducator().setAssessmentProgramId(asmpgrm.getId());
	        }
	        
			if (student.getSaveStatus().equals(RecordSaveStatus.STUDENT_FOUND)) {
				boolean rejectKAP = false;
				// Get all enrollments including subject areas on the student
				List<Enrollment> existedEnrollmets = enrollmentDao.findSubjectEnrollmentsByStudentId(student.getId(),
						kidRecord.getCurrentSchoolYear(), kidRecord.getStudent().getStateId());
				//Verify whether any active TASC enrollment exists with different schools
				List<Enrollment> cleanUpEnrollments = new ArrayList<Enrollment>();
				boolean isTascDifferentSchool = false;
				for (Enrollment en : existedEnrollmets) {
					if(en.getActiveFlag() && (SourceTypeEnum.TASCWEBSERVICE.getCode()).equalsIgnoreCase(en.getSourceType()) 
							&& (en.getAypSchoolId()!= kidRecord.getEnrollment().getAypSchoolId() 
							|| en.getAttendanceSchoolId() != kidRecord.getEnrollment().getAttendanceSchoolId())){
						isTascDifferentSchool = true;
						cleanUpEnrollments.add(en);
					}
				}
				//Verify whether any active enrollment exists
				Enrollment anyActiveEnroll = null;
				for (Enrollment extenroll : existedEnrollmets) {
					if(extenroll.getActiveFlag()){
						anyActiveEnroll = extenroll;
					}
				}
				boolean gradeChange = false;
				if(anyActiveEnroll != null)
				{
					 gradeChange = isGradeChange(kidRecord.getEnrollment(), anyActiveEnroll);
				}
				//Get all existed assessment programs for the student
				List<StudentAssessmentProgram> studentAssessmentPrograms = studentAssessmentProgramsDao.getByStudentId(student.getId());
				Long[] apIds = student.getStudentAssessmentPrograms();
				boolean isDLMExit = false;
				// Verify DLM Assessment program here....If student is already
				// enrolled through DLM for this school year, ignore TEST record
				boolean activeDLMStudent = false;
				if ((apIds != null && apIds.length > 0) || (anyActiveEnroll != null)) {
					for (StudentAssessmentProgram sap : studentAssessmentPrograms) {
						if (sap.getAssessmentProgramId().equals(dlmap.getId()) && sap.getActiveFlag()) {
							if (existedEnrollmets!= null && existedEnrollmets.size() > 0) {
								if(anyActiveEnroll != null){//Still active DLM
									activeDLMStudent = true;
									if(isKAPKid(kidRecord)){
										LOGGER.debug("RecordCommonId: " + kidRecord.getRecordCommonId() + " is rejected because the student is enrolled in DLM for the current school year.");
										// Figure out DTC and BTC for Att school and AYP school and send email
										errorMessage = "The student is enrolled in DLM for the current school year, so no changes were made";
										updateKidAuditData(kidRecord, errorMessage, "TEST_DLMStudent");
										buildDashboardErrorMessageList(kidRecord, aypSchool, attendanceSchool, "NOT_PROCESSED", "Notification", errorMessage, null, "ALL_KAP_SUBJECTS");
										
										if(isCpassKid(kidRecord) || isKELPAKid(kidRecord)){
											rejectKAP = true;											
										}else{
											return kidRecord;
										}
									}
								}
								if(anyActiveEnroll == null){//Exited DLM
									isDLMExit = true;
									claimDLMStudentAsKap(kidRecord, user, student, studentAssessmentPrograms,apIds, sap);
								}
							} else {
								// If student is enrolled to DLM in previous year
								// and now came as KAP student, remove DLM for that student and activate as KAP
								claimDLMStudentAsKap(kidRecord, user, student, studentAssessmentPrograms,apIds, sap);
							}
						}
					}
				}

				//Grade change
				if(gradeChange){
					if(!activeDLMStudent)
						// Update enrollment for any grade change
						AddOrUpdateEnrollmentForGradeChange(kidRecord.getEnrollment(), existedEnrollmets);
					else
					{
						LOGGER.debug("RecordCommonId: " + kidRecord.getRecordCommonId() + " is rejected because this student is enrolled in DLM for the current school year and grade cannot be changed");
						// Figure out DTC and BTC for ATT school and AYP school and send email
						errorMessage = "The student is enrolled in DLM for the current school year, so no changes were made";
						updateKidAuditData(kidRecord, errorMessage, "TEST_DLMStudent");
						buildDashboardErrorMessageList(kidRecord, aypSchool, attendanceSchool, "NOT_PROCESSED", "Notification", errorMessage, null, "ALL_KAP_SUBJECTS");
						kidRecord.setDoReject(true);
						kidRecord.setStatus(NOT_PROCESSED);
						return kidRecord;
					}
				}
				
				// Update AssessmentPrograms to system if received additional from TEST record
				boolean exists = false;
				if (apIds != null && apIds.length > 0) {
					for (Long apId : apIds) {
						if(rejectKAP && (apId == kapap.getId().longValue())){
							continue;
						}
						exists = false;
						for (StudentAssessmentProgram sap : studentAssessmentPrograms) {
							if (sap.getAssessmentProgramId().equals(apId)) {
								if (!sap.getActiveFlag()) {
									sap.setActiveFlag(true);
									studentAssessmentProgramsDao.updateByPrimaryKey(sap);
								}
								exists = true;
								break;
							}
						}
						if (!exists) {
							StudentAssessmentProgram stuAssessmentProgram = new StudentAssessmentProgram();
							stuAssessmentProgram.setActiveFlag(true);
							stuAssessmentProgram.setAssessmentProgramId(apId);
							stuAssessmentProgram.setStudentId(student.getId());
							studentAssessmentProgramsDao.insert(stuAssessmentProgram);
						}
					}
				}
				
				// Verify if enrollment already existed with that subject combination
				// Assuming always a single record found for each subject, if there is any data issue, needs to clean up the data
				boolean enrollSubExists;
				Enrollment existedDbEnroll = null;
				boolean isRosterProcessRequired = false;
				boolean historyProctorProcessRequired = false;
				
				for (EnrollmentTestTypeSubjectArea ettsa : inTestTypeSubjectAreas) {
					if(rejectKAP && isKap(ettsa, subjectAreaMap)){
						continue;
					}
					enrollSubExists = false;
					for (Enrollment enroll : existedEnrollmets) {
						for (edu.ku.cete.domain.enrollment.SubjectArea sa : enroll.getSubjectAreaList()) {
							if (sa.getId().longValue() == ettsa.getSubjectareaId().longValue()) {
								enrollSubExists = true;
								existedDbEnroll = enroll;
								existedDbEnroll.setSubjectAreaId(sa.getId());
							}
						}
						if (enrollSubExists)
							break;
					}
					if (enrollSubExists && existedDbEnroll != null && !isDLMExit) {
						// Enrollment exists for the given subject
						//Create new enrollment if only exit record exists
						if(!existedDbEnroll.getActiveFlag()){
							if(ettsa.isClear()){
								updateKidAuditData(kidRecord, kidRecord + ": Clear not processed. No TEST record found for subject area - "+subjectAreaIdMap.get(ettsa.getSubjectareaId()).getSubjectAreaName(), null);
								continue;
							}else{
								if(isKElpa(ettsa, subjectAreaMap)){
									//This is new test record after exit - so validation required to verify scoring status
									if(processEducator(kidRecord, attendanceSchool, user)){
										isRosterProcessRequired = true;
										validateTransfer(kidRecord, existedDbEnroll, ettsa, subjectAreaMap);
									}else{
										if(kidRecord.getStatus().equals(NOT_PROCESSED))
											removeStudentKELPAAssociation(studentAssessmentPrograms, kidRecord);
										
										continue;
									}
								}									
								//HGSS check
								if(isKapHGSS(ettsa, subjectAreaMap)){
									if(validateProctorInfo(kidRecord, attendanceSchool, user)){
										historyProctorProcessRequired = true;
									}else{											
										continue;
									}
								}
								
								if(isKap(ettsa, subjectAreaMap) && existedDbEnroll.getExitWithdrawalType() == 1){
									validateTransfer(kidRecord, existedDbEnroll, ettsa, subjectAreaMap);
								}
								addOrUpdateEnrollmentForTest(kidRecord, existedEnrollmets, existedDbEnroll, user, ettsa);
							}
						}else{
							if ((kidRecord.getAttendanceSchoolProgramIdentifier().equalsIgnoreCase(existedDbEnroll.getAttendanceSchoolProgramIdentifier())
									&& kidRecord.getAypSchoolIdentifier().equalsIgnoreCase(existedDbEnroll.getAypSchoolIdentifier()))){
								LOGGER.debug(kidRecord + ": Both AYP and Attendance schools are same from existing enrollment record");
								// given subject existed with given combination of schools, so verify next steps..
								if (ettsa.isClear()) {
									processClearTestTypes(kidRecord, existedDbEnroll, user, testTypesMap, subjectAreaIdMap, ettsa);
								} else {
									if(isKElpa(ettsa, subjectAreaMap)){
										//Verify for scoring status before process educator changes
										if(validForEducatorChange(kidRecord.getStudentId())){
											if(processEducator(kidRecord, attendanceSchool, user))
												isRosterProcessRequired = true;
											else{
												if(kidRecord.getStatus().equals(NOT_PROCESSED))
													removeStudentKELPAAssociation(studentAssessmentPrograms, kidRecord);
												
												continue;
											}
										}else{											
											updateKidAuditData(kidRecord, "No changes were made because scoring is underway.", "TEST_ScoringInprogress");
											kidRecord.setStatus(NOT_PROCESSED);
											removeStudentKELPAAssociation(studentAssessmentPrograms, kidRecord);
											continue;
										}
									}
									
									if(isKapHGSS(ettsa, subjectAreaMap)){
										//TODO : revisit for scoring changes
										//if(validForEducatorChange(kidRecord.getStudentId())){
											if(validateProctorInfo(kidRecord, attendanceSchool, user)){
												historyProctorProcessRequired = true;
											}else{											
												continue;
											}
										//}
									}
									
									kidRecord.getEnrollment().setId(existedDbEnroll.getId());
									if(StringUtils.equalsIgnoreCase(existedDbEnroll.getSourceType(), SourceTypeEnum.TASCWEBSERVICE.getCode())){
										if(existedDbEnroll.getSchoolEntryDate() == null) {
											addOrUpdate(kidRecord.getEnrollment());
										}
									}
									
								}
							}else{
								LOGGER.debug("RecordCommonId: " + kidRecord.getRecordCommonId() + ": AYP/Attendance school is different from existing enrollment record");
								// If enrollment not exists for the given subject and clear as input
								if (ettsa.isClear()) {
									updateKidAuditData(kidRecord, "Clear not processed. No TEST record found for subject area - "
													+ subjectAreaIdMap.get(ettsa.getSubjectareaId()).getSubjectAreaName(), null);
									continue;
								}
								if(isKElpa(ettsa, subjectAreaMap)){
									//In transfer schools also, no check required to verify scoring status..
									if(processEducator(kidRecord, attendanceSchool, user))
										isRosterProcessRequired = true;
									else{
										if(kidRecord.getStatus().equals(NOT_PROCESSED))
											removeStudentKELPAAssociation(studentAssessmentPrograms, kidRecord);
										
										continue;
									}
								}
								
								if(isKapHGSS(ettsa, subjectAreaMap)){
									if(validateProctorInfo(kidRecord, attendanceSchool, user)){
										historyProctorProcessRequired = true;
									}else{											
										continue;
									}
								}
								
								if (!kidRecord.getAttendanceSchoolProgramIdentifier()
										.equalsIgnoreCase(existedDbEnroll.getAttendanceSchoolProgramIdentifier())) {
									validateTransfer(kidRecord, existedDbEnroll, ettsa, subjectAreaMap);
									transferSubjectSchool(kidRecord, existedDbEnroll, existedEnrollmets, user, ettsa, true, isKElpa(ettsa, subjectAreaMap), contentAreaIdsMatchedWithKidsTestRecords, isKapHGSS(ettsa, subjectAreaMap), testTypeIdMap);
								}else if (kidRecord.getAttendanceSchoolProgramIdentifier().equalsIgnoreCase(existedDbEnroll.getAttendanceSchoolProgramIdentifier())
										&& !kidRecord.getAypSchoolIdentifier().equalsIgnoreCase(existedDbEnroll.getAypSchoolIdentifier())) {
									// Change enrollment Test record with latest ayp school info and verify tests and resets
									transferSubjectSchool(kidRecord, existedDbEnroll, existedEnrollmets, user, ettsa, true, isKElpa(ettsa, subjectAreaMap), contentAreaIdsMatchedWithKidsTestRecords, isKapHGSS(ettsa, subjectAreaMap), testTypeIdMap);
								}
							}
						}
						//}
					} else {// Enrollment not found for the given subject
						LOGGER.debug("RecordCommonId: " + kidRecord.getRecordCommonId() + " Creating new enrollment for subject - " + ettsa.getSubjectareaId());
						// If enrollment not exists for the given subject and clear as input
						if (ettsa.isClear()) {
							updateKidAuditData(kidRecord, "Clear not processed. No TEST record found for subject area - "
											+ subjectAreaIdMap.get(ettsa.getSubjectareaId()).getSubjectAreaName(), null);
							kidRecord.setStatus(NOT_PROCESSED);
							continue;
						}
						if(isKElpa(ettsa, subjectAreaMap)){
							if(processEducator(kidRecord, attendanceSchool, user))
								isRosterProcessRequired = true;
							else{
								if(kidRecord.getStatus().equals(NOT_PROCESSED))
									removeStudentKELPAAssociation(studentAssessmentPrograms, kidRecord);
								
								continue;
							}
						}
						
						if(isKapHGSS(ettsa, subjectAreaMap)){
							if(validateProctorInfo(kidRecord, attendanceSchool, user)){
								historyProctorProcessRequired = true;
							}else{											
								continue;
							}
						}
						
						addOrUpdateEnrollmentForTest(kidRecord, existedEnrollmets, existedDbEnroll, user, ettsa);
						for(Enrollment en : existedEnrollmets) {
							if (!(kidRecord.getAttendanceSchoolProgramIdentifier()
									.equalsIgnoreCase(en.getAttendanceSchoolProgramIdentifier())
									&& kidRecord.getAypSchoolIdentifier()
											.equalsIgnoreCase(en.getAypSchoolIdentifier()) && en.getActiveFlag())) {								
								processCleanupEnrollment(en, contentAreaIdsMatchedWithKidsTestRecords, kidRecord);								
							}
						}
					}
				}
				if(isKELPAKid(kidRecord) && isRosterProcessRequired){
					//roster process
					processKELPAProctor(kidRecord, attendanceSchool, aypSchool, user);
				}
				
				if(historySubjectExists(kidRecord) && historyProctorProcessRequired){
					processHGSSProctor(kidRecord, attendanceSchool, aypSchool, user,existedDbEnroll);
				}
				
				//Updating student at the end to avoid unexceptional returns
				//studentService.addOrUpdate(kidRecord.getStudent(), kidRecord.getAttendanceSchoolId());
				updateStudent(kidRecord.getStudent());
				if(isTascDifferentSchool){
					if(CollectionUtils.isNotEmpty(cleanUpEnrollments)){
						for(Enrollment en : cleanUpEnrollments){
							processCleanupTASCEnrollment(en, contentAreaIdsMatchedWithKidsTestRecords);
							processCleanupTASCRosters(en, kidRecord, contentAreaIdsMatchedWithKidsTestRecords);
						}
					}
				}
			} else if (student.getSaveStatus().equals(RecordSaveStatus.STUDENT_ADDED)) {
				// New student, add student assessment programs
				LOGGER.debug("Inserting student assessment programs for " + kidRecord);
				if(student.getStudentAssessmentPrograms() == null){
					updateKidAuditData(kidRecord, "Atleast one valid test type code is required.", null);
					kidRecord.setDoReject(true);
					kidRecord.setStatus(NOT_PROCESSED);
					return kidRecord;
				}
				for (Long apId : student.getStudentAssessmentPrograms()) {
					StudentAssessmentProgram stuAssessmentProgram = new StudentAssessmentProgram();
					stuAssessmentProgram.setActiveFlag(true);
					stuAssessmentProgram.setAssessmentProgramId(apId);
					stuAssessmentProgram.setStudentId(student.getId());
					studentAssessmentProgramsDao.insert(stuAssessmentProgram);
				}
				// create new enrollment and testtypesubjectareas
				LOGGER.debug("Inserting enrollment and test type subject areas for " + kidRecord);
				Enrollment newEnrollment = kidRecord.getEnrollment();
				boolean isRosterProcessRequired = false;
				boolean historyProctorProcessRequired = false;
				
				for (EnrollmentTestTypeSubjectArea ettsa : inTestTypeSubjectAreas) {
					// For new student, given subject and clear as input
					if (ettsa.isClear()) {
						updateKidAuditData(kidRecord,
								"Clear not processed. No TEST record found for subject area - "
										+ subjectAreaIdMap.get(ettsa.getSubjectareaId()).getSubjectAreaName(), null);
						continue;
					}
					if(isKElpa(ettsa, subjectAreaMap)){
						if(processEducator(kidRecord, attendanceSchool, user))
							isRosterProcessRequired = true;
						else{
							if(kidRecord.getStatus().equals(NOT_PROCESSED))
								removeStudentKELPAAssociation(kidRecord);
							continue;
						}
					}
					
					if(isKapHGSS(ettsa, subjectAreaMap)){
						if(validateProctorInfo(kidRecord, attendanceSchool, user)){
							historyProctorProcessRequired = true;
						}else{											
							continue;
						}
					}
					
					// enrollment already added
					if (newEnrollment.getId() != 0 || newEnrollment.getSaveStatus().equals(RecordSaveStatus.ENROLLMENT_ADDED)) {
						// create enrollmentTesttypesubjectarea record
						addOrUpdateEnrollmentTestType(newEnrollment, user, ettsa, null);
					} else {
						// create new enrollment
						newEnrollment = addOrUpdate(newEnrollment);
						if(newEnrollment == null || newEnrollment.getId() == 0){
							updateKidAuditData(kidRecord, kidRecord +
									":Failed while enrollment creating using AYP school - " + newEnrollment.getAypSchoolIdentifier() +
									"and Attendance school - " + newEnrollment.getAttendanceSchoolProgramIdentifier(), null);
							kidRecord.setStatus(NOT_PROCESSED);
							if(isKELPAKid(kidRecord) && kidRecord.getStatus().equals(NOT_PROCESSED))
								removeStudentKELPAAssociation(kidRecord);							
							return kidRecord;
						}
						// create enrollmentTesttypesubjectarea record
						addOrUpdateEnrollmentTestType(newEnrollment, user, ettsa, null);
					}
				}
				if(isKELPAKid(kidRecord) && isRosterProcessRequired){					
					processKELPAProctor(kidRecord, attendanceSchool, aypSchool, user);
				}
				
				if(historySubjectExists(kidRecord) && historyProctorProcessRequired){
					processHGSSProctor(kidRecord, attendanceSchool, aypSchool, user,null);
				}
				
			}

		} else if (kidRecord.getRecordType().equalsIgnoreCase("EXIT")) {
			LOGGER.debug("Processing an EXIT record for student: " + kidRecord.getStateStudentIdentifier()
					+ " and school: " + kidRecord.getAttendanceSchoolProgramIdentifier());
			Student student = studentService.validateIfStudentExists(kidRecord.getStudent());
			if (!student.isDoReject()) {
				
				//List<StudentAssessmentProgram> studentAssessmentPrograms = studentAssessmentProgramsDao.getByStudentId(student.getId());
				List<Enrollment> existedEnrollmets = enrollmentDao.findSubjectEnrollmentsByStudentId(student.getId(), kidRecord.getCurrentSchoolYear(), student.getStateId());
				Enrollment anyActiveEnrollment = getFirstActiveEnrollment(existedEnrollmets);
				// Verify DLM Assessment program here....If student is already
				// enrolled through DLM for this school year, ignore TEST record
				 AssessmentProgram ap = assessmentProgramDao.findByStudentId(student.getId(), kidRecord.getCurrentSchoolYear());
				 if(ap != null && "DLM".equals(ap.getAbbreviatedname())){	
						if (existedEnrollmets!= null && existedEnrollmets.size() > 0 && anyActiveEnrollment != null ) {
							LOGGER.debug("The record: " + kidRecord.getRecordCommonId() + " is rejected because the student is enrolled in DLM for the current school year, so no changes were made.");
							// Figureout DTC and BTC for Att school and AYP school and send email
							errorMessage = "The student is enrolled in DLM for the current school year, so no changes were made.";
							updateKidAuditData(kidRecord, errorMessage, "TEST_DLMStudent");
							buildDashboardErrorMessageList(kidRecord, aypSchool, attendanceSchool, "NOT_PROCESSED", "Notification", errorMessage, null, "ALL_KAP_SUBJECTS");
							kidRecord.setStatus(NOT_PROCESSED);
							return kidRecord;
						}
					}
				 List<String> kidsAssessmentPgm = appConfigurationService.getValueByAttributeType(CommonConstants.KIDS_EXIT_ASS_PGM);	
				 List<Long> assessmentPgmIds = assessmentProgramService.getAssessPgmIdByAbbreviatedName(kidsAssessmentPgm);
				 List<Integer> stateSupportExitCodes = studentService.getStateSpecificExitCodesForKids(studentState.getId(), assessmentPgmIds, Long.valueOf(kidRecord.getCurrentSchoolYear()));				

				 
				if (Long.valueOf(kidRecord.getExitWithdrawalType()).equals(99L)) {//exit type is 99 - exit reversal
					boolean foundUndoExit = false;
					List<Enrollment> inactiveEnrollments = enrollmentDao.getInactiveEnrollment(
							kidRecord.getStateStudentIdentifier(), studentState.getId(), enrollmentCurrentSchoolYear);
					for (Enrollment e : inactiveEnrollments) {
						if (e.getAypSchoolId() == kidRecord.getEnrollment().getAypSchoolId()
								&& e.getAttendanceSchoolId() == kidRecord.getAttendanceSchoolId()
								&& e.getCurrentSchoolYear() == kidRecord.getCurrentSchoolYear()
								&& e.getExitWithdrawalDate() != null
								&& e.getExitWithdrawalDate().getTime() == kidRecord.getExitWithdrawalDate().getTime()) {
							foundUndoExit = true;
							undoExitRecords(user, e);
						}
					}
					if(!foundUndoExit){//undo exit record not found, then verify whether student exists in other school
						List<Enrollment> enrollments = enrollmentDao.getBySsidAndState(
								kidRecord.getStateStudentIdentifier(), studentState.getId(), enrollmentCurrentSchoolYear);
						if(enrollments == null || enrollments.size()==0){
							updateKidAuditData(kidRecord, "No active enrollment found to process EXIT reversal", null);
						}else{
							for (Enrollment e : enrollments) {
								if ((e.getAypSchoolId() != kidRecord.getEnrollment().getAypSchoolId() || e.getAttendanceSchoolId() != kidRecord.getEnrollment().getAttendanceSchoolId())
										&& e.getCurrentSchoolYear() == kidRecord.getCurrentSchoolYear()) {
									errorMessage = "No changes were made because the student no longer attends the district/school";
									updateKidAuditData(kidRecord, errorMessage, "EXIT_OrgMismatch");
									kidRecord.setStatus(NOT_PROCESSED);
									
									buildDashboardErrorMessageList(kidRecord, aypSchool, attendanceSchool, "NOT_PROCESSED", MESSAGE_TYPE_NOTIFICATION, errorMessage, null, null);
									
									return kidRecord;
								}
							}
						}
					}
				} else if (!stateSupportExitCodes.contains(Integer.valueOf(kidRecord.getExitWithdrawalType()))) { //F651 Exit code by State
					errorMessage = " This record is rejected because exit code \""+ kidRecord.getExitWithdrawalType() +"\" is not valid in your state. ";
					updateKidAuditData(kidRecord, errorMessage, "EXIT_OrgMismatch");
					buildDashboardErrorMessageList(kidRecord, aypSchool, attendanceSchool, "NOT_PROCESSED", MESSAGE_TYPE_NOTIFICATION, errorMessage, null, null);				
					kidRecord.setStatus(NOT_PROCESSED);
					return kidRecord;
				} else {//any other exit including exit type 1
					List<Enrollment> enrollments = enrollmentDao.getEnrollmentsByAypAndAttendanceSchool(
							kidRecord.getStateStudentIdentifier(), studentState.getId(), enrollmentCurrentSchoolYear, kidRecord.getEnrollment().getAypSchoolId(), kidRecord.getEnrollment().getAttendanceSchoolId());
					if(enrollments == null || enrollments.isEmpty()){
						LOGGER.debug("RecordCommonId" + kidRecord.getRecordCommonId() + ": Enrollments are not found in the system to process EXIT.");
						if(anyActiveEnrollment == null){
							updateKidAuditData(kidRecord, "Enrollments are not found in the system to process EXIT.", null);
						}else{
							errorMessage = "No changes were made because the student no longer attends the district/school";
							updateKidAuditData(kidRecord, errorMessage, "EXIT_OrgMismatch");
							buildDashboardErrorMessageList(kidRecord, aypSchool, attendanceSchool, "NOT_PROCESSED", MESSAGE_TYPE_NOTIFICATION, errorMessage, null, null);
						}
						kidRecord.setStatus(NOT_PROCESSED);
						return kidRecord;
					}else{
						for (Enrollment e : enrollments) {
							processExitRecord(kidRecord, user, e);
						}
					}
				}
			} else {
				updateKidAuditData(kidRecord, kidRecord + ": Existing student information was not found in the system.", null);				
				kidRecord.setStatus(NOT_PROCESSED);
				return kidRecord;
			}
		}
		return kidRecord;
	}
	
	public void removeStudentKELPAAssociation(List<StudentAssessmentProgram> studentAssessmentPrograms, KidRecord kidRecord){
		boolean exists = false;
		//This section of code handles TEST records for existing students in system. 
		//If kelpa2 record fails and if not already associated with KELPA2, remove the kelpa2 association with the student
		//If an existing KELPA2 student and TEST record fails, ignore.
		AssessmentProgram kelpaAP = assessmentProgramDao.findByAbbreviatedName("KELPA2");
		for (StudentAssessmentProgram sap : studentAssessmentPrograms) {
			if (sap.getAssessmentProgramId().equals(kelpaAP.getId())) {
				exists = true;
				break;
			}
		}
		
		if(!exists)
			studentAssessmentProgramsDao.deactivateByStudentIdAndAssessmentProgramId(kidRecord.getStudentId(), kelpaAP.getId());
	}
	
	public void removeStudentKELPAAssociation(KidRecord kidRecord){
		//This section of code handles adding new students. Hence if kelpa record fails remove the kelpa association with the student. 
		AssessmentProgram kelpaAP = assessmentProgramDao.findByAbbreviatedName("KELPA2");
		studentAssessmentProgramsDao.deactivateByStudentIdAndAssessmentProgramId(kidRecord.getStudentId(), kelpaAP.getId());
	}

	private List<Long> getKidsSubjectAreasAndContentAreaMapping(List<EnrollmentTestTypeSubjectArea> inTestTypeSubjectAreas) {		
		return contentAreaService.getContentAreaIdsByTestTypeAndSubjectArea(inTestTypeSubjectAreas);
	}

	private void claimDLMStudentAsKap(KidRecord kidRecord, User user, Student student,
			List<StudentAssessmentProgram> studentAssessmentPrograms, Long[] apIds, StudentAssessmentProgram sap) {
		try {
			List<Long> otherApIds = new ArrayList<Long>(apIds.length + studentAssessmentPrograms.size() - 1);
			for (StudentAssessmentProgram tmp : studentAssessmentPrograms) {
				if (!tmp.getAssessmentProgramId().equals(sap.getAssessmentProgramId())) {
					otherApIds.add(tmp.getAssessmentProgramId());
				}
			}
			otherApIds.addAll(Arrays.asList(apIds));
			//remove PNP attributes and claim for KAP
		     studentProfileService.removeNonAssociatedPNPSettings(student.getId(), otherApIds, user.getId(),
		    		 user.getContractingOrganization().getId());
		     studentAssessmentProgramsDao.deactivateByStudentIdAndAssessmentProgramId(student.getId(), sap.getAssessmentProgramId());
		 } catch (JsonProcessingException e) {
			 updateKidAuditData(kidRecord, kidRecord + ": Removing PNP changes for DLM assessment failed ", null);
		 	 LOGGER.debug("Failed in changing PNP setting while assessment change "+e.getMessage());      
		 }
	}

	private void validateTransfer(KidRecord kidRecord, Enrollment existedDbEnroll, EnrollmentTestTypeSubjectArea ettsa, Map<String, SubjectArea> subjectAreaMap){
		if (!kidRecord.getAttendanceSchoolProgramIdentifier()
				.equalsIgnoreCase(existedDbEnroll.getAttendanceSchoolProgramIdentifier())) {
			LOGGER.debug(kidRecord + ": AYP school is different from existing enrollment record and TEST record");
			String errorMessage = StringUtils.EMPTY;
			// Verify school district changes only for KAP - confirmed on 08/08/16 - Susan and JB
			// Get all tests for the given subject and status with existing school
			if(isKap(ettsa,subjectAreaMap)){
				String testStatus = studentsTestsService.getStudentsTestStageStatus(existedDbEnroll,
						ettsa.getTestTypeId(), ettsa.getSubjectareaId(), false);
				// Transfer in same district
				if (kidRecord.getEnrollment().getAttendanceSchoolDistrictId().longValue() == existedDbEnroll.getAttendanceSchoolDistrictId().longValue()) {
					// stage1 && stage2 completed
					if (testStatus.equalsIgnoreCase(STAGE2_COMPLETED)) {
						errorMessage = "No additional test sessions will be generated for the student.";
						updateKidAuditData(kidRecord,  errorMessage,"TEST_TSNotCreated");						
						
						buildDashboardErrorMessageList(kidRecord, null, null, "COMPLETED", MESSAGE_TYPE_NOTIFICATION, errorMessage, null, getSubjectAbbreviatedNameMapping(ettsa.getSubjectAreaCode()));
						
					} else if (testStatus.equalsIgnoreCase(STAGE1_COMPLETED) || testStatus.equalsIgnoreCase(STAGE_EXISTS)) {
						// stage1 completed && stage2 not completed
						errorMessage = "Additional test sessions will be generated within 48 hours.";
						updateKidAuditData(kidRecord, errorMessage, "TEST_TSCreated");
						
						buildDashboardErrorMessageList(kidRecord, null, null, "COMPLETED", MESSAGE_TYPE_NOTIFICATION, errorMessage, null, getSubjectAbbreviatedNameMapping(ettsa.getSubjectAreaCode()));
					} 
					
				} else {// different district, any stage is completed, no test will be generated
					if (testStatus.equalsIgnoreCase(STAGE2_COMPLETED)
							|| testStatus.equalsIgnoreCase(STAGE1_COMPLETED)) {
						errorMessage = "No additional test sessions will be generated for the student.";
						updateKidAuditData(kidRecord,  errorMessage, "TEST_TSNotCreated");
						
						buildDashboardErrorMessageList(kidRecord, null, null, "COMPLETED", MESSAGE_TYPE_NOTIFICATION, errorMessage, null, getSubjectAbbreviatedNameMapping(ettsa.getSubjectAreaCode()));
						
					}else if(testStatus.equalsIgnoreCase(STAGE_EXISTS)){
						errorMessage = "Additional test sessions will be generated within 48 hours.";
						updateKidAuditData(kidRecord, errorMessage, "TEST_TSCreated");
						
						buildDashboardErrorMessageList(kidRecord, null, null, "COMPLETED", MESSAGE_TYPE_NOTIFICATION, errorMessage, null, getSubjectAbbreviatedNameMapping(ettsa.getSubjectAreaCode()));
					}
				}
			}else if(isKElpa(ettsa, subjectAreaMap)){
				String testStatus = studentsTestsService.getStudentsTestStageStatus(existedDbEnroll,
						ettsa.getTestTypeId(), ettsa.getSubjectareaId(), true);
				if(NO_STAGE.equalsIgnoreCase(testStatus)){
					//verify operational test windows
					List<OperationalTestWindow> otwLst= operationalTestWindowDao.fetchOperationalTestWindowsByAssessmentProgram("KELPA2");
					if(otwLst == null || otwLst.size()==0 || otwLst.get(0).getEffectiveDate().getTime() > new Date().getTime()){
						errorMessage = "OTW not open for KEPLA";
						updateKidAuditData(kidRecord,  errorMessage,"TEST_GenerateAdditionalTests");
						
						buildDashboardErrorMessageList(kidRecord, null, null, "COMPLETED", MESSAGE_TYPE_NOTIFICATION, errorMessage, null, getSubjectAbbreviatedNameMapping(ettsa.getSubjectAreaCode()));
						
					}else if(otwLst != null && otwLst.size()>0 && otwLst.get(0).getExpiryDate().getTime() < new Date().getTime()){
						errorMessage = "OTW expired for KEPLA";
						updateKidAuditData(kidRecord,  errorMessage,"TEST_NotGenerateAdditionalTests");
						
						buildDashboardErrorMessageList(kidRecord, null, null, "COMPLETED", MESSAGE_TYPE_NOTIFICATION, errorMessage, null, getSubjectAbbreviatedNameMapping(ettsa.getSubjectAreaCode()));
					}
				}
				// Some stages unused/in-progress
				if (testStatus.equalsIgnoreCase(STAGE_EXISTS)) {
					errorMessage = "Additional test sessions will be generated within 48 hours.";
					updateKidAuditData(kidRecord,  errorMessage,"TEST_GenerateAdditionalTests");
					
					buildDashboardErrorMessageList(kidRecord, null, null, "COMPLETED", MESSAGE_TYPE_NOTIFICATION, errorMessage, null, getSubjectAbbreviatedNameMapping(ettsa.getSubjectAreaCode()));
					
				} else if (testStatus.equalsIgnoreCase(ALL_STAGES_COMPLETED)) {// All Stages completed
					errorMessage = "No additional test sessions will be generated for the student.";
					updateKidAuditData(kidRecord, errorMessage, "TEST_NotGenerateAdditionalTests");
					
					buildDashboardErrorMessageList(kidRecord, null, null, "COMPLETED", MESSAGE_TYPE_NOTIFICATION, errorMessage, null, getSubjectAbbreviatedNameMapping(ettsa.getSubjectAreaCode()));
				} 
			}
		}
	}
	
	/**
	 * 
	 * @param enroll
	 * @param kidRecord
	 * @param contentAreaIdsMappedToTestRecords
	 */
	private void processCleanupTASCRosters(Enrollment enroll, KidRecord kidRecord, List<Long> contentAreaIdsMappedToTestRecords){
		LOGGER.debug("processCleanupTASCRosters for enrollmentId :"+ enroll.getId());
		if(CollectionUtils.isNotEmpty(contentAreaIdsMappedToTestRecords)) {
			for(Long contentAreaId : contentAreaIdsMappedToTestRecords) {
				List<Roster> rosters = getRostersAssociatedWithEnrollment(enroll, contentAreaId);
				//roster exists for enrollment
		        if(rosters != null && rosters.size() > 0){
		        	for(Roster roster: rosters){
			        	inactivateEnrollmentRoster(kidRecord.getCurrentContextUserId(), roster, enroll.getId());
						User teacher = new User();
						teacher.setId(roster.getTeacherId());
						teacher.setCurrentContextUserId(roster.getTeacherId());
						interimGroupService.deleteOnRosterChange(teacher, enroll.getStudentId());
						
						interimService.exitStudentRosterKIDSInterim(enroll, roster);
		        	}
		        }
			}
		}
	}

	private List<Roster> getRostersAssociatedWithEnrollment(Enrollment enroll, Long contentareaId) {
		return rosterService.getRostersByContentAreaAndEnrollment(contentareaId, enroll);
	}
	
	/**
	 * Inactivate enrollmentroster record. ie..remove student teacher association
	 * @param kidRecord
	 * @param currentContextUserId
	 * @param rosters
	 */
	private void inactivateEnrollmentRoster(Long currentContextUserId, Roster roster, Long enrollmentId) {
		EnrollmentsRosters enrollmentsRosters = new EnrollmentsRosters(enrollmentId, roster.getId());
		enrollmentsRosters.setActiveFlag(false);			
		enrollmentsRosters.setCourseEnrollmentStatusId(99L);
		enrollmentsRosters.setCurrentContextUserId(currentContextUserId);								
		enrollmentsRostersService.updateEnrollementRosterToInActive(enrollmentsRosters);
	}
	
	private void addOrUpdateEnrollmentForTest(KidRecord kidRecord, List<Enrollment> existedEnrollmets, Enrollment existedDbEnroll, User user, EnrollmentTestTypeSubjectArea ettsa){
	
		// Check whether active enrollment exists with the given school combination and school year
		boolean enrollExists = false;
		if (!RecordSaveStatus.ENROLLMENT_ADDED.equals(kidRecord.getEnrollment().getSaveStatus())) {
			for (Enrollment enroll : existedEnrollmets) {
				if (kidRecord.getAttendanceSchoolProgramIdentifier()
						.equalsIgnoreCase(enroll.getAttendanceSchoolProgramIdentifier())
						&& kidRecord.getAypSchoolIdentifier()
								.equalsIgnoreCase(enroll.getAypSchoolIdentifier()) && enroll.getActiveFlag()) {
					enrollExists = true;
					existedDbEnroll = enroll;
					break;
				}
			}
			if (enrollExists) {
				kidRecord.getEnrollment().setId(existedDbEnroll.getId());
				//Update Enrollment if only TASC exists
				if(StringUtils.equalsIgnoreCase(existedDbEnroll.getSourceType(),SourceTypeEnum.TASCWEBSERVICE.getCode())){
					addOrUpdate(kidRecord.getEnrollment());
				}
				// Create enrollmentTesttype record for the subject - ettsa.getSubjectareaId()
				addOrUpdateEnrollmentTestType(existedDbEnroll, user, ettsa, null);
			} else {
				// create new enrollment
				Enrollment newEnrollment = addOrUpdate(kidRecord.getEnrollment());
				// create enrollmentTesttypesubjectarea record
				addOrUpdateEnrollmentTestType(newEnrollment, user, ettsa, null);
			}
		} else {
			// Enrollment already created, use the newly created enrollment
			addOrUpdateEnrollmentTestType(kidRecord.getEnrollment(), user, ettsa, null);
		}
	}
	
	/**
	 * Get all assessment programs from the TEST record
	 * @param kidRecord
	 * @param studentState
	 * @param student
	 * @return List of assessment programs
	 */
	private List<Long> getAssessmentProgramKid(KidRecord kidRecord, Organization studentState, Student student) {
		List<Long> studentAssessmentPrograms = null;
		if (studentState.getDisplayIdentifier().equalsIgnoreCase("KS")) {
			studentAssessmentPrograms = new ArrayList<Long>();

			if (isCpassKid(kidRecord)) {
				AssessmentProgram ap = assessmentProgramDao.findByAbbreviatedName("CPASS");
				studentAssessmentPrograms.add(ap.getId());
			}

			if (isKELPAKid(kidRecord)) {
				AssessmentProgram ap = assessmentProgramDao.findByAbbreviatedName("KELPA2");
				if (ap != null)
					studentAssessmentPrograms.add(ap.getId());
			}

			if (isKAPKid(kidRecord)) {
				AssessmentProgram ap = assessmentProgramDao.findByAbbreviatedName("KAP");
				studentAssessmentPrograms.add(ap.getId());
			}

			if (!studentAssessmentPrograms.isEmpty()) {
				student.setStudentAssessmentPrograms(
						studentAssessmentPrograms.toArray(new Long[studentAssessmentPrograms.size()]));
			}
		}
		return studentAssessmentPrograms;
	}
	
	/**
	 * 
	 * @param kidEnrollment
	 * @param anyActiveEnroll
	 * @return boolean
	 */
	private boolean isGradeChange(Enrollment kidEnrollment, Enrollment anyActiveEnroll){
		boolean gradechange = false;
	
		if (anyActiveEnroll != null && anyActiveEnroll.getCurrentGradeLevel() != null && 
				kidEnrollment.getCurrentGradeLevel()!= null && 	!anyActiveEnroll.getCurrentGradeLevel().equals(kidEnrollment.getCurrentGradeLevel())){
			gradechange = true;
		}
		return gradechange;
	}
	
	/**
	 * Update existing active enrollments for grade change
	 * @param kidEnrollment
	 * @param existedEnrollmets
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private final void AddOrUpdateEnrollmentForGradeChange(Enrollment kidEnrollment, List<Enrollment> existedEnrollmets) {
		if (existedEnrollmets != null && existedEnrollmets.size() > 0) {
			for (Enrollment otherEnrollment : existedEnrollmets) {
				if (otherEnrollment.getActiveFlag()) {
					Long previousGrade = otherEnrollment.getCurrentGradeLevel();
					otherEnrollment.setCurrentGradeLevel(kidEnrollment.getCurrentGradeLevel());
					if(kidEnrollment.getId() != otherEnrollment.getId() || !RecordSaveStatus.ENROLLMENT_UPDATED.equals(kidEnrollment.getSaveStatus())){
						otherEnrollment = enrollmentService.update(otherEnrollment);						
						Roster roster = null;
                		 interimService.exitStudentRosterKIDSInterim(otherEnrollment, roster);
                		 interimService.autoAssignKIDSInterim(otherEnrollment, roster); 
					}
					if (previousGrade != null && otherEnrollment.getCurrentGradeLevel() != null) {
						if (!previousGrade.equals(otherEnrollment.getCurrentGradeLevel())) {
							studentsTestsService.unEnrollStudentTestsOnGradeChange(otherEnrollment);
							enrollmentService.addGradeChangeEventDomainAuditHistory(otherEnrollment, previousGrade);
						}
					}
				}
			}
		}
	}
	
	/**
	 * returns first active enrollment
	 * @param enrollments
	 * @return Enrollment
	 */
	private Enrollment getFirstActiveEnrollment(List<Enrollment> enrollments){
		for (Enrollment enroll : enrollments) {
			if(enroll.getActiveFlag()){
				return enroll;
			}
		}
		return null;
	}
	
	/**
	 * Clean up enrollment which is not associated to any subject
	 * @param tempEnroll
	 */
	private void processCleanupEnrollment(Enrollment tempEnroll, List<Long> contentAreaIdsMatchedWithKidsTestRecords, KidRecord kidRecord){
		if(tempEnroll.getActiveFlag() && tempEnroll.getSourceType().equals(SourceTypeEnum.TESTWEBSERVICE.getCode()) && 
					(tempEnroll.getSubjectAreaList()== null || tempEnroll.getSubjectAreaList().isEmpty())){
			if(!isStudentHaveRostersOtherThanSubjectAreas(tempEnroll, contentAreaIdsMatchedWithKidsTestRecords)) {
				tempEnroll.setActiveFlag(false);
				addOrUpdate(tempEnroll);
				//unenroll from predictive tests
				studentsTestsService.removeStudentPredictiveTests(tempEnroll.getId(), new Long(tempEnroll.getCurrentSchoolYear()), null);
			}
		}
		processCleanupTASCRosters(tempEnroll, kidRecord, contentAreaIdsMatchedWithKidsTestRecords);
	}
	
	private boolean isStudentHaveRostersOtherThanSubjectAreas(Enrollment tempEnroll, List<Long> contentAreaIdsMappedToTestRecords) {
		List<Roster> rostersExisted = getRostersAssociatedWithEnrollment(tempEnroll, null);
		if(CollectionUtils.isEmpty(contentAreaIdsMappedToTestRecords)) {
			return true;
		} else {
			for(Roster existingRoster : rostersExisted) {
				if(!contentAreaIdsMappedToTestRecords.contains(existingRoster.getStateSubjectAreaId())) {
					return true;					
				}
			}
		}
		return false;
	}

	/**
	 * Clean up enrollment which is not associated to any subject
	 * @param tempEnroll
	 * @param contentAreaIdsMappedToTestRecords
	 */
	private void processCleanupTASCEnrollment(Enrollment tempEnroll, List<Long> contentAreaIdsMappedToTestRecords){
		if(tempEnroll.getActiveFlag() && tempEnroll.getSourceType().equals(SourceTypeEnum.TASCWEBSERVICE.getCode()) && 
					(tempEnroll.getSubjectAreaList()== null || tempEnroll.getSubjectAreaList().isEmpty())){
				if(!isStudentHaveRostersOtherThanSubjectAreas(tempEnroll, contentAreaIdsMappedToTestRecords)){
					tempEnroll.setActiveFlag(false);
					addOrUpdate(tempEnroll);
					
					//Remove predictive tests
		    		studentsTestsService.removeStudentPredictiveTests(tempEnroll.getId(), new Long(tempEnroll.getCurrentSchoolYear()), null);
				}
		}
	}
	
	/**
	 * Identifies KAP subject or not
	 * @param ettsa
	 * @param subjectAreaMap
	 * @return boolean
	 */
	private boolean isKap(EnrollmentTestTypeSubjectArea ettsa,  Map<String, SubjectArea> subjectAreaMap){
		if(ettsa.getSubjectareaId().equals((subjectAreaMap.get("D74")).getId()) ||
				ettsa.getSubjectareaId().equals((subjectAreaMap.get("SELAA")).getId()) ||
				ettsa.getSubjectareaId().equals((subjectAreaMap.get("SSCIA")).getId()) ||
				ettsa.getSubjectareaId().equals((subjectAreaMap.get("SHISGOVA")).getId())){
			return true;
		}
		return false;
	}
	
	/**
	 * Identifies ELPA subject or not
	 * @param ettsa
	 * @param subjectAreaMap
	 * @return boolean
	 */
	private boolean isKElpa(EnrollmentTestTypeSubjectArea ettsa,  Map<String, SubjectArea> subjectAreaMap){
		if(ettsa.getSubjectareaId().equals((subjectAreaMap.get("D83")).getId())){
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @param kidRecord
	 * @param testTypesMap
	 * @param subjectAreaMap
	 * @return List<EnrollmentTestTypeSubjectArea>
	 */
	public final List<EnrollmentTestTypeSubjectArea> getTestTypeSubjectAreasFromKidRecord(KidRecord kidRecord,
			Map<String, TestType> testTypesMap, Map<String, SubjectArea> subjectAreaMap) {
		List<EnrollmentTestTypeSubjectArea> testTypeSubjectAreas = new ArrayList<EnrollmentTestTypeSubjectArea>();

		if (kidRecord.getStateMathAssess() != null && StringUtils.isNotEmpty(kidRecord.getStateMathAssess())
				&& Arrays.asList(new String[] { "2", "C" }).indexOf(kidRecord.getStateMathAssess()) >= 0) {
			EnrollmentTestTypeSubjectArea testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
			if (kidRecord.getStateMathAssess().equalsIgnoreCase("C")) {
				testTypeSubjectArea.setClear(true);
			} else {
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("D74-" + kidRecord.getStateMathAssess()).getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingInd1Math());
				testTypeSubjectArea.setGroupingInd2(kidRecord.getGroupingInd2Math());
			}
			testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("D74")).getId());
			testTypeSubjectArea.setSubjectAreaCode("D74");
			kidRecord.getReceivedSubjectCodes().add("M");
			testTypeSubjectAreas.add(testTypeSubjectArea);
		}

		if (kidRecord.getStateELAAssessment() != null && StringUtils.isNotEmpty(kidRecord.getStateELAAssessment())
				&& Arrays.asList(new String[] { "2", "C" }).indexOf(kidRecord.getStateELAAssessment()) >= 0) {
			EnrollmentTestTypeSubjectArea testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
			if (kidRecord.getStateELAAssessment().equalsIgnoreCase("C")) {
				testTypeSubjectArea.setClear(true);
			} else {
				testTypeSubjectArea
						.setTestTypeId(testTypesMap.get("SELAA-" + kidRecord.getStateELAAssessment()).getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingInd1ELA());
				testTypeSubjectArea.setGroupingInd2(kidRecord.getGroupingInd2ELA());
			}
			testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("SELAA")).getId());
			testTypeSubjectArea.setSubjectAreaCode("SELAA");
			kidRecord.getReceivedSubjectCodes().add("ELA");
			testTypeSubjectAreas.add(testTypeSubjectArea);
		}

		if (kidRecord.getStateSciAssessment() != null && StringUtils.isNotEmpty(kidRecord.getStateSciAssessment())
				&& Arrays.asList(new String[] { "2", "C" }).indexOf(kidRecord.getStateSciAssessment()) >= 0) {
			EnrollmentTestTypeSubjectArea testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
			if (kidRecord.getStateSciAssessment().equalsIgnoreCase("C")) {
				testTypeSubjectArea.setClear(true);
			} else {
				testTypeSubjectArea
						.setTestTypeId(testTypesMap.get("SSCIA-" + kidRecord.getStateSciAssessment()).getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingInd1Sci());
				testTypeSubjectArea.setGroupingInd2(kidRecord.getGroupingInd2Sci());
			}
			testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("SSCIA")).getId());
			testTypeSubjectArea.setSubjectAreaCode("SSCIA");
			kidRecord.getReceivedSubjectCodes().add("Sci");
			testTypeSubjectAreas.add(testTypeSubjectArea);
		}

		if (kidRecord.getStateHistGovAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment())
				&& Arrays.asList(new String[] { "2", "C" }).indexOf(kidRecord.getStateHistGovAssessment()) >= 0) {

			EnrollmentTestTypeSubjectArea testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
			if (kidRecord.getStateHistGovAssessment().equalsIgnoreCase("C")) {
				testTypeSubjectArea.setClear(true);
			} else {
				testTypeSubjectArea
						.setTestTypeId(testTypesMap.get("SHISGOVA-" + kidRecord.getStateHistGovAssessment()).getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingInd1HistGov());
				testTypeSubjectArea.setGroupingInd2(kidRecord.getGroupingInd2HistGov());
			}
			testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("SHISGOVA")).getId());
			testTypeSubjectArea.setSubjectAreaCode("SHISGOVA");
			kidRecord.getReceivedSubjectCodes().add("SS");
			testTypeSubjectAreas.add(testTypeSubjectArea);
		}

		//GKS: General Knowledge and Skills (Comprehensive)
		if (kidRecord.getGeneralCTEAssessment() != null && StringUtils.isNotEmpty(kidRecord.getGeneralCTEAssessment())
				&& Arrays.asList(new String[] { "1", "3", "6", "C" }).indexOf(kidRecord.getGeneralCTEAssessment()) >= 0) {
			EnrollmentTestTypeSubjectArea testTypeSubjectArea = null;
			//kidRecord.getReceivedSubjectCodes().add("GKS");
			if ("C".equalsIgnoreCase(kidRecord.getGeneralCTEAssessment())) {//Clear
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("GKS")).getId());
				testTypeSubjectArea.setClear(true);
				testTypeSubjectAreas.add(testTypeSubjectArea);
			} else if("1".equalsIgnoreCase(kidRecord.getGeneralCTEAssessment())){//Stage1 only
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("GKS")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("GKS-2").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingInd1CTE());
				testTypeSubjectArea.setGroupingInd2(kidRecord.getGroupingInd2CTE());
				testTypeSubjectArea.setSubjectAreaCode("GKS");				
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("3".equalsIgnoreCase(kidRecord.getGeneralCTEAssessment())){//Stage1 && Performance
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("GKS")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("GKS-2").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingInd1CTE());
				testTypeSubjectArea.setGroupingInd2(kidRecord.getGroupingInd2CTE());
				testTypeSubjectArea.setSubjectAreaCode("GKS");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("GKS")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("GKS-2Q").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingInd1CTE());
				testTypeSubjectArea.setGroupingInd2(kidRecord.getGroupingInd2CTE());
				testTypeSubjectArea.setSubjectAreaCode("GKS");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("6".equalsIgnoreCase(kidRecord.getGeneralCTEAssessment())){//Performance only
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("GKS")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("GKS-2Q").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingInd1CTE());
				testTypeSubjectArea.setGroupingInd2(kidRecord.getGroupingInd2CTE());
				testTypeSubjectArea.setSubjectAreaCode("GKS");
				testTypeSubjectAreas.add(testTypeSubjectArea);
			}
		}

		//AGF&NR: Agriculture, Food, and Natural Resources (Comprehensive Agriculture)
		if (kidRecord.getComprehensiveAgAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getComprehensiveAgAssessment())
				&& Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7", "C" })
						.indexOf(kidRecord.getComprehensiveAgAssessment()) >= 0) {
			EnrollmentTestTypeSubjectArea testTypeSubjectArea = null;
			//kidRecord.getReceivedSubjectCodes().add("AgF&NR");
			
			if ("C".equalsIgnoreCase(kidRecord.getComprehensiveAgAssessment())) {//Clear 
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setClear(true);
				testTypeSubjectAreas.add(testTypeSubjectArea);
			} else if("1".equalsIgnoreCase(kidRecord.getComprehensiveAgAssessment())){//Stage1 only
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-A").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveAg());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("2".equalsIgnoreCase(kidRecord.getComprehensiveAgAssessment())){//Stage1 && Stage2
				//Stage1
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-A").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveAg());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				//Stage2
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-AM").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveAg());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("3".equalsIgnoreCase(kidRecord.getComprehensiveAgAssessment())){//Stage1 && Performance
				//Stage1
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-A").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveAg());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				//Performance
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-AQ").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveAg());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("4".equalsIgnoreCase(kidRecord.getComprehensiveAgAssessment())){//All: Stage1, Stage2 && Performance
				//Stage1
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-A").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveAg());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				//Stage2
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-AM").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveAg());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				//Performance
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-AQ").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveAg());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("5".equalsIgnoreCase(kidRecord.getComprehensiveAgAssessment())){//Stage2 only
				//Stage2
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-AM").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveAg());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("6".equalsIgnoreCase(kidRecord.getComprehensiveAgAssessment())){//Performance only
				//Performance
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-AQ").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveAg());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("7".equalsIgnoreCase(kidRecord.getComprehensiveAgAssessment())){//Stage2 && Performance
				//Stage2
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-AM").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveAg());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				//Performance
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-AQ").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveAg());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
			}				
			
		}

		//AGF&NR: Agriculture, Food, and Natural Resources (Animal Systems)
		if (kidRecord.getAnimalSystemsAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getAnimalSystemsAssessment())
				&& Arrays.asList(new String[] { "1", "3", "6", "C" }).indexOf(kidRecord.getAnimalSystemsAssessment()) >= 0) {

			EnrollmentTestTypeSubjectArea testTypeSubjectArea = null;
			//kidRecord.getReceivedSubjectCodes().add("AgF&NR");
			
			if ("C".equalsIgnoreCase(kidRecord.getAnimalSystemsAssessment())) {//Clear
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId(subjectAreaMap.get("AgF&NR").getId());
				testTypeSubjectArea.setClear(true);
				testTypeSubjectAreas.add(testTypeSubjectArea);
			} else if("1".equalsIgnoreCase(kidRecord.getAnimalSystemsAssessment())){//Stage1 only
				//Stage1
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId(subjectAreaMap.get("AgF&NR").getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-B").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingAnimalSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("3".equalsIgnoreCase(kidRecord.getAnimalSystemsAssessment())){//Stage1 && Performance
				//Stage1
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId(subjectAreaMap.get("AgF&NR").getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-B").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingAnimalSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				//Performance
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId(subjectAreaMap.get("AgF&NR").getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-BQ").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingAnimalSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("6".equalsIgnoreCase(kidRecord.getAnimalSystemsAssessment())){//Performance only
				//Performance
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId(subjectAreaMap.get("AgF&NR").getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-BQ").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingAnimalSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
			}				
			
		}

		//AGF&NR: Agriculture, Food, and Natural Resources (Plant Systems)
		if (kidRecord.getPlantSystemsAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getPlantSystemsAssessment())
				&& Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7", "C" })
						.indexOf(kidRecord.getPlantSystemsAssessment()) >= 0) {
			EnrollmentTestTypeSubjectArea testTypeSubjectArea = null;
			//kidRecord.getReceivedSubjectCodes().add("AgF&NR");
			
			if ("C".equalsIgnoreCase(kidRecord.getPlantSystemsAssessment())) {//Clear
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setClear(true);
				testTypeSubjectAreas.add(testTypeSubjectArea);
			} else if("1".equalsIgnoreCase(kidRecord.getPlantSystemsAssessment())){//Stage1 only
				//Stage1
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-D").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingPlantSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("2".equalsIgnoreCase(kidRecord.getPlantSystemsAssessment())){//Stage1 && Stage2
				//Stage1
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-D").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingPlantSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				//Stage2
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-DM").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingPlantSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("3".equalsIgnoreCase(kidRecord.getPlantSystemsAssessment())){//Stage1 && Performance
				//Stage1
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-D").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingPlantSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				//Performance
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-DQ").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingPlantSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("4".equalsIgnoreCase(kidRecord.getPlantSystemsAssessment())){//All: Stage1, Stage2 && Performance
				//Stage1
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-D").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingPlantSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				//Stage2
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-DM").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingPlantSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				//Performance
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-DQ").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingPlantSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("5".equalsIgnoreCase(kidRecord.getPlantSystemsAssessment())){//Stage2 only
				//Stage2
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-DM").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingPlantSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("6".equalsIgnoreCase(kidRecord.getPlantSystemsAssessment())){//Performance only
				//Performance
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-DQ").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingPlantSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}else if("7".equalsIgnoreCase(kidRecord.getPlantSystemsAssessment())){//Stage2 && Performance
				//Stage2
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-DM").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingPlantSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				//Performance
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("AgF&NR")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("AgF&NR-DQ").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingPlantSystems());
				testTypeSubjectArea.setSubjectAreaCode("AgF&NR");
				testTypeSubjectAreas.add(testTypeSubjectArea);
			}
			
		}

		//MFG: Manufacturing
		if (kidRecord.getManufacturingProdAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getManufacturingProdAssessment())
				&& Arrays.asList(new String[] { "1", "C" })
						.indexOf(kidRecord.getManufacturingProdAssessment()) >= 0) {

			EnrollmentTestTypeSubjectArea testTypeSubjectArea = null;
			//kidRecord.getReceivedSubjectCodes().add("Mfg");
			
			if ("C".equalsIgnoreCase(kidRecord.getManufacturingProdAssessment())) {//Clear
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId(subjectAreaMap.get("Mfg").getId());
				testTypeSubjectArea.setClear(true);
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			} else if("1".equalsIgnoreCase(kidRecord.getManufacturingProdAssessment())){//Stage1 only
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId(subjectAreaMap.get("Mfg").getId());				
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("Mfg-E").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingManufacturingProd());
				testTypeSubjectArea.setSubjectAreaCode("Mfg");
				testTypeSubjectAreas.add(testTypeSubjectArea);				
			}
		}

		//ARCH&CONST: Architecture & Construction (Design and Pre-Construction)
		if (kidRecord.getDesignPreConstructionAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getDesignPreConstructionAssessment())
				&& Arrays.asList(new String[] { "1", "3", "C" })
						.indexOf(kidRecord.getDesignPreConstructionAssessment()) >= 0) {

			EnrollmentTestTypeSubjectArea testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
			//kidRecord.getReceivedSubjectCodes().add("Arch&Const");
			
			if ("C".equalsIgnoreCase(kidRecord.getDesignPreConstructionAssessment())) {//Clear
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId(subjectAreaMap.get("Arch&Const").getId());
				testTypeSubjectArea.setClear(true);
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			} else if("1".equalsIgnoreCase(kidRecord.getDesignPreConstructionAssessment())){//Stage1 only
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId(subjectAreaMap.get("Arch&Const").getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("Arch&Const-F").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingDesignPreConstruction());
				testTypeSubjectArea.setSubjectAreaCode("Arch&Const");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			}
		}

		//BM&A: Business Management and Administration (Comprehensive Business)
		if (kidRecord.getComprehensiveBusinessAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getComprehensiveBusinessAssessment())
				&& Arrays.asList(new String[] { "1", "C" })
						.indexOf(kidRecord.getComprehensiveBusinessAssessment()) >= 0) {

			EnrollmentTestTypeSubjectArea testTypeSubjectArea = null;
			//kidRecord.getReceivedSubjectCodes().add("BM&A");
			
			if ("C".equalsIgnoreCase(kidRecord.getComprehensiveBusinessAssessment())) {//Clear
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId(subjectAreaMap.get("BM&A").getId());
				testTypeSubjectArea.setClear(true);
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			} else if("1".equalsIgnoreCase(kidRecord.getComprehensiveBusinessAssessment())){//Stage1 only
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId(subjectAreaMap.get("BM&A").getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("BM&A-G").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingComprehensiveBusiness());
				testTypeSubjectArea.setSubjectAreaCode("BM&A");
				testTypeSubjectAreas.add(testTypeSubjectArea);				
			}
		}

		//BM&A: Business Management and Administration (Finance)
		if (kidRecord.getFinanceAssessment() != null && StringUtils.isNotEmpty(kidRecord.getFinanceAssessment())
				&& Arrays.asList(new String[] { "1", "2", "5", "C" })
						.indexOf(kidRecord.getFinanceAssessment()) >= 0) {
			EnrollmentTestTypeSubjectArea testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
			//kidRecord.getReceivedSubjectCodes().add("BM&A");
			
			if ("C".equalsIgnoreCase(kidRecord.getFinanceAssessment())) {//Clear
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("BM&A")).getId());
				testTypeSubjectArea.setClear(true);
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			} else if("1".equalsIgnoreCase(kidRecord.getFinanceAssessment())){//Stage1 only
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("BM&A")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("BM&A-H").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingFinance());
				testTypeSubjectArea.setSubjectAreaCode("BM&A");
				testTypeSubjectAreas.add(testTypeSubjectArea);
			} else if("2".equalsIgnoreCase(kidRecord.getFinanceAssessment())){//Stage1 && Stage2
				//Stage1
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("BM&A")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("BM&A-H").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingFinance());
				testTypeSubjectArea.setSubjectAreaCode("BM&A");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
				//Stage2
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("BM&A")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("BM&A-HM").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingFinance());
				testTypeSubjectArea.setSubjectAreaCode("BM&A");
				testTypeSubjectAreas.add(testTypeSubjectArea);
				
			} else if("5".equalsIgnoreCase(kidRecord.getFinanceAssessment())){//Stage2 only
				//Stage2
				testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
				testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("BM&A")).getId());
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("BM&A-HM").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingFinance());
				testTypeSubjectArea.setSubjectAreaCode("BM&A");
				testTypeSubjectAreas.add(testTypeSubjectArea);
			}
			
		}

		if (kidRecord.getElpa21Assessment() != null && StringUtils.isNotEmpty(kidRecord.getElpa21Assessment())
				&& Arrays.asList(new String[] { "1", "2", "C" }).indexOf(kidRecord.getElpa21Assessment()) >= 0) {

			EnrollmentTestTypeSubjectArea testTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
			kidRecord.getReceivedSubjectCodes().add("ELP");
			
			if (kidRecord.getElpa21Assessment().equalsIgnoreCase("C")) {
				testTypeSubjectArea.setClear(true);
			} else {
				testTypeSubjectArea.setTestTypeId(testTypesMap.get("D83-2").getId());
				testTypeSubjectArea.setGroupingInd1(kidRecord.getGroupingInd1Elpa21());
				testTypeSubjectArea.setGroupingInd2(kidRecord.getGroupingInd2Elpa21());
			}
			testTypeSubjectArea.setSubjectareaId((subjectAreaMap.get("D83")).getId());
			testTypeSubjectArea.setSubjectAreaCode("D83");
			testTypeSubjectAreas.add(testTypeSubjectArea);

		}
		return testTypeSubjectAreas;
	}
	
	/**
	 * Validates received TEST record is valid or not
	 * @param kidRecord
	 * @return boolean
	 */
	public final boolean isValidTestRecord(KidRecord kidRecord) {
		boolean isValidTestRecord = false;
		if (kidRecord.getStateMathAssess() != null && StringUtils.isNotEmpty(kidRecord.getStateMathAssess())
				&& !"0".equals(kidRecord.getStateMathAssess())) {
			isValidTestRecord = true;
		} else if (kidRecord.getStateELAAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getStateELAAssessment())
				&& !"0".equals(kidRecord.getStateELAAssessment())) {
			isValidTestRecord = true;
		} else if (kidRecord.getStateSciAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getStateSciAssessment())
				&& !"0".equals(kidRecord.getStateSciAssessment())) {
			isValidTestRecord = true;
		} else if (kidRecord.getStateHistGovAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment())
				&& !"0".equals(kidRecord.getStateHistGovAssessment())) {
			isValidTestRecord = true;
		} else if (kidRecord.getGeneralCTEAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getGeneralCTEAssessment())
				&& (Arrays.asList(new String[] { "1", "3", "6", "C" }).indexOf(kidRecord.getGeneralCTEAssessment()) >= 0
						|| Arrays.asList(new String[] { "2", "2Q", "C" })
								.indexOf(kidRecord.getGeneralCTEAssessment()) >= 0)) {
			isValidTestRecord = true;
		} else if (kidRecord.getComprehensiveAgAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getComprehensiveAgAssessment())
				&& (Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7","C" })
						.indexOf(kidRecord.getComprehensiveAgAssessment()) >= 0
						|| Arrays.asList(new String[] { "A", "AM", "AQ", "C" })
								.indexOf(kidRecord.getComprehensiveAgAssessment()) >= 0)) {
			isValidTestRecord = true;
		} else if (kidRecord.getAnimalSystemsAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getAnimalSystemsAssessment())
				&& (Arrays.asList(new String[] { "1", "3", "6", "C" }).indexOf(kidRecord.getAnimalSystemsAssessment()) >= 0
						|| Arrays.asList(new String[] { "B", "BQ", "C" })
								.indexOf(kidRecord.getAnimalSystemsAssessment()) >= 0)) {
			isValidTestRecord = true;
		} else if (kidRecord.getPlantSystemsAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getPlantSystemsAssessment())
				&& (Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7", "C" })
						.indexOf(kidRecord.getPlantSystemsAssessment()) >= 0
						|| Arrays.asList(new String[] { "D", "DQ", "DM", "C" })
								.indexOf(kidRecord.getPlantSystemsAssessment()) >= 0)) {
			isValidTestRecord = true;
		} else if (kidRecord.getManufacturingProdAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getManufacturingProdAssessment())
				&& (Arrays.asList(new String[] { "1", "C" })
						.indexOf(kidRecord.getManufacturingProdAssessment()) >= 0
						|| Arrays.asList(new String[] { "E", "C" })
								.indexOf(kidRecord.getManufacturingProdAssessment()) >= 0)) {
			isValidTestRecord = true;
		} else if (kidRecord.getDesignPreConstructionAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getDesignPreConstructionAssessment())
				&& (Arrays.asList(new String[] { "1", "C" })
						.indexOf(kidRecord.getDesignPreConstructionAssessment()) >= 0
						|| Arrays.asList(new String[] { "F", "C" })
								.indexOf(kidRecord.getDesignPreConstructionAssessment()) >= 0)) {
			isValidTestRecord = true;
		} else if (kidRecord.getComprehensiveBusinessAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getComprehensiveBusinessAssessment())
				&& (Arrays.asList(new String[] { "1", "C" })
						.indexOf(kidRecord.getComprehensiveBusinessAssessment()) >= 0
						|| Arrays.asList(new String[] { "G", "C" })
								.indexOf(kidRecord.getComprehensiveBusinessAssessment()) >= 0)) {
			isValidTestRecord = true;
		} else if (kidRecord.getFinanceAssessment() != null && StringUtils.isNotEmpty(kidRecord.getFinanceAssessment())
				&& (Arrays.asList(new String[] { "1", "2", "5", "C" })
						.indexOf(kidRecord.getFinanceAssessment()) >= 0
						|| Arrays.asList(new String[] { "H", "HM", "C" })
								.indexOf(kidRecord.getFinanceAssessment()) >= 0)) {
			isValidTestRecord = true;
		} else if (kidRecord.getElpa21Assessment() != null && StringUtils.isNotEmpty(kidRecord.getElpa21Assessment())
				&& Arrays.asList(new String[] { "1", "2", "C" }).indexOf(kidRecord.getElpa21Assessment()) >= 0) {
			isValidTestRecord = true;
		}

		return isValidTestRecord;
	}
	
	/**
	 * Identifies the kid record is KAP or not
	 * @param kidRecord
	 * @return boolean
	 */
	public final boolean isKAPKid(KidRecord kidRecord) {
		boolean isKAPKid = false;
		if (kidRecord.getStateMathAssess() != null && StringUtils.isNotEmpty(kidRecord.getStateMathAssess())
				&& "2".equals(kidRecord.getStateMathAssess())) {
			isKAPKid = true;
		}

		if (kidRecord.getStateELAAssessment() != null && StringUtils.isNotEmpty(kidRecord.getStateELAAssessment())
				&& "2".equals(kidRecord.getStateELAAssessment())) {
			isKAPKid = true;
		}

		if (kidRecord.getStateSciAssessment() != null && StringUtils.isNotEmpty(kidRecord.getStateSciAssessment())
				&& "2".equals(kidRecord.getStateSciAssessment())) {
			isKAPKid = true;
		}

		if (kidRecord.getStateHistGovAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment())
				&& "2".equals(kidRecord.getStateHistGovAssessment())) {
			isKAPKid = true;
		}
		return isKAPKid;
	}
	
	
	/**
	 * Check for HGSS subject presence in incoming TEST record 
	 * @param kidRecord
	 * @return boolean
	 */
	public final boolean historySubjectExists(KidRecord kidRecord){
		boolean historyExists = false;
		if (kidRecord.getStateHistGovAssessment() != null && 
				StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment()) && 
				"2".equals(kidRecord.getStateHistGovAssessment())) {
			historyExists = true;
		}
		
		return historyExists;
	}
	
	/**
	 * Identifies the TEST record has ELPA or not
	 * @param kidRecord
	 * @return
	 */
	public final boolean isKELPAKid(KidRecord kidRecord) {
		boolean isKELPAKid = false;
		if (kidRecord.getElpa21Assessment() != null && StringUtils.isNotEmpty(kidRecord.getElpa21Assessment())
				&& Arrays.asList(new String[] { "1", "2" }).indexOf(kidRecord.getElpa21Assessment()) >= 0) {
			isKELPAKid = true;
		}
		return isKELPAKid;
	}
	
	/**
	 * Identifies the TEST record has CPASS or not
	 * @param kidRecord
	 * @return
	 */
	public final boolean isCpassKid(KidRecord kidRecord) {
		boolean cpassKid = false;

		if (kidRecord.getGeneralCTEAssessment() != null && StringUtils.isNotEmpty(kidRecord.getGeneralCTEAssessment())
				&& (Arrays.asList(new String[] { "1", "3", "6" }).indexOf(kidRecord.getGeneralCTEAssessment()) >= 0 || Arrays
						.asList(new String[] { "2", "2Q" }).indexOf(kidRecord.getGeneralCTEAssessment()) >= 0)) {
			cpassKid = true;
		}
		if (kidRecord.getComprehensiveAgAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getComprehensiveAgAssessment())
				&& (Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7" })
						.indexOf(kidRecord.getComprehensiveAgAssessment()) >= 0
						|| Arrays.asList(new String[] { "A", "AM", "AQ" })
								.indexOf(kidRecord.getComprehensiveAgAssessment()) >= 0)) {
			cpassKid = true;
		}
		if (kidRecord.getAnimalSystemsAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getAnimalSystemsAssessment())
				&& (Arrays.asList(new String[] { "1", "3", "6" }).indexOf(kidRecord.getAnimalSystemsAssessment()) >= 0
						|| Arrays.asList(new String[] { "B", "BQ" })
								.indexOf(kidRecord.getAnimalSystemsAssessment()) >= 0)) {
			cpassKid = true;
		}
		if (kidRecord.getPlantSystemsAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getPlantSystemsAssessment())
				&& (Arrays.asList(new String[] { "1", "2", "3", "4", "5", "6", "7" })
						.indexOf(kidRecord.getPlantSystemsAssessment()) >= 0
						|| Arrays.asList(new String[] { "D", "DQ", "DM" })
								.indexOf(kidRecord.getPlantSystemsAssessment()) >= 0)) {
			cpassKid = true;
		}
		if (kidRecord.getManufacturingProdAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getManufacturingProdAssessment())
				&& (Arrays.asList(new String[] { "1"}).indexOf(kidRecord.getManufacturingProdAssessment()) >= 0
						|| Arrays.asList(new String[] { "E"})
								.indexOf(kidRecord.getManufacturingProdAssessment()) >= 0)) {
			cpassKid = true;
		}
		if (kidRecord.getDesignPreConstructionAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getDesignPreConstructionAssessment())
				&& (Arrays.asList(new String[] { "1"})
						.indexOf(kidRecord.getDesignPreConstructionAssessment()) >= 0
						|| Arrays.asList(new String[] { "F"})
								.indexOf(kidRecord.getDesignPreConstructionAssessment()) >= 0)) {
			cpassKid = true;
		}
		if (kidRecord.getComprehensiveBusinessAssessment() != null
				&& StringUtils.isNotEmpty(kidRecord.getComprehensiveBusinessAssessment())
				&& (Arrays.asList(new String[] { "1"})
						.indexOf(kidRecord.getComprehensiveBusinessAssessment()) >= 0
						|| Arrays.asList(new String[] { "G"})
								.indexOf(kidRecord.getComprehensiveBusinessAssessment()) >= 0)) {
			cpassKid = true;
		}
		if (kidRecord.getFinanceAssessment() != null && StringUtils.isNotEmpty(kidRecord.getFinanceAssessment())
				&& (Arrays.asList(new String[] { "1", "2", "5"}).indexOf(kidRecord.getFinanceAssessment()) >= 0
						|| Arrays.asList(new String[] { "H", "HM" })
								.indexOf(kidRecord.getFinanceAssessment()) >= 0)) {
			cpassKid = true;
		}

		return cpassKid;
	}
	
	/**
	 * Add or Update enrollment
	 * @param newEnrollment
	 * @return enrollment
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private final Enrollment addOrUpdate(Enrollment newEnrollment) {
		if (newEnrollment != null) {
			if (newEnrollment.getId() == 0) {
				newEnrollment = enrollmentService.add(newEnrollment);
				newEnrollment.setSaveStatus(RecordSaveStatus.ENROLLMENT_ADDED);
			} else {
				newEnrollment = enrollmentService.update(newEnrollment);
				newEnrollment.setSaveStatus(RecordSaveStatus.ENROLLMENT_UPDATED);
			}
		}
		return newEnrollment;
	}

	/**
	 * Inactivate EnrollmentTestType
	 * @param enrollment
	 * @param user
	 * @param ettsa
	 * @return
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private int inactivateEnrollmentTestType(Enrollment enrollment, User user, EnrollmentTestTypeSubjectArea ettsa) {

		EnrollmentTestTypeSubjectArea ettsaInactive = new EnrollmentTestTypeSubjectArea();
		ettsaInactive.setEnrollmentId(enrollment.getId());
		ettsaInactive.setActiveFlag(false);
		ettsaInactive.setCurrentContextUserId(user.getId());
		ettsa.setAuditColumnPropertiesForUpdate();
		EnrollmentTestTypeSubjectAreaExample enrollmentTestTypeSubjectAreaExample = new EnrollmentTestTypeSubjectAreaExample();
		EnrollmentTestTypeSubjectAreaExample.Criteria enrollmentTestTypeSubjectAreaCriteria = enrollmentTestTypeSubjectAreaExample
				.createCriteria();
		enrollmentTestTypeSubjectAreaCriteria.andEnrollmentIdEqualTo(enrollment.getId());
		enrollmentTestTypeSubjectAreaCriteria.andSubjectareaIdEqualTo(ettsa.getSubjectareaId());
		enrollmentTestTypeSubjectAreaCriteria.andTestTypeIdEqualTo(ettsa.getTestTypeId());
		int updatedValue = enrollmentTestTypeSubjectAreaDao.updateByExampleSelective(ettsaInactive,
				enrollmentTestTypeSubjectAreaExample);
		return updatedValue;
	}

	/**
	 * 
	 * @param enrollment
	 * @param user
	 * @param testTypeId
	 * @param subjectAreaId
	 * @param groupingInd1
	 * @param groupingInd2
	 * @param clearTestTypeId
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addOrUpdateEnrollmentTestType(Enrollment enrollment, User user, Long testTypeId, Long subjectAreaId,
			String groupingInd1, String groupingInd2, Long clearTestTypeId) {

		EnrollmentTestTypeSubjectArea ettsa = new EnrollmentTestTypeSubjectArea();

		ettsa.setEnrollmentId(enrollment.getId());
		ettsa.setSubjectareaId(subjectAreaId);
		ettsa.setGroupingInd1(groupingInd1);
		ettsa.setGroupingInd2(groupingInd2);
		ettsa.setCurrentContextUserId(user.getId());
		ettsa.setTestTypeId(testTypeId);
		if (clearTestTypeId != null) {
			ettsa.setTestTypeId(clearTestTypeId);
			ettsa.setActiveFlag(false);
		}

		ettsa.setAuditColumnPropertiesForUpdate();
		EnrollmentTestTypeSubjectAreaExample enrollmentTestTypeSubjectAreaExample = new EnrollmentTestTypeSubjectAreaExample();
		EnrollmentTestTypeSubjectAreaExample.Criteria enrollmentTestTypeSubjectAreaCriteria = enrollmentTestTypeSubjectAreaExample
				.createCriteria();
		enrollmentTestTypeSubjectAreaCriteria.andEnrollmentIdEqualTo(enrollment.getId());
		enrollmentTestTypeSubjectAreaCriteria.andSubjectareaIdEqualTo(subjectAreaId);
		enrollmentTestTypeSubjectAreaCriteria.andTestTypeIdEqualTo(testTypeId);
		Integer updatedValue = enrollmentTestTypeSubjectAreaDao.updateByExampleSelective(ettsa,
				enrollmentTestTypeSubjectAreaExample);

		if (null == clearTestTypeId && (updatedValue == null || updatedValue < 1)) {
			ettsa.setAuditColumnProperties();
			enrollmentTestTypeSubjectAreaDao.insert(ettsa);
		}
	}
	
	/**
	 * Add or Update EnrollmentTestType
	 * @param enrollment
	 * @param user
	 * @param ettsa
	 * @param clearTestTypeId
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addOrUpdateEnrollmentTestType(Enrollment enrollment, User user, EnrollmentTestTypeSubjectArea ettsa,
			Long clearTestTypeId) {
		addOrUpdateEnrollmentTestType(enrollment, user, ettsa.getTestTypeId(), ettsa.getSubjectareaId(),
				ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), null);
	}

	/**
	 * Process Clear TestTypes
	 * @param kidRecord
	 * @param enrollment
	 * @param user
	 * @param testTypesMap
	 * @param subjectAreaIdMap
	 * @param ettsa
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final void processClearTestTypes(KidRecord kidRecord, Enrollment enrollment, User user,
			Map<String, TestType> testTypesMap, Map<Long, SubjectArea> subjectAreaIdMap,
			EnrollmentTestTypeSubjectArea ettsa) {
		Long clearTestTypeId = testTypesMap.get("C").getId();
		SubjectArea subjectArea = (SubjectArea) subjectAreaIdMap.get(ettsa.getSubjectareaId());
		switch (subjectArea.getSubjectAreaCode()) {
		case "D74":
			if (ettsa.isClear()) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("D74-2").getTestTypeCode(),
						subjectArea.getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("D74-3").getTestTypeCode(),
						subjectArea.getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("D74-GN").getTestTypeCode(),
						subjectArea.getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("D74-P").getTestTypeCode(),
						subjectArea.getSubjectAreaCode());

				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("D74-2").getId(), subjectArea.getId(),
						ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("D74-3").getId(), subjectArea.getId(),
						ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("D74-GN").getId(), subjectArea.getId(),
						ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("D74-P").getId(), subjectArea.getId(),
						ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
			}
			break;
			
		case "SELAA":
			if (ettsa.isClear()) {
				studentsTestsService.removeTestSessionsOnClear(enrollment,
						testTypesMap.get("SELAA-2").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment,
						testTypesMap.get("SELAA-3").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment,
						testTypesMap.get("SELAA-GN").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment,
						testTypesMap.get("SELAA-P").getTestTypeCode(), subjectArea.getSubjectAreaCode());

				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SELAA-2").getId(),
						subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SELAA-3").getId(),
						subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SELAA-GN").getId(),
						subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SELAA-P").getId(),
						subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
			}
			break;
			
		case "SSCIA":
			if (ettsa.isClear()) {
				studentsTestsService.removeTestSessionsOnClear(enrollment,
						testTypesMap.get("SSCIA-2").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment,
						testTypesMap.get("SSCIA-3").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment,
						testTypesMap.get("SSCIA-GN").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment,
						testTypesMap.get("SSCIA-P").getTestTypeCode(), subjectArea.getSubjectAreaCode());

				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SSCIA-2").getId(),
						subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SSCIA-3").getId(),
						subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SSCIA-GN").getId(),
						subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SSCIA-P").getId(),
						subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
			}
			break;
			
		case "SHISGOVA":
			if (ettsa.isClear()) {
				scoringAssignmentService.reassignStudentsOnRosterChangeKapSS(enrollment.getId(), null, null);
				studentsTestsService.removeTestSessionsOnClear(enrollment,
						testTypesMap.get("SHISGOVA-2").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment,
						testTypesMap.get("SHISGOVA-3").getTestTypeCode(), subjectArea.getSubjectAreaCode());

				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SHISGOVA-2").getId(),
						subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SHISGOVA-3").getId(),
						subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);				
				
				//remove rosters
				ContentArea contentArea = contentAreaService.findByAbbreviatedName("SS");				
				List<Roster> rosters = getRostersAssociatedWithEnrollment(enrollment, contentArea.getId());				
		        if(rosters != null && rosters.size() > 0){
		        	for(Roster roster: rosters){
			        	inactivateEnrollmentRoster(kidRecord.getCurrentContextUserId(), roster, enrollment.getId());						
		        	}
		        }
				
			}
			break;
			
		case "GKS":
			if (ettsa.isClear()) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("GKS-2").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("GKS-2Q").getTestTypeCode(), subjectArea.getSubjectAreaCode());

				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("GKS-2").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("GKS-2Q").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
			} else if ("1".equals(kidRecord.getGeneralCTEAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("GKS-2Q").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("GKS-2Q").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
			}else if("6".equalsIgnoreCase(kidRecord.getGeneralCTEAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("GKS-2").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("GKS-2").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
			}
			break;
			
		case "AgF&NR":
			if (StringUtils.isNotEmpty(kidRecord.getComprehensiveAgAssessment())) {
				if ("C".equals(kidRecord.getComprehensiveAgAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-A").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AM").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AQ").getTestTypeCode(), subjectArea.getSubjectAreaCode());

					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-A").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AM").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AQ").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				} else if ("1".equals(kidRecord.getComprehensiveAgAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AM").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AQ").getTestTypeCode(), subjectArea.getSubjectAreaCode());

					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AM").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AQ").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				
				} else if ("2".equals(kidRecord.getComprehensiveAgAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AQ").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AQ").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				
				} else if ("3".equals(kidRecord.getComprehensiveAgAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AM").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AM").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);

				} else if ("5".equals(kidRecord.getComprehensiveAgAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-A").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AQ").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-A").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AQ").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					
				} else if ("6".equals(kidRecord.getComprehensiveAgAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-A").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AM").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-A").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AM").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);

				} else if ("7".equals(kidRecord.getComprehensiveAgAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-A").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-A").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				}
			} 
			if (StringUtils.isNotEmpty(kidRecord.getAnimalSystemsAssessment())) {
				if ("C".equals(kidRecord.getAnimalSystemsAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-B").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-BQ").getTestTypeCode(), subjectArea.getSubjectAreaCode());

					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-B").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-BQ").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				
				} else if ("1".equals(kidRecord.getAnimalSystemsAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-BQ").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-BQ").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				
				} else if ("6".equals(kidRecord.getAnimalSystemsAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-B").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-B").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				}
			} 
			if (StringUtils.isNotEmpty(kidRecord.getPlantSystemsAssessment())) {
				if ("C".equals(kidRecord.getPlantSystemsAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-D").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DQ").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DM").getTestTypeCode(), subjectArea.getSubjectAreaCode());

					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-D").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DQ").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DM").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				} else if ("1".equals(kidRecord.getPlantSystemsAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DQ").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DM").getTestTypeCode(), subjectArea.getSubjectAreaCode());

					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DQ").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DM").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				} else if ("2".equals(kidRecord.getPlantSystemsAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DQ").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DQ").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				
				} else if ("3".equals(kidRecord.getPlantSystemsAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DM").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DM").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);

				} else if ("5".equals(kidRecord.getPlantSystemsAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-D").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DQ").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-D").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DQ").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					
				} else if ("6".equals(kidRecord.getPlantSystemsAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-D").getTestTypeCode(), subjectArea.getSubjectAreaCode());					
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DM").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-D").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DM").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);

				} else if ("7".equals(kidRecord.getPlantSystemsAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-D").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-D").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				}
			}
			break;
			
		case "Mfg":
			if ("C".equals(kidRecord.getManufacturingProdAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("Mfg-E").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("Mfg-E").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
			}
			break;
			
		case "Arch&Const":
			if ("C".equals(kidRecord.getDesignPreConstructionAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("Arch&Const-F").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("Arch&Const-F").getId(), subjectArea.getId(), ettsa.getGroupingInd1(), ettsa.getGroupingInd2(), clearTestTypeId);
				
			}
			break;
			
		case "BM&A":
			if (StringUtils.isNotEmpty(kidRecord.getComprehensiveBusinessAssessment())) {
				if ("C".equals(kidRecord.getComprehensiveBusinessAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-G").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-G").getId(), subjectArea.getId(), kidRecord.getGroupingComprehensiveBusiness(), null, clearTestTypeId);
				}
			} 
			if (StringUtils.isNotEmpty(kidRecord.getFinanceAssessment())) {
				if ("C".equals(kidRecord.getFinanceAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-H").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-HM").getTestTypeCode(), subjectArea.getSubjectAreaCode());

					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-H").getId(), subjectArea.getId(), kidRecord.getGroupingFinance(), null, clearTestTypeId);
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-HM").getId(), subjectArea.getId(), kidRecord.getGroupingFinance(), null, clearTestTypeId);
				} else if ("1".equals(kidRecord.getFinanceAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-HM").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-HM").getId(), subjectArea.getId(), kidRecord.getGroupingFinance(), null, clearTestTypeId);
					
				} else if ("5".equals(kidRecord.getFinanceAssessment())) {
					studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-H").getTestTypeCode(), subjectArea.getSubjectAreaCode());
					addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-H").getId(), subjectArea.getId(), kidRecord.getGroupingFinance(), null, clearTestTypeId);
				}
			}			
			break;
			
		case "D83":
			if (ettsa.isClear()) {
				scoringAssignmentService.reAssignStudentsOnExitStudent(kidRecord.getEnrollment().getId(), new Date(), user);
				studentsTestsService.removeKelpaTestSessionsOnClear(enrollment,
						testTypesMap.get("D83-2").getTestTypeCode(), subjectArea.getSubjectAreaCode());
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("D83-2").getId(),
						subjectArea.getId(), kidRecord.getGroupingInd1Elpa21(), kidRecord.getGroupingInd2Elpa21(),
						clearTestTypeId);
				
				//remove rosters
				ContentArea contentArea = contentAreaService.findByAbbreviatedName("ELP");				
				List<Roster> rosters = getRostersAssociatedWithEnrollment(enrollment, contentArea.getId());				
		        if(rosters != null && rosters.size() > 0){
		        	for(Roster roster: rosters){
			        	inactivateEnrollmentRoster(kidRecord.getCurrentContextUserId(), roster, enrollment.getId());						
		        	}
		        }
			}
			break;

		}
	}

	/**
	 * 
	 * @param kidRecord
	 * @param existedExrollment
	 * @param enrollments
	 * @param user
	 * @param ettsa
	 * @param isSubAreaRequired
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void transferSubjectSchool(KidRecord kidRecord, Enrollment existedExrollment, List<Enrollment> enrollments,
			User user, EnrollmentTestTypeSubjectArea ettsa, boolean isSubAreaRequired, boolean isKelpa, List<Long> contentAreaIdsMatchedWithKidsTestRecords, boolean isKapHGSS, Map<Long, TestType> testTypeIdMap) {
		Enrollment newEnrollment = null; 
		// Verify whether the enrollment record already exists with the given
		// schools, use that enrollment instead of creating new.
		for (Enrollment enrollment : enrollments) {
			if (enrollment.getActiveFlag()
					&& enrollment.getAttendanceSchoolProgramIdentifier()
							.equals(kidRecord.getAttendanceSchoolProgramIdentifier())
					&& enrollment.getAypSchoolIdentifier().equals(kidRecord.getAypSchoolIdentifier())) {
				newEnrollment = enrollment;
				kidRecord.getEnrollment().setId(enrollment.getId());				
				break;
			}
		}
		if(isKelpa){
			scoringAssignmentService.reAssignStudentsOnExitStudent(existedExrollment.getId(), new Date(), user);			
		}
		
		if(isKelpa || isKapHGSS){
			inactivateEnrollmentRoster(existedExrollment.getId(), user);
		}
		
		TestType ttype = testTypeIdMap.get(ettsa.getTestTypeId());
		
		// clear tests based on status
		studentsTestsService.removeTestSessionsOnClear(existedExrollment, ttype.getTestTypeCode(), ettsa.getSubjectAreaCode());
		
		//unenroll from predictive tests
		studentsTestsService.removeStudentPredictiveTests(existedExrollment.getId(), new Long(existedExrollment.getCurrentSchoolYear()), null);
		
		// Inactivate existing EnrollmentTestTypeSubjectArea
		inactivateEnrollmentTestType(existedExrollment, user, ettsa);
		
		List<edu.ku.cete.domain.enrollment.SubjectArea> subjectAreaLst = existedExrollment.getSubjectAreaList();
		int clearSubject=0; boolean isClearRequired = false;
		for(int i=0; i<subjectAreaLst.size(); i++){
			edu.ku.cete.domain.enrollment.SubjectArea sa = subjectAreaLst.get(i);
			if (sa.getId().longValue() == ettsa.getSubjectareaId().longValue()) {
				clearSubject = i;
				isClearRequired = true;
				break;
			}
		}
		if(isClearRequired){
			subjectAreaLst.remove(clearSubject);
		}
		
		processCleanupEnrollment(existedExrollment, contentAreaIdsMatchedWithKidsTestRecords, kidRecord);
		
		if (newEnrollment != null && isSubAreaRequired) {// enrollment found with current combinations of schools			
			if(StringUtils.equalsIgnoreCase(newEnrollment.getSourceType(), SourceTypeEnum.TASCWEBSERVICE.getCode()) 
					&& newEnrollment.getSchoolEntryDate() == null) {
				kidRecord.getEnrollment().setId(newEnrollment.getId());
				addOrUpdate(kidRecord.getEnrollment());
			}
			// Move TestRecord to new enrollment
			addOrUpdateEnrollmentTestType(newEnrollment, user, ettsa, null);
		} else if(newEnrollment == null){
			// no enrollment found, create enrollment and EnrollmentTestTypeSubjectArea
			newEnrollment = addOrUpdate(kidRecord.getEnrollment());
			if(kidRecord.getEnrollment().getId() == 0 && newEnrollment != null && newEnrollment.getId() != 0) {
				kidRecord.getEnrollment().setId(newEnrollment.getId());
			}
			if(isSubAreaRequired){
				addOrUpdateEnrollmentTestType(newEnrollment, user, ettsa, null);
			}
		}
	
	}

	/**
	 * Update audit data
	 * @param kidRecord
	 * @param reason
	 * @param templateId
	 */
	private void updateKidAuditData(KidRecord kidRecord, String reason, String templateId) {

		kidRecord.setEmailSent(false);
		kidRecord.setTriggerEmail(false);
		
		if (reason != null && !reason.isEmpty()) {
			if(kidRecord.getReasons() != null){
				kidRecord.setReasons((kidRecord.getReasons()).concat("\n" + reason));
			}else{
				kidRecord.setReasons(reason);
			}
			
		}

		if (templateId != null) {
			Long emailTemplateId = categoryDao.getCategoryId(templateId, "KIDS_EMAIL_TEMPLATES");
			if (emailTemplateId != null) {				
				kidRecord.setTriggerEmail(true);

				StringBuilder sb = null;
				
				if (kidRecord.getEmailTemplateIds() != null && !kidRecord.getEmailTemplateIds().isEmpty()) {
					sb = new StringBuilder(kidRecord.getEmailTemplateIds() + ",");
				} else {
					sb = new StringBuilder();
				}
				sb.append(emailTemplateId.toString());
				kidRecord.setEmailTemplateIds(sb.toString());
			}
		}
	}

	/**
	 * Undo exit
	 * @param user
	 * @param e
	 */
	private void undoExitRecords(User user, Enrollment e) {
		e.setExitWithdrawalDate(null);
		e.setExitWithdrawalType(0);
		e.setActiveFlag(true);
		e.setModifiedDate(new Date());
		e.setModifiedUser(user.getId());
		enrollmentDao.update(e);
		undoEnrollmentTestTypeSubjectAreaExits(e.getId());
		//DE15220 - no longer inactivate the enrollmentsrosters when exiting or reactivate the enrollment rosters when undoing the exit
		//undoEnrollmentRosterExits(e.getId(), e.getAttendanceSchoolId());
	}
	
	/**
	 * Undo exit on EnrollmentTestTypeSubjectArea
	 * @param enrollmentId
	 */
	private void undoEnrollmentTestTypeSubjectAreaExits(long enrollmentId){
		 List<EnrollmentTestTypeSubjectArea> subjectAreaLst = enrollmentTestTypeSubjectAreaDao.selectByEnrollmentId(enrollmentId);
		 for(EnrollmentTestTypeSubjectArea ettsa : subjectAreaLst){
			// ettsa.setActiveFlag(true);
			 ettsa.setExited(null);
			 enrollmentTestTypeSubjectAreaDao.updateByPrimaryKey(ettsa);
		 }
	}
	
	
	private void undoEnrollmentRosterExits(long enrollmentId, long attendanceSchoolId){

		ContentArea stateSubjectArea = rosterService.getContentArea("ELP", attendanceSchoolId);
 		if (stateSubjectArea == null) {
 			LOGGER.error("StateSubjectArea not found");
 			return;
 		}
 		
		EnrollmentsRosters er = enrollmentsRostersDao.getInactiveLatestEnrollmentsRosterByEnrollmentIdContentAreaId(enrollmentId, stateSubjectArea.getId());
		if(er !=null && er.getId()!= null && er.getId() != 0){
			er.setActiveFlag(true);
			er.setAuditColumnProperties();
			EnrollmentsRostersExample enrollmentsRostersExample = new EnrollmentsRostersExample();
			EnrollmentsRostersExample.Criteria enrollmentsRostersCriteria = enrollmentsRostersExample.createCriteria();
			enrollmentsRostersCriteria.andEnrollmentIdEqualTo(er.getEnrollmentId());
			enrollmentsRostersCriteria.andRosterIdEqualTo(er.getRosterId());
			enrollmentsRostersDao.updateByExample(er, enrollmentsRostersExample);
		}
	}
	/**
	 * Process Exit
	 * @param kidRecord
	 * @param user
	 * @param e
	 */
	public void processExitRecord(KidRecord kidRecord, User user, Enrollment e) {
		if ((e.getSourceType() != null && e.getSourceType().equals("TASC")) | e.getSchoolEntryDate() != null) {
			LOGGER.debug(kidRecord + "In processExitRecord, " +"kidRecord.getExitWithdrawalDate() : " + (kidRecord.getExitWithdrawalDate() !=null ? kidRecord.getExitWithdrawalDate().getTime() : " N/A"));

			if ((e.getSourceType() != null && e.getSourceType().equals("TASC")) || kidRecord.getExitWithdrawalDate().getTime() >= e.getSchoolEntryDate().getTime()) {

				StudentJson student = studentDao.getStudentjsonData(e.getStudentId());
				if (student != null) {
					student.setExitReason(kidRecord.getExitWithdrawalType());
					student.setExitDate(kidRecord.getExitWithdrawalDate());
					student.setExitedSchool(String.valueOf(e.getAttendanceSchoolId()));
					studentService.insertIntoDomainAuditHistory(e.getStudentId(), user.getId(),
							EventTypeEnum.EXIT.getCode(), SourceTypeEnum.TESTWEBSERVICE.getCode(), null,
							student.buildjsonString());
				}
				scoringAssignmentService.reAssignStudentsOnExitStudent(e.getId(), kidRecord.getExitWithdrawalDate(), user);
				/*
				 * Code change for DE17013
				 * The NULL AND NULL are for new enrollment details, which will be null in case of a Exit Scenario.
				 * Exit Scenario NULL's are handled in the roster change method.
				 */
				scoringAssignmentService.reassignStudentsOnRosterChangeKapSS(e.getId(),null,null);
				enrollmentDao.updateExitInfo(kidRecord.getExitWithdrawalDate(), Integer.valueOf(kidRecord.getExitWithdrawalType()), e.getId(), user.getId(), SourceTypeEnum.TESTWEBSERVICE.getCode());
				studentsTestsService.unEnrollStudentFromKidsProcess(e);
				updateEnrollmentTestTypeSubjectAreaOnExit(e.getId());
				//DE15220 - no longer inactivate the enrollmentsrosters when exiting or reactivate the enrollment rosters when undoing the exit
				//inactivateEnrollmentRoster(e.getId(), user);
			}else{
				//audit
				updateKidAuditData(kidRecord, kidRecord + ": Exit date is before school entry date of TEST record", null);
				kidRecord.setStatus(NOT_PROCESSED);
			}
		} else {
			//audit
			updateKidAuditData(kidRecord, kidRecord + ": No school entry date was provided on the enrollment record.  Cannot compare with EXIT date.", null);
			kidRecord.setStatus(NOT_PROCESSED);
		}
	}
	
	/**
	 * Process Exit on EnrollmentTestTypeSubjectArea
	 * @param enrollmentId
	 */
	 @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	 private void updateEnrollmentTestTypeSubjectAreaOnExit(long enrollmentId){
		 List<EnrollmentTestTypeSubjectArea> subjectAreaLst = enrollmentTestTypeSubjectAreaDao.selectByEnrollmentId(enrollmentId);
		 for(EnrollmentTestTypeSubjectArea ettsa : subjectAreaLst){
			 ettsa.setExited(true);
			 enrollmentTestTypeSubjectAreaDao.updateByPrimaryKey(ettsa);
		 }
	 }
	 
	 /**
	  * Populate student data from existing record, to avoid overwriting
	  * @param kidRecord
	  * @param studentFound
	  */
	 private void populateStudentToUpdate(KidRecord kidRecord, Student studentFound) {   
		 Student studentToUpdate = kidRecord.getStudent();
    	studentToUpdate.setId(studentFound.getId());
    	//Credentials should not change for the student.
    	studentToUpdate.setUsername(studentFound.getUsername());
    	studentToUpdate.setPassword(studentFound.getPassword());
    	studentToUpdate.setSaveStatus(studentFound.getSaveStatus());
    	studentToUpdate.setCreatedDate(studentFound.getCreatedDate());
    	studentToUpdate.setCreatedUser(studentFound.getCreatedUser());
    	studentToUpdate.setSynced(studentFound.getSynced());
	}
	 
	 /**
	  * 
	  * @param toSaveorUpdate
	  * @return Student
	  */
	 private Student updateStudent(Student toSaveorUpdate){
		 if (toSaveorUpdate!= null && (RecordSaveStatus.STUDENT_FOUND).equals(toSaveorUpdate.getSaveStatus())) {
	        	StudentJson studentJson = studentDao.getStudentjsonData(toSaveorUpdate.getId());
	            if(studentJson  != null){
	            	toSaveorUpdate.setBeforeJsonString(studentJson.buildjsonString());
	            }
	            toSaveorUpdate = studentService.update(toSaveorUpdate);
	            toSaveorUpdate.setSaveStatus(RecordSaveStatus.STUDENT_UPDATED);
	        }
		 return toSaveorUpdate;
	 }
	 
	 /**
	  * 
	  * @param kidRecord
	  * @param attendanceSchool
	  * @param contractingOrganizationTree
	  * @param user
	  * @return error message
	  */
	 private boolean processEducator(KidRecord kidRecord, Organization attendanceSchool, User user){
		String errorMessage = null; 
		User educator = kidRecord.getEducator();
		educator.setCurrentContextUserId(user.getId());
		educator.setSourceType(kidRecord.getRecordType());
		kidRecord.appendSchoolIdentifier(attendanceSchool.getId());
		//Educator Id All 9's
        if(kidRecord.getEducator().getUniqueCommonIdentifier() == null || kidRecord.getEducator().getUniqueCommonIdentifier().isEmpty() || "9999999999".equals(kidRecord.getEducator().getUniqueCommonIdentifier())){
        	errorMessage = kidRecord+": Invalid EducatorId - "+ kidRecord.getEducator().getUniqueCommonIdentifier();
        	updateKidAuditData(kidRecord, errorMessage , null);
        	kidRecord.setStatus(NOT_PROCESSED);
        	return false;
        }
      
        if(kidRecord.getEducator().getUniqueCommonIdentifier() != null && !kidRecord.getEducator().getUniqueCommonIdentifier().isEmpty()){
        	educator = userService.checkEducatorExistsForKELPA(educator, attendanceSchool);
        	kidRecord.setEducator(educator);
        	if((RecordSaveStatus.EDUCATOR_NOT_FOUND).equals(educator.getSaveStatus())){
        		errorMessage = "The educator ID submitted is not associated with an Educator Portal user. Please add the user and resubmit the TEST.";
        		updateKidAuditData(kidRecord,  errorMessage, "TEST_EducatorNotFound");
        		
        		buildDashboardErrorMessageList(kidRecord, null, attendanceSchool, "NOT_PROCESSED", MESSAGE_TYPE_ERROR, errorMessage, educator, "ELP");
        		kidRecord.setStatus(NOT_PROCESSED);
        		return false;
        	}else if(("Inactive").equalsIgnoreCase(educator.getStatusCode())){
        		errorMessage = "Please activate the new user in Educator Portal.";
        		updateKidAuditData(kidRecord,  errorMessage, "TEST_EducatorNotActivated");
        		buildDashboardErrorMessageList(kidRecord, null, attendanceSchool, "COMPLETED", MESSAGE_TYPE_NOTIFICATION, errorMessage, educator, "ELP");
        	}
        }
        return true;
	 }
	 
	     
	 /**
		 * Create new roster if not present and associcate student enrollment with roster for the subject at ATT school
		 * @param kidRecord
		 * @param attendanceSchool
		 * @param aypSchool
		 */
		private void createRosterAndEnrollmentRoster(KidRecord kidRecord, Organization attendanceSchool,
				Organization aypSchool, ContentArea stateSubjectArea, Long oldRosterId, User user) {
			EnrollmentsRosters enrollmentsRosters;
			Roster roster = new Roster();
			roster.setAttendanceSchoolId(attendanceSchool.getId());
			roster.setTeacherId(kidRecord.getEducator().getId());
			roster.setStateSubjectAreaId(stateSubjectArea.getId());
			roster.setRestrictionId(kidRecord.getRoster().getRestrictionId());
			roster.setCurrentSchoolYear(kidRecord.getCurrentSchoolYear());
			if(aypSchool != null && aypSchool.getId() != null) {
				roster.setAypSchoolId(aypSchool.getId());			
			} else {
				roster.setAypSchoolId(attendanceSchool.getId());			
			}
			
			//get existing roster if present
			List<Roster> existingRosters = rosterService.selectRosterByRosterAndAttendanceSchool(roster, attendanceSchool.getId());
			if(existingRosters != null && existingRosters.size() > 0){//roster exists	
				enrollmentsRosters = addEnrollmentToRoster(kidRecord, kidRecord.getCurrentContextUserId(), existingRosters);
				rosterService.addStudentToRosterEventToDomainAuditHistory(enrollmentsRosters, kidRecord.getRoster());				
				kidRecord.setRecordStatus(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS.toString());
				LOGGER.debug(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS + "for student: " +kidRecord.getStudent().getStateStudentIdentifier());
			}else{
				Roster newRoster = addRoster(kidRecord, kidRecord.getCurrentContextUserId(), aypSchool, attendanceSchool, stateSubjectArea, kidRecord.getEducator());
				kidRecord.setRoster(newRoster);
				
				if(newRoster.getId() != null){
					LOGGER.debug(RecordSaveStatus.ROSTER_ADDED + "for student: " +kidRecord.getStudent().getStateStudentIdentifier());
					existingRosters = new ArrayList<Roster>();
					existingRosters.add(newRoster);
					enrollmentsRosters = addEnrollmentToRoster(kidRecord, kidRecord.getCurrentContextUserId(), existingRosters);
					rosterService.addStudentToRosterEventToDomainAuditHistory(enrollmentsRosters, kidRecord.getRoster());				
					kidRecord.setRecordStatus(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS.toString());
					LOGGER.debug(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS + "for student: " +kidRecord.getStudent().getStateStudentIdentifier());
				}
			}	
			if(oldRosterId != null){
				scoringAssignmentService.reAssignStudentsOnChangeRoster(kidRecord.getEnrollment().getId(), oldRosterId, existingRosters.get(0).getId(),user);
			}
		}
		
	 private void processKELPAProctor(KidRecord kidRecord, Organization attendanceSchool, Organization aypSchool, User user){
		  LOGGER.debug(kidRecord+" : Processing roster for ELP");
			String errorMessage = null;
			ContentArea stateSubjectArea = rosterService.getContentArea("ELP", attendanceSchool.getId());
	 		if (stateSubjectArea == null) {
	 			updateKidAuditData(kidRecord, "StateSubjectArea not found", null);
	 			return;
	 		}

			//check for existing enrollmentrosters
			List<Roster> rosters = rosterService.getRostersByContentAreaAndEnrollment(stateSubjectArea.getId(), kidRecord.getEnrollment());
	        
			//roster exists for enrollment
	        if(rosters != null && rosters.size() > 0){
	        	if(rosters.get(0).getTeacherId().equals(kidRecord.getEducator().getId())){
	        		errorMessage = "Roster Existed with same educator - No change";
        			updateKidAuditData(kidRecord, errorMessage, null);
        			return;
        		}else{
        			//remove student-roster connection, add to new teacher
					inactivateEnrollmentRoster(kidRecord, kidRecord.getCurrentContextUserId(), rosters);
					//check for roster for subject associated with new teacher came in TEST
					createRosterAndEnrollmentRoster(kidRecord, attendanceSchool, aypSchool, stateSubjectArea, rosters.get(0).getId(), user);
        		}
	        }else{//Roster not found for subject and student, then add new roster
	        	createRosterAndEnrollmentRoster(kidRecord, attendanceSchool, aypSchool, stateSubjectArea, null, user);
	        }	
	        return;
	}
	 
	 /**
		 * Inactivate enrollmentroster record. ie..remove student teacher association
		 * @param kidRecord
		 * @param currentContextUserId
		 * @param rosters
		 */
		private void inactivateEnrollmentRoster(KidRecord kidRecord, Long currentContextUserId, List<Roster> rosters) {
			LOGGER.debug(kidRecord+" : Processing inactivateEnrollmentRoster for ELP");
			EnrollmentsRosters enrollmentsRosters = new EnrollmentsRosters(kidRecord.getEnrollment().getId(), rosters.get(0).getId());
			kidRecord.getRoster().setSourceType(kidRecord.getRecordType());
			enrollmentsRosters.setActiveFlag(false);			
			if (kidRecord.getEnrollmentStatus() != null) {
				enrollmentsRosters.setCourseEnrollmentStatusId(kidRecord.getEnrollmentStatus().getId());
			}
			enrollmentsRosters.setCurrentContextUserId(currentContextUserId);								
			enrollmentsRostersService.updateEnrollementRosterToInActive(enrollmentsRosters);
		}

		/**
		 * Create Roster for the subject at ATT school. This is being used by both KELPA2 and  HGSS
		 * @param kidRecord
		 * @param currentContextUserId
		 * @param aypSchool
		 * @param attendanceSchool
		 * @return
		 */
		private Roster addRoster(KidRecord kidRecord, Long currentContextUserId, Organization aypSchool,
				Organization attendanceSchool, ContentArea stateSubjectArea, User educator) {
			LOGGER.debug(kidRecord+" : Processing addRoster for subject: " + stateSubjectArea.getAbbreviatedName());
			Roster roster = new Roster();
			
			String courseSectionName = rosterService.getWebServiceCourseSectionName(educator.getFirstName(), 
					educator.getSurName(), stateSubjectArea.getAbbreviatedName(),
					kidRecord.getRecordType());
			
			roster.setCourseSectionName(courseSectionName);
			roster.setAttendanceSchoolId(attendanceSchool.getId());
			roster.setTeacherId(educator.getId());
			roster.setStateSubjectAreaId(stateSubjectArea.getId());
			roster.setStateCourseCode(kidRecord.getStateCourseCode());
			roster.setRestrictionId(kidRecord.getHistoryRoster().getRestrictionId());
			roster.setCurrentSchoolYear(kidRecord.getCurrentSchoolYear());
			roster.setSourceType(kidRecord.getRecordType());		
			roster.setStateSubjectCourseIdentifier(stateSubjectArea.getAbbreviatedName());
			if(kidRecord.getLocalCourseId() != null){
				roster.setLocalCourseId(kidRecord.getLocalCourseId().toString());
			}		
			roster.setEducatorschooldisplayidentifier((educator.getSchoolID() != null &&
					!educator.getSchoolID().isEmpty()) ? educator.getSchoolID() : kidRecord.getAttendanceSchoolProgramIdentifier());
			
			if(aypSchool != null && aypSchool.getId() != null) {
				roster.setAypSchoolId(aypSchool.getId());			
			} else {
				roster.setAypSchoolId(attendanceSchool.getId());			
			}
			if (roster.getRestrictionId() == null) {
				roster.setRestrictionId(kidRecord.getEnrollment()
						.getRestrictionId());
			}
			roster.setCurrentContextUserId(currentContextUserId);
			roster.setAuditColumnProperties();				
			
			Roster newRoster = new Roster();
			newRoster = rosterService.insert(roster);
			return newRoster;
		}

		/**
		 * Associate student with the teacher for the subject
		 * @param kidRecord
		 * @param currentContextUserId
		 * @param rosters
		 * @return
		 */
		private EnrollmentsRosters addEnrollmentToRoster(KidRecord kidRecord, Long currentContextUserId,
				List<Roster> rosters) {
			EnrollmentsRosters enrollmentsRosters;
			enrollmentsRosters = new EnrollmentsRosters(kidRecord.getEnrollment().getId(), rosters.get(0).getId());
			kidRecord.getRoster().setSourceType(kidRecord.getRecordType());
			enrollmentsRosters.setActiveFlag(true);
			
			if (kidRecord.getEnrollmentStatus() != null) {
				enrollmentsRosters.setCourseEnrollmentStatusId(kidRecord
					.getEnrollmentStatus().getId());
			}
			enrollmentsRosters.setCurrentContextUserId(currentContextUserId);
			enrollmentsRostersService.addEnrollmentToRoster(enrollmentsRosters);
			return enrollmentsRosters;
		}
		
		private void inactivateEnrollmentRoster(Long enrollmentId, User user){
			//First get all active enrollments and then inactivate - Tthis is because latest timestamp is requirred for exit reversal
			EnrollmentsRosters er = new EnrollmentsRosters();
			er.setEnrollmentId(enrollmentId);
			er.setActiveFlag(true);
			EnrollmentsRostersExample enrollmentsRostersExample = new EnrollmentsRostersExample();
			EnrollmentsRostersExample.Criteria enrollmentsRostersCriteria = enrollmentsRostersExample.createCriteria();
			enrollmentsRostersCriteria.andEnrollmentIdEqualTo(er.getEnrollmentId());
			List<EnrollmentsRosters> enrollRosterLst = enrollmentsRostersDao.selectByExample(enrollmentsRostersExample);
			if(enrollRosterLst != null && enrollRosterLst.size() > 0){
				for(EnrollmentsRosters er1 : enrollRosterLst){
					er1.setAuditColumnProperties();
					er1.setModifiedUser(user.getId());
					er1.setActiveFlag(false);
					enrollmentsRostersExample = new EnrollmentsRostersExample();
					enrollmentsRostersCriteria = enrollmentsRostersExample.createCriteria();
					enrollmentsRostersCriteria.andEnrollmentIdEqualTo(er1.getEnrollmentId());
					enrollmentsRostersCriteria.andRosterIdEqualTo(er1.getRosterId());
					enrollmentsRostersDao.updateByExampleSelective(er1,	enrollmentsRostersExample);
				}
			}
		}
	

	private boolean validForEducatorChange(Long studentid){
		int count = ccqScoreDao.getCountOfInprogressDomainsForStudent(new ArrayList<String>(Arrays.asList(HUMAN_SCORING_DOMAINS.split("\\s*,\\s*"))), studentid);
		if(count > 0)
			return false;
		else
			return true;
	}
	
	private boolean validateProctorInfo(KidRecord kidRecord, Organization attendanceSchool, User user){
		String errorMessage = null; 
		User educator = kidRecord.getHistoryProctor();
		educator.setCurrentContextUserId(user.getId());
		educator.setSourceType(kidRecord.getRecordType());
		//Educator Id All 9's
        if(kidRecord.getHistoryProctor().getUniqueCommonIdentifier() == null || kidRecord.getHistoryProctor().getUniqueCommonIdentifier().isEmpty() || "9999999999".equals(kidRecord.getHistoryProctor().getUniqueCommonIdentifier())){
        	errorMessage = "Invalid EducatorId - "+ kidRecord.getHistoryProctor().getUniqueCommonIdentifier();
        	updateKidAuditData(kidRecord, errorMessage , null);
        	kidRecord.setStatus(NOT_PROCESSED);
        	return false;
        }
      
        if(kidRecord.getHistoryProctor().getUniqueCommonIdentifier() != null && !kidRecord.getHistoryProctor().getUniqueCommonIdentifier().isEmpty()){
        	educator = userService.checkEducatorExistsForKELPA(educator, attendanceSchool);
        	kidRecord.setHistoryProctor(educator);
        	if((RecordSaveStatus.EDUCATOR_NOT_FOUND).equals(educator.getSaveStatus())){
        		errorMessage = "The educator ID submitted is not associated with an Educator Portal user. Please add the user and resubmit the TEST.";
        		updateKidAuditData(kidRecord,  errorMessage, "TEST_EducatorNotFound");
        		buildDashboardErrorMessageList(kidRecord, null, attendanceSchool, "NOT_PROCESSED", MESSAGE_TYPE_ERROR, errorMessage, educator, "SS");
        		kidRecord.setStatus(NOT_PROCESSED);
        		return false;
        	}else if(("Inactive").equalsIgnoreCase(educator.getStatusCode())){
        		errorMessage = "Please activate the new user in Educator Portal.";
        		updateKidAuditData(kidRecord, errorMessage, "TEST_EducatorNotActivated");
        		buildDashboardErrorMessageList(kidRecord, null, attendanceSchool, "COMPLETED", MESSAGE_TYPE_NOTIFICATION, errorMessage, educator, "SS");
        	}
        }
        return true;
	 }

	
	/**
	 * Check if the subject is HGSS
	 * @param ettsa
	 * @param subjectAreaMap
	 * @return boolean
	 */
	private boolean isKapHGSS(EnrollmentTestTypeSubjectArea ettsa,  Map<String, SubjectArea> subjectAreaMap){
		if(ettsa.getSubjectareaId().equals((subjectAreaMap.get("SHISGOVA")).getId())){
			return true;
		}
		return false;
	}
	
	/**
	 * Roster process for HGSS 
	 * @param kidRecord
	 * @param attendanceSchool
	 * @param aypSchool
	 * @param user
	 * @param existedDbEnroll 
	 */
	private void processHGSSProctor(KidRecord kidRecord, Organization attendanceSchool, Organization aypSchool, User user, Enrollment existedDbEnroll){
	  LOGGER.debug(kidRecord+" : Processing roster for History Government subject");
		String errorMessage = null;
		ContentArea stateSubjectArea = rosterService.getContentArea("SS", attendanceSchool.getId());
 		if (stateSubjectArea == null) {
 			updateKidAuditData(kidRecord, "StateSubjectArea not found", null);
 			return;
 		}

		//check for existing enrollmentrosters
		List<Roster> rosters = rosterService.getRostersByContentAreaAndEnrollment(stateSubjectArea.getId(), kidRecord.getEnrollment());
        
		//roster exists for enrollment
        if(rosters != null && rosters.size() > 0){
        	if(rosters.get(0).getTeacherId().equals(kidRecord.getHistoryProctor().getId())){
        		errorMessage = "Roster Exists with same proctor for HGSS - No changes were made";
	  			updateKidAuditData(kidRecord, errorMessage, null);
	  			return;
	  		}else{
	  			//remove student-roster connection, add to new teacher
				inactivateEnrollmentRoster(kidRecord, kidRecord.getCurrentContextUserId(), rosters);
				
				//check for roster for subject associated with new teacher came in TEST
				createHGSSRoster(kidRecord, attendanceSchool, aypSchool, stateSubjectArea, rosters.get(0).getId(), user,existedDbEnroll);
	  		}
        	
        }else{//Roster not found for subject and student, then add new roster
        	createHGSSRoster(kidRecord, attendanceSchool, aypSchool, stateSubjectArea, null, user,existedDbEnroll);
        }
        
        return;
	}
	
	/**
	 * Create Roster for HGSS 
	 * @param kidRecord
	 * @param attendanceSchool
	 * @param aypSchool
	 * @param stateSubjectArea
	 * @param oldRosterId
	 * @param user
	 * @param existedDbEnroll 
	 */
	private void createHGSSRoster(KidRecord kidRecord, Organization attendanceSchool,
			Organization aypSchool, ContentArea stateSubjectArea, Long oldRosterId, User user, Enrollment existedDbEnroll) {
		EnrollmentsRosters enrollmentsRosters;
		Roster roster = new Roster();
		roster.setAttendanceSchoolId(attendanceSchool.getId());
		roster.setTeacherId(kidRecord.getHistoryProctor().getId());
		roster.setStateSubjectAreaId(stateSubjectArea.getId());
		roster.setRestrictionId(kidRecord.getRoster().getRestrictionId());
		roster.setCurrentSchoolYear(kidRecord.getCurrentSchoolYear());
		roster.setSourceType(kidRecord.getRecordType());
		if(aypSchool != null && aypSchool.getId() != null) {
			roster.setAypSchoolId(aypSchool.getId());			
		} else {
			roster.setAypSchoolId(attendanceSchool.getId());			
		}
		
		//get existing roster if present
		List<Roster> existingRosters = rosterService.selectRosterByRosterAndAttendanceSchool(roster, attendanceSchool.getId());
		if(existingRosters != null && existingRosters.size() > 0){//roster exists	
			enrollmentsRosters = addEnrollmentToRoster(kidRecord, kidRecord.getCurrentContextUserId(), existingRosters);
			rosterService.addStudentToRosterEventToDomainAuditHistory(enrollmentsRosters, kidRecord.getRoster());				
			kidRecord.setRecordStatus(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS.toString());
			LOGGER.debug(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS + "for student: " +kidRecord.getStudent().getStateStudentIdentifier());
		}else{
			Roster newRoster = addRoster(kidRecord, kidRecord.getCurrentContextUserId(), aypSchool, attendanceSchool, stateSubjectArea, kidRecord.getHistoryProctor());
			kidRecord.setHistoryRoster(newRoster);
			
			if(newRoster.getId() != null){
				LOGGER.debug("HGSS "+ RecordSaveStatus.ROSTER_ADDED + "for student: " +kidRecord.getStudent().getStateStudentIdentifier());
				existingRosters = new ArrayList<Roster>();
				existingRosters.add(newRoster);
				enrollmentsRosters = addEnrollmentToRoster(kidRecord, kidRecord.getCurrentContextUserId(), existingRosters);
				rosterService.addStudentToRosterEventToDomainAuditHistory(enrollmentsRosters, kidRecord.getHistoryRoster());				
				kidRecord.setRecordStatus(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS.toString());
				LOGGER.debug(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS + "for student: " +kidRecord.getStudent().getStateStudentIdentifier());
			}
		}	
		if(existedDbEnroll!=null)
		{
			scoringAssignmentService.reassignStudentsOnRosterChangeKapSS(existedDbEnroll.getId(),kidRecord.getEnrollment().getId(),existingRosters.get(0).getId());
		}
		
	}
	
	private void buildDashboardErrorMessageList(KidRecord kidRecord, Organization aypSchool, Organization attendanceSchool, String status, String messageType, String errorMessage, User educator, String subjectArea){
		KidsDashboardRecord dashboardRecord = null;
		
		if("ALL_KAP_SUBJECTS".equalsIgnoreCase(subjectArea)){
			//Math
			if (kidRecord.getStateMathAssess() != null && StringUtils.isNotEmpty(kidRecord.getStateMathAssess())
					&& Arrays.asList(new String[] { "2", "C" }).indexOf(kidRecord.getStateMathAssess()) >= 0
					&& !kidRecord.getErrorSubjectCodes().contains("M")) {
				dashboardRecord = getKidsDashboardRecord(kidRecord, aypSchool, attendanceSchool, status, messageType, errorMessage, educator);
				dashboardRecord.setSubjectArea("M");
				kidRecord.getErrorSubjectCodes().add("M");
				kidRecord.getKidsDashboardRecords().add(dashboardRecord);
			}

			//ELA
			if (kidRecord.getStateELAAssessment() != null && StringUtils.isNotEmpty(kidRecord.getStateELAAssessment())
					&& Arrays.asList(new String[] { "2", "C" }).indexOf(kidRecord.getStateELAAssessment()) >= 0
					&& !kidRecord.getErrorSubjectCodes().contains("ELA")) {
				dashboardRecord = getKidsDashboardRecord(kidRecord, aypSchool, attendanceSchool, status, messageType, errorMessage, educator);
				dashboardRecord.setSubjectArea("ELA");
				kidRecord.getErrorSubjectCodes().add("ELA");
				kidRecord.getKidsDashboardRecords().add(dashboardRecord);
			}

			//Sci
			if (kidRecord.getStateSciAssessment() != null && StringUtils.isNotEmpty(kidRecord.getStateSciAssessment())
					&& Arrays.asList(new String[] { "2", "C" }).indexOf(kidRecord.getStateSciAssessment()) >= 0
					&& !kidRecord.getErrorSubjectCodes().contains("Sci")) {
				dashboardRecord = getKidsDashboardRecord(kidRecord, aypSchool, attendanceSchool, status, messageType, errorMessage, educator);
				dashboardRecord.setSubjectArea("Sci");
				kidRecord.getErrorSubjectCodes().add("Sci");
				kidRecord.getKidsDashboardRecords().add(dashboardRecord);
			}

			//HGSS
			if (kidRecord.getStateHistGovAssessment() != null
					&& StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment())
					&& Arrays.asList(new String[] { "2", "C" }).indexOf(kidRecord.getStateHistGovAssessment()) >= 0
					&& !kidRecord.getErrorSubjectCodes().contains("SS")) {
				dashboardRecord = getKidsDashboardRecord(kidRecord, aypSchool, attendanceSchool, status, messageType, errorMessage, educator);
				dashboardRecord.setSubjectArea("SS");
				kidRecord.getErrorSubjectCodes().add("SS");
				kidRecord.getKidsDashboardRecords().add(dashboardRecord);
			}			
			
		}else{
			if(!kidRecord.getErrorSubjectCodes().contains(subjectArea)){
				dashboardRecord = getKidsDashboardRecord(kidRecord, aypSchool, attendanceSchool, status, messageType, errorMessage, educator);
				dashboardRecord.setSubjectArea(subjectArea);
				kidRecord.getErrorSubjectCodes().add(subjectArea);
				kidRecord.getKidsDashboardRecords().add(dashboardRecord);
			}			
		}
	}
	
	private KidsDashboardRecord getKidsDashboardRecord(KidRecord kidRecord, Organization aypSchool, Organization attendanceSchool, String status, String messageType, String errorMessage, User educator){
		KidsDashboardRecord dashboardRecord = new KidsDashboardRecord();
		dashboardRecord.setRecordType(kidRecord.getRecordType());
		dashboardRecord.setSeqNo(kidRecord.getSeqNo());
		dashboardRecord.setStateStudentIdentifier(kidRecord.getStudent().getStateStudentIdentifier());
		dashboardRecord.setLegalFirstName(kidRecord.getStudent().getLegalFirstName());
		dashboardRecord.setLegalMiddleName(kidRecord.getStudent().getLegalMiddleName());
		dashboardRecord.setLegalLastName(kidRecord.getStudent().getLegalLastName());
		dashboardRecord.setDateOfBirth(kidRecord.getStudent().getDateOfBirth() != null ? kidRecord.getStudent().getDateOfBirth() : null);
		dashboardRecord.setAypSchoolIdentifier(aypSchool != null ? aypSchool.getDisplayIdentifier() : kidRecord.getAypSchoolIdentifier());
		dashboardRecord.setAttendanceSchoolIdentifier(attendanceSchool != null ? attendanceSchool.getDisplayIdentifier() : kidRecord.getAttendanceSchoolProgramIdentifier());
		dashboardRecord.setAypSchoolId(aypSchool != null ? aypSchool.getId() : kidRecord.getEnrollment().getAypSchoolId());
		dashboardRecord.setAttendanceSchoolId(attendanceSchool != null ? attendanceSchool.getId() : kidRecord.getEnrollment().getAttendanceSchoolId());
		dashboardRecord.setAypSchoolName(aypSchool != null ? aypSchool.getOrganizationName() : kidRecord.getAypSchoolName());
		dashboardRecord.setAttendanceSchoolName(attendanceSchool != null ? attendanceSchool.getOrganizationName() : kidRecord.getAttendanceSchoolName());
		dashboardRecord.setCurrentGradeLevel(kidRecord.getGradeCourseAbbreviatedName());
		dashboardRecord.setSchoolYear(new Long(kidRecord.getCurrentSchoolYear()));
		dashboardRecord.setStatus(status);
		dashboardRecord.setMessageType(messageType);
		dashboardRecord.setReasons(errorMessage);
		dashboardRecord.setRecordCommonId(Long.valueOf(kidRecord.getRecordCommonId()));
		dashboardRecord.setProcessedDate(new Date());
		
		if(educator != null){
			dashboardRecord.setEducatorIdentifier(educator.getUniqueCommonIdentifier());
			dashboardRecord.setEducatorFirstName(educator.getFirstName());
			dashboardRecord.setEducatorLastName(educator.getSurName());
		}
		
		return dashboardRecord;
		
	}
	
	
	private String getSubjectAbbreviatedNameMapping(String subjectCode){
		String subjAbbreviatedName = StringUtils.EMPTY;
		if("SELAA".equalsIgnoreCase(subjectCode)){
			subjAbbreviatedName = "ELA";
		}else if("D74".equalsIgnoreCase(subjectCode)){
			subjAbbreviatedName = "M";
		}else if("SSCIA".equalsIgnoreCase(subjectCode)){
			subjAbbreviatedName = "Sci";
		}else if("SHISGOVA".equalsIgnoreCase(subjectCode)){
			subjAbbreviatedName = "SS";
		}
		return subjAbbreviatedName;
	}
	
	
}
