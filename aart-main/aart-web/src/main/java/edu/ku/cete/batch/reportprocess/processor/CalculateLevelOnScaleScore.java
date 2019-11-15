package edu.ku.cete.batch.reportprocess.processor;

 
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.report.TestCutScores;
import edu.ku.cete.service.report.LevelDescriptionService;
import edu.ku.cete.service.report.TestCutScoresService;
public class CalculateLevelOnScaleScore implements ItemProcessor<StudentReport,Object>
{
    private StepExecution stepExecution;

	@Autowired
	private TestCutScoresService testCutScoresService;
	
	@Autowired
	private LevelDescriptionService levelDescriptionService;
	
    final static Log logger = LogFactory.getLog(CalculateLevelOnScaleScore.class);
    
    @Value("${levelDescriptionType.mdpt}")
   	private String levelDescriptionTypeMDPT;
    
    @Value("${levelDescriptionType.main}")
   	private String levelDescriptionTypeMain;
    
    private Long testingProgramId;
    
	@SuppressWarnings("unchecked")
	@Override
    public StudentReport process(StudentReport studentReport) throws Exception {
		logger.debug("Inside Calculate Level Based On ScaleScore process....Student - " + studentReport.getStudentId());
		Long scaleScore = studentReport.getScaleScore();
		Long level = null;
	
		List<TestCutScores> testCutScoresData = testCutScoresService.getCutScoresBasedOnAssessmentProgramSubjectYear(studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getSchoolYear(), studentReport.getGradeId(), testingProgramId);
		if(CollectionUtils.isEmpty(testCutScoresData)){
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			String msg = "No scale score value exists in TestCutScores for School Year - "+studentReport.getSchoolYear()+", Assessment Program - " + studentReport.getAssessmentProgramId() + ", GradeId - " + studentReport.getGradeId() + ", SubjectId - " + studentReport.getContentAreaId()+", Student - "+studentReport.getStudentId();
			reportProcessReason.setReason(msg);
			reportProcessReason.setStudentId(studentReport.getStudentId());
			reportProcessReason.setTestId1(studentReport.getExternalTest1Id());
			reportProcessReason.setTestId2(studentReport.getExternalTest2Id());
			reportProcessReason.setTestId3(studentReport.getExternalTest3Id());
			reportProcessReason.setTestId4(studentReport.getExternalTest4Id());
			reportProcessReason.setPerformanceTestExternalId(studentReport.getPerformanceTestExternalId());
			reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		}else{
			for(TestCutScores testCutScore : testCutScoresData){
				if(scaleScore.longValue() >= testCutScore.getLevelLowCutScore().longValue() && 
				   scaleScore.longValue() <= testCutScore.getLevelHighCutScore().longValue()){
					level = testCutScore.getLevel();
				}
			}
			if(level == null){
				ReportProcessReason reportProcessReason = new ReportProcessReason();
				String msg = "Could not find level for scale score of " + scaleScore  + " from raw score to scale score for student "+studentReport.getStudentId();
				reportProcessReason.setReason(msg);
				reportProcessReason.setStudentId(studentReport.getStudentId());
				reportProcessReason.setTestId1(studentReport.getExternalTest1Id());
				reportProcessReason.setTestId2(studentReport.getExternalTest2Id());
				reportProcessReason.setTestId3(studentReport.getExternalTest3Id());
				reportProcessReason.setTestId4(studentReport.getExternalTest4Id());
				reportProcessReason.setPerformanceTestExternalId(studentReport.getPerformanceTestExternalId());
				reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
				((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
				throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
			}
		}
		
		//get level id from leveldescription table for above calculated level based on scale score
		List<LevelDescription> levelDescriptions = null;
		levelDescriptions = levelDescriptionService.selectLevelsByAssessmentProgramSubjectGradeYearLevel(studentReport.getSchoolYear(), studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId(), level, testingProgramId);
		if(CollectionUtils.isEmpty(levelDescriptions)){
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			String msg = "Level(Level " + level + ") descriptions are not uploaded for School Year - "+studentReport.getSchoolYear()+", Assessment Program - " + studentReport.getAssessmentProgramId() + ", GradeId - " + studentReport.getGradeId() + ", SubjectId - " + studentReport.getContentAreaId()+", Student -  "+studentReport.getStudentId();
			reportProcessReason.setReason(msg);
			reportProcessReason.setStudentId(studentReport.getStudentId());
			reportProcessReason.setTestId1(studentReport.getExternalTest1Id());
			reportProcessReason.setTestId2(studentReport.getExternalTest2Id());
			reportProcessReason.setTestId3(studentReport.getExternalTest3Id());
			reportProcessReason.setTestId4(studentReport.getExternalTest4Id());
			reportProcessReason.setPerformanceTestExternalId(studentReport.getPerformanceTestExternalId());
			reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		}else{
			for(LevelDescription levelDescription: levelDescriptions){
				if(levelDescription.getDescriptionType() != null && levelDescriptionTypeMain.equalsIgnoreCase(levelDescription.getDescriptionType())){
					studentReport.setLevelId(levelDescription.getId());//Main level
				}
			}
			
		}
		
		
		if(studentReport.getMdptScore()!=null){
			levelDescriptions = null;			
			levelDescriptions = levelDescriptionService.selectLevelsByAssessmentProgramSubjectGradeYearLevel(studentReport.getSchoolYear(), studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getMdptScore().longValue(), testingProgramId);
			
			if(CollectionUtils.isEmpty(levelDescriptions)){
				ReportProcessReason reportProcessReason = new ReportProcessReason();
				String msg = "MDPT Level(Level " + studentReport.getMdptScore().longValue() + ") descriptions are not uploaded for School Year - "+studentReport.getSchoolYear()+", Assessment Program - " + studentReport.getAssessmentProgramId() + ", "
						+ "GradeId - " + studentReport.getGradeId() + ", SubjectId - " + studentReport.getContentAreaId()+", Student -  "+studentReport.getStudentId();
				reportProcessReason.setReason(msg);
				reportProcessReason.setStudentId(studentReport.getStudentId());
				reportProcessReason.setTestId1(studentReport.getExternalTest1Id());
				reportProcessReason.setTestId2(studentReport.getExternalTest2Id());
				reportProcessReason.setTestId3(studentReport.getExternalTest3Id());
				reportProcessReason.setTestId4(studentReport.getExternalTest4Id());
				reportProcessReason.setPerformanceTestExternalId(studentReport.getPerformanceTestExternalId());
				reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
				((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
				throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
			}else{
				for(LevelDescription levelDescription : levelDescriptions)
				{
					if(levelDescription.getDescriptionType() != null && levelDescriptionTypeMDPT.equalsIgnoreCase(levelDescription.getDescriptionType()))
						studentReport.setMdptLevelId(levelDescription.getId());
				}
			}
		}
		
		logger.debug("Completed Calculate Level Based On ScaleScore.");
    	return studentReport;
    }
	
	
	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}


	public Long getTestingProgramId() {
		return testingProgramId;
	}


	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}

}
