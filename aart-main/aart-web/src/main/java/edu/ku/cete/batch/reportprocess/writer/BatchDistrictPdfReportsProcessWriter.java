package edu.ku.cete.batch.reportprocess.writer;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.report.BatchReportProcessService;

public class BatchDistrictPdfReportsProcessWriter implements ItemWriter<StudentReport>{

	final static Log logger = LogFactory.getLog(BatchDistrictPdfReportsProcessWriter.class);

	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
    private Long batchReportProcessId;
    
    private StepExecution stepExecution;

	private JobExecution jobExecution;
	    
    private String assessmentProgramCode;
    
	@Value("${general.student.bundled.report.type.code}")
	private String generalStudentBundledReportTypeCode;
		
	@Value("${alternate.student.bundled.report.type.code}")
	private String alternateStudentBundledReportTypeCode;

	@Value("${cpass.student.bundled.report.type.code}")
	private String cpassStudentBundledReportTypeCode;
	
	@Override
	public void write(List<? extends StudentReport> studentReportList) throws Exception {
		if(!studentReportList.isEmpty()){
			Set<Long> districtIds = new HashSet<Long>();
			jobExecution= stepExecution.getJobExecution();
			for(StudentReport studentReport : studentReportList){
				logger.debug("Inside  BatchDistrictPdfReportsProcessWriter ....");
				districtIds.add(studentReport.getDistrictId());
				OrganizationReportDetails schoolReportPdfOfStudentReoports = new OrganizationReportDetails();
				schoolReportPdfOfStudentReoports.setAssessmentProgramId(studentReport.getAssessmentProgramId());
				schoolReportPdfOfStudentReoports.setSchoolYear(studentReport.getSchoolYear());
				schoolReportPdfOfStudentReoports.setBatchReportProcessId(studentReport.getBatchReportProcessId());
				schoolReportPdfOfStudentReoports.setGradeCourseAbbrName(studentReport.getGradeCode());
				schoolReportPdfOfStudentReoports.setOrganizationId(studentReport.getDistrictId());
				schoolReportPdfOfStudentReoports.setCreatedDate(new Date());
				schoolReportPdfOfStudentReoports.setBatchReportProcessId(batchReportProcessId);
				schoolReportPdfOfStudentReoports.setDetailedReportPath(studentReport.getDetailedReportPath());
				schoolReportPdfOfStudentReoports.setSchoolReportPdfSize(studentReport.getSchoolReportPdfSize());
				if(assessmentProgramCode!=null && "CPASS".equals(assessmentProgramCode))
					schoolReportPdfOfStudentReoports.setReportType(cpassStudentBundledReportTypeCode);
				else if(assessmentProgramCode!=null && "DLM".equals(assessmentProgramCode))
					schoolReportPdfOfStudentReoports.setReportType(alternateStudentBundledReportTypeCode);
				else if(assessmentProgramCode!=null && "KAP".equals(assessmentProgramCode))
					schoolReportPdfOfStudentReoports.setReportType(generalStudentBundledReportTypeCode);
				batchReportProcessService.insertSchoolReportOfStudentFilesPdf(schoolReportPdfOfStudentReoports);
				logger.debug("Completed BatchDistrictPdfReportsProcessWriter Org Id - " + studentReport.getDistrictId());
			}
			
			((Set<Long>)jobExecution.getExecutionContext().get("successDistrictIds")).addAll(districtIds);
		}
		
	}
	
	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}


	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}

	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}

	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}


}
