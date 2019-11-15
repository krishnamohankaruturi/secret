package edu.ku.cete.controller;

import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.ku.cete.batch.BatchRegistrationProcessStarter;
import edu.ku.cete.domain.BatchRegistrationDTO;
import edu.ku.cete.domain.TestEnrollmentMethod;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.domain.student.Student;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.report.domain.BatchRegistration;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.service.ComplexityBandService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.StudentService;
import edu.ku.cete.service.StudentsTestsService;
import edu.ku.cete.service.SubjectAreaService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.util.CommonConstants;
import edu.ku.cete.util.SourceTypeEnum;


@Controller
public class BatchRegistrationController {
	
    private Logger logger = LoggerFactory.getLogger(BatchRegistrationController.class);

    @Autowired
    private TestTypeService testTypeService;
    
    @Autowired
    private ContentAreaService contentAreaService;
    
    @Autowired
    private GradeCourseService gradeCourseService;
    
    @Autowired
    private AssessmentProgramService assessmentProgramService;
    
    @Autowired
    private TestingProgramService testingProgramService;
    
    @Autowired
    private AssessmentService assessmentService;
    
    @Autowired
    private EnrollmentService enrollmentService;
    
    @Autowired
    private BatchRegistrationProcessor batchRegistrationProcessor;
    
    @Autowired
    private StudentsTestsService studentsTestsService;
    
    @Autowired
    private BatchRegistrationService batchRegistrationService;
    
    @Autowired
    private ComplexityBandService complexityBandService;
    
    @Autowired
	private OrganizationService organizationService;
    
    @Autowired
   	private BatchRegistrationProcessStarter starter;
    
    @Autowired
    private GradeCourseService gradeCouseService;
    
    @Autowired
    private SubjectAreaService subjectAreaService;
    
    @Autowired
    private StudentService studentService;
    
    @Value("${user.organization.DLM}")
    private String dlmOrgName;
    
    @Autowired
    private BatchRegistrationService brService;
    
	@RequestMapping(value = "getAssessmentProgramsForAutoRegistration.htm", method = RequestMethod.GET)
	public final @ResponseBody List<AssessmentProgram> getAssessmentProgramsForAutoRegistration(){
		logger.trace("Entering getAssessmentProgramsForAutoRegistration");
		List<AssessmentProgram> assessmentPrograms = assessmentProgramService.selectAssessmentProgramsForAutoRegistration();
		logger.trace("Leaving getAssessmentProgramsForAutoRegistration");
		return assessmentPrograms;
	}
	
	@RequestMapping(value = "getTestingProgramsForAutoRegistration.htm", method = RequestMethod.GET)
	public final @ResponseBody List<TestingProgram> getTestingProgramsForAutoRegistration(@RequestParam("assessmentProgramId")  Long assessmentProgramId){
		logger.trace("Entering getTestingProgramsForAutoRegistration");
		List<TestingProgram> testingPrograms = testingProgramService.selectTestingtProgramsForAutoRegistration(assessmentProgramId);
		logger.trace("Leaving getTestingProgramsForAutoRegistration");
		return testingPrograms;
	}
	
	@RequestMapping(value = "getAssessmentsForAutoRegistration.htm", method = RequestMethod.GET)
	public final @ResponseBody List<Assessment> getAssessmentsForAutoRegistration(@RequestParam("testingProgramId") Long testingProgramId, 
			@RequestParam("assessmentProgramId")  Long assessmentProgramId){
		logger.trace("Entering getAssessmentsForAutoRegistration");
		List<Assessment> testTypes = assessmentService.getAssessmentsForAutoRegistration(testingProgramId,assessmentProgramId);
		logger.trace("Leaving getAssessmentsForAutoRegistration");
		return testTypes;
	}
	
	@RequestMapping(value = "getTestTypeByAssessmentId.htm", method = RequestMethod.GET)
	public final @ResponseBody List<TestType> getTestTypeByAssessmentId(@RequestParam("assessmentId") Long assessmentId ){
		logger.trace("Entering getTestTypeByAssessmentId");
		List<TestType> testTypes = testTypeService.getTestTypeByAssessmentId(assessmentId);
		logger.trace("Leaving getTestTypeByAssessmentId");
		return testTypes;
	}

