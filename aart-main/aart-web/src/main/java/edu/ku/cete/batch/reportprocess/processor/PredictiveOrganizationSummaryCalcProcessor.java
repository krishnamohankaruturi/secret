/**
 * 
 */
package edu.ku.cete.batch.reportprocess.processor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.report.PredictiveReportCreditPercent;
import edu.ku.cete.domain.report.PredictiveReportOrganization;
import edu.ku.cete.domain.report.QuestionInformation;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.QuestionInformationService;

/**
 * @author Kiran Reddy Taduru
 *
 * Sep 27, 2017 3:12:30 PM
 */
public class PredictiveOrganizationSummaryCalcProcessor	implements ItemProcessor<PredictiveReportOrganization, Object> {

	final static Log logger = LogFactory.getLog(PredictiveOrganizationSummaryCalcProcessor.class);
	private StepExecution stepExecution;
    private Long batchReportProcessId;
    private Long assessmentProgramId;
	private Long testingProgramId;
	private String reportCycle;
	private Long testsCompletedStatusId;
    private Long testsInprogressStatusId;
    private Long testStatusInprogressTimedoutId;
    private Long fullCreditCategoryId;
    
    @Autowired
   	private BatchReportProcessService batchReportProcessService;

    @Autowired 
	private QuestionInformationService questionInformationService;
    
