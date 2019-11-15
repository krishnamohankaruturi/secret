/**
 * 
 */
package edu.ku.cete.service.impl.enrollment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.StudentAssessmentProgram;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.SubjectArea;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.ksde.kids.result.KidsDashboardRecord;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.model.StudentAssessmentProgramMapper;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.InterimGroupService;
import edu.ku.cete.service.InterimService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.EnrollmentsRostersService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.enrollment.TASCService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.AartParseException;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.SourceTypeEnum;

/**
 * @author ktaduru_sta
 *
 */
@Service
public class TASCServiceImpl implements TASCService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TASCServiceImpl.class);
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private AssessmentProgramDao assessmentProgramDao;
	 
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private EnrollmentsRostersService enrollmentsRostersService;	
	
	@Autowired
	private CategoryDao categoryDao;
	
	@Autowired
	private InterimGroupService interimGroupService;
	
	@Autowired
	private StudentProfileService studentProfileService;
	
	@Autowired
	private StudentAssessmentProgramMapper studentAssessmentProgramsDao;
	
	@Value("${kidRecord.notProcessedCode}")
	private String NOT_PROCESSED;
	
	@Autowired
    private StudentDao studentDao;
	
	@Autowired
	private InterimService interimService;
	
	@Autowired
	private StudentsTestsService studentsTestsService;

	@Autowired
	private AppConfigurationService appConfigurationService;
	
	@Value("${kids.general.errorMessage.templateCode}")
	private String GENERAL_TEMPLATE_CODE;
	
	@Value("${kids.errorMessage.type.notification}")
	private String MESSAGE_TYPE_NOTIFICATION;
	
	@Value("${kids.errorMessage.type.error}")
	private String MESSAGE_TYPE_ERROR;
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String cascadeAddOrUpdate(KidRecord kidRecord, User user, ContractingOrganizationTree contractingOrganizationTree) {
		LOGGER.debug("--> start TASC Implementation cascadeAddOrUpdate()");
 		boolean isKapStudent = false;
 		kidRecord.setCurrentContextUserId(user.getId());
		String errorMessage = null;
		Organization aypSchool = null;
		boolean createNewEnrollment = false;		
		
		if((kidRecord.getAypSchoolIdentifier() != null && !kidRecord.getAypSchoolIdentifier().isEmpty()) && (kidRecord.getAttendanceSchoolProgramIdentifier() == null
				|| kidRecord.getAttendanceSchoolProgramIdentifier().isEmpty())){
			kidRecord.setAttendanceSchoolProgramIdentifier(kidRecord.getAypSchoolIdentifier());
		}else if((kidRecord.getAypSchoolIdentifier() == null || kidRecord.getAypSchoolIdentifier().isEmpty()) && (kidRecord.getAttendanceSchoolProgramIdentifier() != null
				&& !kidRecord.getAttendanceSchoolProgramIdentifier().isEmpty())){
			try {
				kidRecord.setAypSchoolIdentifier(kidRecord.getAttendanceSchoolProgramIdentifier());
			} catch (AartParseException e) {
				LOGGER.debug(e.getMessage());
			}
		}
		
		if(!kidRecord.getStateStudentIdentifier().trim().isEmpty() ){
        	String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
        	int allowedLength = Integer.parseInt(stateStudentIdentifierLength);
        		if(kidRecord.getStateStudentIdentifier().trim().length()>allowedLength){
        			String stateStudentIdentifierLengthError = appConfigurationService.getByAttributeCode(CommonConstants.STATE_STUDENT_IDENTIFIER_LENGTH_ERROR);
        			errorMessage = stateStudentIdentifierLengthError.concat(Integer.toString(allowedLength)).concat(".");			
    				updateKidAuditData(kidRecord, errorMessage, null, NOT_PROCESSED);
    				LOGGER.debug(errorMessage);
    				return errorMessage;
        		}
        }
		
		//Check for Accountability school existence
		if(kidRecord.getAypSchoolIdentifier() != null){			
			aypSchool = contractingOrganizationTree.getUserOrganizationTree().getOrganization(kidRecord.getAypSchoolIdentifier());		
			if(aypSchool == null) {
				kidRecord.setRecordStatus(RecordSaveStatus.AYP_SCHOOL_NOT_FOUND.toString());
				errorMessage = "Error for student:["+kidRecord.getStudent().getStateStudentIdentifier()+"]. Feild:" + FieldName.AYP_SCHOOL_IDENTIFIER + " is not found in the system. TASC record will not be processed.";				
				updateKidAuditData(kidRecord, errorMessage, null, NOT_PROCESSED);
				LOGGER.debug(errorMessage);
				return errorMessage;
			}			
			
			aypSchool.setCurrentContextUserId(user.getId());
		}		
		
		//Check for Attendance school existence
		Organization attendanceSchool = contractingOrganizationTree.getUserOrganizationTree().getOrganization(kidRecord.getAttendanceSchoolProgramIdentifier());		
		if(attendanceSchool == null) {
			kidRecord.setRecordStatus(RecordSaveStatus.ATTENDANCE_SCHOOL_NOT_FOUND.toString());
			errorMessage = "Error for student:["+kidRecord.getStudent().getStateStudentIdentifier()+"]. Feild:" + FieldName.ATTENDANCE_SCHOOL + " is not found in the system. TASC record will not be proessed.";
			updateKidAuditData(kidRecord, errorMessage, null, NOT_PROCESSED);
			LOGGER.debug(errorMessage);
			return errorMessage;
		}			
		
		kidRecord.setAttendanceSchool(attendanceSchool);
		
		// school year validation
		long contractingOrgSchoolYear = user.getContractingOrganization().getCurrentSchoolYear();		
		if (contractingOrgSchoolYear != kidRecord.getCurrentSchoolYear()) {
			LOGGER.debug("The record: " + kidRecord.getRecordCommonId() + " is rejected because the contracting organization school year does not match.");
			errorMessage = "Error for student:["+kidRecord.getStudent().getStateStudentIdentifier()+"]. Field:" + FieldName.CURRENT_SCHOOL_YEAR + "(" + kidRecord.getCurrentSchoolYear() +")"
							+ " does not match with the contracting organization school year."+contractingOrgSchoolYear;			
			updateKidAuditData(kidRecord, errorMessage, null, NOT_PROCESSED);
			LOGGER.debug(errorMessage);
			return errorMessage;
		}
				
		//determine assessment program based on statesubjectarea
		isKapStudent = isKAPKid(kidRecord);	
		
		//Gradecourse
		GradeCourse gradeCourse = null;
		GradeCourse grade = null;
		if (kidRecord.getCurrentGradeLevel() != null) {
			gradeCourse = new GradeCourse();
			String strGradeLevel = "0" + kidRecord.getCurrentGradeLevel();
			// add a zero to the beginning and then if there are more than 2
			// digits remove the first digit
			strGradeLevel = strGradeLevel.length() > 2 ? strGradeLevel.substring(1) : strGradeLevel;
			// store in abbr name to send to query
			gradeCourse.setAbbreviatedName(strGradeLevel);
			gradeCourse.setCurrentContextUserId(user.getId());
			grade = gradeCourseService.getWebServiceGradeCourseByCode(gradeCourse);
		}
		
		
		if (grade != null) {
			kidRecord.getEnrollment().setCurrentGradeLevel(grade.getId());
			kidRecord.setGradeCourseAbbreviatedName(grade.getAbbreviatedName());
		} else {
			kidRecord.getEnrollment().setCurrentGradeLevel(null);
			kidRecord.setDoReject(true);
			errorMessage = "Error for student:["+kidRecord.getStudent().getStateStudentIdentifier()+"]. Feild:Current_Grade_Level is not found in the system. ";
			updateKidAuditData(kidRecord, errorMessage, null, NOT_PROCESSED);
			LOGGER.debug(errorMessage);
			return errorMessage;
		}
		
		//check for Invalid Subject code, moved this from processor validation, since we need to send an email to DTC/BTC and show it on Dashboard page 
		if(kidRecord.getInvalidSubjectAreaCode() != null && kidRecord.getInvalidSubjectAreaCode()){
			errorMessage = "Invalid state subject area code received on TASC. Allowable values are only:" + kidRecord.getAllowableSubjectCodes();
			updateKidAuditData(kidRecord, errorMessage, GENERAL_TEMPLATE_CODE, NOT_PROCESSED);
			buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, NOT_PROCESSED, MESSAGE_TYPE_NOTIFICATION, errorMessage, null, null);
			LOGGER.debug(errorMessage);
			return errorMessage;
		}
		
		//subject area
		ContentArea stateSubjectArea = rosterService.getContentArea(kidRecord.getStateSubjectAreaCode(), attendanceSchool.getId());
		if (stateSubjectArea == null) {
			// could not find the subject area	
			errorMessage = "Error for student:["+kidRecord.getStudent().getStateStudentIdentifier()+"]. Feild:" + FieldName.SUBJECT + " must be valid. Subject Code in xml : " + kidRecord.getStateSubjectAreaCode();
			updateKidAuditData(kidRecord, errorMessage, null, NOT_PROCESSED);
			LOGGER.debug(errorMessage);
			return errorMessage;
		}
		kidRecord.setStateSubjectArea(stateSubjectArea);
		kidRecord.getStateCourse().setCurrentContextUserId(user.getId());
		if (kidRecord.getStateSubjectArea() != null) {
			kidRecord.getReceivedSubjectCodes().add(kidRecord.getStateSubjectArea().getAbbreviatedName());
		}
				
		// set contracting organization on student
        Organization studentState = organizationService.getContractingOrganization(attendanceSchool.getId());
        kidRecord.getStudent().setStateId(studentState.getId());
        
        //set the currentContextUserId to be used in transaction
        kidRecord.getStudent().setCurrentContextUserId(user.getId());	
        kidRecord.getStudent().setSourceType(kidRecord.getRecordType());
		Student student = kidRecord.getStudent();		
		
		student = studentService.getByStateStudentIdentifier(student, attendanceSchool.getId());
		
		if(student.getSaveStatus() != null && (student.getSaveStatus().equals(RecordSaveStatus.STUDENT_FOUND))){
			kidRecord.setStudent(student);
			
			//check for student association with assessment program			
			AssessmentProgram ap = assessmentProgramDao.findByStudentId(student.getId(), kidRecord.getCurrentSchoolYear());
			
			boolean removePnp = false;
			//If DLM student then do not process
			if(ap != null && "DLM".equals(ap.getAbbreviatedname())){				
				List<Enrollment> enrollments = null;
				enrollments = enrollmentService.getEnrollmentBySSIDAndStateId(kidRecord.getStudent().getStateStudentIdentifier(), studentState.getId(), kidRecord.getCurrentSchoolYear());
				
				if(enrollments != null && enrollments.size() > 0){
					errorMessage = "The student is enrolled in DLM for the current school year, so no changes were made";
					updateKidAuditData(kidRecord, errorMessage, GENERAL_TEMPLATE_CODE, NOT_PROCESSED);
					buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, NOT_PROCESSED, MESSAGE_TYPE_NOTIFICATION, errorMessage, null, null);
					LOGGER.debug(errorMessage);
					return errorMessage;
					
				}else{
					removePnp = true;
					//Inactivate DLM assessment  program and remove PNP attributes and claim for KAP					
					studentAssessmentProgramsDao.deactivateByStudentIdAndAssessmentProgramId(student.getId(), ap.getId());
					
				}
				
			}
			//Not a DLM student, then look for active enrollments				
			//Check if Student exists, if not create student record
			
			//assign assessment program id
	        if(isKapStudent) {
	        	AssessmentProgram asmpgrm = assessmentProgramDao.findByAbbreviatedName("KAP");
	        	kidRecord.getStudent().setAssessmentProgramId(asmpgrm.getId());
	        	kidRecord.getEducator().setAssessmentProgramId(asmpgrm.getId());
	        }
	        
			List<Enrollment> enrollments = new ArrayList<Enrollment>();
			enrollments = enrollmentService.getEnrollmentsByStudentIdForTasc(student.getId(), new Long(kidRecord.getCurrentSchoolYear()));								
			Long currentGradeLevel = kidRecord.getEnrollment().getCurrentGradeLevel();
			boolean rostersCleanUpRequired = false;
			boolean enrollmentFound = false;
			if(enrollments == null || enrollments.size() == 0){
				//no active enrollment found, create new enrollment				
				//Add Enrollment only if course status is not 99 
				if(!"99".equalsIgnoreCase(kidRecord.getCourseStatus().toString())){
					createNewEnrollment = true;						
					
				}else{
					errorMessage = "Error for student:["+kidRecord.getStudent().getStateStudentIdentifier()+"]. "
							+ "Enrollment not found and can not be created when course status is 99";
					updateKidAuditData(kidRecord, errorMessage, null, NOT_PROCESSED);
					LOGGER.debug(errorMessage);
					return errorMessage;
				}
			}else{
				rostersCleanUpRequired = true;
				boolean testRecordFoundForSubject = false;				
				List<SubjectArea> subjectAreaList = null;
				for(Enrollment enrollment : enrollments){
					if((SourceTypeEnum.TESTWEBSERVICE.getCode()).equalsIgnoreCase(enrollment.getSourceType())){
						subjectAreaList = enrollment.getSubjectAreaList();
						if(CollectionUtils.isNotEmpty(subjectAreaList)){
							for(SubjectArea sa : subjectAreaList){
								if(sa.getSubjectAreaCode().equalsIgnoreCase(stateSubjectArea.getAbbreviatedName())){
									testRecordFoundForSubject = true;
									break;
								}
							}
						}
						if(testRecordFoundForSubject){
							if(kidRecord.getAypSchoolIdentifier().equalsIgnoreCase(enrollment.getAypSchoolIdentifier())
											&& kidRecord.getAttendanceSchoolProgramIdentifier().equalsIgnoreCase(enrollment.getAttendanceSchoolProgramIdentifier())){
								enrollmentFound = true;							
								kidRecord.setEnrollment(enrollment);
								break;
								
							}else{//schools does not match, reject TASC record
								//enrollment found but ATT does not match with ATT on TASC, ignore and add to audit trail
								errorMessage = "Student is currently enrolled in a different attendance school";
								updateKidAuditData(kidRecord, errorMessage, GENERAL_TEMPLATE_CODE, NOT_PROCESSED);
								buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, NOT_PROCESSED, MESSAGE_TYPE_NOTIFICATION, errorMessage, null, null);
								LOGGER.debug(errorMessage);
								return errorMessage;
							}
						}else{
							if(kidRecord.getAypSchoolIdentifier().equalsIgnoreCase(enrollment.getAypSchoolIdentifier())
											&& kidRecord.getAttendanceSchoolProgramIdentifier().equalsIgnoreCase(enrollment.getAttendanceSchoolProgramIdentifier())){
								enrollmentFound = true;							
								kidRecord.setEnrollment(enrollment);								
							}
						}
						
						
					}else{
						if(((kidRecord.getAypSchoolIdentifier() != null && 
								enrollment.getAypSchoolIdentifier().equalsIgnoreCase(kidRecord.getAypSchoolIdentifier()))
								|| (kidRecord.getAypSchoolIdentifier() == null && 
								enrollment.getAypSchoolIdentifier().equalsIgnoreCase(kidRecord.getAttendanceSchoolProgramIdentifier())))
							&& enrollment.getAttendanceSchoolProgramIdentifier().equalsIgnoreCase(kidRecord.getAttendanceSchoolProgramIdentifier())){
							enrollmentFound = true;
							rostersCleanUpRequired = true;
							kidRecord.setEnrollment(enrollment);
							
						}
					}				
					
				}
			
				if(!enrollmentFound){
					rostersCleanUpRequired = true;
					if(!"99".equalsIgnoreCase(kidRecord.getCourseStatus().toString())){
						createNewEnrollment = true;						
						
					}else{
						errorMessage = "Student is currently enrolled at a different school. Course status of 99 cannot be processed";
						updateKidAuditData(kidRecord, errorMessage, GENERAL_TEMPLATE_CODE, NOT_PROCESSED);
						buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, NOT_PROCESSED, MESSAGE_TYPE_NOTIFICATION, errorMessage, null, null);
						LOGGER.debug(errorMessage);
						return errorMessage;
					}
				}
				
			}
	        
			//Educator
			 errorMessage = processEducator(kidRecord, attendanceSchool, contractingOrganizationTree, user);
			 if(errorMessage != null){
				 return errorMessage;
			 }
	        
			//add/update student info
			if(!"99".equalsIgnoreCase(kidRecord.getCourseStatus().toString())){
				student = studentService.addOrUpdateSelective(student, attendanceSchool.getId());
		        kidRecord.setStudent(student);
			}else if("99".equalsIgnoreCase(kidRecord.getCourseStatus().toString())){
				List<Student> existingStudent = studentDao.getBySsidAndState(kidRecord.getStateStudentIdentifier(), studentState.getId());
				if(!compareStudentFields(kidRecord, existingStudent.get(0))){
					errorMessage = "Student demographic data does not match data previously received for the current school year.";
     				updateKidAuditData(kidRecord, errorMessage, GENERAL_TEMPLATE_CODE, NOT_PROCESSED);
     				buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, NOT_PROCESSED, MESSAGE_TYPE_NOTIFICATION, errorMessage, null, null);
     				return errorMessage;
				}
			}		        		        
	        
			if(removePnp){
				List<StudentAssessmentProgram> studentAssessmentPrograms = studentAssessmentProgramsDao.getByStudentId(student.getId());
				List<Long> assessmentProgramIds = new ArrayList<Long>(studentAssessmentPrograms.size());
				for (StudentAssessmentProgram sap : studentAssessmentPrograms) {
					if (Boolean.TRUE.equals(sap.getActiveFlag())) {
						assessmentProgramIds.add(sap.getAssessmentProgramId());
					}
				}
				try {
					studentProfileService.removeNonAssociatedPNPSettings(student.getId(), assessmentProgramIds, user.getId(), user.getContractingOrganization().getId());
					removePnp = false;
				} catch (JsonProcessingException e) {
					errorMessage = "Removing PNP changes for DLM assessment failed ";
					updateKidAuditData(kidRecord, errorMessage, null, null);
			     	LOGGER.error(e.getMessage());
				}
			}
			
			if(rostersCleanUpRequired){
				for(Enrollment enrollment : enrollments){
					//if(SourceTypeEnum.TASCWEBSERVICE.getCode().equals(enrollment.getSourceType()) && CollectionUtils.isEmpty(enrollment.getSubjectAreaList())){
						if(!enrollmentFound || (enrollmentFound && enrollment.getId() != kidRecord.getEnrollment().getId())){
							processCleanupTASCRosters(enrollment, kidRecord, stateSubjectArea.getId());
						}						
					//}					
				}
				
			}
	        //create enrollment if not found
	        if(createNewEnrollment){
	        	addEnrollment(kidRecord, aypSchool, attendanceSchool, student, enrollments);
	        }
	        
	        //update if there is a change in grade
	       
        	for(Enrollment enrollment : enrollments){		        	
				Long previousGrade = enrollment.getCurrentGradeLevel();						
				if(previousGrade != null && currentGradeLevel != null) {
                	if(!previousGrade.equals(currentGradeLevel)) {
                		 if(!"99".equalsIgnoreCase(kidRecord.getCourseStatus().toString())){
                			 if((SourceTypeEnum.TESTWEBSERVICE.getCode()).equalsIgnoreCase(enrollment.getSourceType())){
                				 if(((kidRecord.getAypSchoolIdentifier() != null && 
         								enrollment.getAypSchoolIdentifier().equalsIgnoreCase(kidRecord.getAypSchoolIdentifier()))
         								|| (kidRecord.getAypSchoolIdentifier() == null && 
         								enrollment.getAypSchoolIdentifier().equalsIgnoreCase(kidRecord.getAttendanceSchoolProgramIdentifier())))
         							&& enrollment.getAttendanceSchoolProgramIdentifier().equalsIgnoreCase(kidRecord.getAttendanceSchoolProgramIdentifier())){
                					errorMessage = "TASC record does not match any existing records, so no grade changes were made";
                      				updateKidAuditData(kidRecord, errorMessage, GENERAL_TEMPLATE_CODE, NOT_PROCESSED);
                      				buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, NOT_PROCESSED, MESSAGE_TYPE_NOTIFICATION, errorMessage, kidRecord.getEducator(), null);
                      				return errorMessage;
                				 }
                			 }
                			 enrollment.setCurrentGradeLevel(currentGradeLevel);
                			 enrollment.setRestrictionId(kidRecord.getRoster().getRestrictionId());
 	                		 enrollment = enrollmentService.update(enrollment);
 	                		 Roster roster = null;
 	                		 interimService.exitStudentRosterKIDSInterim(enrollment, roster);
 	                		 interimService.autoAssignKIDSInterim(enrollment, roster); 	                		
 	                		 enrollmentService.addGradeChangeEventDomainAuditHistory(enrollment, previousGrade); 
                		 }else{
                			errorMessage = "TASC record does not match any existing records, so no grade changes were made";
             				updateKidAuditData(kidRecord, errorMessage, GENERAL_TEMPLATE_CODE, NOT_PROCESSED);
             				buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, NOT_PROCESSED, MESSAGE_TYPE_NOTIFICATION, errorMessage, kidRecord.getEducator(), null);
             				return errorMessage;
                		 }
                		
                	}
                }
	        }
	                
	        
	        //roster process
	        errorMessage = processRoster(kidRecord, attendanceSchool, aypSchool);			
			
			
		}else{
			if(!"99".equalsIgnoreCase(kidRecord.getCourseStatus().toString())){
				if(isKapStudent) {
		        	AssessmentProgram asmpgrm = assessmentProgramDao.findByAbbreviatedName("KAP");
		        	kidRecord.getStudent().setAssessmentProgramId(asmpgrm.getId());
		        	kidRecord.getEducator().setAssessmentProgramId(asmpgrm.getId());
		        }
				
				//Educator
				errorMessage = processEducator(kidRecord, attendanceSchool, contractingOrganizationTree, user);
				if(errorMessage != null){
					return errorMessage;
				}
				
				//update student info
				student = studentService.addOrUpdateSelective(student, attendanceSchool.getId());
			    kidRecord.setStudent(student);
			    List<Enrollment> enrollments = new ArrayList<Enrollment>();
			    
			    //create enrollment
				addEnrollment(kidRecord, aypSchool, attendanceSchool, kidRecord.getStudent(), enrollments);
				
				//roster process
				errorMessage = processRoster(kidRecord, attendanceSchool, aypSchool);
			}else{
				errorMessage = "TASC record does not match any existing records";
				updateKidAuditData(kidRecord, errorMessage, GENERAL_TEMPLATE_CODE, NOT_PROCESSED);
				buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, NOT_PROCESSED, MESSAGE_TYPE_NOTIFICATION, errorMessage, kidRecord.getEducator(), null);
			}
			
		}
		LOGGER.debug("<-- End TASC Implementation cascadeAddOrUpdate()");
		return errorMessage;
		
	}


	/**
	 * Create educator at ATT school if not present or use existing educator or do not process TASC 
	 * and send an email to DTC/BTC if any validation fails
	 * @param kidRecord
	 * @param attendanceSchool
	 * @param contractingOrganizationTree
	 * @param user
	 * @return
	 */
	private String processEducator(KidRecord kidRecord, Organization attendanceSchool, ContractingOrganizationTree contractingOrganizationTree, User user){
		String errorMessage = null; 
		User dbEducator = null;
        kidRecord.getEducator().setCurrentContextUserId(user.getId());
        kidRecord.getEducator().setSourceType(kidRecord.getRecordType());
        String userDisplayName = kidRecord.getTeacherFirstName() + " " + kidRecord.getTeacherLastName();
        kidRecord.getEducator().setDisplayName(userDisplayName);
        User educator = kidRecord.getEducator();
        kidRecord.appendSchoolIdentifier(attendanceSchool.getId());
        Long emailTemplateId = null;
        
		//Educator Id All 9's
        if(kidRecord.getEducator().getUniqueCommonIdentifier() != null && !kidRecord.getEducator().getUniqueCommonIdentifier().isEmpty() &&
        		"9999999999".equals(kidRecord.getEducator().getUniqueCommonIdentifier())){
        	
        	 if(kidRecord.getEducatorEmailId() != null && !kidRecord.getEducatorEmailId().isEmpty()){
        		 
        		 dbEducator = userService.getByEmail(kidRecord.getEducatorEmailId());
        		 //New to EP OR exists with matching Id 9's 
            	 if(dbEducator == null || (dbEducator!= null && "9999999999".equals(dbEducator.getUniqueCommonIdentifier()))){
            		 if (attendanceSchool != null) {
            			 	educator.setUserName(kidRecord.getEducatorEmailId());
            			 	
            				educator = userService.addIfNotPresentForTASC(educator,
            						contractingOrganizationTree.getUserOrganizationTree()
            								.getUserOrganizationTree(), attendanceSchool, kidRecord.getCourseStatus().toString());	
            				
            				if (educator.getSaveStatus().equals(RecordSaveStatus.EDUCATOR_ADDED)) {
            					emailTemplateId = categoryDao.getCategoryId("TASC_EducatorNotActivated", "KIDS_EMAIL_TEMPLATES");
            					errorMessage = "Please activate the new user in Educator Portal.";
            					updateKidAuditData(kidRecord, errorMessage, emailTemplateId.toString(), null);                        		
            					
            					buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, "COMPLETED", MESSAGE_TYPE_NOTIFICATION, errorMessage, educator, null);
            					//this error message should stop from processing record further, so set error message to null
            					errorMessage = null;
            					kidRecord.setCreated(true);
            				}else if(educator.getSaveStatus().equals(RecordSaveStatus.INVALID_COURSE_STATUS)){
            					errorMessage = "Error for student:["+kidRecord.getStudent().getStateStudentIdentifier()+"]. Educator can not be created as course status is 99.";
                        		updateKidAuditData(kidRecord, errorMessage, null, NOT_PROCESSED);
                        		LOGGER.debug(errorMessage);
            				}
            				
            				kidRecord.setEducator(educator);
            			}
            	 }else if(dbEducator!= null && !"9999999999".equals(dbEducator.getUniqueCommonIdentifier())){
            		 //Rcvd email EXISTS in EP but with ID not all 9's or empty.
            		 
            		educator.setSaveStatus(RecordSaveStatus.EDUCATOR_INVALID_MULTIPLE_ID);
            		emailTemplateId = categoryDao.getCategoryId("TASC_MultipleEducatorIds", "KIDS_EMAIL_TEMPLATES");			            		
            		
            		errorMessage = "The educator ID submitted does not match the one on file in Educator Portal. Please make corrections and resubmit.";
            		updateKidAuditData(kidRecord, errorMessage, emailTemplateId.toString(), NOT_PROCESSED);
            		
            		buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, NOT_PROCESSED, MESSAGE_TYPE_ERROR, errorMessage, educator, null);
            		
            		LOGGER.debug(errorMessage);
            		 
            	 }
        	 } 
        	 
        //Educator Id is not 9's	 
        }else if(kidRecord.getEducator().getUniqueCommonIdentifier() != null && !kidRecord.getEducator().getUniqueCommonIdentifier().isEmpty() &&
        		!"9999999999".equals(kidRecord.getEducator().getUniqueCommonIdentifier())){
        	
        	educator = userService.addEducatorIfNotPresentForTASCByEmailUserId(educator,
						contractingOrganizationTree.getUserOrganizationTree()
								.getUserOrganizationTree(), attendanceSchool, kidRecord.getCourseStatus().toString());
        	
        	if(educator.getSaveStatus() != null){
        		if(educator.getSaveStatus().equals(RecordSaveStatus.EDUCATOR_INVALID_MULTIPLE_ID)){
        			emailTemplateId = categoryDao.getCategoryId("TASC_MultipleEducatorIds", "KIDS_EMAIL_TEMPLATES");		            		
        			errorMessage = "The educator ID submitted does not match the one on file in Educator Portal. Please make corrections and resubmit.";
        			updateKidAuditData(kidRecord, errorMessage, emailTemplateId.toString(), NOT_PROCESSED);
        			
        			buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, NOT_PROCESSED, MESSAGE_TYPE_ERROR, errorMessage, educator, null);
        			
        			LOGGER.debug(errorMessage);
        		}else if(educator.getSaveStatus().equals(RecordSaveStatus.EDUCATOR_INVALID_MULTIPLE_EMAIL_ADDRESS)){
        			emailTemplateId = categoryDao.getCategoryId("TASC_MultipleEmailIds", "KIDS_EMAIL_TEMPLATES");		            		
            			            		
        			errorMessage = "The email address submitted does not match the one on file in Educator Portal. Please make corrections and resubmit.";
        			updateKidAuditData(kidRecord, errorMessage, emailTemplateId.toString(), NOT_PROCESSED);
        			
        			buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, NOT_PROCESSED, MESSAGE_TYPE_ERROR, errorMessage, educator, null);
        			
        			LOGGER.debug(errorMessage);
        		}else if(educator.getSaveStatus().equals(RecordSaveStatus.INVALID_COURSE_STATUS)){
					errorMessage = "Error for student:["+kidRecord.getStudent().getStateStudentIdentifier()+"]. Educator can not be created as course status is 99.";
            		updateKidAuditData(kidRecord, errorMessage, null, NOT_PROCESSED);
            		LOGGER.debug(errorMessage);
				}else if (educator.getSaveStatus().equals(RecordSaveStatus.EDUCATOR_ADDED)) {
					emailTemplateId = categoryDao.getCategoryId("TASC_EducatorNotActivated", "KIDS_EMAIL_TEMPLATES");
					errorMessage = "Please activate the new user in Educator Portal.";
					updateKidAuditData(kidRecord, errorMessage, emailTemplateId.toString(), null); 
					
					buildDashboardErrorMessageList(kidRecord, attendanceSchool, attendanceSchool, "COMPLETED", MESSAGE_TYPE_NOTIFICATION, errorMessage, educator, null);
					//this error message should stop from processing record further, so set error message to null
					errorMessage = null;
					kidRecord.setCreated(true);
				}
        	}
        	
        	kidRecord.setEducator(educator);
        }
        
        return errorMessage;
	}

	
	/**
	 * Roster process for student
	 * @param kidRecord
	 * @param attendanceSchool
	 * @param aypSchool
	 * @return
	 */
	private String processRoster(KidRecord kidRecord, Organization attendanceSchool, Organization aypSchool){
		String errorMessage = null;
		
		//check for existing enrollmentrosters
		List<Roster> rosters = rosterService.getRostersByContentAreaAndEnrollment(kidRecord.getStateSubjectAreaId(), kidRecord.getEnrollment());
        
		//roster exists for enrollment
        if(rosters != null && rosters.size() > 0){
        	if(kidRecord.getCourseStatus() != null && (99 == kidRecord.getCourseStatus())){		        		
				inactivateEnrollmentRoster(kidRecord, kidRecord.getCurrentContextUserId(), rosters);
				User teacher = new User();
				teacher.setId(rosters.get(0).getTeacherId());
				teacher.setCurrentContextUserId(rosters.get(0).getTeacherId());
				interimGroupService.deleteOnRosterChange(teacher, kidRecord.getEnrollment().getStudentId());
				
				interimService.exitStudentRosterKIDSInterim(kidRecord.getEnrollment(), rosters.get(0));
				
				LOGGER.debug("CourseStatus is: "+kidRecord.getCourseStatus()+" hence, removed student roster association for Student: "+kidRecord.getStudent().getStateStudentIdentifier());
				
        	}else{
        		if(rosters.get(0).getTeacherId().equals(kidRecord.getEducator().getId())){
        			errorMessage = "Error for student:["+kidRecord.getStudent().getStateStudentIdentifier()+"]. Roster exists for the subject area and student, SAME teacher on TASC and existing roster.";
        			updateKidAuditData(kidRecord, errorMessage, null, NOT_PROCESSED);
        			return errorMessage;
        			
        		}else{
        			//remove student-roster connection, add to new teacher
					inactivateEnrollmentRoster(kidRecord, kidRecord.getCurrentContextUserId(), rosters);
					User teacher = new User();
					teacher.setId(rosters.get(0).getTeacherId());
					teacher.setCurrentContextUserId(rosters.get(0).getTeacherId());
					interimGroupService.deleteOnRosterChange(teacher, kidRecord.getEnrollment().getStudentId());
					
					interimService.exitStudentRosterKIDSInterim(kidRecord.getEnrollment(), rosters.get(0));
					//check for roster for subject associated with new teacher came in TASC
					createRosterAndEnrollmentRoster(kidRecord, attendanceSchool, aypSchool);
        		}
        	}
        }else{//Roster not found for subject and student, then add new roster
        	if(!"99".equalsIgnoreCase(kidRecord.getCourseStatus().toString())){
        		createRosterAndEnrollmentRoster(kidRecord, attendanceSchool, aypSchool);
        	}else{
        		errorMessage = "Error for student:["+kidRecord.getStudent().getStateStudentIdentifier()+"]. Roster can not be created as course status is 99";
    			updateKidAuditData(kidRecord, errorMessage, null, NOT_PROCESSED);
    			return errorMessage;
        	}
        }	
        
        return errorMessage;
	}



	/**
	 * Create new roster if not present and associcate student enrollment with roster for the subject at ATT school
	 * @param kidRecord
	 * @param attendanceSchool
	 * @param aypSchool
	 */
	private void createRosterAndEnrollmentRoster(KidRecord kidRecord, Organization attendanceSchool,
			Organization aypSchool) {
		
		EnrollmentsRosters enrollmentsRosters;
		Roster roster = new Roster();
		roster.setAttendanceSchoolId(attendanceSchool.getId());
		roster.setTeacherId(kidRecord.getEducator().getId());
		roster.setStateSubjectAreaId(kidRecord.getStateSubjectAreaId());
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
			//add Enrollment to Roster
			if (null != kidRecord.getCourseStatus() && (99 != kidRecord.getCourseStatus())) {
				enrollmentsRosters = addEnrollmentToRoster(kidRecord, kidRecord.getCurrentContextUserId(), existingRosters);
				//auto assign interim tests
				interimService.autoAssignKIDSInterim(kidRecord.getEnrollment(), existingRosters.get(0));
				
				rosterService.addStudentToRosterEventToDomainAuditHistory(enrollmentsRosters, kidRecord.getRoster());				
				kidRecord.setRecordStatus(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS.toString());
				LOGGER.debug(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS + "for student: " +kidRecord.getStudent().getStateStudentIdentifier());
			}
			
		}else{
			Roster newRoster = addRoster(kidRecord, kidRecord.getCurrentContextUserId(), aypSchool, attendanceSchool);
			kidRecord.setRoster(newRoster);
			
			if(newRoster.getId() != null){
				LOGGER.debug(RecordSaveStatus.ROSTER_ADDED + "for student: " +kidRecord.getStudent().getStateStudentIdentifier());
			
				//add Enrollment to Roster
				if (null != kidRecord.getCourseStatus() && (99 != kidRecord.getCourseStatus())) {
					existingRosters = new ArrayList<Roster>();
					existingRosters.add(newRoster);
					enrollmentsRosters = addEnrollmentToRoster(kidRecord, kidRecord.getCurrentContextUserId(), existingRosters);
					
					//auto assign interim tests
					interimService.autoAssignKIDSInterim(kidRecord.getEnrollment(), newRoster);
					
					rosterService.addStudentToRosterEventToDomainAuditHistory(enrollmentsRosters, kidRecord.getRoster());				
					kidRecord.setRecordStatus(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS.toString());
					LOGGER.debug(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS + "for student: " +kidRecord.getStudent().getStateStudentIdentifier());
				}
			}
		}		
	}
	
	/**
	 * Create student enrollment at ATT school
	 * @param kidRecord
	 * @param aypSchool
	 * @param attendanceSchool
	 * @param student
	 * @param enrollments
	 */
	private void addEnrollment(KidRecord kidRecord, Organization aypSchool, Organization attendanceSchool,
			Student student, List<Enrollment> enrollments) {
		Enrollment newEnrollment = kidRecord.getEnrollment();
		newEnrollment.setAttendanceSchoolId(attendanceSchool.getId());
		newEnrollment.setStudentId(student.getId());
		newEnrollment.setStudent(student);
		newEnrollment.setCurrentContextUserId(kidRecord.getCurrentContextUserId());
		newEnrollment.setSourceType(kidRecord.getRecordType());
		if(aypSchool != null && aypSchool.getId() != null) {
			newEnrollment.setAypSchoolId(aypSchool.getId());
		} else {
			newEnrollment.setAypSchoolId(attendanceSchool.getId());
		}
		if(aypSchool != null && aypSchool.getId() != null) {
			newEnrollment.setAypSchoolIdentifier(aypSchool.getDisplayIdentifier());
		} else {
			newEnrollment.setAypSchoolIdentifier(attendanceSchool.getDisplayIdentifier());
		}
		newEnrollment.setRestrictionId(kidRecord.getRoster().getRestrictionId());
		kidRecord.setCreated(true);
		newEnrollment = enrollmentService.add(newEnrollment);
		enrollments.add(newEnrollment);
		kidRecord.setEnrollment(newEnrollment);
		LOGGER.debug(RecordSaveStatus.ENROLLMENT_ADDED + "for student: " +kidRecord.getStudent().getStateStudentIdentifier());
	}



	/**
	 * Inactivate enrollmentroster record. ie..remove student teacher association
	 * @param kidRecord
	 * @param currentContextUserId
	 * @param rosters
	 */
	private void inactivateEnrollmentRoster(KidRecord kidRecord, Long currentContextUserId, List<Roster> rosters) {
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



	/**
	 * Create Roster for the subject at ATT school
	 * @param kidRecord
	 * @param currentContextUserId
	 * @param aypSchool
	 * @param attendanceSchool
	 * @return
	 */
	private Roster addRoster(KidRecord kidRecord, Long currentContextUserId, Organization aypSchool,
			Organization attendanceSchool) {
		Roster roster = new Roster();
		
		String courseSectionName = rosterService.getWebServiceCourseSectionName(kidRecord.getEducator().getFirstName(), 
				kidRecord.getEducator().getSurName(), kidRecord.getStateSubjectArea().getAbbreviatedName(),
				kidRecord.getRecordType());
		
		roster.setCourseSectionName(courseSectionName);
		roster.setAttendanceSchoolId(attendanceSchool.getId());
		roster.setTeacherId(kidRecord.getEducator().getId());
		roster.setStateSubjectAreaId(kidRecord.getStateSubjectAreaId());
		roster.setStateCourseCode(kidRecord.getStateCourseCode());
		roster.setRestrictionId(kidRecord.getRoster().getRestrictionId());
		roster.setCurrentSchoolYear(kidRecord.getCurrentSchoolYear());
		roster.setSourceType(kidRecord.getRecordType());		
		roster.setStateSubjectCourseIdentifier(kidRecord.getTascStateSubjectAreaCode());
		if(kidRecord.getLocalCourseId() != null){
			roster.setLocalCourseId(kidRecord.getLocalCourseId().toString());
		}		
		roster.setEducatorschooldisplayidentifier((kidRecord.getEducatorSchoolId() != null && !kidRecord.getEducatorSchoolId().isEmpty()) ? kidRecord.getEducatorSchoolId() : kidRecord.getAttendanceSchoolProgramIdentifier());
		
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
	
	
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean isKAPKid(KidRecord kidRecord) {
    	boolean isKapKid = false;

    	if(kidRecord.getStateSubjectAreaCode() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateSubjectAreaCode()) &&(
    			"01".equals(kidRecord.getStateSubjectAreaCode())||
    			"02".equals(kidRecord.getStateSubjectAreaCode())||
    			"51".equals(kidRecord.getStateSubjectAreaCode())||
    			"52".equals(kidRecord.getStateSubjectAreaCode())||
    			"80".equals(kidRecord.getStateSubjectAreaCode())||
    			"81".equals(kidRecord.getStateSubjectAreaCode())||
    			"82".equals(kidRecord.getStateSubjectAreaCode())
    			)) {
    		isKapKid = true;
    	}
       	
    	
    	return isKapKid;
    }	
	
	
	/**
	 * update audit data like reason, status and email template id
	 * @param kidRecord
	 * @param reason
	 * @param templateId
	 * @param status
	 */
	private void updateKidAuditData(KidRecord kidRecord, String reason, String templateId, String status) {
		String emailSentToUsersList = null;
		kidRecord.setEmailSent(false);
		kidRecord.setTriggerEmail(false);
		kidRecord.setStatus(status);
		if (reason != null && !reason.isEmpty()) {
			if(kidRecord.getReasons() != null){
				kidRecord.setReasons((kidRecord.getReasons()).concat(reason));
			}else{
				kidRecord.setReasons(reason);
			}
			
		}

		if (templateId != null) {							
				kidRecord.setTriggerEmail(true);
				kidRecord.setEmailSentTo(emailSentToUsersList);
				StringBuilder sb = null;
				
				if (kidRecord.getEmailTemplateIds() != null && !kidRecord.getEmailTemplateIds().isEmpty()) {
					sb = new StringBuilder(kidRecord.getEmailTemplateIds() + ",");
				} else {
					sb = new StringBuilder();
				}
				sb.append(templateId);
				kidRecord.setEmailTemplateIds(sb.toString());			
		}
		
	}
	
	private boolean compareStudentFields(KidRecord kidRecord, Student student){			
		
		if(!kidRecord.getStudent().getLegalLastName().equals(student.getLegalLastName())
				|| !kidRecord.getStudent().getLegalFirstName().equals(student.getLegalFirstName())
				|| ((kidRecord.getStudent().getLegalMiddleName() != null && student.getLegalMiddleName() == null)
						|| (kidRecord.getStudent().getLegalMiddleName() == null && student.getLegalMiddleName() != null)
						|| (kidRecord.getStudent().getLegalMiddleName() != null && student.getLegalMiddleName() != null
								&& !kidRecord.getStudent().getLegalMiddleName().equals(student.getLegalMiddleName())))
				|| !kidRecord.getStudent().getDateOfBirthStr().equals(student.getDateOfBirthStr())){
			return false;
		}
		
		if((kidRecord.getStudent().getGenerationCode() != null && student.getGenerationCode() == null)
				|| (kidRecord.getStudent().getGenerationCode() == null && student.getGenerationCode() != null)
				|| (kidRecord.getStudent().getGenerationCode() != null && student.getGenerationCode() != null
						&& !kidRecord.getStudent().getGenerationCode().equals(student.getGenerationCode()))){
			return false;
		}
		
		if((kidRecord.getStudent().getGender() != null && student.getGender() == null)
				|| (kidRecord.getStudent().getGender() == null && student.getGender() != null)
				|| (kidRecord.getStudent().getGender() != null && student.getGender() != null
						&& kidRecord.getStudent().getGender().intValue() != student.getGender().intValue())){
			return false;
		}
		
		if((kidRecord.getStudent().getHispanicEthnicity() != null && student.getHispanicEthnicity() == null)
				|| (kidRecord.getStudent().getHispanicEthnicity() == null && student.getHispanicEthnicity() != null)
				|| (kidRecord.getStudent().getHispanicEthnicity() != null && student.getHispanicEthnicity() != null
						&& !kidRecord.getStudent().getHispanicEthnicityStr().equals(student.getHispanicEthnicityStr()))){
			return false;
		}
		return true;
		
	}
	
	private void processCleanupTASCRosters(Enrollment enroll, KidRecord kidRecord, Long contentAreaId){
		LOGGER.debug("processCleanupTASCRosters for enrollmentId :"+ enroll.getId());
		List<Roster> rosters = rosterService.getRostersByContentAreaAndEnrollment(contentAreaId, enroll);
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
        
        //Remove predictive tests
		studentsTestsService.removeStudentPredictiveTests(enroll.getId(), new Long(kidRecord.getCurrentSchoolYear()), contentAreaId);
		
        rosters = rosterService.getRostersByContentAreaAndEnrollment(null, enroll);
        if(rosters != null && rosters.size() == 0 && CollectionUtils.isEmpty(enroll.getSubjectAreaList()) && SourceTypeEnum.TASCWEBSERVICE.getCode().equals(enroll.getSourceType())){
    		enroll.setActiveFlag(false);
    		enroll.setRestrictionId(1);
    		enrollmentService.update(enroll);    		
        }
		    
	}
	
	private void inactivateEnrollmentRoster(Long currentContextUserId, Roster roster, Long enrollmentId) {
		EnrollmentsRosters enrollmentsRosters = new EnrollmentsRosters(enrollmentId, roster.getId());
		enrollmentsRosters.setActiveFlag(false);			
		enrollmentsRosters.setCourseEnrollmentStatusId(99L);
		enrollmentsRosters.setCurrentContextUserId(currentContextUserId);								
		enrollmentsRostersService.updateEnrollementRosterToInActive(enrollmentsRosters);
	}
	
	private void buildDashboardErrorMessageList(KidRecord kidRecord, Organization aypSchool, Organization attendanceSchool, String status, String messageType, String errorMessage, User educator, String subjectArea){
		KidsDashboardRecord dashboardRecord = null;		
		if (kidRecord.getStateSubjectArea() != null) {
			dashboardRecord = getKidsDashboardRecord(kidRecord, aypSchool, attendanceSchool, status, messageType, errorMessage, educator);
			dashboardRecord.setSubjectArea(kidRecord.getStateSubjectArea().getAbbreviatedName());
			kidRecord.getKidsDashboardRecords().add(dashboardRecord);
			kidRecord.getErrorSubjectCodes().add(kidRecord.getStateSubjectArea().getAbbreviatedName());
		}else if(kidRecord.getInvalidSubjectAreaCode() != null && kidRecord.getInvalidSubjectAreaCode()){
			dashboardRecord = getKidsDashboardRecord(kidRecord, aypSchool, attendanceSchool, status, messageType, errorMessage, educator);
			kidRecord.getKidsDashboardRecords().add(dashboardRecord);
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
		dashboardRecord.setAypSchoolIdentifier(aypSchool.getDisplayIdentifier());
		dashboardRecord.setAttendanceSchoolIdentifier(attendanceSchool.getDisplayIdentifier());
		dashboardRecord.setAypSchoolId(aypSchool.getId());
		dashboardRecord.setAttendanceSchoolId(attendanceSchool.getId());
		dashboardRecord.setAypSchoolName(aypSchool.getOrganizationName());
		dashboardRecord.setAttendanceSchoolName(attendanceSchool.getOrganizationName());
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
		}else{
			if(kidRecord.getTascEducatorIdentifier() != null){
				dashboardRecord.setEducatorIdentifier(kidRecord.getTascEducatorIdentifier());
			}
			if(kidRecord.getTeacherLastName() != null){
				dashboardRecord.setEducatorLastName(kidRecord.getTeacherLastName());
			}
			if(kidRecord.getTeacherFirstName() != null){
				dashboardRecord.setEducatorFirstName(kidRecord.getTeacherFirstName());
			}
		}
		
		return dashboardRecord;
		
	}
	
	
	
}