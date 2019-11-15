package edu.ku.cete.service.impl.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.OrgAssessmentProgram;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.common.OrganizationType;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentRecord;
import edu.ku.cete.domain.enrollment.EnrollmentsOrganizationInfo;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.UploadedUser;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.BatchUploadCustomValidationForAlertService;
import edu.ku.cete.service.BatchUploadCustomValidationService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.OrganizationTypeService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.ParsingConstants;
 

/**
 * Added By Prasanth 
 * User Story: US16352:
 * Spring batch upload for data file(Enrollment)
 */
@SuppressWarnings("unused")
@Service
public class EnrollmentUploadCustomValidationServiceImpl implements BatchUploadCustomValidationService , BatchUploadCustomValidationForAlertService{
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
    private GradeCourseService gradeCourseService;
	
	@Autowired
	private StudentService studentService; 
	
	@Autowired
	private OrganizationTypeService organizationTypeService;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	
	@Autowired
	private TestSessionService testSessionService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private EnrollmentDao enrollmentDao;
	
	@Autowired
    private AppConfigurationService appConfigurationService;
	
	@Value("${testsession.status.gradechangeinactivated}")
	private String gradeChangeInactivatedPrefix;
	
	@Value("${student.upload.demographic.alert.message}")
	private String demographicAlertMessage;
	
	final static Log logger = LogFactory.getLog(EnrollmentUploadCustomValidationServiceImpl.class);