    @Override
    public Map<String, Object> process(PredictiveReportOrganization reportOrganization) throws Exception {
		logger.info(">>>>> Inside PredictiveOrganizationSummaryReportProcessor full Credit percent calculation process....OrgId -" + reportOrganization.getOrganizationId() 
																												+ " - AssessmentProgramId: " + reportOrganization.getAssessmentProgramId()
																												+ " - ReportCycle: " + reportOrganization.getReportCycle()
																												+ " - ContentAreaId: "+ reportOrganization.getContentAreaId()
																												+ " - GradeId: " + reportOrganization.getGradeId()
																												+ " - TestId: " + reportOrganization.getTestId());
		
		Map<String, Object> reportsMap = new HashMap<String, Object>();
		List<PredictiveReportCreditPercent> reportCreditPercentList = new ArrayList<PredictiveReportCreditPercent>();
		//number of students who started the test
		Integer testAttemptedStudentCount = 0;
		//number of students who did not answer all of the questions
		Integer unAnsweredStudentCount = 0;	
				
		List<Long> testsStatusIds = new ArrayList<Long>();		
		testsStatusIds.add(testsInprogressStatusId);
		testsStatusIds.add(testStatusInprogressTimedoutId);
		testsStatusIds.add(testsCompletedStatusId);
		
		//Get complete details for each taskvariant on actual test
		List<QuestionInformation> questionInformationList = questionInformationService.getAllQuestionsInfoByTestId(reportOrganization.getAssessmentProgramId(), 
																									reportOrganization.getTestingProgramId(), 
																									reportOrganization.getReportCycle(), 
																									reportOrganization.getContentAreaId(), 
																									reportOrganization.getGradeId(), 
																									reportOrganization.getSchoolYear(), 
																									reportOrganization.getTestId());
		
		//Construct a map for each taskvariant by externalid, so that if there is no fullcredit found for any variant then we can get that missing question details from this map to insert with ZERO percent
		Map<Long, QuestionInformation> questionMap = new HashMap<Long, QuestionInformation>();
		if(CollectionUtils.isNotEmpty(questionInformationList)){
			for(QuestionInformation qnInfo : questionInformationList){
				questionMap.put(qnInfo.getTaskVariantExternalId(), qnInfo);
			}
		}
		
		//Get full credit questions and their counts		
		List<PredictiveReportCreditPercent> summaryOrgPercents = batchReportProcessService.getQuestionCreditPercentCountByOrganizatonId(assessmentProgramId, testingProgramId, reportCycle, 
																							reportOrganization.getSchoolYear(), reportOrganization.getContentAreaId(), reportOrganization.getGradeId(), 
																							reportOrganization.getTestId(), reportOrganization.getOrganizationId(), reportOrganization.getOrgTypeCode(), 
																							fullCreditCategoryId, testsStatusIds);
		
		//Get test attempted student count
		testAttemptedStudentCount = batchReportProcessService.getTestAttemptedStudentCount(assessmentProgramId, 
															    testingProgramId, reportCycle, reportOrganization.getSchoolYear(),  reportOrganization.getContentAreaId(), reportOrganization.getGradeId(), 
																reportOrganization.getOrganizationId(), reportOrganization.getOrgTypeCode(), reportOrganization.getExternalTestId(), testsStatusIds);
		
		//Get number of students who did not answer to all questions on a test (responded items < total items on the test)
		unAnsweredStudentCount = batchReportProcessService.getUnAnsweredStudentCount(assessmentProgramId, testingProgramId, reportCycle, 
								 							reportOrganization.getSchoolYear(),  reportOrganization.getContentAreaId(), reportOrganization.getGradeId(),
								 							reportOrganization.getOrganizationId(), reportOrganization.getOrgTypeCode(), reportOrganization.getExternalTestId());
		
		//List to maintain taskvariants for which we have found FullCredit counts, calculate percentage = No.of students who got full credit on that question/ attempted student count
		List<Long> fullCreditExternalTaskVariants = new ArrayList<Long>();
		
		if(CollectionUtils.isNotEmpty(summaryOrgPercents)){				
			for(PredictiveReportCreditPercent orgCredit : summaryOrgPercents){				
				orgCredit.setFullCreditPercent(Math.round((orgCredit.getFullCreditStudentCount() * 100/ (float) testAttemptedStudentCount)));
				orgCredit.setTestAttemptedStudentCount(testAttemptedStudentCount);
				orgCredit.setUnAnsweredStudentCount(unAnsweredStudentCount);
				orgCredit.setOrganizationId(reportOrganization.getOrganizationId());
				orgCredit.setOrganizationTypeId(reportOrganization.getOrganizationTypeId());
				orgCredit.setCreditTypeId(fullCreditCategoryId);
				fullCreditExternalTaskVariants.add(orgCredit.getTaskVariantExternalId());				
			}
			//add to list to write into db
			reportCreditPercentList.addAll(summaryOrgPercents);
		}
		
		//Add question details to the list for which we did not find fullcredit
		if(questionMap != null && questionMap.keySet().size() >0){
			PredictiveReportCreditPercent fullCreditZeroPercentRecord = null;
			QuestionInformation missingQnInfo = null;
			for(Long taskVariantExternalId : questionMap.keySet()){
				if(!fullCreditExternalTaskVariants.contains(taskVariantExternalId)){
					missingQnInfo = questionMap.get(taskVariantExternalId);
					
					fullCreditZeroPercentRecord = new PredictiveReportCreditPercent();
					fullCreditZeroPercentRecord.setAssessmentProgramId(assessmentProgramId);
					fullCreditZeroPercentRecord.setTestingProgramId(testingProgramId);
					fullCreditZeroPercentRecord.setReportCycle(reportCycle);
					fullCreditZeroPercentRecord.setContentAreaId(reportOrganization.getContentAreaId());
					fullCreditZeroPercentRecord.setGradeId(reportOrganization.getGradeId());
					fullCreditZeroPercentRecord.setSchoolYear(reportOrganization.getSchoolYear());
					fullCreditZeroPercentRecord.setOrganizationId(reportOrganization.getOrganizationId());
					fullCreditZeroPercentRecord.setOrganizationTypeId(reportOrganization.getOrganizationTypeId());
					fullCreditZeroPercentRecord.setExternalTestId(reportOrganization.getExternalTestId());
					fullCreditZeroPercentRecord.setTestId(reportOrganization.getTestId());
					fullCreditZeroPercentRecord.setTaskVariantId(missingQnInfo.getTaskVariantId());
					fullCreditZeroPercentRecord.setTaskVariantExternalId(missingQnInfo.getTaskVariantExternalId());
					fullCreditZeroPercentRecord.setFullCreditStudentCount(0);
					fullCreditZeroPercentRecord.setCreditTypeId(fullCreditCategoryId);
					fullCreditZeroPercentRecord.setTaskVariantPosition(missingQnInfo.getTaskVariantPosition());
					fullCreditZeroPercentRecord.setQuestionInformationId(missingQnInfo.getQuestionInformationId());
					fullCreditZeroPercentRecord.setTestAttemptedStudentCount(testAttemptedStudentCount);
					fullCreditZeroPercentRecord.setUnAnsweredStudentCount(unAnsweredStudentCount);
					fullCreditZeroPercentRecord.setFullCreditPercent(Math.round((0*100/ (float) testAttemptedStudentCount)));
					//add to list to write into db
					reportCreditPercentList.add(fullCreditZeroPercentRecord);
				}
			}
		}
		
		//add organization record to map, so that we use organization related info in writer, like organizationid to write to logs 
		reportsMap.put("reportOrganization", reportOrganization);
		
		//add all percentage records for each question on a test to be used on writer class to insert into database
		reportsMap.put("fullCreditPercentRecords", reportCreditPercentList);
		
		logger.info("<<<<< PredictiveOrganizationSummaryReportProcessor full Credit percent calculation process....OrgId -" + reportOrganization.getOrganizationId() 
																												+ " - AssessmentProgramId: " + reportOrganization.getAssessmentProgramId()
																												+ " - ReportCycle: " + reportOrganization.getReportCycle()
																												+ " - ContentAreaId: "+ reportOrganization.getContentAreaId()
																												+ " - GradeId: " + reportOrganization.getGradeId()
																												+ " - TestId: " + reportOrganization.getTestId());
		
		return reportsMap;
    }

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}

	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	public BatchReportProcessService getBatchReportProcessService() {
		return batchReportProcessService;
	}

	public void setBatchReportProcessService(BatchReportProcessService batchReportProcessService) {
		this.batchReportProcessService = batchReportProcessService;
	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}

	public String getReportCycle() {
		return reportCycle;
	}

	public void setReportCycle(String reportCycle) {
		this.reportCycle = reportCycle;
	}

	public Long getTestsCompletedStatusId() {
		return testsCompletedStatusId;
	}

	public void setTestsCompletedStatusId(Long testsCompletedStatusId) {
		this.testsCompletedStatusId = testsCompletedStatusId;
	}

	public Long getTestsInprogressStatusId() {
		return testsInprogressStatusId;
	}

	public void setTestsInprogressStatusId(Long testsInprogressStatusId) {
		this.testsInprogressStatusId = testsInprogressStatusId;
	}

	public Long getTestStatusInprogressTimedoutId() {
		return testStatusInprogressTimedoutId;
	}

	public void setTestStatusInprogressTimedoutId(Long testStatusInprogressTimedoutId) {
		this.testStatusInprogressTimedoutId = testStatusInprogressTimedoutId;
	}

	public Long getFullCreditCategoryId() {
		return fullCreditCategoryId;
	}

	public void setFullCreditCategoryId(Long fullCreditCategoryId) {
		this.fullCreditCategoryId = fullCreditCategoryId;
	}
      
}
