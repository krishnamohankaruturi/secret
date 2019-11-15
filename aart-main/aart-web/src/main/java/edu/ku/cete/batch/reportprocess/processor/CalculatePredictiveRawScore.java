/**
 * 
 */
package edu.ku.cete.batch.reportprocess.processor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.batch.support.SkipBatchException;
import edu.ku.cete.domain.StudentsResponses;
import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.StudentReportTestResponses;
import edu.ku.cete.service.StudentReportService;
import edu.ku.cete.util.StageEnum;
import edu.ku.cete.domain.report.ExcludedItems;
import edu.ku.cete.service.report.ExcludedItemsService;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 8, 2017 3:15:55 PM
 */
public class CalculatePredictiveRawScore implements ItemProcessor<InterimStudentReport, Object> {

final static Log logger = LogFactory.getLog(CalculatePredictiveRawScore.class);
	
    private StepExecution stepExecution;
    private Long testsCompletedStatusId;
    private Long testsInProgressStatusId;
    private Long testStatusUnusedId;
    private Long testStatusPendingId;
    private Long testStatusInprogressTimedoutId;
    private Long studentNotTestedReasonId;
    private Long testNotCompletedReasonId;    
    private List<Long> rawScaleExternalTestIds;  
    private String contentAreaAbbreviatedName;
    private Boolean isTestingWindowComplete;
    private String reportCycle;
    
	@Autowired
	private StudentReportService studentReportService;  

	@Autowired
	private ExcludedItemsService excludedItemsService;  
		
