/**
 * 
 */
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
import org.springframework.security.core.context.SecurityContextHolder;

import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.report.StudentReport;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.report.BatchReportProcessService;

/**
 * @author Kiran Reddy Taduru
 * May 19, 2017 1:58:19 PM
 */
public class StudentSummaryBundledReportWriter implements ItemWriter<StudentReport> {
	final static Log logger = LogFactory.getLog(StudentSummaryBundledReportWriter.class);

	@Autowired 
	BatchReportProcessService batchReportProcessService;
	
	private Long batchReportProcessId;
	private String bundledReportType;
	
	private JobExecution jobExecution;
	private StepExecution stepExecution;
	
	@Value("${external.schoolLevel.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL;
	    
    @Value("${external.districtLevel.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL;
	    
	@Override
	public void write(List<? extends StudentReport> studentReportList) throws Exception {
		logger.debug("Inside  StudentSummaryBundledReportWriter ....");
		if(!studentReportList.isEmpty()){
			jobExecution = stepExecution.getJobExecution();
			Set<Long> orgIds = new HashSet<Long>();
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			for(StudentReport studentReport : studentReportList){
				
				if(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL.equalsIgnoreCase(bundledReportType)){
					orgIds.add(studentReport.getAttendanceSchoolId());
					OrganizationReportDetails schStudentSummaryBundleReport = new OrganizationReportDetails();
					schStudentSummaryBundleReport.setAssessmentProgramId(studentReport.getAssessmentProgramId());
					schStudentSummaryBundleReport.setSchoolYear(studentReport.getSchoolYear());
					schStudentSummaryBundleReport.setBatchReportProcessId(studentReport.getBatchReportProcessId());
					schStudentSummaryBundleReport.setGradeCourseAbbrName(studentReport.getGradeCode());
					schStudentSummaryBundleReport.setOrganizationId(studentReport.getAttendanceSchoolId());
					schStudentSummaryBundleReport.setBatchReportProcessId(batchReportProcessId);
					schStudentSummaryBundleReport.setDetailedReportPath(studentReport.getDetailedReportPath());
					schStudentSummaryBundleReport.setSchoolReportPdfSize(studentReport.getSchoolReportPdfSize());
					schStudentSummaryBundleReport.setReportType(studentReport.getReportType());
					schStudentSummaryBundleReport.setCreatedDate(new Date());
					schStudentSummaryBundleReport.setModifiedDate(new Date());
					schStudentSummaryBundleReport.setCreatedUser(user.getId());
					schStudentSummaryBundleReport.setModifiedUser(user.getId());
					batchReportProcessService.insertSchoolReportOfStudentFilesPdf(schStudentSummaryBundleReport);
					logger.debug("Completed StudentSummaryBundledReportWriter Org Id - " + studentReport.getAttendanceSchoolId());
				}else if(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL.equalsIgnoreCase(bundledReportType)){
					orgIds.add(studentReport.getDistrictId());
					OrganizationReportDetails districtStudentSummaryBundleReport = new OrganizationReportDetails();
					districtStudentSummaryBundleReport.setAssessmentProgramId(studentReport.getAssessmentProgramId());
					districtStudentSummaryBundleReport.setSchoolYear(studentReport.getSchoolYear());
					districtStudentSummaryBundleReport.setBatchReportProcessId(studentReport.getBatchReportProcessId());
					districtStudentSummaryBundleReport.setGradeCourseAbbrName(studentReport.getGradeCode());
					districtStudentSummaryBundleReport.setOrganizationId(studentReport.getDistrictId());
					districtStudentSummaryBundleReport.setBatchReportProcessId(batchReportProcessId);
					districtStudentSummaryBundleReport.setDetailedReportPath(studentReport.getDetailedReportPath());
					districtStudentSummaryBundleReport.setSchoolReportPdfSize(studentReport.getSchoolReportPdfSize());
					districtStudentSummaryBundleReport.setReportType(studentReport.getReportType());
					districtStudentSummaryBundleReport.setCreatedDate(new Date());
					districtStudentSummaryBundleReport.setModifiedDate(new Date());
					districtStudentSummaryBundleReport.setCreatedUser(user.getId());
					districtStudentSummaryBundleReport.setModifiedUser(user.getId());
					batchReportProcessService.insertSchoolReportOfStudentFilesPdf(districtStudentSummaryBundleReport);
					logger.debug("Completed StudentSummaryBundledReportWriter Org Id - " + studentReport.getDistrictId());
				}
				
			}
			if(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL.equalsIgnoreCase(bundledReportType)){
				((Set<Long>)jobExecution.getExecutionContext().get("successSchoolIds")).addAll(orgIds);
			}else{
				((Set<Long>)jobExecution.getExecutionContext().get("successDistrictIds")).addAll(orgIds);
			}
			
		}
		logger.debug("Leaving StudentSummaryBundledReportWriter ....");
	}
	
	public Long getBatchReportProcessId() {
		return batchReportProcessId;
	}


	public void setBatchReportProcessId(Long batchReportProcessId) {
		this.batchReportProcessId = batchReportProcessId;
	}

	public String getBundledReportType() {
		return bundledReportType;
	}

	public void setBundledReportType(String bundledReportType) {
		this.bundledReportType = bundledReportType;
	}

	public StepExecution getStepExecution() {
		return stepExecution;
	}

	public void setStepExecution(StepExecution stepExecution) {
		this.stepExecution = stepExecution;
	}
}
