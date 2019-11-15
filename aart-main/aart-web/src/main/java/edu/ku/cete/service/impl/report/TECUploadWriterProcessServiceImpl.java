package edu.ku.cete.service.impl.report;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import edu.ku.cete.constants.validation.InvalidTypes;
import edu.ku.cete.domain.ContractingOrganizationTree;
import edu.ku.cete.domain.EnrollmentTestTypeSubjectArea;
import edu.ku.cete.domain.EnrollmentTestTypeSubjectAreaExample;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.enrollment.Enrollment;
import edu.ku.cete.domain.enrollment.EnrollmentsRosters;
import edu.ku.cete.domain.enrollment.Roster;
import edu.ku.cete.domain.enrollment.SubjectArea;
import edu.ku.cete.domain.enrollment.TecRecord;
import edu.ku.cete.domain.enrollment.WebServiceRosterRecord;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.validation.FieldName;
import edu.ku.cete.ksde.kids.result.KidRecord;
import edu.ku.cete.model.ContentAreaDao;
import edu.ku.cete.model.EnrollmentTestTypeSubjectAreaDao;
import edu.ku.cete.model.enrollment.EnrollmentDao;
import edu.ku.cete.model.enrollment.StudentsAssessmentsDao;
import edu.ku.cete.report.domain.DomainAuditHistory;
import edu.ku.cete.report.model.DomainAuditHistoryMapper;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.BatchUploadWriterService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.UserService;
import edu.ku.cete.service.enrollment.EnrollmentsRostersService;
import edu.ku.cete.service.enrollment.RosterService;
import edu.ku.cete.util.ParsingConstants;
import edu.ku.cete.util.RecordSaveStatus;
import edu.ku.cete.util.SourceTypeEnum;

/**
 * Added By Prasanth 
 * User Story: US16352:
 * Spring batch upload for data file(Student TEC)
 */
@Service
public class TECUploadWriterProcessServiceImpl implements BatchUploadWriterService{

