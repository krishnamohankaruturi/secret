package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
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
import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.domain.report.ReportSubscores;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.ExcludedItemsService;
import edu.ku.cete.service.report.RawToScaleScoresService;
import edu.ku.cete.service.report.SubscoreRawToScaleScoresService;

public class BatchReportProcessPartitioner implements Partitioner {

	private final static Log logger = LogFactory .getLog(BatchReportProcessPartitioner.class);
	
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
	private SubscoreRawToScaleScoresService subscoreRawToScaleScoresService;
	
	@Autowired
	private BatchReportProcessService batchReportProcessService;
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private ExcludedItemsService excludedItemsService;
	
	private Long assessmentProgramId;
	private Long testTypeId;
	private Long contentAreaId;
	private Long gradeCourseId;
	private Long assessmentId;
	private Long studentId;
	private Long batchReportProcessId;
	private StepExecution stepExecution;
	private String processByStudentId;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchReportProcessPartitioner partition size : "+gridSize);
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		Category testStatusCompletedCategory = categoryService.selectByCategoryCodeAndType("complete",  "STUDENT_TEST_STATUS");
		Category testStatusInProgressCategory = categoryService.selectByCategoryCodeAndType("inprogress",  "STUDENT_TEST_STATUS");
		Category testStatusUnusedCategory = categoryService.selectByCategoryCodeAndType("unused",  "STUDENT_TEST_STATUS");
		Category testStatusPendingCategory = categoryService.selectByCategoryCodeAndType("pending",  "STUDENT_TEST_STATUS");
		Category testStatusInProgressTimedOutCategory = categoryService.selectByCategoryCodeAndType("inprogresstimedout",  "STUDENT_TEST_STATUS");		
		Category testSectionStatusCompletedCategory = categoryService.selectByCategoryCodeAndType("complete",  "STUDENT_TESTSECTION_STATUS");
		Category testSectionStatusInProgressCategory = categoryService.selectByCategoryCodeAndType("inprogress",  "STUDENT_TESTSECTION_STATUS");
		Category testSectionStatusUnusedCategory = categoryService.selectByCategoryCodeAndType("unused",  "STUDENT_TESTSECTION_STATUS");
		Category specialCircumstanceStatusSavedCategory = categoryService.selectByCategoryCodeAndType("SAVED",  "SPECIAL CIRCUMSTANCE STATUS");
		Category specialCircumstanceStatusApprovedCategory = categoryService.selectByCategoryCodeAndType("APPROVED",  "SPECIAL CIRCUMSTANCE STATUS");
		Category scoringCompletedCategory = categoryService.selectByCategoryCodeAndType("COMPLETED","SCORING_STATUS");
		Category testStatusDeployedCategory = categoryService.selectByCategoryCodeAndType("DEPLOYED","TESTSTATUS");
		Category enableTroubleshootingCategory = categoryService.selectByCategoryCodeAndType("ENABLE_TROUBLESHOOTING_QUERIES","TROUBLESHOOT_REPORTING");
		
		List<Organization> contractingOrgs = orgService.getContractingOrgsByAssessmentProgramId(assessmentProgramId);
		List<TestingProgram> testingPrograms = testingProgramService.getByAssessmentProgIdAndTestingProgAbbr(assessmentProgramId, "S");
		
		List<Assessment> assessments = getAssessments(testingPrograms.get(0).getId());
		
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
									
									List<Long> rawScaleExternalTestIds = rawToScaleScoresService.selectDistinctTestIds(assessmentProgramId, contentArea.getId(), gradeCourse.getId(), Long.valueOf(contractingOrg.getReportYear()), testingPrograms.get(0).getId(), null);
									List<Long> subscoreRawScaleExternalTestIds = subscoreRawToScaleScoresService.selectDistinctTestIds(assessmentProgramId, contentArea.getId(), gradeCourse.getId(), Long.valueOf(contractingOrg.getReportYear()));
									List<ExcludedItems> excludedItems= excludedItemsService.getByTaskVariantIds(assessmentProgramId, contentArea.getId(), gradeCourse.getId(),
											Long.valueOf(contractingOrg.getReportYear()), null, null, null);
									
