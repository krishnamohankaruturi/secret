package edu.ku.cete.batch.reportprocess;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.service.report.BatchReportProcessService;

public class BatchSchoolReportCompressedFileProcessReader<T> extends AbstractPagingItemReader<T>  {
	
	@Autowired 
	BatchReportProcessService batchReportProcessService;
	
	private Long contractingOrganizationId;
	private Long schoolYear;
	private Long assessmentProgramId;
	private StepExecution stepExecution;
    private Long batchReportProcessId;
    private String assessmentProgramCode;
    
	@Value("${general.student.bundled.report.type.code}")
	private String generalStudentBundledReportTypeCode;
		
	@Value("${alternate.student.bundled.report.type.code}")
	private String alternateStudentBundledReportTypeCode;

	@Value("${cpass.student.bundled.report.type.code}")
	private String cpassStudentBundledReportTypeCode;
	
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(getSchoolIdsFromOrgDetailReportByStateIdAssmntProgIdAndSchoolYear(contractingOrganizationId, assessmentProgramId, schoolYear, getPage() * getPageSize(), getPageSize()));
	}
	
	@SuppressWarnings("unchecked")
	private Collection<? extends T> getSchoolIdsFromOrgDetailReportByStateIdAssmntProgIdAndSchoolYear(Long contractingOrganizationId, Long assessmentProgramId, Long schoolYear, Integer offset, Integer pageSize) {		
		
		String reportType="";
		if(assessmentProgramCode!=null && "CPASS".equals(assessmentProgramCode))
			reportType = cpassStudentBundledReportTypeCode;
		else if(assessmentProgramCode!=null && "DLM".equals(assessmentProgramCode))
			reportType = alternateStudentBundledReportTypeCode;
		else if(assessmentProgramCode!=null && "KAP".equals(assessmentProgramCode))
			reportType = generalStudentBundledReportTypeCode;
		
		List<T> results = (List<T>) batchReportProcessService.getSchoolIdsFromOrgDetailReportByStateIdAssmntProgIdAndSchoolYear(contractingOrganizationId, assessmentProgramId, schoolYear, offset, pageSize, reportType);
		return results;
	}
	
	@Override
	protected void doJumpToPage(int itemIndex) {
		// Nothing
		
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
	public Long getContractingOrganizationId() {
		return contractingOrganizationId;
	}
	public void setContractingOrganizationId(Long contractingOrganizationId) {
		this.contractingOrganizationId = contractingOrganizationId;
	}
	public Long getSchoolYear() {
		return schoolYear;
	}
	public void setSchoolYear(Long schoolYear) {
		this.schoolYear = schoolYear;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}	
	
}
