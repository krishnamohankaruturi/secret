package edu.ku.cete.batch.reportprocess.writer;

import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.OrganizationBundleReport;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.report.BatchReportProcessService;

/* sudhansu.b
 * Added for F460 - Dynamic bundle report
 */	
public class BatchDynamicBundleofStudentReportsProcessWriter implements ItemWriter<StudentReport>{

	final static Log logger = LogFactory.getLog(BatchSchoolPdfReportsProcessWriter.class);

	 @Autowired 
	 BatchReportProcessService batchReportProcessService;

    private OrganizationBundleReport bundleRequest;
    
	private String assessmentProgramCode;
	
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
			//batchReportProcessService.deleteOrganizationBundleReportFilesByOrganization(bundleRequest.getOrganizationId(), bundleRequest.getAssessmentProgramId()
              //      ,bundleRequest.getSchoolYear());
            //batchReportProcessService.deleteOrganizationBundleReportsByOrganization(bundleRequest.getOrganizationId(), bundleRequest.getAssessmentProgramId()
                  //  ,bundleRequest.getSchoolYear());
            
			for(StudentReport studentReport : studentReportList){
				logger.debug("Inside  BatchDynamicBundleofStudentReportsProcessWriter ....");
				
				OrganizationReportDetails schoolReportPdfOfStudentReoports = new OrganizationReportDetails();
				schoolReportPdfOfStudentReoports.setAssessmentProgramId(studentReport.getAssessmentProgramId());
				schoolReportPdfOfStudentReoports.setSchoolYear(studentReport.getSchoolYear());
				//schoolReportPdfOfStudentReoports.setBatchReportProcessId(studentReport.getBatchReportProcessId());
				//schoolReportPdfOfStudentReoports.setGradeCourseAbbrName(studentReport.getGradeCode());
				schoolReportPdfOfStudentReoports.setOrganizationId(studentReport.getAttendanceSchoolId());
				schoolReportPdfOfStudentReoports.setCreatedDate(new Date());
				schoolReportPdfOfStudentReoports.setBatchReportProcessId(bundleRequest.getId());
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
				
				logger.debug("Completed BatchDynamicBundleofStudentReportsProcessWriter Org Id - " + studentReport.getAttendanceSchoolId());
			}
		}
		
	}
	public OrganizationBundleReport getBundleRequest() {
		return bundleRequest;
	}

	public void setBundleRequest(OrganizationBundleReport bundleRequest) {
		this.bundleRequest = bundleRequest;
	}
	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}
	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}
	
}