									//Constructing a map subscores for each possible testid so that it will not be executed for each kid in subscores calcualtions
									Map<Long, List<ReportSubscores>> testLevelSubscoreBucketMap = new HashMap<Long, List<ReportSubscores>>();
									
									if(CollectionUtils.isNotEmpty(subscoreRawScaleExternalTestIds)){
										List<Long> publisedTestIds = null;
										List<ReportSubscores> testLevelSubscoresList = null;
										for(Long externalTestId : subscoreRawScaleExternalTestIds){
											publisedTestIds = testService.getAllPublishedTestByExternalId(externalTestId, testStatusDeployedCategory.getId());
											
											if(CollectionUtils.isNotEmpty(publisedTestIds)){
												for(Long testId : publisedTestIds){
													testLevelSubscoresList = new ArrayList<ReportSubscores>();
													testLevelSubscoresList = batchReportProcessService.getItemCountBySubscoreDefinitionNameByTestId(testId, Long.valueOf(contractingOrg.getReportYear()), 
															assessmentProgramId, contentArea.getId(), gradeCourse.getId());
													if(testLevelSubscoresList != null && testLevelSubscoresList.size() > 0){
														testLevelSubscoreBucketMap.put(testId, testLevelSubscoresList);
													}
												}
											}
											
										}
									}
									
									List<Long> studentIdListForReprocess = null;
									if("TRUE".equals(processByStudentId)){
										studentIdListForReprocess = batchReportProcessService.getStudentsForReportProcessByStudentId(assessmentProgramId, contentArea.getId(), gradeCourse.getId(), Long.valueOf(contractingOrg.getReportYear()), testingPrograms.get(0).getId());
									}
									ExecutionContext context = new ExecutionContext();
									context.put("assessment", assessment.getId());
									context.put("testType", testType.getId());
									context.put("gradeCourseId", gradeCourse.getId());
									context.put("contentAreaId", contentArea.getId());
									context.put("contractingOrganization", contractingOrg);
									context.put("rawScaleExternalTestIds", rawScaleExternalTestIds);
									context.put("subscoreRawScaleExternalTestIds", subscoreRawScaleExternalTestIds);
									context.put("studentIdListForReprocess", studentIdListForReprocess);
									context.put("testsCompletedStatusId", testStatusCompletedCategory.getId());
									context.put("testsInprogressStatusId", testStatusInProgressCategory.getId());
									context.put("testStatusUnusedId", testStatusUnusedCategory.getId());
									context.put("testStatusPendingId", testStatusPendingCategory.getId());
									context.put("testStatusInprogressTimedoutId", testStatusInProgressTimedOutCategory.getId());
									context.put("testSectionStatusCompletedId", testSectionStatusCompletedCategory.getId());
									context.put("testSectionStatusInProgressId", testSectionStatusInProgressCategory.getId());
									context.put("testSectionStatusUnusedId", testSectionStatusUnusedCategory.getId());
									context.put("scoringStatusCompletedId", scoringCompletedCategory.getId());
									context.put("specialCircumstanceStatusSavedId", specialCircumstanceStatusSavedCategory.getId());
									context.put("specialCircumstanceStatusApprovedId", specialCircumstanceStatusApprovedCategory.getId());
									context.put("contentAreaAbbreviatedName", contentArea.getAbbreviatedName());
									context.put("testingProgramId", testingPrograms.get(0).getId());
									context.put("gradeCourseAbbreviatedName", gradeCourse.getAbbreviatedName());
									context.put("testLevelSubscoreBucketMap", testLevelSubscoreBucketMap);
									context.put("enableTroubleShooting", enableTroubleshootingCategory.getCategoryName());
									context.put("excludedItems", excludedItems);
									
									partitionMap.put(contractingOrg.getId()+"_"+assessment.getAssessmentCode()+"_"+contentArea.getAbbreviatedName() + "_" + gradeCourse.getAbbreviatedName(), 
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
}
