package edu.ku.cete.batch.reportprocess.processor;

 
import java.math.BigDecimal;
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
import edu.ku.cete.domain.CombinedLevelMap;
import edu.ku.cete.domain.report.LevelDescription;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.report.CombinedLevelMapService;
import edu.ku.cete.service.report.LevelDescriptionService;

public class CalculateCombinedLevelOnMDPTAndScaleScores implements ItemProcessor<StudentReport,Object>
{
    private StepExecution stepExecution;

	@Autowired
	private CombinedLevelMapService combinedLevelMapService;
	
	@Autowired
	private LevelDescriptionService levelDescriptionService;
	
	final static Log logger = LogFactory.getLog(CalculateLevelOnScaleScore.class);
	
	@Value("${levelDescriptionType.combined}")
   	private String levelDescriptionTypeCombined;
	
	private Long testingProgramId;
	
	@SuppressWarnings("unchecked")
	@Override
    public StudentReport process(StudentReport studentReport) throws Exception {
		logger.info("Inside Calculate Combined Level Based On Scale Score and MDPT Score....Student - " + studentReport.getStudentId());
		Long scaleScore = studentReport.getScaleScore();
		BigDecimal mdptScore = studentReport.getMdptScore();
		BigDecimal combinedLevel = null;
		
		
		if(studentReport.getPerformanceRawscoreIncludeFlag() != null && !studentReport.getPerformanceRawscoreIncludeFlag())
		{
			/*
			   Performance Scale Score;	MDPT Scorable Flag;	Combined Level
				Null;	null;	null
				null;	TRUE;	error
				null;	FALSE;	set to 1
			 */
			if(studentReport.getMdptScore()==null)
			{
				if(studentReport.isMdptScorableFlag()!=null){
					if(!studentReport.isMdptScorableFlag())
					{
						combinedLevel = new BigDecimal("1");
						logger.debug("isMdptScorableFlag-false; Setting to 1");
					}
					else
					{
						String msg = "Scorable but performance score not found.";
						logger.debug(msg);
						ReportProcessReason reportProcessReason = new ReportProcessReason();
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
			}
			/*
			   Performance Scale Score;	MDPT Scorable Flag;	Combined Level
			    value;	null;	error
				value;	TRUE;	calculate
				value;	FALSE;	error
			 */
			else 
			{
				if(studentReport.isMdptScorableFlag()==null)
				{
					String msg = "MDPTScoreableFlag not set but found MDPT score of "+ mdptScore +" for student "+studentReport.getStudentId();
					logger.debug(msg);
					ReportProcessReason reportProcessReason = new ReportProcessReason();
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
				else if(studentReport.isMdptScorableFlag()!=null && !studentReport.isMdptScorableFlag())
				{
					String msg = "MDPTScoreableFlag is false. Performance test is non-scorable but found MDPT score of "+ mdptScore +" for student "+studentReport.getStudentId();
					logger.debug(msg);
					ReportProcessReason reportProcessReason = new ReportProcessReason();
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
				else
				{	
					List<CombinedLevelMap> combinedLevelMapData = combinedLevelMapService.getCombinedLevelBasedOnAssessmentProgramSubjectGradeYear(studentReport.getAssessmentProgramId(), 
							studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getSchoolYear());
					if(CollectionUtils.isEmpty(combinedLevelMapData)){
						String msg = "No scale score value exists in CombinedLevelMap for School Year - "+studentReport.getSchoolYear()+", Assessment Program - " + studentReport.getAssessmentProgramId() + ", GradeId - " + studentReport.getGradeId() + ", SubjectId - " + studentReport.getContentAreaId()+", Student - "+studentReport.getStudentId();
						logger.debug(msg);
						ReportProcessReason reportProcessReason = new ReportProcessReason();
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
						for(CombinedLevelMap combinedLevelMap : combinedLevelMapData){
							if(scaleScore.longValue() >= combinedLevelMap.getStagesLowScaleScore().longValue() && 
									   scaleScore.longValue() <= combinedLevelMap.getStagesHighScaleScore().longValue() && mdptScore.compareTo(combinedLevelMap.getPerformanceScaleScore()) == 0){
								combinedLevel = combinedLevelMap.getCombinedLevel();
							}
						}
						if(combinedLevel == null){
							String msg = "Could not find Combined Level for scale score of " + scaleScore  + " and MDPT score of "+ mdptScore +" from CombinedLevelMap for student "+studentReport.getStudentId();
							logger.debug(msg);
							ReportProcessReason reportProcessReason = new ReportProcessReason();
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
				}
			}
			studentReport.setCombinedLevel(combinedLevel);
			
			if(combinedLevel!=null){
				List<LevelDescription> levelDescriptions = null;
				Long combinedLevelLongValue = Long.parseLong(combinedLevel.toString());
				levelDescriptions = levelDescriptionService.selectLevelsByAssessmentProgramSubjectGradeYearLevel(studentReport.getSchoolYear(), studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId(), combinedLevelLongValue, testingProgramId);
				
				if(CollectionUtils.isEmpty(levelDescriptions)){
					ReportProcessReason reportProcessReason = new ReportProcessReason();
					String msg = "Combined Level(Level " + combinedLevelLongValue + ") descriptions are not uploaded for School Year - "+studentReport.getSchoolYear()+", Assessment Program - " + studentReport.getAssessmentProgramId() + ", "
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
						if(levelDescription.getDescriptionType() != null && levelDescriptionTypeCombined.equalsIgnoreCase(levelDescription.getDescriptionType()))
							studentReport.setCombinedLevelId(levelDescription.getId());
					}
				}
			}
		}
		
		logger.debug("Completed Combined Level Based On Scale Score and MDPT Score.");
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
