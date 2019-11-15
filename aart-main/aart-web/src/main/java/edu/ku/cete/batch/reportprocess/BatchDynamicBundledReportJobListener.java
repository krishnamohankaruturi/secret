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

public class BatchDynamicBundledReportJobListener implements JobExecutionListener {
	final static Log logger = LogFactory.getLog(BatchDynamicBundledReportJobListener.class);
	

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

	@Value("${general.student.bundled.report.type.code}")
	private String generalStudentBundledReportTypeCode;
		
	@Value("${alternate.student.bundled.report.type.code}")
	private String alternateStudentBundledReportTypeCode;

	@Value("${cpass.student.bundled.report.type.code}")
	private String cpassStudentBundledReportTypeCode;
	
	@Value("${kelpa.student.bundled.report.type.code}")
	private String kelpaStudentBundledReportTypeCode;
	
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
      
        String reportType="";
		if(assessmentProgram!=null && "CPASS".equals(assessmentProgram.getAbbreviatedname()))
			reportType=cpassStudentBundledReportTypeCode;
		else if(assessmentProgram!=null && "DLM".equals(assessmentProgram.getAbbreviatedname()))
			reportType=alternateStudentBundledReportTypeCode;
		else if(assessmentProgram!=null && "KAP".equals(assessmentProgram.getAbbreviatedname()))
			reportType=generalStudentBundledReportTypeCode;
		else if(assessmentProgram!=null && "KELPA2".equals(assessmentProgram.getAbbreviatedname()))
			reportType=kelpaStudentBundledReportTypeCode;
               
        
        List<Long> schoolIds = new ArrayList<Long>();
        if(bundleReport.getSchoolIds() != null){	       
			for (String s : bundleReport.getSchoolIds().split(","))
				schoolIds.add(Long.parseLong(s));
        }
		
		List<Long> subjectIds = new ArrayList<Long>();
		for (String s : bundleReport.getSubjects().split(","))
			subjectIds.add(Long.parseLong(s));
		
		List<Long> gradeIds = new ArrayList<Long>();
		for (String s : bundleReport.getGrades().split(","))
			gradeIds.add(Long.parseLong(s));		
		
		batchReportProcessService.deleteOrganizationBundleReportFilesByOrganization(bundleReport.getOrganizationId(), bundleReport.getAssessmentProgramId()
                ,bundleReport.getSchoolYear(), reportType);
		batchReportProcessService.deleteOrganizationBundleReportsByOrganization(bundleReport.getOrganizationId(), bundleReport.getAssessmentProgramId()
                ,bundleReport.getSchoolYear(), reportType);
        
		jobExecution.getExecutionContext().put("schoolIds", schoolIds);
		jobExecution.getExecutionContext().put("subjectIds", subjectIds);
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
		
		logger.info("Dynamic Bundle Report Finish job: "+jobExecution.getJobParameters().getLong("requestId") +", duration:" + duration);
		logger.debug("Dynamic Bundle Report Finish job: "+jobExecution.getJobParameters().getLong("requestId") +", duration after reasons write: " 
					+ getDuration(new Interval(startTime, new Instant()).toPeriod()));
		logger.debug("<-- afterJob");
	}

	private static String getDuration(Period period) {
		return period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds() + "." + period.getMillis();
	}
	
}