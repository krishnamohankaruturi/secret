package edu.ku.cete.batch.reportprocess.writer;

 
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.batch.upload.BatchUploadJobListener;
import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.service.report.BatchReportProcessService;

 
/**
 * Writes products to a database
 * @param <T>
 */
public class BatchOrganizationReportProcessWriter implements ItemWriter<StudentReport>
{
	final static Log logger = LogFactory.getLog(BatchUploadJobListener.class);

	 @Autowired 
	 BatchReportProcessService batchReportProcessService;
	 
     private Long batchReportProcessId;
 
	 private String assessmentProgramCode;
	
	 @Value("${general.school.summary.report.type.code}")
	 private String generalSchoolSummaryReportTypeCode;

	 @Value("${general.district.summary.report.type.code}")
	 private String generalDistrictSummaryReportTypeCode;
		
	@Override
	public void write(List<? extends StudentReport> studentReportList) throws Exception {
			if(!studentReportList.isEmpty()){
				for(StudentReport studentReport : studentReportList){
					logger.debug("Inside BatchOrganizationReportProcessWriter ....writing for Org Id - ");
					OrganizationReportDetails orgRptDetails = new OrganizationReportDetails();
					orgRptDetails.setAssessmentProgramId(studentReport.getAssessmentProgramId());
					orgRptDetails.setGradeId(studentReport.getGradeId());
					orgRptDetails.setContentAreaId(studentReport.getContentAreaId());
					orgRptDetails.setSchoolYear(studentReport.getCurrentSchoolYear());
					orgRptDetails.setBatchReportProcessId(batchReportProcessId);
					if(studentReport.getAttendanceSchoolId() != null){
						orgRptDetails.setOrganizationId(studentReport.getAttendanceSchoolId());
					} else if(studentReport.getAttendanceSchoolId() == null &&
					   studentReport.getDistrictId() != null){
						orgRptDetails.setOrganizationId(studentReport.getDistrictId());
					} else {
						orgRptDetails.setOrganizationId(studentReport.getStateId());
					}
					orgRptDetails.setDetailedReportPath(studentReport.getFilePath());
					orgRptDetails.setCreatedDate(new Date());
					orgRptDetails.setModifiedDate(new Date());
					if(studentReport.getAttendanceSchoolId() != null)
						orgRptDetails.setReportType(generalSchoolSummaryReportTypeCode);
					else 
						orgRptDetails.setReportType(generalDistrictSummaryReportTypeCode);
					
					batchReportProcessService.insertOrganizationReportDetails(orgRptDetails);
					logger.debug("Completed BatchOrganizationReportProcessWriter Org Id - ");
				}
			}
	}


	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}


	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}


	public String getAssessmentProgramCode() {
		return assessmentProgramCode;
	}


	public void setAssessmentProgramCode(String assessmentProgramCode) {
		this.assessmentProgramCode = assessmentProgramCode;
	}
	
	 
	

}
