package edu.ku.cete.batch.reportprocess;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.service.report.BatchReportProcessService;

public class ReportProcessMedianReader<T> extends AbstractPagingItemReader<T> {
	
	
	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
	 private Long contentAreaId;
	 private Long gradeCourseId;
	 private Long assessmentProgramId;
     private StepExecution stepExecution;
     private Long batchReportProcessId;

	 
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(getDistinctOrgIdsFromStudentReport(assessmentProgramId, contentAreaId, gradeCourseId, getPage() * getPageSize(), getPageSize()));
	}
	
	@Override
	protected void doJumpToPage(int arg0) {
		// nothing
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> getDistinctOrgIdsFromStudentReport(Long assessmentProgramId, Long contentAreaId, Long gradeCourseId, Integer offset, Integer pageSize) {
		List<T> results = (List<T>) batchReportProcessService.getAllOrgsFromStudentReport(assessmentProgramId, contentAreaId, gradeCourseId, offset, pageSize);
		//results.addAll((List<T>) batchReportProcessService.getDistinctStateIdsFromStudentReport(assessmentProgramId, contentAreaId, gradeCourseId, offset, pageSize));
		//results.addAll((List<T>) batchReportProcessService.getDistinctDistrictIdsFromStudentReport(assessmentProgramId, contentAreaId, gradeCourseId, offset, pageSize));
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
	
	
}
