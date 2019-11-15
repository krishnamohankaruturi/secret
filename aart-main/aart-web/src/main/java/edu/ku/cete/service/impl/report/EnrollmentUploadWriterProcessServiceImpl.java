package edu.ku.cete.service.impl.report;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;

import edu.ku.cete.domain.StudentAssessmentProgram;
import edu.ku.cete.domain.StudentsTestSections;
import edu.ku.cete.domain.StudentsTests;
import edu.ku.cete.domain.StudentsTestsExample;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentRecord;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.EnrollmentsRostersExample;
import edu.ku.cete.domain.enrollment.StudentRoster;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.student.StudentJson;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.model.ContentAreaDao;
import edu.ku.cete.model.EnrollmentTestTypeSubjectAreaDao;
import edu.ku.cete.model.InterimGroupDao;
import edu.ku.cete.model.OrganizationDao;
import edu.ku.cete.model.StudentAssessmentProgramMapper;
import edu.ku.cete.model.StudentsTestsDao;
import edu.ku.cete.model.TestSessionDao;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.model.enrollment.EnrollmentsRostersDao;
import edu.ku.cete.model.enrollment.StudentDao;
import edu.ku.cete.model.mapper.StudentsTestSectionsDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.student.StudentProfileService;
import edu.ku.cete.util.DateUtil;
import edu.ku.cete.util.EventTypeEnum;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.SourceTypeEnum;

/**
 *  Added By Prasanth 
 * User Story: US16352:
 * Spring batch upload for data file(Enrollment)
 */
@Service
public class EnrollmentUploadWriterProcessServiceImpl implements BatchUploadWriterService{

	final static Log logger = LogFactory.getLog(EnrollmentUploadWriterProcessServiceImpl.class);
	
	@Value("${studentstests.status.assessmentprogramunenrolled}")
    private String assessmentProgramUnenrolledStudentTest;
	
	@Value("${studentstestsections.status.assessmentprogramunenrolled}")
    private String assessmentProgramUnenrolledStudentTestSection;
	
	@Value("${studentstests.status.type}")
    private String STUDENT_TEST_STATUS_TYPE;
	
	@Value("${studentstestsections.status.type}")
    private String STUDENT_TEST_SECTION_STATUS_TYPE;

	@Autowired
	private StudentService studentService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	StudentDao studentDao;
	
	@Autowired
	private  StudentAssessmentProgramMapper studentAssessmentProgramDao; 
	
	@Autowired
	private StudentProfileService studentProfileService;
	
	@Autowired
	private EnrollmentDao enrollmentDao;
	
	@Autowired
	private EnrollmentsRostersDao enrollmentsRostersDao;
	
	@Autowired
	private StudentsTestsDao studentsTestsDao;
	
	@Autowired
	private StudentsTestSectionsDao studentsTestSectionsDao;
	
	@Autowired
    private TestSessionDao testSessionDao;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private OrganizationService organizationService;
	
	@Autowired
	private AssessmentProgramService assessmentProgramService;
	
	@Autowired
	private InterimGroupDao interimGroupDao;
	
	@Autowired
	private ContentAreaDao contentAreaDao;
	
	@Autowired
	private EnrollmentTestTypeSubjectAreaDao ettsaDao;
	
