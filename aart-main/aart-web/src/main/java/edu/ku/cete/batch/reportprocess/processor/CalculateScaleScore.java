package edu.ku.cete.batch.reportprocess.processor;

 
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.report.RawToScaleScoresService;
public class CalculateScaleScore implements ItemProcessor<StudentReport,Object>
{
    private StepExecution stepExecution;    

	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;
	
    final static Log logger = LogFactory.getLog(CalculateScaleScore.class);
    
    private Long testingProgramId;
    
	@SuppressWarnings("unchecked")
	@Override
    public StudentReport process(StudentReport studentReport) throws Exception {
		logger.debug("Inside CalculateScaleScore process......Student - " + studentReport.getStudentId());
		BigDecimal rawScore = studentReport.getRawScore();
		List<Long> testIds = new ArrayList<Long>();
		
		testIds.add(studentReport.getExternalTest1Id());
		if(studentReport.getExternalTest2Id() !=null){
			testIds.add(studentReport.getExternalTest2Id());
		}
		if(studentReport.getExternalTest3Id() != null){
			testIds.add(studentReport.getExternalTest3Id());
		}		
		if(studentReport.getExternalTest4Id() != null){
			testIds.add(studentReport.getExternalTest4Id());
		}
				
		RawToScaleScores rawToScaleScore = rawToScaleScoresService.selectByTestIdsAPSubjIdGradeId(testIds, studentReport.getAssessmentProgramId(), 
				studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
				studentReport.getExternalTest1Id(), studentReport.getExternalTest2Id(), studentReport.getExternalTest3Id(), 
				studentReport.getExternalTest4Id(), studentReport.getPerformanceTestExternalId(), rawScore, testingProgramId, null);
		
		
		if(rawToScaleScore == null){
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			String msg = "Rawscore " + rawScore + " not found in Raw To Scale score Upload file.";
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
			studentReport.setScaleScore(rawToScaleScore.getScaleScore());
			studentReport.setStandardError(rawToScaleScore.getStandardError());
		}
			
		
		logger.debug("Completed CalculateScaleScore.....Student - " + studentReport.getStudentId());
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
