package edu.ku.cete.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.EnrollmentTestTypeSubjectArea;
import edu.ku.cete.domain.EnrollmentTestTypeSubjectAreaExample;
import edu.ku.cete.domain.StudentAssessmentProgram;
import edu.ku.cete.domain.StudentTestResourceInfo;
import edu.ku.cete.domain.enrollment.SubjectArea;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.common.OrganizationTreeDetail;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.GradeCourseExample;
import edu.ku.cete.domain.enrollment.AutoRegisteredStudentsDTO;
import edu.ku.cete.domain.enrollment.DLMStudentSurveyRosterDetailsDto;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentExample;
import edu.ku.cete.domain.enrollment.EnrollmentExample.Criteria;
import edu.ku.cete.domain.enrollment.EnrollmentRecord;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.EnrollmentsRostersExample;
import edu.ku.cete.domain.enrollment.FindEnrollments;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.enrollment.TecRecord;
import edu.ku.cete.domain.enrollment.TestRecord;
import edu.ku.cete.domain.enrollment.WebServiceRosterRecord;
import edu.ku.cete.domain.security.Restriction;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentExample;
import edu.ku.cete.domain.student.StudentJson;
import edu.ku.cete.domain.student.StudentSchoolRelationDataObject;
import edu.ku.cete.domain.student.StudentSchoolRelationInformation;
import edu.ku.cete.domain.student.TransferStudentDTO;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.model.AssessmentProgramDao;
import edu.ku.cete.model.EnrollmentTestTypeSubjectAreaDao;
import edu.ku.cete.model.GradeCourseDao;
import edu.ku.cete.model.InValidDetail;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.StudentAssessmentProgramMapper;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.model.enrollment.EnrollmentsRostersDao;
import edu.ku.cete.model.enrollment.RosterDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.service.AppConfigurationService;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.ResourceRestrictionService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.PermissionUtil;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.RestrictedResourceConfiguration;
import edu.ku.cete.util.SourceTypeEnum;
import edu.ku.cete.util.json.EnrlAndRosterEventTrackerConverter;
import edu.ku.cete.web.StudentProfileItemAttributeDTO;

