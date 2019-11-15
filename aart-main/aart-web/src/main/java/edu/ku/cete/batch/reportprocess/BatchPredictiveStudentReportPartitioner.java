package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.domain.report.TestingCycle;
import edu.ku.cete.model.OperationalTestWindowDao;
import edu.ku.cete.service.OrganizationService;
import edu.ku.cete.service.TestingProgramService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.LevelDescriptionService;
import edu.ku.cete.service.report.TestCutScoresService;

public class BatchPredictiveStudentReportPartitioner implements Partitioner{
	private final static Log logger = LogFactory .getLog(BatchPredictiveStudentReportPartitioner.class);
	private Long assessmentProgramId;
	private Long reportProcessId;
	private String assessmentProgramCode;
	private StepExecution stepExecution;
	private Long schoolYear;
	private String reportCycle;
	private Long testingProgramId;
	private Long contentAreaId;
	private Long gradeCourseId;
	
	@Autowired 
	BatchReportProcessService batchReportProcessService;
	
	@Autowired
	TestingProgramService testingProgramService;
	
	@Autowired
	OrganizationService orgService;
	
	@Autowired
    private OperationalTestWindowDao operationalTestWindowDao;

	
	@Autowired
	private LevelDescriptionService levelDescriptionService;
	
	@Autowired
	private TestCutScoresService testCutScoresService;
	
	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		
		Map<String, ExecutionContext> partitionMap =  new HashMap<String, ExecutionContext>(gridSize);
		
		List<Organization> contractingOrgs = orgService.getContractingOrgsForPredictiveReports(assessmentProgramId, testingProgramId, reportCycle);
		
		if(contractingOrgs.size() > 0){
		 schoolYear = contractingOrgs.get(0).getCurrentSchoolYear();
		}else{
			logger.debug("No test window found for assessmentprogram "+assessmentProgramId+" reportCycle " + reportCycle);
			return partitionMap;
		}
		
		List<Long> contentAreaIds = new ArrayList<Long>();
		List<Long> gradeIds = new ArrayList<Long>();
		if(contentAreaId != null){
			contentAreaIds.add(contentAreaId);
		}else{
			contentAreaIds.addAll(batchReportProcessService.getContentAreaIdsFromInterimStudentReport(assessmentProgramId, schoolYear, reportCycle, testingProgramId));
		}
		//Getting all the test cycles for current year
        List<TestingCycle> testingCycles = batchReportProcessService.getTestingCyclesBySchoolYear(
        		assessmentProgramId, schoolYear, testingProgramId);        
		
		for (Long contentAreaId : contentAreaIds) {
			if(gradeCourseId != null){
				gradeIds.add(gradeCourseId);
			}else{
				gradeIds.addAll(batchReportProcessService.getGradeIdsFromInterimStudentReport(assessmentProgramId, schoolYear, contentAreaId, reportCycle, testingProgramId));
			}
			for (Long gradeId : gradeIds) {
				List<TestCutScores> testCutScores = testCutScoresService.getTestCutScoresByReportCycle(assessmentProgramId,gradeId,contentAreaId,schoolYear,testingProgramId,reportCycle);
				List<LevelDescription> levelDescriptions = levelDescriptionService.selectLevelsByAssessmentProgramSubjectGradeYearCycle(schoolYear, assessmentProgramId, contentAreaId, gradeId, testingProgramId, reportCycle);
				ExecutionContext context = new ExecutionContext();
				context.put("contentAreaId", contentAreaId);
				context.put("gradeId", gradeId);
				context.put("schoolYear", schoolYear);
				context.put("currentYearTestingCycles", testingCycles);
				context.put("testCutScores", testCutScores);
				context.put("levelDescriptions", levelDescriptions);
				partitionMap.put(contentAreaId+ "_" + gradeId, context);
				
			}
			gradeIds.clear();
		}
		return partitionMap;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getReportProcessId() {
		return reportProcessId;
	}

	public void setReportProcessId(Long reportProcessId) {
		this.reportProcessId = reportProcessId;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
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


	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
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

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public Long getGradeCourseId() {
		return gradeCourseId;
	}

	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}

}
