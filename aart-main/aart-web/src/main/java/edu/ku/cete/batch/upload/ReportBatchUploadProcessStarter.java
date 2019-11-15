package edu.ku.cete.batch.upload;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import edu.ku.cete.batch.BaseBatchProcessStarter;
import edu.ku.cete.domain.content.AssessmentProgram;
import edu.ku.cete.domain.content.ContentArea;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.service.AssessmentProgramService;
import edu.ku.cete.service.ContentAreaService;
import edu.ku.cete.service.report.BatchUploadService;

@Component
public class ReportBatchUploadProcessStarter extends BaseBatchProcessStarter {

	private final static Log logger = LogFactory.getLog(ReportBatchUploadProcessStarter.class);
	@Resource
	private Job batchUploadJob;

	@Autowired
	private AssessmentProgramService assessmentProgramService;

	private String isScheduleOn;

	@Autowired
	ContentAreaService contentAreaService;

	@Autowired
	private BatchUploadService batchUploadService;

	public Long startBatchUploadProcess() throws Exception {
		logger.debug("--> startBatchUploadProcess isScheduleOn " + isScheduleOn);
		Long jobId = null;

		if (isScheduleOn.equalsIgnoreCase("ON")) {
			logger.debug("startBatchUploadProcess -- ON ");
			BatchUpload pendingUploadRecord = batchUploadService.selectOnePending("Pending", getUploadType());
			if (pendingUploadRecord != null) {
				JobParametersBuilder builder = new JobParametersBuilder();

				/* Summative report Upload */
				AssessmentProgram assessmentProgram = assessmentProgramService.findByAssessmentProgramId(pendingUploadRecord
						.getAssessmentProgramId());
				ContentArea contentArea = contentAreaService.selectByPrimaryKey(pendingUploadRecord.getContentAreaId());
				builder.addString("assessmentProgramCode", assessmentProgram.getAbbreviatedname());
				builder.addLong("assessmentProgramId", pendingUploadRecord.getAssessmentProgramId());
				// builder.addLong("uploadTypeId", uploadTypeId);
				builder.addLong("subjectId", pendingUploadRecord.getContentAreaId());
				builder.addString("subjectCode", contentArea.getAbbreviatedName());
				builder.addString("UPLOAD_TYPE", "REPORT");

				// get Subject
				builder.addString("uploadTypeCode", pendingUploadRecord.getUploadType());
				// need unique job parameter to rerun the completed job
				builder.addDate("run date", new Date());
				builder.addString("inputFile", pendingUploadRecord.getFilePath());
				builder.addLong("batchUploadId", pendingUploadRecord.getId());
				builder.addString("recordType", getUploadType());
				builder.addLong("documentId", pendingUploadRecord.getDocumentId());
				builder.addLong("testingProgramId", pendingUploadRecord.getTestingProgramId());
				builder.addString("testingProgramName", pendingUploadRecord.getTestingProgramName());
				builder.addString("reportCycle", pendingUploadRecord.getReportCycle());
				builder.addLong("createdUser", pendingUploadRecord.getCreatedUser());
				
				jobId = startJob(batchUploadJob, builder.toJobParameters());
			}
		}
		logger.debug("<-- startBatchUploadProcess");
		return jobId;
	}

	public void setBatchUploadJob(Job batchUploadJob) {
		this.batchUploadJob = batchUploadJob;
	}

	public String getIsScheduleOn() {
		return isScheduleOn;
	}

	public void setIsScheduleOn(String isScheduleOn) {
		this.isScheduleOn = isScheduleOn;
	}

	@Override
	public String getUploadType() {
		return "REPORT_UPLOAD_FILE_TYPE";
	}

}
