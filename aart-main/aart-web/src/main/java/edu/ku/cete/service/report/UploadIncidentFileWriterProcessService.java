package edu.ku.cete.service.report;

import edu.ku.cete.domain.UploadIncidentFile;

public interface UploadIncidentFileWriterProcessService {
	

	Integer insertIncidentFileRecords(UploadIncidentFile uploadIncidentFileRecord);
	void updateIncidentFileRecordsByStateAndReportYear(Long state, Long reportYear,Long uploadedUserId,Long batchProcessId,boolean status);
	void deleteFailedBatchIncidentFileRecords(Long batchUploadId);
}
