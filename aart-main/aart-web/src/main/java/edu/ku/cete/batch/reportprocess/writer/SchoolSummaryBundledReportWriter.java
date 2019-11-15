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
import org.springframework.security.core.context.SecurityContextHolder;

import edu.ku.cete.domain.report.OrganizationReportDetails;
import edu.ku.cete.domain.user.User;
import edu.ku.cete.domain.user.UserDetailImpl;
import edu.ku.cete.service.report.BatchReportProcessService;


/**
 * @author Kiran Reddy Taduru
 * May 19, 2017 1:58:57 PM
 */
public class SchoolSummaryBundledReportWriter implements ItemWriter<OrganizationReportDetails> {
	final static Log logger = LogFactory.getLog(SchoolSummaryBundledReportWriter.class);

	@Autowired 
	BatchReportProcessService batchReportProcessService;
	
	private Long batchReportProcessId;
	private String bundledReportType;
	
	private JobExecution jobExecution;
	private StepExecution stepExecution;
	
	@Override
	public void write(List<? extends OrganizationReportDetails> organizationReportList) throws Exception {
		logger.debug("Inside  SchoolSummaryBundledReportWriter ....");
		if(!organizationReportList.isEmpty()){
			jobExecution = stepExecution.getJobExecution();
			Set<Long> orgIds = new HashSet<Long>();
			UserDetailImpl userDetails = (UserDetailImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			User user = userDetails.getUser();
			
			for(OrganizationReportDetails organizationReport : organizationReportList){				
				orgIds.add(organizationReport.getDistrictId());
				OrganizationReportDetails districtStudentSummaryBundleReport = new OrganizationReportDetails();
				districtStudentSummaryBundleReport.setAssessmentProgramId(organizationReport.getAssessmentProgramId());
				districtStudentSummaryBundleReport.setSchoolYear(organizationReport.getSchoolYear());
				districtStudentSummaryBundleReport.setOrganizationId(organizationReport.getDistrictId());
				districtStudentSummaryBundleReport.setCreatedDate(new Date());
				districtStudentSummaryBundleReport.setBatchReportProcessId(batchReportProcessId);
				districtStudentSummaryBundleReport.setDetailedReportPath(organizationReport.getDetailedReportPath());
				districtStudentSummaryBundleReport.setSchoolReportPdfSize(organizationReport.getSchoolReportPdfSize());
				districtStudentSummaryBundleReport.setReportType(organizationReport.getReportType());
				districtStudentSummaryBundleReport.setModifiedDate(new Date());
				districtStudentSummaryBundleReport.setCreatedUser(user.getId());
				districtStudentSummaryBundleReport.setModifiedUser(user.getId());
				batchReportProcessService.insertSchoolReportOfStudentFilesPdf(districtStudentSummaryBundleReport);
				logger.debug("Completed SchoolSummaryBundledReportWriter Org Id - " + organizationReport.getDistrictId());				
			}			
		
			((Set<Long>)jobExecution.getExecutionContext().get("successDistrictIds")).addAll(orgIds);
			
			
		}
		logger.debug("Leaving SchoolSummaryBundledReportWriter ....");
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
