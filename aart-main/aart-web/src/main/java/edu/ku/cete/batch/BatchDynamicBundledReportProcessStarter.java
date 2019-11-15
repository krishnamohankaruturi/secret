package edu.ku.cete.batch;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;

import edu.ku.cete.domain.OrganizationBundleReport;
import edu.ku.cete.service.organizationbundle.OrganizationBundleReportService;

/* sudhansu.b
 * Added for F460 - Dynamic bundle report
 */	
public class BatchDynamicBundledReportProcessStarter extends BaseBatchProcessStarter {

    private final static Log logger = LogFactory.getLog(BatchDynamicBundledReportProcessStarter.class);
    
    @Resource
	private Job batchDynamicBundleofStudentReportJob;  
	
	private String isScheduleOn;	
   
	
	@Autowired
	OrganizationBundleReportService bundleReportService;
     
	public Long startbatchDynamicBundledReportProcess()
			throws Exception {
 		logger.debug("--> BatchDynamicBundledReportProcess isScheduleOn " + isScheduleOn);
		Long jobId = null;
			
		//Get one pending records from new table
		OrganizationBundleReport bundleReport = bundleReportService.getLatestPendingReqest();
		if(bundleReport != null){
			JobParametersBuilder builder = new JobParametersBuilder();
			
			//set all the requested parameter as job parameter
			builder.addDate("run date", new Date());
			builder.addLong("requestId", bundleReport.getId());
			
			//trigger the job
			jobId = startJob(batchDynamicBundleofStudentReportJob, builder.toJobParameters());
		}
		logger.debug("<-- startBatchDynamicBundledReport-->");
		return jobId;
	}

	public void setBatchDynamicBundleofStudentReportJob(Job batchDynamicBundleofStudentReportJob) {
		this.batchDynamicBundleofStudentReportJob = batchDynamicBundleofStudentReportJob;
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
		// TODO Auto-generated method stub
		return null;
	}

}
