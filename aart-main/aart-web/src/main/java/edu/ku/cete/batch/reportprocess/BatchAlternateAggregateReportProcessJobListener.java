package edu.ku.cete.batch.reportprocess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.StepExecution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.report.domain.BatchUploadReason;
import edu.ku.cete.service.report.BatchReportProcessService;
import edu.ku.cete.service.report.BatchUploadService;
import edu.ku.cete.service.report.UploadGrfFileWriterProcessService;
import edu.ku.cete.util.SourceTypeEnum;

public class BatchAlternateAggregateReportProcessJobListener implements JobExecutionListener {

	final static Log logger = LogFactory.getLog(BatchAlternateAggregateReportProcessJobListener.class);

	@Autowired
	private BatchUploadService batchUploadService;

	@Autowired
	private BatchReportProcessService batchReportProcessService;

	@Autowired
	private UploadGrfFileWriterProcessService uploadGrfFileWriterProcessService;

	@Value("${GRF.district.report.process.string}")
	private String GrfDistrictReportProcessString;

	@Value("${GRF.school.report.process.string}")
	private String GrfSchoolReportProcessString;

	@Value("${GRF.classroom.report.process.string}")
	private String GrfClassroomReportProcessString;

	@Override
	public void beforeJob(JobExecution jobExecution) {
		logger.debug("--> beforeJob");

		JobParameters params = jobExecution.getJobParameters();
		String jobName = jobExecution.getJobInstance().getJobName();

		String reportProcess = null;

		if (jobName.equals("batchAlternateAggregateOrganizationReportJob")) {
			reportProcess = GrfSchoolReportProcessString;
		} else if (jobName.equals("batchAlternateAggregateClassRoomReportJob")) {
			reportProcess = GrfClassroomReportProcessString;
		}

		if (jobName.startsWith("batchAlternateAggregateOrganizationReportJob")
				|| jobName.startsWith("batchAlternateAggregateClassRoomReportJob")) {
			if (jobName.startsWith("batchAlternateAggregateOrganizationReportJob")) {
				reportProcess = GrfSchoolReportProcessString;
			} else {
				reportProcess = GrfClassroomReportProcessString;
			}

			Date now = new Date();
			BatchUpload upload = new BatchUpload();
			upload.setAssessmentProgramId(params.getLong("assessmentProgramId"));
			upload.setContentAreaId(0L);
			upload.setSubmissionDate(now);
			upload.setCreatedUser(params.getLong("userId"));
			upload.setCreatedUserDisplayName(params.getString("userDisplayname"));
			upload.setStatus("STARTED");
			upload.setFileName("N/A");
			upload.setFilePath("");
			upload.setActiveFlag(true);
			upload.setStateId(params.getLong("stateId"));
			upload.setReportYear(params.getLong("reportYear"));
			upload.setGrfProcessType(reportProcess);
			batchUploadService.insertBatchUpload(upload);

			jobExecution.getExecutionContext().put("batchReportProcessId", upload.getId());
		}

		logger.debug("<-- beforeJob");
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		logger.debug("--> afterJob");

		JobParameters params = jobExecution.getJobParameters();
		int successCount = 0, failedCount = 0;
		String reportProcess = null;

		Long reportProcessId = 0L;
		if (jobExecution.getExecutionContext().get("batchReportProcessId") == null)
			reportProcessId = params.getLong("reportProcessId");
		else
			reportProcessId = (Long) jobExecution.getExecutionContext().get("batchReportProcessId");

		Collection<StepExecution> sExecutions = jobExecution.getStepExecutions();
		String stepName = "";
		for (StepExecution sExecution : sExecutions) {
			successCount = sExecution.getWriteCount();
			failedCount = sExecution.getProcessSkipCount();
			stepName = sExecution.getStepName();
			break;
		}

		if (stepName.startsWith("batchDLMOrganizationSummaryReportProcessStep")) {
			reportProcess = GrfDistrictReportProcessString;
		} else if (stepName.startsWith("partitionedbatchAlternateOrganizationReportProcessStep")) {
			reportProcess = GrfSchoolReportProcessString;
		} else if (stepName.startsWith("partitionedbatchAlternateClassRoomReportProcessStep")) {
			reportProcess = GrfClassroomReportProcessString;
		}

		if (stepName.startsWith("batchDLMOrganizationSummaryReportProcessStep")
				|| stepName.startsWith("partitionedbatchAlternateOrganizationReportProcessStep")
				|| stepName.startsWith("partitionedbatchAlternateClassRoomReportProcessStep")) {

			BatchUpload bUpload = batchUploadService.selectByPrimaryKeyBatchUpload(reportProcessId);
			bUpload.setStatus(jobExecution.getStatus().name());
			bUpload.setModifiedDate(new Date());
			bUpload.setSuccessCount(successCount);
			bUpload.setFailedCount(failedCount);
			bUpload.setGrfProcessType(reportProcess);
			batchUploadService.updateByPrimaryKeySelectiveBatchUpload(bUpload);

		}

		List<BatchUploadReason> errorMessages = new ArrayList<BatchUploadReason>();
		if (jobExecution.getStatus().equals(BatchStatus.FAILED)) {

			BatchUploadReason buReason = new BatchUploadReason();
			buReason.setBatchUploadId(reportProcessId);
			for (Throwable t : jobExecution.getAllFailureExceptions()) {
				buReason.setReason(t.getMessage());
				buReason.setFieldName("");
				errorMessages.add(buReason);
			}
		}

		if (CollectionUtils.isNotEmpty(errorMessages)) {
			batchUploadService.insertBatchUploadReasons(errorMessages);
		}

		logger.debug("<-- afterJob");
	}

}