	@Autowired
    private OrganizationDao organizationDao;
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		logger.debug(" Enrollment writter ");
		for( Object object : objects){
			EnrollmentRecord enrollmentRecord = (EnrollmentRecord) object;
			addorUpdateEnrollmentUpload(enrollmentRecord);
		}  
	}
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private final void addorUpdateEnrollmentUpload(EnrollmentRecord enrollmentRecord) {
    	//TODO add this to the aspect.
    	logger.debug("Enrollment Upload Writer");
    	
    	Student st = enrollmentRecord.getStudent();
    	Long userId = enrollmentRecord.getCreatedUser();
    	SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
    	//Changed during de13887 -- primary disability code storing in lower case
    	if(StringUtils.isNotBlank(st.getPrimaryDisabilityCode())){
  	      st.setPrimaryDisabilityCode(st.getPrimaryDisabilityCode().trim().toUpperCase());
    	}
    	st.setCreatedUser(userId);
    	st.setModifiedUser(userId);
    	st.setAuditColumnProperties();
    	//Changed during DE12837 -- set source type in student
    	st.setSourceType(SourceTypeEnum.UPLOAD.getCode());
    	Student student = studentService.addOrUpdate(st,enrollmentRecord.getAttendanceSchoolId());
    	
    	updateStudentAssessmentProgram(userId, enrollmentRecord);
    	
    	enrollmentRecord.getStudent().setId(student.getId());
        enrollmentRecord.setStudentId(enrollmentRecord.getStudent().getId());
        enrollmentRecord.getEnrollment().setStudent(enrollmentRecord.getStudent());
        enrollmentRecord.getEnrollment().setStudentId(enrollmentRecord.getStudent().getId());
        enrollmentRecord.getEnrollment().setAttendanceSchoolProgramIdentifier(enrollmentRecord.getAttendanceSchoolProgramIdentifier());
        enrollmentRecord.getEnrollment().setAttendanceSchoolId(enrollmentRecord.getAttendanceSchoolId());
        //Commented for F733 - Because setting aypschoolid same as attendance school id breaking the functionality in UI. 
        //if(enrollmentRecord.getAypSchool().getId()!=null)
        	enrollmentRecord.getEnrollment().setAypSchoolId(enrollmentRecord.getAypSchoolId());
        //else
        	//enrollmentRecord.getEnrollment().setAypSchoolId(enrollmentRecord.getAttendanceSchoolId());
        enrollmentRecord.getEnrollment().setCreatedUser(userId);
        enrollmentRecord.getEnrollment().setModifiedUser(userId);
        enrollmentRecord.getEnrollment().setSourceType(SourceTypeEnum.UPLOAD.getCode());
        
        //DE17697 - Use organization timezone to save dates
        String timeZone = organizationDao.getTimeZoneForOrg(enrollmentRecord.getAttendanceSchoolId());
        if(StringUtils.isEmpty(timeZone)){
        	timeZone = "US/Central";
        }
        
        if(enrollmentRecord.getEnrollment().getSchoolEntryDate() != null) {
        	enrollmentRecord.getEnrollment().setSchoolEntryDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(
        			sdf.format(enrollmentRecord.getEnrollment().getSchoolEntryDate()), timeZone,  "MM/dd/yyyy")
    						.getTime()));
        }
        
        if(enrollmentRecord.getEnrollment().getDistrictEntryDate() != null) {
        	enrollmentRecord.getEnrollment().setDistrictEntryDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(
        			sdf.format(enrollmentRecord.getEnrollment().getDistrictEntryDate()), timeZone,  "MM/dd/yyyy")
    						.getTime()));
        }
        
        if(enrollmentRecord.getEnrollment().getStateEntryDate() != null) {
        	enrollmentRecord.getEnrollment().setStateEntryDate(new Timestamp(DateUtil.convertStringDatetoSpecificTimeZoneDateFormat(
        			sdf.format(enrollmentRecord.getEnrollment().getStateEntryDate()), timeZone,  "MM/dd/yyyy")
    						.getTime()));
        }
        /*add as part of F733 when user uploaded Accountability District Identifier as null
        we are setting the Accountability District Id also as null*/
        if(StringUtils.isEmpty(enrollmentRecord.getAccountabilityDistrictIdentifier())) {
        	enrollmentRecord.setAccountabilityDistrictId(null);
        }
        
        //Enrollment enrollment = enrollmentService.addOrUpdate(enrollmentRecord.getEnrollment());
        enrollmentService.addOrUpdate(enrollmentRecord.getEnrollment());
        
        try {
        	Organization state = organizationService.getContractingOrganization(enrollmentRecord.getEnrollment().getAttendanceSchoolId());
			studentProfileService.removeNonAssociatedPNPSettings(student.getId(), enrollmentRecord.getAssessmentProgsIds(), userId, state.getId());
		} catch (JsonProcessingException e) {
			logger.error("Error updating student " + student.getId() + "'s PNP during enrollment upload:", e);
		}
        
        boolean isInsert = true;
        if(student.getSaveStatus().equals(RecordSaveStatus.STUDENT_UPDATED) ){
        	isInsert = false;
        }
        
        /*addtoDomainAuditHistory(enrollment.getId(), userId, isInsert);*/
        StudentJson studentJson = studentDao.getStudentjsonData(student.getId());
        
        student.setAfterJsonString(studentJson != null ? studentJson.buildjsonString() : null);
        
        studentService.insertIntoDomainAuditHistory(student.getId(),student.getModifiedUser(),
                isInsert ? EventTypeEnum.INSERT.getCode() : EventTypeEnum.UPDATE.getCode(),
                SourceTypeEnum.UPLOAD.getCode(), student.getBeforeJsonString(), student.getAfterJsonString());
    }
	
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    private void updateStudentAssessmentProgram(Long modifiedUserId, EnrollmentRecord enrollmentRecord) {
		if (enrollmentRecord != null) {
			Long studentId = enrollmentRecord.getStudent().getId();
			List<Long> assessmentProgramIds = enrollmentRecord.getAssessmentProgsIds();
	        if (CollectionUtils.isNotEmpty(assessmentProgramIds)) {
	        	List<StudentAssessmentProgram> studentAssessmentPrograms = studentAssessmentProgramDao.getByStudentId(studentId);
	        	List<Long> studentAssessmentProgramIds = new ArrayList<Long>();
	    		for (StudentAssessmentProgram studentAssessmentProgram : studentAssessmentPrograms) {
	    			studentAssessmentProgramIds.add(studentAssessmentProgram.getAssessmentProgramId());
	    		}
	    		
	    		List<Long> assessmentProgramIdsToRemove = new ArrayList<Long>();
	    		
	    		// KAP and DLM can't co-exist, <highlander-movie-reference> "THERE CAN BE ONLY ONE" </highlander-movie-reference>
	    		AssessmentProgram kap = assessmentProgramService.findByAbbreviatedName("KAP");
	    		AssessmentProgram dlm = assessmentProgramService.findByAbbreviatedName("DLM");
	    		if (assessmentProgramIds.contains(dlm.getId()) && studentAssessmentProgramIds.contains(kap.getId())) {
	    			assessmentProgramIdsToRemove.add(kap.getId());
	    		} else if (assessmentProgramIds.contains(kap.getId()) && studentAssessmentProgramIds.contains(dlm.getId())) {
	    			assessmentProgramIdsToRemove.add(dlm.getId());
	    		}
	        	
	    		int schoolYear = enrollmentRecord.getEnrollment().getCurrentSchoolYear();
	    		
	    		if (CollectionUtils.isNotEmpty(assessmentProgramIdsToRemove)) {
	    			List<Enrollment> enrollments = enrollmentService.getCurrentEnrollmentsByStudentId(studentId, null, schoolYear,true);
	    			
	    			List<StudentRoster> enrollmentsRosters = enrollmentService.getEnrollmentWithRosterForAssessmentPrograms(
	    					studentId, schoolYear, assessmentProgramIdsToRemove);
	    			
	    			ContentArea elp = contentAreaDao.findByAbbreviatedName("ELP");
	    			
	    			for (Enrollment e : enrollments) {
	    				boolean removeAll = e.getAttendanceSchoolId() != enrollmentRecord.getAttendanceSchoolId();
	    				
		    			for (StudentRoster enrollmentsRoster : enrollmentsRosters) {
		    				if (enrollmentsRoster.getEnrollmentId().equals(e.getId())) {
			    				// if the kid is being switched to DLM, skip rosters that are using the KELPA2 subject
			    				if (assessmentProgramIds.contains(dlm.getId()) && studentAssessmentProgramIds.contains(kap.getId())) {
			    					if (!removeAll && elp.getId().longValue() == enrollmentsRoster.getRoster().getStateSubjectAreaId().longValue()) {
			    						continue;
			    					}
			    				}
			    				
				    			//Inactivate EnrollmentRosters
				    			EnrollmentsRosters oldEnrlRoster = new EnrollmentsRosters();
				    			oldEnrlRoster.setEnrollmentId(enrollmentsRoster.getEnrollmentId());
				    			oldEnrlRoster.setRosterId(enrollmentsRoster.getRoster().getId());
				    			oldEnrlRoster.setActiveFlag(Boolean.FALSE);
				    			oldEnrlRoster.setModifiedUser(enrollmentRecord.getModifiedUser());
				    			oldEnrlRoster.setModifiedDate(enrollmentRecord.getModifiedDate());
				    			
				    			EnrollmentsRostersExample example = new EnrollmentsRostersExample();
				    			EnrollmentsRostersExample.Criteria criteria = example.createCriteria();
				    			criteria.andEnrollmentIdEqualTo(enrollmentsRoster.getEnrollmentId());
				    			criteria.andRosterIdEqualTo(enrollmentsRoster.getRoster().getId());
				    			
				    			enrollmentsRostersDao.updateByExampleSelective(oldEnrlRoster, example);
		    				}
			    		}
		    			ettsaDao.removeTestsForAssessmentProgramsByEnrollmentId(e.getId(), assessmentProgramIdsToRemove, enrollmentRecord.getModifiedUser());
		    			if (removeAll) {
		    				enrollmentDao.inactivateOldEnrollmentForNewEnrollmentUpload(e.getId(), modifiedUserId, SourceTypeEnum.UPLOAD.getCode());
		    			}
	    			}
	    			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder
	    	                .getContext().getAuthentication().getPrincipal();
		    		for (Long assessmentProgramIdToRemove : assessmentProgramIdsToRemove) {
		    			removeStudentTestsForAssessmentProgram(enrollmentRecord, assessmentProgramIdToRemove);
		    			
		    			if (assessmentProgramIdToRemove.longValue() == kap.getId().longValue()) {
		    				interimGroupDao.deleteOnStudent(studentId,new Date(),userDetails.getUserId());
		    			}
		    		}
	    		}
	        	
	        	/* 
	        	 * For all the assessment programs that must be added, check against the active/inactive assessmentprograms associated with the student.
	        	 * If the new assessmentprogram to be added matches the student's AP then update the activeflag to true. If not, insert new record. 
	        	 * 
	        	 * Ideally there should be only one assessmentprogram to be added. So, we add the new one and remove the old assessmentprogram. 
	        	 * Also inactivate all the enrollments, rosters and test sessions associated with the old assessment program.
	        	 * 
	        	 */
	        	for (Long asmntProgId : assessmentProgramIds) {
	        		boolean exist = false;
	        		for (StudentAssessmentProgram studentAssessmentProgram : studentAssessmentPrograms) {
	        			if (studentAssessmentProgram.getAssessmentProgramId() == asmntProgId) {
	        				if (!studentAssessmentProgram.getActiveFlag()) {
	        					//If already assessment program is present with activeflag as false Then update that one.
	        					studentAssessmentProgram.setActiveFlag(true);
	        					studentAssessmentProgramDao.updateByPrimaryKey(studentAssessmentProgram);                			
	        				}
	        				exist = true;
	        				break;
	        			}
	        		}
	        		
	        		if (!exist) {
	        			StudentAssessmentProgram studentAssessmentProgram = new StudentAssessmentProgram();
	        			studentAssessmentProgram.setStudentId(studentId);
	        			studentAssessmentProgram.setAssessmentProgramId(asmntProgId);
	        			studentAssessmentProgram.setActiveFlag(true);
	        			studentAssessmentProgramDao.insert(studentAssessmentProgram);
	        		}
	        		
	        		
	        	}

        		//Deactivate assessment program of the student which doesn't match the new assessment program.
        		//Since only assessmentprogram1 column values will be received in this method, rest of them can safely be inactivated.
	        	if (CollectionUtils.isNotEmpty(assessmentProgramIdsToRemove)) {
	        		studentAssessmentProgramDao.deactivateStudentIdAndAssessmentProgramIds(studentId, assessmentProgramIdsToRemove);
	        	}
        		
        		/*
        		 Should I compare ayp/att schools from old and new enrollments??
        		 within same assessment program, same district, same school cases?
        		 */
        		//inactivateEnrollmentsAndRostersForOtherAssessmentProgramIds(enrollmentRecord, assessmentProgramIdsToRemove);
    
	        }
		}
    }
	
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void removeStudentTestsForAssessmentProgram(EnrollmentRecord enrollmentRecord, Long assessmentProgramId) {
		Long studentId = enrollmentRecord.getStudent().getId();
		List<Long> testSessionIds = testSessionDao.selectTestSessionByStudentIdAndSourceWithActiveOTW("BATCHAUTO",
				studentId, assessmentProgramId);
		
		for (Long testSessionId : testSessionIds) {
			List<StudentsTests> studentsTests = studentsTestsDao.findByTestSessionAndStudentId(testSessionId, studentId);
			for (StudentsTests st : studentsTests) {
				Category sectionCategory = categoryService.selectByCategoryCodeAndType(assessmentProgramUnenrolledStudentTestSection,
						STUDENT_TEST_SECTION_STATUS_TYPE);
				StudentsTestSections studentTestSection = new StudentsTestSections();
		    	studentTestSection.setStatusId(sectionCategory.getId());
		    	studentTestSection.setStatus(sectionCategory);
		    	studentTestSection.setModifiedDate(enrollmentRecord.getModifiedDate());
		        studentTestSection.setModifiedUser(enrollmentRecord.getModifiedUser());
		    	studentTestSection.setActiveFlag(false);
		    	studentsTestSectionsDao.updateStatusByStudentTestIds(studentTestSection, Arrays.asList(st.getId()));
		    	
		        StudentsTests studentTest = new StudentsTests();
		        Category testCategory = categoryService.selectByCategoryCodeAndType(assessmentProgramUnenrolledStudentTest, STUDENT_TEST_STATUS_TYPE);
		        studentTest.setStatus(testCategory.getId());
		        studentTest.setStudentTestStatus(testCategory);
		        studentTest.setModifiedDate(enrollmentRecord.getModifiedDate());
		        studentTest.setModifiedUser(enrollmentRecord.getModifiedUser());
		        studentTest.setActiveFlag(false);
		
		        StudentsTestsExample example = new StudentsTestsExample();
		        StudentsTestsExample.Criteria criteria = example.createCriteria();
		        criteria.andIdEqualTo(st.getId());
		        studentsTestsDao.updateByExampleSelective(studentTest, example);
			}
		}
	}
}