	@RequestMapping(value = "getContentAreaByTestType.htm", method = RequestMethod.GET)
	public final @ResponseBody List<ContentArea> getContentAreaByTestType(@RequestParam("testTypeId") Long testTypeId,
			@RequestParam("assessmentId") Long assessmentId){
		logger.trace("Entering getContentAreaByTestType");
		List<ContentArea> contentAreas = contentAreaService.getByTestTypeAssessment(testTypeId, assessmentId);
		logger.trace("Leaving getContentAreaByTestType");
		return contentAreas;
	}
	
	@RequestMapping(value = "getGradeCoursesByTestTypeAndContentArea.htm", method = RequestMethod.GET)
	public final @ResponseBody List<GradeCourse> getGradeCoursesByTestTypeAndContentArea(@RequestParam("contentAreaId") Long contentAreaId,
			@RequestParam("testTypeId") Long testTypeId,@RequestParam("assessmentId") Long assessmentId){
		logger.trace("Entering getGradeCoursesByTestTypeAndContentArea");
		List<GradeCourse> gradeCourses = gradeCourseService.getGradeCourseForAuto(contentAreaId, testTypeId, assessmentId);
		logger.trace("Leaving getGradeCoursesByTestTypeAndContentArea");
		return gradeCourses;
	}
	
	@RequestMapping(value = "getGradesByAssessmentProgramId.htm", method = RequestMethod.GET)
	public final @ResponseBody List<GradeCourse> getGradesByAssessmentProgramId(@RequestParam("assessmentProgramId") Long assessmentProgramId){
		logger.trace("Entering getGradesByAssessmentProgramId");
		AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
		List<GradeCourse> gradeCourses = null;
		// I-Smart is grade banded, not grade coursed. existing query couldn't be modified to handle without changing DLM result
		if(CommonConstants.ASSESSMENT_PROGRAM_I_SMART.equalsIgnoreCase(ap.getAbbreviatedname())
				|| CommonConstants.ASSESSMENT_PROGRAM_I_SMART2.equalsIgnoreCase(ap.getAbbreviatedname())){
			gradeCourses = gradeCourseService.getDistinctGradesByAssessmentPrgmIdBanded(assessmentProgramId);
		}else {
			gradeCourses = gradeCourseService.getDistinctGradesByAssessmentPrgmId(assessmentProgramId);
		}
		if(CollectionUtils.isNotEmpty(gradeCourses)) {
			Collections.sort(gradeCourses, new Comparator<GradeCourse>() {
				
	            @Override
	            public int compare(GradeCourse o1, GradeCourse o2) {
	            	return o2.getName().compareTo(o1.getName());
	            }
	        });
		}		
		logger.trace("Leaving getGradesByAssessmentProgramId");
		return gradeCourses;
	}	
	
	@RequestMapping(value = "getTestEnrollmentMethods.htm", method = RequestMethod.GET)
	public final @ResponseBody List<TestEnrollmentMethod> getTestEnrollmentMethods(@RequestParam("assessmentProgramId") Long assessmentProgramId){
		logger.trace("Entering getTestEnrollmentMethods");
	
		List<TestEnrollmentMethod> testWindows = batchRegistrationService.getTestEnrollmentMethods(assessmentProgramId);
		
		//DO not allow "MLTASGNFT" for UI run. Only allowed to be part of StudentTracker job
		CollectionUtils.filter(testWindows, new Predicate() {
		    @Override
		    public boolean evaluate(Object o) {
		    	TestEnrollmentMethod m = (TestEnrollmentMethod) o;
		    	if(m.getMethodCode().equalsIgnoreCase("MLTASGNFT")) {
		    		return false;
		    	}
		        return true;
		    }
		});
		logger.trace("Leaving getTestEnrollmentMethods");
		
		return testWindows;
	}
	
	@RequestMapping(value = "getTestWindows.htm", method = RequestMethod.GET)
	public final @ResponseBody List<OperationalTestWindow> getTestWindows(@RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("autoEnrollmentMethodId") Long autoEnrollmentMethodId){
		logger.trace("Entering getTestWindows");
	
		List<OperationalTestWindow> testWindows = batchRegistrationService.getTestWindowsForBatchRegistration(assessmentProgramId, autoEnrollmentMethodId);
		logger.trace("Leaving getTestWindows");
		
		return testWindows;
	}
	
