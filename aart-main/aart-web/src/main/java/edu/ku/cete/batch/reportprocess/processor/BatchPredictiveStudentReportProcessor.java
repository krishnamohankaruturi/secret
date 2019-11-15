package edu.ku.cete.batch.reportprocess.processor;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.StudentReportQuestionInfo;
import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.domain.report.TestingCycle;
import edu.ku.cete.report.InterimStudentReportGenerator;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.LevelDescriptionService;
import edu.ku.cete.service.report.QuestionInformationService;
import edu.ku.cete.service.report.TestCutScoresService;

public class BatchPredictiveStudentReportProcessor implements ItemProcessor<Long, Object> {
	private final static Log logger = LogFactory .getLog(BatchPredictiveStudentReportProcessor.class);
	private Long assessmentProgramId;
	private StepExecution stepExecution;
	private Long schoolYear;
	private Long gradeId;
	private Long contentAreaId;
	private String reportCycle;
	private List<TestingCycle> currentYearTestingCycles;
	private Long testingProgramId;
	private String processByStudentId;
	private String reprocessEntireDistrict;
    private String generateSpecificISROption;
    private List<TestCutScores> testCutScores;
    private List<LevelDescription> levelDescriptions;
    
	@Autowired
	private BatchReportProcessService batchReportProcessService;
	
	@Autowired
	private LevelDescriptionService levelDescriptionService;
	
	@Autowired
	private QuestionInformationService questionInformationService;
	
	@Autowired
	private TestCutScoresService testCutScoresService;
	
	@Autowired
	private InterimStudentReportGenerator interimStudentReportGenerator;
	
	@Override
	public InterimStudentReport process(Long studentId) throws Exception {
	 writeReason("InterimStudentReport process started with studentid - "+studentId+" subjectId - "+contentAreaId+" gradeId - "+gradeId);
	 
	 Map<String, InterimStudentReport> testingCycleRecordMap = new LinkedHashMap<String, InterimStudentReport>();
	 List<InterimStudentReport> interimReports = batchReportProcessService.getInterimStudentsForReportGeneration(assessmentProgramId, gradeId, contentAreaId, studentId, schoolYear, testingProgramId, processByStudentId);
	 
	 for (TestingCycle cycle : currentYearTestingCycles) {
		 testingCycleRecordMap.put(cycle.getTestingCycleName(), null);
	 }
	 
	 if(interimReports.size() > 0){			 
		 for (InterimStudentReport interimStudentReport : interimReports) {
			testingCycleRecordMap.put(interimStudentReport.getReportCycle(), interimStudentReport);
		 }
		 
		 //Get Student Question Info and test cut score
		 List<StudentReportQuestionInfo> questionInfo = new ArrayList<StudentReportQuestionInfo>();
		 
         questionInfo = questionInformationService.getStudentReportQuestionInfo(testingCycleRecordMap.get(reportCycle).getId());
         if(questionInfo.size() == 0){
        	 writeReason("Question information  not present for student - "+studentId);
        	 return null;
         }
	     //testCutScores = testCutScoresService.getTestCutScoresByReportCycle(assessmentProgramId,gradeId,contentAreaId,schoolYear,testingProgramId,reportCycle);
	     if(testCutScores.size() == 0){
        	 writeReason("TestCut score is not present for grade - "+gradeId+"and subject - "+contentAreaId);
        	 return null;
         }
		 //levelDescriptions = levelDescriptionService.selectLevelsByAssessmentProgramSubjectGradeYearCycle(schoolYear, assessmentProgramId, contentAreaId, gradeId, testingProgramId, reportCycle);	 
		 if(levelDescriptions.size() == 0){
			 writeReason("Level description is not present for grade - "+gradeId+"and subject - "+contentAreaId);
			 return null;
         }		 
		 
		 try{
			 //call report generation method			 
			 return interimStudentReportGenerator.generateInterimStudentReportFile(testingCycleRecordMap, reportCycle, questionInfo, testCutScores, levelDescriptions);
		 }catch(Exception e){
			 writeReason("PDF generation problem for student - "+studentId + " - Exception is: " + e.getMessage());	
			 logger.error("Exception for studentId: "+ studentId + " is:: ", e);
			 return null;
		 }		
	 }
	 writeReason("No Entry availabe in iterimstudentreport table for student +"+studentId);
	 writeReason("InterimStudentReport process completed with studentid - "+studentId+" subjectId - "+contentAreaId+" gradeId - "+gradeId);
	return null;
  }
	
	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
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

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	public String getReportCycle() {
		return reportCycle;
	}

	public void setReportCycle(String reportCycle) {
		this.reportCycle = reportCycle;
	}

	public List<TestingCycle> getCurrentYearTestingCycles() {
		return currentYearTestingCycles;
	}

	public void setCurrentYearTestingCycles(List<TestingCycle> currentYearTestingCycles) {
		this.currentYearTestingCycles = currentYearTestingCycles;
	}

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}
	
	private void writeReason(String msg) {
		logger.info(msg);
	}

	public String getReprocessEntireDistrict() {
		return reprocessEntireDistrict;
	}

	public void setReprocessEntireDistrict(String reprocessEntireDistrict) {
		this.reprocessEntireDistrict = reprocessEntireDistrict;
	}

	public String getGenerateSpecificISROption() {
		return generateSpecificISROption;
	}

	public void setGenerateSpecificISROption(String generateSpecificISROption) {
		this.generateSpecificISROption = generateSpecificISROption;
	}

	public String getProcessByStudentId() {
		return processByStudentId;
	}

	public void setProcessByStudentId(String processByStudentId) {
		this.processByStudentId = processByStudentId;
	}

	public List<TestCutScores> getTestCutScores() {
		return testCutScores;
	}

	public void setTestCutScores(List<TestCutScores> testCutScores) {
		this.testCutScores = testCutScores;
	}

	public List<LevelDescription> getLevelDescriptions() {
		return levelDescriptions;
	}

	public void setLevelDescriptions(List<LevelDescription> levelDescriptions) {
		this.levelDescriptions = levelDescriptions;
	}

}
