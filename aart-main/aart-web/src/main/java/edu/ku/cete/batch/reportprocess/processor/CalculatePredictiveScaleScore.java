/**
 * 
 */
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
import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.RawToScaleScores;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.service.report.RawToScaleScoresService;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 11, 2017 11:31:10 AM
 */
public class CalculatePredictiveScaleScore implements ItemProcessor<InterimStudentReport, Object> {

	final static Log logger = LogFactory.getLog(CalculatePredictiveScaleScore.class);
	
	private StepExecution stepExecution;    

	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;

	private String reportCycle;
	private Long testingProgramId;
	
	@SuppressWarnings("unchecked")
	@Override
    public InterimStudentReport process(InterimStudentReport studentReport) throws Exception {
		logger.debug("Inside CalculatePredictiveScaleScore process......Student - " + studentReport.getStudentId());
		BigDecimal rawScore = studentReport.getRawScore();
		List<Long> testIds = new ArrayList<Long>();		
		
		if(studentReport.getExternalTestId() !=null){
			testIds.add(studentReport.getExternalTestId());
		}		
				
		RawToScaleScores rawToScaleScore = rawToScaleScoresService.selectByTestIdsAPSubjIdGradeId(testIds, studentReport.getAssessmentProgramId(), 
				studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
				null, null, null, null, null, rawScore, testingProgramId, reportCycle);		
		
		if(rawToScaleScore == null){
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			String msg = "Rawscore " + rawScore + " not found in Raw To Scale score Upload file.";
			reportProcessReason.setReason(msg);
			reportProcessReason.setStudentId(studentReport.getStudentId());
			reportProcessReason.setTestId1(studentReport.getExternalTestId());
			reportProcessReason.setTestId2(null);
			reportProcessReason.setTestId3(null);
			reportProcessReason.setTestId4(null);
			reportProcessReason.setPerformanceTestExternalId(null);			
			reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		}else{
			studentReport.setScaleScore(rawToScaleScore.getScaleScore());
			studentReport.setStandardError(rawToScaleScore.getStandardError());
		}			
		
		logger.debug("Completed CalculatePredictiveScaleScore.....Student - " + studentReport.getStudentId());
    	return studentReport;
    }

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
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
    
}
