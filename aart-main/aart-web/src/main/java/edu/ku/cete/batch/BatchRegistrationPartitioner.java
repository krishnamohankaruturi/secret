package edu.ku.cete.batch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.OperationalTestWindow;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.Stage;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.BatchRegistrationService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestSessionService;
import edu.ku.cete.service.TestTypeService;

public class BatchRegistrationPartitioner implements Partitioner {

	private final static Log logger = LogFactory .getLog(BatchRegistrationPartitioner.class);
	
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
	private TestCollectionService tcService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private BatchRegistrationService brService;
	
	@Autowired
	private TestSessionService testSessionService;
	
	private static final String ENROLLMENT_RANDOMIZATION_TYPE = "enrollment";
	private static final String ENROLLMENT_ADAPTIVE = "ADP";

	private StepExecution stepExecution;
	private String assessmentProgramCode;
	private Long assessmentProgramId;
	private Long testingProgramId;
	private Long testTypeId;
	private Long contentAreaId;
	private Long gradeCourseId;
	private Long assessmentId;
	private Long studentId;
	private Long batchRegistrationId;
	private Long operationalTestWindowId;
	private String enrollmentMethod;
	private Long enrollmentMethodId;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchRegistrationPartitioner partition size : "+gridSize);
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		
		List<Long> operationalTestWindowIds = getOperationalTestWindowIds();
		Long operationalTestWindowId = null;
		if (operationalTestWindowIds.isEmpty()) {
			writeReason("No operational test windows found");
		} else {
			Random rand = new Random();
			operationalTestWindowId = operationalTestWindowIds.get(rand.nextInt(operationalTestWindowIds.size())); // grab 1
		
			List<Organization> contractingOrgs = orgService.getStatesByOperationalTestWindowId(operationalTestWindowId);
	
			List<Assessment> assessments = getAssessments();
			if(CollectionUtils.isEmpty(assessments)){
				writeReason("No assessments found");
			}
			logger.debug("batchRegistrationId - " + batchRegistrationId + " , assessments found - " + assessments.size());
			for(Assessment assessment: assessments) {
				List<TestType> testTypes = getTestTypes(assessment);
				if(CollectionUtils.isEmpty(testTypes)){
					writeReason(String.format("No Test Types found for assessment: %d (%s)", assessment.getId(), assessment.getAssessmentCode()));
				}
				logger.debug("batchRegistrationId - " + batchRegistrationId + " , Test Type found - " + testTypes.size());
				for(TestType testType: testTypes) {
					List<ContentArea> contentAreas = getContentAreas(testType,assessment);
					if(CollectionUtils.isEmpty(contentAreas)){
						writeReason(String.format("No Content Areas found for assessment: %d (%s), testtype: %d (%s)",
								assessment.getId(), assessment.getAssessmentCode(), testType.getId(), testType.getTestTypeCode()));
					}
					logger.debug("batchRegistrationId - " + batchRegistrationId + " , contentAreas found - " + contentAreas.size());
					for (ContentArea contentArea : contentAreas) {
						List<Stage> stages;
						Stage stage = null;
						if (assessmentProgramCode.equalsIgnoreCase("CPASS")) {
							stages = getStages(contentArea, testType, assessment);
							if (CollectionUtils.isNotEmpty(stages)) {
								stage = stages.get(0);
							}
						}
						List<GradeCourse> gradeCourses = getGradeCourses(testType, contentArea, assessment);
						if(CollectionUtils.isEmpty(gradeCourses)){
							writeReason(String.format("No Grade Courses found for assessment: %d (%s), testtype: %d (%s), contentarea: %d (%s)",
									assessment.getId(), assessment.getAssessmentCode(), testType.getId(), testType.getTestTypeCode(),
									contentArea.getId(), contentArea.getAbbreviatedName()));
						}
						logger.debug("batchRegistrationId - " + batchRegistrationId + " , gradeCourses found - " + gradeCourses.size());
						for(GradeCourse gradeCourse: gradeCourses) {
							List<TestCollection> testCollections = new ArrayList<TestCollection>();
							testCollections.addAll(getTestCollections(assessment, testType, contentArea, gradeCourse, operationalTestWindowId, stage == null ? null : stage.getId()));
							boolean validTestCollections = false;
							if(CollectionUtils.isEmpty(testCollections)) {
								String reason = String.format("No test collections found for assessment: %d (%s), testtype: %d (%s), contentarea: %d (%s), gradecourse: %d (%s)", 
										assessment.getId(), assessment.getAssessmentCode(), testType.getId(), testType.getTestTypeCode(),
										contentArea.getId(), contentArea.getAbbreviatedName(), gradeCourse.getId(), gradeCourse.getAbbreviatedName());
								logger.debug("batchRegistrationId - " + batchRegistrationId + " "  + reason );
								writeReason(reason);
							} else if((assessmentProgramCode.equalsIgnoreCase("KAP") || assessmentProgramCode.equalsIgnoreCase("AMP")) && CollectionUtils.isNotEmpty(testCollections)) {
								List<Integer> missingStages = getMissingKAPStages(testCollections);
								if(CollectionUtils.isNotEmpty(missingStages)) {
									String reason = String.format("Test collections are missing for stages: %s, assessment: %d (%s), testtype: %d (%s), contentarea: %d (%s), gradecourse: %d (%s)", 
											missingStages, assessment.getId(), assessment.getAssessmentCode(), testType.getId(), testType.getTestTypeCode(),
											contentArea.getId(), contentArea.getAbbreviatedName(), gradeCourse.getId(), gradeCourse.getAbbreviatedName());
									logger.debug("batchRegistrationId - " + batchRegistrationId + " "  + reason );
									writeReason(reason);	
								} else {
									validTestCollections = CollectionUtils.isNotEmpty(testCollections);
								}
							} else {
								validTestCollections = CollectionUtils.isNotEmpty(testCollections);
							}
							if(validTestCollections) {
								if(assessmentProgramCode.equalsIgnoreCase("CPASS")){
									while(testCollections.size() > 1) { // remove any extra
										testCollections.remove(testCollections.size()-1);
									}
								}
								for(Organization contractingOrg: contractingOrgs) {
									logger.debug("processing contracting organization: "+contractingOrg.getId() +" ("+contractingOrg.getDisplayIdentifier()+")");
									List<Long> noEnrollmentOrgs = enrollmentService.getNoEnrollmentOrganizations(testType.getId(), contentArea.getId(), gradeCourse.getId(), 
											contractingOrg.getId(), assessment.getId(), contractingOrg.getCurrentSchoolYear());
									if(!noEnrollmentOrgs.isEmpty()) {
										writeReason(String.format("No enrollments found for schools: %s , currentschoolyear: %s, assessment: %d (%s), testtype: %d (%s), contentarea: %d (%s), gradecourse: %d (%s)", 
												noEnrollmentOrgs, contractingOrg.getCurrentSchoolYear(), assessment.getId(), 
												assessment.getAssessmentCode(), testType.getId(), testType.getTestTypeCode(),
												contentArea.getId(), contentArea.getAbbreviatedName(), gradeCourse.getId(), gradeCourse.getAbbreviatedName()));
									}
									ExecutionContext context = new ExecutionContext();
									context.put("assessment", assessment);
									context.put("testType", testType);
									context.put("gradeCourse", gradeCourse);
									context.put("contentArea", contentArea);
									context.put("contractingOrganization", contractingOrg);
									context.put("testCollections", testCollections);
									partitionMap.put(contractingOrg.getId()+"_"+assessment.getAssessmentCode()+"_"+testType.getTestTypeCode()+"_"+contentArea.getAbbreviatedName()+"_"+gradeCourse.getAbbreviatedName(),
											context);
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
	
	private List<Integer> getMissingKAPStages(List<TestCollection> testCollections) {
		List<Integer> dbStageOrders = new ArrayList<Integer>();
		for(TestCollection tc: testCollections) {
			if(tc.getStage().getSortOrder() != null && !dbStageOrders.contains(tc.getStage().getSortOrder())) {
				dbStageOrders.add(tc.getStage().getSortOrder());
			}
		}
		
		Collections.sort(dbStageOrders);
		List<Integer> missingStages = new ArrayList<Integer>();

		int j = 1;
		for (int i = 0; i < dbStageOrders.size(); i++) {
			if (j == dbStageOrders.get(i)) {
				j++;
				continue;
			} else {
				missingStages.add(j);
				i--;
				j++;
			}
		}
		return missingStages;
	}

	private List<TestCollection> getTestCollections(Assessment assessment, TestType testType, ContentArea contentArea, GradeCourse gradeCourse,
			Long operationalTestWindowId, Long stageId) {
		List<TestCollection> testCollections = null;
		if(assessmentProgramCode.equalsIgnoreCase("KAP") && enrollmentMethod.equalsIgnoreCase(ENROLLMENT_ADAPTIVE)) {
			testCollections = tcService.getTestCollectionForAutoAdaptive(assessmentProgramCode, assessment.getId(), gradeCourse.getId(), testType.getId(), contentArea.getId(), ENROLLMENT_RANDOMIZATION_TYPE, contentArea.getAbbreviatedName(), assessment.getAssessmentCode(), operationalTestWindowId, enrollmentMethod);
		}else{
			testCollections = tcService.getTestCollectionForAuto(assessmentProgramCode, assessment.getId(), gradeCourse.getId(), testType.getId(), contentArea.getId(), ENROLLMENT_RANDOMIZATION_TYPE, contentArea.getAbbreviatedName(), assessment.getAssessmentCode(), operationalTestWindowId, stageId);
		}
		// check for uniqueness of collection stages
		if(enrollmentMethod.equalsIgnoreCase(ENROLLMENT_ADAPTIVE) || enrollmentMethod.equalsIgnoreCase("MLTSTG")) {
			Map<String, TestCollection> dbStageOrders = new HashMap<String, TestCollection>();
			for(TestCollection tc: testCollections) {
				if(tc.getStage().getCode() != null)
					dbStageOrders.put(tc.getStage().getCode(), tc);
			}
			if(dbStageOrders.size() > 0){
				testCollections.clear();
				testCollections.addAll(dbStageOrders.values());
			}
		}else { // return only one collection
			while(testCollections.size() > 1) {
				testCollections.remove(testCollections.size()-1);
			}
		}
		return testCollections;
	}

	@SuppressWarnings("unchecked")
	private void writeReason(String msg) {
		logger.debug(msg);
		
		BatchRegistrationReason brReason = new BatchRegistrationReason();
		brReason.setBatchRegistrationId(batchRegistrationId);
		brReason.setReason(msg);
		((CopyOnWriteArrayList<BatchRegistrationReason>) stepExecution.getJobExecution().getExecutionContext().get("jobMessages")).add(brReason);
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
			gradeCourses.add(selectedGC);
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
			contentAreas.add(selectedCA);
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

	private List<Assessment> getAssessments() {
		List<Assessment> assessments = null;
		if(assessmentId == null) {
			assessments = assessmentService.getAssessmentsForAutoRegistration(testingProgramId, assessmentProgramId);
		} else {
			assessments = new ArrayList<Assessment>();
			assessments.add(assessmentService.getById(assessmentId));
		}
		
		return assessments;
	}
	
	/** ONLY FOR CPASS FOR NOW
	 * @param testType
	 * @return
	 */
	private List<Stage> getStages(ContentArea contentArea, TestType testType, Assessment assessment) {
		return testSessionService.selectStageByContentAreaTestTypeAssessment(contentArea.getId(), testType.getId(), assessment.getId());
	}
	
	private List<Long> getOperationalTestWindowIds() {
		List<Long> operationalTestWindowIds = new ArrayList<Long>();
		if (operationalTestWindowId == null) {
			List<OperationalTestWindow> otws = getOperationalTestWindowsForEnrollmentMethod(getEnrollmentMethodId());
			for (OperationalTestWindow otw : otws) {
				operationalTestWindowIds.add(otw.getId());
			}
		} else {
			operationalTestWindowIds.add(operationalTestWindowId);
		}
		return operationalTestWindowIds;
	}
	
	private List<OperationalTestWindow> getOperationalTestWindowsForEnrollmentMethod(Long enrollmentMethodId) {
		return brService.getEffectiveTestWindowsForBatchRegistration(assessmentProgramId, enrollmentMethodId);
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
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

	public Long getBatchRegistrationId() {
		return batchRegistrationId;
	}

	public void setBatchRegistrationId(Long batchRegistrationId) {
		this.batchRegistrationId = batchRegistrationId;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}
	
	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public String getEnrollmentMethod() {
		return enrollmentMethod;
	}

	public void setEnrollmentMethod(String enrollmentMethod) {
		this.enrollmentMethod = enrollmentMethod;
	}

	public Long getEnrollmentMethodId() {
		return enrollmentMethodId;
	}

	public void setEnrollmentMethodId(Long enrollmentMethodId) {
		this.enrollmentMethodId = enrollmentMethodId;
	}
}
