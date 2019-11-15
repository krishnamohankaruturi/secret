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
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.security.TestingProgram;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.TestingProgramService;

public class BatchOrganizationReportGenerationPartitioner implements Partitioner {

	private final static Log logger = LogFactory .getLog(BatchOrganizationReportGenerationPartitioner.class);
	
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
	
	private Long assessmentProgramId;
	private Long testTypeId;
	private Long contentAreaId;
	private Long gradeCourseId;
	private Long assessmentId;
	private Long studentId;
	private Long batchReportProcessId;
	private StepExecution stepExecution;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		logger.debug("Enter BatchReportProcessPartitioner partition size : "+gridSize);
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		List<Organization> contractingOrgs = orgService.getContractingOrgsByAssessmentProgramId(assessmentProgramId);

		List<Assessment> assessments = getAssessments();
		if(CollectionUtils.isEmpty(assessments)){
			writeReason("No assessments found");
		}
		logger.debug("batchRegistrationId - " + batchReportProcessId + " , assessments found - " + assessments.size());
		for(Assessment assessment: assessments) {
			List<TestType> testTypes = getTestTypes(assessment);
			if(CollectionUtils.isEmpty(testTypes)){
				writeReason(String.format("No Test Types found for assessment: %d (%s)", assessment.getId(), assessment.getAssessmentCode()));
			}
			logger.debug("batchRegistrationId - " + batchReportProcessId + " , Test Type found - " + testTypes.size());
			for(TestType testType: testTypes) {
				List<ContentArea> contentAreas = getContentAreas(testType,assessment);
				if(CollectionUtils.isNotEmpty(contentAreas)){
					logger.debug("batchRegistrationId - " + batchReportProcessId + " , contentAreas found - " + contentAreas.size());
					for (ContentArea contentArea : contentAreas) {
						List<GradeCourse> gradeCourses = getGradeCourses(testType, contentArea, assessment);
						if(CollectionUtils.isNotEmpty(gradeCourses)){ 
							logger.debug("batchRegistrationId - " + batchReportProcessId + " , gradeCourses found - " + gradeCourses.size());
							for(GradeCourse gradeCourse: gradeCourses) {
								for(Organization contractingOrg: contractingOrgs) {
									logger.debug("processing contracting organization: "+contractingOrg.getId() +" ("+contractingOrg.getDisplayIdentifier()+")");
									
									ExecutionContext context = new ExecutionContext();
									context.put("gradeCourse", gradeCourse);
									context.put("contentArea", contentArea);
									context.put("contractingOrganization", contractingOrg);
									context.put("schoolYear", contractingOrg.getReportYear());
									partitionMap.put(contractingOrg.getId()+ "_" + contentArea.getAbbreviatedName() + "_" + gradeCourse.getAbbreviatedName(), 
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

	private List<Assessment> getAssessments() {
		List<Assessment> assessments = null;
		List<TestingProgram> testProgramId = testingProgramService.getByAssessmentProgIdAndTestingProgAbbr(assessmentProgramId, "S");
		if(assessmentId == null) {
			assessments = assessmentService.getAssessmentsForAutoRegistration(testProgramId.get(0).getId(), assessmentProgramId);
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
}
