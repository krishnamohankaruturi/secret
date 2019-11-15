package edu.ku.cete.batch.reportprocess;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.service.report.BatchReportProcessService;

public class OrganizationReportProcessPageReader<T> extends AbstractPagingItemReader<T> {
	
	
	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
	 private ContentArea contentArea;
	 private GradeCourse gradeCourse;
	 private Long assessmentProgramId;
	 private Long schoolYear;
     private StepExecution stepExecution;
     private Long batchReportProcessId;
     private Organization contractingOrganization;
	 
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(getDistinctOrgIdsFromMedianReport(assessmentProgramId, contentArea.getId(), gradeCourse, schoolYear, contractingOrganization.getId(), getPage() * getPageSize(), getPageSize()));
	}
	
	@Override
	protected void doJumpToPage(int arg0) {
		// nothing
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> getDistinctOrgIdsFromMedianReport(Long assessmentProgramId, Long contentAreaId, GradeCourse gradeCourse, Long schoolYear, Long contractOrgId, Integer offset, Integer pageSize) {
		Long gradeId = null;
		if(gradeCourse != null)
			gradeId = gradeCourse.getId();
		List<T> results = (List<T>) batchReportProcessService.getDistinctOrganizationIdFromMedianScore(assessmentProgramId, contentAreaId, gradeId, schoolYear, contractOrgId, offset, pageSize);
		return results;
	}
	
	 

	public ContentArea getContentArea() {
		return contentArea;
	}

	public void setContentArea(ContentArea contentArea) {
		this.contentArea = contentArea;
	}

	public GradeCourse getGradeCourse() {
		return gradeCourse;
	}

	public void setGradeCourse(GradeCourse gradeCourse) {
		this.gradeCourse = gradeCourse;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
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

	public Organization getContractingOrganization() {
		return contractingOrganization;
	}

	public void setContractingOrganization(Organization contractingOrganization) {
		this.contractingOrganization = contractingOrganization;
	}
	
}
