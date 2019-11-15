package edu.ku.cete.batch.upload;

import java.util.Date;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import edu.ku.cete.batch.BaseBatchProcessStarter;
import edu.ku.cete.report.domain.BatchUpload;
import edu.ku.cete.service.report.BatchUploadService;

@Component
public class BatchUploadProcessStarter extends BaseBatchProcessStarter {

    private final static Log logger = LogFactory.getLog(BatchUploadProcessStarter.class);
	@Resource
	private Job batchUploadJob;  
	
	@Resource
	private Job batchGRFProcessJob;
	
	@Resource
	private Job batchUploadExcelJob;  
	
	private String isScheduleOn;
	
	@Value("${uploadCommonGrfFileType}")
	private String uploadCommonGrfFileType;
	
	@Value("${uploadIowaGrfFileType}")
	private String uploadIowaGrfFileType;
	
	@Value("${uploadNewYorkGrfFileType}")
	private String uploadNewYorkGrfFileType;
	
	@Value("${uploadDelawareGrfFileType}")
	private String uploadDelawareGrfFileType;
	
	@Value("${uploadDCGrfFileType}")
	private String uploadDCGrfFileType;
	
	@Value("${uploadArkansasGrfFileType}")
	private String uploadArkansasGrfFileType;
	
    @Autowired
  	private BatchUploadService batchUploadService;
     
	public Long startBatchUploadProcess()
			throws Exception {
 		logger.debug("--> startBatchUploadProcess isScheduleOn " + isScheduleOn);
		Long jobId = null;
		
		if(isScheduleOn.equalsIgnoreCase("ON")) {
			logger.debug("startBatchUploadProcess -- ON ");
			BatchUpload pendingUploadRecord = batchUploadService.selectOnePending("Pending", getUploadType());
			if(pendingUploadRecord != null){
				JobParametersBuilder builder = new JobParametersBuilder();
				
				/**
				 * Prasanth :  US16352 : To upload data file     
				 */
				builder.addLong("stateId", pendingUploadRecord.getStateId());
				builder.addLong("districtId", pendingUploadRecord.getDistrictId());
				builder.addLong("schoolId", pendingUploadRecord.getSchoolId());
				builder.addLong("selectedOrgId", pendingUploadRecord.getSelectedOrgId());
				builder.addLong("uploadedUserOrgId", pendingUploadRecord.getUploadedUserOrgId());
				builder.addLong("uploadedUserGroupId", pendingUploadRecord.getUploadedUserGroupId());
				builder.addLong("subjectId", pendingUploadRecord.getContentAreaId());
				builder.addLong("assessmentProgramId", pendingUploadRecord.getAssessmentProgramId());
				builder.addString("UPLOAD_TYPE", "FILE_DATA");
				builder.addLong("uploadedUserId", pendingUploadRecord.getCreatedUser());
				
				//get Subject
				builder.addString("uploadTypeCode", pendingUploadRecord.getUploadType());
				//need unique job parameter to rerun the completed job
				builder.addDate("run date", new Date());
				builder.addString("inputFile", pendingUploadRecord.getFilePath());
				builder.addLong("batchUploadId", pendingUploadRecord.getId());
				builder.addString("recordType", getUploadType());
				builder.addLong("reportYear", pendingUploadRecord.getReportYear());
				builder.addLong("testingProgramId", pendingUploadRecord.getTestingProgramId());
				builder.addString("testingProgramName", pendingUploadRecord.getTestingProgramName());
				builder.addString("reportCycle", pendingUploadRecord.getReportCycle());
				builder.addLong("createdUser", pendingUploadRecord.getCreatedUser());
				if(uploadCommonGrfFileType.equalsIgnoreCase(pendingUploadRecord.getUploadType()) ||
						uploadIowaGrfFileType.equalsIgnoreCase(pendingUploadRecord.getUploadType())||
          				uploadNewYorkGrfFileType.equalsIgnoreCase(pendingUploadRecord.getUploadType()) ||
          				uploadDelawareGrfFileType.equalsIgnoreCase(pendingUploadRecord.getUploadType()) ||
          				uploadDCGrfFileType.equalsIgnoreCase(pendingUploadRecord.getUploadType()) ||
          				uploadArkansasGrfFileType.equalsIgnoreCase(pendingUploadRecord.getUploadType())){
					jobId = startJob(getBatchGRFProcessJob(), builder.toJobParameters());
				}else if(pendingUploadRecord.getFileName().endsWith("csv") ||  pendingUploadRecord.getFileName().endsWith("CSV")){
					jobId = startJob(batchUploadJob, builder.toJobParameters());
				}else{
					jobId = startJob(batchUploadExcelJob, builder.toJobParameters());
				}
				
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
	
	public void setBatchUploadExcelJob(Job batchUploadExcelJob) {
		this.batchUploadExcelJob = batchUploadExcelJob;
	}

	@Override
	public String getUploadType() {
		return "CSV_RECORD_TYPE";
	}

	public Job getBatchGRFProcessJob() {
		return batchGRFProcessJob;
	}

	public void setBatchGRFProcessJob(Job batchGRFProcessJob) {
		this.batchGRFProcessJob = batchGRFProcessJob;
	}

}
