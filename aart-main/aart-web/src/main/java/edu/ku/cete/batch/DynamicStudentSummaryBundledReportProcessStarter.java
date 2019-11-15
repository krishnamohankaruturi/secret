/**
 * 
 */
package edu.ku.cete.batch;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.OrganizationBundleReport;
import edu.ku.cete.service.organizationbundle.OrganizationBundleReportService;

/**
 * @author Kiran Reddy Taduru
 * May 19, 2017 10:22:37 AM
 */
public class DynamicStudentSummaryBundledReportProcessStarter extends BaseBatchProcessStarter {

private final static Log logger = LogFactory.getLog(DynamicStudentSummaryBundledReportProcessStarter.class);
    
    @Resource
	private Job batchDynamicBundleOfStudentSummaryReportsJob;  
	
	private String isScheduleOn;	
   
	
	@Autowired
	OrganizationBundleReportService bundleReportService;
    
	@Value("${external.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED;
	
	public Long startbatchStudentSummaryDynamicBundledReportProcess() throws Exception {
 		logger.debug("--> Entering startbatchDynamicBundledReportProcess isScheduleOn " + isScheduleOn);
		Long jobId = null;
			
		//Get one pending record
		OrganizationBundleReport bundleReport = bundleReportService.getLatestPendingReqestForStudentBundledReport(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
		if(bundleReport != null){
			JobParametersBuilder builder = new JobParametersBuilder();
			
			//set all the requested parameter as job parameter
			builder.addDate("run date", new Date());
			builder.addLong("requestId", bundleReport.getId());
			
			//trigger the job
			jobId = startJob(batchDynamicBundleOfStudentSummaryReportsJob, builder.toJobParameters());
		}
		logger.debug("<-- Leaving startbatchDynamicBundledReportProcess -->");
		return jobId;
	}

	
	public Job getBatchDynamicBundleOfStudentSummaryReportsJob() {
		return batchDynamicBundleOfStudentSummaryReportsJob;
	}


	public void setBatchDynamicBundleOfStudentSummaryReportsJob(Job batchDynamicBundleOfStudentSummaryReportsJob) {
		this.batchDynamicBundleOfStudentSummaryReportsJob = batchDynamicBundleOfStudentSummaryReportsJob;
	}


	public String getIsScheduleOn() {
		return isScheduleOn;
	}

	public void setIsScheduleOn(String isScheduleOn) {
		this.isScheduleOn = isScheduleOn;
	}

	public String getRecordType() {
		return null;
	}

	@Override
	public String getUploadType() {
		return null;
	}

}
