/**
 * 
 */
package edu.ku.cete.batch.reportprocess;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.service.report.BatchReportProcessService;

/**
 * @author Kiran Reddy Taduru
 *
 * Sep 27, 2017 3:11:50 PM
 */
public class PredictiveOrganizationSummaryCalcReader<T> extends AbstractPagingItemReader<T> {
	
	
	@Autowired 
	BatchReportProcessService batchReportProcessService;
	 
	private Long contentAreaId;
	private Long gradeCourseId;
	private Long assessmentProgramId;
	private Long testingProgramId;
	private String reportCycle;
	private Long schoolYear;
    private StepExecution stepExecution;
    private Long batchReportProcessId;

    private Long testsCompletedStatusId;
    private Long testsInprogressStatusId;
    private Long testStatusInprogressTimedoutId;
    private Long createdUserId;
	 
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(getDistinctOrgIdsFromStudentReport(assessmentProgramId, testingProgramId, reportCycle, schoolYear, contentAreaId, gradeCourseId, getPage() * getPageSize(), getPageSize()));
	}
	
	@Override
	protected void doJumpToPage(int arg0) {
		// nothing
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> getDistinctOrgIdsFromStudentReport(Long assessmentProgramId, Long testingProgramId, String reportCycle, Long schoolYear, Long contentAreaId, Long gradeCourseId, Integer offset, Integer pageSize) {
		List<T> results = (List<T>) batchReportProcessService.getAllOrgsFromInterimStudentReport(assessmentProgramId, testingProgramId, reportCycle, schoolYear, contentAreaId, gradeCourseId, offset, pageSize);
		return results;
	}
	
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

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
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

	public Long getCreatedUserId() {
		return createdUserId;
	}

	public void setCreatedUserId(Long createdUserId) {
		this.createdUserId = createdUserId;
	}
	
	
}
