package edu.ku.cete.batch.reportprocess;

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

import edu.ku.cete.domain.common.Category;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.service.AssessmentService;
import edu.ku.cete.service.CategoryService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.GradeCourseService;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestTypeService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.report.LevelDescriptionService;

public class BatchKELPAStudentReportPartitioner implements Partitioner {

	private final static Log logger = LogFactory
			.getLog(BatchKELPAStudentReportPartitioner.class);

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
    private CategoryService categoryService;
    
    @Autowired
    private LevelDescriptionService levelDescriptionService;

	
	@Autowired
	private TestingProgramService testingProgramService;
	
	private Long assessmentProgramId;
	private Long testTypeId;
	private Long contentAreaId;
	private Long gradeCourseId;
	private Long schoolYear;
	private Long studentId;
	private Long stateId;
	private Long batchReportProcessId;
	private StepExecution stepExecution;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		
		List<Category> domainPerfLevel = getDomainPerformanceLevel();
		Map<String, String> progressionText = getProgressionText();
		Map<String, String> staticPDFcontents = getstaticPDFcontents();
		Map<Long,String> testStatusMap = getTestStatus();
		Map<Long,String> scoringStatus = getScoringStatus();
		logger.debug("Enter BatchKELPAStudentReportPartitioner partition size : "+gridSize);
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		List<ContentArea> contentAreas = getContentAreas();
		if(CollectionUtils.isNotEmpty(contentAreas)){
			logger.debug("batchRegistrationId - " + batchReportProcessId + " , contentAreas found - " + contentAreas.size());
			for (ContentArea contentArea : contentAreas) {				
				List<LevelDescription> levelDescriptions = getLevelDescriptions(contentArea.getId(), -1l);				
				List<GradeCourse> gradeCourses = getGradeCourses(contentArea);
				if(CollectionUtils.isNotEmpty(gradeCourses)){ 
					logger.debug("batchRegistrationId - " + batchReportProcessId + " , gradeCourses found - " + gradeCourses.size());
					for(GradeCourse gradeCourse: gradeCourses) {
							logger.debug("processing contracting organization: "+ stateId);
							ExecutionContext context = new ExecutionContext();
							context.put("gradeCourseId", gradeCourse.getId());
							context.put("contentAreaId", contentArea.getId());
							context.put("schoolYear", schoolYear);
							context.put("levelDescriptions", levelDescriptions);
							context.put("scoringStatusMap", scoringStatus);
							context.put("testStatusMap", testStatusMap);
							context.put("staticPDFcontentMap", staticPDFcontents);
							context.put("progressionTextMap", progressionText);
							context.put("domainPerfLevel", domainPerfLevel);
							partitionMap.put(stateId+ "_" + contentArea.getAbbreviatedName() + "_" + gradeCourse.getAbbreviatedName(), 
									context);							
					}
				}
			}
		}			
		logger.debug("Created "+partitionMap.size()+" partitions.");
		return partitionMap;
	}
	
	private Map<String, String> getstaticPDFcontents() {
		List<Category> pdfStaticTextList = categoryService.selectByCategoryType("PDF_STATIC_CONTENT");
		Map<String, String> pdfStaticTextMap = new HashMap<String, String>();
		
		for (Category category : pdfStaticTextList) {
			pdfStaticTextMap.put(category.getCategoryCode(), category.getCategoryDescription());
		} 
		return pdfStaticTextMap;
	}

	private List<LevelDescription> getLevelDescriptions(Long contentAreaId, Long gradeCourseId) {
		return levelDescriptionService.selectLevelsByAssessmentProgramSubjectGradeYearLevel(schoolYear, assessmentProgramId, contentAreaId, gradeCourseId, null, null);
	}

	private Map<Long, String> getScoringStatus() {
		List<Category> scoringStatusList = categoryService.selectByCategoryType("SCORING_STATUS");
		Map<Long, String> scoringstatusMap = new HashMap<Long, String>();
		
		for (Category category : scoringStatusList) {
			scoringstatusMap.put(category.getId(), category.getCategoryCode());
		} 
		return scoringstatusMap;
	}

	private Map<Long, String> getTestStatus() {
		List<Category> testStatusList = categoryService.selectByCategoryType("STUDENT_TEST_STATUS");
		Map<Long, String> testStatusMap = new HashMap<Long, String>();
		
		for (Category category : testStatusList) {
			testStatusMap.put(category.getId(), category.getCategoryCode());
		} 
		return testStatusMap;
	}

	private Map<String, String> getProgressionText() {
		List<Category> progressionTextList = categoryService.selectByCategoryType("DOMAIN_LEVEL_PROGRESS_STATUS");
		Map<String, String> progressionMap = new HashMap<String, String>();
		
		for (Category category : progressionTextList) {
			progressionMap.put(category.getCategoryCode(), category.getCategoryName());
		} 
		return progressionMap;
	}

	private List<Category> getDomainPerformanceLevel() {
		return categoryService.selectByCategoryType("DOMAIN_PERF_LEVEL");
	}

	private List<GradeCourse> getGradeCourses(ContentArea contentArea) {
		List<GradeCourse> gradeCourses = gradeCourseService.getGradesForReportGeneration(contentArea.getId(), assessmentProgramId,schoolYear, stateId);
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
	
	private List<ContentArea> getContentAreas() {
		 List<ContentArea> contentAreas = caService.getSubjectsForReportGeneration(assessmentProgramId,schoolYear, stateId);
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


	public Long getSchoolYear() {
		return schoolYear;
	}


	public void setSchoolYear(Long schoolyear) {
		this.schoolYear = schoolyear;
	}


	public Long getStateId() {
		return stateId;
	}


	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

}