	@RequestMapping(value = "processBatchRegistration.htm", method = RequestMethod.POST)
	public final @ResponseBody Map<String, String> processBatchRegistration( @RequestParam("assessmentProgramId") Long assessmentProgramId,
			@RequestParam("testTypeId") Long testTypeId, @RequestParam("subjectAreaId") Long contentAreaId,
			@RequestParam("gradeCourseId") Long gradeCourseId, Long assessmentId, Long testingProgramId, @RequestParam("testEnrollmentMethodId") Long testEnrollmentMethodId, 
			@RequestParam("testWindowId") Long testWindowId, @RequestParam("dlmFixedGradeAbbrName") String dlmFixedGradeAbbrName,HttpServletRequest request) throws Exception{
		logger.trace("Entering processBatchRegistration");
		
		boolean breakDayAlaska = false;
		AssessmentProgram ap = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
		TestingProgram tp = testingProgramService.getByTestingProgramId(testingProgramId);
		
		// If assessmentprogram == KAP && testingprogram == Formative -- invoke old batch process
		/*if(null != ap && null != tp && null != ap.getAbbreviatedname() 
				&& null != tp.getProgramAbbr() && ap.getAbbreviatedname().equalsIgnoreCase("KAP") 
				&& tp.getProgramAbbr().equalsIgnoreCase("F")){
			
			breakDayAlaska = true;
		}*/
		UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userDetails.getUser();
		Map<String, String> result = new HashMap<String, String>();
		if(!breakDayAlaska) {
			
			BatchRegistration brRecord = new BatchRegistration();
			//brRecord.setAssessmentProgram(params.getLong("assessmentProgramId"));
			brRecord.setSubmissionDate(new Date());
			brRecord.setStatus("IN-PROGRESS");
			brRecord.setSuccessCount(0);
			brRecord.setFailedCount(0);
			brRecord.setBatchTypeCode(SourceTypeEnum.BATCHAUTO.getCode());
			if(null != assessmentProgramId) {
				brRecord.setAssessmentProgram(assessmentProgramId);
			}
			if(null != testingProgramId) {
				brRecord.setTestingProgram(testingProgramId);
			}
			if(null != assessmentId) {
				brRecord.setAssessment(assessmentId);
			}
			if(null !=gradeCourseId) {
				brRecord.setGrade(gradeCourseId);
			}
			if(null != contentAreaId) {
				brRecord.setContentAreaId(contentAreaId);
			}
			if(null != testTypeId) {
				brRecord.setTestType(testTypeId);
			}
			//US16879		
			if(null != testWindowId) {
				brRecord.setOperationalTestWindowId(testWindowId);
			}
			if(null != testEnrollmentMethodId) {
				brRecord.setAutoEnrollmentMethodId(testEnrollmentMethodId);
			}
			brRecord.setCreatedDate(new Date());
			brRecord.setModifiedDate(new Date());
			brRecord.setCreatedUser(user.getId());
			brRecord.setStatus("IN-PROGRESS");
			brRecord.setBatchTypeCode("BATCHUIJOB");
			brService.insertSelectiveBatchRegistration(brRecord);
			
			Long jobId = starter.startBatchRegProcess(assessmentProgramId, testingProgramId, assessmentId, testTypeId, contentAreaId, gradeCourseId, testEnrollmentMethodId, testWindowId, dlmFixedGradeAbbrName, brRecord.getId());
			logger.trace("Leaving processBatchRegistration");
			result.put("status", "IN-PROGRESS");
			result.put("jobId", String.valueOf(brRecord.getId()));
		} else {
			// This logic is very specific to Alaska Break Day
/*			List<Organization> orgs = organizationService.getByDisplayIdentifier("AK");
			Organization alaskaOrg = null;
			for (Organization org : orgs) {
				if(org.getContractingOrganization()) {
					alaskaOrg = org;
					break;
				}
			}*/
			
    		
            Organization contractingOrg = user.getContractingOrganization();
			//Which test type and gradecourse
			//if(null != alaskaOrg) {    		
    			Future<Map<String, Object>> batchRegStatus = batchRegistrationProcessor.startBatchRegProcess(assessmentProgramId, testTypeId, null, gradeCourseId, assessmentId, testingProgramId, contractingOrg.getId());
        		HttpSession session = request.getSession();
            	session.setAttribute("batchRegStatus", batchRegStatus);
				result.put("status", "INPROCESS");
				result.put("jobId", String.valueOf(-1));
			//}
		}

		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "monitorBatchRegistration.htm", method = RequestMethod.GET)
	public final @ResponseBody Map<String, String> monitorBatchRegistration(HttpServletRequest request, @RequestParam("jobId") Long jobId){
		logger.trace("Entering monitorBatchRegistration");
		Map<String, String> responseResult = new HashMap<String, String>();
		
		if(jobId > -1) {
			BatchRegistration batchJobDetails = batchRegistrationService.getBatchRegistrationById(jobId);
			responseResult.put("status", batchJobDetails.getStatus());
		} else{		
			HttpSession session = request.getSession();
			Future<Map<String, Object>> batchRegStatus = (Future<Map<String, Object>>) session.getAttribute("batchRegStatus");
			if (batchRegStatus != null) {
				if (batchRegStatus.isDone() || batchRegStatus.isCancelled()) {
					Map<String, Object> result;
					try {
						result = batchRegStatus.get();
						String status = String.valueOf(result.get("STATUS"));
						responseResult.put("status", status);
						if (status.equals("VALIDATIONERROR")) {
							responseResult.put("status", "Completed");
						}
						if (status.equals("COMPLETED")) {
							session.removeAttribute("batchRegStatus");
						}
					} catch (Exception e) {
						logger.error("Exception: ", e);
						responseResult.put("status", "INPROCESS");
					}
					
				} else {
					responseResult.put("status", "INPROCESS");
				}
			}
		}
    	logger.trace("Leaving processBatchRegistration");
		return responseResult;
	}
	
