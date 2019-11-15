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
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.service.organizationbundle.OrganizationBundleReportService;
import edu.ku.cete.service.report.BatchReportProcessService;
/* sudhansu.b
 * Added for F460 - Dynamic bundle report
 */	
@SuppressWarnings("unchecked")
public class BatchSchoolPdfReportsProcessWriter implements ItemWriter<StudentReport>{

	final static Log logger = LogFactory.getLog(BatchSchoolPdfReportsProcessWriter.class);
	
	@Autowired
	OrganizationBundleReportService bundleReportService;
	
	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
	 @Autowired
	 CategoryDao categoryDao;
	 
    private Long batchReportProcessId;
    
    private String assessmentProgramCode;
    
    private StepExecution stepExecution;
    
    private JobExecution jobExecution;

	@Value("${general.student.bundled.report.type.code}")
	private String generalStudentBundledReportTypeCode;
		
	@Value("${alternate.student.bundled.report.type.code}")
	private String alternateStudentBundledReportTypeCode;

	@Value("${cpass.student.bundled.report.type.code}")
	private String cpassStudentBundledReportTypeCode;
	
	@Value("${kelpa.student.bundled.report.type.code}")
	private String kelpaStudentBundledReportTypeCode;
	
	@Override
	public void write(List<? extends StudentReport> studentReportList) throws Exception {
		if(!studentReportList.isEmpty()){
			jobExecution = stepExecution.getJobExecution();
			Set<Long> schoolIds = new HashSet<Long>();
			
			for(StudentReport studentReport : studentReportList){
				logger.debug("Inside  BatchSchoolPdfReportsProcessWriter ....");
				schoolIds.add(studentReport.getAttendanceSchoolId());
				OrganizationReportDetails schoolReportPdfOfStudentReoports = new OrganizationReportDetails();
				schoolReportPdfOfStudentReoports.setAssessmentProgramId(studentReport.getAssessmentProgramId());
				schoolReportPdfOfStudentReoports.setSchoolYear(studentReport.getSchoolYear());
				schoolReportPdfOfStudentReoports.setBatchReportProcessId(studentReport.getBatchReportProcessId());
				schoolReportPdfOfStudentReoports.setGradeCourseAbbrName(studentReport.getGradeCode());
				schoolReportPdfOfStudentReoports.setOrganizationId(studentReport.getAttendanceSchoolId());
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
				else if(assessmentProgramCode!=null && "KELPA2".equals(assessmentProgramCode))
					schoolReportPdfOfStudentReoports.setReportType(kelpaStudentBundledReportTypeCode);
				batchReportProcessService.insertSchoolReportOfStudentFilesPdf(schoolReportPdfOfStudentReoports);
				logger.debug("Completed BatchSchoolPdfReportsProcessWriter Org Id - " + studentReport.getAttendanceSchoolId());
			}
			
			((Set<Long>)jobExecution.getExecutionContext().get("successSchoolIds")).addAll(schoolIds);
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
