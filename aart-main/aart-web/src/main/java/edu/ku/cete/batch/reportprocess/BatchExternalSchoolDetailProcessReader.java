package edu.ku.cete.batch.reportprocess;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.service.report.OrganizationPrctByAssessmentTopicService;

/*
 * Sudhansu : Created for F688 - CPASS School Detail Report 
 */
public class BatchExternalSchoolDetailProcessReader<T> extends AbstractPagingItemReader<T> {

	private Long assessmentProgramId;
	private Long schoolYear;
	private Long stateId;
    private String assessmentCode;
	private String reportCycle;
	private Long testingProgramId;
	private Long gradeCourseId;
	private Long contentAreaId;
	
	@Autowired
	private OrganizationPrctByAssessmentTopicService organizationPrctByAssessmentTopicService;

	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}	
		
		results.addAll(getSchoolIdsForReportGeneration(assessmentProgramId, schoolYear, stateId, assessmentCode, reportCycle, testingProgramId, gradeCourseId, contentAreaId,  getPage() * getPageSize(), getPageSize()));

	}

	private List<T> getSchoolIdsForReportGeneration(Long assessmentProgramId, Long schoolYear, Long stateId, String assessmentCode, String reportCycle, Long testingProgramId, Long gradeCourseId, Long contentAreaId, int offset, int pageSize) {
		  return (List<T>)organizationPrctByAssessmentTopicService.getSchoolIdsForReportGeneration(schoolYear, stateId, assessmentCode, reportCycle, testingProgramId, gradeCourseId, contentAreaId, offset, pageSize);
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
		// TODO Auto-generated method stub

	}

	public Long getAssessmentProgramId() {
		return assessmentProgramId;
	}

	public void setAssessmentProgramId(Long assessmentProgramId) {
		this.assessmentProgramId = assessmentProgramId;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	public String getAssessmentCode() {
		return assessmentCode;
	}

	public void setAssessmentCode(String assessmentCode) {
		this.assessmentCode = assessmentCode;
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

	public Long getGradeCourseId() {
		return gradeCourseId;
	}

	public void setGradeCourseId(Long gradeCourseId) {
		this.gradeCourseId = gradeCourseId;
	}

	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}
}