	@RequestMapping(value = "getBatchRegistrationHistory.htm", method = RequestMethod.GET)
	public final @ResponseBody List<BatchRegistrationDTO> getBatchRegistrationHistory(@RequestParam("fromDate") Date fromDate, @RequestParam("toDate") Date toDate ){
		logger.trace("Entering getBatchRegistrationHistory");
		List<BatchRegistrationDTO> batchHistory = batchRegistrationService.getBatchRegisteredHistory(fromDate, toDate);
		
		logger.trace("Leaving getBatchRegistrationHistory");
		return batchHistory;
	}
	
	@RequestMapping(value = "getBatchRegistrationErrorDetails.htm", method = RequestMethod.GET)
	public final @ResponseBody List<BatchRegistrationReason> getBatchRegistrationErrorDetails(@RequestParam("batchid") long batchid){
		logger.trace("Entering getBatchRegistrationErrorDetails");
		List<BatchRegistrationReason> batchErrorDetails = batchRegistrationService.getByBatchRegistrationId(batchid);
		
		if(batchErrorDetails != null && batchErrorDetails.size() > 0){
			for(BatchRegistrationReason reason : batchErrorDetails){
				if(reason != null && reason.getStudentId() != null && reason.getStudentId() >0){
					Student student = studentService.getByStudentID(reason.getStudentId());
					reason.setLastName(student.getLegalLastName());
					reason.setFirstName(student.getLegalFirstName());
				}
			}
		}
		
		logger.trace("Leaving getBatchRegistrationErrorDetails");
		return batchErrorDetails;
	}
	
	@RequestMapping(value = "checkMathAndElaLimits.htm", method = RequestMethod.GET)
	public final @ResponseBody boolean checkMathAndElaLimits(){
		boolean valid = true;
        if (complexityBandService.getElaSessionLimit() == null || complexityBandService.getMathSessionLimit() == null){
        	valid = false;
        }
        return valid;
	}
	
	//do check on the pools for each state and send error message if none are set or for each state if some are set
	@RequestMapping(value = "checkPoolTypes.htm", method = RequestMethod.GET)
	public final @ResponseBody List<String> checkPoolTypes(){
		return complexityBandService.checkPoolTypes();
	}
}
