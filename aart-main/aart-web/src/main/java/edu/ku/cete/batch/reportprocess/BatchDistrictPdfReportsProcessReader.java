package edu.ku.cete.batch.reportprocess;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.database.AbstractPagingItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.report.BatchReportProcessService;

public class BatchDistrictPdfReportsProcessReader<T> extends AbstractPagingItemReader<T> {

	@Autowired
	BatchReportProcessService batchReportProcessService;
	
	@Autowired
	AssessmentProgramService assessmentProgramService;
	
	private Long stateId;
	private Long assessmentProgramId;
	private Long schoolYear;
    private StepExecution stepExecution;
    private Long batchReportProcessId;
	
    @Value("${alternate.student.individual.report.type.code}")
	private String dbDLMStudentReportsImportReportType;

    @Value("${cpass.student.individual.report.type.code}")
	private String dbCPASSStudentReportsImportReportType;
	
	@Override
	protected void doReadPage() {
		if (results == null) {
			results = new CopyOnWriteArrayList<T>();
		} else {
			results.clear();
		}
		results.addAll(getDistrictIdsFromStudentReportByStateIdAssmntProgIdAndSchoolYear(stateId,assessmentProgramId, schoolYear, getPage() * getPageSize(), getPageSize()));
	}

	@SuppressWarnings("unchecked")
	private List<T> getDistrictIdsFromStudentReportByStateIdAssmntProgIdAndSchoolYear(Long stateId,Long assessmentProgramId,Long currentSchoolYear, Integer offset, Integer pageSize) {
		
		AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(assessmentProgramId);
		
		String reportType="";
		if(assessmentProgram!=null && "CPASS".equals(assessmentProgram.getAbbreviatedname()))
			reportType = dbCPASSStudentReportsImportReportType;
		else if(assessmentProgram!=null && "DLM".equals(assessmentProgram.getAbbreviatedname()))
			reportType = dbDLMStudentReportsImportReportType;
			
		List<T> results = null;
		if("CPASS".equalsIgnoreCase(assessmentProgram.getAbbreviatedname()) || "DLM".equalsIgnoreCase(assessmentProgram.getAbbreviatedname())){
			results = (List<T>) batchReportProcessService.getDistrictIdsFromExternalStudentReportByStateIdAssmntProgIdAndSchoolYear(stateId, assessmentProgramId, currentSchoolYear, offset, pageSize, reportType);	
		}else{
			results = (List<T>) batchReportProcessService.getDistrictIdsFromStudentReportByStateIdAssmntProgIdAndSchoolYear(stateId, assessmentProgramId, currentSchoolYear, offset, pageSize);
		}
		return results;
	}

	@Override
	protected void doJumpToPage(int itemIndex) {
		// Nothing		
	}

	public BatchReportProcessService getBatchReportProcessService() {
		return batchReportProcessService;
	}

	public void setBatchReportProcessService(
			BatchReportProcessService batchReportProcessService) {
		this.batchReportProcessService = batchReportProcessService;
	}	

	public Long getStateId() {
		return stateId;
	}

	public void setStateId(Long stateId) {
		this.stateId = stateId;
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
