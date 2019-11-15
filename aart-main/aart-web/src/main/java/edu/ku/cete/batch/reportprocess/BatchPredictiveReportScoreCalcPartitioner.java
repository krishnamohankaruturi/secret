/**
 * 
 */
package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.RawToScaleScoresService;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 7, 2017 3:38:17 PM
 */
public class BatchPredictiveReportScoreCalcPartitioner implements Partitioner {

	private final static Log logger = LogFactory .getLog(BatchPredictiveReportScoreCalcPartitioner.class);
	
	@Autowired
    private TestTypeService testTypeService;
    
    @Autowired
    private GradeCourseService gradeCourseService;
	
	@Autowired
	private ContentAreaService caService;
	
    @Autowired
    private AssessmentService assessmentService;
    
    @Autowired
    private OrganizationService orgService;
	
	@Autowired
	private TestingProgramService testingProgramService;
	
	@Autowired
	private CategoryService categoryService;
	
	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;
	
	@Autowired
	private BatchReportProcessService batchReportProcessService;	
	
	private Long assessmentProgramId;
	private Long testTypeId;
	private Long contentAreaId;
	private Long gradeCourseId;
	private Long assessmentId;
	private Long studentId;
	private Long batchReportProcessId;
	private StepExecution stepExecution;
	private String processByStudentId;
	private String reportCycle;
	private Long testingProgramId;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchReportProcessPartitioner partition size : "+gridSize);
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		Category testStatusCompletedCategory = categoryService.selectByCategoryCodeAndType("complete",  "STUDENT_TEST_STATUS");
		Category testStatusInProgressCategory = categoryService.selectByCategoryCodeAndType("inprogress",  "STUDENT_TEST_STATUS");
		Category testStatusUnusedCategory = categoryService.selectByCategoryCodeAndType("unused",  "STUDENT_TEST_STATUS");
		Category testStatusPendingCategory = categoryService.selectByCategoryCodeAndType("pending",  "STUDENT_TEST_STATUS");
		Category testStatusInProgressTimedOutCategory = categoryService.selectByCategoryCodeAndType("inprogresstimedout",  "STUDENT_TEST_STATUS");
		Category reasonIdStudentNotTested = categoryService.selectByCategoryCodeAndType("STUDENT_NOT_TESTED",  "NO_SCORE_RANGE_REASON");
		Category reasonIdTestNotCompleted = categoryService.selectByCategoryCodeAndType("TEST_NOT_COMPLETED",  "NO_SCORE_RANGE_REASON");
		Category noCreditCategory = categoryService.selectByCategoryCodeAndType("NO_CREDIT",  "CREDIT_EARNED");
		Category partialCreditCategory = categoryService.selectByCategoryCodeAndType("PARTIAL_CREDIT",  "CREDIT_EARNED");
		Category fullCreditCategory = categoryService.selectByCategoryCodeAndType("FULL_CREDIT",  "CREDIT_EARNED");
		Category questionUnAsnweredCategory = categoryService.selectByCategoryCodeAndType("QUESTION_UNANSWERED",  "CREDIT_EARNED");
		
		Boolean isTestingWindowComplete = Boolean.FALSE;
		Long operationTestWindowId = null;
		if(testingProgramId == null){
			List<TestingProgram> testingPrograms = testingProgramService.getByAssessmentProgIdAndTestingProgAbbr(assessmentProgramId, "F");
			testingProgramId = testingPrograms.get(0).getId();
		}
		
		List<Organization> contractingOrgs = orgService.getContractingOrgsForPredictiveReports(assessmentProgramId, testingProgramId, reportCycle);
				
		if(CollectionUtils.isNotEmpty(contractingOrgs)){
			operationTestWindowId = contractingOrgs.get(0).getOperationalWindowId();
			/*check for testing window is completed or still open, this will be used in determining pulling students and responses.
			 * Within the window, only pull the students with tests in completed status and who did not get a report
			 * After the window, pull all the students who got a predictive test(doesn't have to be in complete status) and did not get a report 
			 */			
			 if(operationTestWindowId != null){
				 if(new Date().getTime() > contractingOrgs.get(0).getOperationalTestWindowExpiryDate().getTime()){
					 isTestingWindowComplete = true;
				 }
			 }
		}
		 
		
		List<Assessment> assessments = getAssessments(testingProgramId);
		
		if(CollectionUtils.isEmpty(assessments)){
			writeReason("No assessments found");
		}
		
