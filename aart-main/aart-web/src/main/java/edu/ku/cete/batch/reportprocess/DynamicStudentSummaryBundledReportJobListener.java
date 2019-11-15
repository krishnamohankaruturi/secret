/**
 * 
 */
package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.OrganizationBundleReport;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.organizationbundle.OrganizationBundleReportService;
import edu.ku.cete.service.report.BatchReportProcessService;

/**
 * @author Kiran Reddy Taduru
 * May 19, 2017 3:10:48 PM
 */
public class DynamicStudentSummaryBundledReportJobListener implements JobExecutionListener {

final static Log logger = LogFactory.getLog(DynamicStudentSummaryBundledReportJobListener.class);
	

	@Autowired
	OrganizationBundleReportService bundleReportService;
	
	@Autowired
	BatchReportProcessService batchReportProcessService;
	
	@Autowired
	CategoryDao categoryDao;
	
	@Autowired
	AssessmentProgramService assessmentProgramService;
	
    private Instant startTime; 
    
    private Long inProgressStatusId;
	
	private Long inQueueStatusId;
	
	private Long failedStatusId;
	
	private Long completedStatusId;
    
	@Value("${external.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED;
	
	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("--> beforeJob");
		startTime = new Instant();		
        JobParameters params = jobExecution.getJobParameters();
        
    	
        if(null == inProgressStatusId){
			inProgressStatusId = categoryDao.getCategoryId("IN_PROGRESS", "PD_REPORT_STATUS");
		}
					
		if(null == inQueueStatusId){
			inQueueStatusId = categoryDao.getCategoryId("IN_QUEUE", "PD_REPORT_STATUS");
		}
					
		if(null == failedStatusId){
			failedStatusId = categoryDao.getCategoryId("FAILED", "PD_REPORT_STATUS");
		}
		
		if(null == completedStatusId){
			completedStatusId = categoryDao.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
		}
		
        OrganizationBundleReport bundleReport = bundleReportService.selectByPrimaryKey(params.getLong("requestId"));
        if(bundleReport  != null){
	        bundleReport.setStatus(inProgressStatusId);	
	        bundleReport.setModifiedDate(new Date());
	        bundleReportService.updateByPrimaryKeySelective(bundleReport);
	 
	        AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(bundleReport.getAssessmentProgramId());
	        List<Long> schoolIds = new ArrayList<Long>();
	        if(bundleReport.getSchoolIds() != null){	       
				for (String s : bundleReport.getSchoolIds().split(",")){
					schoolIds.add(Long.parseLong(s));
				}					
	        }			
			
			List<Long> gradeIds = new ArrayList<Long>();
			for (String s : bundleReport.getGrades().split(",")){
				gradeIds.add(Long.parseLong(s));
			}
						
			
			batchReportProcessService.deleteOrganizationBundleReportFilesByOrganization(bundleReport.getOrganizationId(), bundleReport.getAssessmentProgramId()
	                ,bundleReport.getSchoolYear(), REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
			batchReportProcessService.deleteOrganizationBundleReportsByOrganization(bundleReport.getOrganizationId(), bundleReport.getAssessmentProgramId()
	                ,bundleReport.getSchoolYear(), REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
	        
			jobExecution.getExecutionContext().put("schoolIds", schoolIds);
			jobExecution.getExecutionContext().put("gradeIds", gradeIds);
	        jobExecution.getExecutionContext().put("bundleRequest", bundleReport);
	        jobExecution.getExecutionContext().put("assessmentProgramCode", assessmentProgram.getAbbreviatedname());
        }
		logger.debug("<-- beforeJob");
	}


	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.debug("--> afterJob");
		String duration = getDuration(new Interval(startTime, new Instant()).toPeriod());
		OrganizationBundleReport bundleReport = bundleReportService.selectByPrimaryKey(jobExecution.getJobParameters().getLong("requestId"));
		
		if(jobExecution.getStatus().equals(BatchStatus.FAILED)) {
			bundleReport.setStatus(failedStatusId);
		}else{
			bundleReport.setStatus(completedStatusId);
		}
		bundleReport.setModifiedDate(new Date());
		bundleReportService.updateByPrimaryKeySelective(bundleReport);
		
		logger.info("StudentSummary Dynamic Bundle Report Finish job: "+jobExecution.getJobParameters().getLong("requestId") +", duration:" + duration);
		logger.debug("StudentSummary Dynamic Bundle Report Finish job: "+jobExecution.getJobParameters().getLong("requestId") +", duration after reasons write: " 
					+ getDuration(new Interval(startTime, new Instant()).toPeriod()));
		logger.debug("<-- afterJob");
	}

	private static String getDuration(Period period) {
		return period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds() + "." + period.getMillis();
	}

}
