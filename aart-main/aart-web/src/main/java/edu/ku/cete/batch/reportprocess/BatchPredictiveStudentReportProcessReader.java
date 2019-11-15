package edu.ku.cete.batch.reportprocess;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.service.report.BatchReportProcessService;

public class BatchPredictiveStudentReportProcessReader<T> extends
		AbstractPagingItemReader<T> {

	private Long assessmentProgramId;
	private StepExecution stepExecution;
	private Long schoolYear;
	private Long gradeId;
	private Long contentAreaId;
	private String reportCycle;
	private Long testingProgramId;
	private String processByStudentId;
	private String reprocessEntireDistrict;
	private String generateSpecificISROption;
    
	public Long getContentAreaId() {
		return contentAreaId;
	}

	public void setContentAreaId(Long contentAreaId) {
		this.contentAreaId = contentAreaId;
	}

	@Autowired
	BatchReportProcessService batchReportProcessService;

	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		if (getGenerateSpecificISROption()!=null && StringUtils.equalsIgnoreCase(getGenerateSpecificISROption(), "2")){
			setReprocessEntireDistrict("TRUE");
		}else{
			setReprocessEntireDistrict("FALSE");
		}
		
		results.addAll(getStudentIdsForReportGeneration(gradeId, schoolYear, contentAreaId, assessmentProgramId, getPage() * getPageSize(), getPageSize()));

	}

	private List<T> getStudentIdsForReportGeneration(Long gradeId, Long schoolYear, Long contentAreaId, Long assessmentProgramId, int offset, int pageSize) {
		  return (List<T>)batchReportProcessService.getInterimStudentIdsForReportGeneration(gradeId, contentAreaId, assessmentProgramId, schoolYear, reportCycle, testingProgramId, processByStudentId, reprocessEntireDistrict, offset, pageSize);
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

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public BatchReportProcessService getBatchReportProcessService() {
		return batchReportProcessService;
	}

	public void setBatchReportProcessService(
			BatchReportProcessService batchReportProcessService) {
		this.batchReportProcessService = batchReportProcessService;
	}

	public Long getGradeId() {
		return gradeId;
	}

	public void setGradeId(Long gradeId) {
		this.gradeId = gradeId;
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

	public String getProcessByStudentId() {
		return processByStudentId;
	}

	public void setProcessByStudentId(String processByStudentId) {
		this.processByStudentId = processByStudentId;
	}

	public String getReprocessEntireDistrict() {
		return reprocessEntireDistrict;
	}

	public void setReprocessEntireDistrict(String reprocessEntireDistrict) {
		this.reprocessEntireDistrict = reprocessEntireDistrict;
	}

	public String getGenerateSpecificISROption() {
		return generateSpecificISROption;
	}

	public void setGenerateSpecificISROption(String generateSpecificISROption) {
		this.generateSpecificISROption = generateSpecificISROption;
	}

}
