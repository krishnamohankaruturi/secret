/**
 * 
 */
package edu.ku.cete.batch.reportprocess.processor;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.QuestionInformation;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReportQuestionInfo;
import edu.ku.cete.service.report.QuestionInformationService;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 11, 2017 1:55:15 PM
 */
public class CalculatePredictiveReportQuestionCredit implements ItemProcessor<InterimStudentReport, Object> {

final static Log logger = LogFactory.getLog(CalculatePredictiveScaleScore.class);
	
	private StepExecution stepExecution; 	

	private String reportCycle;
	private Long testingProgramId;
	private Long noCreditCategoryId;
	private Long partialCreditCategoryId;
	private Long fullCreditCategoryId;
	private Long unAnsweredCategoryId;	
		
	@Autowired 
	private QuestionInformationService questionInformationService;
	
	@SuppressWarnings("unchecked")
	@Override
	public InterimStudentReport process(InterimStudentReport studentReport) throws Exception {
		logger.debug("Inside CalculatePredictiveReportQuestionCredit process......Student - " + studentReport.getStudentId());
		
		if(studentReport.getExternalTestId() != null){
			List<QuestionInformation> questionInformationList = questionInformationService.getAllQuestionsInfoByTestId(studentReport.getAssessmentProgramId(), 
																testingProgramId, 
																reportCycle, 
																studentReport.getContentAreaId(), 
																studentReport.getGradeId(), 
																studentReport.getCurrentSchoolYear(), 
																studentReport.getTestId());
			
			List<StudentReportQuestionInfo> studentReportQuestionInfoList = null; 
			StudentReportQuestionInfo studentReportQuestionInfo = null;
					
			if(CollectionUtils.isNotEmpty(questionInformationList)){
				studentReportQuestionInfoList = new ArrayList<StudentReportQuestionInfo>();
				
				//check for each taskvariant on test and compate score on student response and determine question credit
				//get variantmap which was set in rawscore processor
				Map<Long, BigDecimal> taskVariantScoreMap = studentReport.getTaskVariantScoreMap();
				
				for(QuestionInformation questionInfo : questionInformationList){
					studentReportQuestionInfo = new StudentReportQuestionInfo();
					studentReportQuestionInfo.setQuestionInformationId(questionInfo.getQuestionInformationId());
					studentReportQuestionInfo.setTaskVariantPosition(questionInfo.getTaskVariantPosition());
					studentReportQuestionInfo.setTestId(questionInfo.getTestId());
					studentReportQuestionInfo.setExternalTestId(questionInfo.getExternalTestId());;
					studentReportQuestionInfo.setTaskVariantExternalId(questionInfo.getTaskVariantExternalId());
					studentReportQuestionInfo.setTaskVariantId(questionInfo.getTaskVariantId());
					
					if(questionInfo.getTaskVariantExternalId() != null){
						BigDecimal responseScore = taskVariantScoreMap.get(questionInfo.getTaskVariantExternalId());
						if(responseScore != null){
							//response score = 0
							if(responseScore.compareTo(new BigDecimal(0)) == 0){
								studentReportQuestionInfo.setCreditEarned(noCreditCategoryId);
							}else if(responseScore.compareTo(questionInfo.getMaxScore()) == 0){//response score = max score
								studentReportQuestionInfo.setCreditEarned(fullCreditCategoryId);
							}else if(responseScore.compareTo(new BigDecimal(0)) == 1 && responseScore.compareTo(questionInfo.getMaxScore()) == -1){//response score > 0 && < max score
								studentReportQuestionInfo.setCreditEarned(partialCreditCategoryId);
							}							
							
						}else{							
							studentReportQuestionInfo.setCreditEarned(unAnsweredCategoryId);
						}
					}
					studentReportQuestionInfoList.add(studentReportQuestionInfo);
				}
				
				if(CollectionUtils.isNotEmpty(studentReportQuestionInfoList)){
					studentReport.setReportQuestionInformation(studentReportQuestionInfoList);
				}
				
			}else{
				ReportProcessReason reportProcessReason = new ReportProcessReason();
				String msg = "Question details not found for AssessmentProgramId - " + studentReport.getAssessmentProgramId()
								+ " TestingProgramId - " + testingProgramId
								+ " ReportCycle - " + reportCycle
								+ " ContentAreaId - " + studentReport.getContentAreaId()
								+ " GradeId - " + studentReport.getGradeId()
								+ " SchoolYear - " + studentReport.getCurrentSchoolYear()
								+ " EP TestID - " + studentReport.getTestId();
				
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
			}
		}else{
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			String msg = "Student not tested in AssessmentPRogramId - " + studentReport.getAssessmentProgramId()
					+ " TestingProgramId - " + testingProgramId
					+ " ReportCycle - " + reportCycle
					+ " ContentAreaId - " + studentReport.getContentAreaId()
					+ " GradeId - " + studentReport.getGradeId()
					+ " SchoolYear - " + studentReport.getCurrentSchoolYear()
					+ " TestID - " + studentReport.getTestId();
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
		}
		
		logger.debug("Completed CalculatePredictiveReportQuestionCredit.....Student - " + studentReport.getStudentId());
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

	public Long getNoCreditCategoryId() {
		return noCreditCategoryId;
	}

	public void setNoCreditCategoryId(Long noCreditCategoryId) {
		this.noCreditCategoryId = noCreditCategoryId;
	}

	public Long getPartialCreditCategoryId() {
		return partialCreditCategoryId;
	}

	public void setPartialCreditCategoryId(Long partialCreditCategoryId) {
		this.partialCreditCategoryId = partialCreditCategoryId;
	}

	public Long getFullCreditCategoryId() {
		return fullCreditCategoryId;
	}

	public void setFullCreditCategoryId(Long fullCreditCategoryId) {
		this.fullCreditCategoryId = fullCreditCategoryId;
	}

	public Long getUnAnsweredCategoryId() {
		return unAnsweredCategoryId;
	}

	public void setUnAnsweredCategoryId(Long unAnsweredCategoryId) {
		this.unAnsweredCategoryId = unAnsweredCategoryId;
	}

}