	final static Log logger = LogFactory.getLog(TECUploadWriterProcessServiceImpl.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private GradeCourseService gradeCourseService;
	
	@Autowired
	private ContentAreaService contentAreaService;

	@Autowired
	private AssessmentService assessmentService;
	
	@Autowired
	private StudentsAssessmentsDao studentsAssessmentsDao;
	
	@Autowired
	private EnrollmentTestTypeSubjectAreaDao enrollmentTestTypeSubjectAreaDao;
	
	@Autowired
	private EnrollmentDao enrollmentDao;

	@Autowired
	private EnrollmentService enrollmentService;

	@Autowired
	private ContentAreaDao contentAreaDao;

	@Autowired
	private EnrollmentsRostersService enrollmentsRostersService;
	 
	@Autowired
	private RosterService rosterService;
	
	@Autowired
	private DomainAuditHistoryMapper domainAuditHistoryDao;
	
	@Autowired
    private OrganizationService organizationService;	
	
	@Override
	public void writerProcess(List<? extends Object> objects) {
		
		for( Object object : objects){
			TecRecord uploadedTec = (TecRecord) object;
			addorUpdateStudentTec(uploadedTec);
		} 
	}
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addorUpdateStudentTec(TecRecord uploadTecRecord){
		
		
		KidRecord kidRecord =	uploadTecRecord.getKidRecord();
		User user = uploadTecRecord.getUser();
		//boolean isTecRecord = true;
		//Student student = kidRecord.getStudent();
		
		ContractingOrganizationTree contractingOrganizationTree = organizationService.getTree(kidRecord.getAttendanceSchool());
		uploadTecRecord.getEnrollment().setSourceType(SourceTypeEnum.UPLOAD.getCode());		
		uploadTecRecord = enrollmentService.cascadeAddOrUpdate(uploadTecRecord, contractingOrganizationTree, user);
		

//		Long attendanceSchoolId = kidRecord.getAttendanceSchoolId();
//		Long userContextId = uploadTecRecord.getUser().getId();
//        GradeCourse gradeCourse = null;
//        GradeCourse grade = null;
//        if(kidRecord.getCurrentGradeLevel() != null) {
//	        gradeCourse = new GradeCourse();
//	        String strGradeLevel = "0" + kidRecord.getCurrentGradeLevel();
//	        //add a zero to the beginning and then if there are more than 2 digits remove the first digit
//	        strGradeLevel = strGradeLevel.length() > 2 ? strGradeLevel.substring(1) : strGradeLevel;
//	        //store in abbr name to send to query
//	        gradeCourse.setAbbreviatedName(strGradeLevel);
//	        gradeCourse.setCurrentContextUserId(userContextId);
//	        grade = gradeCourseService.getWebServiceGradeCourseByCode(gradeCourse);
//        }
//        
//        if(grade!=null) {
//            kidRecord.getEnrollment().setCurrentGradeLevel(grade.getId());
//        }
//        //create assessment student relations.
//        for (String assessmentCode : kidRecord.getAssessmentInputNames().keySet()) {
//            ContentArea contentArea = null; 
//            Assessment assessment = null;
//            StudentsAssessments studentsAssessments = null;
//        	contentArea
//            	= contentAreaService.findByAbbreviatedName(kidRecord.getAssessmentInputNames().get(assessmentCode).getContentAreaAbbreviatedName());
//            //create the assessment.
//            assessment = new Assessment(); 
//            assessment.setAssessmentName(kidRecord.getAssessmentInputNames().get(assessmentCode).getAssessmentCode());
//            assessment.setAssessmentCode(kidRecord.getAssessmentInputNames().get(assessmentCode).getAssessmentCode());
//            assessment.setCurrentContextUserId(userContextId);
//            assessment = assessmentService.saveAssessment(assessment);
//            
//            studentsAssessments = new StudentsAssessments();
//            studentsAssessments.setStudentId(student.getId());
//            studentsAssessments.setAssessmentCode(assessment.getAssessmentCode());            
//            studentsAssessments.setContentAreaId(contentArea.getId());
//            studentsAssessments.setCurrentContextUserId(userContextId);
//            studentsAssessments.setAuditColumnProperties();
//            studentsAssessmentsDao.saveStudentsAssessments(studentsAssessments);
//        }
//	
//        //set the assessment school relations.
//        kidRecord.getStudent().setId(student.getId());
//        kidRecord.setStudentId(kidRecord.getStudent().getId());
//
//        kidRecord.getEnrollment().setStudent(kidRecord.getStudent());
//        kidRecord.getEnrollment().setStudentId(kidRecord.getStudent().getId());
//        kidRecord.getEnrollment().setAttendanceSchoolProgramIdentifier(
//                kidRecord.getAttendanceSchoolProgramIdentifier());
//        kidRecord.getEnrollment().setAttendanceSchoolId(
//        		attendanceSchoolId);
//        kidRecord.getEnrollment().setCurrentContextUserId(userContextId);
//        /*if (kidRecord.getRecordType().equalsIgnoreCase("EXIT")){
//        	 kidRecord.getEnrollment().setExitWithdrawalDate(kidRecord.getExitWithdrawalDate());
//        	 kidRecord.getEnrollment().setExitWithdrawalType(Integer.valueOf(kidRecord.getExitWithdrawalType()));
//        }*/
//        if(isTecRecord) {
//        	kidRecord.getEnrollment().setAypSchoolId(attendanceSchoolId);
//        } else {
////        	kidRecord.getEnrollment().setAypSchoolId(aypSchool.getId());
//        }
//        kidRecord.getEnrollment().setAuditColumnProperties();
//        
//        Enrollment enrollment = kidRecord.getEnrollment();
//        /*if (!isTecRecord){
//        	enrollment = addOrUpdateTestOrExit(kidRecord.getEnrollment(), kidRecord.getRecordType().equalsIgnoreCase("TEST"));
//        }*/
//        
//        kidRecord.setEnrollment(enrollment);
//        
//        if(kidRecord.getRecordType().equalsIgnoreCase("TEST") || kidRecord.getRecordType().equalsIgnoreCase("CLEAR")) {
//        	
//        	List<TestType> testTypes = enrollmentTestTypeSubjectAreaDao.selectAllTestTypes();
//        	List<SubjectArea> subjectAreas = enrollmentTestTypeSubjectAreaDao.selectAllSubjectAreas();
//        	Map<String, TestType> testTypesMap = new HashMap<String, TestType>();
//        	Map<String, SubjectArea> subjectAreaMap = new HashMap<String, SubjectArea>();
//        	
//        	for(TestType testType : testTypes) {
//        		testTypesMap.put(testType.getTestTypeCode(), testType);
//        	}
//        	
//        	for(SubjectArea subjectArea : subjectAreas) {
//        		subjectAreaMap.put(subjectArea.getSubjectAreaCode(), subjectArea);
//        	}
//        	// process clear records
//        	if (enrollment.getExitWithdrawalDate() == null ||
//        			enrollment.getExitWithdrawalDate().getTime() < enrollment.getSchoolEntryDate().getTime()){
//				enrollmentService.processClearTestTypes(kidRecord, enrollment, user, testTypesMap, subjectAreaMap, isTecRecord);
//        	}
//        	//enrollmentService.addEnrollmentTestTypes(kidRecord, enrollment, user, testTypesMap, subjectAreaMap);
//        	addEnrollmentTestTypes(kidRecord, enrollment, user, testTypesMap, subjectAreaMap);
//	        WebServiceRosterRecord rosterRecord = uploadTecRecord.getRosterRecord();
//	        if(kidRecord.getDlmELAProctorId() != null && kidRecord.getStateELAAssessment() != null 
//					&& !"0".equals(kidRecord.getStateELAAssessment())){
//	        	addOrUpdateRoster(rosterRecord,userContextId);
//	        }
//	        
//	        if(kidRecord.getDlmMathProctorId() != null && kidRecord.getStateMathAssess() != null 
//					&& !"0".equals(kidRecord.getStateMathAssess())){
//	        	addOrUpdateRoster(rosterRecord,userContextId);
//	        }
//	        
//	        if(kidRecord.getDlmSciProctorId() != null && kidRecord.getStateSciAssessment() != null 
//					&& !"0".equals(kidRecord.getStateSciAssessment())){
//	        	addOrUpdateRoster(rosterRecord,userContextId);
//	        }
//	        
//	        if(kidRecord.getCpassProctorId() != null) {
//	        	if(kidRecord.getEndOfPathwaysAssessment() != null && !"0".equals(kidRecord.getEndOfPathwaysAssessment())){
//	        		List<ContentArea> contentAreas = contentAreaDao.findBySubjectAreaTestType(
//		        			"EOPA", kidRecord.getEndOfPathwaysAssessment());
//		        	
//		        	if(!CollectionUtils.isEmpty(contentAreas)) {
//		        		addOrUpdateRoster(rosterRecord,userContextId);
//		        	}		        	
//	        	} 
//	        	if (kidRecord.getGeneralCTEAssessment() != null && !"0".equals(kidRecord.getGeneralCTEAssessment())) {
//	        		List<ContentArea> contentAreas = contentAreaDao.findBySubjectAreaTestType(
//		        			"GCTEA", kidRecord.getGeneralCTEAssessment());
//		        	if(!CollectionUtils.isEmpty(contentAreas)) {
//		        		addOrUpdateRoster(rosterRecord,userContextId);
//		        	}		        	
//	        	}
//	        }
//        }  
//        else if (kidRecord.getRecordType().equalsIgnoreCase("EXIT")){
//        	logger.debug("Processing an EXIT record for student: "+kidRecord.getStudent().getStateStudentIdentifier()+" and school: "+kidRecord.getAttendanceSchoolProgramIdentifier()
//    			+" with type: "+kidRecord.getRecordType());
//        	 int enrollmentCurrentSchoolYear = kidRecord.getCurrentSchoolYear();
//        	//US13725 process undoing an exit record
//        	if (Long.valueOf(kidRecord.getExitWithdrawalType()).equals(99L)){
//        		List<Enrollment> inactiveEnrollments = enrollmentDao.getInactiveEnrollment(kidRecord.getStateStudentIdentifier(), student.getStateId(), enrollmentCurrentSchoolYear);
//	        	for (Enrollment e : inactiveEnrollments){
//	        		if(isTecRecord) {
//	        			if (e.getAttendanceSchoolProgramIdentifier().equals(kidRecord.getAttendanceSchoolProgramIdentifier())
//	        					&& e.getCurrentSchoolYear() == kidRecord.getCurrentSchoolYear() 
//	        					&& e.getExitWithdrawalDate() != null 
//	        					&& e.getExitWithdrawalDate().getTime() == kidRecord.getExitWithdrawalDate().getTime()){
//	        				//should we remove the info for the exit date and code previously stored?
//	        				enrollmentService.undoExitRecords(user, e);
//			        		break;
//	        			}
//	        		} else {
//	        			if (e.getAttendanceSchoolId() == kidRecord.getEnrollment().getAypSchoolId()
//	        					&& e.getCurrentSchoolYear() == kidRecord.getCurrentSchoolYear() 
//	        					&& e.getExitWithdrawalDate() != null 
//	        					&& e.getExitWithdrawalDate().getTime() == kidRecord.getExitWithdrawalDate().getTime()){
//	        				enrollmentService.undoExitRecords(user, e);
//	        			}
//	        		}
//	        		addtoDomainAuditHistory(e.getId(),user.getId(),"TEC-EXIT",false);
//	        	}
//        	}else{
//	        	List<Enrollment> enrollments = enrollmentDao.getBySsidAndState(kidRecord.getStateStudentIdentifier(), student.getStateId(), enrollmentCurrentSchoolYear);
//	        	for (Enrollment e : enrollments){
//	        		if(isTecRecord) {
//	        			if (e.getAttendanceSchoolProgramIdentifier().equals(kidRecord.getAttendanceSchoolProgramIdentifier())
//	        					&& e.getCurrentSchoolYear() == kidRecord.getCurrentSchoolYear()){
//	        				enrollmentService.processExitRecord(kidRecord, user, e);
//	        			}
//	        		} else {
//	        			if (e.getAttendanceSchoolId() == kidRecord.getEnrollment().getAypSchoolId()
//	        					&& e.getCurrentSchoolYear() == kidRecord.getCurrentSchoolYear()){	        				
//	        				enrollmentService.processExitRecord(kidRecord, user, e);
//	        			}
//	        		}
//	        		addtoDomainAuditHistory(e.getId(),user.getId(),"TEC-EXIT",false);
//	        	}
//        	}
//        }
	}
	@Transactional(readOnly=false,propagation=Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	private void addOrUpdateRoster(WebServiceRosterRecord scrsRecord,Long currentContextUserId ){
			GradeCourse gradeCourse = null;
		GradeCourse grade = null;
		if (scrsRecord.getEnrollment().getCurrentGradeLevel() != null) {
			gradeCourse = new GradeCourse();
			String strGradeLevel = "0"
					+ scrsRecord.getEnrollment().getCurrentGradeLevel();
			// add a zero to the beginning and then if there are more than 2
			// digits remove the first digit
			strGradeLevel = strGradeLevel.length() > 2 ? strGradeLevel
					.substring(1) : strGradeLevel;
			// store in abbr name to send to query
			gradeCourse.setAbbreviatedName(strGradeLevel);
			gradeCourse.setCurrentContextUserId(scrsRecord.getUser().getId());
			grade = gradeCourseService
					.getWebServiceGradeCourseByCode(gradeCourse);
		}
		Long rosterCourseId = null;
		if (grade != null) {
			scrsRecord.getEnrollment().setCurrentGradeLevel(grade.getId());
		} else {
			scrsRecord.getEnrollment().setCurrentGradeLevel(null);
		}
		Student student =  scrsRecord.getStudent();
		Enrollment newEnrollment = scrsRecord.getEnrollment();
		newEnrollment.setAttendanceSchoolId(scrsRecord.getSchool().getId());
		newEnrollment.setStudentId(student.getId());
		newEnrollment.setCurrentContextUserId(currentContextUserId);
	
		newEnrollment.setAypSchoolId(scrsRecord.getSchool().getId());
			
		List<Enrollment> enrollments = new ArrayList<Enrollment>();
		// if student exists in the system then try to find his enrollments for
		// the school.
		if (student.getSaveStatus().equals(RecordSaveStatus.STUDENT_FOUND)) {
			enrollments = enrollmentService.addIfNotPresentSTCO(newEnrollment);
			// even if one enrollment is added, it means we have added new
			// enrollments.
			for (Enrollment enrollment : enrollments) {
				if (enrollment.getSaveStatus().equals(
						RecordSaveStatus.ENROLLMENT_ADDED)) {
					//scrsRecordSaveStatus = RecordSaveStatus.ENROLLMENT_ADDED;
				}
			}
		}
		// DO not look for enrollments as the student is just created.
		else {
			scrsRecord.setCreated(true);
			newEnrollment = enrollmentService.add(newEnrollment);
			enrollments.add(newEnrollment);
			//scrsRecordSaveStatus = RecordSaveStatus.ENROLLMENT_ADDED;
		}
		// find rosters if any for the given school i.e. enrollments for given
		// school
		// but not necessarily for the given student.

		Roster roster = new Roster();
		roster.setCourseSectionName(scrsRecord.getCourseSection());
		roster.setAttendanceSchoolId(scrsRecord.getSchool().getId());
		roster.setTeacherId(scrsRecord.getEducator().getId());
		roster.setStateSubjectAreaId(scrsRecord.getStateSubjectAreaId());
		roster.setStateCourseCode(scrsRecord.getStateCourseCode());
		roster.setStateCoursesId(rosterCourseId);
		roster.setRestrictionId(scrsRecord.getRoster().getRestrictionId());
		roster.setCurrentSchoolYear(scrsRecord.getCurrentSchoolYear());
		roster.setSourceType(scrsRecord.getSourceType());
		
		roster.setAypSchoolId(scrsRecord.getSchool().getId());			
		
		if (roster.getRestrictionId() == null) {
			roster.setRestrictionId(scrsRecord.getEnrollment()
					.getRestrictionId());
		}
		roster.setCurrentContextUserId(currentContextUserId);
		// This is for populating the
		// KCCID,LocalCourseId,Educatorschooldisplayidentifier which we would be
		// getting in STCO webservice data only.
		
		roster.setStateSubjectCourseIdentifier(((WebServiceRosterRecord) scrsRecord)
				.getStateSubjectCourseIdentifier());
		roster.setLocalCourseId(((WebServiceRosterRecord) scrsRecord)
				.getLocalCourseId());
		roster.setEducatorschooldisplayidentifier(((WebServiceRosterRecord) scrsRecord)
				.getEducatorSchoolIdentifier());
	
		roster.setAuditColumnProperties();
		List<Roster> rosters = rosterService.getRostersByCriteria(roster, scrsRecord.getSchool().getId());
		if (rosters.size() > 1) {
			//scrsRecord.setSaveStatus(scrsRecordSaveStatus);
			
			// the same teacher is teaching more than one class/roster.
			// in the same school and they have the same section name.
			scrsRecord.addInvalidField(FieldName.UPLOAD
					+ ParsingConstants.BLANK,
					ParsingConstants.BLANK, true,
					InvalidTypes.ROSTER_NOT_UNIQUE + ParsingConstants.BLANK);
			//return scrsRecord;
		}
		// add enrollment to roster.
		for (Enrollment enrollment : enrollments) {
			EnrollmentsRosters enrollmentsRosters = new EnrollmentsRosters(
					enrollment.getId(), scrsRecord.getRoster().getId());

			if (null != scrsRecord.getEnrollmentStatusCode()
					&& (99 == scrsRecord.getEnrollmentStatusCode() || 4 == scrsRecord
							.getEnrollmentStatusCode())) {
				enrollmentsRosters.setActiveFlag(false);
			}

			if (scrsRecord.getEnrollmentStatus() != null) {
				enrollmentsRosters.setCourseEnrollmentStatusId(scrsRecord
					.getEnrollmentStatus().getId());
			}
			enrollmentsRosters.setCurrentContextUserId(currentContextUserId);
			enrollmentsRostersService.addEnrollmentToRoster(enrollmentsRosters);
			scrsRecord
					.setSaveStatus(RecordSaveStatus.ENROLLMENTS_ADDED_TO_ROSTERS);
		}
		
	}
	  @Transactional(readOnly=false, propagation=Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	  private final void addEnrollmentTestTypes(KidRecord kidRecord, Enrollment enrollment, User user,
	    		Map<String, TestType> testTypesMap, Map<String, SubjectArea> subjectAreaMap) {
	    	
	    	if(kidRecord.getStateMathAssess() != null && 
	    			StringUtils.isNotEmpty(kidRecord.getStateMathAssess()) &&
	    			!"0".equals(kidRecord.getStateMathAssess())) {
	    		
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get(kidRecord.getStateMathAssess()).getId(), (subjectAreaMap.get("D74")).getId(),
	    				kidRecord.getGroupingInd1Math(), kidRecord.getGroupingInd2Math());
	    	}
	  
	    	if(kidRecord.getGeneralCTEAssessment() != null && 
	    			StringUtils.isNotEmpty(kidRecord.getGeneralCTEAssessment()) &&
	    			!"0".equals(kidRecord.getGeneralCTEAssessment())) {
	    		
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get(kidRecord.getGeneralCTEAssessment()).getId(), (subjectAreaMap.get("GCTEA")).getId(),
	    				kidRecord.getGroupingInd1CTE(), kidRecord.getGroupingInd2CTE());
	    	}  

	       	if(kidRecord.getEndOfPathwaysAssessment() != null && 
	    			StringUtils.isNotEmpty(kidRecord.getEndOfPathwaysAssessment()) &&
	    			!"0".equals(kidRecord.getEndOfPathwaysAssessment())) {
	    		
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get(kidRecord.getEndOfPathwaysAssessment()).getId(), (subjectAreaMap.get("EOPA")).getId(),
	    				kidRecord.getGroupingInd1Pathways(), kidRecord.getGroupingInd2Pathways());
	    	} 
	       	
	       	if(kidRecord.getStateELAAssessment() != null && 
	    			StringUtils.isNotEmpty(kidRecord.getStateELAAssessment()) &&
	    			!"0".equals(kidRecord.getStateELAAssessment())) {
	    		
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get(kidRecord.getStateELAAssessment()).getId(), (subjectAreaMap.get("SELAA")).getId(), 
	    				kidRecord.getGroupingInd1ELA(), kidRecord.getGroupingInd2ELA());
	    	}
	       	
