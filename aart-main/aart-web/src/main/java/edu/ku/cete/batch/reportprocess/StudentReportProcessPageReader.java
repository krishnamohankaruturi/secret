package edu.ku.cete.batch.reportprocess;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.apache.commons.lang3.StringUtils;
import edu.ku.cete.domain.common.Organization;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.domain.content.GradeCourse;
import edu.ku.cete.service.report.BatchReportProcessService;

public class StudentReportProcessPageReader<T> extends AbstractPagingItemReader<T> {
	
	
	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
	 private ContentArea contentArea;
	 private GradeCourse gradeCourse;
	 private Long assessmentProgramId;
     private StepExecution stepExecution;
     private Long batchReportProcessId;
 	 private Organization contractingOrganization;
 	 private Long schoolYear;
     private Long studentId;
     private String processByStudentId;
     private String reprocessEntireDistrict;
     private String generateSpecificISROption;
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
			}
		if (generateSpecificISROption!=null && StringUtils.equalsIgnoreCase(generateSpecificISROption, "2")){
			reprocessEntireDistrict="TRUE";
		}else{
			reprocessEntireDistrict="FALSE";
		}

		results.addAll(getAllStudentsForReportGeneration(studentId, contractingOrganization.getId(), assessmentProgramId, contentArea.getId(), gradeCourse.getId(),schoolYear, getPage() * getPageSize(), getPageSize(), processByStudentId,reprocessEntireDistrict));
	
	}
	
	@Override
	protected void doJumpToPage(int arg0) {
		// nothing
	}
	
	@SuppressWarnings("unchecked")
	protected List<T> getAllStudentsForReportGeneration(Long studentId, Long orgId, Long assessmentProgramId, Long contentAreaId, Long gradeCourseId, Long schoolYear, Integer offset, Integer pageSize,String processByStudentId,String reprocessEntireDistrict) {
	
		return (List<T>) batchReportProcessService.getStudentsForReportGeneration(studentId, orgId, assessmentProgramId, gradeCourseId, contentAreaId, schoolYear, offset, pageSize,processByStudentId,reprocessEntireDistrict);
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

	public Long getSchoolYear() {
		return schoolYear;
	}

	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public Long getStudentId() {
		return studentId;
	}

	public void setStudentId(Long studentId) {
		this.studentId = studentId;
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
