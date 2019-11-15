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
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.service.report.BatchUploadService;

@Component
public class XmlBatchUploadProcessStarter extends BaseBatchProcessStarter {

	private final static Log logger = LogFactory.getLog(XmlBatchUploadProcessStarter.class);
	@Resource
	private Job xmlBatchUploadJob;

	private String isScheduleOn;

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

				/* XML Record Upload */
				builder.addLong("stateId", pendingUploadRecord.getStateId());
				builder.addLong("districtId", pendingUploadRecord.getDistrictId());
				builder.addLong("schoolId", pendingUploadRecord.getSchoolId());
				builder.addLong("selectedOrgId", pendingUploadRecord.getSelectedOrgId());
				builder.addLong("uploadedUserOrgId", pendingUploadRecord.getUploadedUserOrgId());
				builder.addLong("uploadedUserGroupId", pendingUploadRecord.getUploadedUserGroupId());
				builder.addString("UPLOAD_TYPE", "XML_FILE_DATA");
				
				//Add upload id
				builder.addLong("sifUploadId", pendingUploadRecord.getDocumentId());

				builder.addLong("uploadedUserId", pendingUploadRecord.getCreatedUser());
				// get Subject
				builder.addString("uploadTypeCode", pendingUploadRecord.getUploadType());
				// need unique job parameter to rerun the completed job
				builder.addDate("run date", new Date());
				builder.addString("inputFile", pendingUploadRecord.getFilePath());
				builder.addLong("batchUploadId", pendingUploadRecord.getId());
				builder.addString("recordType", getUploadType());
				jobId = startJob(xmlBatchUploadJob, builder.toJobParameters());
			}
		}
		logger.debug("<-- startBatchUploadProcess");
		return jobId;
	}

	public void setBatchUploadJob(Job batchUploadJob) {
		this.xmlBatchUploadJob = batchUploadJob;
	}

	public String getIsScheduleOn() {
		return isScheduleOn;
	}

	public void setIsScheduleOn(String isScheduleOn) {
		this.isScheduleOn = isScheduleOn;
	}

	@Override
	public String getUploadType() {
		return "XML_RECORD_TYPE";
	}

}
