package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.Instant;
import org.joda.time.Interval;
import org.joda.time.Period;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.domain.OrganizationBundleReport;
import edu.ku.cete.domain.ReportProcess;
import edu.ku.cete.domain.report.ReportProcessReason;
import edu.ku.cete.domain.report.ReportProcessRecordCounts;
import edu.ku.cete.model.common.CategoryDao;
import edu.ku.cete.service.organizationbundle.OrganizationBundleReportService;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.BatchUploadService;

@SuppressWarnings("unchecked")
public class BatchReportProcessJobListener implements JobExecutionListener {
	final static Log logger = LogFactory.getLog(BatchReportProcessJobListener.class);

	@Autowired
	private BatchReportProcessService batchReportProcessService;

	@Autowired
	private OrganizationBundleReportService bundleReportService;

	@Autowired
	CategoryDao categoryDao;

	@Autowired
	BatchUploadService batchUploadService;

	private Instant startTime;

	private Long completedStatusId;
	private Long failedStatusId;

	@Value("${external.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED;

	@Value("${external.schoolLevel.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL;

	@Value("${external.districtLevel.studentSummary.bundled.reportType}")
	private String REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL;
	
	@Value("${external.import.reportType.studentSummary}")
	private String REPORT_TYPE_STUDENT_SUMMARY; 
	
	@Value("${alternate.student.individual.report.type.code}")
	private String dbDLMStudentReportsImportReportType;
	
	@Value("${alternate.student.bundled.report.type.code}")
	private String alternateStudentBundledReportTypeCode;
	
	@Value("${external.import.reportType.school}")
	private String REPORT_TYPE_SCHOOL; 
	
	@Value("${external.districtLevel.school.bundled.reportType}")
	private String SCH_SUM_BUNDLED_DT_LVL;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("--> beforeJob");
		startTime = new Instant();
		JobParameters params = jobExecution.getJobParameters();
		jobExecution.getExecutionContext().put("batchReportProcessId", params.getLong("reportProcessId"));
		ReportProcess reportProcessRecord = new ReportProcess();
		reportProcessRecord.setStatus(jobExecution.getStatus().name());
		reportProcessRecord.setId(params.getLong("reportProcessId"));
		reportProcessRecord.setModifiedDate(new Date());
		batchReportProcessService.updateByPrimaryKeySelectiveBatchReportProcess(reportProcessRecord);
		logger.debug("<-- beforeJob");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.debug("--> afterJob");
		String duration = getDuration(new Interval(startTime, new Instant()).toPeriod());
		int successCount = 0, failedCount = 0;
		Long count=(long) 0;
		List<ReportProcessReason> errorMessages = new ArrayList<ReportProcessReason>();

		JobParameters params = jobExecution.getJobParameters();
		Long stateId =params.getLong("organizationId");
		Long assessmentProgramId =  params.getLong("assessmentProgramId");
		Long reportyear =params.getLong("schoolYear");
		String state=batchReportProcessService.getStateName(stateId);
		if (null == failedStatusId) {
			failedStatusId = categoryDao.getCategoryId("FAILED", "PD_REPORT_STATUS");
		}

		if (null == completedStatusId) {
			completedStatusId = categoryDao.getCategoryId("COMPLETED", "PD_REPORT_STATUS");
		}

		Long reportProcessId = (Long) jobExecution.getExecutionContext().get("batchReportProcessId");
		Collection<StepExecution> sExecutions = jobExecution.getStepExecutions();
		String stepName = "";
		for (StepExecution sExecution : sExecutions) {
			successCount = sExecution.getWriteCount();
			failedCount = sExecution.getProcessSkipCount();
			stepName = sExecution.getStepName();
			break;
		}
		// calc job
		if (stepName.startsWith("batchReportProcessAllScoreCalcJob")) {
			List<ReportProcessRecordCounts> recordCountList = batchReportProcessService
					.getRecordCountForBatchReportScoreCalcJob(reportProcessId);
			if (CollectionUtils.isNotEmpty(recordCountList)) {
				batchReportProcessService.insertBatchReportCounts(recordCountList, "Process data", reportProcessId);
			}
		} else if (stepName.startsWith("partitionedBatchStudentReportProcessStep")) {
			List<ReportProcessRecordCounts> recordCountList = batchReportProcessService
					.getRecordCountForBatchStudentReportJob(reportProcessId);
			if (CollectionUtils.isNotEmpty(recordCountList)) {
				batchReportProcessService.insertBatchReportCounts(recordCountList, "Student report", reportProcessId);
			}
		} else if (stepName.startsWith("partitionedBatchOrganizationReportProcessStep")) {
			List<ReportProcessRecordCounts> schoolRecordCountList = batchReportProcessService
					.getRecordCountForOrganizationReportJob(reportProcessId, "SCH");
			if (CollectionUtils.isNotEmpty(schoolRecordCountList)) {
				batchReportProcessService.insertBatchReportCounts(schoolRecordCountList, "School Report",
						reportProcessId);
			}
			List<ReportProcessRecordCounts> districtRecordCountList = batchReportProcessService
					.getRecordCountForOrganizationReportJob(reportProcessId, "DT");
			if (CollectionUtils.isNotEmpty(districtRecordCountList)) {
				batchReportProcessService.insertBatchReportCounts(districtRecordCountList, "District Report",
						reportProcessId);
			}
		} else if (stepName.startsWith("partitionedbatchDistrictPdfFilesofStudentReportsProcessStep")
				|| stepName.startsWith("partitionedbatchSchoolSummaryBundledStep")) {
			// Update records in organizationdynamicbundlereportprocess table
			List<OrganizationBundleReport> bundleReports = (List<OrganizationBundleReport>) jobExecution
					.getExecutionContext().get("bundleReports");
			Set<Long> successDistrictIds = (Set<Long>) jobExecution.getExecutionContext().get("successDistrictIds");

			for (OrganizationBundleReport bundleReport : bundleReports) {
				if (successDistrictIds.contains(bundleReport.getOrganizationId())) {
					bundleReport.setStatus(completedStatusId);
					bundleReport.setModifiedDate(new Date());
					bundleReportService.updateByPrimaryKeySelective(bundleReport);
				} else {
					bundleReport.setStatus(failedStatusId);
					bundleReport.setModifiedDate(new Date());
					bundleReportService.updateByPrimaryKeySelective(bundleReport);
				}
			}
			
			if (stepName.startsWith("partitionedbatchDistrictPdfFilesofStudentReportsProcessStep")){
				
				Long bundledCount=batchReportProcessService.getStudentsCountOfDistrictlLevel(stateId, assessmentProgramId,
						reportyear, alternateStudentBundledReportTypeCode);
				
				Long studentCount=batchReportProcessService.getexternelStudentsCountOfDistrictlLevel(stateId, assessmentProgramId,
						reportyear, dbDLMStudentReportsImportReportType);
				
				if (bundledCount.longValue() == studentCount.longValue()) {
					count = bundledCount;
				} else {
					jobExecution.setStatus(BatchStatus.FAILED);
					ReportProcessReason buProcessReason = new ReportProcessReason();
					buProcessReason.setReportProcessId(reportProcessId);
					buProcessReason.setReason("counts mismatched");
					errorMessages.add(buProcessReason);
				}
				
			}else if (stepName.startsWith("partitionedbatchSchoolSummaryBundledStep")){
				
				Long bundledCount=batchReportProcessService.getSchoolSummaryBundleCountOfDistrictlLevel(stateId, assessmentProgramId,
							reportyear, SCH_SUM_BUNDLED_DT_LVL);
					
				Long schoolCount=batchReportProcessService.getSchoolSummaryCountOfDistrictlLevel(stateId, assessmentProgramId,
							reportyear, REPORT_TYPE_SCHOOL);
					
				if (bundledCount.longValue() == schoolCount.longValue()) {
					count = bundledCount;
				} else {
					jobExecution.setStatus(BatchStatus.FAILED);
					ReportProcessReason buProcessReason = new ReportProcessReason();
					buProcessReason.setReportProcessId(reportProcessId);
					buProcessReason.setReason("counts mismatched");
					errorMessages.add(buProcessReason);
				}	
			}

		} else if (stepName.startsWith("partitionedbatchSchoolPdfFilesofStudentReportsProcessStep")
				|| stepName.startsWith("partitionedbatchStudentSummaryBundledStep")) {
			// Update records in organizationdynamicbundlereportprocess table
			List<OrganizationBundleReport> bundleReports = (List<OrganizationBundleReport>) jobExecution
					.getExecutionContext().get("bundleReports");
			Set<Long> successOrgIds = null; //

			if (CollectionUtils.isNotEmpty(bundleReports)) {
				if (REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL.equalsIgnoreCase(bundleReports.get(0).getReportType())
						&& stepName.startsWith("partitionedbatchStudentSummaryBundledStep")) {
					successOrgIds = (Set<Long>) jobExecution.getExecutionContext().get("successDistrictIds");
					Long bundledCount=batchReportProcessService.getStudentsCountOfDistrictlLevel(stateId, assessmentProgramId,
							reportyear, REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
					
					Long studentCount=batchReportProcessService.getexternelStudentsCountOfDistrictlLevel(stateId, assessmentProgramId,
							reportyear, REPORT_TYPE_STUDENT_SUMMARY);
					
					if(bundledCount.longValue() == studentCount.longValue())
					{
						count=bundledCount;
					}
					else
					{
						jobExecution.setStatus(BatchStatus.FAILED);
						ReportProcessReason buProcessReason = new ReportProcessReason();
						buProcessReason.setReportProcessId(reportProcessId);
						buProcessReason.setReason("counts mismatched");
						errorMessages.add(buProcessReason);
					}
				} else if(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL.equalsIgnoreCase(bundleReports.get(0).getReportType())
						&& stepName.startsWith("partitionedbatchStudentSummaryBundledStep")) {
					successOrgIds = (Set<Long>) jobExecution.getExecutionContext().get("successSchoolIds");
					Long bundledCount=batchReportProcessService.getStudentsCountOfSchoolLevel(stateId, assessmentProgramId,
							reportyear, REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
					
					Long studentCount=batchReportProcessService.getexternelStudentsCountOfSchoolLevel(stateId, assessmentProgramId,
							reportyear, REPORT_TYPE_STUDENT_SUMMARY);
					
					
					if(bundledCount.longValue() == studentCount.longValue())
					{
						count=bundledCount;
					}
					else
					{
						jobExecution.setStatus(BatchStatus.FAILED);
						ReportProcessReason buProcessReason = new ReportProcessReason();
						buProcessReason.setReportProcessId(reportProcessId);
						buProcessReason.setReason("counts mismatched");
						errorMessages.add(buProcessReason);
					}
				}
				else if(stepName.startsWith("partitionedbatchSchoolPdfFilesofStudentReportsProcessStep"))
				{
					successOrgIds = (Set<Long>) jobExecution.getExecutionContext().get("successSchoolIds");
					Long bundledCount=batchReportProcessService.getStudentsCountOfSchoolLevel(stateId, assessmentProgramId,
							reportyear, alternateStudentBundledReportTypeCode);
					
					Long studentCount=batchReportProcessService.getexternelStudentsCountOfSchoolLevel(stateId, assessmentProgramId,
							reportyear, dbDLMStudentReportsImportReportType);
					
					
					if(bundledCount.longValue() == studentCount.longValue())
					{
						count=bundledCount;
					}
					else
					{
						jobExecution.setStatus(BatchStatus.FAILED);
						ReportProcessReason buProcessReason = new ReportProcessReason();
						buProcessReason.setReportProcessId(reportProcessId);
						buProcessReason.setReason("counts mismatched");
						errorMessages.add(buProcessReason);
					}
				}
				for (OrganizationBundleReport bundleReport : bundleReports) {
					if (bundleReport.getReportType() != null && (REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_DT_LVL
							.equalsIgnoreCase(bundleReport.getReportType())
							|| REPORT_TYPE_STUDENT_SUMMARY_BUNDLED_SCH_LVL
									.equalsIgnoreCase(bundleReport.getReportType()))) {
						bundleReport.setReportTypeCode(REPORT_TYPE_STUDENT_SUMMARY_BUNDLED);
					}
					if (successOrgIds.contains(bundleReport.getOrganizationId())) {
						bundleReport.setStatus(completedStatusId);
						bundleReport.setModifiedDate(new Date());
						bundleReportService.updateByPrimaryKeySelective(bundleReport);
					} else {
						bundleReport.setStatus(failedStatusId);
						bundleReport.setModifiedDate(new Date());
						bundleReportService.updateByPrimaryKeySelective(bundleReport);
					}
				}
				
			}
		} else if (stepName.startsWith("batchDLMOrganizationSummaryReportProcessStep")) {
			// mark the GRF batch to completed on success of this step
			/*
			 * BatchUpload bUpload =
			 * batchUploadService.selectByPrimaryKeyBatchUpload(params.getLong(
			 * "batchUploadProcessId"));
			 * 
			 * bUpload.setStatus("UPLOAD COMPLETED, REPORT GENERATION COMPLETED");
			 * bUpload.setModifiedDate(new Date());
			 * 
			 * batchUploadService.updateByPrimaryKeySelectiveBatchUpload(bUpload);
			 */

		}

		ReportProcess reportProcessRecord = new ReportProcess();
		reportProcessRecord.setStatus(jobExecution.getStatus().name());
		reportProcessRecord.setId(reportProcessId);
		reportProcessRecord.setFailedCount(failedCount);
		reportProcessRecord.setSuccessCount(successCount);
		reportProcessRecord.setModifiedDate(new Date());
		if (stepName.startsWith("partitionedbatchDistrictPdfFilesofStudentReportsProcessStep") ||
				stepName.startsWith("partitionedbatchSchoolPdfFilesofStudentReportsProcessStep") ||
				stepName.startsWith("partitionedbatchStudentSummaryBundledStep") ||
				stepName.startsWith("partitionedbatchSchoolSummaryBundledStep")){
			reportProcessRecord.setCount(count);			
		}
		reportProcessRecord.setState(state);
		batchReportProcessService.updateByPrimaryKeySelectiveBatchReportProcess(reportProcessRecord);

		
		if (jobExecution.getStatus().equals(BatchStatus.FAILED)) {

			if (stepName.startsWith("batchDLMOrganizationSummaryReportProcessStep")) {
				// mark the GRF batch to completed on success of this step
				/*
				 * BatchUpload bUpload =
				 * batchUploadService.selectByPrimaryKeyBatchUpload(params.getLong(
				 * "batchUploadProcessId"));
				 * bUpload.setStatus("UPLOAD COMPLETED, REPORT GENERATION FAILED");
				 * bUpload.setModifiedDate(new Date());
				 * batchUploadService.updateByPrimaryKeySelectiveBatchUpload(bUpload);
				 */
			}

			ReportProcessReason buProcessReason = new ReportProcessReason();
			buProcessReason.setReportProcessId(reportProcessId);
			for (Throwable t : jobExecution.getAllFailureExceptions()) {
				buProcessReason.setReason(t.getMessage());
				buProcessReason.setReportProcessId(reportProcessId);
				errorMessages.add(buProcessReason);
			}
		}

		if (CollectionUtils.isNotEmpty(errorMessages)) {
			batchReportProcessService.insertSelectiveReportProcessReasons(errorMessages);
		}

		// logger.debug("***** Batch Upload successCount: "+successCount);
		// logger.debug("***** Batch Upload failed/skippedCount: "+failedCount);
		// logger.debug("Batch Upload Finish job:
		// "+jobExecution.getExecutionContext().get("batchUploadId") +", duration:" +
		// duration);
		logger.debug("Batch Report Process Finish job: " + reportProcessId + ", duration after reasons write: "
				+ getDuration(new Interval(startTime, new Instant()).toPeriod()));
		logger.debug("<-- afterJob");
	}

	private static String getDuration(Period period) {
		return period.getHours() + ":" + period.getMinutes() + ":" + period.getSeconds() + "." + period.getMillis();
	}

}
