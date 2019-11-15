/**
 * 
 */
package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.report.InterimStudentReport;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.service.report.BatchReportProcessService;

/**
 * @author Kiran Reddy Taduru
 * @Date Sep 8, 2017 5:13:45 PM
 */
public class PredictiveStudentReportScoreCalcPageReader<T> extends AbstractPagingItemReader<T> {

	private final static Log logger = LogFactory.getLog(PredictiveStudentReportScoreCalcPageReader.class);
	private Long contentAreaId;
	private Long gradeCourseId;
	private Long assessmentProgramId;
	private String assessmentProgramCode;
	private Organization contractingOrganization;
	private StepExecution stepExecution;
    private Long batchReportProcessId;
    private List<Long> rawScaleExternalTestIds;
    private Long testsCompletedStatusId;
    private Long testsInprogressStatusId;
    private Long testStatusInprogressTimedoutId;
    private Long testStatusUnusedId;
    private Long testStatusPendingId;
    private Long studentId;
    private Long assessmentId;
    private String processByStudentId;
    private List<Long> studentIdListForReprocess;
    private Boolean isTestingWindowComplete;
    private String reportCycle;
	private Long testingProgramId;
	private Long operationalTestWindowId;
	private Long createdUserId;
	private Date jobStartDate;
	
	@Autowired
	private BatchReportProcessService batchReportProcessService;	
	
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		}
		else {
			results.clear();
		}
		logger.debug("Report Process -->> Student Reader: assessmentProgramId:-" + assessmentProgramId
				+ ", assessmentId:-" + assessmentId
				+ ", assessmentProgramCode:-" + assessmentProgramCode
				+ ", testingProgramId:-" + testingProgramId
				+ ", reportCycle:-" + reportCycle
				+ ", contentAreaId:-" + contentAreaId
				+ ", gradeCourseId:-" + gradeCourseId
				+ ", contractingOrganizationId:-" + contractingOrganization.getDisplayIdentifier()
				+ ", pageNumber:-" + getPage()
				+ ", getPageSize:-" + getPageSize());
		results.addAll(getStudents(assessmentProgramId, assessmentId, assessmentProgramCode, contentAreaId, gradeCourseId, contractingOrganization, getPage() * getPageSize(), getPageSize()));
		logger.debug("Report Process <<-- Student Reader: contentAreaId:-"+ contentAreaId + ",gradeCourseId:-" + gradeCourseId 
				+ ",contractingOrganizationId:-" + contractingOrganization.getDisplayIdentifier() + ", results Size :-" + results.size());
	}

	@SuppressWarnings("unchecked")
	private List<T> getStudents(Long assessmentProgramId, Long assessmentId, String assessmentProgramCode, Long contentAreaId, Long gradeCourseId, Organization contractingOrganization, Integer offset, Integer pageSize) {
		List<Long> testsStatusIds = new ArrayList<Long>();
		
		if(isTestingWindowComplete){
			testsStatusIds.add(testsInprogressStatusId);
			testsStatusIds.add(testStatusInprogressTimedoutId);
			testsStatusIds.add(testStatusPendingId);
			//testsStatusIds.add(testStatusUnusedId);
			testsStatusIds.add(testsCompletedStatusId);
		}else{
			testsStatusIds.add(testsCompletedStatusId);
		}
		
		List<InterimStudentReport> studentReports = new ArrayList<InterimStudentReport>();
		if(CollectionUtils.isNotEmpty(rawScaleExternalTestIds)){
			//studentId = 1416445l;//1416448l;
			if("TRUE".equals(processByStudentId)){
				if(CollectionUtils.isNotEmpty(studentIdListForReprocess)){					
					
					studentReports = batchReportProcessService.getStudentsForPredictiveReportProcess(studentId, contractingOrganization.getId(), assessmentId, assessmentProgramId, 
												assessmentProgramCode, gradeCourseId, contentAreaId, contractingOrganization.getCurrentSchoolYear(), rawScaleExternalTestIds, 
												testsStatusIds, studentIdListForReprocess, testingProgramId, reportCycle, offset, pageSize, null);
				}
			}else{
				
				studentReports = batchReportProcessService.getStudentsForPredictiveReportProcess(studentId, contractingOrganization.getId(), assessmentId, assessmentProgramId, 
						assessmentProgramCode, gradeCourseId, contentAreaId, contractingOrganization.getCurrentSchoolYear(), rawScaleExternalTestIds, 
						testsStatusIds, null, testingProgramId, reportCycle, offset, pageSize, jobStartDate);
			}
			
			if(CollectionUtils.isNotEmpty(studentReports)){
				for(InterimStudentReport studentReport : studentReports){
					studentReport.setAssessmentProgramCode(assessmentProgramCode);
					studentReport.setSchoolYear(contractingOrganization.getCurrentSchoolYear());
					studentReport.setTestingProgramId(testingProgramId);
					studentReport.setReportCycle(reportCycle);
					studentReport.setOperationalTestWindowId(operationalTestWindowId);
					studentReport.setCreatedUser(createdUserId);
					studentReport.setModifiedUser(createdUserId);
					if("TRUE".equals(processByStudentId)){
						studentReport.setIsProcessBySpecificStudentId(Boolean.TRUE);
					}
				}
			}
			
		}else{
			ReportProcessReason reportProcessReason = new ReportProcessReason();
			String msg = "No data found in Interim Raw to scale scores for Assessment Program - " + assessmentProgramCode + " TestingProgramId - " + testingProgramId + " ReportCycle - " + reportCycle + " Grade Id - " + gradeCourseId + " Subject Id -" + contentAreaId;
			reportProcessReason.setReason(msg);
			reportProcessReason.setReportProcessId(batchReportProcessId);
			((CopyOnWriteArrayList<ReportProcessReason>) stepExecution.getExecutionContext().get("stepReasons")).add(reportProcessReason);
		}
		
		return (List<T>) studentReports;
	} 
	
	@Override
	protected void doJumpToPage(int arg0) {
		//no-op
		logger.debug("NO-OP");
	};

	 

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

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
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
	
	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}

	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	public List<Long> getRawScaleExternalTestIds() {
		return rawScaleExternalTestIds;
	}

	public void setRawScaleExternalTestIds(List<Long> rawScaleExternalTestIds) {
		this.rawScaleExternalTestIds = rawScaleExternalTestIds;
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

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
	}

	public Long getAssessmentId() {
		return assessmentId;
	}

	public void setAssessmentId(Long assessmentId) {
		this.assessmentId = assessmentId;
	}

	public String getProcessByStudentId() {
		return processByStudentId;
	}

	public void setProcessByStudentId(String processByStudentId) {
		this.processByStudentId = processByStudentId;
	}

	public List<Long> getStudentIdListForReprocess() {
		return studentIdListForReprocess;
	}

	public void setStudentIdListForReprocess(List<Long> studentIdListForReprocess) {
		this.studentIdListForReprocess = studentIdListForReprocess;
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

	public Long getTestingProgramId() {
		return testingProgramId;
	}

	public void setTestingProgramId(Long testingProgramId) {
		this.testingProgramId = testingProgramId;
	}

	public Long getOperationalTestWindowId() {
		return operationalTestWindowId;
	}

	public void setOperationalTestWindowId(Long operationalTestWindowId) {
		this.operationalTestWindowId = operationalTestWindowId;
	}

	public Long getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}

	public Date getJobStartDate() {
		return jobStartDate;
	}

	public void setJobStartDate(Date jobStartDate) {
		this.jobStartDate = jobStartDate;
	}
	 
}