		logger.debug("batchReportProcessId - " + batchReportProcessId + " , assessments found - " + assessments.size());
		for(Assessment assessment: assessments) {
			List<TestType> testTypes = getTestTypes(assessment);
			if(CollectionUtils.isEmpty(testTypes)){
				writeReason(String.format("No Test Types found for assessment: %d (%s)", assessment.getId(), assessment.getAssessmentCode()));
			}
			logger.debug("batchReportProcessId - " + batchReportProcessId + " , Test Type found - " + testTypes.size());
			for(TestType testType: testTypes) {
				List<ContentArea> contentAreas = getContentAreas(testType,assessment);
				if(CollectionUtils.isNotEmpty(contentAreas)){
					logger.debug("batchReportProcessId - " + batchReportProcessId + " , contentAreas found - " + contentAreas.size());
					for (ContentArea contentArea : contentAreas) {
						List<GradeCourse> gradeCourses = getGradeCourses(testType, contentArea, assessment);
						if(CollectionUtils.isNotEmpty(gradeCourses)){ 
							logger.debug("batchReportProcessId - " + batchReportProcessId + " , gradeCourses found - " + gradeCourses.size());
							for(GradeCourse gradeCourse: gradeCourses) {
								for(Organization contractingOrg: contractingOrgs) {
									logger.debug("processing contracting organization: "+contractingOrg.getId() +" ("+contractingOrg.getDisplayIdentifier()+")");
									
									List<Long> rawScaleExternalTestIds = rawToScaleScoresService.selectDistinctTestIds(assessmentProgramId, contentArea.getId(), gradeCourse.getId(), contractingOrg.getCurrentSchoolYear(), testingProgramId, reportCycle);
									
									List<Long> studentIdListForReprocess = null;
									if("TRUE".equals(processByStudentId)){
										studentIdListForReprocess = batchReportProcessService.getStudentsForReportProcessByStudentId(assessmentProgramId, contentArea.getId(), gradeCourse.getId(), contractingOrg.getCurrentSchoolYear(), testingProgramId);
									}
									ExecutionContext context = new ExecutionContext();
									context.put("assessment", assessment.getId());
									context.put("testType", testType.getId());
									context.put("gradeCourseId", gradeCourse.getId());
									context.put("contentAreaId", contentArea.getId());
									context.put("contractingOrganization", contractingOrg);
									context.put("rawScaleExternalTestIds", rawScaleExternalTestIds);
									context.put("studentIdListForReprocess", studentIdListForReprocess);
									context.put("testsCompletedStatusId", testStatusCompletedCategory.getId());
									context.put("testsInprogressStatusId", testStatusInProgressCategory.getId());
									context.put("testStatusUnusedId", testStatusUnusedCategory.getId());
									context.put("testStatusPendingId", testStatusPendingCategory.getId());
									context.put("testStatusInprogressTimedoutId", testStatusInProgressTimedOutCategory.getId());
									context.put("contentAreaAbbreviatedName", contentArea.getAbbreviatedName());
									context.put("reportCycle", reportCycle);
									context.put("operationalTestWindowId", operationTestWindowId);
									context.put("isTestingWindowComplete", isTestingWindowComplete);
									context.put("studentNotTestedReasonId", reasonIdStudentNotTested.getId());
									context.put("testNotCompletedReasonId", reasonIdTestNotCompleted.getId());
									context.put("testingProgramId", testingProgramId);
									context.put("noCreditCategoryId", noCreditCategory.getId());
									context.put("partialCreditCategoryId", partialCreditCategory.getId());
									context.put("fullCreditCategoryId", fullCreditCategory.getId());
									context.put("unAnsweredCategoryId", questionUnAsnweredCategory.getId());
									context.put("jobStartDate", new Date());
									
									partitionMap.put(contractingOrg.getId()+"_"+ reportCycle +"_"+assessment.getAssessmentCode()+"_"+contentArea.getAbbreviatedName() + "_" + gradeCourse.getAbbreviatedName(), 
											context );							
								}						
							}
						}
					}
				}
			}
		}
		logger.debug("Created "+partitionMap.size()+" partitions.");
		return partitionMap;
	}
	

	private void writeReason(String msg) {
		logger.debug(msg);
		
	}

	private List<GradeCourse> getGradeCourses(TestType testType, ContentArea contentArea, Assessment assessment) {
		List<GradeCourse> gradeCourses = gradeCourseService.getGradeCourseForAuto(contentArea.getId(), testType.getId(), assessment.getId());
		if(gradeCourseId != null) {
			GradeCourse selectedGC = null;
			for(GradeCourse gc: gradeCourses) {
				if(gradeCourseId.equals(gc.getId())) {
					selectedGC = gc;
					break;
				}
			}
			gradeCourses.clear();
			if(selectedGC != null){
				gradeCourses.add(selectedGC);
			}
		}
		return gradeCourses;
	}
	
	private List<ContentArea> getContentAreas(TestType testType, Assessment assessment) {
		List<ContentArea> contentAreas = caService.getByTestTypeAssessment(testType.getId(), assessment.getId());
		if(contentAreaId != null) {
			ContentArea selectedCA = null;
			for(ContentArea ca: contentAreas) {
				if(contentAreaId.equals(ca.getId())) {
					selectedCA = ca;
					break;
				}
			}
			contentAreas.clear();
			if(selectedCA != null){
				contentAreas.add(selectedCA);
			}
		}
		return contentAreas;
	}

	private List<TestType> getTestTypes(Assessment assessment) {
		List<TestType> testTypes = null;
		if(testTypeId == null) {
			testTypes = testTypeService.getTestTypeByAssessmentId(assessment.getId());
		} else {
			testTypes = new ArrayList<TestType>();
			testTypes.add(testTypeService.getById(testTypeId));
		}
		return testTypes;
	}

	private List<Assessment> getAssessments(Long testingProgramId) {
		List<Assessment> assessments = null;
		
		if(assessmentId == null) {
			assessments = assessmentService.getAssessmentsForAutoRegistration(testingProgramId, assessmentProgramId);
		} else {
			assessments = new ArrayList<Assessment>();
			assessments.add(assessmentService.getById(assessmentId));
		}
		
		return assessments;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getTestTypeId() {
		return testTypeId;
	}

	public void setTestTypeId(Long testTypeId) {
		this.testTypeId = testTypeId;
	}

	public Long getGradeCourseId() {
		return gradeCourseId;
	}

	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}

	public Long getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(Long assessmentId) {
		this.assessmentId = assessmentId;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}

	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}


	public String getProcessByStudentId() {
		return processByStudentId;
	}


	public void setProcessByStudentId(String processByStudentId) {
		this.processByStudentId = processByStudentId;
	}


	public String getReportCycle() {
		return reportCycle;
	}


	public void setReportCycle(String reportCycle) {
		this.reportCycle = reportCycle;
	}


	public Long getTestingProgramId() {
		return testingProgramId;
	}


	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}
}