	@Override
	public Map<String, Object> customValidation(BeanPropertyBindingResult validationErrors, Object rowData, Map<String, Object> params, Map<String, String> mappedFieldNames) {
		logger.debug(" enrollment upload custom validation ..1 ");
		EnrollmentRecord enrollmentRecord = (EnrollmentRecord) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		
		String lineNumber = enrollmentRecord.getLineNumber();
		
		boolean validationPassed = true;
		String organizationTypeCode = "";
		
		Long parentOrgId = (Long)params.get("selectedOrgId");
		Long organizationId  = parentOrgId;
		Long stateId = (Long) params.get("stateId");
		
		ContractingOrganizationTree contractingOrganizationTree = (ContractingOrganizationTree)params.get("contractingOrganizationTree");
		
		Organization currentContext = (Organization) params.get("currentContext");
		Long currentOrgId = currentContext.getId();
		
		UserDetailImpl userDetails =  (UserDetailImpl)params.get("currentUser");
		Long studentStateId = userDetails.getUser().getContractingOrgId();
		
		String uploadTypeCode = (String) params.get("uploadTypeCode");
		if ("ENRL_XML_RECORD_TYPE".equals(uploadTypeCode)) {
			if (StringUtils.isNotBlank(enrollmentRecord.getResidenceDistrictIdentifier())) {
				OrganizationType organizationType = organizationTypeService.getByTypeCode("DT");
				Organization districtOrg = organizationService.getByDisplayIdAndType(
						enrollmentRecord.getResidenceDistrictIdentifier(), organizationType.getOrganizationTypeId(), stateId);
				Organization state = organizationService.getContractingOrganization(districtOrg.getId());
				studentStateId = state.getId();
				contractingOrganizationTree = organizationService.getTree(state);
				currentOrgId = state.getId();
			}
		}
		
		
		Restriction restriction = (Restriction)params.get("restriction");
		
		
		
		Set<Long> contractingOrganizationIds = contractingOrganizationTree.getContractingOrganizationTreeIds();
		
		String stateStudentIdent = enrollmentRecord.getStudent().getStateStudentIdentifier();
	     if(contractingOrganizationIds == null || 
	    		 	CollectionUtils.isEmpty(contractingOrganizationIds)) {
					logger.debug("Invalid input organizations" +
							" when Enforcing uniqueness of state id in students"
							+ enrollmentRecord);
		
			if (enrollmentRecord != null && enrollmentRecord.getStudent() != null) {
				
				enrollmentRecord.addInvalidField("stateStudentIdentifier",
						stateStudentIdent, true,
						InvalidTypes.SCHOOL_NOT_CONTRACTING_FOR_ASSESSMENT);
				
				logger.debug("Student is rejected because " + enrollmentRecord
						+ "School not contracting for assessment");
				
				validationPassed = false ;
			}
		}
	    else{
	    	enrollmentRecord.getEnrollment().setRestrictionId(restriction == null ? 1: restriction.getId());

	    	enrollmentRecord.getStudent().setStateId(studentStateId);
            Date currentDate = new Date();
            Date dob = enrollmentRecord.getDateOfBirth();
            if(dob != null && dob.compareTo(currentDate) > 0){
              	 enrollmentRecord.addInvalidField("dateOfBirth",
                          enrollmentRecord.getDateOfBirthStr(), true, " is not valid. Date is in future.");
              	validationPassed = false;
            }
            Date schoolEntryDate = enrollmentRecord.getSchoolEntryDate();
            if(schoolEntryDate != null && schoolEntryDate.compareTo(currentDate) > 0){
              	 enrollmentRecord.addInvalidField("schoolEntryDate",
                          enrollmentRecord.getSchoolEntryDateStr(), true, " is not valid. Date is in future.");
              	validationPassed = false;
            }
            String assessmentProgramESOLParticipationCode = appConfigurationService.getByAttributeCode(CommonConstants.ESOL_PARTICIPATION_CODE_ASSESSMENT_PROGRAM);
            List<String> assessmentProgramList = new ArrayList<String>();
    		if (StringUtils.isNotBlank(assessmentProgramESOLParticipationCode)
    				&& assessmentProgramESOLParticipationCode.contains(",")) {
    			String aPrgm[] = assessmentProgramESOLParticipationCode.split("\\,");
    			if (aPrgm.length > 0) {
    				for (int j = 0; j < aPrgm.length; j++) {
    					assessmentProgramList.add(aPrgm[j]);
    				}
    			}
    		} else {
    			assessmentProgramList.add(assessmentProgramESOLParticipationCode);
    		}
            
            String esolParticipationCode = enrollmentRecord.getEsolParticipationCode();
            String assessmentProgram1 = enrollmentRecord.getAssessmentProgram1();
			String assessmentProgram2 = enrollmentRecord.getAssessmentProgram2();
			String assessmentProgram3 = enrollmentRecord.getAssessmentProgram3();
            if(esolParticipationCode.equals("7")||esolParticipationCode.equals("8")){       	
                	if(assessmentProgramList.contains(assessmentProgram1)||
                			assessmentProgramList.contains(assessmentProgram2)||
                			assessmentProgramList.contains(assessmentProgram3)) {
                	}else {	
                		enrollmentRecord.addInvalidField("esolParticipationCode",
                                enrollmentRecord.getEsolParticipationCode(), true, " is not allowed.");
                    	 validationPassed = false;
                	}
            }else if(esolParticipationCode.equals("4")){
            	if(assessmentProgramList.contains(assessmentProgram1)||
            			assessmentProgramList.contains(assessmentProgram2)||
            			assessmentProgramList.contains(assessmentProgram3)) {
                		enrollmentRecord.addInvalidField("esolParticipationCode",
                                enrollmentRecord.getEsolParticipationCode(), true, " is not allowed.");
                    	 validationPassed = false;
                	}
            }
            Organization residentDistOrg = null;
            OrganizationType orgType = null;
            residentDistOrg = organizationService.getOrganizationByDisplayIdentifierAndRelationalOrgnizationId(enrollmentRecord.getResidenceDistrictIdentifier(), currentOrgId);
            if (residentDistOrg == null || residentDistOrg.getOrganizationTypeId() != CommonConstants.ORGANIZATION_TYPE_ID_5.longValue()) {            	
                enrollmentRecord.addInvalidField("residenceDistrictIdentifier", enrollmentRecord.getResidenceDistrictIdentifier(), true, " not found.");
                validationPassed = false;
            }else {            
	            Organization attendanceSchool =  null;
	            //validating if attendance school is found.Needed if saving enrollment.
	            //find the organization only from at or below the user's organization.
	            attendanceSchool = contractingOrganizationTree.getUserOrganizationTree()
	            			.getOrganization(enrollmentRecord.getAttendanceSchoolProgramIdentifier());
	            if (attendanceSchool == null) {
	                enrollmentRecord.addInvalidField("attendanceSchoolProgramIdentifier", enrollmentRecord.getAttendanceSchoolProgramIdentifier(), true, " not found.");
	                validationPassed = false;
	            }else{
	            	orgType = organizationTypeService.get(attendanceSchool.getOrganizationType().getOrganizationTypeId());
	            	if(orgType != null && !"SCH".equals(orgType.getTypeCode())){
	            		enrollmentRecord.addInvalidField("attendanceSchoolProgramIdentifier", enrollmentRecord.getAttendanceSchoolProgramIdentifier(), true, " is not a school.");
	            		validationPassed = false;
	            	}
	            	Long contractingOrgSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
	            	enrollmentRecord.setAttendanceSchoolId(attendanceSchool.getId());
	            	if ("ENRL_XML_RECORD_TYPE".equals(uploadTypeCode)) {
	            		contractingOrgSchoolYear = Long.valueOf(enrollmentDao.getContractingOrgSchoolYear(attendanceSchool.getId()));
	            	}
	                int enrollmentCurrentSchoolYear = enrollmentRecord.getCurrentSchoolYear();
	                if(contractingOrgSchoolYear != enrollmentCurrentSchoolYear){
	                	enrollmentRecord.addInvalidField("currentSchoolYear",
	        					enrollmentRecord.getCurrentSchoolYear() + ParsingConstants.BLANK, true," is invalid.");
	                	validationPassed = false;
	                }
	                
	                
	                OrganizationTreeDetail orgDetail = organizationService.getOrganizationDetailBySchoolId(attendanceSchool.getId());
	                if((orgDetail != null) && (!StringUtils.equals(orgDetail.getDistrictDisplayIdentifier(), enrollmentRecord.getResidenceDistrictIdentifier()))){
	                	enrollmentRecord.addInvalidField("residenceDistrictIdentifier", enrollmentRecord.getResidenceDistrictIdentifier(), true, " does not match the district of the attendance school "+enrollmentRecord.getAttendanceSchoolProgramIdentifier()+".");
	                    validationPassed = false;
	                }
	                
	            }
            }
            Organization  accountabilityDistrict =  null;
            if(!enrollmentRecord.getAccountabilityDistrictIdentifier().trim().isEmpty()){
            	accountabilityDistrict = organizationService.getOrganizationByDisplayIdentifierAndRelationalOrgnizationId(enrollmentRecord.getAccountabilityDistrictIdentifier(), organizationService.getStateIdByUserOrgId(currentOrgId));
	            if (accountabilityDistrict == null || accountabilityDistrict.getOrganizationTypeId() != CommonConstants.ORGANIZATION_TYPE_ID_5.longValue()) {
	            	enrollmentRecord.addInvalidField("accountabilityDistrictIdentifier", enrollmentRecord.getAccountabilityDistrictIdentifier(), true, "not found.");
	            	validationPassed = false;
	            }else{
	            	enrollmentRecord.setAccountabilityDistrictId( accountabilityDistrict.getId());
	            }
	           
            }
            
        	
            Organization accountabilitySchool =  null;
            OrganizationType accountabilityOrgType = null;
            
            if(accountabilityDistrict!= null && StringUtils.isNotEmpty(enrollmentRecord.getAypSchoolIdentifier())){
            	accountabilitySchool = organizationService.getOrganizationByDisplayIdentifierAndRelationalOrgnizationId(enrollmentRecord.getAypSchoolIdentifier().trim(), organizationService.getStateIdByUserOrgId(currentOrgId));
                  if (accountabilitySchool == null) {
                      enrollmentRecord.addInvalidField("aypSchoolIdentifier", enrollmentRecord.getAypSchoolIdentifier(), true, " not found.");
                      validationPassed = false;
                  }else{
                  	orgType = organizationTypeService.get(accountabilitySchool.getOrganizationType().getOrganizationTypeId());
                  	if(orgType != null && !"SCH".equals(orgType.getTypeCode())){
                  		enrollmentRecord.addInvalidField("aypSchoolIdentifier", enrollmentRecord.getAypSchoolIdentifier(), true, " is not a school.");
                  		validationPassed = false;
                  	}
                  	Long contractingOrgSchoolYear = userDetails.getUser().getContractingOrganization().getCurrentSchoolYear();
                  	enrollmentRecord.setAypSchoolId(accountabilitySchool.getId());
                  	OrganizationTreeDetail orgDetail = organizationService.getOrganizationDetailBySchoolId(accountabilitySchool.getId());
                      if((orgDetail != null) && (!StringUtils.equals(orgDetail.getDistrictDisplayIdentifier(), enrollmentRecord.getAccountabilityDistrictIdentifier().trim()))){
                      	enrollmentRecord.addInvalidField("accountabilityDistrictIdentifier", enrollmentRecord.getAccountabilityDistrictIdentifier(), true, " does not match the district of the accountability school "+enrollmentRecord.getAypSchoolIdentifier()+".");
                          validationPassed = false;
                      }
                      
                  }
            }else if(enrollmentRecord.getAccountabilityDistrictIdentifier().trim().isEmpty() && StringUtils.isNotEmpty(enrollmentRecord.getAypSchoolIdentifier())){
            	enrollmentRecord.addInvalidField("aypSchoolIdentifier", enrollmentRecord.getAccountabilityDistrictIdentifier(), true, " provided without accountability district.");
                validationPassed = false;
            }
            if(!enrollmentRecord.getStateStudentIdentifier().trim().isEmpty() ){
            	String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
            	int allowedLength = Integer.parseInt(stateStudentIdentifierLength);
            		if(enrollmentRecord.getStateStudentIdentifier().trim().length()>allowedLength){
                    	String stateStudentIdentifierLengthError = appConfigurationService.getByAttributeCode(CommonConstants.STATE_STUDENT_IDENTIFIER_LENGTH_ERROR);
            			enrollmentRecord.addInvalidField("stateStudentIdentifier", enrollmentRecord.getStateStudentIdentifier(), true, 
            					stateStudentIdentifierLengthError.concat(Integer.toString(allowedLength)).concat("."));
            			validationPassed = false;
            		}
            }
            
            
        	if(validationPassed){
        		Student student = studentService.getByStateStudentIdentifier(enrollmentRecord.getStudent(),enrollmentRecord.getAttendanceSchoolId());
        		if( student.isDoReject()){
        			enrollmentRecord.getInValidDetails().addAll(student.getInValidDetails());
        			enrollmentRecord.setDoReject(true);
        			validationPassed = false;
        		} else {
        			boolean sameExistingGrade = false;
                	if(enrollmentRecord.getCurrentGradeLevel() != null) {
                		GradeCourse grade = gradeCourseService.getIndependentGradeByAbbreviatedName(enrollmentRecord.getCurrentGradeLevel());
                		if (grade == null){
                        	enrollmentRecord.addInvalidField("currentGradeLevel",
                        			enrollmentRecord.getCurrentGradeLevel(),
                                    true, InvalidTypes.NOT_FOUND);
                        	validationPassed = false;
                        } else {
	                		if (student.getId() != null && studentService.isStudentOnlyInDLM(student.getId())) {
		                		//get existing enrollments and check grades
		                		List<Enrollment> currentEnrollments = enrollmentService.getCurrentEnrollmentsByStudentId(student.getId(), null, enrollmentRecord.getCurrentSchoolYear(),true);
		                		for (Enrollment enrollment : currentEnrollments) {
		                			if(enrollment.getAttendanceSchoolProgramIdentifier().equals(enrollmentRecord.getAttendanceSchoolProgramIdentifier()) && enrollment.getCurrentGradeLevel() == grade.getId()){
		                				sameExistingGrade = true;
		                			}
		                			if (enrollment.getCurrentGradeLevel() != grade.getId() & !enrollment.getAttendanceSchoolProgramIdentifier().equals(enrollmentRecord.getAttendanceSchoolProgramIdentifier())) {
		                            	enrollmentRecord.addInvalidField("currentGradeLevel",
		                            			null, true, null, String.format(" of %s is in conflict with the student's active enrollment at %s. Upload matching grade level or manually change the grade level for %s.", enrollment.getCurrentGrade(), enrollment.getAttendanceSchoolProgramIdentifier(), enrollment.getAttendanceSchoolProgramIdentifier()));
		                				validationPassed = false;
		                				break;
		                			}
		                		}
		                		if (validationPassed && !sameExistingGrade) {
				                		if (grade == null){
				                        	enrollmentRecord.addInvalidField("currentGradeLevel",
				                        			enrollmentRecord.getCurrentGradeLevel(),
				                                    true, InvalidTypes.NOT_FOUND);
				                        	validationPassed = false;
				                        } else {
				                        	List<Enrollment> enrollments = enrollmentService.getCurrentEnrollmentsByStudentId(student.getId(), enrollmentRecord.getAttendanceSchoolId(), enrollmentRecord.getCurrentSchoolYear(),true);
				                        	if (!enrollments.isEmpty()) {
				                        		Enrollment enrollment = enrollments.get(0);				                        	
				                        		List<Long> foundTestSessions = testSessionService.findTestSessionsToDeactivateForGradeChange(student.getId(), enrollmentRecord.getCurrentSchoolYear(), enrollmentRecord.getAttendanceSchoolId(), enrollment.getCurrentGradeLevel(), grade.getId());
				                        		foundTestSessions.addAll(testSessionService.findTestSessionsInactivatedBy(gradeChangeInactivatedPrefix, student.getId(), enrollmentRecord.getCurrentSchoolYear(), enrollmentRecord.getAttendanceSchoolId(),  grade.getId()));
					                        	if (foundTestSessions.isEmpty()) {
					                        		enrollmentRecord.getEnrollment().setCurrentGradeLevel(grade.getId());
					                        	} else {
					                            	enrollmentRecord.addInvalidField("currentGradeLevel",
					                            			null,
					                                        true, null, String.format(" change for student %s %s: %s must be completed manually in the system.", student.getLegalLastName(), student.getLegalFirstName(), student.getStateStudentIdentifier()));
					                            	validationPassed = false;
					                        	}
				                        	} else {
				                        		enrollmentRecord.getEnrollment().setCurrentGradeLevel(grade.getId());
				                        	}
				                        }
		                		}
	                		} else {
	                			enrollmentRecord.getEnrollment().setCurrentGradeLevel(grade.getId());
	                		}
                		}
                	} else {
                		enrollmentRecord.addInvalidField("currentGradeLevel",
                    			enrollmentRecord.getCurrentGradeLevel(),
                                true, InvalidTypes.NOT_FOUND);
                		validationPassed = false;
                	}
                	if (validationPassed) {
	        			String asseProgrmStr1 = enrollmentRecord.getAssessmentProgram1();
	        			String asseProgrmStr2 = enrollmentRecord.getAssessmentProgram2();
	        			String asseProgrmStr3 = enrollmentRecord.getAssessmentProgram3();
	        			if(StringUtils.isBlank(asseProgrmStr1) /*&&  StringUtils.isBlank(asseProgrmStr2) && StringUtils.isBlank(asseProgrmStr3)*/ )
	        			{
	        				enrollmentRecord.addInvalidField("assessmentProgram1",
	    	            			enrollmentRecord.getAssessmentProgram1(),
	    	                        true, " assessment program not specified");
	        				validationPassed = false;
	        				
	        			}
	        			if( validationPassed ){
	        				BatchUpload upload = (BatchUpload) params.get("batchUploadRecord");
	        				Long assessmentProgramId = upload.getAssessmentProgramId();
		        			Long orgId = userDetails.getUser().getCurrentOrganizationId();
		        			
		        			List<Long> assessmentPrgIds = new ArrayList<Long>();
		        			
		        			if( StringUtils.isNotEmpty(asseProgrmStr1 ) ){
		        				AssessmentProgram assessmentprg1 = null;
		        				if ("ENRL_XML_RECORD_TYPE".equals(uploadTypeCode)) {
		        					assessmentprg1 = validateAndGetAssessmentProgram(asseProgrmStr1, currentOrgId);
		        				} else {
		        					assessmentprg1 = validateAndGetAssessmentProgram(asseProgrmStr1, orgId);
		        				}
		        				if( assessmentprg1 == null ){
		        					enrollmentRecord.addInvalidField("assessmentProgram1",
		        	            			enrollmentRecord.getAssessmentProgram1(),
		        	                        true, " not found.");
		        					
		        					validationPassed = false;
		        				}
		        				else{
		        					if (assessmentProgramId.longValue() != assessmentprg1.getId().longValue()) {
		        						enrollmentRecord.addInvalidField("assessmentProgram1",
			        	            			enrollmentRecord.getAssessmentProgram1(),
			        	                        true, "doesn't match user's current Assessment Program.");
		        						
		        						validationPassed = false;
		        					} else {
		        						assessmentPrgIds.add(assessmentprg1.getId());
		        					}
		        				}
							}
		        			
		        				
		        			if( StringUtils.isNotEmpty(asseProgrmStr2) ){
		        				/*if( asseProgrmStr2.equalsIgnoreCase(asseProgrmStr1) ){
		        					enrollmentRecord.addInvalidField("assessmentProgram2",
		        	            			enrollmentRecord.getAssessmentProgram2(),
		        	                        true, " found multiple times. ");
		        					validationPassed = false;
		        				}
		        				else{
			        				AssessmentProgram assessmentprg2 = validateAndGetAssessmentProgram(asseProgrmStr2, orgId);
			        				if( assessmentprg2 == null ){
			        					enrollmentRecord.addInvalidField("assessmentProgram2",
			        	            			enrollmentRecord.getAssessmentProgram2(),
			        	                        true, " not found.");
			        					
			        					validationPassed = false;
			        				}
			        				else{
			        					assessmentPrgIds.add(assessmentprg2.getId());
			        				}
		        				}*/
		        				enrollmentRecord.addInvalidField("assessmentProgram2", asseProgrmStr2, false, " ignored.");
		        				validationPassed = false;
							}
		        			if( StringUtils.isNotEmpty(enrollmentRecord.getAssessmentProgram3() ) ){
		        				/*if( asseProgrmStr3.equalsIgnoreCase(asseProgrmStr1) ){
		        					enrollmentRecord.addInvalidField("assessmentProgram3",
		        	            			enrollmentRecord.getAssessmentProgram3(),
		        	                        true, " found multiple times. ");
		        					validationPassed = false;
		        				}
		        				else if( asseProgrmStr3.equalsIgnoreCase(asseProgrmStr2) ){
		        					enrollmentRecord.addInvalidField("assessmentProgram3",
		        	            			enrollmentRecord.getAssessmentProgram3(),
		        	                        true, " found multiple times.");
		        					validationPassed = false;
		        				}
		        				else{
			        				AssessmentProgram assessmentprg3 = validateAndGetAssessmentProgram(asseProgrmStr3, orgId);
			        				if( assessmentprg3 == null ){
			        					enrollmentRecord.addInvalidField("assessmentProgram3",
			        	            			enrollmentRecord.getAssessmentProgram3(),
			        	                        true, " not found.");
			        					
			        					validationPassed = false;
			        				}
		        				
			        				else{
			        					assessmentPrgIds.add(assessmentprg3.getId());
			        				}
		        				}*/
		        				enrollmentRecord.addInvalidField("assessmentProgram3", asseProgrmStr3, false, " ignored.");
		        				validationPassed = false;
							}
	        				
		        			if( validationPassed )
		        				enrollmentRecord.setAssessmentProgsIds(assessmentPrgIds);
		        				//@SuppressWarnings("unchecked")
								//List<AssessmentProgram> assessmentProgram = (List<AssessmentProgram>) params.get("assessmentPrograms");
		        				
				                /*if (enrollmentRecord.getDlmStatus()){
				                	enrollmentRecord.setAssessmentProgramId(assessmentProgram.getId());
				                }*/
	        			}
	        		}
        		}
        	}
	    }
        if( ! validationPassed ){
        	for( InValidDetail inValidDetail  : enrollmentRecord.getInValidDetails() ){
        		String errMsg = new StringBuilder(stateStudentIdent)
				    			.append("###")
				    			.append(inValidDetail.getInValidMessage())
				    			.toString();
        		
				String fieldName = inValidDetail.getActualFieldName();
				
				if("residenceDistrictIdentifier".equalsIgnoreCase(fieldName))
					errMsg = errMsg.replace("Residence", "Attendance");
				
				if(errMsg.contains("Ayp"))
					errMsg = errMsg.replace("Ayp", "Accountability");
				
				
				validationErrors.rejectValue(fieldName, "", new String[]{lineNumber, mappedFieldNames.get(fieldName)}, errMsg);
        	}
        }
        else
        {
        	enrollmentRecord.setCreatedUser(userDetails.getUserId());
        	enrollmentRecord.setModifiedUser(userDetails.getUserId());
        }
		
		//uploadedUser.setBatchUploadId(batchUploadId);
 		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", enrollmentRecord);
	//	logger.debug("Completed validation completed.");
		return customValidationResults;
	}
	
