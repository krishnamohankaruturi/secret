/**
 * 
 */
package edu.ku.cete.batch.kelpa.auto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.kap.adaptive.KapAdaptivePartitioner;
import edu.ku.cete.domain.TestType;
import edu.ku.cete.domain.common.Assessment;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.content.TestCollection;
import edu.ku.cete.report.domain.BatchRegistrationReason;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.EnrollmentService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestCollectionService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.util.StageEnum;

/**
 * @author ktaduru_sta
 *
 */
public class KELPAPartitioner implements Partitioner {

	private final static Log logger = LogFactory .getLog(KapAdaptivePartitioner.class);
	
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
	
	private static final String ENROLLMENT_RANDOMIZATION_TYPE = "enrollment";
		
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
	private StepExecution stepExecution;
	private String enrollmentMethod;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchRegistrationPartitioner partition size : "+gridSize);
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		
		List<Organization> contractingOrgs = orgService.getContractingOrgsByAssessmentProgramIdOTWId(assessmentProgramId, operationalTestWindowId, enrollmentMethod);
		
		List<Assessment> assessments = getAssessments();
		if(CollectionUtils.isEmpty(assessments)){
			writeReason("No assessments found");
		}
		logger.debug("batchRegistrationId - " + batchRegistrationId + " , assessments found - " + assessments.size());
		
		List<String> kelpaStages = new ArrayList<String>();
		kelpaStages.add(StageEnum.LISTENING.getCode());
		kelpaStages.add(StageEnum.READING.getCode());
		kelpaStages.add(StageEnum.SPEAKING.getCode());
		kelpaStages.add(StageEnum.WRITING.getCode());
		
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
					List<GradeCourse> gradeCourses = getGradeCourses(testType, contentArea, assessment);
					if(CollectionUtils.isEmpty(gradeCourses)){
						writeReason(String.format("No Grade Courses found for assessment: %d (%s), testtype: %d (%s), contentarea: %d (%s)",
								assessment.getId(), assessment.getAssessmentCode(), testType.getId(), testType.getTestTypeCode(),
								contentArea.getId(), contentArea.getAbbreviatedName()));
					}
					logger.debug("batchRegistrationId - " + batchRegistrationId + " , gradeCourses found - " + gradeCourses.size());
					for(GradeCourse gradeCourse: gradeCourses) {
						List<TestCollection> testCollections = null;
						for(Organization contractingOrg: contractingOrgs) {
							
							testCollections = new ArrayList<TestCollection>();
							testCollections = getKELPATestCollections(assessment, testType, contentArea, gradeCourse, contractingOrg.getOperationalWindowId(), null);							
							
							if(CollectionUtils.isEmpty(testCollections)) {
								String reason = String.format("No test collections found for assessment: %d (%s), testtype: %d (%s), contentarea: %d (%s), gradecourse: %d (%s)", 
										assessment.getId(), assessment.getAssessmentCode(), testType.getId(), testType.getTestTypeCode(),
										contentArea.getId(), contentArea.getAbbreviatedName(), gradeCourse.getId(), gradeCourse.getAbbreviatedName());
								logger.debug("batchRegistrationId - " + batchRegistrationId + " "  + reason );
								writeReason(reason);
							} else if(CollectionUtils.isNotEmpty(testCollections)) {
								List<String> missingStages = getMissingKAPStages(testCollections, kelpaStages);
								if(CollectionUtils.isNotEmpty(missingStages)) {
									String reason = String.format("Test collections are missing for stages: %s, assessment: %d (%s), testtype: %d (%s), contentarea: %d (%s), gradecourse: %d (%s)", 
											missingStages, assessment.getId(), assessment.getAssessmentCode(), testType.getId(), testType.getTestTypeCode(),
											contentArea.getId(), contentArea.getAbbreviatedName(), gradeCourse.getId(), gradeCourse.getAbbreviatedName());
									logger.debug("batchRegistrationId - " + batchRegistrationId + " "  + reason );
									writeReason(reason);	
								} else {
									logger.debug("processing contracting organization: "+contractingOrg.getId() +" ("+contractingOrg.getDisplayIdentifier()+")");
									List<Long> noEnrollmentOrgs = enrollmentService.getNoKELPAEnrollmentOrganizations(testType.getId(), contentArea.getId(), gradeCourse.getId(), 
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
	
	private List<String> getMissingKAPStages(List<TestCollection> testCollections, List<String> kelpaStages) {
		List<String> missingStages = new ArrayList<String>();
		boolean stgExists = false;		
		
		for(String stg : kelpaStages){
			stgExists = false;
			for(TestCollection tc: testCollections) {
				if(stg.equalsIgnoreCase(tc.getStage().getCode())){
					stgExists = true;
					break;
				}
			}
			if(!stgExists){
				missingStages.add(stg);
			}
		}
		return missingStages;
	}

	private List<TestCollection> getKELPATestCollections(Assessment assessment, TestType testType, ContentArea contentArea, GradeCourse gradeCourse,
			Long operationalTestWindowId, Long stageId) {
		List<TestCollection> testCollections = null;
		testCollections = tcService.getTestCollectionForKELPABatchAuto(assessmentProgramCode, assessment.getId(), gradeCourse.getId(), testType.getId(), contentArea.getId(), ENROLLMENT_RANDOMIZATION_TYPE, contentArea.getAbbreviatedName(), assessment.getAssessmentCode(), operationalTestWindowId);
		
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

}