	@SuppressWarnings("unchecked")
	@Override
	public InterimStudentReport process(InterimStudentReport studentReport) throws Exception {
		logger.info("Inside CalculatePredictiveRawScore process....: for student::" + studentReport.getStudentId());
		
		studentReport.setStatus(true);
		int taskVariantCount = 0;
		int responseCount = 0;
		int excludedCount = 0;	
		
		List<StudentReportTestResponses> studentReportTestResponses = studentReport.getStudentTestsScore();
		if(CollectionUtils.isEmpty(studentReportTestResponses)){
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			String msg = "Responses not found for student: " + studentReport.getStudentId() ;
			reportProcessReason.setReason(msg);
			reportProcessReason.setStudentId(studentReport.getStudentId());
			reportProcessReason.setReportProcessId(studentReport.getBatchReportProcessId());
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
			throw new SkipBatchException(msg + ". Skipping student id - " + studentReport.getStudentId());
		}
		
		BigDecimal rawCutScore = new BigDecimal(0);
		Map<String, BigDecimal> stageTestScore = new HashMap<String, BigDecimal>();
		
		for(StudentReportTestResponses studentReportTestResponse : studentReportTestResponses){
			taskVariantCount = studentReportService.taskvariantCountByTestId(studentReportTestResponse.getTestId());
			responseCount = 0;
			
			if(testsCompletedStatusId.equals(studentReportTestResponse.getStudentsTestStatus())  
					 || testsInProgressStatusId.equals(studentReportTestResponse.getStudentsTestStatus())  
					 || testStatusInprogressTimedoutId.equals(studentReportTestResponse.getStudentsTestStatus())){
				
				Map<Long, BigDecimal> variantScoreMap = new HashMap<Long, BigDecimal>();
				Map<Long, BigDecimal> variantScoreWithoutExludeItems = new HashMap<Long, BigDecimal>();
				List<StudentsResponses> studentsResponses = studentReportTestResponse.getStudentResponses();
				
				
				for(StudentsResponses responses : studentsResponses){					
					//Take only responses that are from completed and inprogress, inprogress timedout tests
					if(testsCompletedStatusId.equals(responses.getStudentsTestStatus())
							 || testsInProgressStatusId.equals(responses.getStudentsTestStatus()) 
							 || testStatusInprogressTimedoutId.equals(responses.getStudentsTestStatus())){
						
						boolean studentAnsweredQuestion = responses.getTaskVariantExternalId() != null &&
							(
								responses.getFoilId() != null || StringUtils.isNotBlank(responses.getResponse())
							);
						
						if(studentAnsweredQuestion){
							responseCount++;
							logger.debug("Student answered " + studentReportTestResponse.getStageCode() + " taskvariant.externalid " + responses.getTaskVariantExternalId() + " -- responseCount = " + responseCount);
							BigDecimal currentTaskScore = new BigDecimal(0);
							
							if (Boolean.TRUE.equals(responses.getScoringNeeded())) {
								if (responses.getScore() != null) {
									currentTaskScore = responses.getScore();
									
									BigDecimal existingScore = variantScoreMap.get(responses.getTaskVariantExternalId());
									if (existingScore != null){
										variantScoreMap.put(responses.getTaskVariantExternalId(), existingScore.add(currentTaskScore));
									} else {
										variantScoreMap.put(responses.getTaskVariantExternalId(), currentTaskScore);
									}
									
								} else {
									logger.warn("No score found in studentsresponses for studentsTestSectionsId: " + responses.getStudentsTestSectionsId() +
											", taskVariantId: " + responses.getTaskVariantId());
								}
							}							
							
						}
					}
					
					
				}				

				if(!variantScoreMap.isEmpty()){
					variantScoreWithoutExludeItems.putAll(variantScoreMap);
					List<ExcludedItems> excludedItems = null;
						excludedItems = excludedItemsService.getByTaskVariantIds(studentReport.getAssessmentProgramId(), studentReport.getContentAreaId(), studentReport.getGradeId(),
								studentReport.getCurrentSchoolYear(), variantScoreMap.keySet(), studentReport.getTestingProgramId(), studentReport.getReportCycle());
					
						excludedCount = excludedItems.size();
						
					if(CollectionUtils.isNotEmpty(excludedItems)){
						BigDecimal removedItem = null;
						for(ExcludedItems eItems : excludedItems){
							removedItem = variantScoreMap.remove(eItems.getTaskVariantId());
							if(removedItem != null){
								logger.debug(eItems.getTaskVariantId() + " was excluded and removed -- responseCount = " + responseCount);
							}							
						}
					}
				}
				
				BigDecimal testScore = new BigDecimal(0);
				for(Long variantScore : variantScoreMap.keySet()){
					BigDecimal responseScore = variantScoreMap.get(variantScore);
					if(responseScore != null)
						testScore = testScore.add(responseScore);
				}
				
				BigDecimal tmpScore = stageTestScore.get(studentReportTestResponse.getStageCode());
				if (tmpScore == null || tmpScore.compareTo(testScore) == 1){
					stageTestScore.put(studentReportTestResponse.getStageCode(), testScore);
					studentReport.setTaskVariantScoreMap(variantScoreWithoutExludeItems);
					if(StageEnum.STAGE1.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setExternalTestId(studentReportTestResponse.getTestExternalId());
				    	studentReport.setTestId(studentReportTestResponse.getTestId());
				    	studentReport.setTotalIncludedItemCount(taskVariantCount);
				    	studentReport.setRespondedItemCount(responseCount);
				    	studentReport.setExcludedItemCount(excludedCount);
				    	studentReport.setEnrollmentId(studentReportTestResponse.getEnrollmentId());
				    	studentReport.setStudentsTestStatus(studentReportTestResponse.getStudentsTestStatus());
					}
				}
			}else{//Pending or Unused status
				if(stageTestScore.get(studentReportTestResponse.getStageCode()) == null) {
					if(StageEnum.STAGE1.getCode().equalsIgnoreCase(studentReportTestResponse.getStageCode())){
						studentReport.setStudentsTestStatus(studentReportTestResponse.getStudentsTestStatus());						
						studentReport.setEnrollmentId(studentReportTestResponse.getEnrollmentId());
						studentReport.setExternalTestId(studentReportTestResponse.getTestExternalId());
				    	studentReport.setTestId(studentReportTestResponse.getTestId());
				    	studentReport.setTotalIncludedItemCount(taskVariantCount);
				    	studentReport.setRespondedItemCount(responseCount);
				    	studentReport.setExcludedItemCount(excludedCount);
					}
					
				}
				
			}
		}
		
		logger.info(" Stage_1  rawscore for student: "+studentReport.getStudentId()+" Score: " + stageTestScore.get(StageEnum.STAGE1.getCode()));
		if(stageTestScore.get(StageEnum.STAGE1.getCode()) != null) {
			rawCutScore = rawCutScore.add(stageTestScore.get(StageEnum.STAGE1.getCode()));
		}
		
		if(testStatusUnusedId.equals(studentReport.getStudentsTestStatus())){
			studentReport.setStatus(false);
			//get reasonid for Student not tested
			studentReport.setScoreRangeDisplayReasonId(studentNotTestedReasonId);
		}else{
			if(studentReport.getRespondedItemCount() < studentReport.getTotalIncludedItemCount()){//Student not answered all questions
				studentReport.setStatus(false);
				//get reason id for Test not completed
				studentReport.setScoreRangeDisplayReasonId(testNotCompletedReasonId);
			}
		}
		
		studentReport.setRawScore(rawCutScore.setScale(0, BigDecimal.ROUND_HALF_UP));
		logger.info("Completed CalculatePredictiveRawCutScore....: for student::"+studentReport.getStudentId());
    	return studentReport;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getTestsCompletedStatusId() {
		return testsCompletedStatusId;
	}

	public void setTestsCompletedStatusId(Long testsCompletedStatusId) {
		this.testsCompletedStatusId = testsCompletedStatusId;
	}

	public Long getTestsInProgressStatusId() {
		return testsInProgressStatusId;
	}

	public void setTestsInProgressStatusId(Long testsInProgressStatusId) {
		this.testsInProgressStatusId = testsInProgressStatusId;
	}

	public Long getTestStatusUnusedId() {
		return testStatusUnusedId;
	}

	public void setTestStatusUnusedId(Long testStatusUnusedId) {
		this.testStatusUnusedId = testStatusUnusedId;
	}

	public Long getTestStatusPendingId() {
		return testStatusPendingId;
	}

	public void setTestStatusPendingId(Long testStatusPendingId) {
		this.testStatusPendingId = testStatusPendingId;
	}

	public Long getTestStatusInprogressTimedoutId() {
		return testStatusInprogressTimedoutId;
	}

	public void setTestStatusInprogressTimedoutId(Long testStatusInprogressTimedoutId) {
		this.testStatusInprogressTimedoutId = testStatusInprogressTimedoutId;
	}

	public Long getStudentNotTestedReasonId() {
		return studentNotTestedReasonId;
	}

	public void setStudentNotTestedReasonId(Long studentNotTestedReasonId) {
		this.studentNotTestedReasonId = studentNotTestedReasonId;
	}

	public Long getTestNotCompletedReasonId() {
		return testNotCompletedReasonId;
	}

	public void setTestNotCompletedReasonId(Long testNotCompletedReasonId) {
		this.testNotCompletedReasonId = testNotCompletedReasonId;
	}

	public List<Long> getRawScaleExternalTestIds() {
		return rawScaleExternalTestIds;
	}

	public void setRawScaleExternalTestIds(List<Long> rawScaleExternalTestIds) {
		this.rawScaleExternalTestIds = rawScaleExternalTestIds;
	}

	public String getContentAreaAbbreviatedName() {
		return contentAreaAbbreviatedName;
	}

	public void setContentAreaAbbreviatedName(String contentAreaAbbreviatedName) {
		this.contentAreaAbbreviatedName = contentAreaAbbreviatedName;
	}

	public Boolean getIsTestingWindowComplete() {
		return isTestingWindowComplete;
	}

	public void setIsTestingWindowComplete(Boolean isTestingWindowComplete) {
		this.isTestingWindowComplete = isTestingWindowComplete;
	}

	public String getReportCycle() {
		return reportCycle;
	}

	public void setReportCycle(String reportCycle) {
		this.reportCycle = reportCycle;
	}

}
