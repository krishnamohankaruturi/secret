package edu.ku.cete.service.report;

import edu.ku.cete.domain.GrfStateApproveAudit;
import edu.ku.cete.domain.UploadGrfFile;

public interface UploadGrfFileWriterProcessService {
	Integer insertGrfFileRecords(UploadGrfFile uploadGrfFileRecord);

	void updateGrfFileRecordsByStateAndReportYear(Long state, Long reportYear, Long uploadedUserId,
			Long assessmentProgramId, Long batchProcessId, boolean Status);

	void deleteFailedBatchGrfFileRecords(Long batchUploadId);

	void clearRecordsOnOriginalGRFUpload(Long stateId, Long reportYear, Long assessmentProgramId);

	void setGRFAuditInfo(GrfStateApproveAudit audit);

	void setRecentFlag(Long batchUploadId, Long reportYear);

}
