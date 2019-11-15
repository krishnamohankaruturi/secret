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

public class KelpaCalculateScaleScore implements ItemProcessor<StudentReport,Object>
{
    private StepExecution stepExecution;    

	@Autowired
	private RawToScaleScoresService rawToScaleScoresService;
	
    final static Log logger = LogFactory.getLog(KelpaCalculateScaleScore.class);
    
    private Long testingProgramId;
    
	@SuppressWarnings("unchecked")
	@Override
    public StudentReport process(StudentReport studentReport) throws Exception {
		logger.debug("Inside KelpaCalculateScaleScore process......Student - " + studentReport.getStudentId());
		/*
		 * For reference, these are the stages and how they "should" map to the existing POJOs, to keep everything consistent.
		 * 
		 * TestId1: Reading
		 * TestId2: Listening
		 * TestId3: Speaking
		 * TestId4: Writing
		 */
		
		List<Long> testIds = new ArrayList<Long>();
		
		testIds.add(studentReport.getExternalTest1Id());
		BigDecimal readingRawScore = studentReport.getReadingRawScore() == null ? new BigDecimal(0) : studentReport.getReadingRawScore();
		RawToScaleScores readingScaleScore = rawToScaleScoresService.selectByTestIdsAPSubjIdGradeId(testIds, studentReport.getAssessmentProgramId(), 
				studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
				studentReport.getExternalTest1Id(), null, null,	null, null, readingRawScore, testingProgramId, null);
		
		// reading
		if(readingScaleScore == null) {
			String msg = "Rawscore " + readingRawScore + " (Domain: Reading) not found in Raw To Scale score Upload file.";
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(buildReason(studentReport, msg, testIds));
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		} else {
			studentReport.setReadingScaleScore(readingScaleScore.getScaleScore());
			studentReport.setReadingStandardError(readingScaleScore.getStandardError());
		}
		testIds.clear();
		
		// listening
		testIds.add(studentReport.getExternalTest2Id());
		BigDecimal listeningRawScore = studentReport.getListeningRawScore() == null ? new BigDecimal(0) : studentReport.getListeningRawScore();
		RawToScaleScores listeningScaleScore = rawToScaleScoresService.selectByTestIdsAPSubjIdGradeId(testIds, studentReport.getAssessmentProgramId(), 
				studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
				studentReport.getExternalTest2Id(), null, null,	null, null, listeningRawScore, testingProgramId, null);
		
		if(listeningScaleScore == null) {
			String msg = "Rawscore " + listeningRawScore + " (Domain: Listening) not found in Raw To Scale score Upload file.";
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(buildReason(studentReport, msg, testIds));
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		} else {
			studentReport.setListeningScaleScore(listeningScaleScore.getScaleScore());
			studentReport.setListeningStandardError(listeningScaleScore.getStandardError());
		}
		testIds.clear();
		
		// speaking
		testIds.add(studentReport.getExternalTest3Id());
		BigDecimal speakingRawScore = studentReport.getSpeakingRawScore() == null ? new BigDecimal(0) : studentReport.getSpeakingRawScore();
		RawToScaleScores speakingScaleScore = rawToScaleScoresService.selectByTestIdsAPSubjIdGradeId(testIds, studentReport.getAssessmentProgramId(), 
				studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
				studentReport.getExternalTest3Id(), null, null,	null, null, speakingRawScore, testingProgramId, null);
		
		if(speakingScaleScore == null) {
			String msg = "Rawscore " + speakingRawScore + " (Domain: Speaking) not found in Raw To Scale score Upload file.";
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(buildReason(studentReport, msg, testIds));
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		} else {
			studentReport.setSpeakingScaleScore(speakingScaleScore.getScaleScore());
			studentReport.setSpeakingStandardError(speakingScaleScore.getStandardError());
		}
		testIds.clear();
		
		// writing
		testIds.add(studentReport.getExternalTest4Id());
		BigDecimal writingRawScore = studentReport.getWritingRawScore() == null ? new BigDecimal(0) : studentReport.getWritingRawScore();
		RawToScaleScores writingScaleScore = rawToScaleScoresService.selectByTestIdsAPSubjIdGradeId(testIds, studentReport.getAssessmentProgramId(), 
				studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
				studentReport.getExternalTest4Id(), null, null,	null, null, writingRawScore, testingProgramId, null);
		
		if(writingScaleScore == null) {
			String msg = "Rawscore " + writingRawScore + " (Domain: Writing) not found in Raw To Scale score Upload file.";
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(buildReason(studentReport, msg, testIds));
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		} else {
			studentReport.setWritingScaleScore(writingScaleScore.getScaleScore());
			studentReport.setWritingStandardError(writingScaleScore.getStandardError());
		}
		testIds.clear();
		
		// comprehension
		testIds.add(studentReport.getExternalTest1Id());
		testIds.add(studentReport.getExternalTest2Id());
		BigDecimal comprehensionRawScore = studentReport.getComprehensionRawScore() == null ? new BigDecimal(0) : studentReport.getComprehensionRawScore();
		RawToScaleScores comprehensionScaleScore = rawToScaleScoresService.selectByTestIdsAPSubjIdGradeId(testIds, studentReport.getAssessmentProgramId(), 
				studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
				studentReport.getExternalTest1Id(), studentReport.getExternalTest2Id(), null, null, null, comprehensionRawScore, testingProgramId, null);
		
		if(comprehensionScaleScore == null) {
			String msg = "Rawscore " + comprehensionRawScore + " (Domain: Comprehension) not found in Raw To Scale score Upload file.";
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(buildReason(studentReport, msg, testIds));
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		} else {
			studentReport.setComprehensionScaleScore(comprehensionScaleScore.getScaleScore());
			studentReport.setComprehensionStandardError(comprehensionScaleScore.getStandardError());
		}
		testIds.clear();
		
		// overall
		// NOTE: The overall scale score calculation was removed as part of F657 in February 2018
		/*
		testIds.add(studentReport.getExternalTest1Id());
		testIds.add(studentReport.getExternalTest2Id());
		testIds.add(studentReport.getExternalTest3Id());
		testIds.add(studentReport.getExternalTest4Id());
		
		BigDecimal overallRawScore = studentReport.getRawScore() == null ? new BigDecimal(0) : studentReport.getRawScore();
		RawToScaleScores overallScaleScore = rawToScaleScoresService.selectByTestIdsAPSubjIdGradeId(testIds, studentReport.getAssessmentProgramId(), 
				studentReport.getContentAreaId(), studentReport.getGradeId(), studentReport.getCurrentSchoolYear(), 
				studentReport.getExternalTest1Id(), studentReport.getExternalTest2Id(), studentReport.getExternalTest3Id(), 
				studentReport.getExternalTest4Id(), null, overallRawScore, testingProgramId, null);
		
		if(overallScaleScore == null) {
			String msg = "Rawscore " + overallRawScore + " (Domain: Overall Proficiency) not found in Raw To Scale score Upload file.";
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(buildReason(studentReport, msg, testIds));
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		} else {
			studentReport.setScaleScore(overallScaleScore.getScaleScore());
			studentReport.setStandardError(overallScaleScore.getStandardError());
		}
		testIds.clear();
		*/
		
		logger.debug("Completed KelpaCalculateScaleScore.....Student - " + studentReport.getStudentId());
    	return studentReport;
    }
	
	private ReportProcessReason buildReason(StudentReport studentReport, String msg, List<Long> testIds) {
		ReportProcessReason reportProcessReason = new ReportProcessReason();
		reportProcessReason.setReason(msg);
		reportProcessReason.setStudentId(studentReport.getStudentId());
		reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
		reportProcessReason.setPerformanceTestExternalId(null);
		if (testIds != null) {
			reportProcessReason.setTestId1(testIds.size() > 0 ? testIds.get(0) : null);
			reportProcessReason.setTestId2(testIds.size() > 1 ? testIds.get(1) : null);
			reportProcessReason.setTestId3(testIds.size() > 2 ? testIds.get(2) : null);
			reportProcessReason.setTestId4(testIds.size() > 3 ? testIds.get(3) : null);
		}
		return reportProcessReason;
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