	public AssessmentProgram validateAndGetAssessmentProgram(String assessmentProgramAbbrCode, Long orgId){
		
		List<AssessmentProgram> assessmentProgs = assessmentProgramService.findByOrganizationId(orgId);
		
		for(AssessmentProgram assmentProg : assessmentProgs){
			
			if ( assmentProg.getAbbreviatedname().equalsIgnoreCase( assessmentProgramAbbrCode) ){
				return assmentProg; 
			}
		}
		return null;
		//return uploadedUser;
	}
  
	
	
	//added during US16966 - To add alert message to upload
	@Override
	public Map<String, Object> customValidationForAlert(
			BeanPropertyBindingResult validationErrors, Object rowData,
			Map<String, Object> params, Map<String, String> mappedFieldNames) {
		
		logger.debug(" enrollment upload custom validation For Alert Message");
		EnrollmentRecord enrollmentRecord = (EnrollmentRecord) rowData;
		Map<String, Object> customValidationResults = new HashMap<String, Object>();
		
		String lineNumber = enrollmentRecord.getLineNumber();		
		
		UserDetailImpl userDetails =  (UserDetailImpl)params.get("currentUser");
		
		boolean validationPassed = true;
		
        Long stateId = userDetails.getUser().getOrganization().getId();
		
        String stateStudentIdentifier = enrollmentRecord.getStateStudentIdentifier();
        
        String stateStudentIdent = enrollmentRecord.getStudent().getStateStudentIdentifier();
        
        List<EnrollmentsOrganizationInfo> enrollmentsOrganizations = studentService.getOrganizationsByStateStudentId(enrollmentRecord.getStateStudentIdentifier(),stateId,enrollmentRecord.getCurrentSchoolYear());
		
        ContractingOrganizationTree contractingOrganizationTree = (ContractingOrganizationTree)params.get("contractingOrganizationTree");
        
        Organization attendanceSchool = contractingOrganizationTree.getUserOrganizationTree()
    			.getOrganization(enrollmentRecord.getAttendanceSchoolProgramIdentifier());       
        
        List<Organization> parents = organizationService.getImmediateParents(attendanceSchool.getId());
        
        enrollmentRecord.setAttendanceSchoolId(attendanceSchool.getId());
        String enrollMessage ="";
        if(enrollmentsOrganizations.size()>0){
	        for (EnrollmentsOrganizationInfo enrollmentsOrganizationInfo : enrollmentsOrganizations) {
				if(enrollmentRecord.getAttendanceSchoolId() != enrollmentsOrganizationInfo.getAttendanceSchoolId()){
					validationPassed = false;					
						enrollMessage = enrollMessage +enrollmentsOrganizationInfo.getSchoolName() + " in " + enrollmentsOrganizationInfo.getDistrictName()+", ";	
						
				}					
			} 
	        if(validationPassed == false) {
	        	enrollMessage =enrollMessage + attendanceSchool.getOrganizationName() + " in "+ parents.get(0).getOrganizationName();	        
		    	enrollmentRecord.addInvalidField("stateStudentIdentifier", enrollmentRecord.getStateStudentIdentifier(), true, 
		                 "Student " +enrollmentRecord.getLegalFirstName()+", "+enrollmentRecord.getLegalLastName()+
		                 " is now enrolled in multiple schools for the current year : "+enrollMessage);
	        }
        }
	    Long addstateId = studentService.getStudentStateIdBySchoolId(enrollmentRecord.getAttendanceSchoolId());
	    int isEditstudent = studentService.getBySsidAndState(enrollmentRecord.getStateStudentIdentifier(),addstateId).size();
	    boolean demographic;
		if (isEditstudent == 0) {
			demographic = studentService.isAddStudentDemographicValueExists(enrollmentRecord.getLegalFirstName(),
					enrollmentRecord.getLegalLastName(), Long.valueOf(enrollmentRecord.getGender()),
					DateUtil.parse(enrollmentRecord.getDateOfBirthStr()), addstateId);
		} else {
			demographic = studentService.isEditStudentDemographicValueExists(
					enrollmentRecord.getStateStudentIdentifier(), enrollmentRecord.getLegalFirstName(),
					enrollmentRecord.getLegalLastName(), Long.valueOf(enrollmentRecord.getGender()),
					DateUtil.parse(enrollmentRecord.getDateOfBirthStr()), addstateId);

		}
		if (demographic) {
			validationPassed = false;
			enrollmentRecord.addInvalidField("stateStudentIdentifier", enrollmentRecord.getStateStudentIdentifier(),
					true, "State ID { " + enrollmentRecord.getStateStudentIdentifier()
							+ "}"+demographicAlertMessage);
		}
    
	    if(!validationPassed){	        
        	for( InValidDetail inValidDetail  : enrollmentRecord.getInValidDetails()){
        		String errMsg = new StringBuilder(stateStudentIdent)
    			.append("###")
    			.append(inValidDetail.getAlertMessage())
    			.toString();
				String fieldName = inValidDetail.getActualFieldName();
				if("residenceDistrictIdentifier".equalsIgnoreCase(fieldName))
					errMsg = errMsg.replace("Residence", "Attendance");
				
				validationErrors.rejectValue(fieldName, "", new String[]{lineNumber, mappedFieldNames.get(fieldName)}, errMsg);
        	}
	    }
		customValidationResults.put("errors", validationErrors.getAllErrors());
		customValidationResults.put("rowDataObject", enrollmentRecord);
		return customValidationResults;
	}
}