	       	if(kidRecord.getStateSciAssessment() != null && 
	    			StringUtils.isNotEmpty(kidRecord.getStateSciAssessment()) &&
	    			!"0".equals(kidRecord.getStateSciAssessment())) {
	    		
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get(kidRecord.getStateSciAssessment()).getId(), (subjectAreaMap.get("SSCIA")).getId(),
	    				kidRecord.getGroupingInd1Sci(), kidRecord.getGroupingInd2Sci());
	    	}   
	       	
	       	if(kidRecord.getStateHistGovAssessment() != null && 
	    			StringUtils.isNotEmpty(kidRecord.getStateHistGovAssessment()) &&
	    			!"0".equals(kidRecord.getStateHistGovAssessment())) {
	    		addOrUpdateEnrollmentTestType(enrollment, user,
	    				testTypesMap.get(kidRecord.getStateHistGovAssessment()).getId(), (subjectAreaMap.get("SHISGOVA")).getId(),
	    				kidRecord.getGroupingInd1HistGov(), kidRecord.getGroupingInd2HistGov());
	    	}        	
	    }
	  @Transactional(readOnly=false, propagation=Propagation.REQUIRED, isolation = Isolation.DEFAULT)
	  private void addOrUpdateEnrollmentTestType(Enrollment enrollment, 
				User user, Long testTypeId, Long subjectAreaId, String groupingInd1, String groupingInd2) {
			
		  	boolean isInsert = false;
		  	
			EnrollmentTestTypeSubjectArea enrollmentTestTypeSubjectArea = new EnrollmentTestTypeSubjectArea();
			enrollmentTestTypeSubjectArea.setEnrollmentId(enrollment.getId());
			enrollmentTestTypeSubjectArea.setTestTypeId(testTypeId);
			enrollmentTestTypeSubjectArea.setSubjectareaId(subjectAreaId);
			enrollmentTestTypeSubjectArea.setGroupingInd1(groupingInd1);
			enrollmentTestTypeSubjectArea.setGroupingInd2(groupingInd2);
			enrollmentTestTypeSubjectArea.setCurrentContextUserId(user.getId());
			enrollmentTestTypeSubjectArea.setAuditColumnPropertiesForUpdate();
			
			EnrollmentTestTypeSubjectAreaExample enrollmentTestTypeSubjectAreaExample = new EnrollmentTestTypeSubjectAreaExample();
			EnrollmentTestTypeSubjectAreaExample.Criteria enrollmentTestTypeSubjectAreaCriteria
			    = enrollmentTestTypeSubjectAreaExample.createCriteria();
			enrollmentTestTypeSubjectAreaCriteria.andEnrollmentIdEqualTo(enrollment.getId());
			enrollmentTestTypeSubjectAreaCriteria.andSubjectareaIdEqualTo(subjectAreaId);
			Integer updatedValue = enrollmentTestTypeSubjectAreaDao.updateByExampleSelective(
					enrollmentTestTypeSubjectArea, enrollmentTestTypeSubjectAreaExample);
			
			if(updatedValue == null || updatedValue < 1) {
				isInsert = true;
				enrollmentTestTypeSubjectArea.setAuditColumnProperties();
				enrollmentTestTypeSubjectAreaDao.insert(enrollmentTestTypeSubjectArea);
			}else{
			  List<EnrollmentTestTypeSubjectArea> enrollmentTestTypeSubjectAreaList = enrollmentTestTypeSubjectAreaDao.selectByExample(enrollmentTestTypeSubjectAreaExample);
			  enrollmentTestTypeSubjectArea = enrollmentTestTypeSubjectAreaList.get(0);
			}
			
			addtoDomainAuditHistory(enrollmentTestTypeSubjectArea.getId(),user.getId(),"TEC",isInsert);
			
		}
	  
	  @Transactional(readOnly = false, propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
		private void addtoDomainAuditHistory(Long objectId, Long createdUserId, String objectType,boolean isInsert){
			DomainAuditHistory domainAuditHistory = new DomainAuditHistory();
			
			domainAuditHistory.setSource(SourceTypeEnum.UPLOAD.getCode());
			domainAuditHistory.setObjectType(objectType);
			domainAuditHistory.setObjectId(objectId);
			domainAuditHistory.setCreatedUserId( createdUserId.intValue() );
			domainAuditHistory.setCreatedDate(new Date());
			domainAuditHistory.setAction(isInsert ? "INSERT":"UPDATE");
			
			domainAuditHistoryDao.insert(domainAuditHistory);
		}
}