@Service
@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class EnrollmentServiceImpl implements EnrollmentService {

    /**
     * logger.
     */
    private Logger logger = LoggerFactory.getLogger(EnrollmentServiceImpl.class);

    /** EnrollmentDao holder. */
    @Autowired
    private EnrollmentDao enrollmentDao;
    
    @Autowired
	private StudentProfileService studentProfileService;

    
    /**
     * enrollmentTestTypeSubjectAreaDao
     */
    @Autowired
    private EnrollmentTestTypeSubjectAreaDao enrollmentTestTypeSubjectAreaDao;
    
    /** StudentDao holder. */
    @Autowired
    private StudentService studentService;
    /**
     * for school.
     */
    @Autowired
    private OrganizationService organizationService;
    /**
     *  assessmentService.
     */
    @Autowired
    private AssessmentService assessmentService;
    
    
    @Autowired
    private AssessmentProgramDao assessmentProgramDao;   

    @Autowired
    private GradeCourseService gradeCourseService;
    
    @Autowired
    private GradeCourseDao gradeCourseDao;   

    @Autowired
    private StudentDao studentDao;
    /**
     * contentAreaService.
     */
    @Autowired
    private ContentAreaService contentAreaService;

    /**
     * permissionUtil.
     */
    @Autowired
    private PermissionUtil permissionUtil;
    
    /**
     * studentsTestsService
     */
    @Autowired
    private StudentsTestsService studentsTestsService;
    
    /**
     * rosterService
     */
    @Autowired
    private RosterService rosterService;	 
	
	@Autowired
	private StudentAssessmentProgramMapper studentAssessmentProgramsDao;
	
	@Autowired
	private DomainAuditHistoryMapper domainAuditHistoryDao;
    
	@Autowired
	private RosterDao rosterDao;
	
	@Autowired
	private EnrollmentsRostersDao enrollmentsRostersDao;
	
    /**
     * kansasAutoRegistrationSwitch
     */
    @Value("${kansas.autoregistration.switch}")
    private String kansasAutoRegistrationSwitch;
    
    @Value("${user.organization.DLM}")
    private String dlmOrgName;
    
    /**
     * Added for enrollment upload using spring batch to find out restricted
     ****/
	@Autowired
	private RestrictedResourceConfiguration restrictedResourceConfiguration;
	
	@Autowired
	private ResourceRestrictionService resourceRestrictionService;
	
	@Autowired
	private TestSessionService testSessionService;
	
	@Autowired
    private OrganizationDao organizationDao;

	@Autowired
	private AppConfigurationService appConfigurationService;
   
    /**
     * @return the enrollmentDao
     */
    public final EnrollmentDao getEnrollmentDao() {
        return enrollmentDao;
    }   

    /**
     * @param newEnrollmentDao The enrollmentDao to set.
     */
    public final void setEnrollmentDao(final EnrollmentDao newEnrollmentDao) {
        this.enrollmentDao = newEnrollmentDao;
    }

    /**
     * TODO do not catch and re-throw DB exceptions.investigate and fix.
     * @param newEnrollment {@link Enrollment}
     * @return {@link Enrollment}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Enrollment add(Enrollment newEnrollment) {
    	newEnrollment.setAuditColumnProperties();
        enrollmentDao.add(newEnrollment);
        //addNewEnrollmentDomainAuditHistory(newEnrollment);
        return newEnrollment;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void addNewEnrollmentDomainAuditHistory(Enrollment newEnrollment){
		DomainAuditHistory domainAuditHistory = getDomainAduditHostory(newEnrollment);
		domainAuditHistory.setAction("NEW_ENROLLMENT");		
		domainAuditHistory.setObjectAfterValues(EnrlAndRosterEventTrackerConverter.getNewEnrollmentEvent(newEnrollment));
		domainAuditHistoryDao.insert(domainAuditHistory);
	}
    
    private DomainAuditHistory getDomainAduditHostory(Enrollment newEnrollment) {
    	DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
		if(StringUtils.isNotBlank(newEnrollment.getSourceType())) {
			domainAuditHistory.setSource(newEnrollment.getSourceType());
		} else {
			domainAuditHistory.setSource("ANONYMOUS");
		}		
		domainAuditHistory.setObjectType("ENROLLMENT");		
		domainAuditHistory.setCreatedDate(new Date());
		domainAuditHistory.setObjectId(newEnrollment.getId());
		if(newEnrollment.getCreatedUser() != null) {
			domainAuditHistory.setCreatedUserId(newEnrollment.getCreatedUser().intValue());
		} else if(newEnrollment.getModifiedUser() != null){
			domainAuditHistory.setCreatedUserId(newEnrollment.getModifiedUser().intValue());
		}
		return domainAuditHistory;
    }
    
    /**
     * @param enrollment {@link Enrollment}
     * @return {@link Enrollment}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Enrollment update(Enrollment enrollment) {
    	enrollment.setAuditColumnProperties();
        enrollmentDao.update(enrollment);
        if(enrollment.getExitWithdrawalDate() != null) {
        	//addExitStudentEventToDomainAuditHistory(enrollment);
        }
        return enrollment;
    }
    
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final Enrollment updateGradeLevel(Enrollment enrollment) {
		enrollment.setAuditColumnProperties();
		enrollmentDao.updateGradeLevel(enrollment);
		return enrollment;
	}

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private void addExitStudentEventToDomainAuditHistory(Enrollment enrollment) {
    	DomainAuditHistory domainAuditHistory = getDomainAduditHostory(enrollment);
		domainAuditHistory.setAction("EXIT_STUDENT");
		if(enrollment.getStudent().getStateId() == null || enrollment.getStudent().getStateStudentIdentifier() == null) {
			Student stu = studentDao.getByStudentID(enrollment.getStudentId());
			if(stu != null) {
				enrollment.getStudent().setStateId(stu.getStateId());
				enrollment.getStudent().setStateStudentIdentifier(stu.getStateStudentIdentifier());
			}
		}		
		domainAuditHistory.setObjectAfterValues(EnrlAndRosterEventTrackerConverter.getExitStudentEvent(enrollment));
		domainAuditHistoryDao.insert(domainAuditHistory);
	}

	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Enrollment addOrUpdate(Enrollment newEnrollment) {
        if (newEnrollment.getId() == 0) {
            if (newEnrollment.getStateStudentIdentifier() == null ||
            		StringUtils.isEmpty(newEnrollment.getStateStudentIdentifier())) {
                newEnrollment = add(newEnrollment);
                newEnrollment.setSaveStatus(RecordSaveStatus.ENROLLMENT_ADDED);
            } else {
                //state student id, ayp school id,attendance id,current school year
                EnrollmentExample enrollmentExample = new EnrollmentExample();
                Criteria cr = enrollmentExample.createCriteria();
                cr.andStateStudentIdentifierEqualTo(newEnrollment.getStateStudentIdentifier());
                //TODO should an enrollment be searched on
                //ayp school identifier given that it ayp school id always does not have to be present.
                //i think yes.note this can also be null.
//                    cr.andAypschoolidentifierEqualTo(newEnrollment.getAypSchoolIdentifier());
                cr.andAttendanceschoolprogramidentifierEqualTo(
                        newEnrollment.getAttendanceSchoolProgramIdentifier());
                cr.andCurrentschoolyearEqualTo(newEnrollment.getCurrentSchoolYear());
                List<Enrollment> enrollments = getByCriteria(enrollmentExample);
                if (enrollments != null && enrollments.size() >= 1 && enrollments.get(0) != null) {
                    newEnrollment.setId(enrollments.get(0).getId());
                    Long oldGradeId = enrollments.get(0).getCurrentGradeLevel();
                    newEnrollment = update(newEnrollment);
                    if(oldGradeId != null && newEnrollment.getCurrentGradeLevel() != null) {
                    	if(!oldGradeId.equals(newEnrollment.getCurrentGradeLevel())) {
                    		//addGradeChangeEventDomainAuditHistory(newEnrollment, oldGradeId);
                    	}
                    }
                    newEnrollment.setSaveStatus(RecordSaveStatus.ENROLLMENT_UPDATED);
                } else {
                    newEnrollment = add(newEnrollment);
                    newEnrollment.setSaveStatus(RecordSaveStatus.ENROLLMENT_ADDED);
                }
            }
        } else {
        	Long previousGrade = newEnrollment.getCurrentGradeLevel();
            newEnrollment = update(newEnrollment);
            if(previousGrade != null && newEnrollment.getCurrentGradeLevel() != null) {
            	if(!previousGrade.equals(newEnrollment.getCurrentGradeLevel())) {
            		//addGradeChangeEventDomainAuditHistory(newEnrollment, previousGrade);
            	}
            }
            newEnrollment.setSaveStatus(RecordSaveStatus.ENROLLMENT_UPDATED);
        }
        return newEnrollment;
    }
    
	@Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void addGradeChangeEventDomainAuditHistory(Enrollment newEnrollment, Long oldGradeId) {
    	DomainAuditHistory domainAuditHistory = getDomainAduditHostory(newEnrollment);
		domainAuditHistory.setAction("GRADE_CHANGE");		
		domainAuditHistory.setObjectAfterValues(EnrlAndRosterEventTrackerConverter.getGradeLevelChangeEvent(newEnrollment, oldGradeId));
		domainAuditHistoryDao.insert(domainAuditHistory);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private final Enrollment addOrUpdateTestOrExit(Enrollment newEnrollment, boolean isTest) {
        if (newEnrollment.getId() == 0) {
            if (newEnrollment.getStateStudentIdentifier() == null ||
            		StringUtils.isEmpty(newEnrollment.getStateStudentIdentifier())) {
            	if(!isTest) {
            		newEnrollment.setActiveFlag(false);
            	}
                newEnrollment = add(newEnrollment);
                newEnrollment.setSaveStatus(RecordSaveStatus.ENROLLMENT_ADDED);
            } else {
                //state student id, ayp school id,attendance id,current school year
                EnrollmentExample enrollmentExample = new EnrollmentExample();
                Criteria cr = enrollmentExample.createCriteria();
                cr.andAypSchoolIdEqualTo(newEnrollment.getAypSchoolId());
                cr.andStateStudentIdentifierEqualTo(newEnrollment.getStateStudentIdentifier());
                cr.andAttendanceschoolidEqualTo(
                        newEnrollment.getAttendanceSchoolId());
                cr.andCurrentschoolyearEqualTo(newEnrollment.getCurrentSchoolYear());
                List<Enrollment> enrollments = getByCriteria(enrollmentExample);
                if (enrollments != null && enrollments.size() >= 1 && enrollments.get(0) != null) {
                    newEnrollment.setId(enrollments.get(0).getId());
                    if(!isTest) {
                    	setExitDetailsInNewEnrollment(newEnrollment, enrollments.get(0));
                    } else {
                    	setTestDetailsInNewEnrollment(newEnrollment, enrollments.get(0));
                    }
                    Long previousGrade = enrollments.get(0).getCurrentGradeLevel();                    
                    newEnrollment = update(newEnrollment);
                    if(previousGrade != null && newEnrollment.getCurrentGradeLevel() != null) {
                    	if(!previousGrade.equals(newEnrollment.getCurrentGradeLevel())) {
                    		//addGradeChangeEventDomainAuditHistory(newEnrollment, previousGrade);
                    	}
                    }
                    newEnrollment.setSaveStatus(RecordSaveStatus.ENROLLMENT_UPDATED);
                } else {
                	if(!isTest) {
                		newEnrollment.setActiveFlag(false);
                	}
                    newEnrollment = add(newEnrollment);
                    newEnrollment.setSaveStatus(RecordSaveStatus.ENROLLMENT_ADDED);
                }
            }
        } else {
            Enrollment enrollment = getById(newEnrollment.getId());

        	if(!isTest) {
        		setExitDetailsInNewEnrollment(newEnrollment, enrollment);
            } else {
            	setTestDetailsInNewEnrollment(newEnrollment, enrollment);
            }
        }
        newEnrollment = update(newEnrollment);
        newEnrollment.setSaveStatus(RecordSaveStatus.ENROLLMENT_UPDATED);
        
        return newEnrollment;
    }

	private void setTestDetailsInNewEnrollment(Enrollment newEnrollment, Enrollment enrollment) {
		setSchDistStateEntryDates(newEnrollment, enrollment);		
    	newEnrollment.setActiveFlag(enrollment.getActiveFlag());
    	newEnrollment.setExitWithdrawalDate(enrollment.getExitWithdrawalDate());
    	newEnrollment.setExitWithdrawalType(enrollment.getExitWithdrawalType());
	}

	private void setSchDistStateEntryDates(Enrollment newEnrollment, Enrollment enrollment) {
		if(newEnrollment.getSchoolEntryDate() == null) {
			newEnrollment.setSchoolEntryDate(enrollment.getSchoolEntryDate());
		}
		if(newEnrollment.getDistrictEntryDate() == null) {
			newEnrollment.setDistrictEntryDate(enrollment.getDistrictEntryDate());
		}
		if(newEnrollment.getStateEntryDate() == null) {
			newEnrollment.setStateEntryDate(enrollment.getStateEntryDate());			
		}
	}

	private void setExitDetailsInNewEnrollment(Enrollment newEnrollment, Enrollment enrollment) {
		newEnrollment.setExitWithdrawalDate(enrollment.getExitWithdrawalDate());
		newEnrollment.setExitWithdrawalType(enrollment.getExitWithdrawalType());
		if (newEnrollment.getExitWithdrawalDate() != null 
				&& newEnrollment.getExitWithdrawalDate().getTime() >= newEnrollment.getSchoolEntryDate().getTime()){
			newEnrollment.setActiveFlag(false);
		}
		setSchDistStateEntryDates(newEnrollment, enrollment);
	}
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Enrollment> addIfNotPresentSTCO(Enrollment newEnrollment) {
        List<Enrollment> enrollments = new ArrayList<Enrollment>();
        // state student id, ayp school id,attendance id,current school year
        EnrollmentExample enrollmentExample = new EnrollmentExample();
        Criteria cr = enrollmentExample.createCriteria();
        cr.andStudentidEqualTo(newEnrollment
                .getStudentId());
        // TODO should an enrollment be searched on
        // ayp school identifier given that it ayp school id always does not
        // have to be present.
        // i think yes.note this can also be null.
        if (StringUtils.isNotEmpty(newEnrollment.getAypSchoolIdentifier())) {
        	//Enrollment is unique by attendance school, current school year and student.
        	logger.debug("Ayp school identifier is ignored in search"
        	+ newEnrollment.getAypSchoolIdentifier());
        }
        cr.andAttendanceidEqualTo(newEnrollment
                .getAttendanceSchoolId());
       	cr.andAypSchoolIdEqualTo(newEnrollment.getAypSchoolId());
        cr.andCurrentschoolyearEqualTo(newEnrollment.getCurrentSchoolYear());
        enrollments = getByCriteria(enrollmentExample);
        if (enrollments == null || enrollments.size() < 1) {
            newEnrollment = add(newEnrollment);
            newEnrollment.setSaveStatus(RecordSaveStatus.ENROLLMENT_ADDED);
            enrollments.add(newEnrollment);
        } 
        return enrollments;
    }
    
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Enrollment> addIfNotPresentForRosterUpload(Enrollment newEnrollment) {
        List<Enrollment> enrollments = new ArrayList<Enrollment>();
        // state student id, ayp school id,attendance id,current school year
        EnrollmentExample enrollmentExample = new EnrollmentExample();
        Criteria cr = enrollmentExample.createCriteria();
        cr.andStudentidEqualTo(newEnrollment
                .getStudentId());
        
        if (StringUtils.isNotEmpty(newEnrollment.getAypSchoolIdentifier())) {
        	//Enrollment is unique by attendance school, current school year and student.
        	logger.debug("Ayp school is ignored in search during roster upload " + newEnrollment.getAypSchoolIdentifier());
        }
        cr.andAttendanceidEqualTo(newEnrollment
                .getAttendanceSchoolId());
        cr.andCurrentschoolyearEqualTo(newEnrollment.getCurrentSchoolYear());
        enrollments = getByCriteria(enrollmentExample);
        if (enrollments == null || enrollments.size() < 1) {
            newEnrollment = add(newEnrollment);
            newEnrollment.setSaveStatus(RecordSaveStatus.ENROLLMENT_ADDED);
            enrollments.add(newEnrollment);
        } 
        return enrollments;
    }


    /**
     * @param kidRecord {@link KidRecord}
     * @param user {@link User}
     * @return {@link Enrollment}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final KidRecord cascadeAddOrUpdate(KidRecord kidRecord,
    		ContractingOrganizationTree contractingOrganizationTree, User user, boolean isTecRecord) {
        //ayp school id is a must for enrollment.
        RecordSaveStatus enrollmentRecordSaveStatus = RecordSaveStatus.KID_RECORD_SAVE_BEGAN;
        
    	//validating if attendance school is found.Needed if saving enrollment.
    	//find the organization only from at or below the user's organization.
    	Organization attendanceSchool = contractingOrganizationTree.getUserOrganizationTree()
    			.getOrganization(kidRecord.getAttendanceSchoolProgramIdentifier());
    	//easier to add the multiple organization context here.
        if (attendanceSchool == null) {
        	logger.debug("The record " + kidRecord
        			+ " is rejected because the organization is unrecognized");
        	kidRecord.addInvalidField(
        			ParsingConstants.BLANK + FieldName.ATTENDANCE_SCHOOL,
        			kidRecord.getAttendanceSchoolProgramIdentifier(),
        			true, InvalidTypes.NOT_FOUND);
        	return kidRecord;
        }        
        kidRecord.setAttendanceSchoolId(attendanceSchool.getId());        
        //int contractingOrgSchoolYear = enrollmentDao.getContractingOrgSchoolYear(attendanceSchool.getId());
        int contractingOrgSchoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
        int enrollmentCurrentSchoolYear = kidRecord.getCurrentSchoolYear();
        if(contractingOrgSchoolYear != enrollmentCurrentSchoolYear){
        	kidRecord.addInvalidField(FieldName.CURRENT_SCHOOL_YEAR	+ ParsingConstants.BLANK,
					kidRecord.getCurrentSchoolYear() + ParsingConstants.BLANK, 
					true,		
					InvalidTypes.IN_CORRECT,
					" does not match the contracting organization school year.");
            return kidRecord;
        }
                		
		if(!kidRecord.getStateStudentIdentifier().trim().isEmpty() ){

			String stateStudentIdentifierLength =studentService.getStateStudentIdentifierLengthByStateID();
			int allowedLength = Integer.parseInt(stateStudentIdentifierLength);
			if(kidRecord.getStateStudentIdentifier().trim().length()>allowedLength){
    			String stateStudentIdentifierLengthError = appConfigurationService.getByAttributeCode(CommonConstants.STATE_STUDENT_IDENTIFIER_LENGTH_ERROR);
    			String errorMessage = stateStudentIdentifierLengthError.concat(Integer.toString(allowedLength)).concat(".");
				kidRecord.addInvalidField("stateStudentIdentifier", kidRecord.getStateStudentIdentifier(), true, errorMessage);
				return kidRecord;
			}
		}
        
        if (!isTecRecord && !kidRecord.getRecordType().equalsIgnoreCase("EXIT")
        		&& !isValidTestRecord( kidRecord)) {
        	kidRecord.addInvalidField(FieldName.TEST_TYPE+ ParsingConstants.BLANK,
					kidRecord.getCurrentSchoolYear() + ParsingConstants.BLANK, 
					true,		
					InvalidTypes.IN_CORRECT,
					" Atleast one valid test type code is required.");
        	return kidRecord;
        }
        
        // set contracting organization on student
        Organization studentState = organizationService.getContractingOrganization(attendanceSchool.getId());
        kidRecord.getStudent().setStateId(studentState.getId());
        
        //set the currentContextUserId to be used in transaction
        kidRecord.getStudent().setCurrentContextUserId(user.getId());
        
        //set source type on student
        kidRecord.getStudent().setSourceType(SourceTypeEnum.TESTWEBSERVICE.getCode());
        
        //check for state id
        Student student = kidRecord.getStudent();
        
        //decide assessment program
        decideAssessmentProgram(kidRecord, studentState, student);
        
        Organization aypSchool = contractingOrganizationTree.getUserOrganizationTree()
    			.getOrganization(kidRecord.getAypSchoolIdentifier());
        if (!isTecRecord){
        	if(aypSchool == null) {
        		logger.debug("The record " + kidRecord
            			+ " is rejected because the AYP School organization is unrecognized");
            	kidRecord.addInvalidField(
            			ParsingConstants.BLANK + FieldName.AYP_SCHOOL,
            			kidRecord.getAypSchoolIdentifier(),
            			true, InvalidTypes.NOT_FOUND);
            	return kidRecord;
        	}        	
        	String mappedCompRace = null;
        	if(StringUtils.isNotEmpty(student.getComprehensiveRace())){
        		mappedCompRace = studentDao.findMappedComprehensiveRaceCode(student.getComprehensiveRace());
        	}
        	kidRecord.getStudent().setComprehensiveRace(mappedCompRace);
        	
        	if(kidRecord.getRecordType().equalsIgnoreCase("TEST")) {
        		Long[] apIds = student.getStudentAssessmentPrograms();
        		student = studentService.addOrUpdate(kidRecord.getStudent(), kidRecord.getAttendanceSchoolId());
        		
        		if(apIds != null) { //associate student to ap
        			List<StudentAssessmentProgram> allAps = studentAssessmentProgramsDao.getByStudentId(student.getId());
        			boolean exists = false;
        			for(Long apId: apIds) {
        				exists = false;

            			for(StudentAssessmentProgram sap : allAps) {
	        				if(sap.getAssessmentProgramId().equals(apId)) {
	        					//If already assessment program is present with activeflag as false Then update that one.
	        					if(!sap.getActiveFlag()){
	        						sap.setActiveFlag(true);
	        						studentAssessmentProgramsDao.updateByPrimaryKey(sap);
	        					}
	        					exists = true;
	        					break;
	        				}
        				}
        				if(!exists) {
        					StudentAssessmentProgram stuAssessmentProgram = new StudentAssessmentProgram();
        					stuAssessmentProgram.setActiveFlag(true);
        					stuAssessmentProgram.setAssessmentProgramId(apId);
        					stuAssessmentProgram.setStudentId(student.getId());					
        					studentAssessmentProgramsDao.insert(stuAssessmentProgram);
        				}
        			}
        		}
        	} else {
        		student = studentService.validateIfStudentExists(kidRecord.getStudent());
        	}
        }
        
        if(student.isDoReject()) {
        	kidRecord.getInValidDetails().addAll(student.getInValidDetails());
        	kidRecord.setDoReject(true);
        	return kidRecord;
        }
        if (student.getSaveStatus().equals(RecordSaveStatus.STUDENT_ADDED)) {
            kidRecord.setCreated(true);
        }
	
        GradeCourse gradeCourse = null;
        GradeCourse grade = null;
        if(kidRecord.getCurrentGradeLevel() != null) {
	        gradeCourse = new GradeCourse();
	        String strGradeLevel = "0" + kidRecord.getCurrentGradeLevel();
	        //add a zero to the beginning and then if there are more than 2 digits remove the first digit
	        strGradeLevel = strGradeLevel.length() > 2 ? strGradeLevel.substring(1) : strGradeLevel;
	        //store in abbr name to send to query
	        gradeCourse.setAbbreviatedName(strGradeLevel);
	        gradeCourse.setCurrentContextUserId(user.getId());
	        grade = gradeCourseService.getWebServiceGradeCourseByCode(gradeCourse);
        }
        
        if(grade!=null) {
            kidRecord.getEnrollment().setCurrentGradeLevel(grade.getId());
        }
	
        //set the assessment school relations.
        kidRecord.getStudent().setId(student.getId());
        kidRecord.setStudentId(kidRecord.getStudent().getId());

        kidRecord.getEnrollment().setStudent(kidRecord.getStudent());
        kidRecord.getEnrollment().setStudentId(kidRecord.getStudent().getId());
        kidRecord.getEnrollment().setAttendanceSchoolProgramIdentifier(
                kidRecord.getAttendanceSchoolProgramIdentifier());
        kidRecord.getEnrollment().setAttendanceSchoolId(
                attendanceSchool.getId());
        kidRecord.getEnrollment().setCurrentContextUserId(user.getId());

        if(isTecRecord) {
        	kidRecord.getEnrollment().setAypSchoolId(attendanceSchool.getId());
        } else {
        	kidRecord.getEnrollment().setAypSchoolId(aypSchool.getId());
        }
        kidRecord.getEnrollment().setAuditColumnProperties();
        
        Enrollment enrollment = kidRecord.getEnrollment();
        if (!isTecRecord){
        	enrollment = addOrUpdateTestOrExit(kidRecord.getEnrollment(), kidRecord.getRecordType().equalsIgnoreCase("TEST"));
        }
        kidRecord.setEnrollment(enrollment);
        if (enrollmentRecordSaveStatus.equals(RecordSaveStatus.ENROLLMENT_ADDED)) {
            kidRecord.setCreated(true);
        }
        
        if(kidRecord.getRecordType().equalsIgnoreCase("TEST") || kidRecord.getRecordType().equalsIgnoreCase("CLEAR")) {
        	List<TestType> testTypes = enrollmentTestTypeSubjectAreaDao.selectAllTestTypes();
        	List<SubjectArea> subjectAreas = enrollmentTestTypeSubjectAreaDao.selectAllSubjectAreas();
        	Map<String, TestType> testTypesMap = new HashMap<String, TestType>();
        	Map<String, SubjectArea> subjectAreaMap = new HashMap<String, SubjectArea>();
        	
        	
	        	for(TestType testType : testTypes) {
	        		if(testType.getTestTypeCode().equals("C")) {
	        			testTypesMap.put(testType.getTestTypeCode(), testType);
	        			testTypesMap.get("C").getId();
	        			break;
	        		}
	        	}
	        	
	        	for(SubjectArea subjectArea : subjectAreas) {
	        		subjectAreaMap.put(subjectArea.getSubjectAreaCode(), subjectArea);
	        		testTypes = enrollmentTestTypeSubjectAreaDao.selectTestTypesBySubjectCode(subjectArea.getSubjectAreaCode());
	        		for(TestType testType : testTypes) {
	            		testTypesMap.put(subjectArea.getSubjectAreaCode()+"-"+testType.getTestTypeCode(), testType);
	            	}
	        	}
   
	        	// process clear records
        	if (enrollment.getExitWithdrawalDate() == null ||
        			enrollment.getExitWithdrawalDate().getTime() < enrollment.getSchoolEntryDate().getTime() ){
        		if(isTecRecord && kidRecord.getRecordType().equalsIgnoreCase("CLEAR")){
        			tecRecordClearSubjectTestType(kidRecord, enrollment, user, testTypesMap, subjectAreaMap, isTecRecord);
        		}else{
        			processClearTestTypes(kidRecord, enrollment, user, testTypesMap, subjectAreaMap, isTecRecord);
        		}
        	}
        	
        	if(isTecRecord) {
        		addTECEnrollmentTestTypes(kidRecord, enrollment, user, testTypesMap, subjectAreaMap);
        	} else { //FOR KS
        		addEnrollmentTestTypes(kidRecord, enrollment, user, testTypesMap, subjectAreaMap);
        	}

//	        if(kidRecord.getDlmELAProctorId() != null && kidRecord.getStateELAAssessment() != null 
//					&& !"0".equals(kidRecord.getStateELAAssessment())){
//	        	addOrUpdateRoster(contractingOrganizationTree, attendanceSchool,
//						student, enrollment, kidRecord.getDlmELAProctorId(), kidRecord.getDlmELAProctorName(), "ELA", "DLM");
//	        }
//	        
//	        if(kidRecord.getDlmMathProctorId() != null && kidRecord.getStateMathAssess() != null 
//					&& !"0".equals(kidRecord.getStateMathAssess())){
//	        	addOrUpdateRoster(contractingOrganizationTree, attendanceSchool,
//						student, enrollment, kidRecord.getDlmMathProctorId(), kidRecord.getDlmMathProctorName(), "M", "DLM");
//	        }
//	        
//	        if(kidRecord.getDlmSciProctorId() != null && kidRecord.getStateSciAssessment() != null 
//					&& !"0".equals(kidRecord.getStateSciAssessment())){
//	        	addOrUpdateRoster(contractingOrganizationTree, attendanceSchool,
//						student, enrollment, kidRecord.getDlmSciProctorId(), kidRecord.getDlmSciProctorName(), "Sci", "DLM");
//	        }
        }  
        else if (kidRecord.getRecordType().equalsIgnoreCase("EXIT")){
        	logger.debug("Processing an EXIT record for student: "+kidRecord.getStudent().getStateStudentIdentifier()+" and school: "+kidRecord.getAttendanceSchoolProgramIdentifier()
    			+" with type: "+kidRecord.getRecordType());
    	
        	//US13725 process undoing an exit record
        	if (Long.valueOf(kidRecord.getExitWithdrawalType()).equals(99L)){
        		List<Enrollment> inactiveEnrollments = enrollmentDao.getInactiveEnrollment(kidRecord.getStateStudentIdentifier(), studentState.getId(), enrollmentCurrentSchoolYear);
	        	for (Enrollment e : inactiveEnrollments){
	        		if(isTecRecord) {
	        			if (e.getAttendanceSchoolProgramIdentifier().equals(kidRecord.getAttendanceSchoolProgramIdentifier())
	        					&& e.getCurrentSchoolYear() == kidRecord.getCurrentSchoolYear() 
	        					&& e.getExitWithdrawalDate() != null 
	        					&& e.getExitWithdrawalDate().getTime() == kidRecord.getExitWithdrawalDate().getTime()){
	        				//should we remove the info for the exit date and code previously stored?
	        				undoExitRecords(user, e);
			        		break;
	        			}
	        		} else {
	        			if (e.getAypSchoolId() == kidRecord.getEnrollment().getAypSchoolId()
	        					&& e.getCurrentSchoolYear() == kidRecord.getCurrentSchoolYear() 
	        					&& e.getExitWithdrawalDate() != null 
	        					&& e.getExitWithdrawalDate().getTime() == kidRecord.getExitWithdrawalDate().getTime()){
	        				undoExitRecords(user, e);
	        			}
	        		}
	        	}
        	}else{
	        	List<Enrollment> enrollments = enrollmentDao.getBySsidAndState(kidRecord.getStateStudentIdentifier(), studentState.getId(), enrollmentCurrentSchoolYear);
	        	for (Enrollment e : enrollments){
	        		if(isTecRecord) {
	        			if (e.getAttendanceSchoolProgramIdentifier().equals(kidRecord.getAttendanceSchoolProgramIdentifier())
	        					&& e.getCurrentSchoolYear() == kidRecord.getCurrentSchoolYear()){
	        				processExitRecord(kidRecord, user, e);
	        			}
	        		} else {
	        			if (e.getAypSchoolId() == kidRecord.getEnrollment().getAypSchoolId()
	        					&& e.getCurrentSchoolYear() == kidRecord.getCurrentSchoolYear()){	        				
        					processExitRecord(kidRecord, user, e);
	        			}
	        		}
	        	}
	        }
        }
       	return kidRecord;
    }
    
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void tecRecordClearSubjectTestType(KidRecord kidRecord,Enrollment enrollment, User user,Map<String, TestType> testTypesMap,	Map<String, SubjectArea> subjectAreaMap, boolean isTecRecord) {
		
    	Long clearTestTypeId=testTypesMap.get("C").getId();
    	if(kidRecord.getStateMathAssess() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateMathAssess()) &&
    			"C".equals(kidRecord.getStateMathAssess())) {
    		studentsTestsService.removeTestSessionsOnClear(enrollment, kidRecord.getTestType(), (subjectAreaMap.get("D74")).getSubjectAreaCode());
    		addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("D74-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("D74")).getId(),
    				kidRecord.getGroupingInd1Math(), kidRecord.getGroupingInd2Math(), clearTestTypeId);
     	} 
       	
       	if(kidRecord.getStateELAAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateELAAssessment()) &&
    			"C".equals(kidRecord.getStateELAAssessment())) {
    
       		studentsTestsService.removeTestSessionsOnClear(enrollment, kidRecord.getTestType(), (subjectAreaMap.get("SELAA")).getSubjectAreaCode());
    		addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SELAA-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("SELAA")).getId(),
    				kidRecord.getGroupingInd1ELA(), kidRecord.getGroupingInd2ELA(), clearTestTypeId);
        	
    	}
       	
       	if(kidRecord.getStateSciAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateSciAssessment()) &&
    			"C".equals(kidRecord.getStateSciAssessment())) {
    		studentsTestsService.removeTestSessionsOnClear(enrollment, 
    					kidRecord.getTestType(), (subjectAreaMap.get("SSCIA")).getSubjectAreaCode());
    		
    		addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SSCIA-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("SSCIA")).getId(),
    				kidRecord.getGroupingInd1Sci(), kidRecord.getGroupingInd2Sci(), clearTestTypeId);
        	
    	}   
       	
       	if(kidRecord.getStateHistGovAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment()) &&
    			"C".equals(kidRecord.getStateHistGovAssessment())) {
    		studentsTestsService.removeTestSessionsOnClear(enrollment, 
    				kidRecord.getTestType(), (subjectAreaMap.get("SHISGOVA")).getSubjectAreaCode());
    	   	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SHISGOVA-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("SHISGOVA")).getId(),
        				kidRecord.getGroupingInd1HistGov(), kidRecord.getGroupingInd2HistGov(), clearTestTypeId);
    	}
    	
    	if(kidRecord.getGeneralCTEAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getGeneralCTEAssessment()) && "C".equals(kidRecord.getGeneralCTEAssessment())) {
    		studentsTestsService.removeTestSessionsOnClear(enrollment, kidRecord.getTestType(), (subjectAreaMap.get("GKS")).getSubjectAreaCode());
				
	    		addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("GKS-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("GKS")).getId(),
	    				kidRecord.getGroupingInd1CTE(), kidRecord.getGroupingInd2CTE(), clearTestTypeId);
	       
    	}  
        
       	if(kidRecord.getComprehensiveAgAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getComprehensiveAgAssessment()) && "C".equals(kidRecord.getComprehensiveAgAssessment())) {
       			studentsTestsService.removeTestSessionsOnClear(enrollment, kidRecord.getTestType(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	    				kidRecord.getGroupingComprehensiveAg(), null, clearTestTypeId);
	        	
    	} 
       	if(kidRecord.getAnimalSystemsAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getAnimalSystemsAssessment()) && "C".equals(kidRecord.getAnimalSystemsAssessment())) {
       			studentsTestsService.removeTestSessionsOnClear(enrollment, kidRecord.getTestType(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	    				kidRecord.getGroupingAnimalSystems(), null, clearTestTypeId);
	       
    	} 
       	if(kidRecord.getPlantSystemsAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getPlantSystemsAssessment()) && "C".equals(kidRecord.getPlantSystemsAssessment()) ) {
 				studentsTestsService.removeTestSessionsOnClear(enrollment, kidRecord.getTestType(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	    				kidRecord.getGroupingPlantSystems(), null, clearTestTypeId);
    	} 
       	if(kidRecord.getManufacturingProdAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getManufacturingProdAssessment()) && "C".equals(kidRecord.getManufacturingProdAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, kidRecord.getTestType(), (subjectAreaMap.get("Mfg")).getSubjectAreaCode());
				
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("Mfg-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("Mfg")).getId(),
	        				kidRecord.getGroupingManufacturingProd(), null, clearTestTypeId);
	        
    	} 
       	if(kidRecord.getDesignPreConstructionAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getDesignPreConstructionAssessment()) && "C".equals(kidRecord.getDesignPreConstructionAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, kidRecord.getTestType(), (subjectAreaMap.get("Arch&Const")).getSubjectAreaCode());
				
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("Arch&Const-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("Arch&Const")).getId(),
        				kidRecord.getGroupingDesignPreConstruction(), null, clearTestTypeId);
    	} 
       	
       	if(kidRecord.getComprehensiveBusinessAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getComprehensiveBusinessAssessment()) && "C".equals(kidRecord.getComprehensiveBusinessAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, kidRecord.getTestType(), (subjectAreaMap.get("BM&A")).getSubjectAreaCode());
				
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingComprehensiveBusiness(), null, clearTestTypeId);
	       
    	}

       	if(kidRecord.getFinanceAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getFinanceAssessment()) &&  "C".equals(kidRecord.getFinanceAssessment()) ) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, kidRecord.getTestType(), (subjectAreaMap.get("BM&A")).getSubjectAreaCode());
				
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingFinance(), null, clearTestTypeId);
	    	} 
       	if(kidRecord.getElpa21Assessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getElpa21Assessment()) &&
    			Arrays.binarySearch(new String[]{"C"}, kidRecord.getElpa21Assessment()) >= 0) {
       		
    			studentsTestsService.removeTestSessionsOnClear(enrollment, kidRecord.getTestType(), (subjectAreaMap.get("ELPA21")).getSubjectAreaCode());
    	       	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("ELPA21-"+kidRecord.getTestType()).getId(), (subjectAreaMap.get("ELPA21")).getId(),
        			kidRecord.getGroupingInd1Elpa21(), kidRecord.getGroupingInd2Elpa21(), clearTestTypeId);
    	} 
	}

	private void decideAssessmentProgram(KidRecord kidRecord, Organization studentState, Student student) {
		if(studentState.getDisplayIdentifier().equalsIgnoreCase("KS")) {
			List<Long> studentAssessmentPrograms = new ArrayList<Long>();
	        if (isDLMKid(kidRecord)){
	        	AssessmentProgram ap = assessmentProgramDao.findByAbbreviatedName("DLM");
	        	studentAssessmentPrograms.add(ap.getId());
	        }
	        
	        if (isCpassKid(kidRecord)) {
	        	AssessmentProgram ap = assessmentProgramDao.findByAbbreviatedName("CPASS");
	        	studentAssessmentPrograms.add(ap.getId());
	        }
	        
	        //ELPA
	        if(isELPAKid(kidRecord)) {
	        	AssessmentProgram ap = assessmentProgramDao.findByAbbreviatedName("KELPA2");
	        	if(ap != null)
	        		studentAssessmentPrograms.add(ap.getId());
	        }
	        
	        //check KAP
	        if(isKAPKid(kidRecord)) {
	        	AssessmentProgram ap = assessmentProgramDao.findByAbbreviatedName("KAP");
	        	studentAssessmentPrograms.add(ap.getId());
	        }
	        //sreevani - Add logic to validate assessment program combinations??
	        if(!studentAssessmentPrograms.isEmpty()) {
	        	student.setStudentAssessmentPrograms(studentAssessmentPrograms.toArray(new Long[studentAssessmentPrograms.size()]));
	        }
		}
	}

	/**
	 * @param kidRecord
	 * @param user
	 * @param e
	 */
	public void processExitRecord(KidRecord kidRecord, User user, Enrollment e) {
		if((e.getSourceType() != null && e.getSourceType().equals("TASC")) | e.getSchoolEntryDate() != null) {
			logger.debug("kidRecord.getExitWithdrawalDate() : "+kidRecord.getExitWithdrawalDate().getTime());
			logger.debug("e.getSchoolEntryDate(): "+(e.getSchoolEntryDate() != null ? e.getSchoolEntryDate().getTime() : "null"));
			logger.debug("e.getStudentId(): "+e.getStudentId());
			if(StringUtils.isNotEmpty(e.getAypSchoolIdentifier())) {
				logger.debug("e.getAypSchoolIdentifier()" + e.getAypSchoolIdentifier());
			}
			if((e.getSourceType() != null && e.getSourceType().equals("TASC")) || kidRecord.getExitWithdrawalDate().getTime() >= e.getSchoolEntryDate().getTime()) {
				
				StudentJson student = studentDao.getStudentjsonData(e.getStudentId());
				if(student != null){
				student.setExitReason(kidRecord.getExitWithdrawalType());
				student.setExitDate(kidRecord.getExitWithdrawalDate());
				student.setExitedSchool(String.valueOf(e.getAttendanceSchoolId()));
				studentService.insertIntoDomainAuditHistory(e.getStudentId(), user.getId(),EventTypeEnum.EXIT.getCode(),
						SourceTypeEnum.STUDENT_EXIT_MANUAL.getCode().equals(kidRecord.getEnrollment().getSourceType())?SourceTypeEnum.MANUAL.getCode():SourceTypeEnum.UPLOAD.getCode()
							, null, student.buildjsonString());
				}
				e.setActiveFlag(false);
				e.setExitWithdrawalDate(kidRecord.getExitWithdrawalDate());
				e.setExitWithdrawalType(Integer.valueOf(kidRecord.getExitWithdrawalType()));
				e.setModifiedDate(new Date());
				e.setModifiedUser(user.getId());
				e.setAttendanceSchoolId(e.getAttendanceSchoolId());
				enrollmentDao.update(e);
				
				e.setSourceType(kidRecord.getEnrollment().getSourceType());								
				studentsTestsService.unEnrollStudent(e);
				
				removeRostersForEnrollment(e);
			}
		} else {
			logger.warn("EnrollmentServiceImpl.processExitRecord: No school entry date was provided on the enrollment record.  Cannot compare with EXIT date. enrollmentid: "+e.getId()+" studentid: "+e.getStudentId());
		}
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void removeRostersForEnrollment(Enrollment enrollment) {
		List<Long> rosterIds = rosterDao.getRosterIdsByEnrollmentId(enrollment.getId());
		
		EnrollmentsRosters er = new EnrollmentsRosters();
		er.setEnrollmentId(enrollment.getId());
		er.setActiveFlag(false);
		er.setModifiedDate(enrollment.getModifiedDate());
		er.setModifiedUser(enrollment.getModifiedUser());
		for (Long rosterId : rosterIds) {
			EnrollmentsRostersExample erExample = new EnrollmentsRostersExample();
			EnrollmentsRostersExample.Criteria erCriteria = erExample.createCriteria();
			erCriteria.andEnrollmentIdEqualTo(enrollment.getId());
			erCriteria.andRosterIdEqualTo(rosterId);
			
			er.setRosterId(rosterId);
			enrollmentsRostersDao.updateByExampleSelective(er, erExample);
			
			if (rosterDao.getNumberOfActiveStudents(rosterId) == 0) {
				rosterService.removeRoster(rosterId, enrollment.getModifiedUser(), enrollment.getSourceType());
			}
		}
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void transferStudents(List<TransferStudentDTO> students, User user,  Long accountabilityDistrictId, String accountabilityDistrictIdentifier) {
		logger.debug("Transfer Student initiated");
		int currentSchoolYear = (int) (long) user.getContractingOrganization().getCurrentSchoolYear();
		List<Enrollment> enrollments = new ArrayList<Enrollment>();
		String beforeJsonString = null;
		String afterJsonString = null;
		Organization targetDistrict = null;
		OrganizationTreeDetail targetAypSchool = null;
		for (TransferStudentDTO student : students) {
			// Un-enrolle the student
			enrollments = enrollmentDao.getBySsidAndOrgId(student.getStateStudentIdentifier(),student.getOldAttendanceSchoolId(), currentSchoolYear);
			long oldAttendanceSchoolId = 0l;
			for (Enrollment enrollment : enrollments) {
				
				StudentJson studentJson = studentDao.getStudentjsonData(enrollment.getStudentId());
				
				if(studentJson  != null)
				beforeJsonString = studentJson.buildjsonString();
				
				oldAttendanceSchoolId = enrollment.getAttendanceSchoolId();
				enrollment.setActiveFlag(false);
				enrollment.setExitWithdrawalDate(new Date());
				enrollment.setExitWithdrawalType(Integer.valueOf(student.getExitWithdrawalType()));
				enrollment.setModifiedDate(new Date());
				enrollment.setModifiedUser(user.getId());
				
				enrollmentDao.update(enrollment);
				
				// Clear unused test session and tests				
				studentsTestsService.unEnrollStudent(enrollment);
				
				logger.debug("Transfer Student : old Enrollment deactivated ");
				long oldEnrollmentId = enrollment.getId();
		/*Changed for F733  Now Accountability school is not mandatory*/
				// Add new Enrollment with new school and district
				if(student.getTargetAypSchoolId() != null) {
					Organization aypSchool = organizationService.get(student.getTargetAypSchoolId());
					enrollment.setAypSchoolIdentifier(aypSchool.getDisplayIdentifier());	
				}else {
					enrollment.setAypSchoolIdentifier(null);
				}
				enrollment.setAypSchoolId(student.getTargetAypSchoolId()==null? 0l: student.getTargetAypSchoolId());
				if (student.getTargetDistrictIdentifier() != null) {
					targetDistrict = organizationService.get(student.getTargetDistrictIdentifier());
					enrollment.setResidenceDistrictIdentifier(targetDistrict.getDisplayIdentifier());
					enrollment.setDistrictEntryDate(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(new SimpleDateFormat("MM/dd/yyyy").format(new Date()), "US/Central",  "MM/dd/yyyy"));
				}
				enrollment.setAccountabilityDistrictId(accountabilityDistrictId);
				enrollment.setAccountabilityDistrictIdentifier(accountabilityDistrictIdentifier);
				
				enrollment.setLocalStudentIdentifier(student.getTargetLocalId());

				enrollment.setId(0);
				enrollment.setActiveFlag(true);
				enrollment.setAuditColumnProperties();
				enrollment.setAttendanceSchoolId(student.getTargetAttendanceSchoolId());
				
				//Changed to match the date format coming from browser, in school entry date  field.
				enrollment.setSchoolEntryDate(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(new SimpleDateFormat("MM/dd/yyyy").format(new Date()), "US/Central",  "MM/dd/yyyy"));
				
				enrollment.setExitWithdrawalDate(null);
				enrollment.setExitWithdrawalType(0);

				long newEnrollmentId = 0;
				
				//Check already inactive enrollment for that school is there or not.
				Enrollment inactiveEnrollment = enrollmentDao.getInactiveEnrollmentByScoolId(student.getStateStudentIdentifier(), student.getTargetAttendanceSchoolId(), currentSchoolYear);				
				if(inactiveEnrollment != null){
					enrollment.setId(inactiveEnrollment.getId());
				    enrollmentDao.update(enrollment);
				    newEnrollmentId = inactiveEnrollment.getId();
				}else{
					enrollment.setSourceType(SourceTypeEnum.STUDENT_TRANSFER.getCode());
					newEnrollmentId = addOrUpdate(enrollment).getId();
				}
				
				logger.debug("Transfer Student : new Enrollment Added "
						+ newEnrollmentId);

				studentJson = studentDao.getStudentjsonData(enrollment.getStudentId());
				
				if(studentJson  != null){
					studentJson.setExitReason(student.getExitWithdrawalType());
					studentJson.setExitDate(new Date());
					studentJson.setExitedSchool(String.valueOf(oldAttendanceSchoolId));
					studentJson.setDestinationAttendanceSchool(student.getTargetAttendanceSchoolId().toString());
					studentJson.setDestinationAypSchool(targetAypSchool != null?targetAypSchool.getSchoolName():"");
					studentJson.setDestinationLocalId(student.getTargetLocalId());
					studentJson.setDestinationDistrict(targetDistrict != null?targetDistrict.getOrganizationName():"");
					afterJsonString = studentJson.buildjsonString();
				}
				
				// Move TestRecord
				List<EnrollmentTestTypeSubjectArea> enrollmentTestTypeSubjectAreaList = enrollmentTestTypeSubjectAreaDao
						.selectByEnrollmentId(oldEnrollmentId);
				for (EnrollmentTestTypeSubjectArea enrollmentTestTypeSubjectArea : enrollmentTestTypeSubjectAreaList) {
					EnrollmentTestTypeSubjectArea ettsa = new EnrollmentTestTypeSubjectArea();
					ettsa.setActiveFlag(true);
					ettsa.setEnrollmentId(newEnrollmentId);
					ettsa.setSubjectareaId(enrollmentTestTypeSubjectArea.getSubjectareaId());
					ettsa.setTestTypeId(enrollmentTestTypeSubjectArea.getTestTypeId());
					ettsa.setGroupingInd1(enrollmentTestTypeSubjectArea.getGroupingInd1());
					ettsa.setGroupingInd2(enrollmentTestTypeSubjectArea.getGroupingInd2());
					ettsa.setCurrentContextUserId(user.getId());
					ettsa.setAuditColumnProperties();
					enrollmentTestTypeSubjectAreaDao
							.insert(ettsa);
				}

				logger.debug("Transfer Student :Test Record moved to new school");
				
				studentService.insertIntoDomainAuditHistory(enrollment.getStudentId(), user.getId(),EventTypeEnum.TRANSFER_STUDENT.getCode(),
				SourceTypeEnum.MANUAL.getCode(), beforeJsonString, afterJsonString); 	
				
		    }

		}

	}
	/**
	 * @param user
	 * @param e
	 */
	public void undoExitRecords(User user, Enrollment e) {
		e.setExitWithdrawalDate(null);
		e.setExitWithdrawalType(0);
		e.setActiveFlag(true);
		e.setModifiedDate(new Date());
		e.setModifiedUser(user.getId());
		enrollmentDao.update(e);
	}


	private void addOrUpdateRoster(ContractingOrganizationTree contractingOrganizationTree, Organization attendanceSchool,
			Student student, Enrollment enrollment, String proctorId, String proctorName, String stateSubjectCode, String assessmentProgramCode) {
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
                .getContext().getAuthentication().getPrincipal();
		WebServiceRosterRecord rosterRecord = new WebServiceRosterRecord();
		rosterRecord.setEducatorIdentifier(proctorId);
		rosterRecord.setEducatorSchoolIdentifier(enrollment.getAttendanceSchoolProgramIdentifier());
		if(proctorName != null && proctorName.trim().length() > 0) {
			
			proctorName = proctorName.trim();
			int spaceIndex = proctorName.indexOf(' ');
			if(spaceIndex > -1){
				rosterRecord.setEducatorFirstName(proctorName.substring(0, spaceIndex));
				rosterRecord.setEducatorLastName(proctorName.substring(spaceIndex+1));
			} else {
				rosterRecord.setEducatorFirstName(proctorName);
				rosterRecord.setEducatorLastName(null);
			}
			rosterRecord.getEducator().setAssessmentProgramCodes(Arrays.asList(assessmentProgramCode));
		}
		rosterRecord.setRosterRecordType("STCO");	
		rosterRecord.setSourceType(SourceTypeEnum.TESTWEBSERVICE.getCode());
		rosterRecord.setCurrentSchoolYear(enrollment.getCurrentSchoolYear());
		rosterRecord.setSchoolIdentifier(enrollment.getAttendanceSchoolProgramIdentifier());
		rosterRecord.setStudent(student);
		rosterRecord.setStateStudentIdentifier(student.getStateStudentIdentifier());
		rosterRecord.setCourseSection(stateSubjectCode);
		rosterRecord.setStateCourseCode(stateSubjectCode);
		rosterRecord.setStateSubjectAreaCode(stateSubjectCode);
		rosterRecord.setEnrollment(enrollment);
		rosterRecord.setCurrentSchoolYear(enrollment.getCurrentSchoolYear());
		rosterRecord = (WebServiceRosterRecord) rosterService.cascadeAddOrUpdate(rosterRecord, contractingOrganizationTree);
		//inactivate previously created proctor roster for this subject 
		if (rosterRecord.getId() != null) {
			enrollmentDao.adjustStudentRoster(new Long(enrollment.getId()), rosterRecord.getId(), SourceTypeEnum.TESTWEBSERVICE.getCode(),
					userDetails.getUserId(),new Date());
		}
	}
    
    /**
     * @param kidRecord
     * @param enrollment
     */
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void addEnrollmentTestTypes(KidRecord kidRecord, Enrollment enrollment, User user,
    		Map<String, TestType> testTypesMap, Map<String, SubjectArea> subjectAreaMap) {
    	
    	if(kidRecord.getStateMathAssess() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateMathAssess()) &&
    			Arrays.asList(new String[]{"2","3"}).indexOf(kidRecord.getStateMathAssess()) >= 0) {
        	addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("D74-"+kidRecord.getStateMathAssess()).getId(), (subjectAreaMap.get("D74")).getId(),
        				kidRecord.getGroupingInd1Math(), kidRecord.getGroupingInd2Math(), null);
    	}
       	
       	if(kidRecord.getStateELAAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateELAAssessment()) &&
    			Arrays.asList(new String[]{"2","3"}).indexOf(kidRecord.getStateELAAssessment()) >= 0) {
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("SELAA-"+kidRecord.getStateELAAssessment()).getId(), (subjectAreaMap.get("SELAA")).getId(), 
    				kidRecord.getGroupingInd1ELA(), kidRecord.getGroupingInd2ELA(), null);
    	}
       	
       	if(kidRecord.getStateSciAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateSciAssessment()) &&
    			Arrays.asList(new String[]{"2","3"}).indexOf(kidRecord.getStateSciAssessment()) >= 0) {
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("SSCIA-"+kidRecord.getStateSciAssessment()).getId(), (subjectAreaMap.get("SSCIA")).getId(),
    				kidRecord.getGroupingInd1Sci(), kidRecord.getGroupingInd2Sci(), null);
    	}   
       	
       	if(kidRecord.getStateHistGovAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment()) &&
    			Arrays.asList(new String[]{"2","3"}).indexOf(kidRecord.getStateHistGovAssessment()) >= 0) {
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("SHISGOVA-"+kidRecord.getStateHistGovAssessment()).getId(), (subjectAreaMap.get("SHISGOVA")).getId(),
    				kidRecord.getGroupingInd1HistGov(), kidRecord.getGroupingInd2HistGov(), null);
    	}  
  
    	if(kidRecord.getGeneralCTEAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getGeneralCTEAssessment()) &&
    			Arrays.asList(new String[]{"1","3"}).indexOf(kidRecord.getGeneralCTEAssessment()) >= 0) {
    		
    		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("GKS-2").getId(), (subjectAreaMap.get("GKS")).getId(),
        				kidRecord.getGroupingInd1CTE(), kidRecord.getGroupingInd2CTE(), null);
    		if("3".equals(kidRecord.getGeneralCTEAssessment())) {
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("GKS-2Q").getId(), (subjectAreaMap.get("GKS")).getId(),
        				kidRecord.getGroupingInd1CTE(), kidRecord.getGroupingInd2CTE(), null);
    		} 
    	}  
        
       	if(kidRecord.getComprehensiveAgAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getComprehensiveAgAssessment()) &&
    			Arrays.asList(new String[]{"1","2","3","4"}).indexOf(kidRecord.getComprehensiveAgAssessment()) >= 0) {
       		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get("AgF&NR-A").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	    				kidRecord.getGroupingComprehensiveAg(), null, null);
    		if("2".equals(kidRecord.getComprehensiveAgAssessment())){
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("AgF&NR-AM").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingComprehensiveAg(), null, null);   	
    		} else if("3".equals(kidRecord.getComprehensiveAgAssessment())){
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("AgF&NR-AQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingComprehensiveAg(), null, null);  
    		} else if("4".equals(kidRecord.getComprehensiveAgAssessment())){
    			addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("AgF&NR-AM").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingComprehensiveAg(), null, null); 
    			addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("AgF&NR-AQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingComprehensiveAg(), null, null);    	
    		}
    	} 
       	if(kidRecord.getAnimalSystemsAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getAnimalSystemsAssessment()) &&
    			Arrays.asList(new String[]{"1","3"}).indexOf(kidRecord.getAnimalSystemsAssessment()) >= 0) {
       		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get("AgF&NR-B").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	    				kidRecord.getGroupingAnimalSystems(), null, null);
    		if("3".equals(kidRecord.getAnimalSystemsAssessment())){
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get("AgF&NR-BQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	    				kidRecord.getGroupingAnimalSystems(), null, null);
    		}
    	} 
       	if(kidRecord.getPlantSystemsAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getPlantSystemsAssessment()) &&
    			Arrays.asList(new String[]{"1","2","3","4"}).indexOf(kidRecord.getPlantSystemsAssessment()) >= 0) {
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("AgF&NR-D").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
    				kidRecord.getGroupingPlantSystems(), null, null);
    		if("2".equals(kidRecord.getPlantSystemsAssessment())){
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("AgF&NR-DM").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingPlantSystems(), null, null);   			
    		} else if("3".equals(kidRecord.getPlantSystemsAssessment())){
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("AgF&NR-DQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingPlantSystems(), null, null);    			
    		} else if("4".equals(kidRecord.getPlantSystemsAssessment())){
    			addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("AgF&NR-DQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingPlantSystems(), null, null); 
    			addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("AgF&NR-DM").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingPlantSystems(), null, null);    	
    		}
    	} 
       	if(kidRecord.getManufacturingProdAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getManufacturingProdAssessment()) &&
    			Arrays.asList(new String[]{"1","3"}).indexOf(kidRecord.getManufacturingProdAssessment()) >= 0) {
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("Mfg-E").getId(), (subjectAreaMap.get("Mfg")).getId(),
    				kidRecord.getGroupingManufacturingProd(), null, null);
    		if("3".equals(kidRecord.getManufacturingProdAssessment())){
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get("Mfg-EM").getId(), (subjectAreaMap.get("Mfg")).getId(),
	    				kidRecord.getGroupingManufacturingProd(), null, null);
    		}
    	} 
       	if(kidRecord.getDesignPreConstructionAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getDesignPreConstructionAssessment()) &&
    			Arrays.asList(new String[]{"1","3"}).indexOf(kidRecord.getDesignPreConstructionAssessment()) >= 0) {
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("Arch&Const-F").getId(), (subjectAreaMap.get("Arch&Const")).getId(),
    				kidRecord.getGroupingDesignPreConstruction(), null, null);
    		if("3".equals(kidRecord.getDesignPreConstructionAssessment())){
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get("Arch&Const-FQ").getId(), (subjectAreaMap.get("Arch&Const")).getId(),
	    				kidRecord.getGroupingDesignPreConstruction(), null, null);
    		}
    	} 
       	
       	if(kidRecord.getComprehensiveBusinessAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getComprehensiveBusinessAssessment()) &&
    			Arrays.asList(new String[]{"1","3"}).indexOf(kidRecord.getComprehensiveBusinessAssessment()) >= 0) {
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("BM&A-G").getId(), (subjectAreaMap.get("BM&A")).getId(),
    				kidRecord.getGroupingComprehensiveBusiness(), null, null);
    		if("3".equals(kidRecord.getComprehensiveBusinessAssessment())){
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get("BM&A-GQ").getId(), (subjectAreaMap.get("BM&A")).getId(),
	    				kidRecord.getGroupingComprehensiveBusiness(), null, null);
    		}
    	}

       	if(kidRecord.getFinanceAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getFinanceAssessment()) &&
    			Arrays.asList(new String[]{"1","2","3","4"}).indexOf(kidRecord.getFinanceAssessment()) >= 0) {
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("BM&A-H").getId(), (subjectAreaMap.get("BM&A")).getId(),
    				kidRecord.getGroupingFinance(), null, null);
    		if("2".equals(kidRecord.getFinanceAssessment())){
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("BM&A-HM").getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingFinance(), null, null);   			
    		} else if("3".equals(kidRecord.getFinanceAssessment())){
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("BM&A-HQ").getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingFinance(), null, null);    			
    		} else if("4".equals(kidRecord.getFinanceAssessment())){
    			addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("BM&A-HQ").getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingFinance(), null, null); 
    			addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("BM&A-HM").getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingFinance(), null, null);    	
    		}
    	} 
       	if(kidRecord.getElpa21Assessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getElpa21Assessment()) &&
    			Arrays.asList(new String[]{"1", "2"}).indexOf(kidRecord.getElpa21Assessment()) >= 0) {
       		if(kidRecord.getElpa21Assessment().equals("1")) { //Have only one code for ELPA, US16702
       			kidRecord.setElpa21Assessment("2");
       		}
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("ELPA21-"+kidRecord.getElpa21Assessment()).getId(), (subjectAreaMap.get("ELPA21")).getId(),
    				kidRecord.getGroupingInd1Elpa21(), kidRecord.getGroupingInd2Elpa21(), null);
    	} 
       	
       	//remove unwanted test types (cpass)
       	processClearTestTypes( kidRecord,  enrollment,  user, testTypesMap,subjectAreaMap, false);
    }
    
    /**
     * @param kidRecord
     * @param enrollment
     */
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void addTECEnrollmentTestTypes(KidRecord kidRecord, Enrollment enrollment, User user,
    		Map<String, TestType> testTypesMap, Map<String, SubjectArea> subjectAreaMap) {
    	
    	if(kidRecord.getStateMathAssess() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateMathAssess()) &&
    			Arrays.asList(new String[]{"2","3","GN","P"}).indexOf(kidRecord.getStateMathAssess()) >= 0){
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("D74-"+kidRecord.getStateMathAssess()).getId(), (subjectAreaMap.get("D74")).getId(),
    				kidRecord.getGroupingInd1Math(), kidRecord.getGroupingInd2Math(), null);
    	}
       	
       	if(kidRecord.getStateELAAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateELAAssessment()) &&
    			Arrays.asList(new String[]{"2","3","GN","P"}).indexOf(kidRecord.getStateELAAssessment()) >= 0){
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("SELAA-"+kidRecord.getStateELAAssessment()).getId(), (subjectAreaMap.get("SELAA")).getId(), 
    				kidRecord.getGroupingInd1ELA(), kidRecord.getGroupingInd2ELA(), null);
    	}
       	
       	if(kidRecord.getStateSciAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateSciAssessment())  &&
    			Arrays.asList(new String[]{"2","3","GN","P"}).indexOf(kidRecord.getStateSciAssessment()) >= 0){
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("SSCIA-"+kidRecord.getStateSciAssessment()).getId(), (subjectAreaMap.get("SSCIA")).getId(),
    				kidRecord.getGroupingInd1Sci(), kidRecord.getGroupingInd2Sci(), null);
    	}   
       	
       	if(kidRecord.getStateHistGovAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment()) &&
    			Arrays.asList(new String[]{"2","3"}).indexOf(kidRecord.getStateHistGovAssessment()) >= 0){
    		addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("SHISGOVA-"+kidRecord.getStateHistGovAssessment()).getId(), (subjectAreaMap.get("SHISGOVA")).getId(),
    				kidRecord.getGroupingInd1HistGov(), kidRecord.getGroupingInd2HistGov(), null);
    	}  
  
    	if(kidRecord.getGeneralCTEAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getGeneralCTEAssessment()) &
    			Arrays.asList(new String[]{"2","2Q"}).indexOf(kidRecord.getGeneralCTEAssessment()) >= 0) {
    		if("2".equals(kidRecord.getGeneralCTEAssessment())) {
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	        				testTypesMap.get("GKS-2").getId(), (subjectAreaMap.get("GKS")).getId(),
	        				kidRecord.getGroupingInd1CTE(), kidRecord.getGroupingInd2CTE(), null);
    		} else if("2Q".equals(kidRecord.getGeneralCTEAssessment())) {
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("GKS-2Q").getId(), (subjectAreaMap.get("GKS")).getId(),
        				kidRecord.getGroupingInd1CTE(), kidRecord.getGroupingInd2CTE(), null);
    		}
    	}  
        
       	if(kidRecord.getComprehensiveAgAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getComprehensiveAgAssessment()) &&
    			Arrays.asList(new String[]{"A","AM","AQ"}).indexOf(kidRecord.getComprehensiveAgAssessment()) >= 0) {
       		if("A".equals(kidRecord.getComprehensiveAgAssessment())){
	       		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get("AgF&NR-A").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	    				kidRecord.getGroupingComprehensiveAg(), null, null);
       		} else if("AM".equals(kidRecord.getComprehensiveAgAssessment())){
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("AgF&NR-AM").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingComprehensiveAg(), null, null);   			
    		} else if("AQ".equals(kidRecord.getComprehensiveAgAssessment())){
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("AgF&NR-AQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingComprehensiveAg(), null, null);    			
    		} 
    	} 
       	
       	if(kidRecord.getAnimalSystemsAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getAnimalSystemsAssessment()) &&
    			Arrays.asList(new String[]{"B","BQ"}).indexOf(kidRecord.getAnimalSystemsAssessment()) >= 0) {
       		if("B".equals(kidRecord.getAnimalSystemsAssessment())){
	       		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get("AgF&NR-B").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	    				kidRecord.getGroupingAnimalSystems(), null, null);
       		} else if("BQ".equals(kidRecord.getAnimalSystemsAssessment())){
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get("AgF&NR-BQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	    				kidRecord.getGroupingAnimalSystems(), null, null);
    		}
    	} 
       	
       	if(kidRecord.getPlantSystemsAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getPlantSystemsAssessment()) &&
    			Arrays.asList(new String[]{"D","DQ","DM"}).indexOf(kidRecord.getPlantSystemsAssessment()) >= 0) {
       		if("D".equals(kidRecord.getPlantSystemsAssessment())){
       			addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("AgF&NR-D").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
    				kidRecord.getGroupingPlantSystems(), null, null);
       		} else if("DQ".equals(kidRecord.getPlantSystemsAssessment())){
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("AgF&NR-DQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingPlantSystems(), null, null);   			
    		} else if("DM".equals(kidRecord.getPlantSystemsAssessment())){
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("AgF&NR-DM").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingPlantSystems(), null, null);    			
    		}
    	} 
       	
       	if(kidRecord.getManufacturingProdAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getManufacturingProdAssessment()) &&
    			Arrays.asList(new String[]{"E","EM"}).indexOf(kidRecord.getManufacturingProdAssessment()) >= 0) {
       		if("E".equals(kidRecord.getManufacturingProdAssessment())){
       			addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("Mfg-E").getId(), (subjectAreaMap.get("Mfg")).getId(),
    				kidRecord.getGroupingManufacturingProd(), null, null);
       		} else if("EM".equals(kidRecord.getManufacturingProdAssessment())){
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get("Mfg-EM").getId(), (subjectAreaMap.get("Mfg")).getId(),
	    				kidRecord.getGroupingManufacturingProd(), null, null);
    		}
    	} 
       	
       	if(kidRecord.getDesignPreConstructionAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getDesignPreConstructionAssessment()) &&
    			Arrays.asList(new String[]{"F","FQ"}).indexOf(kidRecord.getDesignPreConstructionAssessment()) >= 0) {
       		if("F".equals(kidRecord.getDesignPreConstructionAssessment())){
       			addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("Arch&Const-F").getId(), (subjectAreaMap.get("Arch&Const")).getId(),
    				kidRecord.getGroupingDesignPreConstruction(), null, null);
       		} else if("FQ".equals(kidRecord.getDesignPreConstructionAssessment())){
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get("Arch&Const-FQ").getId(), (subjectAreaMap.get("Arch&Const")).getId(),
	    				kidRecord.getGroupingDesignPreConstruction(), null, null);
    		}
    	} 
       	
       	if(kidRecord.getComprehensiveBusinessAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getComprehensiveBusinessAssessment()) &&
    			Arrays.asList(new String[]{"G","GQ"}).indexOf(kidRecord.getComprehensiveBusinessAssessment()) >= 0) {
       		if("G".equals(kidRecord.getComprehensiveBusinessAssessment())){
       			addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("BM&A-G").getId(), (subjectAreaMap.get("BM&A")).getId(),
    				kidRecord.getGroupingComprehensiveBusiness(), null, null);
       		} else if("GQ".equals(kidRecord.getComprehensiveBusinessAssessment())){
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get("BM&A-GQ").getId(), (subjectAreaMap.get("BM&A")).getId(),
	    				kidRecord.getGroupingComprehensiveBusiness(), null, null);
    		}
    	}

       	if(kidRecord.getFinanceAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getFinanceAssessment()) &&
    			Arrays.asList(new String[]{"H","HQ","HM"}).indexOf(kidRecord.getFinanceAssessment()) >= 0) {
       		if("H".equals(kidRecord.getFinanceAssessment())){
       			addOrUpdateEnrollmentTestType(enrollment, user,
    				testTypesMap.get("BM&A-H").getId(), (subjectAreaMap.get("BM&A")).getId(),
    				kidRecord.getGroupingFinance(), null, null);
       		} else if("HQ".equals(kidRecord.getFinanceAssessment())){
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("BM&A-HQ").getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingFinance(), null, null);   			
    		} else if("HM".equals(kidRecord.getFinanceAssessment())){
        		addOrUpdateEnrollmentTestType(enrollment, user,
        				testTypesMap.get("BM&A-HM").getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingFinance(), null, null);    			
    		}
    	}
    }
    
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean isValidTestRecord(KidRecord kidRecord) {
    	boolean isValidTestRecord = false;
    	if(kidRecord.getStateMathAssess() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateMathAssess()) &&
    			!"0".equals(kidRecord.getStateMathAssess())) {
    		isValidTestRecord = true;
    	} else if(kidRecord.getStateELAAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateELAAssessment()) &&
    			!"0".equals(kidRecord.getStateELAAssessment())) {
    		isValidTestRecord = true;
    	} else if(kidRecord.getStateSciAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateSciAssessment()) &&
    			!"0".equals(kidRecord.getStateSciAssessment())) {
    		isValidTestRecord = true;
    	} else if(kidRecord.getStateHistGovAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment()) &&
    			!"0".equals(kidRecord.getStateHistGovAssessment())) {
    		isValidTestRecord = true;
    	} else if(kidRecord.getGeneralCTEAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getGeneralCTEAssessment()) &&
    			(Arrays.asList(new String[]{"1","3","C"}).indexOf(kidRecord.getGeneralCTEAssessment()) >= 0
    					|| Arrays.asList(new String[]{"2","2Q","C"}).indexOf(kidRecord.getGeneralCTEAssessment()) >= 0)) {
    		isValidTestRecord = true;
    	} else if(kidRecord.getComprehensiveAgAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getComprehensiveAgAssessment()) &&
    			(Arrays.asList(new String[]{"1","2","3","4","C"}).indexOf(kidRecord.getComprehensiveAgAssessment()) >= 0
    				|| Arrays.asList(new String[]{"A","AM","AQ","C"}).indexOf(kidRecord.getComprehensiveAgAssessment()) >= 0)) {
    		isValidTestRecord = true;
    	} else if(kidRecord.getAnimalSystemsAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getAnimalSystemsAssessment()) &&
    			(Arrays.asList(new String[]{"1","3","C"}).indexOf(kidRecord.getAnimalSystemsAssessment()) >= 0
    			|| Arrays.asList(new String[]{"B","BQ","C"}).indexOf(kidRecord.getAnimalSystemsAssessment()) >= 0)) {
    		isValidTestRecord = true;
    	} else if(kidRecord.getPlantSystemsAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getPlantSystemsAssessment()) &&
    			(Arrays.asList(new String[]{"1","2","3","4","C"}).indexOf(kidRecord.getPlantSystemsAssessment()) >= 0
    			|| Arrays.asList(new String[]{"D","DQ","DM","C"}).indexOf(kidRecord.getPlantSystemsAssessment()) >= 0)) {
    		isValidTestRecord = true;
    	} else if(kidRecord.getManufacturingProdAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getManufacturingProdAssessment()) &&
    			(Arrays.asList(new String[]{"1","3","C"}).indexOf(kidRecord.getManufacturingProdAssessment()) >= 0
    			|| Arrays.asList(new String[]{"E","EM","C"}).indexOf(kidRecord.getManufacturingProdAssessment()) >= 0)) {
    		isValidTestRecord = true;
    	} else if(kidRecord.getDesignPreConstructionAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getDesignPreConstructionAssessment()) &&
    			(Arrays.asList(new String[]{"1","3","C"}).indexOf(kidRecord.getDesignPreConstructionAssessment()) >= 0
    			|| Arrays.asList(new String[]{"F","FQ","C"}).indexOf(kidRecord.getDesignPreConstructionAssessment()) >= 0)) {
    		isValidTestRecord = true;
    	} else if(kidRecord.getComprehensiveBusinessAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getComprehensiveBusinessAssessment()) &&
    			(Arrays.asList(new String[]{"1","3","C"}).indexOf(kidRecord.getComprehensiveBusinessAssessment()) >= 0
    			 || Arrays.asList(new String[]{"G","GQ","C"}).indexOf(kidRecord.getComprehensiveBusinessAssessment()) >= 0)) {
    		isValidTestRecord = true;
    	} else if(kidRecord.getFinanceAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getFinanceAssessment()) &&
    			(Arrays.asList(new String[]{"1","2","3","4","C"}).indexOf(kidRecord.getFinanceAssessment()) >= 0
    			|| Arrays.asList(new String[]{"H","HQ","HM","C"}).indexOf(kidRecord.getFinanceAssessment()) >= 0)) {
    		isValidTestRecord = true;
    	} else if(kidRecord.getElpa21Assessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getElpa21Assessment()) &&
    			Arrays.asList(new String[]{"1","2","C"}).indexOf(kidRecord.getElpa21Assessment()) >= 0) {
    		isValidTestRecord = true;
    	} 
       	
    	return isValidTestRecord;
    }

    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final void processClearTestTypes(KidRecord kidRecord, Enrollment enrollment, User user,
    		Map<String, TestType> testTypesMap, Map<String, SubjectArea> subjectAreaMap, boolean isTecRecord) {
    	Long clearTestTypeId=testTypesMap.get("C").getId();
    	if(kidRecord.getStateMathAssess() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateMathAssess()) &&
    			"C".equals(kidRecord.getStateMathAssess())) {
    		studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("D74-2").getTestTypeCode(), (subjectAreaMap.get("D74")).getSubjectAreaCode());
    		studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("D74-3").getTestTypeCode(), (subjectAreaMap.get("D74")).getSubjectAreaCode());
    		studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("D74-GN").getTestTypeCode(), (subjectAreaMap.get("D74")).getSubjectAreaCode());
    		studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("D74-P").getTestTypeCode(), (subjectAreaMap.get("D74")).getSubjectAreaCode());
    		
    		addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("D74-2").getId(), (subjectAreaMap.get("D74")).getId(),
    				kidRecord.getGroupingInd1Math(), kidRecord.getGroupingInd2Math(), clearTestTypeId);
        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("D74-3").getId(), (subjectAreaMap.get("D74")).getId(),
        				kidRecord.getGroupingInd1Math(), kidRecord.getGroupingInd2Math(), clearTestTypeId);
        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("D74-GN").getId(), (subjectAreaMap.get("D74")).getId(),
    				kidRecord.getGroupingInd1Math(), kidRecord.getGroupingInd2Math(), clearTestTypeId);
        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("D74-P").getId(), (subjectAreaMap.get("D74")).getId(),
    				kidRecord.getGroupingInd1Math(), kidRecord.getGroupingInd2Math(), clearTestTypeId);
    	} 
       	
       	if(kidRecord.getStateELAAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateELAAssessment()) &&
    			"C".equals(kidRecord.getStateELAAssessment())) {
    		studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("SELAA-2").getTestTypeCode(), (subjectAreaMap.get("SELAA")).getSubjectAreaCode());
    		studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("SELAA-3").getTestTypeCode(), (subjectAreaMap.get("SELAA")).getSubjectAreaCode());
    		studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("SELAA-GN").getTestTypeCode(), (subjectAreaMap.get("SELAA")).getSubjectAreaCode());
    		studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("SELAA-P").getTestTypeCode(), (subjectAreaMap.get("SELAA")).getSubjectAreaCode());
    		
    		addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SELAA-2").getId(), (subjectAreaMap.get("SELAA")).getId(),
    				kidRecord.getGroupingInd1ELA(), kidRecord.getGroupingInd2ELA(), clearTestTypeId);
        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SELAA-3").getId(), (subjectAreaMap.get("SELAA")).getId(),
        				kidRecord.getGroupingInd1ELA(), kidRecord.getGroupingInd2ELA(), clearTestTypeId);
        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SELAA-GN").getId(), (subjectAreaMap.get("SELAA")).getId(),
    				kidRecord.getGroupingInd1ELA(), kidRecord.getGroupingInd2ELA(), clearTestTypeId);
        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SELAA-P").getId(), (subjectAreaMap.get("SELAA")).getId(),
    				kidRecord.getGroupingInd1ELA(), kidRecord.getGroupingInd2ELA(), clearTestTypeId);
    	}
       	
       	if(kidRecord.getStateSciAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateSciAssessment()) &&
    			"C".equals(kidRecord.getStateSciAssessment())) {
    		studentsTestsService.removeTestSessionsOnClear(enrollment, 
    					testTypesMap.get("SSCIA-2").getTestTypeCode(), (subjectAreaMap.get("SSCIA")).getSubjectAreaCode());
    		studentsTestsService.removeTestSessionsOnClear(enrollment, 
					testTypesMap.get("SSCIA-3").getTestTypeCode(), (subjectAreaMap.get("SSCIA")).getSubjectAreaCode());
    		studentsTestsService.removeTestSessionsOnClear(enrollment, 
					testTypesMap.get("SSCIA-GN").getTestTypeCode(), (subjectAreaMap.get("SSCIA")).getSubjectAreaCode());
    		studentsTestsService.removeTestSessionsOnClear(enrollment, 
					testTypesMap.get("SSCIA-P").getTestTypeCode(), (subjectAreaMap.get("SSCIA")).getSubjectAreaCode());
    		
    		addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SSCIA-2").getId(), (subjectAreaMap.get("SSCIA")).getId(),
    				kidRecord.getGroupingInd1Sci(), kidRecord.getGroupingInd2Sci(), clearTestTypeId);
        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SSCIA-3").getId(), (subjectAreaMap.get("SSCIA")).getId(),
        				kidRecord.getGroupingInd1Sci(), kidRecord.getGroupingInd2Sci(), clearTestTypeId);
        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SSCIA-GN").getId(), (subjectAreaMap.get("SSCIA")).getId(),
    				kidRecord.getGroupingInd1Sci(), kidRecord.getGroupingInd2Sci(), clearTestTypeId);
        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SSCIA-P").getId(), (subjectAreaMap.get("SSCIA")).getId(),
    				kidRecord.getGroupingInd1Sci(), kidRecord.getGroupingInd2Sci(), clearTestTypeId);
    	}   
       	
       	if(kidRecord.getStateHistGovAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment()) &&
    			"C".equals(kidRecord.getStateHistGovAssessment())) {
    		studentsTestsService.removeTestSessionsOnClear(enrollment, 
    					testTypesMap.get("SHISGOVA-2").getTestTypeCode(), (subjectAreaMap.get("SHISGOVA")).getSubjectAreaCode());
    		studentsTestsService.removeTestSessionsOnClear(enrollment, 
					testTypesMap.get("SHISGOVA-3").getTestTypeCode(), (subjectAreaMap.get("SHISGOVA")).getSubjectAreaCode());
    		/*studentsTestsService.removeTestSessionsOnClear(enrollment, 
					testTypesMap.get("SHISGOVA-GN").getTestTypeCode(), (subjectAreaMap.get("SHISGOVA")).getSubjectAreaCode());*/
    		
    		addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SHISGOVA-2").getId(), (subjectAreaMap.get("SHISGOVA")).getId(),
    		kidRecord.getGroupingInd1HistGov(), kidRecord.getGroupingInd2HistGov(), clearTestTypeId);
        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SHISGOVA-3").getId(), (subjectAreaMap.get("SHISGOVA")).getId(),
        				kidRecord.getGroupingInd1HistGov(), kidRecord.getGroupingInd2HistGov(), clearTestTypeId);
        	/*addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("SHISGOVA-GN").getId(), (subjectAreaMap.get("SHISGOVA")).getId(),
    				kidRecord.getGroupingInd1HistGov(), kidRecord.getGroupingInd2HistGov(), clearTestTypeId);*/
    	}
    	
    	if(kidRecord.getGeneralCTEAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getGeneralCTEAssessment()) &&
    			Arrays.asList(new String[]{"1","C"}).indexOf(kidRecord.getGeneralCTEAssessment()) >= 0) {

			if("C".equals(kidRecord.getGeneralCTEAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("GKS-2").getTestTypeCode(), (subjectAreaMap.get("GKS")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("GKS-2Q").getTestTypeCode(), (subjectAreaMap.get("GKS")).getSubjectAreaCode());
				
	    		addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("GKS-2").getId(), (subjectAreaMap.get("GKS")).getId(),
	    				kidRecord.getGroupingInd1CTE(), kidRecord.getGroupingInd2CTE(), clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("GKS-2Q").getId(), (subjectAreaMap.get("GKS")).getId(),
	        				kidRecord.getGroupingInd1CTE(), kidRecord.getGroupingInd2CTE(), clearTestTypeId);
			} else {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("GKS-2Q").getTestTypeCode(), (subjectAreaMap.get("GKS")).getSubjectAreaCode());
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("GKS-2Q").getId(), (subjectAreaMap.get("GKS")).getId(),
        				kidRecord.getGroupingInd1CTE(), kidRecord.getGroupingInd2CTE(), clearTestTypeId);
			}
    	}  
        
       	if(kidRecord.getComprehensiveAgAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getComprehensiveAgAssessment()) &&
    			Arrays.asList(new String[]{"1","2","3","C"}).indexOf(kidRecord.getComprehensiveAgAssessment()) >= 0) {
       		if("C".equals(kidRecord.getComprehensiveAgAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-A").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AM").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AQ").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-A").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	    				kidRecord.getGroupingComprehensiveAg(), null, clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AM").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	        				kidRecord.getGroupingComprehensiveAg(), null, clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingComprehensiveAg(), null, clearTestTypeId);
			} else if("1".equals(kidRecord.getComprehensiveAgAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AM").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AQ").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());

	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AM").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	        				kidRecord.getGroupingComprehensiveAg(), null, clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingComprehensiveAg(), null, clearTestTypeId);
			} else if("2".equals(kidRecord.getComprehensiveAgAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AQ").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());

	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingComprehensiveAg(), null, clearTestTypeId);
    		} else if("3".equals(kidRecord.getComprehensiveAgAssessment())){
    			studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-AM").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode()); 
    			
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-AM").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	        				kidRecord.getGroupingComprehensiveAg(), null, clearTestTypeId);		
    		}
    	} 
       	if(kidRecord.getAnimalSystemsAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getAnimalSystemsAssessment()) &&
    			Arrays.asList(new String[]{"1","C"}).indexOf(kidRecord.getAnimalSystemsAssessment()) >= 0) {
       		if("C".equals(kidRecord.getAnimalSystemsAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-B").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-BQ").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-B").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	    				kidRecord.getGroupingAnimalSystems(), null, clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-BQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	        				kidRecord.getGroupingAnimalSystems(), null, clearTestTypeId);
			} else if("1".equals(kidRecord.getAnimalSystemsAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-BQ").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-BQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingAnimalSystems(), null, clearTestTypeId);
			}
    	} 
       	if(kidRecord.getPlantSystemsAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getPlantSystemsAssessment()) &&
    			Arrays.asList(new String[]{"1","2","3","C"}).indexOf(kidRecord.getPlantSystemsAssessment()) >= 0) {
       		if("C".equals(kidRecord.getPlantSystemsAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-D").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DQ").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DM").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-D").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	    				kidRecord.getGroupingPlantSystems(), null, clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	        				kidRecord.getGroupingPlantSystems(), null, clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DM").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingPlantSystems(), null, clearTestTypeId);
			} else if("1".equals(kidRecord.getPlantSystemsAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DQ").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DM").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingPlantSystems(), null, clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DM").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
    				kidRecord.getGroupingPlantSystems(), null, clearTestTypeId);
			}else if("2".equals(kidRecord.getPlantSystemsAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DQ").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());
				
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DQ").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
	        				kidRecord.getGroupingPlantSystems(), null, clearTestTypeId);
    		} else if("3".equals(kidRecord.getPlantSystemsAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("AgF&NR-DM").getTestTypeCode(), (subjectAreaMap.get("AgF&NR")).getSubjectAreaCode());  

	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("AgF&NR-DM").getId(), (subjectAreaMap.get("AgF&NR")).getId(),
        				kidRecord.getGroupingPlantSystems(), null, clearTestTypeId);
    		}
    	} 
       	if(kidRecord.getManufacturingProdAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getManufacturingProdAssessment()) &&
    			Arrays.asList(new String[]{"1","C"}).indexOf(kidRecord.getManufacturingProdAssessment()) >= 0) {
			if("C".equals(kidRecord.getManufacturingProdAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("Mfg-E").getTestTypeCode(), (subjectAreaMap.get("Mfg")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("Mfg-EM").getTestTypeCode(), (subjectAreaMap.get("Mfg")).getSubjectAreaCode());
				
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("Mfg-E").getId(), (subjectAreaMap.get("Mfg")).getId(),
	        				kidRecord.getGroupingManufacturingProd(), null, clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("Mfg-EM").getId(), (subjectAreaMap.get("Mfg")).getId(),
        				kidRecord.getGroupingManufacturingProd(), null, clearTestTypeId);
       		} else if("1".equals(kidRecord.getManufacturingProdAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("Mfg-EM").getTestTypeCode(), (subjectAreaMap.get("Mfg")).getSubjectAreaCode());
				
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("Mfg-EM").getId(), (subjectAreaMap.get("Mfg")).getId(),
        				kidRecord.getGroupingManufacturingProd(), null, clearTestTypeId);
    		}
    	} 
       	if(kidRecord.getDesignPreConstructionAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getDesignPreConstructionAssessment()) &&
    			Arrays.asList(new String[]{"1","C"}).indexOf(kidRecord.getDesignPreConstructionAssessment()) >= 0) {
			if("C".equals(kidRecord.getDesignPreConstructionAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("Arch&Const-F").getTestTypeCode(), (subjectAreaMap.get("Arch&Const")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("Arch&Const-FQ").getTestTypeCode(), (subjectAreaMap.get("Arch&Const")).getSubjectAreaCode());
				
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("Arch&Const-F").getId(), (subjectAreaMap.get("Arch&Const")).getId(),
        				kidRecord.getGroupingDesignPreConstruction(), null, clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("Arch&Const-FQ").getId(), (subjectAreaMap.get("Arch&Const")).getId(),
    				kidRecord.getGroupingDesignPreConstruction(), null, clearTestTypeId);
       		} else if("1".equals(kidRecord.getDesignPreConstructionAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("Arch&Const-FQ").getTestTypeCode(), (subjectAreaMap.get("Arch&Const")).getSubjectAreaCode());
				
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("Arch&Const-FQ").getId(), (subjectAreaMap.get("Arch&Const")).getId(),
    				kidRecord.getGroupingDesignPreConstruction(), null, clearTestTypeId);
    		}
    	} 
       	
       	if(kidRecord.getComprehensiveBusinessAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getComprehensiveBusinessAssessment()) &&
    			Arrays.asList(new String[]{"1","C"}).indexOf(kidRecord.getComprehensiveBusinessAssessment()) >= 0) {
			if("C".equals(kidRecord.getComprehensiveBusinessAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-G").getTestTypeCode(), (subjectAreaMap.get("BM&A")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-GQ").getTestTypeCode(), (subjectAreaMap.get("BM&A")).getSubjectAreaCode());
				
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-G").getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingComprehensiveBusiness(), null, clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-GQ").getId(), (subjectAreaMap.get("BM&A")).getId(),
    				kidRecord.getGroupingComprehensiveBusiness(), null, clearTestTypeId);
       		} else if("1".equals(kidRecord.getComprehensiveBusinessAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-GQ").getTestTypeCode(), (subjectAreaMap.get("BM&A")).getSubjectAreaCode());
				
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-GQ").getId(), (subjectAreaMap.get("BM&A")).getId(),
    				kidRecord.getGroupingComprehensiveBusiness(), null, clearTestTypeId);
    		}
    	}

       	if(kidRecord.getFinanceAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getFinanceAssessment()) &&
    			Arrays.asList(new String[]{"1","2","3","C"}).indexOf(kidRecord.getFinanceAssessment()) >= 0) {
			if("C".equals(kidRecord.getFinanceAssessment())) {
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-H").getTestTypeCode(), (subjectAreaMap.get("BM&A")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-HQ").getTestTypeCode(), (subjectAreaMap.get("BM&A")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-HM").getTestTypeCode(), (subjectAreaMap.get("BM&A")).getSubjectAreaCode());
				
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-H").getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingFinance(), null, clearTestTypeId);
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-HQ").getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingFinance(), null, clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-HM").getId(), (subjectAreaMap.get("BM&A")).getId(),
    				kidRecord.getGroupingFinance(), null, clearTestTypeId);
			} else if("1".equals(kidRecord.getFinanceAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-HQ").getTestTypeCode(), (subjectAreaMap.get("BM&A")).getSubjectAreaCode());
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-HM").getTestTypeCode(), (subjectAreaMap.get("BM&A")).getSubjectAreaCode());
				
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-HQ").getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingFinance(), null, clearTestTypeId);
	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-HM").getId(), (subjectAreaMap.get("BM&A")).getId(),
    				kidRecord.getGroupingFinance(), null, clearTestTypeId);
			}else if("2".equals(kidRecord.getFinanceAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-HQ").getTestTypeCode(), (subjectAreaMap.get("BM&A")).getSubjectAreaCode()); 
				
				addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-HQ").getId(), (subjectAreaMap.get("BM&A")).getId(),
        				kidRecord.getGroupingFinance(), null, clearTestTypeId);
    		} else if("3".equals(kidRecord.getFinanceAssessment())){
				studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("BM&A-HM").getTestTypeCode(), (subjectAreaMap.get("BM&A")).getSubjectAreaCode());  	

	        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("BM&A-HM").getId(), (subjectAreaMap.get("BM&A")).getId(),
    				kidRecord.getGroupingFinance(), null, clearTestTypeId);
    		} 
    	} 
       	if(kidRecord.getElpa21Assessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getElpa21Assessment()) &&
    			Arrays.asList(new String[]{"C"}).indexOf(kidRecord.getElpa21Assessment()) >= 0) {
       		if(isTecRecord){
    			studentsTestsService.removeTestSessionsOnClear(enrollment, kidRecord.getTestType(), (subjectAreaMap.get("ELPA21")).getSubjectAreaCode());
    		} else {
    			studentsTestsService.removeTestSessionsOnClear(enrollment, testTypesMap.get("ELPA21-2").getTestTypeCode(), (subjectAreaMap.get("ELPA21")).getSubjectAreaCode());
    		}
			
        	addOrUpdateEnrollmentTestType(enrollment, user, testTypesMap.get("ELPA21-2").getId(), (subjectAreaMap.get("ELPA21")).getId(),
        			kidRecord.getGroupingInd1Elpa21(), kidRecord.getGroupingInd2Elpa21(), clearTestTypeId);
    	} 
    }
    
	private void addOrUpdateEnrollmentTestType(Enrollment enrollment, 
			User user, Long testTypeId, Long subjectAreaId, String groupingInd1, String groupingInd2, Long clearTestTypeId) {
		
		EnrollmentTestTypeSubjectArea enrollmentTestTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
		enrollmentTestTypeSubjectArea.setEnrollmentId(enrollment.getId());
		enrollmentTestTypeSubjectArea.setSubjectareaId(subjectAreaId);
		enrollmentTestTypeSubjectArea.setGroupingInd1(groupingInd1);
		enrollmentTestTypeSubjectArea.setGroupingInd2(groupingInd2);
		enrollmentTestTypeSubjectArea.setCurrentContextUserId(user.getId());
		enrollmentTestTypeSubjectArea.setAuditColumnPropertiesForUpdate();
		if(null != clearTestTypeId) {
			enrollmentTestTypeSubjectArea.setTestTypeId(clearTestTypeId);
			enrollmentTestTypeSubjectArea.setActiveFlag(false);
		} else {
			enrollmentTestTypeSubjectArea.setTestTypeId(testTypeId);
		}
		
		EnrollmentTestTypeSubjectAreaExample enrollmentTestTypeSubjectAreaExample = new EnrollmentTestTypeSubjectAreaExample();
		EnrollmentTestTypeSubjectAreaExample.Criteria enrollmentTestTypeSubjectAreaCriteria
		    = enrollmentTestTypeSubjectAreaExample.createCriteria();
		enrollmentTestTypeSubjectAreaCriteria.andEnrollmentIdEqualTo(enrollment.getId());
		enrollmentTestTypeSubjectAreaCriteria.andSubjectareaIdEqualTo(subjectAreaId);
		enrollmentTestTypeSubjectAreaCriteria.andTestTypeIdEqualTo(testTypeId);
		Integer updatedValue = enrollmentTestTypeSubjectAreaDao.updateByExampleSelective(
				enrollmentTestTypeSubjectArea, enrollmentTestTypeSubjectAreaExample);
		
		if(null == clearTestTypeId && (updatedValue == null || updatedValue < 1)) {	        
			enrollmentTestTypeSubjectArea.setAuditColumnProperties();
			enrollmentTestTypeSubjectAreaDao.insert(enrollmentTestTypeSubjectArea);
		}
	}	
	
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final boolean isKAPKid(KidRecord kidRecord) {
    	boolean isKAPKid = false;
    	if(kidRecord.getStateMathAssess() != null && StringUtils.isNotEmpty(kidRecord.getStateMathAssess()) &&
    			"2".equals(kidRecord.getStateMathAssess())) {
    		isKAPKid = true;
    	}
       	
       	if(kidRecord.getStateELAAssessment() != null && StringUtils.isNotEmpty(kidRecord.getStateELAAssessment()) &&
       			"2".equals(kidRecord.getStateELAAssessment())) {
       		isKAPKid = true;
    	}
       	
       	if(kidRecord.getStateSciAssessment() != null && StringUtils.isNotEmpty(kidRecord.getStateSciAssessment()) &&
       			"2".equals(kidRecord.getStateSciAssessment())) {
       		isKAPKid = true;
    	}   
       	
       	if(kidRecord.getStateHistGovAssessment() != null && StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment()) &&
       			"2".equals(kidRecord.getStateHistGovAssessment())) {
       		isKAPKid = true;
    	} 
       	return isKAPKid;
	}
    
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
 	public final boolean isELPAKid(KidRecord kidRecord) {
     	boolean isELPAKid = false;
        if(kidRecord.getElpa21Assessment() != null && StringUtils.isNotEmpty(kidRecord.getElpa21Assessment()) &&
     			Arrays.asList(new String[]{"1", "2"}).indexOf(kidRecord.getElpa21Assessment()) >= 0) {
        	isELPAKid = true;
     	}
        return isELPAKid;
 	}
    
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean isDLMKid(KidRecord kidRecord) {
    	boolean dlmKid = false;
    	
    	if(kidRecord.getStateMathAssess() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateMathAssess()) &&
    			"3".equals(kidRecord.getStateMathAssess())) {
    		dlmKid = true;
    	}

    	if(kidRecord.getStateELAAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateELAAssessment()) &&
    			"3".equals(kidRecord.getStateELAAssessment())) {
    		dlmKid = true;
    	}
    	
    	if(kidRecord.getStateSciAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateSciAssessment()) &&
    			"3".equals(kidRecord.getStateSciAssessment())) {
    		dlmKid = true;
    	}
    	
    	if(kidRecord.getStateHistGovAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment()) &&
    			"3".equals(kidRecord.getStateHistGovAssessment())) {
    		dlmKid = true;
    	}
    	
    	return dlmKid;
    }
    
    @Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final boolean isCpassKid(KidRecord kidRecord) {
    	boolean cpassKid = false;

    	if(kidRecord.getGeneralCTEAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getGeneralCTEAssessment()) &&
    			(Arrays.asList(new String[]{"1","3"}).indexOf(kidRecord.getGeneralCTEAssessment()) >= 0
    					|| Arrays.asList(new String[]{"2","2Q"}).indexOf(kidRecord.getGeneralCTEAssessment()) >= 0)) {
    		cpassKid = true;
    	}
       	if(kidRecord.getComprehensiveAgAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getComprehensiveAgAssessment()) &&
    			(Arrays.asList(new String[]{"1","2","3","4"}).indexOf(kidRecord.getComprehensiveAgAssessment()) >= 0
    				|| Arrays.asList(new String[]{"A","AM","AQ"}).indexOf(kidRecord.getComprehensiveAgAssessment()) >= 0)) {
       		cpassKid = true;
    	} 
       	if(kidRecord.getAnimalSystemsAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getAnimalSystemsAssessment()) &&
    			(Arrays.asList(new String[]{"1","3"}).indexOf(kidRecord.getAnimalSystemsAssessment()) >= 0
    			|| Arrays.asList(new String[]{"B","BQ"}).indexOf(kidRecord.getAnimalSystemsAssessment()) >= 0)) {
       		cpassKid = true;
    	} 
       	if(kidRecord.getPlantSystemsAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getPlantSystemsAssessment()) &&
    			(Arrays.asList(new String[]{"1","2","3","4"}).indexOf(kidRecord.getPlantSystemsAssessment()) >= 0
    			|| Arrays.asList(new String[]{"D","DQ","DM"}).indexOf(kidRecord.getPlantSystemsAssessment()) >= 0)) {
       		cpassKid = true;
    	} 
       	if(kidRecord.getManufacturingProdAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getManufacturingProdAssessment()) &&
    			(Arrays.asList(new String[]{"1","3"}).indexOf(kidRecord.getManufacturingProdAssessment()) >= 0
    			|| Arrays.asList(new String[]{"E","EM"}).indexOf(kidRecord.getManufacturingProdAssessment()) >= 0)) {
       		cpassKid = true;
    	} 
       	if(kidRecord.getDesignPreConstructionAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getDesignPreConstructionAssessment()) &&
    			(Arrays.asList(new String[]{"1","3"}).indexOf(kidRecord.getDesignPreConstructionAssessment()) >= 0
    			|| Arrays.asList(new String[]{"F","FQ"}).indexOf(kidRecord.getDesignPreConstructionAssessment()) >= 0)) {
       		cpassKid = true;
       	} 
       	if(kidRecord.getComprehensiveBusinessAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getComprehensiveBusinessAssessment()) &&
    			(Arrays.asList(new String[]{"1","3"}).indexOf(kidRecord.getComprehensiveBusinessAssessment()) >= 0
    			 || Arrays.asList(new String[]{"G","GQ"}).indexOf(kidRecord.getComprehensiveBusinessAssessment()) >= 0)) {
       		cpassKid = true;
    	}
       	if(kidRecord.getFinanceAssessment() != null && 
    			StringUtils.isNotEmpty(kidRecord.getFinanceAssessment()) &&
    			(Arrays.asList(new String[]{"1","2","3","4"}).indexOf(kidRecord.getFinanceAssessment()) >= 0
    			|| Arrays.asList(new String[]{"H","HQ","HM"}).indexOf(kidRecord.getFinanceAssessment()) >= 0)) {
       		cpassKid = true;
    	}
    	
    	return cpassKid;
    }    
	/**
     * @param testRecord {@link TestRecord}
     * @param contractingOrganizationTree {@link ContractingOrganizationTree}
     * @return {@link Enrollment}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TestRecord cascadeAddOrUpdate(TestRecord testRecord,
    		ContractingOrganizationTree contractingOrganizationTree) {
        //ayp school id is a must for enrollment.
        RecordSaveStatus enrollmentRecordSaveStatus = RecordSaveStatus.ENROLLMENT_RECORD_SAVE_BEGAN;

    	//validating if attendance school is found.Needed if saving enrollment.
    	//find the organization only from at or below the user's organization.
    	Organization attendanceSchool = contractingOrganizationTree.getUserOrganizationTree()
    			.getOrganization(testRecord.getAttendanceSchoolProgramIdentifier());
    	//easier to add the multiple organization context here.
        
        if (attendanceSchool == null) {
            testRecord.addInvalidField(FieldName.ATTENDANCE_SCHOOL + ParsingConstants.BLANK,
                    testRecord.getAttendanceSchoolProgramIdentifier(), true, InvalidTypes.NOT_FOUND);
            return testRecord;
        }
        //check for state id
        Student student = testRecord.getStudent();
    	testRecord.setStudent(student);
    	
    	GradeCourse grade = null;
    	if(testRecord.getCurrentGradeLevel() != null) {
    		GradeCourseExample example = new GradeCourseExample();
    		example.createCriteria().andAbbreviatedNameEqualTo(String.valueOf(testRecord.getCurrentGradeLevel()));
    		
    		List<GradeCourse> grades = gradeCourseService.selectByExample(example);
    		if (grades != null && !grades.isEmpty()){
    			grade = grades.get(0);
    		} else {
            	testRecord.addInvalidField(FieldName.GRADE + ParsingConstants.BLANK,
                        testRecord.getCurrentGradeLevel(),
                        false, InvalidTypes.NOT_FOUND);
            }
    	}

        if(grade!=null) {
        	testRecord.getEnrollment().setCurrentGradeLevel(grade.getId());
        }

        testRecord.setAttendanceSchoolId(attendanceSchool.getId());
        Assessment assessment = assessmentService.insertIfNotPresent(testRecord.getTestType());
        if (assessment.getSaveStatus().equals(RecordSaveStatus.ASSESSMENT_CREATED)) {
            //TODO add assessments to this student.
            testRecord.setCreated(true);
            testRecord.addInvalidField(FieldName.ASSESSMENT + ParsingConstants.BLANK,
                    testRecord.getTestSubject() + ParsingConstants.OUTER_DELIM + testRecord.getTestType(),
                    false, InvalidTypes.NOT_RECOGNIZED);
        } else if (assessment.getSaveStatus().equals(RecordSaveStatus.MULTIPLE_ASSESSMENTS_FOUND)) {
        	testRecord.addInvalidField(FieldName.ASSESSMENT + ParsingConstants.BLANK,
                    testRecord.getTestSubject() + ParsingConstants.OUTER_DELIM
                    + testRecord.getTestType(),
                    false, InvalidTypes.MULTIPLE_FOUND);
		}

        ContentArea contentArea = contentAreaService.findByAbbreviatedName(testRecord.getTestSubject());
        if (contentArea == null){
        	testRecord.addInvalidField(FieldName.TEST_SUBJECT + ParsingConstants.BLANK,
                    testRecord.getTestSubject(),
                    true, InvalidTypes.NOT_FOUND);
        	return testRecord;
        }
        student = studentService.addOrUpdate(testRecord.getStudent(), testRecord.getAttendanceSchoolId());

        if (student.getSaveStatus().equals(RecordSaveStatus.STUDENT_ADDED)) {
            testRecord.setCreated(true);
        }

        testRecord.getStudent().setId(student.getId());
        testRecord.setStudentId(testRecord.getStudent().getId());

        testRecord.getEnrollment().setStudent(testRecord.getStudent());
        testRecord.getEnrollment().setStudentId(testRecord.getStudent().getId());
        testRecord.getEnrollment().setAttendanceSchoolProgramIdentifier(
                testRecord.getAttendanceSchoolProgramIdentifier());
        testRecord.getEnrollment().setAttendanceSchoolId(
                attendanceSchool.getId());
        Enrollment enrollment = addOrUpdate(testRecord.getEnrollment());
        testRecord.setEnrollment(enrollment);
        if (enrollmentRecordSaveStatus.equals(RecordSaveStatus.ENROLLMENT_ADDED)) {
            testRecord.setCreated(true);
        }
        return testRecord;
    }

    /**
     * @param enrollmentRecord {@link Enrollment}
     * @param contractingOrganizationTree {@link ContractingOrganizationTree}
     * @return {@link Enrollment}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final EnrollmentRecord cascadeAddOrUpdate(EnrollmentRecord enrollmentRecord,
    		ContractingOrganizationTree contractingOrganizationTree, Long orgId, int contractingOrgSchoolYear) {
    	
    	boolean validationFailed = false;
        //ayp school id is a must for enrollment.
        RecordSaveStatus enrollmentRecordSaveStatus = RecordSaveStatus.ENROLLMENT_RECORD_SAVE_BEGAN;
    	
        Date currentDate = new Date();
        Date dob = enrollmentRecord.getDateOfBirth();
        if(dob != null && dob.compareTo(currentDate) > 0){
          	 enrollmentRecord.addInvalidField(FieldName.DATE_OF_BIRTH + ParsingConstants.BLANK,
                      enrollmentRecord.getDateOfBirthStr(), true, " is not valid. Date is in future.");
          	validationFailed = true;
        }
        Date schoolEntryDate = enrollmentRecord.getSchoolEntryDate();
        if(schoolEntryDate != null && schoolEntryDate.compareTo(currentDate) > 0){
          	 enrollmentRecord.addInvalidField(FieldName.SCHOOL_ENTRY_DATE + ParsingConstants.BLANK,
                      enrollmentRecord.getSchoolEntryDateStr(), true, " is not valid. Date is in future.");
           	validationFailed = true;
        }
        List<Organization> residentDistOrg = organizationService.getDistrictInState(enrollmentRecord.getResidenceDistrictIdentifier(), orgId);        
        
        if (residentDistOrg == null || residentDistOrg.isEmpty()) {
            enrollmentRecord.addInvalidField(FieldName.RESIDENT_DISTRICT_IDENTIFIER + ParsingConstants.BLANK, enrollmentRecord.getResidenceDistrictIdentifier(), true, " not found.");
          	validationFailed = true;
        }
        //validating if attendance school is found.Needed if saving enrollment.
    	//find the organization only from at or below the user's organization.
        
        Organization attendanceSchool = contractingOrganizationTree.getUserOrganizationTree()
    			.getOrganization(enrollmentRecord.getAttendanceSchoolProgramIdentifier());        
        
        if (attendanceSchool == null) {
            enrollmentRecord.addInvalidField(FieldName.ATTENDANCE_SCHOOL + ParsingConstants.BLANK, enrollmentRecord.getAttendanceSchoolProgramIdentifier(), true, " not found.");
          	validationFailed = true;
        }else{        	
        	enrollmentRecord.setAttendanceSchoolId(attendanceSchool.getId());        	
            //int contractingOrgSchoolYear = enrollmentDao.getContractingOrgSchoolYear(attendanceSchool.getId());
            int enrollmentCurrentSchoolYear = enrollmentRecord.getCurrentSchoolYear();
            if(contractingOrgSchoolYear != enrollmentCurrentSchoolYear){
            	enrollmentRecord.addInvalidField(FieldName.CURRENT_SCHOOL_YEAR + ParsingConstants.BLANK,
    					enrollmentRecord.getCurrentSchoolYear() + ParsingConstants.BLANK, true," is invalid.");
              	validationFailed = true;
            }
        }
        
        //GradeCourse grade = null;
    	if(enrollmentRecord.getCurrentGradeLevel() != null) {
    		GradeCourse grade = gradeCourseService.getIndependentGradeByAbbreviatedName(enrollmentRecord.getCurrentGradeLevel());
    		if (grade == null){
            	enrollmentRecord.addInvalidField(FieldName.GRADE + ParsingConstants.BLANK,
            			enrollmentRecord.getCurrentGradeLevel(),
                        true, InvalidTypes.NOT_FOUND);
              	validationFailed = true;
            } else {
            	enrollmentRecord.getEnrollment().setCurrentGradeLevel(grade.getId()); 
            }
    	} else {
    		enrollmentRecord.addInvalidField(FieldName.GRADE + ParsingConstants.BLANK,
        			enrollmentRecord.getCurrentGradeLevel(),
                    true, InvalidTypes.NOT_FOUND);
          	validationFailed = true;
    	}
    	
    	if(enrollmentRecord.getAttendanceSchoolId() != 0) {
			Organization attSchoolsDistrict = organizationService.getDistrictBySchoolOrgId(enrollmentRecord.getAttendanceSchoolId());
			if(attSchoolsDistrict != null && attSchoolsDistrict.getId() != null 
					&& !attSchoolsDistrict.getDisplayIdentifier().equals(enrollmentRecord.getEnrollment().getResidenceDistrictIdentifier())){
				enrollmentRecord.addInvalidField(FieldName.ATTENDANCE_SCHOOL + " is not in Attendance District",
	        			enrollmentRecord.getAttendanceSchoolProgramIdentifier(),
	                    true, InvalidTypes.MIS_MATCH);
	          	validationFailed = true;
			}
    	}

    	if(enrollmentRecord.getAypSchoolId() != 0) {
			Organization aypSchoolsDistrict = organizationService.getDistrictBySchoolOrgId(enrollmentRecord.getAypSchoolId());
			if(aypSchoolsDistrict != null && aypSchoolsDistrict.getId() != null 
					&& !aypSchoolsDistrict.getId().equals(enrollmentRecord.getEnrollment().getAccountabilityDistrictId())){
				enrollmentRecord.addInvalidField(FieldName.AYP_SCHOOL + " is not in Accountability District",
	        			enrollmentRecord.getAypSchoolIdentifier(),
	                    true, InvalidTypes.MIS_MATCH);
	          	validationFailed = true;
			}
    	}
    	
    	if(validationFailed)
    		return enrollmentRecord;
    	
        //check for state id
        Student student = enrollmentRecord.getStudent();
        student = studentService.addOrUpdate(enrollmentRecord.getStudent(),enrollmentRecord.getAttendanceSchoolId());
        
        if(student.isDoReject()) {
        	enrollmentRecord.getInValidDetails().addAll(student.getInValidDetails());
        	enrollmentRecord.setDoReject(true);
        	return enrollmentRecord;
        }

        if (student.getSaveStatus().equals(RecordSaveStatus.STUDENT_ADDED)) {
            enrollmentRecord.setCreated(true);
        }        
        
        
        studentAssessmentProgramsDao.insertMultipleAssessmentProgram(enrollmentRecord.getAssessmentProgramIds(),student.getId());
    
        enrollmentRecord.getStudent().setId(student.getId());
        enrollmentRecord.setStudentId(enrollmentRecord.getStudent().getId());

        enrollmentRecord.getEnrollment().setStudent(enrollmentRecord.getStudent());
        enrollmentRecord.getEnrollment().setStudentId(enrollmentRecord.getStudent().getId());
        enrollmentRecord.getEnrollment().setAttendanceSchoolProgramIdentifier(
                enrollmentRecord.getAttendanceSchoolProgramIdentifier());
        enrollmentRecord.getEnrollment().setAttendanceSchoolId(
                attendanceSchool.getId());
        enrollmentRecord.getEnrollment().setAypSchoolId(enrollmentRecord.getAypSchoolId());
        
        Enrollment enrollment = addOrUpdate(enrollmentRecord.getEnrollment());
        enrollmentRecordSaveStatus = enrollment.getSaveStatus();
        enrollmentRecord.setEnrollment(enrollment);
        if (enrollmentRecordSaveStatus.equals(RecordSaveStatus.ENROLLMENT_ADDED)) {
            enrollmentRecord.setCreated(true);
        }
        
        StudentJson studentJson = studentDao.getStudentjsonData(student.getId());
        if(studentJson  != null){
        	student.setAfterJsonString(studentJson.buildjsonString());
        }
        
        studentService.insertIntoDomainAuditHistory(student.getId(),student.getModifiedUser(),
        		       enrollmentRecord.isCreated()?EventTypeEnum.INSERT.getCode():EventTypeEnum.UPDATE.getCode(),SourceTypeEnum.MANUAL.getCode(), student.getBeforeJsonString(), student.getAfterJsonString());
        return enrollmentRecord;
    }

    
    /**
     * @param tecRecord {@link TecRecord}
     * @param contractingOrganizationTree {@link ContractingOrganizationTree}
     * @param user {@link User}
     * @return {@link TecRecord}
     */
    @Override
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final TecRecord cascadeAddOrUpdate(TecRecord tecRecord,
    		ContractingOrganizationTree contractingOrganizationTree, User user) {
    	KidRecord inKidRecord = convertToKidRecord(tecRecord, contractingOrganizationTree);
    	
    	TecRecord outTecRecord = null;
    	
    	if (!inKidRecord.isDoReject()){
    		if(StringUtils.isNotEmpty(tecRecord.getEnrollment().getSourceType())) {
    			inKidRecord.getEnrollment().setSourceType(tecRecord.getEnrollment().getSourceType());
    		}
    		KidRecord outKidRecord = cascadeAddOrUpdate(inKidRecord, contractingOrganizationTree, user, true);
    		outKidRecord.getEnrollment().setSourceType(tecRecord.getEnrollment().getSourceType());
    		outTecRecord = convertFromKidRecord(outKidRecord);
    	}else{
    		outTecRecord = convertFromKidRecord(inKidRecord);
    	}
    	
    	
    	return outTecRecord;
    }
    
    
    public TecRecord convertFromKidRecord(KidRecord outKidRecord) {
    	TecRecord tecRecord = new TecRecord();
    	tecRecord.setEnrollment(outKidRecord.getEnrollment());
    	tecRecord.setStudent(outKidRecord.getStudent());
    	tecRecord.setAttendanceSchool(outKidRecord.getAttendanceSchool());
    	tecRecord.setSchoolYear(outKidRecord.getCurrentSchoolYear());
    	tecRecord.setInValid(outKidRecord.isInValid());
    	tecRecord.setInValidFields(outKidRecord.getInValidFields());
    	for (InValidDetail detail : outKidRecord.getInValidDetails()){
    		tecRecord.addInvalidDetail(detail);
    	}
    	tecRecord.setDoReject(outKidRecord.isDoReject());
    	tecRecord.setRecordType(outKidRecord.getRecordType());
    	tecRecord.setTestType(outKidRecord.getTestType());
    	tecRecord.setSubject(outKidRecord.getTestSubject());
    	tecRecord.setExitDate(outKidRecord.getExitWithdrawalDate());
    	if (outKidRecord.getExitWithdrawalType() != null)
    		tecRecord.setExitReason(Integer.valueOf(outKidRecord.getExitWithdrawalType()));
    	else
    		tecRecord.setExitReason(0);
		return tecRecord;
	}

	public KidRecord convertToKidRecord(TecRecord tecRecord, ContractingOrganizationTree contractingOrganizationTree) {	
		KidRecord kidRecord = new KidRecord();
		kidRecord.setStateStudentIdentifier(tecRecord.getStateStudentIdentifier());
		kidRecord.setAttendanceSchoolProgramIdentifier(tecRecord.getAttendanceSchoolProgramIdentifier());
		kidRecord.setRecordType(tecRecord.getRecordType());
		kidRecord.setDoReject(tecRecord.isDoReject());
		kidRecord.setInValidFields(tecRecord.getInValidFields());
		kidRecord.setInValid(tecRecord.isInValid());
		for (InValidDetail detail : tecRecord.getInValidDetails()){
			kidRecord.addInvalidDetail(detail);
		}
		kidRecord.setCurrentSchoolYear(tecRecord.getSchoolYear());
		
		Organization attendanceSchool = contractingOrganizationTree.getUserOrganizationTree()
    			.getOrganization(tecRecord.getAttendanceSchoolProgramIdentifier());        
        if (attendanceSchool == null) {
            kidRecord.addInvalidField(FieldName.ATTENDANCE_SCHOOL + ParsingConstants.BLANK,
            		kidRecord.getAttendanceSchoolProgramIdentifier(), true, " is not found.");
            return kidRecord;
        }
        
        String timeZone = organizationDao.getTimeZoneForOrg(attendanceSchool.getId());
        if(StringUtils.isEmpty(timeZone)){
        	timeZone = "US/Central";
        }
        
		if ("Exit".equalsIgnoreCase(tecRecord.getRecordType())){
			kidRecord.setExitWithdrawalType(Integer.toString(tecRecord.getExitReason()));
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			if(tecRecord.getExitDate() != null) {
				kidRecord.setExitWithdrawalDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(
	        			sdf.format(tecRecord.getExitDate()), timeZone,  "MM/dd/yyyy")
	    						.getTime()));
				tecRecord.setExitDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(
	        			sdf.format(tecRecord.getExitDate()), timeZone,  "MM/dd/yyyy")
	    						.getTime()));
	        }			
		}else if ("Test".equalsIgnoreCase(tecRecord.getRecordType())){
		
			if("M".equalsIgnoreCase(tecRecord.getSubject())){
				
				TestType validTestType = findValidTestType(tecRecord.getTestType(), tecRecord.getSubject());
				if (validTestType == null){
		        	kidRecord.addInvalidField("Test Type"
		        			, tecRecord.getTestType()
		        			, true
		        			, " is not found as a valid test type for subject "+tecRecord.getSubject());
		        	kidRecord.setDoReject(true);
		        	return kidRecord;
				}
				kidRecord.setStateMathAssess(tecRecord.getTestType());				
			}else if("ELA".equalsIgnoreCase(tecRecord.getSubject())){
				
				TestType validTestType = findValidTestType(tecRecord.getTestType(), tecRecord.getSubject());
				if (validTestType == null){
		        	kidRecord.addInvalidField("Test Type"
		        			, tecRecord.getTestType()
		        			, true
		        			, " is not found as a valid test type for subject "+tecRecord.getSubject());
		        	kidRecord.setDoReject(true);
		        	return kidRecord;
				}
				kidRecord.setStateELAAssessment(tecRecord.getTestType());				
			} else if ("Sci".equalsIgnoreCase(tecRecord.getSubject())){
				TestType validTestType = findValidTestType(tecRecord.getTestType(), tecRecord.getSubject());
				if (validTestType == null){
		        	kidRecord.addInvalidField("Test Type"
		        			, tecRecord.getTestType()
		        			, true
		        			, " is not found as a valid test type for subject "+tecRecord.getSubject());
		        	kidRecord.setDoReject(true);
		        	return kidRecord;
				}
				kidRecord.setStateSciAssessment(tecRecord.getTestType());
			} else if ("SS".equalsIgnoreCase(tecRecord.getSubject())){
				TestType validTestType = findValidTestType(tecRecord.getTestType(), tecRecord.getSubject());
				if (validTestType == null){
		        	kidRecord.addInvalidField("Test Type"
		        			, tecRecord.getTestType()
		        			, true
		        			, " is not found as a valid test type for subject "+tecRecord.getSubject());
		        	kidRecord.setDoReject(true);
		        	return kidRecord;
				}
				kidRecord.setStateHistGovAssessment(tecRecord.getTestType());
			}else if("GKS".equalsIgnoreCase(tecRecord.getSubject())){
				TestType validTestType = findValidTestType(tecRecord.getTestType(), tecRecord.getSubject());
				if (validTestType == null){
		        	kidRecord.addInvalidField("Test Type"
		        			, tecRecord.getTestType()
		        			, true
		        			, " is not found as a valid test type for subject "+tecRecord.getSubject());
		        	kidRecord.setDoReject(true);
		        	return kidRecord;
				}
				kidRecord.setGeneralCTEAssessment(tecRecord.getTestType());	
			}else if("AgF&NR".equalsIgnoreCase(tecRecord.getSubject()) &&
	    			Arrays.asList(new String[]{"A","AM","AQ"}).indexOf(tecRecord.getTestType()) >= 0) {
				TestType validTestType = findValidTestType(tecRecord.getTestType(), tecRecord.getSubject());
				if (validTestType == null){
		        	kidRecord.addInvalidField("Test Type"
		        			, tecRecord.getTestType()
		        			, true
		        			, " is not found as a valid test type for subject "+tecRecord.getSubject());
		        	kidRecord.setDoReject(true);
		        	return kidRecord;
				}				
				kidRecord.setComprehensiveAgAssessment(tecRecord.getTestType());
			}else if("AgF&NR".equalsIgnoreCase(tecRecord.getSubject()) &&
					Arrays.asList(new String[]{"B","BQ"}).indexOf(tecRecord.getTestType()) >= 0) {
				TestType validTestType = findValidTestType(tecRecord.getTestType(), tecRecord.getSubject());
				if (validTestType == null){
		        	kidRecord.addInvalidField("Test Type"
		        			, tecRecord.getTestType()
		        			, true
		        			, " is not found as a valid test type for subject "+tecRecord.getSubject());
		        	kidRecord.setDoReject(true);
		        	return kidRecord;
				}				
				kidRecord.setAnimalSystemsAssessment(tecRecord.getTestType());
			}else if("AgF&NR".equalsIgnoreCase(tecRecord.getSubject()) &&
					Arrays.asList(new String[]{"D","DQ","DM"}).indexOf(tecRecord.getTestType()) >= 0) {
				TestType validTestType = findValidTestType(tecRecord.getTestType(), tecRecord.getSubject());
				if (validTestType == null){
		        	kidRecord.addInvalidField("Test Type"
		        			, tecRecord.getTestType()
		        			, true
		        			, " is not found as a valid test type for subject "+tecRecord.getSubject());
		        	kidRecord.setDoReject(true);
		        	return kidRecord;
				}				
				kidRecord.setPlantSystemsAssessment(tecRecord.getTestType());
			}else if("Mfg".equalsIgnoreCase(tecRecord.getSubject()) 
					&& Arrays.asList(new String[]{"E","EM"}).indexOf(tecRecord.getTestType()) >= 0){	
				TestType validTestType = findValidTestType(tecRecord.getTestType(), tecRecord.getSubject());
				if (validTestType == null){
		        	kidRecord.addInvalidField("Test Type"
		        			, tecRecord.getTestType()
		        			, true
		        			, " is not found as a valid test type for subject "+tecRecord.getSubject());
		        	kidRecord.setDoReject(true);
		        	return kidRecord;
				}				
				kidRecord.setManufacturingProdAssessment(tecRecord.getTestType());
			}else if("Arch&Const".equalsIgnoreCase(tecRecord.getSubject())
					&& Arrays.asList(new String[]{"F","FQ"}).indexOf(tecRecord.getTestType()) >= 0) {
				TestType validTestType = findValidTestType(tecRecord.getTestType(), tecRecord.getSubject());
				if (validTestType == null){
		        	kidRecord.addInvalidField("Test Type"
		        			, tecRecord.getTestType()
		        			, true
		        			, " is not found as a valid test type for subject "+tecRecord.getSubject());
		        	kidRecord.setDoReject(true);
		        	return kidRecord;
				}				
				kidRecord.setDesignPreConstructionAssessment(tecRecord.getTestType());
			}else if("BM&A".equalsIgnoreCase(tecRecord.getSubject())
					&& Arrays.asList(new String[]{"G","GQ"}).indexOf(tecRecord.getTestType()) >= 0) {
				TestType validTestType = findValidTestType(tecRecord.getTestType(), tecRecord.getSubject());
				if (validTestType == null){
		        	kidRecord.addInvalidField("Test Type"
		        			, tecRecord.getTestType()
		        			, true
		        			, " is not found as a valid test type for subject "+tecRecord.getSubject());
		        	kidRecord.setDoReject(true);
		        	return kidRecord;
				}				
				kidRecord.setComprehensiveBusinessAssessment(tecRecord.getTestType());
			}else if("BM&A".equalsIgnoreCase(tecRecord.getSubject())
					&& Arrays.asList(new String[]{"H","HQ","HM"}).indexOf(tecRecord.getTestType()) >= 0) {
				TestType validTestType = findValidTestType(tecRecord.getTestType(), tecRecord.getSubject());
				if (validTestType == null){
		        	kidRecord.addInvalidField("Test Type"
		        			, tecRecord.getTestType()
		        			, true
		        			, " is not found as a valid test type for subject "+tecRecord.getSubject());
		        	kidRecord.setDoReject(true);
		        	return kidRecord;
				}				
				kidRecord.setFinanceAssessment(tecRecord.getTestType());
			}
		}else if ("Clear".equalsIgnoreCase(tecRecord.getRecordType())){
			if("M".equalsIgnoreCase(tecRecord.getSubject())){				
				kidRecord.setStateMathAssess("C");				
			}else if("ELA".equalsIgnoreCase(tecRecord.getSubject())){				
				kidRecord.setStateELAAssessment("C");			
			}else if("GKS".equalsIgnoreCase(tecRecord.getSubject())){				
				kidRecord.setGeneralCTEAssessment("C");		
			} else if("AgF&NR".equalsIgnoreCase(tecRecord.getSubject())) { //FIXME: TEC clear 
				kidRecord.setAnimalSystemsAssessment("C"); 
				kidRecord.setPlantSystemsAssessment("C"); 
				kidRecord.setComprehensiveAgAssessment("C"); 
			} else if("BM&A".equalsIgnoreCase(tecRecord.getSubject())) { //FIXME: TEC clear 
				kidRecord.setComprehensiveBusinessAssessment("C"); 
				kidRecord.setFinanceAssessment("C"); 
			} else if("Arch&Const".equalsIgnoreCase(tecRecord.getSubject())) { //FIXME: TEC clear 
				kidRecord.setDesignPreConstructionAssessment("C"); 
			} else if("Mfg".equalsIgnoreCase(tecRecord.getSubject())){	//FIXME: TEC clear 	
				kidRecord.setManufacturingProdAssessment("C"); 
			} else if("Sci".equalsIgnoreCase(tecRecord.getSubject())){
				kidRecord.setStateSciAssessment("C");
			} else if("SS".equalsIgnoreCase(tecRecord.getSubject())){
				kidRecord.setStateHistGovAssessment("C");
			}
		}
		
		kidRecord.setTestType(tecRecord.getTestType());
		kidRecord.setTestSubject(tecRecord.getSubject());		
	    

        int enrollmentCurrentSchoolYear = kidRecord.getCurrentSchoolYear();
        StudentExample example = new StudentExample();
        example.createCriteria().andStatestudentidentifierEqualTo(tecRecord.getStateStudentIdentifier()).andAttendanceSchoolIdEqualTo(attendanceSchool.getId());
        List<Student> students = studentService.getByCriteria(example);
        Student student = null;
        if (students != null && !students.isEmpty())
        	student = students.get(0);
        //Student student = studentService.getByStateStudentIdentifier(tecRecord.getStudent(), attendanceSchool.getId());
        
        if (student != null && student.getId() != null){
	        if(student.isDoReject()) {
	        	InValidDetail detail = new InValidDetail("State Student Identifier", student.getStateStudentIdentifier(), true, InvalidTypes.NOT_FOUND);
	        	kidRecord.getInValidDetails().add(detail);
	        	kidRecord.setDoReject(true);
	        	return kidRecord;
	        }
	        boolean activeFlag = true;
	        if(tecRecord.getRecordType().equalsIgnoreCase("Exit")
	        		&& tecRecord.getExitReason() == 99){
	        	//if it is an exit undo, then we are looking for an inactive enrollment
	        	activeFlag = false;
	        }
	        Enrollment enrollment = enrollmentDao.getBySSIDYearAndSchool(student.getStateStudentIdentifier(), enrollmentCurrentSchoolYear, attendanceSchool.getDisplayIdentifier(), activeFlag);
	        if (enrollment == null){
	        	if (activeFlag){
	        	kidRecord.addInvalidField("State Student Identifier, School Year, and Attendance School Program Identifier"
	        			, student.getStateStudentIdentifier()+", "+enrollmentCurrentSchoolYear+", and "+attendanceSchool.getDisplayIdentifier()
	        			, true
	        			, InvalidTypes.NOT_FOUND
	        			, " did not match an existing record in the state.  Please process the enrollment record first.");
	        	kidRecord.setDoReject(true);
	        	}else{
		        	kidRecord.addInvalidField("Exit Reason"
		        			, kidRecord.getExitWithdrawalType()
		        			, true
		        			, InvalidTypes.IN_VALID
		        			, " is invalid as no prior exit has been recieved.");
		        	kidRecord.setDoReject(true);
	        	}
	        	return kidRecord;
	        }else{
	        	if ("Exit".equalsIgnoreCase(tecRecord.getRecordType())){
	        		SimpleDateFormat df = new SimpleDateFormat("M/d/yyyy");
	        		//DE17904 - Added fix to compare dates in same time zone
	        		Date schEntryDate = new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(
		        			df.format(enrollment.getSchoolEntryDate()), timeZone,  "MM/dd/yyyy")
		    						.getTime());
		        	if (enrollment.getSchoolEntryDate() != null && tecRecord.getExitDate().before(schEntryDate)){
			        	kidRecord.addInvalidField("Exit Date"
			        			, df.format(tecRecord.getExitDate())
			        			, true
			        			, " is before the School Entry Date.");
			        	kidRecord.setDoReject(true);
			        	return kidRecord;
		        	}
	        		enrollment.setExitWithdrawalDate(tecRecord.getExitDate());
	        		enrollment.setExitWithdrawalType(tecRecord.getExitReason());
	        	}
	        	kidRecord.setEnrollment(enrollment);
	        	kidRecord.setStudent(student);
	        }
        }else{
        	kidRecord.addInvalidField("State Student Identifier, Attendance School", 
        			tecRecord.getStateStudentIdentifier() + " and " + tecRecord.getAttendanceSchoolProgramIdentifier(), 
        			true, 
        			InvalidTypes.NOT_FOUND, 
        			" did not match an existing record in the state.  Please process the enrollment record first.");
        	kidRecord.setDoReject(true);
        }
		return kidRecord;
	}
	
	private TestType findValidTestType(String testTypeCode, String subjectCode){
		TestType tt = null;
		if(StringUtils.isNotEmpty(testTypeCode) && StringUtils.isNotEmpty(subjectCode)) {
			List<TestType> testTypes = enrollmentTestTypeSubjectAreaDao.selectValidTestTypes(subjectCode);
			for (TestType testType : testTypes){
				if (testTypeCode.equalsIgnoreCase(testType.getTestTypeCode())){
					tt = testType;
					break;
				}
			}
			if (tt == null){
				return null;
			}
		}
			return tt;
	}

    /**
     * @param stateStudentIdentifier {@link String}
     * @return {@link Enrollment}
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Enrollment> get(final String stateStudentIdentifier) {
        return enrollmentDao.get(stateStudentIdentifier);
    }

    /**
     * @param enrollment {@link EnrollmentExample}
     * @return {@link List}
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<Enrollment> getByCriteria(EnrollmentExample enrollment) {
        return enrollmentDao.getByCriteria(enrollment);
    }
    
    /**
     * 
     * @param id
     * @return
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Enrollment getById(Long id) {
        return enrollmentDao.getById(id);
    }
    
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<? extends StudentSchoolRelationInformation> verifyEnrollments(
			List<? extends StudentSchoolRelationInformation> studentSchoolRelations, Collection<Long> UserOrganizationIds) {
		boolean validInput = true;
		List<StudentSchoolRelationDataObject> foundSchoolRelationObjects = null;
		if(studentSchoolRelations == null || CollectionUtils.isEmpty(studentSchoolRelations)) {
			//TODO write an aspect to do the null checks or use the existing aspect.
			validInput = false;
		}
		if(validInput) {
			foundSchoolRelationObjects= enrollmentDao.getByStudentSchoolRelation(studentSchoolRelations, UserOrganizationIds);			
			if (validInput) {
				for (StudentSchoolRelationDataObject foundSchoolRelationObject : foundSchoolRelationObjects) {
					for (StudentSchoolRelationInformation studentSchoolRelation : studentSchoolRelations) {
						studentSchoolRelation = foundSchoolRelationObject.setRelation(studentSchoolRelation);
					}
				}
				for (StudentSchoolRelationInformation studentSchoolRelation : studentSchoolRelations) {
					if(!studentSchoolRelation.isFound()) {
						studentSchoolRelation.addInvalidField(
								FieldName.ENROLLMENT + ParsingConstants.BLANK,
								studentSchoolRelation
								.getResponsibleSchoolIdentifier()
								+ ParsingConstants.OUTER_DELIM
								+ studentSchoolRelation
								.getStateStudentIdentifier(),
								true, InvalidTypes.NOT_FOUND);
					}					
				}
			}
		}
		return studentSchoolRelations;
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentRoster> getEnrollmentWithRoster(
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		String sortByColumn, String sortType, Integer offset,
    		Integer limitCount,	Map<String,String> studentRosterCriteriaMap,
    		Long testSessionId, Long schoolOrgId, Long contentAreaId, int currentSchoolYear) {
		
		//TODO sort type	    
	    List<StudentRoster> studentRosters = enrollmentDao.getEnrollmentWithRoster(
	    		userDetails.getUser().getCurrentOrganizationId(),
				userDetails.getUserId(),
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
						sortByColumn, sortType, offset,
				limitCount, studentRosterCriteriaMap,testSessionId,schoolOrgId,contentAreaId, currentSchoolYear);
	    return studentRosters;
    
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentRoster> getEnrollmentsForDLMStudents(
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		String sortByColumn, String sortType, Integer offset,
    		Integer limitCount,	Map<String,String> studentRosterCriteriaMap,
    		Long testSessionId, Long schoolOrgId, Long contentAreaId, int currentSchoolYear) {
		
		//TODO sort type	    
	    List<StudentRoster> studentRosters = enrollmentDao.DLMStudentReport_Students_Column_List(
	    		userDetails.getUser().getCurrentOrganizationId(),
				userDetails.getUserId(),
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
						sortByColumn, sortType, offset,
				limitCount, studentRosterCriteriaMap,testSessionId,schoolOrgId,contentAreaId, currentSchoolYear);
	    return studentRosters;
    
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.EnrollmentService#countEnrollmentWithRoster(java.util.List, 
	 * java.util.List, edu.ku.cete.domain.user.UserDetailImpl, boolean, java.util.Map)
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int countEnrollmentWithRoster(
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		Map<String,String> studentRosterCriteriaMap,
    		Long testSessionId, Long schoolOrgId, Long contentAreaId, int currentSchoolYear) {
		Integer totalCount = null;
		//DE6522 remove the student limit of 1000 students on setup test session
		totalCount = enrollmentDao.countEnrollmentWithRoster(
			userDetails.getUser().getCurrentOrganizationId(),
			userDetails.getUserId(),
			permissionUtil.hasPermission(userDetails.getAuthorities(),
					RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
			studentRosterCriteriaMap,
			testSessionId, schoolOrgId, contentAreaId, currentSchoolYear);

	    return totalCount;
    
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.EnrollmentService#getAutoRegisteredTSStudents(java.util.List, 
	 * java.util.List, edu.ku.cete.domain.user.UserDetailImpl, java.lang.String, 
	 * java.lang.Integer, java.lang.Integer, java.util.Map, java.lang.Long)
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<AutoRegisteredStudentsDTO> getAutoRegisteredTSStudents(
    		List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
    		UserDetailImpl userDetails,
    		String orderByClause, Integer offset,
    		Integer limitCount,	Map<String,String> studentRosterCriteriaMap,Long testSessionId,
    		String assessmentProgramAbbr, Long organizationId) {
		
	    List<AutoRegisteredStudentsDTO> autoRegisteredStudents = enrollmentDao.getAutoRegisteredTSStudents(
	    		attendanceSchoolIds,
				userOrganizationIds,
				userDetails.getUserId(), userDetails.getUser().isTeacher(),
				orderByClause, offset,
				limitCount, studentRosterCriteriaMap,testSessionId, assessmentProgramAbbr, 
				userDetails.getUser().getContractingOrganization().getCurrentSchoolYear(), organizationId);
	    return autoRegisteredStudents;
    
	}
	
	/* (non-Javadoc)
	 * @see edu.ku.cete.service.EnrollmentService#countAutoRegisteredTSStudents(java.util.List, 
	 * java.util.List, edu.ku.cete.domain.user.UserDetailImpl, java.util.Map, java.lang.Long)
	 */
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer countAutoRegisteredTSStudents(
    		List<Long> attendanceSchoolIds, List<Long> userOrganizationIds,
    		UserDetailImpl userDetails,
    		Map<String,String> studentRosterCriteriaMap,
    		Long testSessionId) {
		
		Integer totalCount = enrollmentDao.countAutoRegisteredTSStudents(
			attendanceSchoolIds,
			userOrganizationIds,
			userDetails.getUserId(), userDetails.getUser().isTeacher(),
			studentRosterCriteriaMap,
			testSessionId);
		
	    return totalCount;    
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getEnrollmentsForBatchRegistration(Long testTypeId, Long contentAreaId, Long gradeCourseId, Long schoolId, Long assessmentId, Long currentSchoolYear, Integer offset, Integer pageSize) {
		return enrollmentDao.findEnrollmentsForBatchRegistration(testTypeId, contentAreaId, gradeCourseId, schoolId, assessmentId, currentSchoolYear, false, offset, pageSize, false, null);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getEnrollmentsForBatchRegistrationOnlyDLM(Long testTypeId, Long contentAreaId, Long gradeCourseId, Long schoolId, Long assessmentId, Long currentSchoolYear, Integer offset, Integer pageSize) {
		return enrollmentDao.findEnrollmentsForBatchRegistration(testTypeId, contentAreaId, gradeCourseId, schoolId, assessmentId, currentSchoolYear, true, offset, pageSize, false, null);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getEnrollmentsForKapAdaptiveRegistration(Long testTypeId, Long contentAreaId, Long gradeCourseId, Long schoolId, Long assessmentId, Long currentSchoolYear, Integer offset, Integer pageSize, Boolean isInterim, Date jobLastSubmissionDate, Long assessmentProgramId) {
		return enrollmentDao.findEnrollmentsForKAPAdaptiveBatchRegistration(testTypeId, contentAreaId, gradeCourseId, schoolId, assessmentId, currentSchoolYear, offset, pageSize,isInterim, jobLastSubmissionDate, assessmentProgramId);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getEnrollmentsForKapPredictiveRegistration(Map<String, Object> params) {
		return enrollmentDao.findEnrollmentsForPredictiveBatchRegistration(params);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getMultiAssignEnrollments(Long contentAreaId, String contentAreaCode, String testEnrollmentMethod, Long operationalTestWindowId, Long schoolId, Long currentSchoolYear,
			Integer multiAssignLimit, boolean isInterim, Integer offset, Integer pageSize) {
		return enrollmentDao.findMultiAssignEnrollments(contentAreaId, contentAreaCode, testEnrollmentMethod, operationalTestWindowId, schoolId, currentSchoolYear, multiAssignLimit, isInterim, offset, pageSize);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> findEnrollmentsForBatchRegistrationKSBreakDay(Long testTypeId, Long subjectAreaId, Long gradeCourseId, Long contractingOrgId){
		return enrollmentDao.findEnrollmentsForBatchRegistrationKSBreakDay(testTypeId, subjectAreaId, gradeCourseId, contractingOrgId);
	}	
	
    /* (non-Javadoc)
     * @see edu.ku.cete.service.EnrollmentService#getStudentsByOrgId(
     * java.lang.Long, java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final  List<Enrollment> getStudentsByOrgId(Long organizationId, String orderByColumn, 
    		Integer offset, Integer limitCount, int currentSchoolYear) {
    	Map<String,Object> criteria = new HashMap<String,Object>();
    	criteria.put("organiztionId",organizationId);
    	criteria.put("limit", limitCount);
		criteria.put("offset", offset);
		criteria.put("orderByColumn", orderByColumn);
		criteria.put("order", "asc");
        return enrollmentDao.getStudentsByOrgId(criteria, currentSchoolYear);
    }
    
    /* (non-Javadoc)
     * @see edu.ku.cete.service.EnrollmentService#getStudentsByOrgId(
     * java.lang.Long, java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final  List<Enrollment> getStudentsByOrgId(Map<String,Object> criteria, String orderByColumn, 
    		String order,Integer offset, Integer limitCount, int currentSchoolYear) {
    	
    	criteria.put("limit", limitCount);
		criteria.put("offset", offset);
		criteria.put("orderByColumn", orderByColumn);
		criteria.put("order", order);
		
        return enrollmentDao.getStudentsByOrgId(criteria, currentSchoolYear);
    }
    /* (non-Javadoc)
     * @see edu.ku.cete.service.EnrollmentService#getStudentsByOrgId(
     * java.lang.Long, java.lang.String, java.lang.Integer, java.lang.Integer)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final  List<Enrollment> getCurrentYearStudentsByOrgId(Long organizationId, String orderByClause, 
    		Integer offset, Integer limitCount) {
        return enrollmentDao.getCurrentYearStudentsByOrgId(organizationId, orderByClause, offset, limitCount);
    }
    /* (non-Javadoc)
     * @see edu.ku.cete.service.EnrollmentService#countStudentsByOrgId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Integer countStudentsByOrgId(Map<String,Object> criteria, int currentSchoolYear) {
        return enrollmentDao.countStudentsByOrgId(criteria, currentSchoolYear);
    }
	
    /* (non-Javadoc)
     * @see edu.ku.cete.service.EnrollmentService#countStudentsByOrgId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Integer countCurrentYearStudentsByOrgId(Long organizationId) {
        return enrollmentDao.countCurrentYearStudentsByOrgId(organizationId);
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<Enrollment> getStudentEnrollmentsByRosterId(Map<String,Object> criteria){
    	return enrollmentDao.getStudentEnrollmentsByRosterId(criteria);
    }

    /* (non-Javadoc)
     * @see edu.ku.cete.service.EnrollmentService#countStudentEnrollmentsByRosterId(java.lang.Long)
     */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Integer countStudentEnrollmentsByRosterId(Map<String,Object> criteria) {
        return enrollmentDao.countStudentEnrollmentsByRosterId(criteria);
    }
    
    /**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15500 : DLM Class Roster Report - online report
	 * Get all students per roster id, these will be populated on roster selection.
	 */
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public List<Student> getStudentsByRosterId(Long rosterId){
    	return enrollmentDao.getStudentsByRosterId(rosterId);
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Enrollment getByEnrollmentId(Long enrollmentId) {
    	return enrollmentDao.getById(enrollmentId);
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public Enrollment getAttendanceSchoolAndDistrictByEnrollmentId(Long enrollmentId){
    	return enrollmentDao.getAttendanceSchoolAndDistrictByEnrollmentId(enrollmentId);
    }
    
    @Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> allEnrollmentsWithSubjectDetailsForStudent(Long studentId, Long currentSchoolYear, Long subjectId){
		return enrollmentDao.allEnrollmentsWithSubjectDetailsForStudent(studentId, currentSchoolYear, subjectId);
	}
	
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final Boolean isTeacher(Long educatorId) {
    	if(educatorId != null && educatorId.longValue() > 0) {
    		int noOfRostersWithTeacherId = enrollmentDao.countRostersByTeacherId(educatorId);
    		return (noOfRostersWithTeacherId > 0 ? true : false);
    	}
    	return false;
    }
    
    @Override
    @Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public final List<String> getStudentNamesEnrolledInTestSession(Long testSessionId){
    	return enrollmentDao.getStudentNamesEnrolledInTestSession(testSessionId);
    }

	@Override
	public Integer getContractingOrgSchoolYear(Long organizationId) {
		return enrollmentDao.getContractingOrgSchoolYear(organizationId);
	}

	@Override
	public List<Long> getNoEnrollmentOrganizations(Long testTypeId, Long contentAreaId, Long gradeCourseId, Long schoolId,
			Long assessmentId, Long currentSchoolYear) {
		return enrollmentDao.findNoEnrollmentOrganizations(testTypeId, contentAreaId, gradeCourseId, 
				schoolId, assessmentId, currentSchoolYear);
	}
	
	/**
	 * Biyatpragyan Mohanty : bmohanty_sta@ku.edu : US15563 : Test Management - Display Test Info pdf for student
     * Added logic to receive the test resources/media i.e. pdf files associated to the test for selected students.
	 */
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Set<StudentTestResourceInfo> getResourceByStudentIdTestSessionId(Long testSessionId,
			Long currentSchoolYear) {
		Map<Long, Set<StudentTestResourceInfo>> smap = new HashMap<Long, Set<StudentTestResourceInfo>>();
		List<Long> studentIds = testSessionService.getStudentsIdsByIncompleteTestSessionId(testSessionId,
				currentSchoolYear);
		if (studentIds != null && studentIds.size() > 0) {
			List<StudentTestResourceInfo> sinfolist = enrollmentDao
					.selectResourceByStudentIdTestSessionId(testSessionId, studentIds);
			if (sinfolist != null && sinfolist.size() > 0) {
				for (StudentTestResourceInfo sinfo : sinfolist) {

					List<String> itemAttributeList = new ArrayList<String>();
					itemAttributeList.add("Braille");

					List<StudentProfileItemAttributeDTO> pnpAttributes = studentProfileService
							.getStudentProfileItemAttribute(sinfo.getStudentId(), itemAttributeList);
					boolean braille = false;

					for (StudentProfileItemAttributeDTO pnpAttribute : pnpAttributes) {
						if (pnpAttribute.getAttributeName().equalsIgnoreCase("Braille")) {
							braille = pnpAttribute.getSelectedValue() != null
									&& pnpAttribute.getSelectedValue().equalsIgnoreCase("true");
						}
					}
					if (sinfo.getFileType().equalsIgnoreCase("UCB") || sinfo.getFileType().equalsIgnoreCase("UEB")
							|| sinfo.getFileType().equalsIgnoreCase("EBAE")) {
						if (!braille)
							break;
					}
					if (smap.get(testSessionId) == null) {
						Set<StudentTestResourceInfo> rlist = new HashSet<StudentTestResourceInfo>();
						rlist.add(sinfo);
						smap.put(testSessionId, rlist);
					} else {
						Set<StudentTestResourceInfo> rlist = smap.get(testSessionId);
						rlist.add(sinfo);
						smap.put(testSessionId, rlist);
					}
				}
			}
		}
		return smap.get(testSessionId);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Enrollment getEnrollmentByStudentId(Long studentId, Long contractingOrganizationId, Long currentSchoolYear, Long contentAreaId) {
		return enrollmentDao.getEnrollmentByStudentId(studentId, contractingOrganizationId, currentSchoolYear, contentAreaId);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getEnrollmentsByStudentId(Long studentId, Long currentSchoolYear){
		return enrollmentDao.getEnrollmentsByStudentId(studentId, currentSchoolYear);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getEnrollmentBySSIDAndStateId(String ssid, Long stateId, int currentSchoolYear) {
		return enrollmentDao.getBySsidAndState(ssid, stateId, currentSchoolYear);
	}

	@Override
	public int countEnrollmentsTestTypeSubjectArea(Long id, Long assessmentId, Long subjectAreaId, String testTypeCode) {
		return enrollmentDao.countEnrollmentsTestTypeSubjectArea(id, assessmentId, subjectAreaId, testTypeCode);
	}
	
	  /**
	 * US16352: enrollment upload using spring batch
     * @param userDetails {@link UserDetailImpl}
     * @return {@link Restriction}
     */
    public Restriction getEnrollmentRestriction(UserDetailImpl userDetails) {
        //Find the restriction for what the user is trying to do on this page.
        Restriction restriction = resourceRestrictionService.getResourceRestriction(
                userDetails.getUser().getOrganization().getId(),
                permissionUtil.getAuthorityId(
                        userDetails.getUser().getAuthoritiesList(),
                        RestrictedResourceConfiguration.getUploadEnrollmentPermissionCode()),
                        null,
                restrictedResourceConfiguration.getEnrollmentResourceCategory().getId());
        return restriction;
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void processExit(String attendanceSchoolDisplayIdentifiers, ContractingOrganizationTree contractingOrganizationTree, String stateStudentIdentifier, String exitReason, Date exitDate, int currentSchoolYear, User user,long selectedOrgId) {   
    	KidRecord kidRecord = new KidRecord();
    	kidRecord.getEnrollment().setSourceType(SourceTypeEnum.STUDENT_EXIT_MANUAL.getCode());
		kidRecord.setExitWithdrawalType(exitReason);
		kidRecord.setExitWithdrawalDate(exitDate);
		kidRecord.setAttendanceSchoolProgramIdentifier(attendanceSchoolDisplayIdentifiers);
		
		/*Organization attendanceSchool = contractingOrganizationTree.getUserOrganizationTree()
    			.getOrganization(kidRecord.getAttendanceSchoolProgramIdentifier());

        Organization studentState = organizationService.getContractingOrganization(attendanceSchool.getId());*/
		

		List<Enrollment> enrollments = enrollmentDao.getBySsidAndOrgId(stateStudentIdentifier,selectedOrgId,currentSchoolYear);
		for (Enrollment e : enrollments){
			processExitRecord(kidRecord, user, e);
		}
    }
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentRoster> getEnrollmentWithRosterAssessmentProgram(
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		String sortByColumn, String sortType, Integer offset,
    		Integer limitCount,	Map<String,String> studentRosterCriteriaMap,
    		Long testSessionId, Long schoolOrgId, Long contentAreaId, int currentSchoolYear, Long assessmentProgramId) {
		
		//TODO sort type	    
	    List<StudentRoster> studentRosters = enrollmentDao.getEnrollmentWithRoster(
	    		userDetails.getUser().getCurrentOrganizationId(),
				userDetails.getUserId(),
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
						sortByColumn, sortType, offset,
				limitCount, studentRosterCriteriaMap,testSessionId,schoolOrgId,contentAreaId, currentSchoolYear, assessmentProgramId);
	    return studentRosters;
    
	}
    
    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int countEnrollmentWithRosterAssessmentProgram(
    		UserDetailImpl userDetails, boolean hasViewAllPermission,
    		Map<String,String> studentRosterCriteriaMap,
    		Long testSessionId, Long schoolOrgId, Long contentAreaId, int currentSchoolYear, Long assessmentProgramId) {
		Integer totalCount = null;
		//DE6522 remove the student limit of 1000 students on setup test session
		//if(userOrganizationIds.size() < simplifiedLimitOrgSize) {
			totalCount = enrollmentDao.countEnrollmentWithRoster(
				userDetails.getUser().getCurrentOrganizationId(),
				userDetails.getUserId(),
				permissionUtil.hasPermission(userDetails.getAuthorities(),
						RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
				studentRosterCriteriaMap,
				testSessionId, schoolOrgId, contentAreaId, currentSchoolYear, assessmentProgramId);
		/*} else {
			//If too many Orgs then use this simplified query to get count by limiting the
			  // records to 10000.
			totalCount = enrollmentDao.simplifiedCountEnrollmentWithRoster(
					attendanceSchoolIds,
					userOrganizationIds,
					userDetails.getUserId(),
					permissionUtil.hasPermission(userDetails.getAuthorities(),
							RestrictedResourceConfiguration.getViewAllRostersPermissionCode()),
					studentRosterCriteriaMap,
					testSessionId, simplifiedLimit);
		}*/
	    return totalCount;
    
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getEnrollmentsForStudent(Long studentId,
			Long testTypeId, Long contentAreaId, Long gradeCourseId,
			Long contractingOrgId, Long assessmentId, Long currentSchoolYear) {
		return enrollmentDao.findByStudentTestTypeContentAreaGradeCourseAssessmentInOrg(studentId, testTypeId, contentAreaId,
				gradeCourseId, contractingOrgId, assessmentId, currentSchoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentRoster> getDlmFixedAssignEnrollments(Long contractingOrgId,Long assessmentProgramId,Long currentSchoolYear, 
			Long contentAreaId, Long courseId, String gradeAbbrName, Integer offset, Integer pageSize) {		
		return enrollmentDao.getDlmFixedAssignEnrollments(contractingOrgId, assessmentProgramId, currentSchoolYear,contentAreaId, courseId, gradeAbbrName, offset, pageSize);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getCPASSEnrollmentsForBatchRegistration(
			Long testTypeId, List<Long> orgIds,
			Long assessmentId, Long schoolYear, Integer offset, Integer pageSize) {
		return enrollmentDao.findCPASSEnrollmentsForBatchRegistration(testTypeId, orgIds, assessmentId, schoolYear, false, offset, pageSize, false);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> findOtherHighStakeEnrollments(Long studentId, List<Long> enrollmentsToExclude,
			Long currentSchoolYear, String assessmentProgramCode,
			String contentAreaCode, Long contractingOrgId) {
		return enrollmentDao.findOtherHighStakesEnrollments(studentId, enrollmentsToExclude, currentSchoolYear, assessmentProgramCode, contentAreaCode, contractingOrgId);
	}
    
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> findReportEnrollments(Long studentId, Long contractOrgId, 
			Long currentSchoolYear, List<Long> rawScaleExternalTestIds,
			List<Long> testsStatusIds) {
		return enrollmentDao.getReportEnrollments(studentId, contractOrgId, currentSchoolYear, rawScaleExternalTestIds, testsStatusIds);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<FindEnrollments> findStudentAvilbility(String studentStateId,
			String studentFirstName, String studentlastName, Long orgId,
			Integer currentSchoolYear, Integer offset, Integer limitcount,
			Map<String, String> studentInformationRecordCriteriaMap,String sortByColumn,String sortType) {
		// TODO Auto-generated method stub
		return enrollmentDao.findStudentAvilbility(studentStateId, studentFirstName, studentlastName, orgId, currentSchoolYear, offset,limitcount,studentInformationRecordCriteriaMap,sortByColumn,sortType);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Integer findStudentAvilbilityCount(String studentStateId,
			String studentFirstName, String studentlastName, Long orgId,
			Integer currentSchoolYear,
			Map<String, String> studentInformationRecordCriteriaMap) {
		// TODO Auto-generated method stub
		return enrollmentDao.findStudentAvilbilityCount(studentStateId, studentFirstName, studentlastName, orgId, currentSchoolYear,studentInformationRecordCriteriaMap);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String reActivateStudent(Long enrollmentId, Long orgDistrictId,
			Long orgSchoolId, Long gradeId,Long studentId,Long userAssessmentProgramId,Long restrictionId, 
			Enrollment enrollment) {
		
		 StudentJson beforeStudentJson = studentDao.getStudentjsonData(studentId);
	       
		 StudentExample example = new StudentExample();
		 example.createCriteria().andIdEqualTo(studentId);
		 
		 UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
					.getContext().getAuthentication().getPrincipal();
		 	Integer currentSchoolYear= userDetails.getUser().getContractingOrganization().getCurrentSchoolYear().intValue();
		 	Organization district = organizationService.get(orgDistrictId);
		 	Organization school = organizationService.get(orgSchoolId);
		 	String gradeCode = gradeCourseDao.findGradeCodeByGradeCourseId(gradeId);
		 	
		 	Enrollment selectedEnrollment = enrollmentDao.getByEnrollmentId(enrollmentId); 
		 	List<Enrollment> existingEnrollments = enrollmentDao.getEnrollmentByStudentIdAttendanceSchoolIdandCurrentSchoolYear(studentId,orgSchoolId,currentSchoolYear);
		 	int status=0;
		 	try{
				if(selectedEnrollment!=null&& selectedEnrollment.getAttendanceSchoolId() == orgSchoolId && currentSchoolYear.equals(selectedEnrollment.getCurrentSchoolYear())){
			 		selectedEnrollment.setActiveFlag(true);
			 		selectedEnrollment.setAuditColumnPropertiesForUpdate();
			 		selectedEnrollment.setSchoolEntryDate(enrollment.getSchoolEntryDate());
			 		
			 		//if(enrollment.getDistrictEntryDate()!=null)
			 		selectedEnrollment.setDistrictEntryDate(enrollment.getDistrictEntryDate()!=null?enrollment.getDistrictEntryDate():null);
			 		
			 		//if(enrollment.getStateEntryDate()!=null)
			 		selectedEnrollment.setStateEntryDate(enrollment.getStateEntryDate()!=null?enrollment.getStateEntryDate():null);
			 		
			 		//if(StringUtils.isNotBlank(enrollment.getLocalStudentIdentifier()))
			 		selectedEnrollment.setLocalStudentIdentifier(StringUtils.isNotBlank(enrollment.getLocalStudentIdentifier())?enrollment.getLocalStudentIdentifier():null);
			 		
			 		//if(enrollment.getAccountabilitySchoolId()!=null)
			 		selectedEnrollment.setAypSchoolId(enrollment.getAccountabilitySchoolId()!=null?enrollment.getAccountabilitySchoolId():0l);
			 		
			 		//if(StringUtils.isNotBlank(enrollment.getAccountabilityschoolidentifier()))
			 		selectedEnrollment.setAypSchoolIdentifier(StringUtils.isNotBlank(enrollment.getAccountabilityschoolidentifier())?enrollment.getAccountabilityschoolidentifier():null);
			 		
			 		//if(enrollment.getAccountabilityDistrictId()!=null)
			 		selectedEnrollment.setAccountabilityDistrictId(enrollment.getAccountabilityDistrictId()!=null?enrollment.getAccountabilityDistrictId():null);
			 		
			 		//if(StringUtils.isNotBlank(enrollment.getAccountabilityDistrictIdentifier()))
			 		selectedEnrollment.setAccountabilityDistrictIdentifier(StringUtils.isNotBlank(enrollment.getAccountabilityDistrictIdentifier())?enrollment.getAccountabilityDistrictIdentifier():null);
			 		
			 		selectedEnrollment.setFirstLanguage(enrollment.getFirstLanguage());
			 		
			 		selectedEnrollment.setSourceType(SourceTypeEnum.MANUAL.getCode());
			 		selectedEnrollment.setRestrictionId(restrictionId);
			 		selectedEnrollment.setCurrentSchoolYear(currentSchoolYear);
			 		selectedEnrollment.setExitWithdrawalDate(null);
			 		selectedEnrollment.setExitWithdrawalType(0);
			 		selectedEnrollment.setCurrentGradeLevel(gradeId);
			 		
			 		//if(enrollment.getGiftedStudent()!=null)
			 		selectedEnrollment.setGiftedStudent(enrollment.getGiftedStudent()!=null?enrollment.getGiftedStudent():null);
			 		 
			 		status = enrollmentDao.updateOnActivate(selectedEnrollment);
			 	} else {
			 		if(selectedEnrollment!=null && existingEnrollments.size() > 0 && currentSchoolYear.equals(existingEnrollments.get(0).getCurrentSchoolYear())){
			 			selectedEnrollment = existingEnrollments.get(0);
		 				selectedEnrollment.setActiveFlag(true);
		 				selectedEnrollment.setAuditColumnPropertiesForUpdate();
		 				selectedEnrollment.setExitWithdrawalDate(null);
				 		selectedEnrollment.setExitWithdrawalType(0);
				 		selectedEnrollment.setRestrictionId(restrictionId);
				 		selectedEnrollment.setCurrentSchoolYear(currentSchoolYear);
				 		selectedEnrollment.setSchoolEntryDate(enrollment.getSchoolEntryDate());	

				 		selectedEnrollment.setDistrictEntryDate(enrollment.getDistrictEntryDate()!=null?enrollment.getDistrictEntryDate():null);
				 		
				 		selectedEnrollment.setStateEntryDate(enrollment.getStateEntryDate()!=null?enrollment.getStateEntryDate():null);
				 		
				 		selectedEnrollment.setLocalStudentIdentifier(StringUtils.isNotBlank(enrollment.getLocalStudentIdentifier())?enrollment.getLocalStudentIdentifier():null);
				 		
				 		selectedEnrollment.setAypSchoolId(enrollment.getAccountabilitySchoolId()!=null?enrollment.getAccountabilitySchoolId():0l);
				 		
				 		selectedEnrollment.setAypSchoolIdentifier(StringUtils.isNotBlank(enrollment.getAccountabilityschoolidentifier())?enrollment.getAccountabilityschoolidentifier():null);
				 		
				 		selectedEnrollment.setAccountabilityDistrictId(enrollment.getAccountabilityDistrictId()!=null?enrollment.getAccountabilityDistrictId():null);
				 		
				 		selectedEnrollment.setAccountabilityDistrictIdentifier(StringUtils.isNotBlank(enrollment.getAccountabilityDistrictIdentifier())?enrollment.getAccountabilityDistrictIdentifier():null);
				 		
				 		selectedEnrollment.setSourceType(SourceTypeEnum.MANUAL.getCode());
				 		selectedEnrollment.setCurrentGradeLevel(gradeId);
				 		
				 		selectedEnrollment.setFirstLanguage(enrollment.getFirstLanguage());
				 		
				 		selectedEnrollment.setGiftedStudent(enrollment.getGiftedStudent()!=null?enrollment.getGiftedStudent():null);
				 		
				 		status = enrollmentDao.updateOnActivate(selectedEnrollment);
			 			
			 		}else{
			 			if(selectedEnrollment==null){
			 				selectedEnrollment=new Enrollment();		 				
			 				selectedEnrollment.setAuditColumnProperties();
			 				
			 			}
			 			selectedEnrollment.setId(0);
			 			selectedEnrollment.setAttendanceSchoolId(orgSchoolId);
			 			selectedEnrollment.setCurrentGradeLevel(gradeId);
			 			selectedEnrollment.setResidenceDistrictIdentifier(district.getDisplayIdentifier());
			 			selectedEnrollment.setAuditColumnProperties();
			 			selectedEnrollment.setStudentId(studentId);
			 			selectedEnrollment.setExitWithdrawalDate(null);
			 			selectedEnrollment.setRestrictionId(restrictionId);
				 		selectedEnrollment.setExitWithdrawalType(0);
				 		selectedEnrollment.setSchoolEntryDate(enrollment.getSchoolEntryDate());
				 		
				 		selectedEnrollment.setDistrictEntryDate(enrollment.getDistrictEntryDate()!=null?enrollment.getDistrictEntryDate():null);
				 		
				 		selectedEnrollment.setStateEntryDate(enrollment.getStateEntryDate()!=null?enrollment.getStateEntryDate():null);
				 		
				 		selectedEnrollment.setLocalStudentIdentifier(StringUtils.isNotBlank(enrollment.getLocalStudentIdentifier())?enrollment.getLocalStudentIdentifier():null);
				 		
				 		selectedEnrollment.setAypSchoolId(enrollment.getAccountabilitySchoolId()!=null?enrollment.getAccountabilitySchoolId():0l);
				 		
				 		selectedEnrollment.setAypSchoolIdentifier(StringUtils.isNotBlank(enrollment.getAccountabilityschoolidentifier())?enrollment.getAccountabilityschoolidentifier():null);
				 		
				 		selectedEnrollment.setAccountabilityDistrictId(enrollment.getAccountabilityDistrictId()!=null?enrollment.getAccountabilityDistrictId():null);
				 		
				 		selectedEnrollment.setAccountabilityDistrictIdentifier(StringUtils.isNotBlank(enrollment.getAccountabilityDistrictIdentifier())?enrollment.getAccountabilityDistrictIdentifier():null);
				 							 	
				 		selectedEnrollment.setCurrentSchoolYear(currentSchoolYear);
				 		selectedEnrollment.setActiveFlag(true);
				 		selectedEnrollment.setAttendanceSchoolProgramIdentifier(school.getDisplayIdentifier());
				 		//selectedEnrollment.setAypSchoolId(school.getId());
				 		//selectedEnrollment.setAypSchoolIdentifier(school.getDisplayIdentifier());
				 		selectedEnrollment.setSpecialCircumstancesTransferChoice(null);			 		
				 		selectedEnrollment.setSourceType(SourceTypeEnum.MANUAL.getCode());
				 		
				 		selectedEnrollment.setFirstLanguage(enrollment.getFirstLanguage());
				 		
				 		selectedEnrollment.setGiftedStudent(enrollment.getGiftedStudent()!=null?enrollment.getGiftedStudent():null);
			 			
				 		status=enrollmentDao.add(selectedEnrollment);
			 			
			 		}
			 	}
		 	} catch(Exception e){		 		
	 			logger.debug("Activating Student :While updating enrolement");
	 			return "{\"response\" : \"Faild\"}";
		 	}
		 	 try{if(status != 0){
		 		 
		 		studentDao.updateOnEditOrActivate(enrollment.getStudent());
		 		 
	 			 StudentAssessmentProgram  studentAssessmentProgramRecord=new StudentAssessmentProgram();
	 			 studentAssessmentProgramRecord.setAssessmentProgramId(userAssessmentProgramId);
	 			 studentAssessmentProgramRecord.setStudentId(studentId);
	 			 studentAssessmentProgramRecord.setActiveFlag(true);
	 			 List<StudentAssessmentProgram> studentAssessmentPrograms=studentAssessmentProgramsDao.updateAndReturnByStudentId(studentId);
	 			 boolean isStudenthaveUserAssProg=false;
	 			 if(studentAssessmentPrograms!=null){
	 				 for(StudentAssessmentProgram studentAssessmentProgram:studentAssessmentPrograms){
	 					 if(studentAssessmentProgram.getAssessmentProgramId().equals(userAssessmentProgramId)){
	 						 isStudenthaveUserAssProg=true;	
	 						studentAssessmentProgramRecord.setId(studentAssessmentProgram.getId());
	 					 }
	 				 }
	 				 if(isStudenthaveUserAssProg){		 					
	 					 studentAssessmentProgramsDao.updateByPrimaryKey(studentAssessmentProgramRecord);	 
	 				 }else if(!isStudenthaveUserAssProg){				 			
	 					 studentAssessmentProgramsDao.insert(studentAssessmentProgramRecord);				 		 
	 				 }

	 			 }else{

	 				 studentAssessmentProgramsDao.insert(studentAssessmentProgramRecord);
	 			 }
	 			 
	 			 studentProfileService.removeNonAssociatedPNPSettings(studentId, Arrays.asList(userAssessmentProgramId), userDetails.getUser().getId(),
	 					userDetails.getUser().getContractingOrganization().getId());
	 			 
	 			
	 			StudentJson studentJson = studentDao.getStudentjsonData(studentId);
	 	        Student student =  studentDao.findById(studentId);
	 	        student.setAfterJsonString(studentJson != null ? studentJson.buildjsonString() : null);
	 	        student.setBeforeJsonString(beforeStudentJson != null ? beforeStudentJson.buildjsonString() : null);
	 	        
	 	        studentService.insertIntoDomainAuditHistory(studentId,student.getModifiedUser(),
	 	                EventTypeEnum.REACTIVATED.getCode(), 
	 	                SourceTypeEnum.MANUAL.getCode(), student.getBeforeJsonString(), student.getAfterJsonString());
	 			 
		 	 }}catch(Exception e){
			 		
		 			logger.debug("Activating Student :While updating Assessment Program");
		 			return "{\"response\" : \"Faild\"}";
			 					
		 		 }
		 	 
		 	
		return "{\"response\" : \"Success\"}";
	}
	
	@Override
	public List<FindEnrollments> findStudentEnrollment(String studentStateId,
			Long stateId,
			Integer currentSchoolYear,
			Long educatorId,
			Boolean isTeacher) {
		return enrollmentDao.findStudentEnrollment(studentStateId,stateId,currentSchoolYear,educatorId,isTeacher);
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getEnrollmentsByStudentIdForTasc(Long studentId, Long currentSchoolYear){
		return enrollmentDao.getEnrollmentsByStudentIdForTasc(studentId, currentSchoolYear);
	}
	
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public final int addEnrollmentTestTypeSubjectArea(Enrollment enrollment, Long testTypeId, Long subjectAreaId){
		EnrollmentTestTypeSubjectArea enrollmentTestTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
		
		enrollmentTestTypeSubjectArea.setEnrollmentId(enrollment.getId());		
		enrollmentTestTypeSubjectArea.setSubjectareaId(subjectAreaId);				
		enrollmentTestTypeSubjectArea.setTestTypeId(testTypeId);
		enrollmentTestTypeSubjectArea.setCurrentContextUserId(enrollment.getCurrentContextUserId());
		enrollmentTestTypeSubjectArea.setAuditColumnProperties();
		return enrollmentTestTypeSubjectAreaDao.insert(enrollmentTestTypeSubjectArea);
		
	}
	
	@Override
	@Transactional(readOnly=false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentRoster> getEnrollmentWithRosterForAssessmentPrograms(Long studentId, Integer schoolYear, List<Long> assessmentProgramIds){
		List<StudentRoster> enrollmentsRosters = enrollmentDao.getEnrollmentWithRosterForAssessmentPrograms(studentId, schoolYear, assessmentProgramIds);
	    
		return enrollmentsRosters;
	    
	}


	@Override
	public List<Enrollment> getEnrollementsByOrg(Long sourceSchoolId, Long currentSchoolYear) {
		return enrollmentDao.getEnrollementsByOrg(sourceSchoolId,currentSchoolYear);
	}

	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public void transferEnrollment(Enrollment enrollment, Long destinationSchool) {
		enrollment.setAuditColumnPropertiesForUpdate();
		enrollment.setAttendanceSchoolId(destinationSchool);
		enrollmentDao.transferEnrollment(enrollment);
	}

	@Override
	public void disableEnrollment(Enrollment enrollment) {
		enrollment.setAuditColumnPropertiesForDelete();
		enrollment.setActiveFlag(false);
		enrollmentDao.disableEnrollment(enrollment);
	}

	@Override
	public Long getCountByOrganizationId(Long organizationId, Long schoolYear) {
		return enrollmentDao.getCountByOrganizationId(organizationId, schoolYear);
	}

	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getEnrollmentsForKELPABatchRegistration(Long testTypeId, Long contentAreaId, Long gradeCourseId, Long schoolId, Long assessmentId, Long currentSchoolYear, Integer offset, Integer pageSize) {
		return enrollmentDao.findEnrollmentsForKELPABatchRegistration(testTypeId, contentAreaId, gradeCourseId, schoolId, assessmentId, currentSchoolYear, offset, pageSize);
	}
	
	@Override
	public List<Enrollment> getCurrentEnrollmentsByStudentId(Long studentId, Long orgId, int currentSchoolYear, boolean enrlActiveFlag) {
		return enrollmentDao.getCurrentEnrollmentsByStudentId(studentId, orgId, currentSchoolYear, enrlActiveFlag );
	}
	
	@Override
	public List<Long> getNoKELPAEnrollmentOrganizations(Long testTypeId, Long contentAreaId, Long gradeCourseId, Long schoolId,
			Long assessmentId, Long currentSchoolYear) {
		return enrollmentDao.findNoKELPAEnrollmentOrganizations(testTypeId, contentAreaId, gradeCourseId, 
				schoolId, assessmentId, currentSchoolYear);
	}
	
	@Override
	public List<Enrollment> findEnrollmentForStudentEdit(Long studentId, Long currentSchoolYear) {
		return enrollmentDao.findEnrollmentForStudentEdit(studentId, currentSchoolYear);
	}
	
	@Override
	public Enrollment findEnrollmentForStudentActivate(Long enrollmentId) {
		return enrollmentDao.findEnrollmentForStudentActivate(enrollmentId);
	}
	
	@Override
	public int getEnrollmentCountBySsidOrgId(String stateStudentIdentifier, Long orgId, int currentSchoolYear, String orgType){
		return enrollmentDao.getBySsidAndUserOrgId(stateStudentIdentifier, orgId, currentSchoolYear, orgType);
	}

	@Override
	public Long getDeactivateCountByOrganizationId(Long organizationId, Long schoolYear) {
		
		return enrollmentDao.getDeactivateCountByOrganizationId(organizationId, schoolYear);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<DLMStudentSurveyRosterDetailsDto> getDLMStudentsForResearchSurvey(Organization contractingOrganization,
			List<ContentArea> contentAreasToLookUp, Long operationalWindowId, int offset, int limit) {
		List<Long> contentAreaIds = new ArrayList<Long>();
		for(ContentArea ca: contentAreasToLookUp) {
			contentAreaIds.add(ca.getId());
		}
		return enrollmentDao.getDLMStudentsForResearchSurvey(contractingOrganization.getId(), contractingOrganization.getCurrentSchoolYear(),
				contractingOrganization.getAssessmentProgramId(), contentAreaIds, operationalWindowId, offset, limit);
	}

	@Override
	public List<Enrollment> getStudentEnrollmentWithoutAssessmentPrograms(
			Long organizationId, Long schoolYear) {		
		return enrollmentDao.getStudentEnrollmentWithoutAssessmentPrograms(organizationId, schoolYear);
		
	}

	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getEnrollmentsByStudentIdInDistrictBySchool(Long studentId, Long currentSchoolYear,
			Long schoolId) {
		return enrollmentDao.getEnrollmentsByStudentIdInDistrictBySchool(studentId, currentSchoolYear,schoolId);

	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getRosteredEnrollmentsByStudentIdSubjectSchYr(Long studentId, Long currentSchoolYear, Long subjectId){
		return enrollmentDao.getRosteredEnrollmentsByStudentIdSubjectSchYr(studentId, currentSchoolYear, subjectId);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> findEnrollmentsForKAPAdaptiveStage2Assignment(Long contentAreaId, Long gradeCourseId,
			Long contractingOrgId, Long currentSchoolYear, String assessmentProgramCode, Long assessmentProgramId,
			Long operationalTestWindowId, Long testCompletedStatusId, Long stage1Id, Date jobLastSubmissionDate,
			Integer offset, Integer pageSize) {
		
		return enrollmentDao.findEnrollmentsForKAPAdaptiveStage2Assignment(contentAreaId, gradeCourseId, contractingOrgId, currentSchoolYear, assessmentProgramCode, assessmentProgramId, operationalTestWindowId, testCompletedStatusId, stage1Id, jobLastSubmissionDate, offset, pageSize);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public Enrollment findStudentBasedOnStateStudentIdentifier(String stateStudentIdentifier, Long organizationId) {
		return enrollmentDao.findStudentBasedOnStateStudentIdentifier(stateStudentIdentifier, organizationId);
	}
	
	@Override
	@Transactional(readOnly=true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public int findEnrollmentBySSIDAndOrgId(String stateStudentIdentifier, Long assessmentProgramId, Long stateId, Long currentSchoolYear, Long organizationId) {
		return enrollmentDao.findEnrollmentBySSIDAndOrgId(stateStudentIdentifier, assessmentProgramId, stateId, currentSchoolYear, organizationId);
	}
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public String findStateStudentIdentifierStatus(Enrollment enrollment, UserDetailImpl user, Long editStudentAuthorityId){
		
		//Student Not Exists
				if(enrollment==null){			
					 return CommonConstants.STATE_STUDENT_IDENTIFIER_NOT_EXISTS;
				}
				else{					
					Long organizationId = null;
					if(StringUtils.equals(user.getUser().getCurrentOrganizationType(),"SCH")){
						organizationId = enrollment.getAttendanceSchoolId();
					}else if(StringUtils.equals(user.getUser().getCurrentOrganizationType(),"DT")){
						organizationId = enrollment.getAttendanceSchoolDistrictId();
					}else if(StringUtils.equals(user.getUser().getCurrentOrganizationType(),"ST")){
						organizationId = user.getUser().getContractingOrgId();
					}
					//Student Exists in current school year or previous year and not active
					if((enrollment.getCurrentSchoolYear() != user.getUser().getContractingOrganization().getCurrentSchoolYear()) ||
						(enrollment.getCurrentSchoolYear() == user.getUser().getContractingOrganization().getCurrentSchoolYear() &&  !enrollment.getActiveFlag())
					){				
						//User dont have edit permission
						if(editStudentAuthorityId==null){
							return CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_BUT_NOT_ACTIVE;
						}
						
						//User have edit permission
						else{
							return CommonConstants.STATE_STUDENT_IDENTIFIER_SHOW_ACTIVATE;
						}
					}//Student Exists and in current school year and In active
					else if(enrollment.getCurrentSchoolYear() == user.getUser().getContractingOrganization().getCurrentSchoolYear() && enrollment.getActiveFlag()){
						
						//Student Already exists and in current school year and in active and not associated with selected organization
						if(organizationId!=null && (user.getUser().getCurrentOrganizationId() != organizationId)){
							return CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_WITH_DIFFERENT_ORG;
						}
						
						//Student Already exists and in current school year and in active and associated with selected organization
						else{
							
							//Student Already exists and in current school year and active and belongs to same Assessment program
							 if(studentAssessmentProgramsDao.isStudentInAssessmentProgram(enrollment.getStudentId(), user.getUser().getCurrentAssessmentProgramId().longValue())){
								 return CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_ACTIVE;
							
							//Not Associated same assesmentProgramid
							}else{
								return CommonConstants.STATE_STUDENT_IDENTIFIER_EXISTS_WITH_DIFFERENT_ASSESSMENTPROGRAM;
							}
						}
						
					}
				
				}
				
		return CommonConstants.STATE_STUDENT_IDENTIFIER_NOT_EXISTS;
		
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<StudentRoster> getEnrollmentsForISmartBatchAuto(Long contractingOrgId,Long assessmentProgramId,Long currentSchoolYear, 
			Long contentAreaId, String gradeAbbrName, Long operationalTestWindowId, Integer offset, Integer pageSize) {		
		return enrollmentDao.getEnrollmentsForISmartBatchAuto(contractingOrgId, assessmentProgramId, currentSchoolYear,contentAreaId, gradeAbbrName, operationalTestWindowId, offset, pageSize);
	}

	@Override
	public int deactivateByStudentId(Long studentId, Long userId) {
		return enrollmentDao.deactivateByStudentId(studentId, userId);
	}
	
	@Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	public List<Enrollment> getEnrollmentsForPLTWBatchRegistration(Map<String, Object> params) {
		return enrollmentDao.getEnrollmentsForPLTWBatchRegistration(params);
	}

}
