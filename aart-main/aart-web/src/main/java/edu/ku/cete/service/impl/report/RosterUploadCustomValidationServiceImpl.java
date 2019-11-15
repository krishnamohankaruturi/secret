package edu.ku.cete.service.impl.report;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.RosterRecord;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.model.enrollment.RosterDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.RecordSaveStatus;
import static edu.ku.cete.util.CommonConstants.DELAWARE_ABBR_NAME;
import static edu.ku.cete.util.CommonConstants.DC_ABBR_NAME;
 

/**
 * Added By Prasanth 
 * User Story: US16352:
 * Spring batch upload for data file(Roster)
 */
@SuppressWarnings("unused")
@Service
public class RosterUploadCustomValidationServiceImpl implements BatchUploadCustomValidationService{
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
    private GradeCourseService gradeCourseService;
	
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private StudentDao studentDao;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RosterDao rosterDao;
	
    @Autowired
    private EnrollmentDao enrollmentDao;
	
	@Autowired
	private OrganizationTypeService organizationTypeService;

	@Autowired
	private AppConfigurationService appConfigurationService;
	
	final static Log logger = LogFactory.getLog(RosterUploadCustomValidationServiceImpl.class);

	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) {
		RosterRecord rosterRecord = (RosterRecord) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		Long batchUploadId = (Long) params.get("batchUploadId");
		Long stateId = (Long) params.get("stateId");
		Long districtId = (Long) params.get("districtId");
		Long schoolId = (Long)params.get("schoolId");
		
		String lineNumber = rosterRecord.getLineNumber();
		
		boolean validationPassed = true;
		String organizationTypeCode = "";
		
		Long parentOrgId = (Long)params.get("selectedOrgId");
		Long organizationId  = parentOrgId;
		
		ContractingOrganizationTree contractingOrganizationTree = (ContractingOrganizationTree)params.get("contractingOrganizationTree");
		
		Organization currentContext = (Organization) params.get("currentContext");
		Long currentOrgId = currentContext.getId();
		
		String uploadTypeCode = (String) params.get("uploadTypeCode");
		if ("ROSTER_XML_RECORD_TYPE".equals(uploadTypeCode)) {
			if (StringUtils.isNotBlank(rosterRecord.getSchool().getDisplayIdentifier())) {
				OrganizationType organizationType = organizationTypeService.getByTypeCode("SCH");
				Organization school = organizationService.getByDisplayIdRelationAndType(rosterRecord.getSchool().getDisplayIdentifier(), 
					organizationType.getOrganizationTypeId());
				if(school != null){
					organizationId = school.getId();
				}
				Organization state = organizationService.getContractingOrganization(school.getId());
				contractingOrganizationTree = organizationService.getTree(state);
				currentOrgId = state.getId();
			}
		}
		UserDetailImpl userDetails =  (UserDetailImpl)params.get("currentUser");
		rosterRecord.setCurrentUser(userDetails.getUser());
		
		Restriction restriction = (Restriction)params.get("restriction");
		
		AssessmentProgram assessmentProgram = (AssessmentProgram) params.get("assessmentProgram");

		
		Set<Long> contractingOrganizationIds = contractingOrganizationTree.getContractingOrganizationTreeIds();
		String stateStudentIdent = rosterRecord.getStudent().getStateStudentIdentifier();
		
	     if(contractingOrganizationIds == null || 
	    		 	CollectionUtils.isEmpty(contractingOrganizationIds)) {
					logger.debug("Invalid input organizations" +
							" when Enforcing uniqueness of state id in students"
							+ rosterRecord);
		
			if (rosterRecord != null && rosterRecord.getStudent() != null) {
				
				rosterRecord.addInvalidField("stateStudentIdentifier",
						stateStudentIdent , true,
						InvalidTypes.SCHOOL_NOT_CONTRACTING_FOR_ASSESSMENT);
				
				logger.debug("Student is rejected because " + rosterRecord
						+ "School not contracting for assessment");
				
				validationPassed = false ;
			}
		}
	    else{
	    	int loggedUserCurrSchYear = (int) (long) userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
	    	if ("ROSTER_XML_RECORD_TYPE".equals(uploadTypeCode)) {
	    		loggedUserCurrSchYear = rosterDao.getCurrentSchoolYear(currentOrgId);
        	}
	    	rosterService.validateSchoolIdentifiers(rosterRecord, organizationId, loggedUserCurrSchYear);
	    
	    	if (rosterRecord.isDoReject()) {
	    		validationPassed = false ;
	    	}	
	    	else{
	    		if("ROSTER_XML_RECORD_TYPE".equals(params.get("uploadTypeCode"))){
	    			StringBuffer rosterName = new StringBuffer(rosterRecord.getStateSubjectArea().getName());
	    			rosterName.append("-");
	    			rosterName.append(rosterRecord.getEducatorFirstName().charAt(0));
	    			rosterName.append(".");
	    			if(rosterRecord.getEducatorLastName().length() > 10){
	    				rosterName.append(rosterRecord.getEducatorLastName().substring(0, 9));
	    			} else {
	    				rosterName.append(rosterRecord.getEducatorLastName());
	    			}
	    			rosterRecord.getRoster().setCourseSectionName(rosterName.toString());
	    		}
	    		rosterRecord.getRoster().setRestriction(restriction);
	    		rosterRecord.getEnrollment().setRestrictionId(restriction == null ? 1: restriction.getId());
                rosterRecord.getRoster().setRestrictionId(restriction == null ? 1: restriction.getId());
                if (rosterRecord.getDlmStatus()){
                	rosterRecord.setAssessmentProgramId(assessmentProgram.getId());
                }                    
                //rosterRecord.getStudent().setStateId(userDetails.getUser().getContractingOrgId());
                Organization attendanceSchool = contractingOrganizationTree
        				.getUserOrganizationTree().getOrganization(rosterRecord.getSchoolIdentifier());
                
                rosterRecord.setCurrentContextUserId(userDetails.getUserId());
                if (attendanceSchool == null) {
                	rosterRecord.addInvalidField(FieldName.SCHOOL_IDENTIFIER+"",
        					 rosterRecord.getSchoolIdentifier(), true,
        					InvalidTypes.SCHOOL_IDENTIFIER_NOT_FOUND);
                	rosterRecord
        					.setSaveStatus(RecordSaveStatus.AYP_SCHOOL_NOT_FOUND);
                	validationPassed = false ;
        		}
                else{
                	attendanceSchool.setCurrentContextUserId(rosterRecord
            				.getCurrentContextUserId());
    				rosterRecord.setSchool(attendanceSchool);
    				Student student = rosterRecord.getStudent();
    				String mappedCompRace = null;
    		    	if(StringUtils.isNotEmpty(student.getComprehensiveRace())){
    		    		mappedCompRace = studentDao.findMappedComprehensiveRaceCode(student.getComprehensiveRace());
    		    	}
    		    	student.setComprehensiveRace(mappedCompRace);
    		    	if(student.getStateId() == null) {
    			        // set contracting organization on student
    			        Organization studentState = organizationService.getContractingOrganization(attendanceSchool.getId());
    			        student.setStateId(studentState.getId());
    			        rosterRecord.getStudent().setStateId(studentState.getId());
    		    	}
    		    	
    		    	if(!rosterRecord.getStateStudentIdentifier().trim().isEmpty() ){
    		    		String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
    		    		int allowedLength = Integer.parseInt(stateStudentIdentifierLength);
    		    		if(rosterRecord.getStateStudentIdentifier().trim().length()>allowedLength){
    		    			String stateStudentIdentifierLengthError = appConfigurationService.getByAttributeCode(CommonConstants.STATE_STUDENT_IDENTIFIER_LENGTH_ERROR);
    		    			String errorMessage = stateStudentIdentifierLengthError.concat(Integer.toString(allowedLength)).concat(".");
    		    			rosterRecord.addInvalidField("stateStudentIdentifier", rosterRecord.getStateStudentIdentifier(), true, 
    		    					errorMessage);
	    		    		validationPassed = false;
    		    		}
		    		}
    		    	
    		    	student = studentService.validateIfStudentExists(student);
        			if (student.isDoReject()) {
        				rosterRecord.addInvalidField(
        	    				"stateStudentIdentifier" ,
        	    				stateStudentIdent, true,
        	    				InvalidTypes.NOT_FOUND);
        				rosterRecord.setDoReject(true);
        				validationPassed = false ;
        			}else{
        				// validate if the teacher is found.Needed if saving roster.
        				User educator = rosterRecord.getEducator();
        				rosterRecord.appendSchoolIdentifier(attendanceSchool.getId());
        				educator.setActiveFlag(false);
        				
        				// Cause: org.postgresql.util.PSQLException: ERROR: duplicate key value
        				// violates unique constraint "uk_email"
        				User dbEducator = userService.getByEmail(educator.getEmail());
        				if (dbEducator != null && !dbEducator.getUniqueCommonIdentifier().equals(
        								educator.getUniqueCommonIdentifier())) {
        					rosterRecord.addInvalidField(FieldName.EDUCATOR_IDENTIFIER+""
        						,
        							"xml: " + rosterRecord.getEducatorIdentifier() + "db:"
        									+ dbEducator.getUniqueCommonIdentifier()
        									, false,
        							InvalidTypes.MIS_MATCH);
        					rosterRecord.setDoReject(true);
        					validationPassed = false ;
        				}
        				else{
        					List<User> foundEducators = userService
        							.getByOrganizationAndUniqueCommonIdentifier(
        									attendanceSchool.getId(),
        									educator.getUniqueCommonIdentifier());
        					User foundEducator = null;
        					if (!foundEducators.isEmpty()) {
        						foundEducator = foundEducators.get(0);
        						
        						//US17281 validate inactive user
        						User checkEducator = userService.getByUserName(foundEducator.getUserName());
        						if(checkEducator.getStatusCode().equals("Inactive")){
        							rosterRecord.addInvalidField(
       									 FieldName.EDUCATOR_IDENTIFIER+"",
       												 rosterRecord.getEducatorIdentifier()
       												, true,
       										InvalidTypes.EDUCATOR_ID_INACTIVE);
       							rosterRecord.setDoReject(true);
       							validationPassed = false ;
        						}
        						
        						if (foundEducator == null) {
        							
        							rosterRecord.addInvalidField(
        									 FieldName.EDUCATOR_IDENTIFIER+"",
        												 rosterRecord.getEducatorIdentifier()
        												, true,
        										InvalidTypes.EDUCATOR_ID_NOT_FOUND);
        							rosterRecord.setDoReject(true);
        							validationPassed = false ;
    							}
        						rosterRecord.setEducator(foundEducator);
        						if (!(rosterRecord.getEducator() == null)) {
            						
        							//For checking the role of the user in the organization provided in the upload and return a value > 0 if Teacher
                					Integer teacherCount = userService.getTeacherCount(rosterRecord.getEducator().getId(), rosterRecord.getSchool().getId(), CommonConstants.GROUP_CODE_TEACHER);
                					                					
                					if (teacherCount <= 0)	{
                						rosterRecord.addInvalidField(FieldName.EDUCATOR_IDENTIFIER+"",
        										rosterRecord.getEducatorIdentifier()
        												, true,
        										InvalidTypes.USER_IS_NOT_A_TEACHER );
        							rosterRecord.setDoReject(true);
        							validationPassed = false ;
                					}
                				}        				
        					}
        					else{
        						Organization residenceDistrict = contractingOrganizationTree
        								.getUserOrganizationTree().getOrganization(
        										rosterRecord.getResidenceDistrictIdentifier());
        						String district = "";
        						if (residenceDistrict != null) {
        							foundEducators = userService
        									.getByOrganizationAndUniqueCommonIdentifier(
        											residenceDistrict.getId(),
        											educator.getUniqueCommonIdentifier());
        							district = residenceDistrict.getDisplayIdentifier();
        						}
        						if (!foundEducators.isEmpty()) {
        							foundEducator = foundEducators.get(0);
        						}
        						
        						if (foundEducator == null) {
        							
        							rosterRecord.addInvalidField(FieldName.EDUCATOR_IDENTIFIER+"",
        										rosterRecord.getEducatorIdentifier()
        												, true,
        										InvalidTypes.EDUCATOR_ID_NOT_FOUND );
        							rosterRecord.setDoReject(true);
        							validationPassed = false ;
        						}
        						rosterRecord.setEducator(foundEducator);
        				    }
        				        					
        					if( validationPassed){
        						
        						// set course enrollment status and state subject area and state course.
        					//	COURSE_ENROLLMENT_STATUS_CODE
        					/*	Category scrsEnrollmentStatus = getCategory( 
        								courseEnrollmentStatusType.getId(),
        								rosterRecord.getEnrollmentStatusCodeStr());
        								*/
        						//rosterRecord.setEnrollmentStatus(scrsEnrollmentStatus);

        						ContentArea scrsStateSubjectArea = rosterService.getContentArea(
        								rosterRecord.getStateSubjectAreaCode(), attendanceSchool.getId());
        						if (scrsStateSubjectArea == null) {
        							//rosterRecord.setSaveStatus(scrsRecordSaveStatus);
        							// could not find the subject area
        							rosterRecord.addInvalidField(FieldName.SUBJECT+"",
        									rosterRecord.getStateSubjectAreaCode()
        											, true,
        											InvalidTypes.SUBJECT_NOT_FOUND.toString());
        							validationPassed = false;
        						}
        						else{
        							rosterRecord.setStateSubjectArea(scrsStateSubjectArea);

        							Organization contractOrganization = userDetails.getUser().getContractingOrganization();   								
     								if(contractOrganization.getDisplayIdentifier()!=null && !("").equals(contractOrganization.getDisplayIdentifier()) 
     										&& !("").equals(rosterRecord.getStateSubjectArea().getAbbreviatedName()) 
     										&& (contractOrganization.getDisplayIdentifier().equals(DELAWARE_ABBR_NAME) || contractOrganization.getDisplayIdentifier().equals(DC_ABBR_NAME))
     										&& rosterRecord.getStateSubjectArea().getAbbreviatedName().equalsIgnoreCase("Sci")
     										&& (rosterRecord.getStateCourseCode()==null || rosterRecord.getStateCourseCode().equals(""))){
 		        								
     									if (student.getStateStudentIdentifier()!= null && attendanceSchool.getDisplayIdentifier()!=null 
 		        								&& rosterRecord.getCurrentSchoolYear()>0) {
 		        									int lowerGradeBound = 10;
 		        									if (contractOrganization.getDisplayIdentifier().equals(DELAWARE_ABBR_NAME)) {
 		        										lowerGradeBound = 10;
 		        									}else if (contractOrganization.getDisplayIdentifier().equals(DC_ABBR_NAME)) {
 		        										lowerGradeBound = 9;
 		        									}
 		        									Enrollment enrollment = enrollmentDao.getByStateStudentIDSchoolYearAndSchool(student.getStateStudentIdentifier(), rosterRecord.getCurrentSchoolYear(), attendanceSchool.getDisplayIdentifier(), true);
 		        										if(enrollment.getGradeCourse().getGradeLevel() >= lowerGradeBound){        								
 		        												rosterRecord.setStateCourseCode("BIO");
 		        										}
 		        								}    
     								}
        							
        							
	        						GradeCourse gradeCourse = null;
	        						GradeCourse grade = null;
	        						if (rosterRecord.getEnrollment().getCurrentGradeLevel() != null) {
	        							gradeCourse = new GradeCourse();
	        							String strGradeLevel = "0"
	        									+ rosterRecord.getEnrollment().getCurrentGradeLevel();
	        							// add a zero to the beginning and then if there are more than 2
	        							// digits remove the first digit
	        							strGradeLevel = strGradeLevel.length() > 2 ? strGradeLevel
	        									.substring(1) : strGradeLevel;
	        							// store in abbr name to send to query
	        							gradeCourse.setAbbreviatedName(strGradeLevel);
	        							gradeCourse.setCurrentContextUserId(rosterRecord.getUser().getId());
	        							grade = gradeCourseService
	        									.getWebServiceGradeCourseByCode(gradeCourse);
	        						}
	        						Long rosterCourseId = null;
	        						Organization contractOrg = userDetails.getUser().getContractingOrganization(); 
	        						if ( rosterRecord.getStateCourseCode() != null && rosterRecord.getStateCourseCode().trim().length() > 0
	        								&& rosterRecord.getStateSubjectArea() != null ) {
	        							GradeCourse validGC = rosterService.findValidCourse(rosterRecord.getStateCourseCode(), 
	        									rosterRecord.getStateSubjectArea().getId()); 
	        							if (validGC == null) {
	        								if(contractOrg.getDisplayIdentifier().equals(DELAWARE_ABBR_NAME) || contractOrg.getDisplayIdentifier().equals(DC_ABBR_NAME)){
	        									rosterRecord.addInvalidField("stateCourseCode",
		        										rosterRecord.getStateCourseCode(), true,
		        										"does not apply for selected subject and organization.");
	        									validationPassed = false;
	        									
	        								}else{
	        									rosterRecord.addInvalidField("stateCourseCode",
		        										rosterRecord.getStateCourseCode(), true,
		        										InvalidTypes.COURSE_NOT_FOUND.toString());
	        								
	        								validationPassed = false;
	        								}
	        							} else {		        								
	        								  								
	        								if(contractOrg.getDisplayIdentifier()!=null && !("").equals(rosterRecord.getStateSubjectArea().getAbbreviatedName()) 
	        										&& !("").equals(contractOrg.getDisplayIdentifier()) 
	        										&& (contractOrg.getDisplayIdentifier().equals(DELAWARE_ABBR_NAME) || contractOrg.getDisplayIdentifier().equals(DC_ABBR_NAME)) 
	        										&& rosterRecord.getStateSubjectArea().getAbbreviatedName().equalsIgnoreCase("Sci")
	        										&& !("").equals(rosterRecord.getStateCourseCode()) 
	        										&& rosterRecord.getStateCourseCode().equalsIgnoreCase("BIO")
	        										){
	        									rosterCourseId = validGC.getId();
	        								}else{	        									
	        									rosterRecord.addInvalidField("stateCourseCode",
		        										rosterRecord.getStateCourseCode(), true,
		        										"does not apply for selected subject and organization.");
		        								validationPassed = false;	
	        								}      								
	        							}
	        						}
	        						
	        						 contractOrg = userDetails.getUser().getContractingOrganization();   								
    								if(contractOrg.getDisplayIdentifier()!=null && !("").equals(contractOrg.getDisplayIdentifier()) 
    										&& !("").equals(rosterRecord.getStateSubjectArea().getAbbreviatedName()) 
    										&& (contractOrg.getDisplayIdentifier().equals(DELAWARE_ABBR_NAME) || contractOrg.getDisplayIdentifier().equals(DC_ABBR_NAME)) 
    										&& rosterRecord.getStateSubjectArea().getAbbreviatedName().equalsIgnoreCase("Sci")
    										&& !("").equals(rosterRecord.getStateCourseCode()) 
    										&& rosterRecord.getStateCourseCode().equalsIgnoreCase("BIO")
    										){
		        						if (student.getStateStudentIdentifier()!= null && attendanceSchool.getDisplayIdentifier()!=null && rosterRecord.getCurrentSchoolYear()>0) {
        									int lowerGradeBound = 10;
        									if (contractOrganization.getDisplayIdentifier().equals(DELAWARE_ABBR_NAME)) {
        										lowerGradeBound = 10;
        									}else if (contractOrganization.getDisplayIdentifier().equals(DC_ABBR_NAME)) {
        										lowerGradeBound = 9;
        									}
		        							Enrollment enrollment = enrollmentDao.getByStateStudentIDSchoolYearAndSchool(student.getStateStudentIdentifier(), rosterRecord.getCurrentSchoolYear(), attendanceSchool.getDisplayIdentifier(), true);
		        							if(enrollment.getGradeCourse().getGradeLevel() < lowerGradeBound){
		        								rosterRecord.addInvalidField("stateCourseCode",
		        										rosterRecord.getStateCourseCode(), true,
		        										"BIO");
		        								validationPassed = false;	        								
		        							}
		        						}    
    								}
	        							        						
	        						
	        						if (grade != null) {
	        							rosterRecord.getEnrollment().setCurrentGradeLevel(grade.getId());
	        						} else {
	        							rosterRecord.getEnrollment().setCurrentGradeLevel(null);
	        						}
	        						if( validationPassed ){
	        							Roster roster = new Roster();
	        							roster.setCourseSectionName(rosterRecord.getCourseSection());
	        							roster.setAttendanceSchoolId(attendanceSchool.getId());
	        							roster.setTeacherId(rosterRecord.getEducator().getId());
	        							roster.setStateSubjectAreaId(rosterRecord.getStateSubjectAreaId());
	        							roster.setStateCourseCode(rosterRecord.getStateCourseCode().toUpperCase());
	        							roster.setStateCoursesId(rosterCourseId);
	        							if(rosterCourseId!=null) rosterRecord.getStateCourse().setId(rosterCourseId);
	        							roster.setRestrictionId(rosterRecord.getRoster().getRestrictionId());
	        							roster.setCurrentSchoolYear(rosterRecord.getCurrentSchoolYear());
	        							roster.setSourceType(rosterRecord.getSourceType());
        								roster.setAypSchoolId(attendanceSchool.getId());			
	        							if (roster.getRestrictionId() == null) {
	        								roster.setRestrictionId(rosterRecord.getEnrollment()
	        										.getRestrictionId());
	        							}
	        							roster.setCurrentContextUserId(userDetails.getUserId());
	        							
	        							String removeFromRosterStatus = rosterRecord.getRemovefromroster();
		        						if ( removeFromRosterStatus != null && (removeFromRosterStatus.equalsIgnoreCase("Yes")
		        								|| removeFromRosterStatus.equalsIgnoreCase("Remove"))) {
		        							
		        							if(StringUtils.isNotBlank(roster.getStateCourseCode())){
		        								
		        							rosterRecord.setRoster(rosterService.getRosterMatchedWithStateCourseCode(roster, attendanceSchool.getId()));			
		        							if (!rosterService.isRosterExitsWithStateCourseCode(roster,
		        									attendanceSchool.getId()) || rosterRecord.getRoster().getId() == null) {
		        								rosterRecord.addInvalidField(FieldName.REMOVE_FROM_ROSTER +"",
		        										"", true,
		        										InvalidTypes.REMOVE_FROM_ROSTER_ERROR );
		        								validationPassed = false;
		        							}
		        							
		        						}
		        					}
		        						if( validationPassed ){
		        							List<Roster> rosters = rosterService.getRostersByCriteria(roster, attendanceSchool.getId());
		        							if (rosters.size() > 1) {
		        								
		        								// the same teacher is teaching more than one class/roster.
		        								// in the same school and they have the same section name.
		        								rosterRecord.addInvalidField(FieldName.EDUCATOR_IDENTIFIER+""
		        										,
		        										rosterRecord.getEducatorIdentifier(), true,
		        										InvalidTypes.ROSTER_NOT_UNIQUE );
		        								validationPassed = false;
		        							}
		        							else{
		        								rosterRecord.setRoster(roster);
		        							}
		        						}
	        						}
        						}	
	        				}
	        			}
	        		}
                }
	    	} 
	    }
        if( ! validationPassed ){
        	for( InValidDetail inValidDetail  : rosterRecord.getInValidDetails() ){
        		String errMsg = new StringBuilder(stateStudentIdent)
				    			.append("###")
				    			.append(inValidDetail.getInValidMessage())
				    			.toString();
        		//errMsg = errMsg.replace("blank","");
				String fieldName = inValidDetail.getActualFieldName();
				fieldName = fieldName.replaceAll(" ", "").replaceAll("_", "");
				fieldName = fieldName.substring(0, 1).toLowerCase() + fieldName.substring(1);
				if("subject".equals(fieldName)){
					fieldName = "stateSubjectAreaCode";
				}				
				validationErrors.rejectValue(fieldName, "", new String[]{lineNumber, mappedFieldNames.get(fieldName)}, errMsg);
        	}
        }
        else
        {
        	rosterRecord.setCreatedUser(userDetails.getUserId());
        	rosterRecord.setModifiedUser(userDetails.getUserId());
        }
		//uploadedUser.setBatchUploadId(batchUploadId);
 		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", rosterRecord);
	//	logger.debug("Completed validation completed.");
		return customValidationResults;
	}


	
}
